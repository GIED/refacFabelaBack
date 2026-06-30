package com.refacFabela.facturacionv2.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.refacFabela.facturacionv2.dto.internal.AuditoriaPacDto;
import com.refacFabela.facturacionv2.dto.internal.ComplementoPagoRequest;
import com.refacFabela.facturacionv2.dto.internal.TimbradoResponse;
import com.refacFabela.facturacionv2.exception.FacturacionException;
import com.refacFabela.facturacionv2.service.FacturoPorTiMapper;
import com.refacFabela.facturacionv2.service.PacFacturacionClient;
import com.refacFabela.model.TcDatosFactura;
import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TwFacturacion;
import com.refacFabela.model.TwVenta;
import com.refacFabela.repository.FacturaRepository;
import com.refacFabela.repository.TcDatosFacturaRepository;
import com.refacFabela.repository.TrVentaCobroRepository;
import com.refacFabela.repository.VentasProductoRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.utils.DateTimeUtil;

@Service
public class ComplementoPagoService {

	private final VentasRepository ventasRepository;
	private final FacturaRepository facturaRepository;
	private final TrVentaCobroRepository trVentaCobroRepository;
	private final TcDatosFacturaRepository tcDatosFacturaRepository;
	private final VentasProductoRepository ventasProductoRepository;
	private final PacFacturacionClient pacFacturacionClient;
	private final FacturoPorTiMapper facturoPorTiMapper;
	private final AuditoriaPacService auditoriaPacService;
	private final FacturacionMontoHelper facturacionMontoHelper;

	public ComplementoPagoService(VentasRepository ventasRepository,
			FacturaRepository facturaRepository,
			TrVentaCobroRepository trVentaCobroRepository,
			TcDatosFacturaRepository tcDatosFacturaRepository,
			VentasProductoRepository ventasProductoRepository,
			PacFacturacionClient pacFacturacionClient,
			FacturoPorTiMapper facturoPorTiMapper,
			AuditoriaPacService auditoriaPacService,
			FacturacionMontoHelper facturacionMontoHelper) {
		this.ventasRepository = ventasRepository;
		this.facturaRepository = facturaRepository;
		this.trVentaCobroRepository = trVentaCobroRepository;
		this.tcDatosFacturaRepository = tcDatosFacturaRepository;
		this.ventasProductoRepository = ventasProductoRepository;
		this.pacFacturacionClient = pacFacturacionClient;
		this.facturoPorTiMapper = facturoPorTiMapper;
		this.auditoriaPacService = auditoriaPacService;
		this.facturacionMontoHelper = facturacionMontoHelper;
	}

	public ComplementoPagoRequest construirRequestComplemento(Long idVenta, String usoCfdi) {
		TwVenta venta = ventasRepository.findBynId(idVenta);
		if (venta == null) {
			throw new FacturacionException("La venta no existe.");
		}
		if (venta.getnIdFacturacion() == null || venta.getnIdFacturacion().longValue() <= 0L) {
			throw new FacturacionException("La venta no tiene factura origen timbrada.");
		}
		if (venta.getTcFormapago() == null || venta.getTcFormapago().getsClave() == null) {
			throw new FacturacionException("La venta no tiene forma de pago definida.");
		}

		TwFacturacion facturaOrigen = facturaRepository.findById(venta.getnIdFacturacion()).orElse(null);
		if (facturaOrigen == null || facturaOrigen.getsUuid() == null || facturaOrigen.getsUuid().trim().isEmpty()) {
			throw new FacturacionException("No existe UUID de la factura origen.");
		}

		TcDatosFactura datosFactura = tcDatosFacturaRepository.obtenerDatos(facturaOrigen.getnIdDatoFactura());
		if (datosFactura == null) {
			throw new FacturacionException("No existe configuración fiscal de la factura origen.");
		}

		List<TrVentaCobro> cobros = trVentaCobroRepository.findBynIdVenta(idVenta);
		if (cobros == null || cobros.isEmpty()) {
			throw new FacturacionException("No hay cobros en tr_venta_cobro para generar el complemento.");
		}

		cobros.sort(new Comparator<TrVentaCobro>() {
			@Override
			public int compare(TrVentaCobro left, TrVentaCobro right) {
				if (left.getdFecha() == null && right.getdFecha() == null) {
					return 0;
				}
				if (left.getdFecha() == null) {
					return -1;
				}
				if (right.getdFecha() == null) {
					return 1;
				}
				return left.getdFecha().compareTo(right.getdFecha());
			}
		});

		ComplementoPagoRequest request = new ComplementoPagoRequest();
		request.setRazonSocialId(datosFactura.getnId());
		request.setUuidFactura(facturaOrigen.getsUuid());
		request.setRfcEmisor(datosFactura.getsRfcEmisor());
		request.setRfcReceptor(venta.getTcCliente().getsRfc());
		request.setMoneda("MXN");
		request.setFechaPago(cobros.get(cobros.size() - 1).getdFecha() != null ? cobros.get(cobros.size() - 1).getdFecha() : DateTimeUtil.obtenerHoraExactaDeMexico());
		request.setPagos(buildPagos(cobros, facturaOrigen, resolveSaldoInicial(venta)));
		Map<String, Object> metadata = new HashMap<String, Object>();
		metadata.put("certificado", datosFactura.getsCertificado());
		metadata.put("llavePrivada", datosFactura.getsRutaKey());
		metadata.put("passwordKey", datosFactura.getsPasswordKey());
		metadata.put("logo", datosFactura.getsLogo());
		metadata.put("nombreEmisor", datosFactura.getsNombreEmisor());
		metadata.put("regimenFiscal", datosFactura.getsRegimenFiscal());
		metadata.put("codigoPostalEmisor", datosFactura.getsCodigoPostal());
		metadata.put("nombreReceptor", venta.getTcCliente().getsRazonSocial());
		metadata.put("codigoPostalReceptor", venta.getTcCliente().getTcCp() != null ? venta.getTcCliente().getTcCp().getsCp() : null);
		metadata.put("regimenFiscalReceptor", venta.getTcCliente().getTcRegimenFiscal() != null ? venta.getTcCliente().getTcRegimenFiscal().getsCveRegimen() : null);
		metadata.put("usoCfdi", usoCfdi);
		metadata.put("serie", datosFactura.getsSerie());
		metadata.put("folio", venta.getnId().toString() + "_P");
		request.setMetadata(metadata);
		return request;
	}

	public TimbradoResponse timbrarComplemento(Long idVenta, String usoCfdi) {
		ComplementoPagoRequest request = construirRequestComplemento(idVenta, usoCfdi);
		String correlationId = UUID.randomUUID().toString();
		TimbradoResponse response = null;
		try {
			response = pacFacturacionClient.timbrarComplementoPago(request);
			registrarAuditoria("complemento-pago", correlationId, request, response, null, null);
			return response;
		} catch (Exception e) {
			registrarAuditoria("complemento-pago", correlationId, request, response, "REP_ERROR", e.getMessage());
			throw e;
		}
	}

	private List<ComplementoPagoRequest.PagoDto> buildPagos(List<TrVentaCobro> cobros, TwFacturacion facturaOrigen, BigDecimal saldoInicial) {
		List<ComplementoPagoRequest.PagoDto> pagos = new ArrayList<ComplementoPagoRequest.PagoDto>();
		BigDecimal saldoAnterior = saldoInicial;

		for (int i = 0; i < cobros.size(); i++) {
			TrVentaCobro cobro = cobros.get(i);
			ComplementoPagoRequest.PagoDto pago = new ComplementoPagoRequest.PagoDto();
			pago.setMonto(cobro.getnMonto());
			pago.setFormaPago(cobro.getTcFormapago() != null ? cobro.getTcFormapago().getsClave() : null);
			pago.setMoneda("MXN");
			pago.setTipoCambio(BigDecimal.ONE);
			pago.setNumeroOperacion(cobro.getnId() != null ? cobro.getnId().toString() : null);

			ComplementoPagoRequest.DocumentoRelacionadoPagoDto documento = new ComplementoPagoRequest.DocumentoRelacionadoPagoDto();
			documento.setIdDocumento(facturaOrigen.getsUuid());
			documento.setMonedaDr("MXN");
			documento.setEquivalenciaDr(BigDecimal.ONE);
			documento.setNumParcialidad(Integer.valueOf(i + 1));
			documento.setImpSaldoAnt(saldoAnterior);
			documento.setImpPagado(cobro.getnMonto());
			BigDecimal importePagado = cobro.getnMonto() != null ? cobro.getnMonto() : BigDecimal.ZERO;
			documento.setImpSaldoInsoluto(BigDecimal.ZERO.max(saldoAnterior.subtract(importePagado)));

			List<ComplementoPagoRequest.DocumentoRelacionadoPagoDto> documentos = new ArrayList<ComplementoPagoRequest.DocumentoRelacionadoPagoDto>();
			documentos.add(documento);
			pago.setDocumentosRelacionados(documentos);
			pagos.add(pago);
			saldoAnterior = documento.getImpSaldoInsoluto();
		}

		return pagos;
	}

	private BigDecimal resolveSaldoInicial(TwVenta venta) {
		return facturacionMontoHelper.calcularTotal(ventasProductoRepository.findBynIdVenta(venta.getnId()));
	}

	private void registrarAuditoria(String operacion, String correlationId, ComplementoPagoRequest request,
			TimbradoResponse response, String errorCode, String errorMessage) {
		AuditoriaPacDto auditoria = new AuditoriaPacDto();
		auditoria.setOperacion(operacion);
		auditoria.setProveedor("facturoporti");
		auditoria.setMetodoHttp("POST");
		auditoria.setEndpoint("/servicios/timbrar/json");
		auditoria.setRequest(facturoPorTiMapper.toFacturoPorTiComplementoPagoRequest(request).getPayload());
		auditoria.setResponse(response);
		auditoria.setSuccess(response != null ? response.getSuccess() : Boolean.FALSE);
		auditoria.setErrorCode(errorCode != null ? errorCode : (response != null ? response.getCodigoError() : null));
		auditoria.setErrorMessage(errorMessage != null ? errorMessage : (response != null ? response.getMensajeError() : null));
		auditoria.setCorrelationId(correlationId);
		auditoria.setUuidRelacionado(request != null ? request.getUuidFactura() : null);
		auditoria.setRazonSocialId(request != null ? request.getRazonSocialId() : null);
		auditoria.setVentaId(resolveVentaId(request));
		auditoria.setRfcEmisor(request != null ? request.getRfcEmisor() : null);
		auditoria.setFecha(DateTimeUtil.obtenerHoraExactaDeMexico().toString());
		auditoria.setUsuario(auditoriaPacService.resolveUsuarioActual());
		auditoriaPacService.registrar(auditoria);
	}

	private Long resolveVentaId(ComplementoPagoRequest request) {
		if (request == null || request.getMetadata() == null) {
			return null;
		}
		Object folio = request.getMetadata().get("folio");
		if (folio == null) {
			return null;
		}
		String value = String.valueOf(folio);
		int separator = value.indexOf('_');
		String ventaId = separator > 0 ? value.substring(0, separator) : value;
		try {
			return Long.valueOf(ventaId);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}