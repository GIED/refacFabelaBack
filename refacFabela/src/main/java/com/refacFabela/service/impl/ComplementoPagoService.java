package com.refacFabela.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.refacFabela.dto.AuditoriaPacDto;
import com.refacFabela.dto.ClasificacionFacturacionVenta;
import com.refacFabela.dto.ComplementoPagoHistorialDto;
import com.refacFabela.dto.ComplementoPagoRequest;
import com.refacFabela.dto.TimbradoResponse;
import com.refacFabela.enums.ClasificacionFacturacionPago;
import com.refacFabela.exception.FacturacionException;
import com.refacFabela.service.PacFacturacionMapper;
import com.refacFabela.service.PacFacturacionClient;
import com.refacFabela.model.TcDatosFactura;
import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwFacturacionComplementoPago;
import com.refacFabela.model.TwFacturacion;
import com.refacFabela.model.TwVenta;
import com.refacFabela.repository.AbonoVentaIdRepository;
import com.refacFabela.repository.FacturacionComplementoPagoRepository;
import com.refacFabela.repository.FacturaRepository;
import com.refacFabela.repository.TcDatosFacturaRepository;
import com.refacFabela.repository.TrVentaCobroRepository;
import com.refacFabela.repository.VentasProductoRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.utils.DateTimeUtil;

@Service
public class ComplementoPagoService {

	private static final String USO_CFDI_COMPLEMENTO_PAGO = "CP01";

	private final VentasRepository ventasRepository;
	private final FacturaRepository facturaRepository;
	private final FacturacionComplementoPagoRepository facturacionComplementoPagoRepository;
	private final TrVentaCobroRepository trVentaCobroRepository;
	private final AbonoVentaIdRepository abonoVentaIdRepository;
	private final TcDatosFacturaRepository tcDatosFacturaRepository;
	private final VentasProductoRepository ventasProductoRepository;
	private final PacFacturacionClient pacFacturacionClient;
	private final PacFacturacionMapper pacFacturacionMapper;
	private final AuditoriaPacService auditoriaPacService;
	private final FacturacionMontoHelper facturacionMontoHelper;
	private final ClasificacionFacturacionService clasificacionFacturacionService;

	public ComplementoPagoService(VentasRepository ventasRepository,
			FacturaRepository facturaRepository,
			FacturacionComplementoPagoRepository facturacionComplementoPagoRepository,
			TrVentaCobroRepository trVentaCobroRepository,
			AbonoVentaIdRepository abonoVentaIdRepository,
			TcDatosFacturaRepository tcDatosFacturaRepository,
			VentasProductoRepository ventasProductoRepository,
			PacFacturacionClient pacFacturacionClient,
			PacFacturacionMapper pacFacturacionMapper,
			AuditoriaPacService auditoriaPacService,
			FacturacionMontoHelper facturacionMontoHelper,
			ClasificacionFacturacionService clasificacionFacturacionService) {
		this.ventasRepository = ventasRepository;
		this.facturaRepository = facturaRepository;
		this.facturacionComplementoPagoRepository = facturacionComplementoPagoRepository;
		this.trVentaCobroRepository = trVentaCobroRepository;
		this.abonoVentaIdRepository = abonoVentaIdRepository;
		this.tcDatosFacturaRepository = tcDatosFacturaRepository;
		this.ventasProductoRepository = ventasProductoRepository;
		this.pacFacturacionClient = pacFacturacionClient;
		this.pacFacturacionMapper = pacFacturacionMapper;
		this.auditoriaPacService = auditoriaPacService;
		this.facturacionMontoHelper = facturacionMontoHelper;
		this.clasificacionFacturacionService = clasificacionFacturacionService;
	}

	public ComplementoPagoRequest construirRequestComplemento(Long idVenta, String usoCfdi) {
		return construirRequestComplemento(idVenta, usoCfdi, null, null);
	}

	public ComplementoPagoRequest construirRequestComplemento(Long idVenta, String usoCfdi,
			ClasificacionFacturacionVenta clasificacion, List<PagoFuenteDto> pagosFuente) {
		return construirRequestComplemento(idVenta, usoCfdi, clasificacion, pagosFuente, null);
	}

	private ComplementoPagoRequest construirRequestComplemento(Long idVenta, String usoCfdi,
			ClasificacionFacturacionVenta clasificacion, List<PagoFuenteDto> pagosFuente,
			EstadoComplementoVenta estadoComplementoVenta) {
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
		if (facturaOrigen.getsMetodoPagoFiscal() == null || facturaOrigen.getsFormaPagoFiscal() == null
				|| !"PPD".equalsIgnoreCase(facturaOrigen.getsMetodoPagoFiscal())
				|| !"99".equalsIgnoreCase(facturaOrigen.getsFormaPagoFiscal())) {
			throw new FacturacionException("La factura origen no es elegible para complemento de pago inmediato.");
		}

		TcDatosFactura datosFactura = tcDatosFacturaRepository.obtenerDatos(facturaOrigen.getnIdDatoFactura());
		if (datosFactura == null) {
			throw new FacturacionException("No existe configuración fiscal de la factura origen.");
		}

		if (clasificacion == null) {
			clasificacion = clasificacionFacturacionService.clasificarVenta(venta,
					ventasProductoRepository.findBynIdVenta(idVenta), trVentaCobroRepository.findBynIdVenta(idVenta));
		}

		if (clasificacion == null || clasificacion.getClasificacion() == ClasificacionFacturacionPago.PUE_UNA_FORMA) {
			throw new FacturacionException("La venta no fue clasificada para complemento de pago.");
		}

		List<PagoFuenteDto> pagosPendientes = pagosFuente != null ? pagosFuente : obtenerPagosPendientesVenta(venta);
		if (pagosPendientes == null || pagosPendientes.isEmpty()) {
			throw new FacturacionException("No hay pagos pendientes de relacionar para generar el complemento.");
		}

		EstadoComplementoVenta estadoOperacion = estadoComplementoVenta != null
				? estadoComplementoVenta
				: resolveEstadoComplementoVenta(venta, resolveSaldoInicial(venta));

		Collections.sort(pagosPendientes, new Comparator<PagoFuenteDto>() {
			@Override
			public int compare(PagoFuenteDto left, PagoFuenteDto right) {
				if (left.getFechaPago() == null && right.getFechaPago() == null) {
					return 0;
				}
				if (left.getFechaPago() == null) {
					return -1;
				}
				if (right.getFechaPago() == null) {
					return 1;
				}
				return left.getFechaPago().compareTo(right.getFechaPago());
			}
		});

		ComplementoPagoRequest request = new ComplementoPagoRequest();
		request.setRazonSocialId(datosFactura.getnId());
		request.setUuidFactura(facturaOrigen.getsUuid());
		request.setRfcEmisor(datosFactura.getsRfcEmisor());
		request.setRfcReceptor(venta.getTcCliente().getsRfc());
		request.setMoneda("MXN");
		request.setFechaPago(pagosPendientes.get(pagosPendientes.size() - 1).getFechaPago() != null
				? pagosPendientes.get(pagosPendientes.size() - 1).getFechaPago()
				: DateTimeUtil.obtenerHoraExactaDeMexico());
		request.setPagos(buildPagos(pagosPendientes, facturaOrigen,
				estadoOperacion.getSaldoAnterior(), estadoOperacion.getSiguienteParcialidad()));
		Map<String, Object> metadata = new HashMap<String, Object>();
		metadata.put("certificado", datosFactura.getsCertificado());
		metadata.put("llavePrivada", datosFactura.getsRutaKey());
		metadata.put("passwordKey", datosFactura.getsPasswordKey());
		metadata.put("logo", datosFactura.getsLogo());
		metadata.put("nombreEmisor", datosFactura.getsNombreEmisor());
		metadata.put("regimenFiscal", datosFactura.getsRegimenFiscal());
		metadata.put("codigoPostalEmisor", datosFactura.getsCodigoPostal());
		metadata.put("nombreReceptor", pacFacturacionMapper.normalizeLegalName(venta.getTcCliente().getsRazonSocial()));
		metadata.put("codigoPostalReceptor", venta.getTcCliente().getTcCp() != null ? venta.getTcCliente().getTcCp().getsCp() : null);
		metadata.put("regimenFiscalReceptor", venta.getTcCliente().getTcRegimenFiscal() != null ? venta.getTcCliente().getTcRegimenFiscal().getsCveRegimen() : null);
		metadata.put("usoCfdi", USO_CFDI_COMPLEMENTO_PAGO);
		metadata.put("serie", datosFactura.getsSerie());
		metadata.put("folio", venta.getnId().toString() + "_P" + estadoOperacion.getSiguienteParcialidad());
		request.setMetadata(metadata);
		return request;
	}

	public TimbradoResponse timbrarComplemento(Long idVenta, String usoCfdi) {
		ComplementoPagoRequest request = construirRequestComplemento(idVenta, usoCfdi);
		return timbrarComplemento(idVenta, request, null);
	}

	public TimbradoResponse timbrarComplemento(Long idVenta, ComplementoPagoRequest request, List<PagoFuenteDto> pagosFuente) {
		return timbrarComplemento(idVenta, request, pagosFuente, null);
	}

	private TimbradoResponse timbrarComplemento(Long idVenta, ComplementoPagoRequest request, List<PagoFuenteDto> pagosFuente,
			EstadoComplementoVenta estadoComplementoVenta) {
		String correlationId = UUID.randomUUID().toString();
		TimbradoResponse response = null;
		TwVenta venta = ventasRepository.findBynId(idVenta);
		TwFacturacion facturaOrigen = venta != null && venta.getnIdFacturacion() != null
				? facturaRepository.findById(venta.getnIdFacturacion()).orElse(null)
				: null;
		try {
			response = pacFacturacionClient.timbrarComplementoPago(request);
			if (response == null || !Boolean.TRUE.equals(response.getSuccess()) || response.getUuid() == null
					|| response.getUuid().trim().isEmpty()) {
				throw new FacturacionException(response != null && response.getMensajeError() != null
						? response.getMensajeError()
						: "No fue posible timbrar el complemento de pago.");
			}
			persistirComplementoPago(facturaOrigen, pagosFuente != null ? pagosFuente : obtenerPagosPendientesVenta(venta), response,
					correlationId, null, null, 1, estadoComplementoVenta);
			registrarAuditoria("complemento-pago", correlationId, request, response, null, null);
			return response;
		} catch (Exception e) {
			persistirComplementoPago(facturaOrigen, pagosFuente != null ? pagosFuente : obtenerPagosPendientesVenta(venta), response,
					correlationId, response != null ? response.getCodigoError() : "REP_ERROR", e.getMessage(), 0,
					estadoComplementoVenta);
			registrarAuditoria("complemento-pago", correlationId, request, response, "REP_ERROR", e.getMessage());
			throw e;
		}
	}

	public TimbradoResponse reintentarComplemento(Long nIdComplemento) {
		TwFacturacionComplementoPago complemento = facturacionComplementoPagoRepository.findById(nIdComplemento)
				.orElseThrow(() -> new FacturacionException("No existe el complemento a reintentar."));
		if (complemento.getnEstatus() != null && complemento.getnEstatus().intValue() == 1) {
			throw new FacturacionException("El complemento seleccionado ya fue timbrado exitosamente.");
		}
		if (complemento.getnIdVenta() == null) {
			throw new FacturacionException("El complemento no tiene venta relacionada.");
		}
		if (!facturacionComplementoPagoRepository
				.findActivosByOrigenPago(complemento.getnIdVenta(), complemento.getsOrigenPago(), complemento.getnIdPagoOrigen())
				.isEmpty()) {
			throw new FacturacionException("El pago origen ya cuenta con un complemento exitoso.");
		}

		List<PagoFuenteDto> pagosFuente = Collections.singletonList(buildPagoFuenteFromComplemento(complemento));
		EstadoComplementoVenta estadoComplementoVenta = new EstadoComplementoVenta();
		estadoComplementoVenta.setSaldoAnterior(
				complemento.getnSaldoAnterior() != null ? complemento.getnSaldoAnterior() : BigDecimal.ZERO);
		estadoComplementoVenta.setSiguienteParcialidad(
				complemento.getnParcialidad() != null ? complemento.getnParcialidad().intValue() : 1);

		ComplementoPagoRequest request = construirRequestComplemento(complemento.getnIdVenta(), USO_CFDI_COMPLEMENTO_PAGO,
				null, pagosFuente, estadoComplementoVenta);
		return timbrarComplemento(complemento.getnIdVenta(), request, pagosFuente, estadoComplementoVenta);
	}

	private List<ComplementoPagoRequest.PagoDto> buildPagos(List<PagoFuenteDto> pagosFuente, TwFacturacion facturaOrigen,
			BigDecimal saldoAnteriorInicial, int parcialidadInicial) {
		List<ComplementoPagoRequest.PagoDto> pagos = new ArrayList<ComplementoPagoRequest.PagoDto>();
		BigDecimal saldoAnterior = saldoAnteriorInicial != null ? saldoAnteriorInicial : BigDecimal.ZERO;

		for (int i = 0; i < pagosFuente.size(); i++) {
			PagoFuenteDto pagoFuente = pagosFuente.get(i);
			ComplementoPagoRequest.PagoDto pago = new ComplementoPagoRequest.PagoDto();
			pago.setMonto(pagoFuente.getMonto());
			pago.setFormaPago(pagoFuente.getClaveFormaPagoSat());
			pago.setMoneda("MXN");
			pago.setTipoCambio(BigDecimal.ONE);
			pago.setNumeroOperacion(pagoFuente.getIdPagoOrigen() != null ? pagoFuente.getIdPagoOrigen().toString() : null);

			ComplementoPagoRequest.DocumentoRelacionadoPagoDto documento = new ComplementoPagoRequest.DocumentoRelacionadoPagoDto();
			documento.setIdDocumento(facturaOrigen.getsUuid());
			documento.setMonedaDr("MXN");
			documento.setEquivalenciaDr(BigDecimal.ONE);
			documento.setNumParcialidad(Integer.valueOf(parcialidadInicial + i));
			documento.setImpSaldoAnt(saldoAnterior);
			documento.setImpPagado(pagoFuente.getMonto());
			BigDecimal importePagado = pagoFuente.getMonto() != null ? pagoFuente.getMonto() : BigDecimal.ZERO;
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
		auditoria.setRequest(pacFacturacionMapper.toFacturoPorTiComplementoPagoRequest(request).getPayload());
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

	private void persistirComplementoPago(TwFacturacion facturaOrigen, List<PagoFuenteDto> pagosFuente,
			TimbradoResponse response, String correlationId, String codigoError, String errorMessage, int estatus) {
		persistirComplementoPago(facturaOrigen, pagosFuente, response, correlationId, codigoError, errorMessage, estatus,
				null);
	}

	private void persistirComplementoPago(TwFacturacion facturaOrigen, List<PagoFuenteDto> pagosFuente,
			TimbradoResponse response, String correlationId, String codigoError, String errorMessage, int estatus,
			EstadoComplementoVenta estadoComplementoVenta) {
		if (facturaOrigen == null || pagosFuente == null || pagosFuente.isEmpty()) {
			return;
		}

		TwVenta venta = facturaOrigen.getN_idVenta() != null ? ventasRepository.findBynId(facturaOrigen.getN_idVenta()) : null;
		EstadoComplementoVenta estadoOperacion = estadoComplementoVenta != null
				? estadoComplementoVenta
				: resolveEstadoComplementoVenta(venta, venta != null ? resolveSaldoInicial(venta) : BigDecimal.ZERO);
		BigDecimal saldoAnterior = estadoOperacion.getSaldoAnterior();
		int parcialidadInicial = estadoOperacion.getSiguienteParcialidad();
		BigDecimal saldoFinal = saldoAnterior;
		for (int index = 0; index < pagosFuente.size(); index++) {
			PagoFuenteDto pagoFuente = pagosFuente.get(index);
			TwFacturacionComplementoPago complemento = new TwFacturacionComplementoPago();
			complemento.setnIdVenta(facturaOrigen.getN_idVenta());
			complemento.setnIdFacturacion(facturaOrigen.getnId());
			complemento.setsUuidFacturaIngreso(facturaOrigen.getsUuid());
			complemento.setsUuidComplementoPago(response != null ? response.getUuid() : null);
			complemento.setsOrigenPago(pagoFuente.getOrigenPago());
			complemento.setnIdPagoOrigen(pagoFuente.getIdPagoOrigen());
			complemento.setnParcialidad(Integer.valueOf(parcialidadInicial + index));
			complemento.setsFormaPagoSat(pagoFuente.getClaveFormaPagoSat());
			complemento.setsDescripcionFormaPago(pagoFuente.getDescripcionFormaPago());
			complemento.setnMontoPagado(pagoFuente.getMonto());
			complemento.setnSaldoAnterior(saldoAnterior);
			BigDecimal montoPagado = pagoFuente.getMonto() != null ? pagoFuente.getMonto() : BigDecimal.ZERO;
			BigDecimal saldoInsoluto = BigDecimal.ZERO.max(saldoAnterior.subtract(montoPagado));
			complemento.setnSaldoInsoluto(saldoInsoluto);
			complemento.setdFechaPago(pagoFuente.getFechaPago());
			complemento.setsProveedor("facturoporti");
			complemento.setsEstado(response != null && response.getEstatus() != null ? response.getEstatus()
					: estatus == 1 ? "TIMBRADO" : "PENDIENTE_COMPLEMENTO_PAGO");
			complemento.setnEstatus(Integer.valueOf(estatus));
			complemento.setsXmlTimbrado(response != null ? response.getXmlBase64() : null);
			complemento.setsCodigoError(codigoError);
			complemento.setsErrorPac(errorMessage);
			complemento.setsCorrelationId(correlationId);
			complemento.setdFechaRegistro(LocalDateTime.now());
			facturacionComplementoPagoRepository.save(complemento);
			saldoAnterior = saldoInsoluto;
			saldoFinal = saldoInsoluto;
		}

		if (estatus == 1 && response != null && response.getUuid() != null && !response.getUuid().trim().isEmpty()) {
			facturaOrigen.setsUuidComplementoPago(response.getUuid());
		}
		facturaOrigen.setsEstadoComplemento(estatus == 1 && saldoFinal.compareTo(BigDecimal.ZERO) <= 0
				? "FACTURADA_CON_COMPLEMENTO_PAGO"
				: "PENDIENTE_COMPLEMENTO_PAGO");
		facturaOrigen.setsErrorComplemento(errorMessage);
		facturaRepository.save(facturaOrigen);
	}

	private EstadoComplementoVenta resolveEstadoComplementoVenta(TwVenta venta, BigDecimal saldoFactura) {
		EstadoComplementoVenta estadoComplementoVenta = new EstadoComplementoVenta();
		estadoComplementoVenta.setSaldoAnterior(saldoFactura != null ? saldoFactura : BigDecimal.ZERO);
		estadoComplementoVenta.setSiguienteParcialidad(1);

		if (venta == null || venta.getnId() == null) {
			return estadoComplementoVenta;
		}

		List<TwFacturacionComplementoPago> complementosActivos = facturacionComplementoPagoRepository.findActivosByVenta(venta.getnId());
		if (complementosActivos == null || complementosActivos.isEmpty()) {
			return estadoComplementoVenta;
		}

		TwFacturacionComplementoPago ultimoComplemento = complementosActivos.get(complementosActivos.size() - 1);
		estadoComplementoVenta.setSiguienteParcialidad(
				ultimoComplemento.getnParcialidad() != null ? ultimoComplemento.getnParcialidad().intValue() + 1
						: complementosActivos.size() + 1);
		if (ultimoComplemento.getnSaldoInsoluto() != null) {
			estadoComplementoVenta.setSaldoAnterior(ultimoComplemento.getnSaldoInsoluto());
		}
		return estadoComplementoVenta;
	}

	private static class EstadoComplementoVenta {

		private BigDecimal saldoAnterior;
		private int siguienteParcialidad;

		public BigDecimal getSaldoAnterior() {
			return saldoAnterior;
		}

		public void setSaldoAnterior(BigDecimal saldoAnterior) {
			this.saldoAnterior = saldoAnterior;
		}

		public int getSiguienteParcialidad() {
			return siguienteParcialidad;
		}

		public void setSiguienteParcialidad(int siguienteParcialidad) {
			this.siguienteParcialidad = siguienteParcialidad;
		}
	}

	public List<PagoFuenteDto> obtenerPagosPendientesVenta(TwVenta venta) {
		if (venta == null || venta.getnId() == null) {
			return new ArrayList<PagoFuenteDto>();
		}

		List<PagoFuenteDto> pagosPendientes = new ArrayList<PagoFuenteDto>();
		List<TrVentaCobro> cobros = trVentaCobroRepository.findBynIdVenta(venta.getnId());
		for (TrVentaCobro cobro : cobros) {
			if (cobro == null || cobro.getnId() == null) {
				continue;
			}
			if (!facturacionComplementoPagoRepository.findActivosByOrigenPago(venta.getnId(), "TR_VENTA_COBRO", cobro.getnId()).isEmpty()) {
				continue;
			}
			pagoPendienteFromCobro(pagosPendientes, cobro);
		}

		if (Long.valueOf(1L).equals(venta.getnTipoPago())) {
			List<TwAbono> abonos = abonoVentaIdRepository.findBynIdVenta(venta.getnId());
			for (TwAbono abono : abonos) {
				if (abono == null || abono.getnId() == null) {
					continue;
				}
				if (!facturacionComplementoPagoRepository.findActivosByOrigenPago(venta.getnId(), "TW_ABONO", abono.getnId()).isEmpty()) {
					continue;
				}
				pagoPendienteFromAbono(pagosPendientes, abono);
			}
		}

		return pagosPendientes;
	}

	public List<ComplementoPagoHistorialDto> consultarComplementosPago(Long nIdVenta) {
		List<ComplementoPagoHistorialDto> historial = new ArrayList<ComplementoPagoHistorialDto>();
		if (nIdVenta == null) {
			return historial;
		}

		TwVenta venta = ventasRepository.findBynId(nIdVenta);
		if (venta == null || venta.getnIdFacturacion() == null || venta.getnIdFacturacion().longValue() <= 0L) {
			return historial;
		}

		List<TwFacturacionComplementoPago> complementos = facturacionComplementoPagoRepository.findByFacturacion(venta.getnIdFacturacion());
		for (TwFacturacionComplementoPago complemento : complementos) {
			ComplementoPagoHistorialDto dto = new ComplementoPagoHistorialDto();
			dto.setnId(complemento.getnId());
			dto.setnIdVenta(complemento.getnIdVenta());
			dto.setnIdFacturacion(complemento.getnIdFacturacion());
			dto.setUuidFacturaIngreso(complemento.getsUuidFacturaIngreso());
			dto.setUuidComplementoPago(complemento.getsUuidComplementoPago());
			dto.setOrigenPago(complemento.getsOrigenPago());
			dto.setnIdPagoOrigen(complemento.getnIdPagoOrigen());
			dto.setParcialidad(complemento.getnParcialidad());
			dto.setFormaPagoSat(complemento.getsFormaPagoSat());
			dto.setDescripcionFormaPago(complemento.getsDescripcionFormaPago());
			dto.setMontoPagado(complemento.getnMontoPagado());
			dto.setSaldoAnterior(complemento.getnSaldoAnterior());
			dto.setSaldoInsoluto(complemento.getnSaldoInsoluto());
			dto.setFechaPago(complemento.getdFechaPago());
			dto.setProveedor(complemento.getsProveedor());
			dto.setEstado(complemento.getsEstado());
			dto.setEstatus(complemento.getnEstatus());
			dto.setCodigoError(complemento.getsCodigoError());
			dto.setErrorPac(complemento.getsErrorPac());
			dto.setCorrelationId(complemento.getsCorrelationId());
			dto.setFechaRegistro(complemento.getdFechaRegistro());
			historial.add(dto);
		}

		return historial;
	}

	private PagoFuenteDto buildPagoFuenteFromComplemento(TwFacturacionComplementoPago complemento) {
		PagoFuenteDto pagoFuente = new PagoFuenteDto();
		pagoFuente.setOrigenPago(complemento.getsOrigenPago());
		pagoFuente.setIdPagoOrigen(complemento.getnIdPagoOrigen());
		pagoFuente.setMonto(complemento.getnMontoPagado());
		pagoFuente.setFechaPago(complemento.getdFechaPago());
		pagoFuente.setClaveFormaPagoSat(complemento.getsFormaPagoSat());
		pagoFuente.setDescripcionFormaPago(complemento.getsDescripcionFormaPago());
		return pagoFuente;
	}

	private void pagoPendienteFromCobro(List<PagoFuenteDto> pagosPendientes, TrVentaCobro cobro) {
		PagoFuenteDto pagoFuente = new PagoFuenteDto();
		pagoFuente.setOrigenPago("TR_VENTA_COBRO");
		pagoFuente.setIdPagoOrigen(cobro.getnId());
		pagoFuente.setMonto(cobro.getnMonto());
		pagoFuente.setFechaPago(cobro.getdFecha());
		pagoFuente.setClaveFormaPagoSat(cobro.getTcFormapago() != null ? cobro.getTcFormapago().getsClave() : null);
		pagoFuente.setDescripcionFormaPago(cobro.getTcFormapago() != null ? cobro.getTcFormapago().getsDescripcion() : null);
		pagosPendientes.add(pagoFuente);
	}

	private void pagoPendienteFromAbono(List<PagoFuenteDto> pagosPendientes, TwAbono abono) {
		PagoFuenteDto pagoFuente = new PagoFuenteDto();
		pagoFuente.setOrigenPago("TW_ABONO");
		pagoFuente.setIdPagoOrigen(abono.getnId());
		pagoFuente.setMonto(abono.getnAbono());
		pagoFuente.setFechaPago(abono.getdFecha());
		pagoFuente.setClaveFormaPagoSat(abono.getTcFormapago() != null ? abono.getTcFormapago().getsClave() : null);
		pagoFuente.setDescripcionFormaPago(abono.getTcFormapago() != null ? abono.getTcFormapago().getsDescripcion() : null);
		pagosPendientes.add(pagoFuente);
	}

	public static class PagoFuenteDto {

		private String origenPago;
		private Long idPagoOrigen;
		private BigDecimal monto;
		private LocalDateTime fechaPago;
		private String claveFormaPagoSat;
		private String descripcionFormaPago;

		public String getOrigenPago() {
			return origenPago;
		}

		public void setOrigenPago(String origenPago) {
			this.origenPago = origenPago;
		}

		public Long getIdPagoOrigen() {
			return idPagoOrigen;
		}

		public void setIdPagoOrigen(Long idPagoOrigen) {
			this.idPagoOrigen = idPagoOrigen;
		}

		public BigDecimal getMonto() {
			return monto;
		}

		public void setMonto(BigDecimal monto) {
			this.monto = monto;
		}

		public LocalDateTime getFechaPago() {
			return fechaPago;
		}

		public void setFechaPago(LocalDateTime fechaPago) {
			this.fechaPago = fechaPago;
		}

		public String getClaveFormaPagoSat() {
			return claveFormaPagoSat;
		}

		public void setClaveFormaPagoSat(String claveFormaPagoSat) {
			this.claveFormaPagoSat = claveFormaPagoSat;
		}

		public String getDescripcionFormaPago() {
			return descripcionFormaPago;
		}

		public void setDescripcionFormaPago(String descripcionFormaPago) {
			this.descripcionFormaPago = descripcionFormaPago;
		}
	}
}
