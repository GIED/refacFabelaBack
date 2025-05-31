package com.refacFabela.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.refacFabela.service.CtpService;

@Service
public class CtpServiceImpl implements CtpService {

    private static final Logger logger = LoggerFactory.getLogger(CtpServiceImpl.class);
    private static final String URL = "https://www.ctpsales.costex.com:11443/WebServices/costex/partService/partController.php";

    @Override
    public Part consultarParte(String numeroParte, String cantidad) {
        logger.info("Consultando parte: {} con cantidad: {}", numeroParte, cantidad);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("format", CtpConstants.FORMAT.value());
        formData.add("acckey", CtpConstants.ACCKEY.value());
        formData.add("userid", CtpConstants.USERID.value());
        formData.add("passw", CtpConstants.PASSWORD.value());
        formData.add("cust", CtpConstants.CUSTOMER.value());
        formData.add("branch", CtpConstants.BRANCH.value());
        formData.add("partn", numeroParte);
        formData.add("qty", cantidad);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        try {
        	RestTemplate restTemplate = new RestTemplate();
        	ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, request, String.class);

        	logger.debug("Respuesta HTTP: {}", response.getStatusCode());
        	logger.debug("Cuerpo: {}", response.getBody());

        	ObjectMapper mapper = new ObjectMapper();
        	JsonNode root = mapper.readTree(response.getBody());

        	// Convertir el nodo raíz a Part
        	Part part = mapper.treeToValue(root, Part.class);

        	// Procesar Locations si existe
        	JsonNode locationsNode = root.get("Locations");
        	if (locationsNode != null && locationsNode.isObject()) {
        	    Map<String, Location> locationMap = new LinkedHashMap<>();
        	    Iterator<Map.Entry<String, JsonNode>> fields = locationsNode.fields();  // CORREGIDO: definir "fields"
        	    int i = 1;
        	    while (fields.hasNext()) {
        	        JsonNode locNode = fields.next().getValue();
        	        Location location = mapper.treeToValue(locNode, Location.class);
        	        locationMap.put("Location" + i++, location);
        	    }
        	    part.setLocations(locationMap);  // Usa setLocations(Map)
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
}
