package com.refacFabela.facturacionv2.service.impl;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.refacFabela.facturacionv2.dto.internal.AuditoriaPacDto;
import com.refacFabela.model.TwFacturacionPacAudit;
import com.refacFabela.model.UsuarioPrincipal;
import com.refacFabela.repository.FacturacionPacAuditRepository;

@Service
public class AuditoriaPacService {

	private static final Logger logger = LogManager.getLogger("errorLogger");

	private final FacturacionPacAuditRepository repository;
	private final ObjectMapper objectMapper;

	public AuditoriaPacService(FacturacionPacAuditRepository repository, ObjectMapper objectMapper) {
		this.repository = repository;
		this.objectMapper = objectMapper;
	}

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
			entity.setsRequestJson(toJson(auditoria.getRequest()));
			entity.setsResponseJson(toJson(auditoria.getResponse()));
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
			entity.setsMetadataJson(toJson(auditoria.getMetadata()));
			repository.save(entity);
		} catch (Exception e) {
			logger.error("No fue posible registrar auditoría PAC en base de datos", e);
		}
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

	private String toJson(Object value) {
		if (value == null) {
			return null;
		}
		try {
			return objectMapper.writeValueAsString(value);
		} catch (Exception e) {
			return String.valueOf(value);
		}
	}

	private String limit(String value, int maxLength) {
		if (value == null || value.length() <= maxLength) {
			return value;
		}
		return value.substring(0, maxLength);
	}
}