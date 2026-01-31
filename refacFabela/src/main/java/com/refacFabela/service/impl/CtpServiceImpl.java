package com.refacFabela.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.refacFabela.enums.CtpConstants;
import com.refacFabela.model.Location;
import com.refacFabela.model.Part;
import com.refacFabela.model.TcProducto;
import com.refacFabela.model.WsError;
import com.refacFabela.repository.ProductosRepository;
import com.refacFabela.service.CtpService;
import com.refacFabela.utils.subirArchivo;

@Service
public class CtpServiceImpl implements CtpService {

    private static final Logger logger = LoggerFactory.getLogger(CtpServiceImpl.class);
    private static final String URL = "https://www.ctpsales.costex.com:11443/WebServices/costex/partService/partController.php";
    
    @Autowired
    private ProductosRepository productosRepository;
    
    private final subirArchivo archivoHelper = new subirArchivo();
    private static final String RUTA_IMAGENES = "/opt/imgprod/";

    
    @Override
    public Part consultarParte(String numeroParte, String cantidad) {
        logger.info("Consultando parte: {} con cantidad: {}", numeroParte, cantidad);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("format",  CtpConstants.FORMAT.value());
        formData.add("acckey",  CtpConstants.ACCKEY.value());
        formData.add("userid",  CtpConstants.USERID.value());
        formData.add("passw",   CtpConstants.PASSWORD.value());
        formData.add("cust",    CtpConstants.CUSTOMER.value());
        formData.add("branch",  CtpConstants.BRANCH.value());
        formData.add("partn",   numeroParte);
        formData.add("qty",     cantidad);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, request, String.class);

            String body = response.getBody();
            logger.debug("Respuesta HTTP: {}", response.getStatusCode());
            logger.debug("Cuerpo WS Costex: {}", body);

            if (body == null || body.trim().isEmpty()) {
                throw new RuntimeException("Respuesta vacía del WS de Costex.");
            }

            ObjectMapper mapper = new ObjectMapper()
                .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

            JsonNode root = mapper.readTree(body);
            if (root == null || root.isMissingNode()) {
                throw new RuntimeException("No se pudo parsear el JSON del WS de Costex.");
            }

            // 1) Guard clause si viene error
            if (root.hasNonNull("ErrorCode")) {
                WsError err = mapper.treeToValue(root, WsError.class);
                String code = err.getErrorCode();
                
                // Si el error es PI004 (Parte no encontrada), devolvemos null para que el proceso continúe
                if ("PI004".equals(code)) {
                    logger.warn("Parte {} no encontrada en Costex (PI004)", numeroParte);
                    return null;
                }

                String msg  = err.getErrorMessage();
                logger.error("WS Costex devolvió error {} - {}. Raw: {}", code, msg, body);
                throw new RuntimeException("WS Costex error " + code + (msg != null ? (": " + msg) : ""));
            }

            // 2) Mapear a Part
            Part part = mapper.treeToValue(root, Part.class);

            // 3) Procesar Locations (aceptar objeto o arreglo)
            JsonNode locationsNode = root.get("Locations");
            if (locationsNode != null && !locationsNode.isNull()) {
                Map<String, Location> locationMap = new LinkedHashMap<>();

                if (locationsNode.isObject()) {
                    // { "Location1": {...}, "Location2": {...} }
                    Iterator<Map.Entry<String, JsonNode>> fields = locationsNode.fields();
                    while (fields.hasNext()) {
                        Map.Entry<String, JsonNode> entry = fields.next();
                        Location location = mapper.treeToValue(entry.getValue(), Location.class);
                        locationMap.put(entry.getKey(), location);
                    }
                } else if (locationsNode.isArray()) {
                    // [ {...}, {...} ]
                    int i = 1;
                    for (JsonNode node : locationsNode) {
                        Location location = mapper.treeToValue(node, Location.class);
                        locationMap.put("Location" + i++, location);
                    }
                } else {
                    logger.warn("Formato inesperado en 'Locations': {}", locationsNode.getNodeType());
                }

                if (!locationMap.isEmpty()) {
                    part.setLocations(locationMap);
                }
            }

            return part;

        } catch (RestClientException restEx) {
            logger.error("Error de conexión con el WS de Costex", restEx);
            throw new RuntimeException("No se pudo conectar al servicio de Costex.", restEx);
        } catch (Exception e) {
            logger.error("Error al procesar la respuesta del WS de Costex", e);
            throw new RuntimeException("Error al procesar la respuesta del WS de Costex.", e);
        }
    }

    @Override
    public List<Part> consultarCostexFaltantes(int limit) {
        List<Part> partes = new ArrayList<>();
        
        try {
            List<TcProducto> productos = productosRepository.findProductosCtpFaltantes(PageRequest.of(0, limit));
            
            for (TcProducto p : productos) {
                try {
                    // Usamos el metodo existente consultarParte
                    if (p.getsNoParte() != null) {
                        Part part = consultarParte(p.getsNoParte(), "1");
                        if (part != null) {
                            // Actualizar Peso (Kgs)
                            if (part.getDblWeigthKgs() != null && !part.getDblWeigthKgs().isEmpty()) {
                                try {
                                    BigDecimal peso = new BigDecimal(part.getDblWeigthKgs());
                                    boolean tienePesoSistema = p.getnPeso() != null && p.getnPeso().compareTo(BigDecimal.ZERO) > 0;
                                    boolean wsMayorCero = peso.compareTo(BigDecimal.ZERO) > 0;
                                    
                                    if (wsMayorCero || (!tienePesoSistema && peso.compareTo(BigDecimal.ZERO) >= 0)) {
                                        p.setnPeso(peso);
                                    }
                                } catch(NumberFormatException e) {
                                  logger.warn("Error al convertir peso para {}", p.getsNoParte());
                                }
                            }
                            
                            // Actualizar Dimensiones (Pulgadas -> CM) * 2.54
                            BigDecimal factorConversion = new BigDecimal("2.54");
                            
                            if (part.getDblLengthIn() != null && !part.getDblLengthIn().isEmpty()) {
                                try {
                                    BigDecimal largo = new BigDecimal(part.getDblLengthIn());
                                    boolean tieneLargoSistema = p.getnLargo() != null && p.getnLargo().compareTo(BigDecimal.ZERO) > 0;
                                    boolean wsMayorCero = largo.compareTo(BigDecimal.ZERO) > 0;

                                    if (wsMayorCero || (!tieneLargoSistema && largo.compareTo(BigDecimal.ZERO) >= 0)) {
                                        p.setnLargo(largo.multiply(factorConversion));
                                    }
                                } catch(NumberFormatException e) {
                                  logger.warn("Error al convertir largo para {}", p.getsNoParte());
                                }
                            }
                            
                            if (part.getDblWidthIn() != null && !part.getDblWidthIn().isEmpty()) {
                                try {
                                    BigDecimal ancho = new BigDecimal(part.getDblWidthIn());
                                    boolean tieneAnchoSistema = p.getnAncho() != null && p.getnAncho().compareTo(BigDecimal.ZERO) > 0;
                                    boolean wsMayorCero = ancho.compareTo(BigDecimal.ZERO) > 0;

                                    if (wsMayorCero || (!tieneAnchoSistema && ancho.compareTo(BigDecimal.ZERO) >= 0)) {
                                        p.setnAncho(ancho.multiply(factorConversion));
                                    }
                                } catch(NumberFormatException e) {
                                  logger.warn("Error al convertir ancho para {}", p.getsNoParte());
                                }
                            }
                            
                            if (part.getDblHeightIn() != null && !part.getDblHeightIn().isEmpty()) {
                                try {
                                    BigDecimal alto = new BigDecimal(part.getDblHeightIn());
                                    boolean tieneAltoSistema = p.getnAlto() != null && p.getnAlto().compareTo(BigDecimal.ZERO) > 0;
                                    boolean wsMayorCero = alto.compareTo(BigDecimal.ZERO) > 0;

                                    if (wsMayorCero || (!tieneAltoSistema && alto.compareTo(BigDecimal.ZERO) >= 0)) {
                                        p.setnAlto(alto.multiply(factorConversion));
                                    }
                                } catch(NumberFormatException e) {
                                  logger.warn("Error al convertir alto para {}", p.getsNoParte());
                                }
                            }
                                                        // Calcular y actualizar Volumen
                            if (p.getnLargo() != null && p.getnAncho() != null && p.getnAlto() != null) {
								BigDecimal volumen = p.getnLargo().multiply(p.getnAncho()).multiply(p.getnAlto());
								p.setnVolumen(volumen);
							}
                                                        // Actualizar Imagen si no tiene
                            if (p.getsRutaImagen() == null || p.getsRutaImagen().isEmpty() || p.getsRutaImagen().equals("CONFRONTADO")) {
                                try {
                                   boolean imagenGuardada = archivoHelper.procesarImagenProducto(p, RUTA_IMAGENES);
                                   if (imagenGuardada) {
                                       String nombreArchivo = p.getsNoParte() + ".jpg";
                                       String rutaFinal = RUTA_IMAGENES + nombreArchivo;
                                       p.setsRutaImagen(rutaFinal);
                                   } else {
                                	   // Si no se encuentra imagen, marcar como CONFRONTADO
                                	   p.setsRutaImagen("CONFRONTADO");
                                   }
                                } catch (Exception e) {
                                    logger.error("Error al procesar imagen para {}: {}", p.getsNoParte(), e.getMessage());
                                    p.setsRutaImagen("CONFRONTADO");
                                }
                            }
                            
                            // Guardar cambios
                            productosRepository.save(p);
                            if (part.getStrPartNumber() == null || part.getStrPartNumber().isEmpty()) {
                                part.setStrPartNumber(p.getsNoParte());
                            }
                            partes.add(part);
                        } else {
                        	// Parte no encontrada en Costex
                        	p.setsRutaImagen("NO ENCONTRADO");
                        	productosRepository.save(p);
                        	
                        	Part notFoundPart = new Part();
                            notFoundPart.setStrPartNumber(p.getsNoParte());
                            notFoundPart.setStrDescrip1("NO ENCONTRADO");
                            partes.add(notFoundPart);
                        }
                    }
                } catch (Exception e) {
                    logger.error("Error al consultar Costex para parte: {} Error: {}", p.getsNoParte(), e.getMessage());
                }
            }
            
        } catch (Exception e) {
            logger.error("Error al obtener lista de productos faltantes", e);
        }
        return partes;
    }

}
