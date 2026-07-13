package com.refacFabela.service.impl;

import java.lang.reflect.Array;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.refacFabela.dto.AuditoriaPacDto;
import com.refacFabela.model.TwFacturacionPacAudit;
import com.refacFabela.model.TwFacturacionPacAuditDetalle;
import com.refacFabela.model.UsuarioPrincipal;
import com.refacFabela.repository.FacturacionPacAuditDetalleRepository;
import com.refacFabela.repository.FacturacionPacAuditRepository;

@Service
public class AuditoriaPacService {

	private static final Logger logger = LogManager.getLogger("errorLogger");
	private static final String BLOQUE_REQUEST = "REQUEST";
	private static final String BLOQUE_RESPONSE = "RESPONSE";
	private static final String BLOQUE_METADATA = "METADATA";

	private final FacturacionPacAuditRepository repository;
	private final FacturacionPacAuditDetalleRepository detalleRepository;
	private final ObjectMapper objectMapper;

	public AuditoriaPacService(FacturacionPacAuditRepository repository,
			FacturacionPacAuditDetalleRepository detalleRepository, ObjectMapper objectMapper) {
		this.repository = repository;
		this.detalleRepository = detalleRepository;
		this.objectMapper = objectMapper;
	}

	@Transactional
	public void registrar(AuditoriaPacDto auditoria) {
		if (auditoria == null) {
			return;
		}
		try {
			TwFacturacionPacAudit entity = new TwFacturacionPacAudit();
			entity.setdFechaRegistro(LocalDateTime.now());
			entity.setsFechaOperacion(auditoria.getFecha());
			entity.setsOperacion(limit(auditoria.getOperacion(), 100));
			entity.setsProveedor(limit(auditoria.getProveedor(), 60));
			entity.setsEndpoint(limit(auditoria.getEndpoint(), 255));
			entity.setsMetodoHttp(limit(auditoria.getMetodoHttp(), 10));
			entity.setnHttpStatus(auditoria.getHttpStatus());
			entity.setbSuccess(auditoria.getSuccess());
			entity.setsErrorCode(limit(auditoria.getErrorCode(), 120));
			entity.setsErrorMessage(limit(auditoria.getErrorMessage(), 1000));
			entity.setsCorrelationId(limit(auditoria.getCorrelationId(), 100));
			entity.setsUuidRelacionado(limit(auditoria.getUuidRelacionado(), 80));
			entity.setnRazonSocialId(auditoria.getRazonSocialId());
			entity.setnIdVenta(auditoria.getVentaId());
			entity.setsRfcEmisor(limit(auditoria.getRfcEmisor(), 20));
			entity.setsUsuario(limit(auditoria.getUsuario(), 120));
			entity = repository.save(entity);

			List<TwFacturacionPacAuditDetalle> detalles = new ArrayList<TwFacturacionPacAuditDetalle>();
			appendDetalles(detalles, entity.getnId(), BLOQUE_REQUEST, auditoria.getRequest());
			appendDetalles(detalles, entity.getnId(), BLOQUE_RESPONSE, auditoria.getResponse());
			appendDetalles(detalles, entity.getnId(), BLOQUE_METADATA, auditoria.getMetadata());
			if (!detalles.isEmpty()) {
				detalleRepository.saveAll(detalles);
			}
		} catch (Exception e) {
			logger.error("No fue posible registrar auditoría PAC en base de datos", e);
		}
	}

	private void appendDetalles(List<TwFacturacionPacAuditDetalle> detalles, Long nIdAuditoria, String bloque,
			Object payload) {
		if (payload == null || nIdAuditoria == null || bloque == null || bloque.trim().isEmpty()) {
			return;
		}

		Object normalizado = normalizePayload(payload);
		flattenValue(detalles, nIdAuditoria, limit(bloque.trim(), 20), null, normalizado);
	}

	@SuppressWarnings("unchecked")
	private void flattenValue(List<TwFacturacionPacAuditDetalle> detalles, Long nIdAuditoria, String bloque,
			String ruta, Object value) {
		if (value == null) {
			return;
		}

		if (value instanceof Map) {
			for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) value).entrySet()) {
				String key = entry.getKey() != null ? String.valueOf(entry.getKey()) : "value";
				flattenValue(detalles, nIdAuditoria, bloque, composePath(ruta, key), entry.getValue());
			}
			return;
		}

		if (value instanceof Iterable) {
			int index = 0;
			for (Object item : (Iterable<?>) value) {
				flattenValue(detalles, nIdAuditoria, bloque, composePath(ruta, "[" + index + "]"), item);
				index++;
			}
			return;
		}

		if (value.getClass().isArray()) {
			int length = Array.getLength(value);
			for (int i = 0; i < length; i++) {
				flattenValue(detalles, nIdAuditoria, bloque, composePath(ruta, "[" + i + "]"), Array.get(value, i));
			}
			return;
		}

		createDetalle(detalles, nIdAuditoria, bloque, ruta != null ? ruta : "value", stringifyValue(value));
	}

	private void createDetalle(List<TwFacturacionPacAuditDetalle> detalles, Long nIdAuditoria, String bloque,
			String clave, String valor) {
		TwFacturacionPacAuditDetalle detalle = new TwFacturacionPacAuditDetalle();
		detalle.setnIdAuditoria(nIdAuditoria);
		detalle.setsBloque(limit(bloque, 20));
		detalle.setsClave(limit(clave, 255));
		detalle.setsValor(valor);
		detalles.add(detalle);
	}

	private Object normalizePayload(Object payload) {
		if (payload == null) {
			return null;
		}
		if (payload instanceof String || payload instanceof Number || payload instanceof Boolean || payload instanceof Temporal
				|| payload instanceof Enum || payload instanceof Map || payload instanceof Iterable
				|| payload.getClass().isArray()) {
			return payload;
		}
		try {
			return objectMapper.convertValue(payload, new TypeReference<Map<String, Object>>() {
			});
		} catch (Exception ex) {
			return String.valueOf(payload);
		}
	}

	private String composePath(String parent, String child) {
		String safeChild = child != null ? child.trim() : "";
		if (safeChild.isEmpty()) {
			safeChild = "value";
		}
		if (parent == null || parent.trim().isEmpty()) {
			return safeChild;
		}
		if (safeChild.startsWith("[")) {
			return parent + safeChild;
		}
		return parent + "." + safeChild;
	}

	private String stringifyValue(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof String) {
			return (String) value;
		}
		return String.valueOf(value);
	}

	public String resolveUsuarioActual() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null) {
				return null;
			}
			Object principal = authentication.getPrincipal();
			if (principal instanceof UsuarioPrincipal) {
				return ((UsuarioPrincipal) principal).getUsername();
			}
			if (principal != null) {
				return principal.toString();
			}
			return authentication.getName();
		} catch (Exception e) {
			logger.warn("No fue posible resolver el usuario autenticado para auditoría PAC", e);
			return null;
		}
	}

	private String limit(String value, int maxLength) {
		if (value == null || value.length() <= maxLength) {
			return value;
		}
		return value.substring(0, maxLength);
	}
}
