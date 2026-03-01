package com.refacFabela.cdn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio para extraer imagenes de productos desde cadeco.com.mx (VTEX).
 * 
 * Estrategia de extraccion:
 * 1. Buscar el producto en cadeco.com.mx por numero de parte
 * 2. Parsear el HTML para extraer la primera imagen del carrusel (vtexassets)
 * 3. Generar URL en alta resolucion cambiando width
 * 4. Opcionalmente descargar la imagen a disco local
 * 
 * El sitio usa VTEX y las imagenes se sirven desde:
 *   https://cadecomx.vtexassets.com/arquivos/ids/{ID}-{size}-auto?...&width={w}
 */
@Service
public class CadecoImageScraperService {

    private static final Logger logger = LogManager.getLogger("errorLogger");

    /** URL base de busqueda de CADECO */
    private static final String CADECO_SEARCH_URL = "https://www.cadeco.com.mx/%s/p";
    
    /** URL de busqueda inteligente VTEX */
    private static final String CADECO_SEARCH_API = "https://www.cadeco.com.mx/api/catalog_system/pub/products/search?fq=alternateIds_RefId:%s";

    /** Patron para extraer el ID de imagen de VTEX assets */
    private static final Pattern VTEX_IMAGE_ID_PATTERN = Pattern.compile(
            "vtexassets\\.com/arquivos/ids/(\\d+)");

    /** Patron para reemplazar el tamano en la URL */
    private static final Pattern VTEX_SIZE_PATTERN = Pattern.compile(
            "(arquivos/ids/\\d+)-\\d+-auto");

    /** Patron para reemplazar el width en query string */
    private static final Pattern WIDTH_PATTERN = Pattern.compile(
            "width=\\d+");

    /** Resolucion alta por defecto */
    private static final int HIGH_RES_WIDTH = 1200;

    /** Cache de URLs ya resueltas: noParte -> urlImagen */
    private final Map<String, String> cacheCadeco = new ConcurrentHashMap<>();

    @Autowired
    private CloudflareProperties cdnProperties;

    @Autowired
    private CloudflareImageService cloudflareService;

    /**
     * Extrae la URL de la primera imagen del carrusel de un producto en cadeco.com.mx
     * dado su numero de parte.
     *
     * @param noParte Numero de parte / SKU del producto
     * @return URL de la imagen en alta resolucion, o null si no se encontro
     */
    public String obtenerUrlImagenCadeco(String noParte) {
        if (noParte == null || noParte.trim().isEmpty()) {
            return null;
        }

        // Revisar cache
        String cacheada = cacheCadeco.get(noParte);
        if (cacheada != null) {
            return cacheada;
        }

        String urlImagen = null;

        // Estrategia 1: API de busqueda VTEX (mas confiable)
        urlImagen = buscarPorApiVtex(noParte);

        // Estrategia 2: Scraping directo del HTML de la pagina del producto
        if (urlImagen == null) {
            urlImagen = buscarPorScrapingHtml(noParte);
        }

        // Estrategia 3: Busqueda por URL directa con slug
        if (urlImagen == null) {
            urlImagen = buscarPorSlug(noParte);
        }

        if (urlImagen != null) {
            // Convertir a alta resolucion
            urlImagen = convertirAltaResolucion(urlImagen);
            cacheCadeco.put(noParte, urlImagen);
        }

        return urlImagen;
    }

    /**
     * Extrae imagenes de multiples productos en lote.
     *
     * @param noPartes Lista de numeros de parte
     * @param descargar Si true, descarga las imagenes al directorio local
     * @param subirR2   Si true, sube las imagenes descargadas a Cloudflare R2
     * @return Mapa con estadisticas y resultados por cada noParte
     */
    public Map<String, Object> extraerImagenesMasivo(List<String> noPartes, boolean descargar, boolean subirR2) {
        Map<String, Object> resultado = new LinkedHashMap<>();
        List<Map<String, String>> exitosos = new ArrayList<>();
        List<Map<String, String>> fallidos = new ArrayList<>();
        int totalProcesados = 0;

        for (String noParte : noPartes) {
            totalProcesados++;
            try {
                String urlImagen = obtenerUrlImagenCadeco(noParte);

                if (urlImagen != null) {
                    Map<String, String> info = new LinkedHashMap<>();
                    info.put("noParte", noParte);
                    info.put("urlCadeco", urlImagen);
                    info.put("urlThumbnail", convertirThumbnail(urlImagen));

                    if (descargar) {
                        String rutaLocal = descargarImagen(noParte, urlImagen);
                        info.put("rutaLocal", rutaLocal != null ? rutaLocal : "ERROR");

                        // Subir a R2 si se solicito y se descargo correctamente
                        if (subirR2 && rutaLocal != null) {
                            File archivo = new File(rutaLocal);
                            if (archivo.exists()) {
                                String urlR2 = cloudflareService.subirImagen(noParte, archivo);
                                info.put("urlR2", urlR2 != null ? urlR2 : "ERROR_SUBIDA");
                            }
                        }
                    }

                    exitosos.add(info);
                } else {
                    Map<String, String> info = new LinkedHashMap<>();
                    info.put("noParte", noParte);
                    info.put("error", "No se encontro imagen en CADECO");
                    fallidos.add(info);
                }

                // Pausa para no saturar el servidor de CADECO
                Thread.sleep(500);

            } catch (Exception e) {
                logger.error("Error procesando imagen CADECO para noParte=" + noParte + ": " + e.getMessage());
                Map<String, String> info = new LinkedHashMap<>();
                info.put("noParte", noParte);
                info.put("error", e.getMessage());
                fallidos.add(info);
            }
        }

        resultado.put("total", totalProcesados);
        resultado.put("exitosos", exitosos.size());
        resultado.put("fallidos", fallidos.size());
        resultado.put("detalleExitosos", exitosos);
        resultado.put("detalleFallidos", fallidos);

        return resultado;
    }

    /**
     * Descarga una imagen desde una URL y la guarda en el repositorio local.
     *
     * @param noParte    Numero de parte (se usa como nombre de archivo)
     * @param urlImagen  URL de la imagen a descargar
     * @return Ruta absoluta del archivo descargado, o null si fallo
     */
    public String descargarImagen(String noParte, String urlImagen) {
        try {
            String rutaBase = cdnProperties.getRutaImagenesLocal();
            Files.createDirectories(Paths.get(rutaBase));
            String rutaArchivo = rutaBase + noParte + ".jpg";

            URL url = new URL(urlImagen);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(15000);
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
            conn.setInstanceFollowRedirects(true);

            int code = conn.getResponseCode();
            if (code != 200) {
                logger.error("Error HTTP " + code + " al descargar imagen CADECO: " + urlImagen);
                conn.disconnect();
                return null;
            }

            InputStream is = conn.getInputStream();
            FileOutputStream fos = new FileOutputStream(rutaArchivo);
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fos.close();
            is.close();
            conn.disconnect();

            System.out.println("Imagen CADECO descargada: " + noParte + " -> " + rutaArchivo);
            return rutaArchivo;

        } catch (Exception e) {
            logger.error("Error al descargar imagen CADECO [" + noParte + "]: " + e.getMessage());
            return null;
        }
    }

    /**
     * Limpia la cache de CADECO para un noParte especifico.
     */
    public void invalidarCache(String noParte) {
        cacheCadeco.remove(noParte);
    }

    /**
     * Limpia toda la cache de CADECO.
     */
    public void limpiarCache() {
        cacheCadeco.clear();
    }

    // ======================== Estrategias de extraccion ========================

    /**
     * Estrategia 1: Usa la API de catalogo de VTEX para buscar por RefId.
     * Retorna la URL de la primera imagen encontrada.
     */
    private String buscarPorApiVtex(String noParte) {
        try {
            String apiUrl = String.format(CADECO_SEARCH_API, URLEncoder.encode(noParte, "UTF-8"));

            HttpURLConnection conn = crearConexion(apiUrl);
            int code = conn.getResponseCode();

            if (code == 200) {
                InputStream is = conn.getInputStream();
                byte[] bytes = leerTodosBytes(is);
                is.close();
                conn.disconnect();
                String json = new String(bytes, "UTF-8");

                // Buscar la primera URL de imagen en el JSON
                // El JSON de VTEX tiene un campo "images" con URLs
                String url = extraerImagenDeJson(json);
                if (url != null) {
                    System.out.println("CADECO API VTEX: imagen encontrada para " + noParte);
                    return url;
                }
            } else {
                conn.disconnect();
            }
        } catch (Exception e) {
            logger.error("Error en API VTEX para " + noParte + ": " + e.getMessage());
        }
        return null;
    }

    /**
     * Estrategia 2: Scraping directo del HTML buscando en la pagina de busqueda.
     */
    private String buscarPorScrapingHtml(String noParte) {
        try {
            // Buscar en el buscador de CADECO
            String searchUrl = "https://www.cadeco.com.mx/" + URLEncoder.encode(noParte, "UTF-8");

            Document doc = Jsoup.connect(searchUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(15000)
                    .followRedirects(true)
                    .get();

            // Buscar imagenes de VTEX assets en el HTML
            String url = extraerPrimeraImagenVtex(doc);
            if (url != null) {
                System.out.println("CADECO Scraping HTML: imagen encontrada para " + noParte);
                return url;
            }

        } catch (Exception e) {
            logger.error("Error en scraping HTML para " + noParte + ": " + e.getMessage());
        }
        return null;
    }

    /**
     * Estrategia 3: Intentar acceder directamente por un slug construido.
     * Formato tipico: https://www.cadeco.com.mx/{slug}-{noParte}/p
     */
    private String buscarPorSlug(String noParte) {
        try {
            // Intentar patron URL directa con solo el noParte
            String directUrl = String.format(CADECO_SEARCH_URL, noParte);

            Document doc = Jsoup.connect(directUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(15000)
                    .followRedirects(true)
                    .ignoreHttpErrors(true)
                    .get();

            // Si la pagina existe, buscar la imagen
            if (doc.title() != null && !doc.title().isEmpty()
                    && !doc.title().toLowerCase().contains("404")
                    && !doc.title().toLowerCase().contains("no encontr")) {

                String url = extraerPrimeraImagenVtex(doc);
                if (url != null) {
                    System.out.println("CADECO Slug: imagen encontrada para " + noParte);
                    return url;
                }
            }

            // Intentar busqueda en el sitio
            String searchUrl = "https://www.cadeco.com.mx/busca?ft=" + URLEncoder.encode(noParte, "UTF-8");
            doc = Jsoup.connect(searchUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(15000)
                    .followRedirects(true)
                    .ignoreHttpErrors(true)
                    .get();

            String url = extraerPrimeraImagenVtex(doc);
            if (url != null) {
                System.out.println("CADECO Busqueda: imagen encontrada para " + noParte);
                return url;
            }

        } catch (Exception e) {
            logger.error("Error en busqueda por slug para " + noParte + ": " + e.getMessage());
        }
        return null;
    }

    // ======================== Utilidades ========================

    /**
     * Extrae la primera URL de imagen de VTEX assets de un documento HTML.
     * Busca en selectores comunes de carrusel VTEX.
     */
    private String extraerPrimeraImagenVtex(Document doc) {
        // Selector 1: Imagenes del carrusel de producto VTEX IO
        String[] selectores = {
                "img[src*=vtexassets.com/arquivos/ids]",
                "img[src*=vteximg.com.br/arquivos/ids]",
                "img[data-src*=vtexassets.com/arquivos/ids]",
                "img[data-src*=vteximg.com.br/arquivos/ids]",
                "[class*=productImage] img",
                "[class*=carousel] img",
                "[class*=swiper] img[src*=vtex]",
                "picture source[srcset*=vtexassets]",
                "noscript img[src*=vtex]"
        };

        for (String selector : selectores) {
            Elements imgs = doc.select(selector);
            for (Element img : imgs) {
                String src = img.hasAttr("src") ? img.attr("src") : null;
                if (src == null || src.isEmpty()) {
                    src = img.hasAttr("data-src") ? img.attr("data-src") : null;
                }
                if (src == null || src.isEmpty()) {
                    src = img.hasAttr("srcset") ? img.attr("srcset").split(",")[0].trim().split("\\s")[0] : null;
                }

                if (src != null && !src.isEmpty() && esUrlImagenVtex(src)) {
                    // Asegurar URL absoluta
                    if (src.startsWith("//")) {
                        src = "https:" + src;
                    }
                    return src;
                }
            }
        }

        // Fallback: buscar en todo el HTML con regex
        Matcher matcher = VTEX_IMAGE_ID_PATTERN.matcher(doc.html());
        if (matcher.find()) {
            String imageId = matcher.group(1);
            return "https://cadecomx.vtexassets.com/arquivos/ids/" + imageId + "-1200-auto?v=638";
        }

        return null;
    }

    /**
     * Extrae la primera URL de imagen de un JSON de la API de VTEX.
     * Busca en el campo "imageUrl" o "imageTag".
     */
    private String extraerImagenDeJson(String json) {
        // Buscar "imageUrl":"..." en el JSON
        Pattern imageUrlPattern = Pattern.compile("\"imageUrl\"\\s*:\\s*\"([^\"]+)\"");
        Matcher matcher = imageUrlPattern.matcher(json);
        if (matcher.find()) {
            String url = matcher.group(1);
            // Limpiar escapes
            url = url.replace("\\/", "/");
            if (url.startsWith("//")) {
                url = "https:" + url;
            }
            return url;
        }

        // Buscar imageId y construir URL
        Pattern imageIdPattern = Pattern.compile("\"imageId\"\\s*:\\s*\"([^\"]+)\"");
        matcher = imageIdPattern.matcher(json);
        if (matcher.find()) {
            String imageId = matcher.group(1);
            return "https://cadecomx.vtexassets.com/arquivos/ids/" + imageId + "-1200-auto";
        }

        // Buscar cualquier referencia a vtexassets con ID
        Matcher vtexMatcher = VTEX_IMAGE_ID_PATTERN.matcher(json);
        if (vtexMatcher.find()) {
            String id = vtexMatcher.group(1);
            return "https://cadecomx.vtexassets.com/arquivos/ids/" + id + "-1200-auto";
        }

        return null;
    }

    /**
     * Convierte una URL de VTEX a alta resolucion (1200px).
     */
    private String convertirAltaResolucion(String url) {
        if (url == null) return null;

        // Reemplazar el segmento de tamano: -150-auto -> -1200-auto
        Matcher sizeMatcher = VTEX_SIZE_PATTERN.matcher(url);
        if (sizeMatcher.find()) {
            url = sizeMatcher.replaceFirst("$1-" + HIGH_RES_WIDTH + "-auto");
        }

        // Reemplazar width=150 -> width=1200
        Matcher widthMatcher = WIDTH_PATTERN.matcher(url);
        if (widthMatcher.find()) {
            url = widthMatcher.replaceFirst("width=" + HIGH_RES_WIDTH);
        }

        return url;
    }

    /**
     * Convierte una URL a thumbnail (150px).
     */
    private String convertirThumbnail(String url) {
        if (url == null) return null;

        Matcher sizeMatcher = VTEX_SIZE_PATTERN.matcher(url);
        if (sizeMatcher.find()) {
            url = sizeMatcher.replaceFirst("$1-150-auto");
        }

        Matcher widthMatcher = WIDTH_PATTERN.matcher(url);
        if (widthMatcher.find()) {
            url = widthMatcher.replaceFirst("width=150");
        }

        return url;
    }

    /**
     * Verifica si una URL corresponde a una imagen de VTEX.
     */
    private boolean esUrlImagenVtex(String url) {
        return url != null && (url.contains("vtexassets.com/arquivos/ids")
                || url.contains("vteximg.com.br/arquivos/ids"));
    }

    /**
     * Crea una conexion HTTP con headers de navegador.
     */
    private HttpURLConnection crearConexion(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(15000);
        conn.setInstanceFollowRedirects(true);
        conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
        conn.setRequestProperty("Accept", "application/json, text/html, */*");
        conn.setRequestProperty("Accept-Language", "es-MX,es;q=0.9");
        return conn;
    }

    /**
     * Lee todos los bytes de un InputStream (compatible con Java 8).
     */
    private byte[] leerTodosBytes(InputStream is) throws Exception {
        java.io.ByteArrayOutputStream buffer = new java.io.ByteArrayOutputStream();
        byte[] data = new byte[8192];
        int bytesRead;
        while ((bytesRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        return buffer.toByteArray();
    }
}
