package com.refacFabela.facturacionv2.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.refacFabela.facturacionv2.config.FacturacionProperties;
import com.refacFabela.facturacionv2.config.FacturoPorTiProperties;
import com.refacFabela.facturacionv2.dto.facturoporti.FacturoPorTiCancelacionRequest;
import com.refacFabela.facturacionv2.dto.facturoporti.FacturoPorTiCancelacionResponse;
import com.refacFabela.facturacionv2.dto.facturoporti.FacturoPorTiTimbradoRequest;
import com.refacFabela.facturacionv2.dto.facturoporti.FacturoPorTiTimbradoResponse;
import com.refacFabela.facturacionv2.dto.internal.ArchivoCfdiResponse;
import com.refacFabela.facturacionv2.dto.internal.CancelacionRequest;
import com.refacFabela.facturacionv2.dto.internal.CancelacionResponse;
import com.refacFabela.facturacionv2.dto.internal.CfdiRelacionadosResponse;
import com.refacFabela.facturacionv2.dto.internal.CfdiTimbradoRequest;
import com.refacFabela.facturacionv2.dto.internal.CodigoPostalResponse;
import com.refacFabela.facturacionv2.dto.internal.CatalogoSatResponse;
import com.refacFabela.facturacionv2.dto.internal.ComplementoPagoRequest;
import com.refacFabela.facturacionv2.dto.internal.DescargaMasivaXmlResponse;
import com.refacFabela.facturacionv2.dto.internal.SolicitudCancelacionDto;
import com.refacFabela.facturacionv2.dto.internal.StatusCfdiRequest;
import com.refacFabela.facturacionv2.dto.internal.StatusCfdiResponse;
import com.refacFabela.facturacionv2.dto.internal.TimbradoResponse;
import com.refacFabela.facturacionv2.dto.internal.ValidacionCertificadoResponse;
import com.refacFabela.facturacionv2.dto.internal.ValidacionEfosEdosResponse;
import com.refacFabela.facturacionv2.dto.internal.ValidacionRfcResponse;
import com.refacFabela.facturacionv2.exception.FacturoPorTiClientException;
import com.refacFabela.facturacionv2.service.FacturoPorTiMapper;
import com.refacFabela.facturacionv2.service.PacFacturacionClient;
import com.refacFabela.model.TcDatosFactura;
import com.refacFabela.repository.TcDatosFacturaRepository;

@Service
public class FacturoPorTiClient implements PacFacturacionClient {

	private static final Logger logger = LogManager.getLogger(FacturoPorTiClient.class);
	private static final String AUTH_TYPE_TOKEN = "token";
	private static final String AUTH_TYPE_USERNAME_PASSWORD = "username-password";
	private static final String TODO_VALIDAR_CON_FACTUROPORTI = "TODO_VALIDAR_CON_FACTUROPORTI";
	private static final String SANDBOX_BASE_URL = "https://testapi.facturoporti.com.mx";
	private static final String PRODUCTION_BASE_URL = "https://api.facturoporti.com.mx";
	private static final String TOKEN_PATH = "/token/crear";
	private static final String TIMBRADO_CFDI_PATH = "/servicios/timbrar/json";
	private static final String TIMBRADO_COMPLEMENTO_PAGO_PATH = "/servicios/timbrar/json";
	private static final String CANCELACION_PATH = "/servicios/cancelar/csd";
	private static final String CONSULTA_ESTATUS_PATH = "/validar/estatuscfdi";
	private static final String SOLICITUDES_PENDIENTES_PATH = "/servicios/consultar/solicitudespendientescancelacion";
	private static final String ACEPTAR_RECHAZAR_CANCELACION_PATH = "/servicios/cancelar/aceptarrechazar/csd";
	private static final String CFDI_RELACIONADOS_PATH = "/servicios/consultar/cfdisRelacionados/csd";
	private static final String CONSULTA_TIMBRES_PATH = "/servicios/consultar/timbres";

	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;
	private final FacturoPorTiProperties properties;
	private final FacturacionProperties facturacionProperties;
	private final FacturoPorTiMapper mapper;
	private final TcDatosFacturaRepository tcDatosFacturaRepository;

	public FacturoPorTiClient(@Qualifier("facturoPorTiRestTemplate") RestTemplate restTemplate,
			ObjectMapper objectMapper,
			FacturoPorTiProperties properties,
			FacturacionProperties facturacionProperties,
			FacturoPorTiMapper mapper,
			TcDatosFacturaRepository tcDatosFacturaRepository) {
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
		this.properties = properties;
		this.facturacionProperties = facturacionProperties;
		this.mapper = mapper;
		this.tcDatosFacturaRepository = tcDatosFacturaRepository;
	}

	@Override
	public TimbradoResponse timbrarCfdi(CfdiTimbradoRequest request) {
		validateActiveProvider();
		String endpoint = resolveEndpoint(TIMBRADO_CFDI_PATH);
		FacturoPorTiTimbradoRequest providerRequest = mapper.toFacturoPorTiTimbradoRequest(request);
		FacturoPorTiTimbradoResponse providerResponse = exchangeTimbrado(endpoint, providerRequest, request);
		return mapper.toTimbradoResponse(providerResponse, request);
	}

	@Override
	public TimbradoResponse timbrarComplementoPago(ComplementoPagoRequest request) {
		validateActiveProvider();
		String endpoint = resolveEndpoint(TIMBRADO_COMPLEMENTO_PAGO_PATH);
		FacturoPorTiTimbradoRequest providerRequest = mapper.toFacturoPorTiComplementoPagoRequest(request);
		CfdiTimbradoRequest internalRequest = new CfdiTimbradoRequest();
		internalRequest.setSerie(request.getMetadata() != null && request.getMetadata().get("serie") != null ? request.getMetadata().get("serie").toString() : null);
		internalRequest.setFolio(request.getMetadata() != null && request.getMetadata().get("folio") != null ? request.getMetadata().get("folio").toString() : null);
		internalRequest.setRazonSocialId(request.getRazonSocialId());
		internalRequest.setMoneda("XXX");
		internalRequest.setTipoDeComprobante("P");
		CfdiTimbradoRequest.EmisorDto emisor = new CfdiTimbradoRequest.EmisorDto();
		emisor.setRfc(request.getRfcEmisor());
		internalRequest.setEmisor(emisor);
		CfdiTimbradoRequest.ReceptorDto receptor = new CfdiTimbradoRequest.ReceptorDto();
		receptor.setRfc(request.getRfcReceptor());
		internalRequest.setReceptor(receptor);
		return mapper.toTimbradoResponse(exchangeTimbrado(endpoint, providerRequest, internalRequest), internalRequest);
	}

	@Override
	public CancelacionResponse cancelarCfdi(CancelacionRequest request) {
		validateActiveProvider();
		String endpoint = resolveEndpoint(CANCELACION_PATH);
		FacturoPorTiCancelacionRequest providerRequest = mapper.toFacturoPorTiCancelacionRequest(request);
		FacturoPorTiCancelacionResponse providerResponse = exchange(endpoint, HttpMethod.POST, providerRequest,
				extractMetadataToken(request != null ? request.getMetadata() : null),
				FacturoPorTiCancelacionResponse.class);
		return mapper.toCancelacionResponse(providerResponse);
	}

	@Override
	public StatusCfdiResponse consultarEstatusCfdi(StatusCfdiRequest request) {
		validateActiveProvider();
		String endpoint = resolveEndpoint(CONSULTA_ESTATUS_PATH);
		Map<String, Object> payload = new LinkedHashMap<String, Object>();
		payload.put("rfcEmisor", request.getRfcEmisor());
		payload.put("rfcReceptor", request.getRfcReceptor());
		payload.put("uuid", request.getUuid());
		payload.put("total", request.getTotal());
		payload.put("sello", request.getSello());
		return parseStatusResponse(exchangeForNode(endpoint, HttpMethod.POST, payload, request.getRfcEmisor(),
				extractMetadataToken(request != null ? request.getMetadata() : null)), request);
	}

	@Override
	public List<SolicitudCancelacionDto> consultarSolicitudesPendientesCancelacion(String rfcEmisor) {
		validateActiveProvider();
		String endpoint = resolveEndpoint(SOLICITUDES_PENDIENTES_PATH);
		String url = UriComponentsBuilder.fromHttpUrl(endpoint)
				.queryParam("rfc", rfcEmisor)
				.build(true)
				.toUriString();
		JsonNode root = exchangeForNode(url, HttpMethod.GET, null, rfcEmisor, null);
		return parseSolicitudesPendientes(root);
	}

	@Override
	public CancelacionResponse aceptarCancelacion(CancelacionRequest request) {
		return procesarAceptacionRechazo(request, "A");
	}

	@Override
	public CancelacionResponse rechazarCancelacion(CancelacionRequest request) {
		return procesarAceptacionRechazo(request, "R");
	}

	@Override
	public CfdiRelacionadosResponse consultarCfdiRelacionados(StatusCfdiRequest request) {
		validateActiveProvider();
		String endpoint = resolveEndpoint(CFDI_RELACIONADOS_PATH);
		Map<String, Object> payload = new LinkedHashMap<String, Object>();
		payload.put("rfc", request.getRfcEmisor());
		payload.put("uuid", request.getUuid());
		if (request.getMetadata() != null) {
			payload.put("certificado", normalizeBase64Value(request.getMetadata().get("certificado")));
			payload.put("llavePrivada", normalizeBase64Value(request.getMetadata().get("llavePrivada")));
			Object password = request.getMetadata().get("password");
			if (password == null) {
				password = request.getMetadata().get("passwordKey");
			}
			payload.put("password", password);
		}
		return parseCfdiRelacionadosResponse(exchangeForNode(endpoint, HttpMethod.POST, payload, request.getRfcEmisor(),
				extractMetadataToken(request != null ? request.getMetadata() : null)), request);
	}

	@Override
	public ArchivoCfdiResponse descargarXml(String uuid) {
		throw new FacturoPorTiClientException(TODO_VALIDAR_CON_FACTUROPORTI + ": descargarXml endpoint oficial");
	}

	@Override
	public ArchivoCfdiResponse descargarPdf(String uuid) {
		throw new FacturoPorTiClientException(TODO_VALIDAR_CON_FACTUROPORTI + ": descargarPdf endpoint oficial");
	}

	@Override
	public int consultarCreditosDisponibles(String rfcEmisor) {
		validateActiveProvider();
		String endpoint = resolveEndpoint(CONSULTA_TIMBRES_PATH);
		try {
			ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.GET,
					new HttpEntity<Object>(buildHeaders(rfcEmisor, null)), String.class);
			if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null || response.getBody().trim().isEmpty()) {
				return 0;
			}
			JsonNode root = objectMapper.readTree(response.getBody());
			JsonNode estatus = root.path("estatus");
			String codigo = estatus.path("codigo").asText(null);
			if (!"000".equals(codigo)) {
				return 0;
			}
			return root.path("creditosRestantes").asInt(0);
		} catch (FacturoPorTiClientException e) {
			logger.warn("No fue posible consultar créditos FacturoPorTi para RFC {}: {}", rfcEmisor, e.getMessage());
			return 0;
		} catch (Exception e) {
			logger.error("Error consultando créditos FacturoPorTi para RFC {}", rfcEmisor, e);
			return 0;
		}
	}

	@Override
	public void enviarCorreoCfdi(String uuid, CfdiTimbradoRequest.DatosCorreoDto correo) {
		throw new FacturoPorTiClientException(TODO_VALIDAR_CON_FACTUROPORTI + ": enviarCorreoCfdi endpoint/body oficial");
	}

	@Override
	public ValidacionRfcResponse validarRfc(String rfc) {
		throw new FacturoPorTiClientException(TODO_VALIDAR_CON_FACTUROPORTI + ": validarRfc endpoint/body oficial");
	}

	@Override
	public ValidacionCertificadoResponse validarCertificado(String certificadoBase64) {
		throw new FacturoPorTiClientException(TODO_VALIDAR_CON_FACTUROPORTI + ": validarCertificado endpoint/body oficial");
	}

	@Override
	public ValidacionEfosEdosResponse validarEfosEdos(String rfc) {
		throw new FacturoPorTiClientException(TODO_VALIDAR_CON_FACTUROPORTI + ": validarEfosEdos endpoint/body oficial");
	}

	@Override
	public CatalogoSatResponse consultarCatalogoSat(String catalogo, String filtro) {
		throw new FacturoPorTiClientException(TODO_VALIDAR_CON_FACTUROPORTI + ": consultarCatalogoSat endpoint/body oficial");
	}

	@Override
	public CodigoPostalResponse consultarCodigoPostal(String codigoPostal) {
		throw new FacturoPorTiClientException(TODO_VALIDAR_CON_FACTUROPORTI + ": consultarCodigoPostal endpoint oficial");
	}

	@Override
	public DescargaMasivaXmlResponse solicitarDescargaMasivaXml(String rfcEmisor) {
		throw new FacturoPorTiClientException(TODO_VALIDAR_CON_FACTUROPORTI + ": solicitarDescargaMasivaXml endpoint/body oficial");
	}

	private void validateActiveProvider() {
		if (facturacionProperties.getProveedorActivo() != null
				&& !"facturoporti".equalsIgnoreCase(facturacionProperties.getProveedorActivo())) {
			throw new FacturoPorTiClientException("El proveedor activo no es FacturoPorTi");
		}
	}

	private String resolveEndpoint(String endpointPath) {
		if (endpointPath == null || endpointPath.trim().isEmpty() || TODO_VALIDAR_CON_FACTUROPORTI.equals(endpointPath)) {
			throw new FacturoPorTiClientException("Endpoint no soportado por FacturoPorTi: " + endpointPath);
		}

		if (endpointPath.startsWith("http://") || endpointPath.startsWith("https://")) {
			return endpointPath;
		}

		String baseUrl = resolveBaseUrl();
		String normalizedBase = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
		String normalizedEndpoint = endpointPath.startsWith("/") ? endpointPath : "/" + endpointPath;
		return normalizedBase + normalizedEndpoint;
	}

	private String resolveBaseUrl() {
		String configuredBaseUrl = properties.getBaseUrl();
		if (configuredBaseUrl != null && !configuredBaseUrl.trim().isEmpty()) {
			return configuredBaseUrl.trim();
		}
		return properties.isSandbox() ? SANDBOX_BASE_URL : PRODUCTION_BASE_URL;
	}

	private HttpHeaders buildHeaders() {
		return buildHeaders(null, null);
	}

	private HttpHeaders buildHeaders(String rfcEmisor) {
		return buildHeaders(rfcEmisor, null);
	}

	private HttpHeaders buildHeaders(String rfcEmisor, String tokenOverride) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		String token = resolveBearerToken(rfcEmisor, tokenOverride);
		headers.setBearerAuth(token.trim());

		String apiKey = properties.getAuth() != null ? properties.getAuth().getApiKey() : null;
		if (apiKey != null && !apiKey.trim().isEmpty()) {
			headers.add("X-API-KEY", apiKey.trim());
		}

		return headers;
	}

	private <T> T exchange(String url, HttpMethod method, Object body, String tokenOverride, Class<T> responseType) {
		try {
			HttpEntity<Object> entity = new HttpEntity<Object>(body, buildHeaders(null, tokenOverride));
			ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);
			if (response.getBody() == null || response.getBody().trim().isEmpty()) {
				return responseType.getDeclaredConstructor().newInstance();
			}
			T parsed = objectMapper.readValue(response.getBody(), responseType);
			attachRawResponse(parsed, response.getBody());
			return parsed;
		} catch (HttpStatusCodeException e) {
			logger.error("Error HTTP FacturoPorTi {} {}", method, url, e);
			throw new FacturoPorTiClientException(buildHttpErrorMessage(e), e);
		} catch (FacturoPorTiClientException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error invocando FacturoPorTi {} {}", method, url, e);
			throw new FacturoPorTiClientException("Error invocando FacturoPorTi", e);
		}
	}

	private JsonNode exchangeForNode(String url, HttpMethod method, Object body, String rfcEmisor, String tokenOverride) {
		try {
			HttpEntity<Object> entity = body != null ? new HttpEntity<Object>(body, buildHeaders(rfcEmisor, tokenOverride))
					: new HttpEntity<Object>(buildHeaders(rfcEmisor, tokenOverride));
			ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);
			String responseBody = response.getBody();
			if (responseBody == null || responseBody.trim().isEmpty()) {
				return objectMapper.createObjectNode();
			}
			return objectMapper.readTree(responseBody);
		} catch (HttpStatusCodeException e) {
			logger.error("Error HTTP FacturoPorTi {} {}", method, url, e);
			throw new FacturoPorTiClientException(buildHttpErrorMessage(e), e);
		} catch (FacturoPorTiClientException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error invocando FacturoPorTi {} {}", method, url, e);
			throw new FacturoPorTiClientException("Error invocando FacturoPorTi", e);
		}
	}

	private void attachRawResponse(Object parsed, String body) {
		try {
			Map<String, Object> raw = objectMapper.readValue(body, new TypeReference<Map<String, Object>>() {
			});
			if (parsed instanceof FacturoPorTiTimbradoResponse) {
				((FacturoPorTiTimbradoResponse) parsed).setRaw(raw);
			} else if (parsed instanceof FacturoPorTiCancelacionResponse) {
				((FacturoPorTiCancelacionResponse) parsed).setRaw(raw);
			}
		} catch (Exception ignored) {
			if (parsed instanceof FacturoPorTiTimbradoResponse) {
				((FacturoPorTiTimbradoResponse) parsed).setRaw(mapper.toMap(body));
			} else if (parsed instanceof FacturoPorTiCancelacionResponse) {
				((FacturoPorTiCancelacionResponse) parsed).setRaw(mapper.toMap(body));
			}
		}
	}

	private FacturoPorTiTimbradoResponse exchangeTimbrado(String url, FacturoPorTiTimbradoRequest providerRequest,
			CfdiTimbradoRequest request) {
		try {
			HttpEntity<Object> entity = new HttpEntity<Object>(providerRequest.getPayload(),
					buildHeaders(request.getEmisor() != null ? request.getEmisor().getRfc() : null,
							extractMetadataToken(request != null ? request.getMetadata() : null)));
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			return parseTimbradoResponse(response.getBody(), request);
		} catch (HttpStatusCodeException e) {
			logger.error("Error HTTP FacturoPorTi POST {}", url, e);
			throw new FacturoPorTiClientException(buildHttpErrorMessage(e), e);
		} catch (FacturoPorTiClientException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error invocando timbrado FacturoPorTi {}", url, e);
			throw new FacturoPorTiClientException("Error invocando FacturoPorTi", e);
		}
	}

	private FacturoPorTiTimbradoResponse parseTimbradoResponse(String body, CfdiTimbradoRequest request) {
		try {
			FacturoPorTiTimbradoResponse parsed = new FacturoPorTiTimbradoResponse();
			if (body == null || body.trim().isEmpty()) {
				parsed.setSuccess(Boolean.FALSE);
				parsed.setCodigoError("EMPTY_RESPONSE");
				parsed.setMensajeError("Respuesta vacia de FacturoPorTi");
				return parsed;
			}

			JsonNode root = objectMapper.readTree(body);
			JsonNode estatus = root.path("estatus");
			JsonNode respuesta = root.path("cfdiTimbrado").path("respuesta");
			if (respuesta.isMissingNode()) {
				respuesta = root.path("respuesta");
			}

			String codigo = estatus.path("codigo").asText(null);
			String descripcion = estatus.path("descripcion").asText(null);
			String informacionTecnica = estatus.path("informacionTecnica").asText(null);

			parsed.setSuccess(Boolean.valueOf("000".equals(codigo)));
			parsed.setCodigoError("000".equals(codigo) ? null : codigo);
			parsed.setMensajeError(buildProviderMessage(descripcion, informacionTecnica));
			parsed.setUuid(readText(respuesta, "uuid"));
			parsed.setSerie(request.getSerie());
			parsed.setFolio(request.getFolio());
			parsed.setXmlBase64(readText(respuesta, "cfdixml"));
			parsed.setPdfBase64(readText(respuesta, "pdf"));
			parsed.setQrBase64(readText(respuesta, "qr"));
			parsed.setFechaTimbrado(readText(respuesta, "fecha"));
			parsed.setSelloCfd(readText(respuesta, "selloCFD"));
			parsed.setSelloSat(readText(respuesta, "selloSAT"));
			parsed.setNoCertificadoSat(readText(respuesta, "noCertificado"));
			parsed.setNoCertificadoEmisor(readText(respuesta, "noCertificadoEmisor"));
			parsed.setCadenaOriginal(readText(respuesta, "cadenaOriginal"));
			parsed.setRaw(objectMapper.readValue(body, new TypeReference<Map<String, Object>>() {
			}));
			return parsed;
		} catch (Exception e) {
			throw new FacturoPorTiClientException("No fue posible procesar la respuesta de timbrado FacturoPorTi", e);
		}
	}

	private String readText(JsonNode node, String field) {
		return node != null && node.has(field) && !node.get(field).isNull() ? node.get(field).asText() : null;
	}

	private CancelacionResponse procesarAceptacionRechazo(CancelacionRequest request, String accion) {
		validateActiveProvider();
		String endpoint = resolveEndpoint(ACEPTAR_RECHAZAR_CANCELACION_PATH);
		Map<String, Object> payload = new LinkedHashMap<String, Object>();
		payload.put("rfc", request.getRfcEmisor());
		payload.put("certificado", normalizeBase64Value(request.getMetadata() != null ? request.getMetadata().get("certificado") : null));
		payload.put("llavePrivada", normalizeBase64Value(request.getMetadata() != null ? request.getMetadata().get("llavePrivada") : null));
		Object password = request.getMetadata() != null ? request.getMetadata().get("password") : null;
		if (password == null && request.getMetadata() != null) {
			password = request.getMetadata().get("passwordKey");
		}
		payload.put("password", password);

		Map<String, Object> uuidPayload = new LinkedHashMap<String, Object>();
		uuidPayload.put("uuid", request.getUuid());
		uuidPayload.put("accion", accion);
		List<Map<String, Object>> uuids = new ArrayList<Map<String, Object>>();
		uuids.add(uuidPayload);
		payload.put("uuids", uuids);

		return parseCancelacionDecisionResponse(exchangeForNode(endpoint, HttpMethod.POST, payload, request.getRfcEmisor(),
				extractMetadataToken(request != null ? request.getMetadata() : null)), request);
	}

	private String extractMetadataToken(Map<String, Object> metadata) {
		if (metadata == null || metadata.isEmpty()) {
			return null;
		}
		Object token = metadata.get("token");
		if (token == null) {
			token = metadata.get("bearerToken");
		}
		if (token == null) {
			token = metadata.get("pacToken");
		}
		return token != null ? token.toString() : null;
	}

	private String normalizeBase64Value(Object rawValue) {
		if (rawValue == null) {
			return null;
		}

		String value = rawValue.toString().trim();
		if (value.isEmpty()) {
			return null;
		}

		try {
			Path filePath = Paths.get(value);
			if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
				byte[] fileBytes = Files.readAllBytes(filePath);
				return Base64.getEncoder().encodeToString(fileBytes);
			}
		} catch (Exception ignored) {
			// If it is not a valid path, treat it as inline content.
		}

		String cleaned = value
				.replace("-----BEGIN CERTIFICATE-----", "")
				.replace("-----END CERTIFICATE-----", "")
				.replace("-----BEGIN PRIVATE KEY-----", "")
				.replace("-----END PRIVATE KEY-----", "")
				.replace("-----BEGIN RSA PRIVATE KEY-----", "")
				.replace("-----END RSA PRIVATE KEY-----", "")
				.replaceAll("\\s+", "");

		try {
			byte[] decoded = Base64.getDecoder().decode(cleaned);
			return Base64.getEncoder().encodeToString(decoded);
		} catch (IllegalArgumentException e) {
			return cleaned;
		}
	}

	private StatusCfdiResponse parseStatusResponse(JsonNode root, StatusCfdiRequest request) {
		StatusCfdiResponse response = new StatusCfdiResponse();
		JsonNode estatus = root.path("estatus");
		String codigo = readText(estatus, "codigo");
		response.setSuccess(Boolean.valueOf("000".equals(codigo) || codigo == null));
		response.setUuid(firstNonEmpty(readText(root, "uuid"), request != null ? request.getUuid() : null));
		response.setCodigoError("000".equals(codigo) ? null : codigo);
		response.setMensajeError(buildProviderMessage(readText(estatus, "descripcion"), readText(estatus, "informacionTecnica")));
		response.setEstatusSat(firstNonEmpty(findText(root, "estatusSat", "estado", "estatus"), findText(root, "EstadoSAT", "Estado")));
		response.setEstatusCancelacion(firstNonEmpty(findText(root, "estatusCancelacion", "estadoCancelacion", "cancelacion"), findText(root, "EsCancelable", "EstatusCancelacion")));
		response.setRawResponse(mapper.toMap(root));
		return response;
	}

	private List<SolicitudCancelacionDto> parseSolicitudesPendientes(JsonNode root) {
		List<SolicitudCancelacionDto> solicitudes = new ArrayList<SolicitudCancelacionDto>();
		JsonNode collectionNode = findArrayNode(root, "solicitudes", "respuesta", "peticiones", "uuids", "data");
		if (collectionNode == null || !collectionNode.isArray()) {
			return solicitudes;
		}
		for (JsonNode item : collectionNode) {
			SolicitudCancelacionDto dto = new SolicitudCancelacionDto();
			dto.setUuid(findText(item, "uuid", "UUID", "folioFiscal"));
			dto.setRfcEmisor(findText(item, "rfcEmisor", "RFCEmisor", "rfc"));
			dto.setRfcReceptor(findText(item, "rfcReceptor", "RFCReceptor"));
			dto.setEstado(findText(item, "estado", "estatus", "accion"));
			dto.setFechaSolicitud(findText(item, "fechaSolicitud", "fecha", "FechaSolicitud"));
			solicitudes.add(dto);
		}
		return solicitudes;
	}

	private CfdiRelacionadosResponse parseCfdiRelacionadosResponse(JsonNode root, StatusCfdiRequest request) {
		CfdiRelacionadosResponse response = new CfdiRelacionadosResponse();
		JsonNode estatus = root.path("estatus");
		String codigo = readText(estatus, "codigo");
		response.setSuccess(Boolean.valueOf("000".equals(codigo) || codigo == null));
		response.setUuid(request != null ? request.getUuid() : readText(root, "uuid"));
		response.setCodigoError("000".equals(codigo) ? null : codigo);
		response.setMensajeError(buildProviderMessage(readText(estatus, "descripcion"), readText(estatus, "informacionTecnica")));

		List<String> relacionados = new ArrayList<String>();
		JsonNode relacionadosNode = findArrayNode(root, "relacionados", "cfdisRelacionados", "uuids", "respuesta");
		if (relacionadosNode != null && relacionadosNode.isArray()) {
			for (JsonNode item : relacionadosNode) {
				String value = item.isValueNode() ? item.asText() : findText(item, "uuid", "UUID", "folioFiscal");
				if (value != null && !value.trim().isEmpty()) {
					relacionados.add(value);
				}
			}
		}
		response.setRelacionados(relacionados);
		return response;
	}

	private CancelacionResponse parseCancelacionDecisionResponse(JsonNode root, CancelacionRequest request) {
		CancelacionResponse response = new CancelacionResponse();
		JsonNode estatus = root.path("estatus");
		String codigo = readText(estatus, "codigo");
		response.setSuccess(Boolean.valueOf("000".equals(codigo) || codigo == null));
		response.setUuid(firstNonEmpty(readText(root, "uuid"), request != null ? request.getUuid() : null));
		response.setEstatus(firstNonEmpty(findText(root, "estatus", "estado", "resultado"), response.getSuccess() ? "PROCESADO" : "ERROR"));
		response.setCodigoError("000".equals(codigo) ? null : codigo);
		response.setMensajeError(buildProviderMessage(readText(estatus, "descripcion"), readText(estatus, "informacionTecnica")));
		response.setAcuseBase64(findText(root, "acuse", "acuseBase64", "xmlAcuse"));
		response.setRawResponse(mapper.toMap(root));
		return response;
	}

	private JsonNode findArrayNode(JsonNode root, String... candidates) {
		for (String candidate : candidates) {
			JsonNode node = findNode(root, candidate);
			if (node != null && node.isArray()) {
				return node;
			}
		}
		return null;
	}

	private String findText(JsonNode root, String... candidates) {
		for (String candidate : candidates) {
			JsonNode node = findNode(root, candidate);
			if (node != null && !node.isNull()) {
				String value = node.asText();
				if (value != null && !value.trim().isEmpty() && !"null".equalsIgnoreCase(value)) {
					return value;
				}
			}
		}
		return null;
	}

	private JsonNode findNode(JsonNode root, String candidate) {
		if (root == null || candidate == null) {
			return null;
		}
		if (root.has(candidate)) {
			return root.get(candidate);
		}
		Iterator<Map.Entry<String, JsonNode>> fields = root.fields();
		while (fields.hasNext()) {
			Map.Entry<String, JsonNode> entry = fields.next();
			JsonNode value = entry.getValue();
			if (value != null && value.isContainerNode()) {
				JsonNode found = findNode(value, candidate);
				if (found != null) {
					return found;
				}
			}
		}
		return null;
	}

	private String firstNonEmpty(String... values) {
		for (String value : values) {
			if (value != null && !value.trim().isEmpty()) {
				return value;
			}
		}
		return null;
	}

	private String buildProviderMessage(String descripcion, String informacionTecnica) {
		if (descripcion == null && informacionTecnica == null) {
			return null;
		}
		if (descripcion == null) {
			return informacionTecnica;
		}
		if (informacionTecnica == null || informacionTecnica.trim().isEmpty()) {
			return descripcion;
		}
		return descripcion + " - " + informacionTecnica;
	}

	private String buildHttpErrorMessage(HttpStatusCodeException e) {
		String body = e.getResponseBodyAsString();
		if (body == null || body.trim().isEmpty()) {
			return "Error HTTP FacturoPorTi: " + e.getRawStatusCode();
		}
		return "Error HTTP FacturoPorTi: " + e.getRawStatusCode() + " - " + body;
	}

	private boolean isAuthConfigured() {
		return hasFixedTokenConfigured() || hasUsernamePasswordConfigured();
	}

	private boolean hasFixedTokenConfigured() {
		String configuredToken = properties.getAuth() != null ? properties.getAuth().getToken() : null;
		return configuredToken != null && !configuredToken.trim().isEmpty();
	}

	private boolean hasUsernamePasswordConfigured() {
		String username = properties.getAuth() != null ? properties.getAuth().getUsername() : null;
		String password = properties.getAuth() != null ? properties.getAuth().getPassword() : null;
		return username != null && !username.trim().isEmpty() && password != null && !password.trim().isEmpty();
	}

	private String resolveBearerToken(String rfcEmisor, String tokenOverride) {
		if (tokenOverride != null && !tokenOverride.trim().isEmpty()) {
			return tokenOverride;
		}

		String emitterToken = resolveEmitterToken(rfcEmisor);
		if (emitterToken != null) {
			return emitterToken;
		}

		String authType = properties.getAuth() != null ? properties.getAuth().getType() : null;

		String configuredToken = properties.getAuth() != null ? properties.getAuth().getToken() : null;
		if (configuredToken != null && !configuredToken.trim().isEmpty()) {
			return configuredToken;
		}

		if (authType != null && AUTH_TYPE_TOKEN.equalsIgnoreCase(authType.trim())) {
			throw new FacturoPorTiClientException(
					"No hay token FacturoPorTi configurado. Configure facturoporti.auth.token o proporcione el token en la metadata de la solicitud.");
		}

		if (!hasUsernamePasswordConfigured()) {
			throw new FacturoPorTiClientException(
					"No hay autenticación configurada para FacturoPorTi. Configure facturoporti.auth.token o facturoporti.auth.username/facturoporti.auth.password.");
		}

		if (authType != null && !authType.trim().isEmpty()
				&& !AUTH_TYPE_USERNAME_PASSWORD.equalsIgnoreCase(authType.trim())
				&& !AUTH_TYPE_TOKEN.equalsIgnoreCase(authType.trim())) {
			logger.warn("Tipo de autenticación FacturoPorTi no reconocido: {}. Se intentará flujo username/password.", authType);
		}

		String username = properties.getAuth() != null ? properties.getAuth().getUsername() : null;
		String password = properties.getAuth() != null ? properties.getAuth().getPassword() : null;

		return createToken(username.trim(), password.trim(), rfcEmisor);
	}

	private String resolveEmitterToken(String rfcEmisor) {
		if (rfcEmisor == null || rfcEmisor.trim().isEmpty()) {
			return null;
		}
		TcDatosFactura datosFactura = tcDatosFacturaRepository.findFirstByRfcEmisor(rfcEmisor.trim());
		if (datosFactura == null) {
			return null;
		}
		String possibleToken = datosFactura.getsTokenApi();
		if (possibleToken == null || possibleToken.trim().isEmpty()) {
			return null;
		}
		return possibleToken.trim();
	}

	private String createToken(String username, String password, String rfcEmisor) {
		try {
			String tokenBaseUrl = resolveBaseUrl();

			String url = UriComponentsBuilder.fromHttpUrl(tokenBaseUrl)
					.path(TOKEN_PATH)
					.queryParam("Usuario", username)
					.queryParam("Password", password)
					.queryParam("RFC", rfcEmisor != null ? rfcEmisor : "")
					.build(true)
					.toUriString();

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
			if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null || response.getBody().trim().isEmpty()) {
				throw new FacturoPorTiClientException("No fue posible obtener token de FacturoPorTi.");
			}

			JsonNode root = objectMapper.readTree(response.getBody());
			String codigo = root.path("codigo").asText(null);
			String token = root.path("token").asText(null);
			String mensaje = root.path("mensaje").asText(null);
			if (!"000".equals(codigo) || token == null || token.trim().isEmpty()) {
				throw new FacturoPorTiClientException("Error obteniendo token FacturoPorTi: " + (mensaje != null ? mensaje : "sin detalle"));
			}
			return token;
		} catch (HttpStatusCodeException e) {
			throw new FacturoPorTiClientException(buildHttpErrorMessage(e), e);
		} catch (FacturoPorTiClientException e) {
			throw e;
		} catch (Exception e) {
			throw new FacturoPorTiClientException("Error obteniendo token FacturoPorTi", e);
		}
	}
}