package com.refacFabela.utils;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.cdn.CloudflareImageService;
import com.refacFabela.cdn.CloudflareProperties;

/**
 * Servicio de resolución de imágenes de producto.
 * 
 * Prioridad de búsqueda:
 * 1. CDN propio (Cloudflare R2 / Jemkal) — si fue subida previamente
 * 2. Repositorio local (/opt/imgprod/) — si existe, se sube a Cloudflare automáticamente
 * 3. CDN del proveedor (Costex) — si la imagen existe en su servidor
 * 
 * Política de sincronización: toda imagen nueva encontrada en cualquier origen se replica
 * SIEMPRE a Cloudflare R2 Y al repositorio local, para que ambos estén siempre actualizados.
 * - Imagen manual/local → sube a R2 y se sirve con versionado para evitar caché vieja
 * - Costex → descarga a local + sube a R2 (background)
 * - R2 → descarga a local si no existe (background)
 * - Local → sube a R2
 */
@Service
public class ImagenProductoService {

    @Autowired
    private CloudflareImageService cloudflareService;

    @Autowired
    private CloudflareProperties cdnProperties;

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
        * 1. Cloudflare CDN (propio - jemkal)
        * 2. Repositorio local (sube a Cloudflare si lo encuentra)
        * 3. CDN Costex (proveedor)
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

        // 1. Verificar Cloudflare CDN (jemkal). Debe tener prioridad para respetar
        // imágenes manuales cargadas desde el formulario. Si existe en Jemkal,
        // solo se muestra; no se sincroniza a local durante la resolución visual.
        String urlCloudflare = verificarCloudflare(noParte);
        if (urlCloudflare != null) {
            String urlVersionada = agregarVersionLocal(noParte, urlCloudflare);
            cacheUrlResuelta.put(noParte, urlVersionada);
            return urlVersionada;
        }

        // 2. Verificar repositorio local y subir a Cloudflare
        String urlDesdeLocal = verificarLocalYSubir(noParte);
        if (urlDesdeLocal != null) {
            cacheUrlResuelta.put(noParte, urlDesdeLocal);
            return urlDesdeLocal;
        }

        // 3. Verificar CDN de Costex
        String urlCostex = verificarCostex(noParte);
        if (urlCostex != null) {
            subirDesdeUrlEnBackground(noParte, urlCostex);
            cacheUrlResuelta.put(noParte, urlCostex);
            return urlCostex;
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
            urlCloudflare = agregarVersionLocal(noParte, urlCloudflare);
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
    }

    /**
     * Limpia toda la caché de URLs resueltas.
     */
    public void limpiarCache() {
        cacheUrlResuelta.clear();
        cacheCostex.clear();
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
     * Verifica si la imagen existe en el repositorio local.
     * Si la encuentra, la sube automáticamente a Cloudflare.
     */
    private String verificarLocalYSubir(String noParte) {
        String rutaLocal = rutaLocalProducto(noParte);
        File archivoLocal = new File(rutaLocal);

        if (!archivoLocal.exists() || !archivoLocal.isFile()) {
            return null;
        }

        System.out.println("Imagen encontrada en local: " + rutaLocal + " -> subiendo a Cloudflare...");

        // Subir a Cloudflare automáticamente
        String urlCloudflare = cloudflareService.subirImagen(noParte, archivoLocal);
        if (urlCloudflare != null) {
            return agregarVersionLocal(noParte, urlCloudflare);
        }

        // Si Cloudflare no está configurado o falló, no podemos servir desde local vía URL
        System.err.println("Imagen local encontrada pero no se pudo subir a Cloudflare: " + noParte);
        return null;
    }

    /**
     * Sincroniza una imagen externa de Costex a AMBOS destinos en background:
     * 1. Repositorio local (descarga)
     * 2. Cloudflare R2 (sube)
     * No bloquea el hilo principal; es un "best effort" para poblar ambos repositorios.
     */
    private void subirDesdeUrlEnBackground(final String noParte, final String urlOrigen) {
        final boolean faltaR2 = cdnProperties.isR2Configurado() && !cloudflareService.existeImagen(noParte);
        final String rutaLocalPath = rutaLocalProducto(noParte);
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
                        rutaDescargada = descargarImagenRemota(noParte, urlOrigen);
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

    private String rutaLocalProducto(String noParte) {
        return Paths.get(cdnProperties.getRutaImagenesLocal(), noParte + ".jpg").toString();
    }

    private String agregarVersionLocal(String noParte, String url) {
        try {
            File archivoLocal = new File(rutaLocalProducto(noParte));
            if (!archivoLocal.exists()) {
                return url;
            }
            String separador = url.contains("?") ? "&" : "?";
            return url + separador + "v=" + archivoLocal.lastModified();
        } catch (Exception e) {
            return url;
        }
    }

    String descargarImagenRemota(String noParte, String urlImagen) {
        HttpURLConnection conexion = null;
        try {
            Path rutaArchivo = Paths.get(rutaLocalProducto(noParte));
            Files.createDirectories(rutaArchivo.getParent());

            String publicUrlBase = cdnProperties.getR2PublicUrl();
            if (publicUrlBase != null && !publicUrlBase.trim().isEmpty()) {
                String baseNormalizada = publicUrlBase.endsWith("/")
                        ? publicUrlBase.substring(0, publicUrlBase.length() - 1)
                        : publicUrlBase;
                if (urlImagen != null && urlImagen.startsWith(baseNormalizada + "/")) {
                    boolean descargada = cloudflareService.descargarImagen(noParte, rutaArchivo.toFile());
                    if (descargada) {
                        return rutaArchivo.toString();
                    }
                }
            }

            URL url = new URL(urlImagen);
            conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");
            conexion.setConnectTimeout(5000);
            conexion.setReadTimeout(10000);
            conexion.setInstanceFollowRedirects(true);
            conexion.setRequestProperty("User-Agent", "Mozilla/5.0");
            conexion.setRequestProperty("Accept", "image/avif,image/webp,image/apng,image/*,*/*;q=0.8");

            int code = conexion.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                System.err.println("Error HTTP " + code + " al descargar imagen: " + urlImagen);
                return null;
            }

            try (InputStream inputStream = conexion.getInputStream()) {
                Files.copy(inputStream, rutaArchivo, StandardCopyOption.REPLACE_EXISTING);
            }

            return rutaArchivo.toString();
        } catch (Exception e) {
            System.err.println("Error al descargar imagen remota [" + noParte + "]: " + e.getMessage());
            return null;
        } finally {
            if (conexion != null) {
                conexion.disconnect();
            }
        }
    }
}
