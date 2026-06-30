package com.refacFabela.facturacionv2.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.refacFabela.facturacionv2.dto.internal.AuditoriaPacDto;
import com.refacFabela.facturacionv2.dto.internal.CancelacionRequest;
import com.refacFabela.facturacionv2.dto.internal.CancelacionResponse;
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
public class FacturoPorTiCancelacionService {

	private final VentasRepository ventasRepository;
	private final FacturaRepository facturaRepository;
	private final TcDatosFacturaRepository tcDatosFacturaRepository;
	private final VentasProductoRepository ventasProductoRepository;
	private final PacFacturacionClient pacFacturacionClient;
	private final AuditoriaPacService auditoriaPacService;
	private final FacturacionMontoHelper facturacionMontoHelper;

	public FacturoPorTiCancelacionService(VentasRepository ventasRepository,
			FacturaRepository facturaRepository,
			TcDatosFacturaRepository tcDatosFacturaRepository,
			VentasProductoRepository ventasProductoRepository,
			PacFacturacionClient pacFacturacionClient,
			AuditoriaPacService auditoriaPacService,
			FacturacionMontoHelper facturacionMontoHelper) {
		this.ventasRepository = ventasRepository;
		this.facturaRepository = facturaRepository;
		this.tcDatosFacturaRepository = tcDatosFacturaRepository;
		this.ventasProductoRepository = ventasProductoRepository;
		this.pacFacturacionClient = pacFacturacionClient;
		this.auditoriaPacService = auditoriaPacService;
		this.facturacionMontoHelper = facturacionMontoHelper;
	}

	public CancelacionResponse cancelarVenta(Long idVenta, String motivo, String folioFiscalSustitucion) {
		TwVenta venta = ventasRepository.findBynId(idVenta);
		if (venta == null) {
			throw new FacturacionException("La venta no existe.");
		}
		if (venta.getnIdFacturacion() == null || venta.getnIdFacturacion().longValue() <= 0L) {
			throw new FacturacionException("La venta no cuenta con una factura timbrada para cancelar.");
		}

		TwFacturacion facturacion = facturaRepository.findById(venta.getnIdFacturacion()).orElse(null);
		if (facturacion == null || facturacion.getsUuid() == null || facturacion.getsUuid().trim().isEmpty()) {
			throw new FacturacionException("No existe el UUID de la factura a cancelar.");
		}

		validateMotivo(motivo, folioFiscalSustitucion);
		TcDatosFactura datosFactura = tcDatosFacturaRepository.obtenerDatos(facturacion.getnIdDatoFactura());
		if (datosFactura == null) {
			throw new FacturacionException("No existe configuración fiscal de la factura a cancelar.");
		}

		CancelacionRequest request = buildRequest(venta, facturacion, datosFactura, motivo, folioFiscalSustitucion);
		String correlationId = UUID.randomUUID().toString();
		CancelacionResponse response = null;
		String errorCode = null;
		String errorMessage = null;
		try {
			response = pacFacturacionClient.cancelarCfdi(request);
			if (!Boolean.TRUE.equals(response.getSuccess())) {
				throw new FacturacionException(response.getMensajeError() != null ? response.getMensajeError() : "FacturoPorTi rechazó la cancelación.");
			}
			if (response.getEstatus() != null) {
				facturacion.setsEstado(response.getEstatus());
				facturaRepository.save(facturacion);
			}
			registrarAuditoria("cancelacion", correlationId, request, response, null, null);
			return response;
		} catch (Exception e) {
			errorCode = response != null ? response.getCodigoError() : "CANCELACION_ERROR";
			errorMessage = e.getMessage();
			registrarAuditoria("cancelacion", correlationId, request, response, errorCode, errorMessage);
			throw e;
		}
	}

	private CancelacionRequest buildRequest(TwVenta venta, TwFacturacion facturacion, TcDatosFactura datosFactura,
			String motivo, String folioFiscalSustitucion) {
		CancelacionRequest request = new CancelacionRequest();
		request.setRazonSocialId(datosFactura.getnId());
		request.setRfcEmisor(datosFactura.getsRfcEmisor());
		request.setRfcReceptor(venta.getTcCliente().getsRfc());
		request.setUuid(facturacion.getsUuid());
		request.setMotivo(motivo);
		request.setFolioFiscalSustitucion(folioFiscalSustitucion);
		request.setSello(resolveSello(facturacion));

		Map<String, Object> metadata = new HashMap<String, Object>();
		metadata.put("certificado", datosFactura.getsCertificado());
		metadata.put("llavePrivada", datosFactura.getsRutaKey());
		metadata.put("password", datosFactura.getsPasswordKey());
		request.setMetadata(metadata);
		request.setTotal(facturacionMontoHelper.calcularTotal(ventasProductoRepository.findBynIdVenta(venta.getnId())));
		return request;
	}

	private void validateMotivo(String motivo, String folioFiscalSustitucion) {
		if (motivo == null || motivo.trim().isEmpty()) {
			throw new FacturacionException("El motivo de cancelación SAT es obligatorio.");
		}
		if (!("01".equals(motivo) || "02".equals(motivo) || "03".equals(motivo) || "04".equals(motivo))) {
			throw new FacturacionException("Motivo de cancelación SAT inválido.");
		}
		if ("01".equals(motivo) && (folioFiscalSustitucion == null || folioFiscalSustitucion.trim().isEmpty())) {
			throw new FacturacionException("El folio fiscal de sustitución es obligatorio para el motivo 01.");
		}
	}

	private void registrarAuditoria(String operacion, String correlationId, CancelacionRequest request,
			CancelacionResponse response, String errorCode, String errorMessage) {
		AuditoriaPacDto auditoria = new AuditoriaPacDto();
		auditoria.setOperacion(operacion);
		auditoria.setProveedor("facturoporti");
		auditoria.setMetodoHttp("POST");
		auditoria.setEndpoint("/servicios/cancelar/csd");
		auditoria.setRequest(request);
		auditoria.setResponse(response);
		auditoria.setSuccess(response != null ? response.getSuccess() : Boolean.FALSE);
		auditoria.setErrorCode(errorCode != null ? errorCode : (response != null ? response.getCodigoError() : null));
		auditoria.setErrorMessage(errorMessage != null ? errorMessage : (response != null ? response.getMensajeError() : null));
		auditoria.setCorrelationId(correlationId);
		auditoria.setUuidRelacionado(request != null ? request.getUuid() : null);
		auditoria.setRazonSocialId(request != null ? request.getRazonSocialId() : null);
		auditoria.setVentaId(resolveVentaId(request));
		auditoria.setRfcEmisor(request != null ? request.getRfcEmisor() : null);
		auditoria.setFecha(DateTimeUtil.obtenerHoraExactaDeMexico().toString());
		auditoria.setUsuario(auditoriaPacService.resolveUsuarioActual());
		auditoriaPacService.registrar(auditoria);
	}

	private String resolveSello(TwFacturacion facturacion) {
		if (facturacion == null || facturacion.getS_selloCfd() == null) {
			return null;
		}
		String sello = facturacion.getS_selloCfd().trim();
		return sello.length() > 8 ? sello.substring(sello.length() - 8) : sello;
	}

	private Long resolveVentaId(CancelacionRequest request) {
		if (request == null || request.getUuid() == null) {
			return null;
		}
		TwFacturacion facturacion = facturaRepository.findAll().stream()
				.filter(item -> request.getUuid().equals(item.getsUuid()))
				.findFirst()
				.orElse(null);
		return facturacion != null ? facturacion.getN_idVenta() : null;
	}
}