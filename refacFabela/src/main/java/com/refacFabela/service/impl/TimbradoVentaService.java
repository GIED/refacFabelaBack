package com.refacFabela.service.impl;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;

import com.refacFabela.dto.CfdiTimbradoRequest;
import com.refacFabela.dto.TimbradoResponse;
import com.refacFabela.dto.AuditoriaPacDto;
import com.refacFabela.exception.FacturacionException;
import com.refacFabela.service.PacFacturacionMapper;
import com.refacFabela.service.PacFacturacionClient;
import com.refacFabela.model.TcDatosFactura;
import com.refacFabela.model.TcFormapago;
import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TwFacturacion;
import com.refacFabela.model.TwVenta;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.repository.CatalagoFormaPagoRepository;
import com.refacFabela.repository.FacturaRepository;
import com.refacFabela.repository.TcDatosFacturaRepository;
import com.refacFabela.repository.TrVentaCobroRepository;
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
	private final VentasService ventasService;
	private final PacFacturacionClient pacFacturacionClient;
	private final PacFacturacionMapper pacFacturacionMapper;
	private final CorreoClienteService correoClienteService;
	private final DatosFacturaStorageResolver datosFacturaStorageResolver;
	private final AuditoriaPacService auditoriaPacService;
	private final FacturacionMontoHelper facturacionMontoHelper;

	public TimbradoVentaService(VentasRepository ventasRepository,
			VentasProductoRepository ventasProductoRepository,
			TrVentaCobroRepository trVentaCobroRepository,
			CatalagoFormaPagoRepository catalagoFormaPagoRepository,
			TcDatosFacturaRepository tcDatosFacturaRepository,
			FacturaRepository facturaRepository,
			VentasService ventasService,
			PacFacturacionClient pacFacturacionClient,
			PacFacturacionMapper pacFacturacionMapper,
			CorreoClienteService correoClienteService,
			DatosFacturaStorageResolver datosFacturaStorageResolver,
			AuditoriaPacService auditoriaPacService,
			FacturacionMontoHelper facturacionMontoHelper) {
		this.ventasRepository = ventasRepository;
		this.ventasProductoRepository = ventasProductoRepository;
		this.trVentaCobroRepository = trVentaCobroRepository;
		this.catalagoFormaPagoRepository = catalagoFormaPagoRepository;
		this.tcDatosFacturaRepository = tcDatosFacturaRepository;
		this.facturaRepository = facturaRepository;
		this.ventasService = ventasService;
		this.pacFacturacionClient = pacFacturacionClient;
		this.pacFacturacionMapper = pacFacturacionMapper;
		this.correoClienteService = correoClienteService;
		this.datosFacturaStorageResolver = datosFacturaStorageResolver;
		this.auditoriaPacService = auditoriaPacService;
		this.facturacionMontoHelper = facturacionMontoHelper;
	}

	public TimbradoResponse timbrarVenta(Long idVenta, String cveCfdi) {
		TwVenta venta = ventasRepository.findBynId(idVenta);
		if (venta == null) {
			throw new FacturacionException("La venta no existe.");
		}
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
		if (cobros == null || cobros.isEmpty()) {
			throw new FacturacionException("La venta no tiene cobros registrados para facturar.");
		}

		String nombreReceptorOriginal = venta.getTcCliente() != null ? venta.getTcCliente().getsRazonSocial() : null;
		List<String> nombresReceptorIntentados = pacFacturacionMapper.buildLegalNameCandidates(nombreReceptorOriginal);
		if (nombresReceptorIntentados.isEmpty()) {
			nombresReceptorIntentados = Collections.singletonList(nombreReceptorOriginal);
		}

		CfdiTimbradoRequest request = buildRequest(venta, datosFactura, productos, cobros, cveCfdi,
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

		persistirFacturacion(venta, datosFactura, response);
		guardarArchivos(datosFactura, idVenta, response);
		enviarCorreoSiAplica(venta, datosFactura);
		return response;
	}

	private CfdiTimbradoRequest buildRequest(TwVenta venta, TcDatosFactura datosFactura,
			List<TwVentasProducto> productos, List<TrVentaCobro> cobros, String cveCfdi, String nombreReceptor) {
		CfdiTimbradoRequest request = new CfdiTimbradoRequest();
		request.setSerie(datosFactura.getsSerie());
		request.setFolio(venta.getnId().toString());
		request.setFecha(DateTimeUtil.obtenerHoraExactaDeMexico());
		request.setFormaPago(resolveFormaPago(venta, cobros, productos));
		request.setCondicionesDePago("Pago en una sola exhibición");
		request.setSubtotal(facturacionMontoHelper.calcularSubTotal(productos));
		request.setDescuento(BigDecimal.ZERO);
		request.setMoneda("MXN");
		request.setTipoCambio(BigDecimal.ONE);
		request.setTotal(facturacionMontoHelper.calcularTotal(productos));
		request.setTipoDeComprobante("I");
		request.setExportacion("01");
		request.setMetodoPago(resolveMetodoPago(venta, cobros, productos));
		request.setLugarExpedicion(resolveCodigoPostalEmisor(datosFactura));
		request.setRazonSocialId(datosFactura.getnId());
		request.setOtrosPagosRecibidos(buildOtrosPagosRecibidos(venta, cobros));

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
		facturacion.setS_noCertificadoSat(response.getNoCertificadoSat());
		facturacion.setS_selloCfd(response.getSelloCfd());
		facturacion.setS_selloSat(response.getSelloSat());
		facturacion.setS_cadenaOriginal(response.getCadenaOriginalComplementoSat());
		facturacion.setnEstatus(1);
		facturacion = facturaRepository.save(facturacion);

		venta.setnIdFacturacion(facturacion.getnId());
		ventasService.updateStatusVenta(venta);
	}

	private void guardarArchivos(TcDatosFactura datosFactura, Long idVenta, TimbradoResponse response) {
		guardarXml(datosFacturaStorageResolver.resolveRutaXml(datosFactura), idVenta, response.getXmlBase64());
		guardarPdf(datosFacturaStorageResolver.resolveRutaPdf(datosFactura), idVenta, response.getPdfBase64());
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
		if (ruta == null || ruta.trim().isEmpty() || xmlContent == null || xmlContent.trim().isEmpty()) {
			return;
		}
		Path path = Paths.get(ruta + idVenta + ".xml");
		try {
			if (path.getParent() != null) {
				Files.createDirectories(path.getParent());
			}
			Files.write(path, resolveXmlBytes(xmlContent));
		} catch (Exception e) {
			throw new FacturacionException("No fue posible guardar el XML timbrado.", e);
		}
	}

	private void guardarPdf(String ruta, Long idVenta, String pdfBase64) {
		if (ruta == null || ruta.trim().isEmpty() || pdfBase64 == null || pdfBase64.trim().isEmpty()) {
			return;
		}
		Path path = Paths.get(ruta + idVenta + ".pdf");
		try {
			if (path.getParent() != null) {
				Files.createDirectories(path.getParent());
			}
			Files.write(path, Base64.getDecoder().decode(pdfBase64));
		} catch (Exception e) {
			throw new FacturacionException("No fue posible guardar el PDF timbrado.", e);
		}
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

	private String resolveFormaPago(TwVenta venta, List<TrVentaCobro> cobros, List<TwVentasProducto> productos) {
		if (isVentaContadoUnaExhibicionConMultiplesCobros(venta, cobros)) {
			String formaPagoMayorMonto = resolveFormaPagoMayorMonto(cobros);
			if (formaPagoMayorMonto != null) {
				return formaPagoMayorMonto;
			}
		}

		if (cobros.size() > 1 || (facturacionMontoHelper.calcularTotal(productos).compareTo(LIMITE_EFECTIVO) >= 0
				&& venta.getTcFormapago() != null && Long.valueOf(1L).equals(venta.getTcFormapago().getnId()))) {
			return "99";
		}
		return venta.getTcFormapago() != null ? venta.getTcFormapago().getsClave() : null;
	}

	private String resolveMetodoPago(TwVenta venta, List<TrVentaCobro> cobros, List<TwVentasProducto> productos) {
		if (isVentaContadoUnaExhibicionConMultiplesCobros(venta, cobros)) {
			return "PUE";
		}

		if (cobros.size() > 1 || (facturacionMontoHelper.calcularTotal(productos).compareTo(LIMITE_EFECTIVO) >= 0
				&& venta.getTcFormapago() != null && Long.valueOf(1L).equals(venta.getTcFormapago().getnId()))) {
			return "PPD";
		}
		return "PUE";
	}

	private List<CfdiTimbradoRequest.OtroPagoDto> buildOtrosPagosRecibidos(TwVenta venta, List<TrVentaCobro> cobros) {
		if (!isVentaContadoUnaExhibicionConMultiplesCobros(venta, cobros)) {
			return null;
		}

		Map<String, BigDecimal> montosPorFormaPago = new LinkedHashMap<String, BigDecimal>();
		for (TrVentaCobro cobro : cobros) {
			if (cobro == null) {
				continue;
			}
			String nombreFormaPago = resolveNombreFormaPagoCobro(cobro);
			if (nombreFormaPago == null || nombreFormaPago.trim().isEmpty()) {
				continue;
			}
			BigDecimal monto = cobro.getnMonto() != null ? cobro.getnMonto() : BigDecimal.ZERO;
			BigDecimal acumulado = montosPorFormaPago.get(nombreFormaPago);
			montosPorFormaPago.put(nombreFormaPago, acumulado != null ? acumulado.add(monto) : monto);
		}

		if (montosPorFormaPago.isEmpty()) {
			return null;
		}

		List<CfdiTimbradoRequest.OtroPagoDto> otrosPagosRecibidos = new ArrayList<CfdiTimbradoRequest.OtroPagoDto>();
		for (Map.Entry<String, BigDecimal> entry : montosPorFormaPago.entrySet()) {
			CfdiTimbradoRequest.OtroPagoDto otroPagoDto = new CfdiTimbradoRequest.OtroPagoDto();
			otroPagoDto.setNombre(entry.getKey());
			otroPagoDto.setImporte(entry.getValue());
			otrosPagosRecibidos.add(otroPagoDto);
		}
		return otrosPagosRecibidos;
	}

	private boolean isVentaContadoUnaExhibicionConMultiplesCobros(TwVenta venta, List<TrVentaCobro> cobros) {
		return venta != null && !Long.valueOf(1L).equals(venta.getnTipoPago()) && cobros != null && cobros.size() > 1;
	}

	private String resolveFormaPagoMayorMonto(List<TrVentaCobro> cobros) {
		if (cobros == null || cobros.isEmpty()) {
			return null;
		}

		TrVentaCobro cobroMayor = null;
		BigDecimal montoMayor = null;
		for (TrVentaCobro cobro : cobros) {
			if (cobro == null) {
				continue;
			}
			BigDecimal monto = cobro.getnMonto() != null ? cobro.getnMonto() : BigDecimal.ZERO;
			if (montoMayor == null || monto.compareTo(montoMayor) > 0) {
				montoMayor = monto;
				cobroMayor = cobro;
			}
		}

		return resolveClaveFormaPagoCobro(cobroMayor);
	}

	private String resolveNombreFormaPagoCobro(TrVentaCobro cobro) {
		if (cobro == null) {
			return null;
		}
		if (cobro.getTcFormapago() != null && cobro.getTcFormapago().getsDescripcion() != null
				&& !cobro.getTcFormapago().getsDescripcion().trim().isEmpty()) {
			return cobro.getTcFormapago().getsDescripcion().trim();
		}
		if (cobro.getnIdFormaPago() == null) {
			return null;
		}

		TcFormapago formaPago = catalagoFormaPagoRepository.findById(cobro.getnIdFormaPago()).orElse(null);
		if (formaPago == null || formaPago.getsDescripcion() == null || formaPago.getsDescripcion().trim().isEmpty()) {
			return null;
		}
		return formaPago.getsDescripcion().trim();
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
}
