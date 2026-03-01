package com.refacFabela.utils;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.cdn.CadecoImageScraperService;
import com.refacFabela.cdn.CloudflareImageService;
import com.refacFabela.cdn.CloudflareProperties;

/**
 * Servicio de resolución de imágenes de producto.
 * 
 * Prioridad de búsqueda:
 * 1. CDN del proveedor (Costex) — si la imagen existe en su servidor
 * 2. CDN propio (Cloudflare R2 / Jemkal) — si fue subida previamente
 * 3. Repositorio local (/opt/imgprod/) — si existe, se sube a Cloudflare automáticamente
 * 4. CADECO (scraping vtexassets) — busca imagen, la descarga y la sube a Cloudflare
 * 
 * Política de sincronización: toda imagen nueva encontrada en cualquier origen se replica
 * SIEMPRE a Cloudflare R2 Y al repositorio local, para que ambos estén siempre actualizados.
 * - Costex → descarga a local + sube a R2 (background)
 * - R2 → descarga a local si no existe (background)
 * - Local → sube a R2
 * - CADECO → descarga a local + sube a R2
 */
@Service
public class ImagenProductoService {

    @Autowired
    private CloudflareImageService cloudflareService;

    @Autowired
    private CloudflareProperties cdnProperties;

    @Autowired
    private CadecoImageScraperService cadecoService;

    /**
     * Caché en memoria para evitar verificaciones HTTP repetidas al CDN de Costex.
     * Key: noParte, Value: true si la imagen existe en Costex
     */
    private final Map<String, Boolean> cacheCostex = new ConcurrentHashMap<>();

    /**
     * Caché en memoria para URLs ya resueltas.
     * Key: noParte, Value: URL pública resuelta
     */
    private final Map<String, String> cacheUrlResuelta = new ConcurrentHashMap<>();

    /**
     * Resuelve la URL de imagen de un producto siguiendo la prioridad:
     * 1. CDN Costex (proveedor)
     * 2. Cloudflare CDN (propio - jemkal)
     * 3. Repositorio local (sube a Cloudflare si lo encuentra)
     * 4. CADECO (scraping, descarga y sube a Cloudflare)
     *
     * @param noParte Número de parte del producto
     * @return URL pública de la imagen, o null si no se encontró en ningún origen
     */
    public String resolverUrlImagen(String noParte) {
        if (noParte == null || noParte.trim().isEmpty()) {
            return null;
        }

        // Revisar caché primero
        String urlCacheada = cacheUrlResuelta.get(noParte);
        if (urlCacheada != null) {
            return urlCacheada;
        }

        // 1. Verificar CDN de Costex
        String urlCostex = verificarCostex(noParte);
        if (urlCostex != null) {
            // Costex tiene la imagen. Verificar si ya tenemos copia en R2 y local.
            // Si R2 ya la tiene, preferir esa URL (CDN propio).
            String urlR2Existente = verificarCloudflare(noParte);
            if (urlR2Existente != null) {
                // Ya está en R2; solo asegurar copia local
                asegurarCopiaLocalEnBackground(noParte, urlR2Existente);
                cacheUrlResuelta.put(noParte, urlR2Existente);
                return urlR2Existente;
            }
            // No está en R2: sincronizar a R2 + local en background
            subirDesdeUrlEnBackground(noParte, urlCostex);
            cacheUrlResuelta.put(noParte, urlCostex);
            return urlCostex;
        }

        // 2. Verificar Cloudflare CDN (jemkal)
        String urlCloudflare = verificarCloudflare(noParte);
        if (urlCloudflare != null) {
            // Asegurar copia local si no existe
            asegurarCopiaLocalEnBackground(noParte, urlCloudflare);
            cacheUrlResuelta.put(noParte, urlCloudflare);
            return urlCloudflare;
        }

        // 3. Verificar repositorio local y subir a Cloudflare
        String urlDesdeLocal = verificarLocalYSubir(noParte);
        if (urlDesdeLocal != null) {
            cacheUrlResuelta.put(noParte, urlDesdeLocal);
            return urlDesdeLocal;
        }

        // 4. Buscar en CADECO (scraping) y subir a Cloudflare
        String urlDesdeCadeco = verificarCadecoYSubir(noParte);
        if (urlDesdeCadeco != null) {
            cacheUrlResuelta.put(noParte, urlDesdeCadeco);
            return urlDesdeCadeco;
        }

        return null;
    }

    /**
     * Resuelve la URL con un fallback por defecto.
     * Si no encuentra imagen en ningún CDN, retorna la URL de Costex (para que
     * el frontend pueda manejar el error con su handler de imagen por defecto).
     *
     * @param noParte Número de parte del producto
     * @return URL de imagen (nunca null)
     */
    public String resolverUrlImagenConFallback(String noParte) {
        String url = resolverUrlImagen(noParte);
        if (url != null) {
            return url;
        }
        // Fallback: URL de Costex (el frontend maneja el error si no existe)
        return cdnProperties.getCostexBaseUrl() + noParte + ".jpg";
    }

    /**
     * Fuerza la subida de una imagen local a Cloudflare, sin importar si ya existe.
     * Útil para actualizar imágenes que cambiaron localmente.
     *
     * @param noParte Número de parte del producto
     * @return URL de Cloudflare, o null si falló
     */
    public String forzarSubidaCloudflare(String noParte) {
        String rutaLocal = cdnProperties.getRutaImagenesLocal() + noParte + ".jpg";
        File archivoLocal = new File(rutaLocal);

        if (!archivoLocal.exists() || !archivoLocal.isFile()) {
            System.err.println("No existe imagen local para subir: " + rutaLocal);
            return null;
        }

        String urlCloudflare = cloudflareService.subirImagen(noParte, archivoLocal);
        if (urlCloudflare != null) {
            cacheUrlResuelta.put(noParte, urlCloudflare);
            cacheCostex.remove(noParte);
        }
        return urlCloudflare;
    }

    /**
     * Limpia la caché de un producto específico (útil tras actualizar imagen).
     */
    public void invalidarCache(String noParte) {
        cacheUrlResuelta.remove(noParte);
        cacheCostex.remove(noParte);
        cadecoService.invalidarCache(noParte);
    }

    /**
     * Limpia toda la caché de URLs resueltas.
     */
    public void limpiarCache() {
        cacheUrlResuelta.clear();
        cacheCostex.clear();
        cadecoService.limpiarCache();
    }

    // ======================== Métodos privados ========================

    /**
     * Verifica si la imagen existe en el CDN de Costex.
     */
    private String verificarCostex(String noParte) {
        Boolean existeEnCache = cacheCostex.get(noParte);
        if (existeEnCache != null) {
            if (existeEnCache) {
                return cdnProperties.getCostexBaseUrl() + noParte + ".jpg";
            }
            return null;
        }

        String urlCostex = cdnProperties.getCostexBaseUrl() + noParte + ".jpg";
        boolean accesible = cloudflareService.verificarUrlAccesible(urlCostex);
        cacheCostex.put(noParte, accesible);

        if (accesible) {
            return urlCostex;
        }
        return null;
    }

    /**
     * Verifica si la imagen existe en Cloudflare CDN (jemkal).
     */
    private String verificarCloudflare(String noParte) {
        if (!cdnProperties.isR2Configurado()) {
            return null;
        }

        if (cloudflareService.existeImagen(noParte)) {
            return cloudflareService.obtenerUrlPublica(noParte);
        }
        return null;
    }

    /**
     * Verifica si existe copia local de la imagen. Si no existe, la descarga
     * desde la URL proporcionada (R2/Costex) en un hilo separado.
     * Garantiza que el repositorio local siempre tenga copia de las imágenes en R2.
     */
    private void asegurarCopiaLocalEnBackground(final String noParte, final String urlOrigen) {
        String rutaLocal = cdnProperties.getRutaImagenesLocal() + noParte + ".jpg";
        File archivoLocal = new File(rutaLocal);
        if (archivoLocal.exists()) {
            return; // Ya existe localmente, no hacer nada
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String ruta = cadecoService.descargarImagen(noParte, urlOrigen);
                    if (ruta != null) {
                        System.out.println("Background: imagen R2 copiada a local: " + noParte);
                    }
                } catch (Exception e) {
                    System.err.println("Background: error al copiar imagen a local para " + noParte + ": " + e.getMessage());
                }
            }
        }).start();
    }

    /**
     * Verifica si la imagen existe en el repositorio local.
     * Si la encuentra, la sube automáticamente a Cloudflare.
     */
    private String verificarLocalYSubir(String noParte) {
        String rutaLocal = cdnProperties.getRutaImagenesLocal() + noParte + ".jpg";
        File archivoLocal = new File(rutaLocal);

        if (!archivoLocal.exists() || !archivoLocal.isFile()) {
            return null;
        }

        System.out.println("Imagen encontrada en local: " + rutaLocal + " -> subiendo a Cloudflare...");

        // Subir a Cloudflare automáticamente
        String urlCloudflare = cloudflareService.subirImagen(noParte, archivoLocal);
        if (urlCloudflare != null) {
            return urlCloudflare;
        }

        // Si Cloudflare no está configurado o falló, no podemos servir desde local vía URL
        System.err.println("Imagen local encontrada pero no se pudo subir a Cloudflare: " + noParte);
        return null;
    }

    /**
     * Busca la imagen en CADECO (scraping de cadeco.com.mx).
     * Si la encuentra, la descarga al repositorio local y la sube a Cloudflare R2.
     * Retorna la URL de Cloudflare (jemkal) para servir desde CDN propio.
     */
    private String verificarCadecoYSubir(String noParte) {
        try {
            String urlCadeco = cadecoService.obtenerUrlImagenCadeco(noParte);
            if (urlCadeco == null) {
                return null;
            }

            System.out.println("Imagen encontrada en CADECO para " + noParte + ": " + urlCadeco);

            // Descargar la imagen al repositorio local
            String rutaLocal = cadecoService.descargarImagen(noParte, urlCadeco);
            if (rutaLocal == null) {
                System.err.println("No se pudo descargar imagen de CADECO para: " + noParte);
                // Retornar la URL de CADECO directamente como fallback
                return urlCadeco;
            }

            // Subir a Cloudflare R2 desde el archivo descargado
            File archivoLocal = new File(rutaLocal);
            if (archivoLocal.exists()) {
                String urlR2 = cloudflareService.subirImagen(noParte, archivoLocal);
                if (urlR2 != null) {
                    System.out.println("Imagen CADECO subida a R2: " + noParte + " -> " + urlR2);
                    return urlR2;
                }
            }

            // Si no se pudo subir a R2, retornar la URL de CADECO directamente
            return urlCadeco;

        } catch (Exception e) {
            System.err.println("Error al buscar imagen en CADECO para " + noParte + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Sincroniza una imagen externa (Costex/CADECO) a AMBOS destinos en background:
     * 1. Repositorio local (descarga)
     * 2. Cloudflare R2 (sube)
     * No bloquea el hilo principal; es un "best effort" para poblar ambos repositorios.
     */
    private void subirDesdeUrlEnBackground(final String noParte, final String urlOrigen) {
        final boolean faltaR2 = cdnProperties.isR2Configurado() && !cloudflareService.existeImagen(noParte);
        final String rutaLocalPath = cdnProperties.getRutaImagenesLocal() + noParte + ".jpg";
        final boolean faltaLocal = !new File(rutaLocalPath).exists();

        if (!faltaR2 && !faltaLocal) {
            return; // Ya existe en ambos destinos
        }

        // Ejecutar en un hilo separado para no bloquear la respuesta
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Siempre descargar a local si no existe
                    String rutaDescargada = rutaLocalPath;
                    if (faltaLocal) {
                        rutaDescargada = cadecoService.descargarImagen(noParte, urlOrigen);
                        if (rutaDescargada != null) {
                            System.out.println("Background: imagen descargada a local: " + noParte);
                        }
                    }

                    // Subir a R2 si no existe ahí
                    if (faltaR2 && rutaDescargada != null) {
                        File archivo = new File(rutaDescargada);
                        if (archivo.exists()) {
                            String urlR2 = cloudflareService.subirImagen(noParte, archivo);
                            if (urlR2 != null) {
                                System.out.println("Background: imagen subida a R2: " + noParte);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Background: error al sincronizar imagen para " + noParte + ": " + e.getMessage());
                }
            }
        }).start();
    }
}
