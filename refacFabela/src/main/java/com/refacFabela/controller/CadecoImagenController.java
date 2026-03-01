package com.refacFabela.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.refacFabela.cdn.CadecoImageScraperService;

/**
 * Controller para extraccion de imagenes de productos desde proveedores externos (CADECO).
 * 
 * Endpoints:
 * - GET  /cadeco/imagen?noParte=xxx          -> Obtener URL de imagen de un producto
 * - POST /cadeco/imagenes/masivo             -> Extraccion masiva de imagenes
 * - POST /cadeco/imagen/descargar?noParte=xxx -> Descargar imagen individual
 */
@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class CadecoImagenController {

    private static final Logger logger = LogManager.getLogger("errorLogger");

    @Autowired
    private CadecoImageScraperService cadecoService;

    /**
     * Obtiene la URL de la primera imagen del carrusel de un producto en CADECO.
     * 
     * @param noParte Numero de parte / SKU del producto
     * @return JSON con url (alta resolucion), urlThumbnail, y encontrada (true/false)
     */
    @GetMapping(value = "/cadeco/imagen", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> obtenerImagenCadeco(@RequestParam String noParte) {
        try {
            String url = cadecoService.obtenerUrlImagenCadeco(noParte);
            Map<String, String> respuesta = new HashMap<>();

            if (url != null) {
                respuesta.put("url", url);
                respuesta.put("encontrada", "true");
                respuesta.put("noParte", noParte);
            } else {
                respuesta.put("url", "");
                respuesta.put("encontrada", "false");
                respuesta.put("noParte", noParte);
            }

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            logger.error("Error al obtener imagen CADECO [" + noParte + "]: " + e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("url", "");
            error.put("encontrada", "false");
            error.put("error", e.getMessage());
            return ResponseEntity.ok(error);
        }
    }

    /**
     * Descarga la imagen de un producto desde CADECO y la guarda localmente.
     * Opcionalmente la sube a Cloudflare R2.
     * 
     * @param noParte Numero de parte
     * @param subirR2 Si es true, tambien sube a R2 despues de descargar (default: false)
     * @return JSON con resultado de la descarga
     */
    @PostMapping(value = "/cadeco/imagen/descargar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> descargarImagenCadeco(
            @RequestParam String noParte,
            @RequestParam(required = false, defaultValue = "false") boolean subirR2) {
        try {
            String urlImagen = cadecoService.obtenerUrlImagenCadeco(noParte);
            Map<String, String> respuesta = new HashMap<>();

            if (urlImagen == null) {
                respuesta.put("noParte", noParte);
                respuesta.put("descargada", "false");
                respuesta.put("error", "No se encontro imagen en CADECO");
                return ResponseEntity.ok(respuesta);
            }

            String rutaLocal = cadecoService.descargarImagen(noParte, urlImagen);

            respuesta.put("noParte", noParte);
            respuesta.put("urlCadeco", urlImagen);

            if (rutaLocal != null) {
                respuesta.put("descargada", "true");
                respuesta.put("rutaLocal", rutaLocal);
            } else {
                respuesta.put("descargada", "false");
                respuesta.put("error", "Error al descargar la imagen");
            }

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            logger.error("Error al descargar imagen CADECO [" + noParte + "]: " + e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("noParte", noParte);
            error.put("descargada", "false");
            error.put("error", e.getMessage());
            return ResponseEntity.ok(error);
        }
    }

    /**
     * Extraccion masiva de imagenes de CADECO.
     * 
     * Recibe un body JSON con:
     * {
     *   "noPartes": ["1763137", "2346862", ...],
     *   "descargar": true/false,
     *   "subirR2": true/false
     * }
     * 
     * @return Estadisticas y detalle de cada producto procesado
     */
    @PostMapping(value = "/cadeco/imagenes/masivo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> extraerImagenesMasivo(@RequestBody Map<String, Object> request) {
        try {
            @SuppressWarnings("unchecked")
            List<String> noPartes = (List<String>) request.get("noPartes");
            boolean descargar = request.containsKey("descargar") && Boolean.TRUE.equals(request.get("descargar"));
            boolean subirR2 = request.containsKey("subirR2") && Boolean.TRUE.equals(request.get("subirR2"));

            if (noPartes == null || noPartes.isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Se requiere una lista de noPartes");
                return ResponseEntity.badRequest().body(error);
            }

            // Limitar a 100 productos por peticion para evitar timeouts
            if (noPartes.size() > 100) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Maximo 100 productos por peticion. Enviados: " + noPartes.size());
                return ResponseEntity.badRequest().body(error);
            }

            Map<String, Object> resultado = cadecoService.extraerImagenesMasivo(noPartes, descargar, subirR2);
            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            logger.error("Error en extraccion masiva CADECO: " + e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    /**
     * Limpia la cache de imagenes de CADECO.
     * 
     * @param noParte Si se especifica, limpia solo ese noParte. Si no, limpia toda la cache.
     */
    @PostMapping(value = "/cadeco/cache/limpiar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> limpiarCache(
            @RequestParam(required = false) String noParte) {
        Map<String, String> respuesta = new HashMap<>();
        if (noParte != null && !noParte.trim().isEmpty()) {
            cadecoService.invalidarCache(noParte);
            respuesta.put("mensaje", "Cache limpiada para: " + noParte);
        } else {
            cadecoService.limpiarCache();
            respuesta.put("mensaje", "Cache completa limpiada");
        }
        return ResponseEntity.ok(respuesta);
    }
}
