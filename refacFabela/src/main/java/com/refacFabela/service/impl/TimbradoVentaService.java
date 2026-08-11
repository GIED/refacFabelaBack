package com.refacFabela.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;

import com.refacFabela.dto.ClasificacionFacturacionVenta;
import com.refacFabela.dto.CfdiTimbradoRequest;
import com.refacFabela.dto.FacturaParcialDto;
import com.refacFabela.dto.ResultadoFacturacionVentaDto;
import com.refacFabela.dto.TimbradoResponse;
import com.refacFabela.dto.AuditoriaPacDto;
import com.refacFabela.enums.ClasificacionFacturacionPago;
import com.refacFabela.exception.FacturacionException;
import com.refacFabela.service.PacFacturacionMapper;
import com.refacFabela.service.PacFacturacionClient;
import com.refacFabela.model.TcDatosFactura;
import com.refacFabela.model.TcFormapago;
import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TwFacturacion;
import com.refacFabela.model.TwPagoAplicacion;
import com.refacFabela.model.TwVenta;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.repository.CatalagoFormaPagoRepository;
import com.refacFabela.repository.FacturaRepository;
import com.refacFabela.repository.TcDatosFacturaRepository;
import com.refacFabela.repository.TrVentaCobroRepository;
import com.refacFabela.repository.TwPagoAplicacionRepository;
import com.refacFabela.repository.VentasProductoRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.service.impl.DatosFacturaStorageResolver;
import com.refacFabela.service.VentasService;
import com.refacFabela.service.impl.CorreoClienteService;
import com.refacFabela.utils.DateTimeUtil;

@Service
public class TimbradoVentaService {

	private static final Logger logger = LogManager.getLogger("errorLogger");

	private static final BigDecimal TASA_IVA = new BigDecimal("0.160000");
	private static final BigDecimal LIMITE_EFECTIVO = new BigDecimal("2000.00");

	private final VentasRepository ventasRepository;
	private final VentasProductoRepository ventasProductoRepository;
	private final TrVentaCobroRepository trVentaCobroRepository;
	private final CatalagoFormaPagoRepository catalagoFormaPagoRepository;
	private final TcDatosFacturaRepository tcDatosFacturaRepository;
	private final FacturaRepository facturaRepository;
	private final TwPagoAplicacionRepository twPagoAplicacionRepository;
	private final VentasService ventasService;
	private final PacFacturacionClient pacFacturacionClient;
	private final PacFacturacionMapper pacFacturacionMapper;
	private final CorreoClienteService correoClienteService;
	private final DatosFacturaStorageResolver datosFacturaStorageResolver;
	private final AuditoriaPacService auditoriaPacService;
	private final FacturacionMontoHelper facturacionMontoHelper;
	private final ClasificacionFacturacionService clasificacionFacturacionService;

	public TimbradoVentaService(VentasRepository ventasRepository,
			VentasProductoRepository ventasProductoRepository,
			TrVentaCobroRepository trVentaCobroRepository,
			CatalagoFormaPagoRepository catalagoFormaPagoRepository,
			TcDatosFacturaRepository tcDatosFacturaRepository,
			FacturaRepository facturaRepository,
			TwPagoAplicacionRepository twPagoAplicacionRepository,
			VentasService ventasService,
			PacFacturacionClient pacFacturacionClient,
			PacFacturacionMapper pacFacturacionMapper,
			CorreoClienteService correoClienteService,
			DatosFacturaStorageResolver datosFacturaStorageResolver,
			AuditoriaPacService auditoriaPacService,
			FacturacionMontoHelper facturacionMontoHelper,
			ClasificacionFacturacionService clasificacionFacturacionService) {
		this.ventasRepository = ventasRepository;
		this.ventasProductoRepository = ventasProductoRepository;
		this.trVentaCobroRepository = trVentaCobroRepository;
		this.catalagoFormaPagoRepository = catalagoFormaPagoRepository;
		this.tcDatosFacturaRepository = tcDatosFacturaRepository;
		this.facturaRepository = facturaRepository;
		this.twPagoAplicacionRepository = twPagoAplicacionRepository;
		this.ventasService = ventasService;
		this.pacFacturacionClient = pacFacturacionClient;
		this.pacFacturacionMapper = pacFacturacionMapper;
		this.correoClienteService = correoClienteService;
		this.datosFacturaStorageResolver = datosFacturaStorageResolver;
		this.auditoriaPacService = auditoriaPacService;
		this.facturacionMontoHelper = facturacionMontoHelper;
		this.clasificacionFacturacionService = clasificacionFacturacionService;
	}

	private void validarVentaDelMesActual(TwVenta venta) {
		if (venta == null || venta.getdFechaVenta() == null) {
			throw new FacturacionException("La venta no tiene fecha de registro para validar su facturación.");
		}

		java.time.LocalDateTime fechaActual = DateTimeUtil.obtenerHoraExactaDeMexico();
		java.time.LocalDateTime fechaVenta = venta.getdFechaVenta();
		if (fechaVenta.getYear() != fechaActual.getYear() || fechaVenta.getMonthValue() != fechaActual.getMonthValue()) {
			throw new FacturacionException("Solo se pueden facturar ventas realizadas en el mes actual.");
		}
	}

	public TimbradoResponse timbrarVenta(Long idVenta, String cveCfdi) {
		TwVenta venta = ventasRepository.findBynId(idVenta);
		if (venta == null) {
			throw new FacturacionException("La venta no existe.");
		}
		validarVentaDelMesActual(venta);
		if (venta.getnIdFacturacion() != null && venta.getnIdFacturacion().longValue() > 0L) {
			throw new FacturacionException("La venta ya fue facturada.");
		}

		TcDatosFactura datosFactura = tcDatosFacturaRepository.obtenerDatos(venta.getTcCliente().getnIdDatoFactura());
		if (datosFactura == null) {
			throw new FacturacionException("No existe configuración fiscal para la razón social emisora.");
		}

		List<TwVentasProducto> productos = ventasProductoRepository.findBynIdVenta(idVenta);
		List<TrVentaCobro> cobros = trVentaCobroRepository.findBynIdVenta(idVenta);
		if (productos == null || productos.isEmpty()) {
			throw new FacturacionException("La venta no tiene productos para facturar.");
		}
		if ((cobros == null || cobros.isEmpty()) && !Long.valueOf(1L).equals(venta.getnTipoPago())) {
			throw new FacturacionException("La venta no tiene cobros registrados para facturar.");
		}

		ClasificacionFacturacionVenta clasificacion = clasificacionFacturacionService.clasificarVenta(venta, productos,
				cobros);

		String nombreReceptorOriginal = venta.getTcCliente() != null ? venta.getTcCliente().getsRazonSocial() : null;
		List<String> nombresReceptorIntentados = pacFacturacionMapper.buildLegalNameCandidates(nombreReceptorOriginal);
		if (nombresReceptorIntentados.isEmpty()) {
			nombresReceptorIntentados = Collections.singletonList(nombreReceptorOriginal);
		}

		CfdiTimbradoRequest request = buildRequest(venta, datosFactura, productos, cobros, clasificacion, cveCfdi,
				nombresReceptorIntentados.get(0));
		String correlationId = java.util.UUID.randomUUID().toString();
		TimbradoResponse response = null;
		int intentoNombreReceptor = 1;
		try {
			for (int index = 0; index < nombresReceptorIntentados.size(); index++) {
				String nombreReceptorIntentado = nombresReceptorIntentados.get(index);
				request.getReceptor().setNombre(nombreReceptorIntentado);
				intentoNombreReceptor = index + 1;
				response = pacFacturacionClient.timbrarCfdi(request);
				if (isTimbradoSuccessful(response)) {
					break;
				}
				if (!shouldRetryReceiverName(response, index, nombresReceptorIntentados.size())) {
					break;
				}
				logger.warn(
						"Reintentando timbrado venta {} por CFDI40145. Nombre receptor original='{}', intento {} enviado='{}'",
						idVenta,
						nombreReceptorOriginal,
						intentoNombreReceptor,
						nombreReceptorIntentado);
			}
			registrarAuditoria(venta, request, response, correlationId, null, null,
					nombreReceptorOriginal, nombresReceptorIntentados, intentoNombreReceptor);
		} catch (Exception e) {
			registrarAuditoria(venta, request, response, correlationId, "TIMBRADO_ERROR", e.getMessage(),
					nombreReceptorOriginal, nombresReceptorIntentados, intentoNombreReceptor);
			logger.error("Payload timbrado rechazado venta {} nombreOriginal='{}' nombreEnviado='{}' payload={}",
					idVenta,
					nombreReceptorOriginal,
					request != null && request.getReceptor() != null ? request.getReceptor().getNombre() : null,
					pacFacturacionMapper.toSanitizedFacturoPorTiTimbradoPayload(request));
			throw e;
		}

		if (!isTimbradoSuccessful(response)) {
			logger.error("Timbrado PAC sin éxito venta {} nombreOriginal='{}' nombreEnviado='{}' payload={} respuesta={}",
					idVenta,
					nombreReceptorOriginal,
					request != null && request.getReceptor() != null ? request.getReceptor().getNombre() : null,
					pacFacturacionMapper.toSanitizedFacturoPorTiTimbradoPayload(request),
					response != null ? response.getRawResponse() : null);
			throw new FacturacionException(buildTimbradoFailureMessage(response, nombreReceptorOriginal,
					request != null && request.getReceptor() != null ? request.getReceptor().getNombre() : null));
		}

		response.setClasificacionFiscal(clasificacion.getClasificacion() != null ? clasificacion.getClasificacion().name() : null);
		response.setMetodoPagoFiscal(clasificacion.getMetodoPagoFiscal());
		response.setFormaPagoFiscal(clasificacion.getFormaPagoFiscal());
		response.setComplementoInmediatoRequerido(Boolean.valueOf(
				clasificacion.getClasificacion() == ClasificacionFacturacionPago.PPD_PAGO_MIXTO_COMPLEMENTO_INMEDIATO));

		persistirFacturacion(venta, datosFactura, response);
		guardarArchivos(datosFactura, idVenta, response);
		enviarCorreoSiAplica(venta, datosFactura);
		return response;
	}

	public ResultadoFacturacionVentaDto timbrarVentaDivididaEfectivo(Long idVenta, String cveCfdi) {
		TwVenta venta = ventasRepository.findBynId(idVenta);
		if (venta == null) {
			throw new FacturacionException("La venta no existe.");
		}
		validarVentaDelMesActual(venta);
		if (venta.getnIdFacturacion() != null && venta.getnIdFacturacion().longValue() > 0L) {
			throw new FacturacionException("La venta ya fue facturada.");
		}
		if (Long.valueOf(1L).equals(venta.getnTipoPago())) {
			throw new FacturacionException("La facturación dividida solo aplica para ventas de contado.");
		}

		TcDatosFactura datosFactura = tcDatosFacturaRepository.obtenerDatos(venta.getTcCliente().getnIdDatoFactura());
		if (datosFactura == null) {
			throw new FacturacionException("No existe configuración fiscal para la razón social emisora.");
		}

		List<TwVentasProducto> productos = ventasProductoRepository.findBynIdVenta(idVenta);
		List<TrVentaCobro> cobros = trVentaCobroRepository.findBynIdVenta(idVenta);
		if (productos == null || productos.isEmpty()) {
			throw new FacturacionException("La venta no tiene productos para facturar.");
		}
		if (cobros == null || cobros.isEmpty()) {
			throw new FacturacionException("La venta no tiene cobros registrados para facturar.");
		}
		if (!isCobroExclusivoEfectivo(cobros)) {
			throw new FacturacionException("La facturación dividida solo aplica cuando todos los cobros son en efectivo (SAT 01).");
		}

		BigDecimal totalVenta = facturacionMontoHelper.calcularTotal(productos);
		if (totalVenta.compareTo(LIMITE_EFECTIVO) <= 0) {
			throw new FacturacionException("La venta no requiere división porque su monto es menor o igual a $2000.00.");
		}

		List<BigDecimal> segmentos = construirSegmentosDivision(totalVenta);

		String nombreReceptorOriginal = venta.getTcCliente() != null ? venta.getTcCliente().getsRazonSocial() : null;
		List<String> nombresReceptorIntentados = pacFacturacionMapper.buildLegalNameCandidates(nombreReceptorOriginal);
		if (nombresReceptorIntentados.isEmpty()) {
			nombresReceptorIntentados = Collections.singletonList(nombreReceptorOriginal);
		}

		TwFacturacion facturacionPrincipal = null;
		List<FacturaParcialDto> facturasParciales = new ArrayList<FacturaParcialDto>();
		for (int index = 0; index < segmentos.size(); index++) {
			BigDecimal montoParcial = segmentos.get(index);

			// Para evitar CFDI40180, cada parcial se calcula con una relación fiscal consistente
			// (importe traslado dentro de límites SAT sobre la base gravable).
			BigDecimal subtotalParcial = montoParcial.divide(BigDecimal.ONE.add(TASA_IVA), 6, RoundingMode.HALF_UP)
					.setScale(2, RoundingMode.HALF_UP);
			BigDecimal ivaParcial = montoParcial.subtract(subtotalParcial).setScale(2, RoundingMode.HALF_UP);

			CfdiTimbradoRequest request = buildRequest(venta, datosFactura, productos, cobros, null, cveCfdi,
					nombresReceptorIntentados.get(0));
			request.setFolio(venta.getnId() + "-P" + (index + 1));
			request.setFormaPago("01");
			request.setMetodoPago("PUE");
			request.setCondicionesDePago("Pago en una sola exhibición");
			request.setSubtotal(subtotalParcial);
			request.setTotal(montoParcial);
			request.setImpuestos(buildImpuestosParcial(ivaParcial));
			request.setConceptos(buildConceptoParcial(venta, productos, subtotalParcial, ivaParcial, index + 1,
					segmentos.size()));

			String correlationId = java.util.UUID.randomUUID().toString();
			TimbradoResponse response = null;
			int intentoNombreReceptor = 1;
			try {
				for (int intento = 0; intento < nombresReceptorIntentados.size(); intento++) {
					String nombreReceptorIntentado = nombresReceptorIntentados.get(intento);
					request.getReceptor().setNombre(nombreReceptorIntentado);
					intentoNombreReceptor = intento + 1;
					response = pacFacturacionClient.timbrarCfdi(request);
					if (isTimbradoSuccessful(response)) {
						break;
					}
					if (!shouldRetryReceiverName(response, intento, nombresReceptorIntentados.size())) {
						break;
					}
				}
				registrarAuditoria(venta, request, response, correlationId, null, null, nombreReceptorOriginal,
						nombresReceptorIntentados, intentoNombreReceptor);
			} catch (Exception e) {
				registrarAuditoria(venta, request, response, correlationId, "TIMBRADO_DIVIDIDO_ERROR", e.getMessage(),
						nombreReceptorOriginal, nombresReceptorIntentados, intentoNombreReceptor);
				throw e;
			}

			if (!isTimbradoSuccessful(response)) {
				throw new FacturacionException(buildTimbradoFailureMessage(response, nombreReceptorOriginal,
						request != null && request.getReceptor() != null ? request.getReceptor().getNombre() : null));
			}

			response.setClasificacionFiscal(ClasificacionFacturacionPago.PUE_UNA_FORMA.name());
			response.setMetodoPagoFiscal("PUE");
			response.setFormaPagoFiscal("01");
			response.setComplementoInmediatoRequerido(Boolean.FALSE);

			TwFacturacion facturacionParcial = persistirFacturacionParcial(venta, datosFactura, response);
			if (facturacionPrincipal == null) {
				facturacionPrincipal = facturacionParcial;
			}
			guardarArchivosParcial(datosFactura, idVenta, index + 1, response);

			FacturaParcialDto parcialDto = new FacturaParcialDto();
			parcialDto.setParcial(Integer.valueOf(index + 1));
			parcialDto.setUuid(response.getUuid());
			parcialDto.setFolio(request.getFolio());
			parcialDto.setEstado(response.getEstatus());
			parcialDto.setMonto(montoParcial);
			parcialDto.setnIdFacturacion(facturacionParcial.getnId());
			facturasParciales.add(parcialDto);
		}

		if (facturacionPrincipal == null) {
			throw new FacturacionException("No se generó ninguna factura parcial para la venta.");
		}

		venta.setnIdFacturacion(facturacionPrincipal.getnId());
		ventasService.updateStatusVenta(venta);
		actualizarAplicacionesCanonicasVenta(venta);
		enviarCorreoSiAplica(venta, datosFactura);

		ResultadoFacturacionVentaDto resultado = new ResultadoFacturacionVentaDto();
		resultado.setSuccess(true);
		resultado.setMensaje("Venta facturada en modo dividido correctamente.");
		resultado.setClasificacionFiscal(ClasificacionFacturacionPago.PUE_UNA_FORMA.name());
		resultado.setMetodoPagoFiscal("PUE");
		resultado.setFormaPagoFiscal("01");
		resultado.setEstadoFacturacion(facturacionPrincipal.getsEstado());
		resultado.setEstadoComplemento("NO_REQUIERE_COMPLEMENTO");
		resultado.setUuidFacturaIngreso(facturacionPrincipal.getsUuid());
		resultado.setTotalFacturasParciales(Integer.valueOf(facturasParciales.size()));
		resultado.setMontoTotalFacturado(totalVenta);
		resultado.setFacturasParciales(facturasParciales);
		return resultado;
	}

	public TimbradoResponse timbrarVentasConsolidadas(List<Long> idsVenta, String cveCfdi) {
		if (idsVenta == null || idsVenta.isEmpty()) {
			throw new FacturacionException("Debes indicar al menos una venta para facturación consolidada.");
		}

		List<TwVenta> ventas = new ArrayList<TwVenta>();
		List<TwVentasProducto> productos = new ArrayList<TwVentasProducto>();
		List<TrVentaCobro> cobros = new ArrayList<TrVentaCobro>();
		Long nIdCliente = null;
		Long nIdDatoFactura = null;
		boolean contieneCredito = false;

		for (Long idVenta : idsVenta) {
			TwVenta venta = ventasRepository.findBynId(idVenta);
			if (venta == null) {
				throw new FacturacionException("La venta " + idVenta + " no existe.");
			}
			validarVentaDelMesActual(venta);
			if (venta.getnIdFacturacion() != null && venta.getnIdFacturacion().longValue() > 0L) {
				throw new FacturacionException("La venta " + idVenta + " ya fue facturada.");
			}
			if (venta.getTcCliente() == null || venta.getTcCliente().getnIdDatoFactura() == null) {
				throw new FacturacionException("La venta " + idVenta + " no tiene razón social fiscal configurada.");
			}
			if (nIdCliente == null) {
				nIdCliente = venta.getnIdCliente();
				nIdDatoFactura = venta.getTcCliente().getnIdDatoFactura();
			} else {
				if (!nIdCliente.equals(venta.getnIdCliente())) {
					throw new FacturacionException("Todas las ventas consolidadas deben pertenecer al mismo cliente.");
				}
				if (!nIdDatoFactura.equals(venta.getTcCliente().getnIdDatoFactura())) {
					throw new FacturacionException("Todas las ventas consolidadas deben usar la misma razón social emisora.");
				}
			}

			List<TwVentasProducto> productosVenta = ventasProductoRepository.findBynIdVenta(idVenta);
			List<TrVentaCobro> cobrosVenta = trVentaCobroRepository.findBynIdVenta(idVenta);
			if (productosVenta == null || productosVenta.isEmpty()) {
				throw new FacturacionException("La venta " + idVenta + " no tiene productos para facturar.");
			}
			if ((cobrosVenta == null || cobrosVenta.isEmpty()) && !Long.valueOf(1L).equals(venta.getnTipoPago())) {
				throw new FacturacionException("La venta " + idVenta + " no tiene cobros registrados para facturar.");
			}

			ventas.add(venta);
			productos.addAll(productosVenta);
			cobros.addAll(cobrosVenta);
			contieneCredito = contieneCredito || Long.valueOf(1L).equals(venta.getnTipoPago());
		}

		TwVenta ventaAncla = ventas.get(0);
		TcDatosFactura datosFactura = tcDatosFacturaRepository.obtenerDatos(nIdDatoFactura);
		if (datosFactura == null) {
			throw new FacturacionException("No existe configuración fiscal para la razón social emisora.");
		}

		TwVenta ventaClasificacion = new TwVenta();
		ventaClasificacion.setnId(ventaAncla.getnId());
		ventaClasificacion.setnTipoPago(contieneCredito ? 1L : ventaAncla.getnTipoPago());
		ventaClasificacion.setTcFormapago(ventaAncla.getTcFormapago());
		ClasificacionFacturacionVenta clasificacion = clasificacionFacturacionService.clasificarVenta(ventaClasificacion,
				productos, cobros);

		String nombreReceptorOriginal = ventaAncla.getTcCliente() != null ? ventaAncla.getTcCliente().getsRazonSocial() : null;
		List<String> nombresReceptorIntentados = pacFacturacionMapper.buildLegalNameCandidates(nombreReceptorOriginal);
		if (nombresReceptorIntentados.isEmpty()) {
			nombresReceptorIntentados = Collections.singletonList(nombreReceptorOriginal);
		}

		CfdiTimbradoRequest request = buildRequest(ventaAncla, datosFactura, productos, cobros, clasificacion, cveCfdi,
				nombresReceptorIntentados.get(0));
		String correlationId = java.util.UUID.randomUUID().toString();
		TimbradoResponse response = null;
		int intentoNombreReceptor = 1;
		try {
			for (int index = 0; index < nombresReceptorIntentados.size(); index++) {
				String nombreReceptorIntentado = nombresReceptorIntentados.get(index);
				request.getReceptor().setNombre(nombreReceptorIntentado);
				intentoNombreReceptor = index + 1;
				response = pacFacturacionClient.timbrarCfdi(request);
				if (isTimbradoSuccessful(response)) {
					break;
				}
				if (!shouldRetryReceiverName(response, index, nombresReceptorIntentados.size())) {
					break;
				}
			}
			registrarAuditoria(ventaAncla, request, response, correlationId, null, null,
					nombreReceptorOriginal, nombresReceptorIntentados, intentoNombreReceptor);
		} catch (Exception e) {
			registrarAuditoria(ventaAncla, request, response, correlationId, "TIMBRADO_ERROR", e.getMessage(),
					nombreReceptorOriginal, nombresReceptorIntentados, intentoNombreReceptor);
			throw e;
		}

		if (!isTimbradoSuccessful(response)) {
			throw new FacturacionException(buildTimbradoFailureMessage(response, nombreReceptorOriginal,
					request != null && request.getReceptor() != null ? request.getReceptor().getNombre() : null));
		}

		response.setClasificacionFiscal(clasificacion.getClasificacion() != null ? clasificacion.getClasificacion().name() : null);
		response.setMetodoPagoFiscal(clasificacion.getMetodoPagoFiscal());
		response.setFormaPagoFiscal(clasificacion.getFormaPagoFiscal());
		response.setComplementoInmediatoRequerido(Boolean.FALSE);

		persistirFacturacionConsolidada(ventas, datosFactura, response);
		guardarArchivos(datosFactura, extractVentaIds(ventas), response);
		enviarCorreoSiAplica(ventaAncla, datosFactura);
		return response;
	}

	private CfdiTimbradoRequest buildRequest(TwVenta venta, TcDatosFactura datosFactura,
			List<TwVentasProducto> productos, List<TrVentaCobro> cobros,
			ClasificacionFacturacionVenta clasificacion, String cveCfdi, String nombreReceptor) {
		CfdiTimbradoRequest request = new CfdiTimbradoRequest();
		request.setSerie(datosFactura.getsSerie());
		request.setFolio(venta.getnId().toString());
		request.setFecha(DateTimeUtil.obtenerHoraExactaDeMexico());
		request.setFormaPago(clasificacion != null ? clasificacion.getFormaPagoFiscal() : null);
		request.setCondicionesDePago("Pago en una sola exhibición");
		request.setSubtotal(facturacionMontoHelper.calcularSubTotal(productos));
		request.setDescuento(BigDecimal.ZERO);
		request.setMoneda("MXN");
		request.setTipoCambio(BigDecimal.ONE);
		request.setTotal(facturacionMontoHelper.calcularTotal(productos));
		request.setTipoDeComprobante("I");
		request.setExportacion("01");
		request.setMetodoPago(clasificacion != null ? clasificacion.getMetodoPagoFiscal() : null);
		request.setLugarExpedicion(resolveCodigoPostalEmisor(datosFactura));
		request.setRazonSocialId(datosFactura.getnId());
		request.setOtrosPagosRecibidos(buildOtrosPagosRecibidos(clasificacion));

		CfdiTimbradoRequest.EmisorDto emisor = new CfdiTimbradoRequest.EmisorDto();
		emisor.setRfc(resolveRfcEmisor(datosFactura));
		emisor.setNombre(resolveNombreEmisor(datosFactura));
		emisor.setRegimenFiscal(resolveRegimenFiscalEmisor(datosFactura));
		emisor.setCodigoPostalExpedicion(resolveCodigoPostalEmisor(datosFactura));
		request.setEmisor(emisor);

		CfdiTimbradoRequest.ReceptorDto receptor = new CfdiTimbradoRequest.ReceptorDto();
		receptor.setRfc(venta.getTcCliente().getsRfc());
		receptor.setNombre(nombreReceptor);
		receptor.setDomicilioFiscalReceptor(venta.getTcCliente().getTcCp() != null ? venta.getTcCliente().getTcCp().getsCp() : null);
		receptor.setRegimenFiscalReceptor(venta.getTcCliente().getTcRegimenFiscal() != null ? venta.getTcCliente().getTcRegimenFiscal().getsCveRegimen() : null);
		receptor.setUsoCfdi(cveCfdi);
		receptor.setEmail(venta.getTcCliente().getsCorreo());
		request.setReceptor(receptor);

		request.setConceptos(buildConceptos(productos));
		request.setImpuestos(buildImpuestos(productos));

		Map<String, Object> metadata = new HashMap<String, Object>();
		metadata.put("certificado", resolveCertificado(datosFactura));
		metadata.put("llavePrivada", resolveLlavePrivada(datosFactura));
		metadata.put("passwordKey", resolvePasswordKey(datosFactura));
		metadata.put("logo", datosFactura.getsLogo());
		request.setMetadata(metadata);
		return request;
	}

	private String resolveRfcEmisor(TcDatosFactura datosFactura) {
		return datosFactura.getsRfcEmisor();
	}

	private String resolveNombreEmisor(TcDatosFactura datosFactura) {
		return datosFactura.getsNombreEmisor();
	}

	private String resolveRegimenFiscalEmisor(TcDatosFactura datosFactura) {
		return datosFactura.getsRegimenFiscal();
	}

	private boolean isTimbradoSuccessful(TimbradoResponse response) {
		return response != null && Boolean.TRUE.equals(response.getSuccess())
				&& response.getUuid() != null && !response.getUuid().trim().isEmpty();
	}

	private boolean shouldRetryReceiverName(TimbradoResponse response, int attemptIndex, int totalAttempts) {
		if (attemptIndex >= totalAttempts - 1 || response == null) {
			return false;
		}
		String mensajeError = response.getMensajeError();
		if (mensajeError == null || mensajeError.trim().isEmpty()) {
			return false;
		}
		String normalized = mensajeError.toUpperCase();
		return normalized.contains("CFDI40145") || normalized.contains("NOMBRE DEL RECEPTOR");
	}

	private String buildTimbradoFailureMessage(TimbradoResponse response, String nombreOriginal, String nombreEnviado) {
		String baseMessage = response != null && response.getMensajeError() != null
				? response.getMensajeError()
				: "FacturoPorTi no devolvió UUID de timbrado.";
		if (baseMessage.contains("CFDI40145") || baseMessage.toUpperCase().contains("NOMBRE DEL RECEPTOR")) {
			return baseMessage + " | nombreReceptorOriginal='" + nombreOriginal + "' | nombreReceptorEnviado='"
					+ nombreEnviado + "'";
		}
		return baseMessage;
	}

	private String resolveCodigoPostalEmisor(TcDatosFactura datosFactura) {
		return datosFactura.getsCodigoPostal();
	}

	private String resolveCertificado(TcDatosFactura datosFactura) {
		return datosFactura.getsCertificado();
	}

	private String resolveLlavePrivada(TcDatosFactura datosFactura) {
		return datosFactura.getsRutaKey();
	}

	private String resolvePasswordKey(TcDatosFactura datosFactura) {
		return datosFactura.getsPasswordKey();
	}

	private List<CfdiTimbradoRequest.ConceptoDto> buildConceptos(List<TwVentasProducto> productos) {
		List<CfdiTimbradoRequest.ConceptoDto> conceptos = new ArrayList<CfdiTimbradoRequest.ConceptoDto>();
		for (TwVentasProducto producto : productos) {
			CfdiTimbradoRequest.ConceptoDto concepto = new CfdiTimbradoRequest.ConceptoDto();
			concepto.setClaveProdServ(producto.getTcProducto().getTcClavesat().getsClavesat());
			concepto.setNoIdentificacion(producto.getTcProducto().getnId() != null ? producto.getTcProducto().getnId().toString() : null);
			concepto.setCantidad(BigDecimal.valueOf(producto.getnCantidad()));
			concepto.setClaveUnidad("H87");
			concepto.setUnidad("PZA");
			concepto.setDescripcion(producto.getTcProducto().getsDescripcion() != null ? producto.getTcProducto().getsDescripcion() : producto.getTcProducto().getsProducto());
			concepto.setValorUnitario(producto.getnPrecioUnitario());
			concepto.setImporte(producto.getnPrecioPartida());
			concepto.setDescuento(BigDecimal.ZERO);
			concepto.setObjetoImp("02");

			CfdiTimbradoRequest.ImpuestoTrasladoDto traslado = new CfdiTimbradoRequest.ImpuestoTrasladoDto();
			traslado.setBase(producto.getnPrecioPartida() != null ? producto.getnPrecioPartida().toPlainString() : "0.00");
			traslado.setImpuesto("002");
			traslado.setTipoFactor("Tasa");
			traslado.setTasaOCuota(TASA_IVA);
			traslado.setImporte(producto.getnIvaPartida());

			List<CfdiTimbradoRequest.ImpuestoTrasladoDto> traslados = new ArrayList<CfdiTimbradoRequest.ImpuestoTrasladoDto>();
			traslados.add(traslado);
			concepto.setTraslados(traslados);
			conceptos.add(concepto);
		}
		return conceptos;
	}

	private CfdiTimbradoRequest.ImpuestosDto buildImpuestos(List<TwVentasProducto> productos) {
		CfdiTimbradoRequest.ImpuestosDto impuestos = new CfdiTimbradoRequest.ImpuestosDto();
		impuestos.setTotalImpuestosTrasladados(facturacionMontoHelper.calcularIvaTotal(productos));
		impuestos.setTotalImpuestosRetenidos(BigDecimal.ZERO);
		return impuestos;
	}

	private void persistirFacturacion(TwVenta venta, TcDatosFactura datosFactura, TimbradoResponse response) {
		TwFacturacion facturacion = new TwFacturacion();
		facturacion.setN_idVenta(venta.getnId());
		facturacion.setnIdDatoFactura(datosFactura.getnId());
		facturacion.setsUuid(response.getUuid());
		facturacion.setsEstado(response.getEstatus() != null ? response.getEstatus() : "Timbrado");
		facturacion.setsClasificacionFiscal(response.getClasificacionFiscal());
		facturacion.setsMetodoPagoFiscal(response.getMetodoPagoFiscal());
		facturacion.setsFormaPagoFiscal(response.getFormaPagoFiscal());
		facturacion.setsEstadoComplemento("PPD".equalsIgnoreCase(response.getMetodoPagoFiscal())
				? "PENDIENTE_COMPLEMENTO_PAGO"
				: "NO_REQUIERE_COMPLEMENTO");
		facturacion.setsUuidComplementoPago(null);
		facturacion.setsErrorComplemento(null);
		facturacion.setS_noCertificadoSat(response.getNoCertificadoSat());
		facturacion.setS_selloCfd(response.getSelloCfd());
		facturacion.setS_selloSat(response.getSelloSat());
		facturacion.setS_cadenaOriginal(response.getCadenaOriginalComplementoSat());
		facturacion.setnEstatus(1);
		facturacion = facturaRepository.save(facturacion);

		venta.setnIdFacturacion(facturacion.getnId());
		ventasService.updateStatusVenta(venta);
		actualizarAplicacionesCanonicasVenta(venta);
	}

	private TwFacturacion persistirFacturacionParcial(TwVenta venta, TcDatosFactura datosFactura,
			TimbradoResponse response) {
		TwFacturacion facturacion = new TwFacturacion();
		facturacion.setN_idVenta(venta.getnId());
		facturacion.setnIdDatoFactura(datosFactura.getnId());
		facturacion.setsUuid(response.getUuid());
		facturacion.setsEstado(response.getEstatus() != null ? response.getEstatus() : "Timbrado");
		facturacion.setsClasificacionFiscal(response.getClasificacionFiscal());
		facturacion.setsMetodoPagoFiscal(response.getMetodoPagoFiscal());
		facturacion.setsFormaPagoFiscal(response.getFormaPagoFiscal());
		facturacion.setsEstadoComplemento("NO_REQUIERE_COMPLEMENTO");
		facturacion.setsUuidComplementoPago(null);
		facturacion.setsErrorComplemento(null);
		facturacion.setS_noCertificadoSat(response.getNoCertificadoSat());
		facturacion.setS_selloCfd(response.getSelloCfd());
		facturacion.setS_selloSat(response.getSelloSat());
		facturacion.setS_cadenaOriginal(response.getCadenaOriginalComplementoSat());
		facturacion.setnEstatus(1);
		return facturaRepository.save(facturacion);
	}

	private void persistirFacturacionConsolidada(List<TwVenta> ventas, TcDatosFactura datosFactura, TimbradoResponse response) {
		TwFacturacion facturacion = new TwFacturacion();
		facturacion.setN_idVenta(ventas.get(0).getnId());
		facturacion.setnIdDatoFactura(datosFactura.getnId());
		facturacion.setsUuid(response.getUuid());
		facturacion.setsEstado(response.getEstatus() != null ? response.getEstatus() : "Timbrado");
		facturacion.setsClasificacionFiscal(response.getClasificacionFiscal());
		facturacion.setsMetodoPagoFiscal(response.getMetodoPagoFiscal());
		facturacion.setsFormaPagoFiscal(response.getFormaPagoFiscal());
		facturacion.setsEstadoComplemento("PPD".equalsIgnoreCase(response.getMetodoPagoFiscal())
				? "PENDIENTE_COMPLEMENTO_PAGO"
				: "NO_REQUIERE_COMPLEMENTO");
		facturacion.setsUuidComplementoPago(null);
		facturacion.setsErrorComplemento(null);
		facturacion.setS_noCertificadoSat(response.getNoCertificadoSat());
		facturacion.setS_selloCfd(response.getSelloCfd());
		facturacion.setS_selloSat(response.getSelloSat());
		facturacion.setS_cadenaOriginal(response.getCadenaOriginalComplementoSat());
		facturacion.setnEstatus(1);
		facturacion = facturaRepository.save(facturacion);

		for (TwVenta venta : ventas) {
			venta.setnIdFacturacion(facturacion.getnId());
			ventasService.updateStatusVenta(venta);
			actualizarAplicacionesCanonicasVenta(venta);
		}
	}

	private void actualizarAplicacionesCanonicasVenta(TwVenta venta) {
		if (venta == null || venta.getnId() == null || venta.getnIdFacturacion() == null
				|| venta.getnIdFacturacion().longValue() <= 0L) {
			return;
		}

		List<TwPagoAplicacion> aplicaciones = twPagoAplicacionRepository.findActivasByVenta(venta.getnId());
		for (TwPagoAplicacion aplicacion : aplicaciones) {
			if (aplicacion == null) {
				continue;
			}
			if (aplicacion.getnIdFacturacion() != null && aplicacion.getnIdFacturacion().longValue() > 0L) {
				continue;
			}
			aplicacion.setnIdFacturacion(venta.getnIdFacturacion());
			twPagoAplicacionRepository.save(aplicacion);
		}
	}

	private void guardarArchivos(TcDatosFactura datosFactura, Long idVenta, TimbradoResponse response) {
		guardarXml(datosFacturaStorageResolver.resolveRutaXml(datosFactura), idVenta, response.getXmlBase64());
		guardarPdf(datosFacturaStorageResolver.resolveRutaPdf(datosFactura), idVenta, response.getPdfBase64());
	}

	private void guardarArchivosParcial(TcDatosFactura datosFactura, Long idVenta, int parcial,
			TimbradoResponse response) {
		if (parcial <= 1) {
			guardarArchivos(datosFactura, idVenta, response);
			return;
		}
		String token = idVenta + "_P" + parcial;
		guardarXml(datosFacturaStorageResolver.resolveRutaXml(datosFactura), token, response.getXmlBase64());
		guardarPdf(datosFacturaStorageResolver.resolveRutaPdf(datosFactura), token, response.getPdfBase64());
	}

	private void guardarArchivos(TcDatosFactura datosFactura, List<Long> idsVenta, TimbradoResponse response) {
		if (idsVenta == null) {
			return;
		}
		for (Long idVenta : idsVenta) {
			guardarArchivos(datosFactura, idVenta, response);
		}
	}

	private List<Long> extractVentaIds(List<TwVenta> ventas) {
		List<Long> ids = new ArrayList<Long>();
		if (ventas == null) {
			return ids;
		}
		for (TwVenta venta : ventas) {
			if (venta != null && venta.getnId() != null) {
				ids.add(venta.getnId());
			}
		}
		return ids;
	}

	private void enviarCorreoSiAplica(TwVenta venta, TcDatosFactura datosFactura) {
		String rutaRaiz = datosFacturaStorageResolver.resolveRutaRaiz(datosFactura);
		if (venta == null || venta.getnIdCliente() == null || datosFactura == null || rutaRaiz == null) {
			return;
		}
		correoClienteService.enviarCorreoCliente(venta.getnIdCliente(),
				"Factura_" + venta.getnId(),
				"<p>Adjuntamos la factura electr&oacute;nica <strong>No. " + venta.getnId()
						+ "</strong> de su operaci&oacute;n.</p>"
						+ "<p>En este correo encontrar&aacute; los archivos <strong>PDF</strong> y <strong>XML</strong> para su control administrativo y fiscal.</p>"
						+ "<p>Gracias por su preferencia.</p>",
				rutaRaiz,
				venta.getnId().toString(),
				2);
	}

	private void guardarXml(String ruta, Long idVenta, String xmlContent) {
		guardarXml(ruta, String.valueOf(idVenta), xmlContent);
	}

	private void guardarXml(String ruta, String fileToken, String xmlContent) {
		if (ruta == null || ruta.trim().isEmpty() || xmlContent == null || xmlContent.trim().isEmpty()) {
			return;
		}
		Path basePath = Paths.get(ruta + fileToken + ".xml");
		try {
			Path path = resolveUniquePath(basePath);
			if (path.getParent() != null) {
				Files.createDirectories(path.getParent());
			}
			Files.write(path, resolveXmlBytes(xmlContent), StandardOpenOption.CREATE_NEW);
		} catch (FileAlreadyExistsException ex) {
			writeWithFallbackUniquePath(basePath, resolveXmlBytes(xmlContent));
		} catch (Exception e) {
			throw new FacturacionException("No fue posible guardar el XML timbrado.", e);
		}
	}

	private void guardarPdf(String ruta, Long idVenta, String pdfBase64) {
		guardarPdf(ruta, String.valueOf(idVenta), pdfBase64);
	}

	private void guardarPdf(String ruta, String fileToken, String pdfBase64) {
		if (ruta == null || ruta.trim().isEmpty() || pdfBase64 == null || pdfBase64.trim().isEmpty()) {
			return;
		}
		Path basePath = Paths.get(ruta + fileToken + ".pdf");
		try {
			Path path = resolveUniquePath(basePath);
			if (path.getParent() != null) {
				Files.createDirectories(path.getParent());
			}
			Files.write(path, Base64.getDecoder().decode(pdfBase64), StandardOpenOption.CREATE_NEW);
		} catch (FileAlreadyExistsException ex) {
			writeWithFallbackUniquePath(basePath, Base64.getDecoder().decode(pdfBase64));
		} catch (Exception e) {
			throw new FacturacionException("No fue posible guardar el PDF timbrado.", e);
		}
	}

	private void writeWithFallbackUniquePath(Path basePath, byte[] data) {
		try {
			Path fallback = resolveUniquePath(basePath);
			if (fallback.getParent() != null) {
				Files.createDirectories(fallback.getParent());
			}
			Files.write(fallback, data, StandardOpenOption.CREATE_NEW);
		} catch (Exception e) {
			throw new FacturacionException("No fue posible guardar el archivo timbrado sin sobreescritura.", e);
		}
	}

	private Path resolveUniquePath(Path basePath) {
		if (basePath == null || !Files.exists(basePath)) {
			return basePath;
		}

		String fileName = basePath.getFileName().toString();
		int lastDot = fileName.lastIndexOf('.');
		String baseName = lastDot > 0 ? fileName.substring(0, lastDot) : fileName;
		String extension = lastDot > 0 ? fileName.substring(lastDot) : "";

		Path parent = basePath.getParent();
		for (int index = 1; index <= 10000; index++) {
			String candidateName = baseName + "_dup" + index + extension;
			Path candidate = parent != null ? parent.resolve(candidateName) : Paths.get(candidateName);
			if (!Files.exists(candidate)) {
				return candidate;
			}
		}

		String candidateName = baseName + "_dup" + System.currentTimeMillis() + extension;
		return parent != null ? parent.resolve(candidateName) : Paths.get(candidateName);
	}

	private byte[] resolveXmlBytes(String xmlContent) {
		String trimmed = xmlContent.trim();
		if (trimmed.startsWith("<")) {
			return trimmed.getBytes(StandardCharsets.UTF_8);
		}
		try {
			byte[] decoded = Base64.getDecoder().decode(trimmed);
			String candidate = new String(decoded, StandardCharsets.UTF_8).trim();
			if (candidate.startsWith("<")) {
				return decoded;
			}
			return trimmed.getBytes(StandardCharsets.UTF_8);
		} catch (IllegalArgumentException e) {
			return trimmed.getBytes(StandardCharsets.UTF_8);
		}
	}

	private void registrarAuditoria(TwVenta venta, CfdiTimbradoRequest request, TimbradoResponse response,
			String correlationId, String errorCode, String errorMessage, String nombreReceptorOriginal,
			List<String> nombresReceptorIntentados, int intentoNombreReceptor) {
		AuditoriaPacDto auditoria = new AuditoriaPacDto();
		auditoria.setOperacion("timbrado");
		auditoria.setProveedor("facturoporti");
		auditoria.setMetodoHttp("POST");
		auditoria.setEndpoint("/servicios/timbrar/json");
		auditoria.setRequest(pacFacturacionMapper.toSanitizedFacturoPorTiTimbradoPayload(request));
		auditoria.setResponse(response);
		auditoria.setSuccess(response != null ? response.getSuccess() : Boolean.FALSE);
		auditoria.setErrorCode(errorCode != null ? errorCode : (response != null ? response.getCodigoError() : null));
		auditoria.setErrorMessage(errorMessage != null ? errorMessage : (response != null ? response.getMensajeError() : null));
		auditoria.setCorrelationId(correlationId);
		auditoria.setUuidRelacionado(response != null ? response.getUuid() : null);
		auditoria.setRazonSocialId(request != null ? request.getRazonSocialId() : null);
		auditoria.setVentaId(venta != null ? venta.getnId() : null);
		auditoria.setRfcEmisor(request != null && request.getEmisor() != null ? request.getEmisor().getRfc() : null);
		auditoria.setFecha(DateTimeUtil.obtenerHoraExactaDeMexico().toString());
		auditoria.setUsuario(auditoriaPacService.resolveUsuarioActual());
		Map<String, Object> metadata = new HashMap<String, Object>();
		metadata.put("nombreReceptorOriginal", nombreReceptorOriginal);
		metadata.put("nombreReceptorEnviado", request != null && request.getReceptor() != null ? request.getReceptor().getNombre() : null);
		metadata.put("nombresReceptorIntentados", nombresReceptorIntentados);
		metadata.put("intentoNombreReceptor", Integer.valueOf(intentoNombreReceptor));
		auditoria.setMetadata(metadata);
		auditoriaPacService.registrar(auditoria);
	}

	private List<CfdiTimbradoRequest.OtroPagoDto> buildOtrosPagosRecibidos(
			ClasificacionFacturacionVenta clasificacion) {
		if (clasificacion == null
				|| clasificacion.getClasificacion() != ClasificacionFacturacionPago.PPD_PAGO_MIXTO_COMPLEMENTO_INMEDIATO
				|| clasificacion.getMontosPorClaveSat() == null || clasificacion.getMontosPorClaveSat().isEmpty()) {
			return null;
		}

		List<CfdiTimbradoRequest.OtroPagoDto> otrosPagosRecibidos = new ArrayList<CfdiTimbradoRequest.OtroPagoDto>();
		for (Map.Entry<String, BigDecimal> entry : clasificacion.getMontosPorClaveSat().entrySet()) {
			CfdiTimbradoRequest.OtroPagoDto otroPagoDto = new CfdiTimbradoRequest.OtroPagoDto();
			otroPagoDto.setNombre(resolveNombreFormaPagoPorClave(entry.getKey()));
			otroPagoDto.setImporte(entry.getValue());
			otrosPagosRecibidos.add(otroPagoDto);
		}
		return otrosPagosRecibidos;
	}

	private String resolveNombreFormaPagoPorClave(String claveSat) {
		if (claveSat == null || claveSat.trim().isEmpty()) {
			return null;
		}
		List<TcFormapago> formasPago = catalagoFormaPagoRepository.findBynEstatus(1);
		for (TcFormapago formaPago : formasPago) {
			if (formaPago != null && claveSat.equals(formaPago.getsClave())) {
				return formaPago.getsDescripcion();
			}
		}
		return claveSat;
	}

	private String resolveClaveFormaPagoCobro(TrVentaCobro cobro) {
		if (cobro == null) {
			return null;
		}
		if (cobro.getTcFormapago() != null && cobro.getTcFormapago().getsClave() != null
				&& !cobro.getTcFormapago().getsClave().trim().isEmpty()) {
			return cobro.getTcFormapago().getsClave().trim();
		}
		if (cobro.getnIdFormaPago() == null) {
			return null;
		}

		TcFormapago formaPago = catalagoFormaPagoRepository.findById(cobro.getnIdFormaPago()).orElse(null);
		if (formaPago == null || formaPago.getsClave() == null || formaPago.getsClave().trim().isEmpty()) {
			return null;
		}
		return formaPago.getsClave().trim();
	}

	private boolean isCobroExclusivoEfectivo(List<TrVentaCobro> cobros) {
		if (cobros == null || cobros.isEmpty()) {
			return false;
		}
		for (TrVentaCobro cobro : cobros) {
			String claveSat = resolveClaveFormaPagoCobro(cobro);
			if (!"01".equals(claveSat)) {
				return false;
			}
		}
		return true;
	}

	private List<BigDecimal> construirSegmentosDivision(BigDecimal totalVenta) {
		List<BigDecimal> segmentos = new ArrayList<BigDecimal>();
		BigDecimal restante = DateTimeUtil.truncarDosDecimales(totalVenta);
		BigDecimal limiteSuperior = new BigDecimal("1999.00");
		while (restante.compareTo(limiteSuperior) > 0) {
			int aleatorio = ThreadLocalRandom.current().nextInt(1950, 2000);
			BigDecimal parcial = new BigDecimal(aleatorio).setScale(2, RoundingMode.UNNECESSARY);
			segmentos.add(parcial);
			restante = DateTimeUtil.truncarDosDecimales(restante.subtract(parcial));
		}
		if (restante.compareTo(BigDecimal.ZERO) > 0) {
			segmentos.add(DateTimeUtil.truncarDosDecimales(restante));
		}
		return segmentos;
	}

	private CfdiTimbradoRequest.ImpuestosDto buildImpuestosParcial(BigDecimal ivaParcial) {
		CfdiTimbradoRequest.ImpuestosDto impuestos = new CfdiTimbradoRequest.ImpuestosDto();
		impuestos.setTotalImpuestosTrasladados(DateTimeUtil.truncarDosDecimales(ivaParcial));
		impuestos.setTotalImpuestosRetenidos(BigDecimal.ZERO);
		return impuestos;
	}

	private List<CfdiTimbradoRequest.ConceptoDto> buildConceptoParcial(TwVenta venta,
			List<TwVentasProducto> productos, BigDecimal subtotalParcial, BigDecimal ivaParcial,
			int parcial, int totalParciales) {
		TwVentasProducto referencia = productos.get(0);
		CfdiTimbradoRequest.ConceptoDto concepto = new CfdiTimbradoRequest.ConceptoDto();
		concepto.setClaveProdServ(referencia != null && referencia.getTcProducto() != null
				&& referencia.getTcProducto().getTcClavesat() != null
				&& referencia.getTcProducto().getTcClavesat().getsClavesat() != null
				? referencia.getTcProducto().getTcClavesat().getsClavesat()
				: "01010101");
		concepto.setNoIdentificacion(referencia != null && referencia.getTcProducto() != null
				&& referencia.getTcProducto().getnId() != null
				? referencia.getTcProducto().getnId().toString()
				: String.valueOf(venta.getnId()));
		concepto.setCantidad(BigDecimal.ONE);
		concepto.setClaveUnidad("H87");
		concepto.setUnidad("PZA");
		concepto.setDescripcion("Facturación dividida venta #" + venta.getnId() + " parcial " + parcial + "/"
				+ totalParciales);
		concepto.setValorUnitario(DateTimeUtil.truncarDosDecimales(subtotalParcial));
		concepto.setImporte(DateTimeUtil.truncarDosDecimales(subtotalParcial));
		concepto.setDescuento(BigDecimal.ZERO);
		concepto.setObjetoImp("02");

		CfdiTimbradoRequest.ImpuestoTrasladoDto traslado = new CfdiTimbradoRequest.ImpuestoTrasladoDto();
		traslado.setBase(DateTimeUtil.truncarDosDecimales(subtotalParcial).toPlainString());
		traslado.setImpuesto("002");
		traslado.setTipoFactor("Tasa");
		traslado.setTasaOCuota(TASA_IVA);
		traslado.setImporte(DateTimeUtil.truncarDosDecimales(ivaParcial));

		List<CfdiTimbradoRequest.ImpuestoTrasladoDto> traslados = new ArrayList<CfdiTimbradoRequest.ImpuestoTrasladoDto>();
		traslados.add(traslado);
		concepto.setTraslados(traslados);

		List<CfdiTimbradoRequest.ConceptoDto> conceptos = new ArrayList<CfdiTimbradoRequest.ConceptoDto>();
		conceptos.add(concepto);
		return conceptos;
	}
}
