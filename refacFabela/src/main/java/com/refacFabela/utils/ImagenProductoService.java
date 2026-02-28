package com.refacFabela.utils;

import java.io.File;
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
 * 1. CDN del proveedor (Costex) — si la imagen existe en su servidor
 * 2. CDN propio (Cloudflare Images) — si fue subida previamente
 * 3. Repositorio local (/opt/imgprod/) — si existe, se sube a Cloudflare automáticamente
 * 
 * El resultado siempre es una URL pública (Costex o Cloudflare), nunca una ruta local.
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
     * 1. CDN Costex (proveedor)
     * 2. Cloudflare CDN (propio)
     * 3. Repositorio local (sube a Cloudflare si lo encuentra)
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
            cacheUrlResuelta.put(noParte, urlCostex);
            return urlCostex;
        }

        // 2. Verificar Cloudflare CDN
        String urlCloudflare = verificarCloudflare(noParte);
        if (urlCloudflare != null) {
            cacheUrlResuelta.put(noParte, urlCloudflare);
            return urlCloudflare;
        }

        // 3. Verificar repositorio local y subir a Cloudflare
        String urlDesdeLocal = verificarLocalYSubir(noParte);
        if (urlDesdeLocal != null) {
            cacheUrlResuelta.put(noParte, urlDesdeLocal);
            return urlDesdeLocal;
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
     * Verifica si la imagen existe en Cloudflare CDN.
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
}
