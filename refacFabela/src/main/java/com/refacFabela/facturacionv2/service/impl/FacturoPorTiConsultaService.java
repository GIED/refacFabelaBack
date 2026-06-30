package com.refacFabela.facturacionv2.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.refacFabela.dto.SolicitudCancelacionAccionDto;
import com.refacFabela.facturacionv2.dto.internal.AuditoriaPacDto;
import com.refacFabela.facturacionv2.dto.internal.CancelacionRequest;
import com.refacFabela.facturacionv2.dto.internal.CancelacionResponse;
import com.refacFabela.facturacionv2.dto.internal.CfdiRelacionadosResponse;
import com.refacFabela.facturacionv2.dto.internal.SolicitudCancelacionDto;
import com.refacFabela.facturacionv2.dto.internal.StatusCfdiRequest;
import com.refacFabela.facturacionv2.dto.internal.StatusCfdiResponse;
import com.refacFabela.facturacionv2.exception.FacturacionException;
import com.refacFabela.facturacionv2.service.PacFacturacionClient;
import com.refacFabela.model.TcDatosFactura;
import com.refacFabela.model.TwFacturacion;
import com.refacFabela.model.TwVenta;
import com.refacFabela.repository.FacturaRepository;
import com.refacFabela.repository.TcDatosFacturaRepository;
import com.refacFabela.repository.VentasProductoRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.utils.DateTimeUtil;

@Service
public class FacturoPorTiConsultaService {

	private final VentasRepository ventasRepository;
	private final FacturaRepository facturaRepository;
	private final TcDatosFacturaRepository tcDatosFacturaRepository;
	private final VentasProductoRepository ventasProductoRepository;
	private final PacFacturacionClient pacFacturacionClient;
	private final FacturacionMontoHelper facturacionMontoHelper;
	private final AuditoriaPacService auditoriaPacService;

	public FacturoPorTiConsultaService(VentasRepository ventasRepository,
			FacturaRepository facturaRepository,
			TcDatosFacturaRepository tcDatosFacturaRepository,
			VentasProductoRepository ventasProductoRepository,
			PacFacturacionClient pacFacturacionClient,
			FacturacionMontoHelper facturacionMontoHelper,
			AuditoriaPacService auditoriaPacService) {
		this.ventasRepository = ventasRepository;
		this.facturaRepository = facturaRepository;
		this.tcDatosFacturaRepository = tcDatosFacturaRepository;
		this.ventasProductoRepository = ventasProductoRepository;
		this.pacFacturacionClient = pacFacturacionClient;
		this.facturacionMontoHelper = facturacionMontoHelper;
		this.auditoriaPacService = auditoriaPacService;
	}

	public StatusCfdiResponse consultarEstatusCfdi(Long nIdVenta) {
		VentaFacturadaContext context = loadVentaFacturadaContext(nIdVenta);
		StatusCfdiRequest request = buildStatusRequest(context, false);
		String correlationId = UUID.randomUUID().toString();
		StatusCfdiResponse response = null;
		try {
			response = pacFacturacionClient.consultarEstatusCfdi(request);
			registrarAuditoria("consulta-estatus-cfdi", correlationId, context, request, response, null, null);
			return response;
		} catch (Exception e) {
			registrarAuditoria("consulta-estatus-cfdi", correlationId, context, request, response, "ESTATUS_CFDI_ERROR", e.getMessage());
			throw e;
		}
	}

	public CfdiRelacionadosResponse consultarCfdiRelacionados(Long nIdVenta) {
		VentaFacturadaContext context = loadVentaFacturadaContext(nIdVenta);
		StatusCfdiRequest request = buildStatusRequest(context, true);
		String correlationId = UUID.randomUUID().toString();
		CfdiRelacionadosResponse response = null;
		try {
			response = pacFacturacionClient.consultarCfdiRelacionados(request);
			registrarAuditoria("consulta-cfdi-relacionados", correlationId, context, request, response, null, null);
			return response;
		} catch (Exception e) {
			registrarAuditoria("consulta-cfdi-relacionados", correlationId, context, request, response, "CFDI_RELACIONADOS_ERROR", e.getMessage());
			throw e;
		}
	}

	public List<SolicitudCancelacionDto> consultarSolicitudesPendientesCancelacion(Long nIdDatoFactura) {
		TcDatosFactura datosFactura = loadDatosFactura(nIdDatoFactura);
		String correlationId = UUID.randomUUID().toString();
		List<SolicitudCancelacionDto> response = null;
		try {
			response = pacFacturacionClient.consultarSolicitudesPendientesCancelacion(datosFactura.getsRfcEmisor());
			registrarAuditoria("consulta-solicitudes-pendientes", correlationId, datosFactura, response, null, null);
			return response;
		} catch (Exception e) {
			registrarAuditoria("consulta-solicitudes-pendientes", correlationId, datosFactura, response, "SOLICITUDES_PENDIENTES_ERROR", e.getMessage());
			throw e;
		}
	}

	public CancelacionResponse aceptarSolicitudCancelacion(SolicitudCancelacionAccionDto solicitudCancelacionAccionDto) {
		return procesarSolicitudPendiente(solicitudCancelacionAccionDto, true);
	}

	public CancelacionResponse rechazarSolicitudCancelacion(SolicitudCancelacionAccionDto solicitudCancelacionAccionDto) {
		return procesarSolicitudPendiente(solicitudCancelacionAccionDto, false);
	}

	private CancelacionResponse procesarSolicitudPendiente(SolicitudCancelacionAccionDto dto, boolean aceptar) {
		if (dto == null || dto.getnIdDatoFactura() == null || dto.getUuid() == null || dto.getUuid().trim().isEmpty()) {
			throw new FacturacionException("Se requiere la razón social emisora y el UUID de la solicitud.");
		}
		TcDatosFactura datosFactura = loadDatosFactura(dto.getnIdDatoFactura());
		CancelacionRequest request = buildDecisionRequest(datosFactura, dto.getUuid());
		String correlationId = UUID.randomUUID().toString();
		CancelacionResponse response = null;
		String operacion = aceptar ? "aceptar-cancelacion-pendiente" : "rechazar-cancelacion-pendiente";
		try {
			response = aceptar ? pacFacturacionClient.aceptarCancelacion(request)
					: pacFacturacionClient.rechazarCancelacion(request);
			registrarAuditoria(operacion, correlationId, datosFactura, request, response, null, null);
			return response;
		} catch (Exception e) {
			registrarAuditoria(operacion, correlationId, datosFactura, request, response,
					aceptar ? "ACEPTAR_CANCELACION_ERROR" : "RECHAZAR_CANCELACION_ERROR", e.getMessage());
			throw e;
		}
	}

	private VentaFacturadaContext loadVentaFacturadaContext(Long nIdVenta) {
		TwVenta venta = ventasRepository.findBynId(nIdVenta);
		if (venta == null) {
			throw new FacturacionException("La venta no existe.");
		}
		if (venta.getnIdFacturacion() == null || venta.getnIdFacturacion().longValue() <= 0L) {
			throw new FacturacionException("La venta no cuenta con un CFDI timbrado.");
		}
		TwFacturacion facturacion = facturaRepository.findById(venta.getnIdFacturacion()).orElse(null);
		if (facturacion == null || facturacion.getsUuid() == null || facturacion.getsUuid().trim().isEmpty()) {
			throw new FacturacionException("No existe el UUID del CFDI relacionado.");
		}
		TcDatosFactura datosFactura = tcDatosFacturaRepository.obtenerDatos(facturacion.getnIdDatoFactura());
		if (datosFactura == null) {
			throw new FacturacionException("No existe configuración fiscal para la razón social emisora.");
		}
		return new VentaFacturadaContext(venta, facturacion, datosFactura);
	}

	private TcDatosFactura loadDatosFactura(Long nIdDatoFactura) {
		TcDatosFactura datosFactura = tcDatosFacturaRepository.obtenerDatos(nIdDatoFactura);
		if (datosFactura == null || datosFactura.getsRfcEmisor() == null || datosFactura.getsRfcEmisor().trim().isEmpty()) {
			throw new FacturacionException("No existe configuración fiscal para la razón social emisora.");
		}
		return datosFactura;
	}

	private StatusCfdiRequest buildStatusRequest(VentaFacturadaContext context, boolean includeCredentials) {
		StatusCfdiRequest request = new StatusCfdiRequest();
		request.setRfcEmisor(context.datosFactura.getsRfcEmisor());
		request.setRfcReceptor(context.venta.getTcCliente() != null ? context.venta.getTcCliente().getsRfc() : null);
		request.setUuid(context.facturacion.getsUuid());
		request.setTotal(facturacionMontoHelper.calcularTotal(ventasProductoRepository.findBynIdVenta(context.venta.getnId())));
		request.setSello(resolveSello(context.facturacion));
		if (includeCredentials) {
			request.setMetadata(buildCredentialMetadata(context.datosFactura));
		}
		return request;
	}

	private CancelacionRequest buildDecisionRequest(TcDatosFactura datosFactura, String uuid) {
		CancelacionRequest request = new CancelacionRequest();
		request.setRazonSocialId(datosFactura.getnId());
		request.setRfcEmisor(datosFactura.getsRfcEmisor());
		request.setUuid(uuid);
		request.setMetadata(buildCredentialMetadata(datosFactura));
		return request;
	}

	private Map<String, Object> buildCredentialMetadata(TcDatosFactura datosFactura) {
		Map<String, Object> metadata = new HashMap<String, Object>();
		metadata.put("certificado", datosFactura.getsCertificado());
		metadata.put("llavePrivada", datosFactura.getsRutaKey());
		metadata.put("password", datosFactura.getsPasswordKey());
		metadata.put("passwordKey", datosFactura.getsPasswordKey());
		return metadata;
	}

	private String resolveSello(TwFacturacion facturacion) {
		if (facturacion == null || facturacion.getS_selloCfd() == null) {
			return null;
		}
		String sello = facturacion.getS_selloCfd().trim();
		return sello.length() > 8 ? sello.substring(sello.length() - 8) : sello;
	}

	private void registrarAuditoria(String operacion, String correlationId, VentaFacturadaContext context,
			Object request, Object response, String errorCode, String errorMessage) {
		AuditoriaPacDto auditoria = buildAuditoriaBase(operacion, correlationId, request, response, errorCode, errorMessage);
		auditoria.setUuidRelacionado(context != null && context.facturacion != null ? context.facturacion.getsUuid() : null);
		auditoria.setRazonSocialId(context != null && context.datosFactura != null ? context.datosFactura.getnId() : null);
		auditoria.setVentaId(context != null && context.venta != null ? context.venta.getnId() : null);
		auditoria.setRfcEmisor(context != null && context.datosFactura != null ? context.datosFactura.getsRfcEmisor() : null);
		auditoriaPacService.registrar(auditoria);
	}

	private void registrarAuditoria(String operacion, String correlationId, TcDatosFactura datosFactura,
			Object response, String errorCode, String errorMessage) {
		registrarAuditoria(operacion, correlationId, datosFactura, null, response, errorCode, errorMessage);
	}

	private void registrarAuditoria(String operacion, String correlationId, TcDatosFactura datosFactura,
			Object request, Object response, String errorCode, String errorMessage) {
		AuditoriaPacDto auditoria = buildAuditoriaBase(operacion, correlationId, request, response, errorCode, errorMessage);
		auditoria.setRazonSocialId(datosFactura != null ? datosFactura.getnId() : null);
		auditoria.setRfcEmisor(datosFactura != null ? datosFactura.getsRfcEmisor() : null);
		auditoriaPacService.registrar(auditoria);
	}

	private AuditoriaPacDto buildAuditoriaBase(String operacion, String correlationId, Object request, Object response,
			String errorCode, String errorMessage) {
		AuditoriaPacDto auditoria = new AuditoriaPacDto();
		auditoria.setOperacion(operacion);
		auditoria.setProveedor("facturoporti");
		auditoria.setMetodoHttp(resolveMetodoHttp(operacion));
		auditoria.setEndpoint(resolveEndpoint(operacion));
		auditoria.setRequest(request);
		auditoria.setResponse(response);
		auditoria.setSuccess(Boolean.valueOf(errorCode == null));
		auditoria.setErrorCode(errorCode);
		auditoria.setErrorMessage(errorMessage);
		auditoria.setCorrelationId(correlationId);
		auditoria.setFecha(DateTimeUtil.obtenerHoraExactaDeMexico().toString());
		auditoria.setUsuario(auditoriaPacService.resolveUsuarioActual());
		return auditoria;
	}

	private String resolveMetodoHttp(String operacion) {
		return "consulta-solicitudes-pendientes".equals(operacion) ? "GET" : "POST";
	}

	private String resolveEndpoint(String operacion) {
		if ("consulta-estatus-cfdi".equals(operacion)) {
			return "/validar/estatuscfdi";
		}
		if ("consulta-cfdi-relacionados".equals(operacion)) {
			return "/servicios/consultar/cfdisRelacionados/csd";
		}
		if ("consulta-solicitudes-pendientes".equals(operacion)) {
			return "/servicios/consultar/solicitudespendientescancelacion";
		}
		return "/servicios/cancelar/aceptarrechazar/csd";
	}

	private static class VentaFacturadaContext {

		private final TwVenta venta;
		private final TwFacturacion facturacion;
		private final TcDatosFactura datosFactura;

		private VentaFacturadaContext(TwVenta venta, TwFacturacion facturacion, TcDatosFactura datosFactura) {
			this.venta = venta;
			this.facturacion = facturacion;
			this.datosFactura = datosFactura;
		}
	}
}