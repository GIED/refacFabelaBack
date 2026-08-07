package com.refacFabela.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.refacFabela.dto.FacturaCreditoPendienteDto;
import com.refacFabela.dto.PagoAplicacionAutomaticaRequestDto;
import com.refacFabela.dto.PagoAplicacionManualLineaDto;
import com.refacFabela.dto.PagoAplicacionManualRequestDto;
import com.refacFabela.dto.PagoAplicacionLineaDto;
import com.refacFabela.dto.PagoAplicacionResultadoDto;
import com.refacFabela.dto.PagoComprobanteCorreoResponseDto;
import com.refacFabela.dto.PagoClienteDetalleDto;
import com.refacFabela.dto.PagoClienteRegistroDto;
import com.refacFabela.enums.TipoDoc;
import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TcDatosFactura;
import com.refacFabela.model.TcFormapago;
import com.refacFabela.model.TcUsuario;
import com.refacFabela.model.TvVentaDetalle;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwCaja;
import com.refacFabela.model.TwFacturacion;
import com.refacFabela.model.TwFacturacionComplementoPago;
import com.refacFabela.model.TwPagoAplicacion;
import com.refacFabela.model.TwPagoCliente;
import com.refacFabela.model.TwVenta;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.repository.AbonoVentaIdRepository;
import com.refacFabela.repository.CajaRepository;
import com.refacFabela.repository.CatalagoFormaPagoRepository;
import com.refacFabela.repository.ClientesRepository;
import com.refacFabela.repository.FacturacionComplementoPagoRepository;
import com.refacFabela.repository.FacturaRepository;
import com.refacFabela.repository.TcDatosFacturaRepository;
import com.refacFabela.repository.TvVentaDetalleRepository;
import com.refacFabela.repository.TwPagoAplicacionRepository;
import com.refacFabela.repository.TwPagoClienteRepository;
import com.refacFabela.repository.TwProductosVentaRepository;
import com.refacFabela.repository.UsuariosRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.service.GeneraReporteService;
import com.refacFabela.service.PagoClienteService;
import com.refacFabela.utils.DateTimeUtil;
import com.refacFabela.utils.envioMail;

@Service
public class PagoClienteServiceImpl implements PagoClienteService {

	private static final String ESTATUS_REGISTRADO = "REGISTRADO";
	private static final String ESTATUS_APLICADO_PARCIAL = "APLICADO_PARCIAL";
	private static final String ESTATUS_APLICADO_TOTAL = "APLICADO_TOTAL";
	private static final String MONEDA_DEFAULT = "MXN";
	private static final String ORIGEN_DEFAULT = "PAGO_CLIENTE";
	private static final String ORIGEN_LEGACY_ABONO = "LEGACY_ABONO";

	@Autowired
	private TwPagoClienteRepository twPagoClienteRepository;

	@Autowired
	private TwPagoAplicacionRepository twPagoAplicacionRepository;

	@Autowired
	private ClientesRepository clientesRepository;

	@Autowired
	private TcDatosFacturaRepository tcDatosFacturaRepository;

	@Autowired
	private TvVentaDetalleRepository tvVentaDetalleRepository;

	@Autowired
	private VentasRepository ventasRepository;

	@Autowired
	private TwProductosVentaRepository twProductosVentaRepository;

	@Autowired
	private FacturaRepository facturaRepository;

	@Autowired
	private FacturacionComplementoPagoRepository facturacionComplementoPagoRepository;

	@Autowired
	private AbonoVentaIdRepository abonoVentaIdRepository;

	@Autowired
	private CatalagoFormaPagoRepository catalagoFormaPagoRepository;

	@Autowired
	private CajaRepository cajaRepository;

	@Autowired
	private UsuariosRepository usuariosRepository;

	@Autowired
	private GeneraReporteService generaReporteService;

	@Autowired
	private CorreoClienteService correoClienteService;

	@Transactional
	@Override
	public PagoClienteDetalleDto registrarPago(PagoClienteRegistroDto registroDto) {
		validarRegistro(registroDto);

		TcCliente cliente = clientesRepository.buscarCliente(registroDto.getnIdCliente());
		if (cliente == null) {
			throw new IllegalArgumentException("No existe el cliente indicado para el pago.");
		}

		TcDatosFactura datoFactura = tcDatosFacturaRepository.obtenerDatos(registroDto.getnIdDatoFactura());
		if (datoFactura == null) {
			throw new IllegalArgumentException("No existe la razón social emisora indicada.");
		}

		TwPagoCliente pago = new TwPagoCliente();
		pago.setnIdCliente(registroDto.getnIdCliente());
		pago.setnIdDatoFactura(registroDto.getnIdDatoFactura());
		pago.setdFechaRegistro(DateTimeUtil.obtenerHoraExactaDeMexico());
		pago.setdFechaPago(DateTimeUtil.normalizarFechaMxPosibleUtc(registroDto.getFechaPago()));
		pago.setnImporteTotal(registroDto.getImporteTotal());
		pago.setnImporteAplicado(BigDecimal.ZERO);
		pago.setnImporteDisponible(registroDto.getImporteTotal());
		pago.setsMoneda(registroDto.getMoneda() == null || registroDto.getMoneda().trim().isEmpty() ? MONEDA_DEFAULT : registroDto.getMoneda().trim().toUpperCase());
		pago.setnIdFormaPago(registroDto.getnIdFormaPago());
		pago.setsFormaPagoSat(trimToNull(registroDto.getFormaPagoSat()));
		pago.setsDescripcionFormaPago(trimToNull(registroDto.getDescripcionFormaPago()));
		pago.setsReferencia(trimToNull(registroDto.getReferencia()));
		pago.setsNumeroAutorizacion(trimToNull(registroDto.getNumeroAutorizacion()));
		pago.setsFolioOperacion(trimToNull(registroDto.getFolioOperacion()));
		pago.setsClaveRastreo(trimToNull(registroDto.getClaveRastreo()));
		pago.setsBancoOrigen(trimToNull(registroDto.getBancoOrigen()));
		pago.setsCuentaOrigen(trimToNull(registroDto.getCuentaOrigen()));
		pago.setsUltimos4CuentaOrigen(trimToNull(registroDto.getUltimos4CuentaOrigen()));
		pago.setsBancoDestino(trimToNull(registroDto.getBancoDestino()));
		pago.setsCuentaDestino(trimToNull(registroDto.getCuentaDestino()));
		pago.setsUltimos4CuentaDestino(trimToNull(registroDto.getUltimos4CuentaDestino()));
		pago.setsTitularCuenta(trimToNull(registroDto.getTitularCuenta()));
		pago.setsTerminal(trimToNull(registroDto.getTerminal()));
		pago.setsNumeroVoucher(trimToNull(registroDto.getNumeroVoucher()));
		pago.setsUltimos4Tarjeta(trimToNull(registroDto.getUltimos4Tarjeta()));
		pago.setsTipoTarjeta(trimToNull(registroDto.getTipoTarjeta()));
		pago.setsRedTarjeta(trimToNull(registroDto.getRedTarjeta()));
		pago.setsComprobanteUrl(trimToNull(registroDto.getComprobanteUrl()));
		pago.setsObservaciones(trimToNull(registroDto.getObservaciones()));
		pago.setnIdUsuarioRegistro(registroDto.getnIdUsuarioRegistro());
		pago.setnIdCaja(registroDto.getnIdCaja());
		pago.setnIdCorteCaja(registroDto.getnIdCorteCaja());
		pago.setsEstatus(ESTATUS_REGISTRADO);
		pago.setnConciliado(Boolean.FALSE);
		pago.setnFacturarRep(resolveFacturarPago(registroDto.getFacturarPago()));
		pago.setnEstatus(1);

		TwPagoCliente saved = twPagoClienteRepository.save(pago);
		return toDetalleDto(saved, new ArrayList<TwPagoAplicacion>());
	}

	@Override
	public PagoClienteDetalleDto consultarPago(Long nIdPagoCliente) {
		TwPagoCliente pago = twPagoClienteRepository.findBynId(nIdPagoCliente);
		if (pago == null) {
			throw new IllegalArgumentException("No existe el pago indicado.");
		}

		List<TwPagoAplicacion> aplicaciones = twPagoAplicacionRepository.findActivasByPago(nIdPagoCliente);
		return toDetalleDto(pago, aplicaciones);
	}

	@Override
	public byte[] descargarPaqueteComprobante(Long nIdPagoCliente) {
		TwPagoCliente pago = obtenerPagoValido(nIdPagoCliente);
		List<TwPagoAplicacion> aplicaciones = twPagoAplicacionRepository.findActivasByPago(nIdPagoCliente);
		if (aplicaciones == null || aplicaciones.isEmpty()) {
			throw new IllegalArgumentException("El pago global no tiene aplicaciones registradas para generar comprobantes.");
		}

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ZipOutputStream zip = new ZipOutputStream(baos)) {
			StringBuilder omisiones = new StringBuilder();
			agregarEntradaZip(zip, "resumen_pago_global_" + pago.getnId() + ".txt", construirResumenPago(pago, aplicaciones).getBytes(StandardCharsets.UTF_8));

			TwFacturacionComplementoPago complemento = resolveComplementoTimbradoPago(aplicaciones);
			if (complemento != null && complemento.getnId() != null) {
				tryAgregarEntradaZip(zip, omisiones, "complemento/complemento_pago_" + pago.getnId() + ".pdf",
						generaReporteService.getDocumentoComplemento(complemento.getnId(), TipoDoc.PDF_COMPLEMENTO_PAGO));
				tryAgregarEntradaZip(zip, omisiones, "complemento/complemento_pago_" + pago.getnId() + ".xml",
						generaReporteService.getDocumentoComplemento(complemento.getnId(), TipoDoc.XML_COMPLEMENTO_PAGO));
			}

			Set<Long> ventasIncluidas = new LinkedHashSet<Long>();
			for (TwPagoAplicacion aplicacion : aplicaciones) {
				if (aplicacion == null || aplicacion.getnIdVenta() == null || !ventasIncluidas.add(aplicacion.getnIdVenta())) {
					continue;
				}

				Long nIdVenta = aplicacion.getnIdVenta();
				tryAgregarEntradaZip(zip, omisiones, "abonos/abono_venta_" + nIdVenta + ".pdf", generaReporteService.getAbonoVentaIdPDF(nIdVenta));

				TwVenta venta = ventasRepository.findBynId(nIdVenta);
				if (venta != null && venta.getnIdFacturacion() != null && venta.getnIdFacturacion().longValue() > 0L) {
					tryAgregarEntradaZip(zip, omisiones, "facturas/factura_venta_" + nIdVenta + ".pdf", generaReporteService.getDocumento(nIdVenta, TipoDoc.PDF_FACTURA));
					tryAgregarEntradaZip(zip, omisiones, "facturas/factura_venta_" + nIdVenta + ".xml", generaReporteService.getDocumento(nIdVenta, TipoDoc.XML_FACTURA));
				}
			}

			if (omisiones.length() > 0) {
				agregarEntradaZip(zip, "resumen_documentos_omitidos.txt", omisiones.toString().getBytes(StandardCharsets.UTF_8));
			}

			zip.finish();
			return baos.toByteArray();
		} catch (IOException e) {
			throw new IllegalStateException("No fue posible construir el paquete del comprobante del pago global.", e);
		}
	}

	@Override
	public PagoComprobanteCorreoResponseDto enviarComprobanteCorreo(Long nIdPagoCliente) {
		TwPagoCliente pago = obtenerPagoValido(nIdPagoCliente);
		byte[] paquete = descargarPaqueteComprobante(nIdPagoCliente);
		if (paquete == null || paquete.length == 0) {
			throw new IllegalArgumentException("No fue posible generar el comprobante del pago global para enviarlo por correo.");
		}

		String asunto = "Comprobante de pago global #" + pago.getnId();
		String mensaje = "<p>Adjunto encontrará el comprobante del pago global registrado en su cuenta, junto con los documentos relacionados disponibles.</p>"
				+ "<p>Si requiere apoyo adicional, puede responder directamente a este correo.</p>";

		envioMail.ResultadoEnvioCorreo resultado = correoClienteService.enviarCorreoClienteConAdjuntos(
				pago.getnIdCliente(), asunto, mensaje,
				java.util.Collections.singletonList(new envioMail.AdjuntoCorreo(
						"comprobante_pago_global_" + pago.getnId() + ".zip", "application/zip", paquete)));

		PagoComprobanteCorreoResponseDto response = new PagoComprobanteCorreoResponseDto();
		response.setnIdPagoCliente(pago.getnId());
		response.setCorreoDestino(pago.getTcCliente() != null ? pago.getTcCliente().getsCorreo() : null);
		response.setEnviado(resultado.isEnviado());
		response.setBloqueado(resultado.debeBloquearCorreoCliente());
		response.setDetalle(resultado.getDetalle());
		return response;
	}

	@Override
	public List<PagoAplicacionLineaDto> consultarAplicacionesVenta(Long nIdVenta) {
		List<TwPagoAplicacion> aplicaciones = twPagoAplicacionRepository.findActivasByVenta(nIdVenta);
		List<PagoAplicacionLineaDto> lineas = new ArrayList<PagoAplicacionLineaDto>();
		for (TwPagoAplicacion aplicacion : aplicaciones) {
			lineas.add(toAplicacionLineaDto(aplicacion));
		}
		Collections.sort(lineas, Comparator.comparing(PagoAplicacionLineaDto::getFechaAplicacion,
				Comparator.nullsLast(Comparator.reverseOrder())));
		return lineas;
	}

	@Override
	public List<PagoClienteDetalleDto> consultarPagosCliente(Long nIdCliente) {
		List<TwPagoCliente> pagos = twPagoClienteRepository.findActivosByCliente(nIdCliente);
		List<PagoClienteDetalleDto> response = new ArrayList<PagoClienteDetalleDto>();
		for (TwPagoCliente pago : pagos) {
			List<TwPagoAplicacion> aplicaciones = twPagoAplicacionRepository.findActivasByPago(pago.getnId());
			response.add(toDetalleDto(pago, aplicaciones));
		}
		return response;
	}

	@Override
	public List<FacturaCreditoPendienteDto> consultarFacturasPendientesCliente(Long nIdCliente, Long nIdDatoFactura) {
		List<TvVentaDetalle> pendientes = tvVentaDetalleRepository.consultaVentaDetalleId(nIdCliente, 1L);
		List<FacturaCreditoPendienteDto> response = new ArrayList<FacturaCreditoPendienteDto>();

		for (TvVentaDetalle pendiente : pendientes) {
			TwVenta venta = ventasRepository.findBynId(pendiente.getnId());
			if (venta == null || venta.getTcCliente() == null) {
				continue;
			}
			if (!Long.valueOf(1L).equals(venta.getnTipoPago())) {
				continue;
			}
			if (!venta.getnIdCliente().equals(nIdCliente)) {
				continue;
			}

			FacturaCreditoPendienteDto dto = construirFacturaPendiente(venta, pendiente);
			if (dto.getSaldoPendiente().compareTo(BigDecimal.ZERO) > 0) {
				response.add(dto);
			}
		}

		Collections.sort(response, Comparator.comparing(FacturaCreditoPendienteDto::getFechaVenta, Comparator.nullsLast(Comparator.naturalOrder())));
		return response;
	}

	@Transactional
	@Override
	public PagoAplicacionResultadoDto aplicarPagoAutomatico(Long nIdPagoCliente, PagoAplicacionAutomaticaRequestDto requestDto) {
		TwPagoCliente pago = obtenerPagoValido(nIdPagoCliente);
		if (pago.getnImporteDisponible().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("El pago ya no tiene importe disponible.");
		}

		List<FacturaCreditoPendienteDto> pendientes = consultarFacturasPendientesCliente(pago.getnIdCliente(), pago.getnIdDatoFactura());
		List<FacturaCreditoPendienteDto> pendientesElegibles = new ArrayList<FacturaCreditoPendienteDto>();
		for (FacturaCreditoPendienteDto pendiente : pendientes) {
			if (pendiente == null || pendiente.getnIdVenta() == null) {
				continue;
			}
			TwVenta venta = ventasRepository.findBynId(pendiente.getnIdVenta());
			if (Boolean.TRUE.equals(resolveFacturarPago(pago.getnFacturarRep()))) {
				if (!esVentaElegibleParaAplicacion(venta, pago.getnIdCliente(), pago.getnIdDatoFactura())) {
					continue;
				}
			} else if (!esVentaSinFacturaAplicableParaPagoGlobal(venta, pago.getnIdCliente())) {
				continue;
			}
			pendientesElegibles.add(pendiente);
		}
		if (pendientesElegibles.isEmpty()) {
			throw new IllegalArgumentException("No hay facturas pendientes elegibles para aplicar este pago.");
		}

		BigDecimal disponible = pago.getnImporteDisponible();
		for (FacturaCreditoPendienteDto pendiente : pendientesElegibles) {
			if (disponible.compareTo(BigDecimal.ZERO) <= 0) {
				break;
			}

			BigDecimal montoAplicar = pendiente.getSaldoPendiente().min(disponible);
			if (montoAplicar.compareTo(BigDecimal.ZERO) <= 0) {
				continue;
			}

			registrarAplicacion(pago, pendiente.getnIdVenta(), montoAplicar, pendiente.getSaldoPendiente(),
					buildOrigenRegistro(requestDto != null ? requestDto.getOrigenRegistro() : null),
					requestDto != null ? requestDto.getnIdUsuario() : pago.getnIdUsuarioRegistro());

			disponible = disponible.subtract(montoAplicar);
		}

		return refreshPagoAplicado(pago.getnId());
	}

	@Transactional
	@Override
	public PagoAplicacionResultadoDto aplicarPagoManual(Long nIdPagoCliente, PagoAplicacionManualRequestDto requestDto) {
		TwPagoCliente pago = obtenerPagoValido(nIdPagoCliente);
		if (requestDto == null || requestDto.getLineas() == null || requestDto.getLineas().isEmpty()) {
			throw new IllegalArgumentException("Debes indicar al menos una factura para aplicar el pago.");
		}

		BigDecimal totalSolicitado = BigDecimal.ZERO;
		for (PagoAplicacionManualLineaDto linea : requestDto.getLineas()) {
			if (linea == null || linea.getnIdVenta() == null || linea.getMontoAplicar() == null) {
				throw new IllegalArgumentException("Todas las líneas deben indicar venta e importe a aplicar.");
			}
			if (linea.getMontoAplicar().compareTo(BigDecimal.ZERO) <= 0) {
				throw new IllegalArgumentException("Los importes a aplicar deben ser mayores a cero.");
			}
			totalSolicitado = totalSolicitado.add(linea.getMontoAplicar());
		}

		if (totalSolicitado.compareTo(pago.getnImporteDisponible()) > 0) {
			throw new IllegalArgumentException("La aplicación manual excede el importe disponible del pago.");
		}

		for (PagoAplicacionManualLineaDto linea : requestDto.getLineas()) {
			TwVenta venta = ventasRepository.findBynId(linea.getnIdVenta());
			if (venta == null) {
				throw new IllegalArgumentException("No existe la venta " + linea.getnIdVenta() + " para aplicar el pago.");
			}
			boolean facturarPago = Boolean.TRUE.equals(resolveFacturarPago(pago.getnFacturarRep()));
			if (facturarPago) {
				if (!esVentaElegibleParaAplicacion(venta, pago.getnIdCliente(), pago.getnIdDatoFactura())) {
					throw new IllegalArgumentException(
							"La venta " + linea.getnIdVenta() + " debe estar facturada en PPD/99 y vigente para generar complemento de pago.");
				}
			} else {
				if (esVentaElegibleParaAplicacion(venta, pago.getnIdCliente(), pago.getnIdDatoFactura())) {
					throw new IllegalArgumentException(
							"La venta " + linea.getnIdVenta() + " ya es relacionable y el pago debe registrarse como facturable para generar su complemento de pago.");
				}
				if (!esVentaSinFacturaAplicableParaPagoGlobal(venta, pago.getnIdCliente())) {
					throw new IllegalArgumentException("La venta " + linea.getnIdVenta() + " debe estar sin factura para aplicar un pago no facturable.");
				}
			}

			TvVentaDetalle detallePendiente = tvVentaDetalleRepository.consultaVentaDetalleId(venta.getnId());
			FacturaCreditoPendienteDto pendiente = construirFacturaPendiente(venta, detallePendiente);
			if (linea.getMontoAplicar().compareTo(pendiente.getSaldoPendiente()) > 0) {
				throw new IllegalArgumentException("La venta " + linea.getnIdVenta() + " no soporta el importe solicitado porque excede su saldo insoluto.");
			}

			registrarAplicacion(pago, venta.getnId(), linea.getMontoAplicar(), pendiente.getSaldoPendiente(),
					buildOrigenRegistro(requestDto.getOrigenRegistro()),
					requestDto.getnIdUsuario() != null ? requestDto.getnIdUsuario() : pago.getnIdUsuarioRegistro());
		}

		return refreshPagoAplicado(pago.getnId());
	}

	private void validarRegistro(PagoClienteRegistroDto registroDto) {
		if (registroDto == null) {
			throw new IllegalArgumentException("No se recibió información del pago.");
		}

		if (registroDto.getnIdCliente() == null) {
			throw new IllegalArgumentException("El cliente es obligatorio.");
		}

		if (registroDto.getnIdDatoFactura() == null) {
			throw new IllegalArgumentException("La razón social emisora es obligatoria.");
		}

		if (registroDto.getImporteTotal() == null || registroDto.getImporteTotal().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("El importe total del pago debe ser mayor a cero.");
		}

		if (registroDto.getnIdFormaPago() == null) {
			throw new IllegalArgumentException("La forma de pago es obligatoria.");
		}

		if (registroDto.getnIdUsuarioRegistro() == null) {
			throw new IllegalArgumentException("El usuario que registra el pago es obligatorio.");
		}
	}

	private TwPagoCliente obtenerPagoValido(Long nIdPagoCliente) {
		TwPagoCliente pago = twPagoClienteRepository.findBynId(nIdPagoCliente);
		if (pago == null || pago.getnEstatus() == null || pago.getnEstatus().intValue() != 1) {
			throw new IllegalArgumentException("No existe el pago indicado.");
		}
		return pago;
	}

	private boolean esVentaElegibleParaAplicacion(TwVenta venta, Long nIdCliente, Long nIdDatoFactura) {
		if (venta == null || venta.getTcCliente() == null) {
			return false;
		}
		if (!Long.valueOf(1L).equals(venta.getnTipoPago())) {
			return false;
		}
		if (!venta.getnIdCliente().equals(nIdCliente)) {
			return false;
		}
		if (venta.getnIdFacturacion() == null || venta.getnIdFacturacion().longValue() <= 0L) {
			return false;
		}
		TwFacturacion facturacion = facturaRepository.findById(venta.getnIdFacturacion()).orElse(null);
		if (facturacion == null) {
			return false;
		}
		if (facturacion.getsEstado() != null && facturacion.getsEstado().toUpperCase().contains("CANCEL")) {
			return false;
		}
		if (facturacion.getsUuid() == null || facturacion.getsUuid().trim().isEmpty()) {
			return false;
		}
		if (!esFacturaPpd99(facturacion)) {
			return false;
		}
		return true;
	}

	private boolean esVentaAplicableParaPagoGlobal(TwVenta venta, Long nIdCliente) {
		if (venta == null || venta.getTcCliente() == null) {
			return false;
		}
		if (!Long.valueOf(1L).equals(venta.getnTipoPago())) {
			return false;
		}
		if (nIdCliente == null || !venta.getnIdCliente().equals(nIdCliente)) {
			return false;
		}
		if (venta.getnIdFacturacion() == null || venta.getnIdFacturacion().longValue() <= 0L) {
			return true;
		}
		TwFacturacion facturacion = facturaRepository.findById(venta.getnIdFacturacion()).orElse(null);
		if (facturacion == null) {
			return true;
		}
		return facturacion.getsEstado() == null || !facturacion.getsEstado().toUpperCase().contains("CANCEL");
	}

	private boolean esVentaSinFacturaAplicableParaPagoGlobal(TwVenta venta, Long nIdCliente) {
		if (!esVentaAplicableParaPagoGlobal(venta, nIdCliente)) {
			return false;
		}
		return venta.getnIdFacturacion() == null || venta.getnIdFacturacion().longValue() <= 0L;
	}

	private FacturaCreditoPendienteDto construirFacturaPendiente(TwVenta venta, TvVentaDetalle detalleBase) {
		FacturaCreditoPendienteDto dto = new FacturaCreditoPendienteDto();
		BigDecimal descuento = detalleBase != null && detalleBase.getDescuento() != null
				? detalleBase.getDescuento()
				: BigDecimal.ZERO;
		BigDecimal totalVenta = detalleBase != null && detalleBase.getnTotalVenta() != null
				? detalleBase.getnTotalVenta().subtract(descuento)
				: calcularTotalVenta(venta.getnId()).subtract(descuento);
		if (totalVenta.compareTo(BigDecimal.ZERO) < 0) {
			totalVenta = BigDecimal.ZERO;
		}

		BigDecimal totalAbonoLegacy = detalleBase != null && detalleBase.getnTotalAbono() != null
				? detalleBase.getnTotalAbono()
				: totalAbonosLegacyVenta(venta.getnId());
		BigDecimal totalAplicadoCanonico = totalAplicadoCanonicoNoReflejadoEnAbonos(venta.getnId());
		BigDecimal totalAplicado = totalAbonoLegacy.add(totalAplicadoCanonico);
		BigDecimal saldoPendiente = totalVenta.subtract(totalAplicado);
		if (saldoPendiente.compareTo(BigDecimal.ZERO) < 0) {
			saldoPendiente = BigDecimal.ZERO;
		}

		boolean facturada = venta.getnIdFacturacion() != null && venta.getnIdFacturacion().longValue() > 0L;

		dto.setnIdVenta(venta.getnId());
		dto.setnIdFacturacion(venta.getnIdFacturacion());
		dto.setFolioVenta(venta.getsFolioVenta());
		dto.setFechaVenta(venta.getdFechaVenta());
		dto.setTotalVenta(totalVenta);
		dto.setTotalAplicado(totalAplicado);
		dto.setSaldoPendiente(saldoPendiente);
		dto.setParcialidadActual(nextParcialidad(venta.getnId()) - 1);
		dto.setFacturada(Boolean.valueOf(facturada));
		dto.setRequiereFacturacion(Boolean.valueOf(!facturada));
		dto.setEstadoFiscal(resolveEstadoFiscalAplicacion(venta));
		return dto;
	}

	private String resolveEstadoFiscalAplicacion(TwVenta venta) {
		if (venta == null || venta.getnIdFacturacion() == null || venta.getnIdFacturacion().longValue() <= 0L) {
			return "VENTA_CREDITO_SIN_FACTURA";
		}

		TwFacturacion facturacion = facturaRepository.findById(venta.getnIdFacturacion()).orElse(null);
		if (facturacion == null) {
			return "FACTURA_NO_LOCALIZADA";
		}
		if (facturacion.getsEstado() != null && facturacion.getsEstado().toUpperCase().contains("CANCEL")) {
			return "FACTURA_CANCELADA";
		}
		if (esFacturaPpd99(facturacion)) {
			return "FACTURA_PPD_99";
		}
		return "FACTURA_EMITIDA";
	}

	private BigDecimal calcularTotalVenta(Long nIdVenta) {
		List<TwVentasProducto> productos = twProductosVentaRepository.findBynIdVenta(nIdVenta);
		BigDecimal total = BigDecimal.ZERO;
		for (TwVentasProducto producto : productos) {
			if (producto.getnTotalPartida() != null) {
				total = total.add(producto.getnTotalPartida());
			}
		}
		return total;
	}

	private BigDecimal totalAplicadoVenta(Long nIdVenta) {
		BigDecimal total = twPagoAplicacionRepository.totalAplicadoActivoVenta(nIdVenta);
		return total == null ? BigDecimal.ZERO : total;
	}

	private BigDecimal totalAplicadoCanonicoNoReflejadoEnAbonos(Long nIdVenta) {
		List<TwPagoAplicacion> aplicaciones = twPagoAplicacionRepository.findActivasByVenta(nIdVenta);
		List<TwAbono> abonos = abonoVentaIdRepository.findBynIdVenta(nIdVenta);
		List<TwAbono> abonosCanonicosDisponibles = new ArrayList<TwAbono>();
		for (TwAbono abono : abonos) {
			if (abono != null && abono.getnIdPagoClienteCanonico() != null) {
				abonosCanonicosDisponibles.add(abono);
			}
		}

		BigDecimal total = BigDecimal.ZERO;
		for (TwPagoAplicacion aplicacion : aplicaciones) {
			if (aplicacion == null || aplicacion.getnMontoAplicado() == null) {
				continue;
			}
			if (ORIGEN_LEGACY_ABONO.equalsIgnoreCase(aplicacion.getsOrigenRegistro())) {
				continue;
			}
			TwAbono abonoRelacionado = resolveAbonoCanonicoRelacionado(abonosCanonicosDisponibles, aplicacion);
			if (abonoRelacionado != null) {
				abonosCanonicosDisponibles.remove(abonoRelacionado);
				continue;
			}
			total = total.add(aplicacion.getnMontoAplicado());
		}
		return total;
	}

	private BigDecimal totalAbonosLegacyVenta(Long nIdVenta) {
		List<TwAbono> abonos = abonoVentaIdRepository.findBynIdVenta(nIdVenta);
		if (abonos == null || abonos.isEmpty()) {
			return BigDecimal.ZERO;
		}

		BigDecimal total = BigDecimal.ZERO;
		for (TwAbono abono : abonos) {
			if (abono == null || abono.getnAbono() == null) {
				continue;
			}
			total = total.add(abono.getnAbono());
		}
		return total;
	}

	private TwAbono resolveAbonoCanonicoRelacionado(List<TwAbono> abonosCanonicosDisponibles, TwPagoAplicacion aplicacion) {
		if (abonosCanonicosDisponibles == null || abonosCanonicosDisponibles.isEmpty() || aplicacion == null
				|| aplicacion.getnIdPagoCliente() == null || aplicacion.getnMontoAplicado() == null) {
			return null;
		}

		for (TwAbono abono : abonosCanonicosDisponibles) {
			if (abono == null || abono.getnIdPagoClienteCanonico() == null || abono.getnAbono() == null) {
				continue;
			}
			if (aplicacion.getnIdPagoCliente().equals(abono.getnIdPagoClienteCanonico())
					&& aplicacion.getnMontoAplicado().compareTo(abono.getnAbono()) == 0) {
				return abono;
			}
		}
		return null;
	}

	private Integer nextParcialidad(Long nIdVenta) {
		Integer parcialidad = twPagoAplicacionRepository.maxParcialidadActivaVenta(nIdVenta);
		if (parcialidad == null || parcialidad.intValue() <= 0) {
			return 1;
		}
		return parcialidad + 1;
	}

	private void registrarAplicacion(TwPagoCliente pago, Long nIdVenta, BigDecimal montoAplicar, BigDecimal saldoAnterior,
			String origenRegistro, Long nIdUsuario) {
		TwVenta venta = ventasRepository.findBynId(nIdVenta);
		BigDecimal saldoInsoluto = saldoAnterior.subtract(montoAplicar);
		if (saldoInsoluto.compareTo(BigDecimal.ZERO) < 0) {
			saldoInsoluto = BigDecimal.ZERO;
		}

		TwPagoAplicacion aplicacion = new TwPagoAplicacion();
		aplicacion.setnIdPagoCliente(pago.getnId());
		aplicacion.setnIdCliente(pago.getnIdCliente());
		aplicacion.setnIdVenta(venta.getnId());
		aplicacion.setnIdFacturacion(venta.getnIdFacturacion());
		aplicacion.setnIdDatoFactura(pago.getnIdDatoFactura());
		aplicacion.setnMontoAplicado(montoAplicar);
		aplicacion.setnSaldoAnterior(saldoAnterior);
		aplicacion.setnSaldoInsoluto(saldoInsoluto);
		aplicacion.setnParcialidad(nextParcialidad(nIdVenta));
		aplicacion.setsEstatus("APLICADA");
		aplicacion.setdFechaAplicacion(DateTimeUtil.obtenerHoraExactaDeMexico());
		aplicacion.setnIdUsuario(nIdUsuario != null ? nIdUsuario : pago.getnIdUsuarioRegistro());
		aplicacion.setnOrdenAplicacion(twPagoAplicacionRepository.findActivasByPago(pago.getnId()).size() + 1);
		aplicacion.setsOrigenRegistro(origenRegistro);
		aplicacion.setnEstatus(1);
		twPagoAplicacionRepository.save(aplicacion);
		registrarAbonoCanonico(pago, venta, aplicacion);
		actualizarFechaLiquidacionCredito(venta, saldoInsoluto, aplicacion.getdFechaAplicacion());
	}

	private void registrarAbonoCanonico(TwPagoCliente pago, TwVenta venta, TwPagoAplicacion aplicacion) {
		if (pago == null || venta == null || aplicacion == null || aplicacion.getnMontoAplicado() == null
				|| aplicacion.getnMontoAplicado().compareTo(BigDecimal.ZERO) <= 0) {
			return;
		}

		TwAbono abono = new TwAbono();
		abono.setnIdVenta(venta.getnId());
		abono.setnIdPagoClienteCanonico(pago.getnId());
		abono.setdFecha(pago.getdFechaPago() != null ? pago.getdFechaPago() : DateTimeUtil.obtenerHoraExactaDeMexico());
		abono.setnAbono(aplicacion.getnMontoAplicado());
		abono.setnEstatus(1);

		TcFormapago formaPago = pago.getnIdFormaPago() != null
				? catalagoFormaPagoRepository.findById(pago.getnIdFormaPago()).orElse(null)
				: null;
		TwCaja caja = pago.getnIdCaja() != null ? cajaRepository.findById(pago.getnIdCaja()).orElse(null) : null;
		TcUsuario usuario = pago.getnIdUsuarioRegistro() != null ? usuariosRepository.findById(pago.getnIdUsuarioRegistro()).orElse(null) : null;

		abono.setTcFormapago(formaPago);
		abono.setTwCaja(caja);
		abono.setTcUsuario(usuario);
		abonoVentaIdRepository.save(abono);
	}

	private void actualizarFechaLiquidacionCredito(TwVenta venta, BigDecimal saldoInsoluto, java.time.LocalDateTime fechaAplicacion) {
		if (venta == null || saldoInsoluto == null || saldoInsoluto.compareTo(BigDecimal.ZERO) != 0) {
			return;
		}
		if (venta.getdFechaPagoCredito() != null) {
			return;
		}

		venta.setdFechaPagoCredito(fechaAplicacion != null ? fechaAplicacion : DateTimeUtil.obtenerHoraExactaDeMexico());
		ventasRepository.save(venta);
	}

	private PagoAplicacionResultadoDto refreshPagoAplicado(Long nIdPagoCliente) {
		TwPagoCliente pago = twPagoClienteRepository.findBynId(nIdPagoCliente);
		List<TwPagoAplicacion> aplicaciones = twPagoAplicacionRepository.findActivasByPago(nIdPagoCliente);
		BigDecimal totalAplicado = BigDecimal.ZERO;
		for (TwPagoAplicacion aplicacion : aplicaciones) {
			if (aplicacion.getnMontoAplicado() != null) {
				totalAplicado = totalAplicado.add(aplicacion.getnMontoAplicado());
			}
		}

		BigDecimal disponible = pago.getnImporteTotal().subtract(totalAplicado);
		if (disponible.compareTo(BigDecimal.ZERO) < 0) {
			disponible = BigDecimal.ZERO;
		}

		pago.setnImporteAplicado(totalAplicado);
		pago.setnImporteDisponible(disponible);
		pago.setsEstatus(disponible.compareTo(BigDecimal.ZERO) == 0 ? ESTATUS_APLICADO_TOTAL : ESTATUS_APLICADO_PARCIAL);
		pago = twPagoClienteRepository.save(pago);

		PagoAplicacionResultadoDto resultado = new PagoAplicacionResultadoDto();
		resultado.setnIdPagoCliente(pago.getnId());
		resultado.setImporteTotal(pago.getnImporteTotal());
		resultado.setImporteAplicado(pago.getnImporteAplicado());
		resultado.setImporteDisponible(pago.getnImporteDisponible());
		resultado.setEstatusPago(pago.getsEstatus());

		List<PagoAplicacionLineaDto> lineas = new ArrayList<PagoAplicacionLineaDto>();
		for (TwPagoAplicacion aplicacion : aplicaciones) {
			lineas.add(toAplicacionLineaDto(aplicacion));
		}
		resultado.setAplicaciones(lineas);
		return resultado;
	}

	private String buildOrigenRegistro(String origenRegistro) {
		if (origenRegistro == null || origenRegistro.trim().isEmpty()) {
			return ORIGEN_DEFAULT;
		}
		return origenRegistro.trim();
	}

	private Boolean resolveFacturarPago(Boolean facturarPago) {
		return facturarPago == null ? Boolean.TRUE : facturarPago;
	}

	private String construirResumenPago(TwPagoCliente pago, List<TwPagoAplicacion> aplicaciones) {
		StringBuilder resumen = new StringBuilder();
		resumen.append("Pago global #").append(pago.getnId()).append('\n');
		resumen.append("Cliente: ").append(pago.getnIdCliente()).append('\n');
		resumen.append("Fecha de pago: ").append(pago.getdFechaPago()).append('\n');
		resumen.append("Importe total: ").append(pago.getnImporteTotal()).append('\n');
		resumen.append("Importe aplicado: ").append(pago.getnImporteAplicado()).append('\n');
		resumen.append("Importe disponible: ").append(pago.getnImporteDisponible()).append('\n');
		resumen.append("Facturable SAT: ").append(Boolean.TRUE.equals(resolveFacturarPago(pago.getnFacturarRep())) ? "SI" : "NO").append("\n\n");
		resumen.append("Ventas relacionadas:\n");
		for (TwPagoAplicacion aplicacion : aplicaciones) {
			if (aplicacion == null) {
				continue;
			}
			resumen.append("- Venta ").append(aplicacion.getnIdVenta())
					.append(" | Monto aplicado: ").append(aplicacion.getnMontoAplicado())
					.append(" | Parcialidad: ").append(aplicacion.getnParcialidad())
					.append(" | Saldo insoluto: ").append(aplicacion.getnSaldoInsoluto())
					.append('\n');
		}
		return resumen.toString();
	}

	private void agregarEntradaZip(ZipOutputStream zip, String nombreEntrada, byte[] contenido) throws IOException {
		if (contenido == null || contenido.length == 0) {
			return;
		}
		ZipEntry entry = new ZipEntry(nombreEntrada);
		zip.putNextEntry(entry);
		zip.write(contenido);
		zip.closeEntry();
	}

	private void tryAgregarEntradaZip(ZipOutputStream zip, StringBuilder omisiones, String nombreEntrada, byte[] contenido)
			throws IOException {
		if (contenido == null || contenido.length == 0) {
			omisiones.append(nombreEntrada).append(" => sin contenido").append('\n');
			return;
		}
		agregarEntradaZip(zip, nombreEntrada, contenido);
	}

	private TwFacturacionComplementoPago resolveComplementoTimbradoPago(List<TwPagoAplicacion> aplicaciones) {
		TwFacturacionComplementoPago ultimoTimbrado = null;
		for (TwPagoAplicacion aplicacion : aplicaciones) {
			if (aplicacion == null || aplicacion.getnIdVenta() == null || aplicacion.getnId() == null) {
				continue;
			}
			List<TwFacturacionComplementoPago> complementos = facturacionComplementoPagoRepository.findActivosByOrigenPago(
					aplicacion.getnIdVenta(), "TW_PAGO_CLIENTE_APLICACION", aplicacion.getnId());
			for (TwFacturacionComplementoPago complemento : complementos) {
				ultimoTimbrado = complemento;
			}
		}
		return ultimoTimbrado;
	}


	private PagoClienteDetalleDto toDetalleDto(TwPagoCliente pago, List<TwPagoAplicacion> aplicaciones) {
		PagoClienteDetalleDto dto = new PagoClienteDetalleDto();
		dto.setnId(pago.getnId());
		dto.setnIdCliente(pago.getnIdCliente());
		dto.setnIdDatoFactura(pago.getnIdDatoFactura());
		dto.setFechaRegistro(pago.getdFechaRegistro());
		dto.setFechaPago(pago.getdFechaPago());
		dto.setImporteTotal(pago.getnImporteTotal());
		dto.setImporteAplicado(pago.getnImporteAplicado());
		dto.setImporteDisponible(pago.getnImporteDisponible());
		dto.setMoneda(pago.getsMoneda());
		dto.setnIdFormaPago(pago.getnIdFormaPago());
		dto.setFormaPagoSat(pago.getsFormaPagoSat());
		dto.setDescripcionFormaPago(pago.getsDescripcionFormaPago());
		dto.setReferencia(pago.getsReferencia());
		dto.setNumeroAutorizacion(pago.getsNumeroAutorizacion());
		dto.setFolioOperacion(pago.getsFolioOperacion());
		dto.setClaveRastreo(pago.getsClaveRastreo());
		dto.setBancoOrigen(pago.getsBancoOrigen());
		dto.setCuentaOrigen(pago.getsCuentaOrigen());
		dto.setUltimos4CuentaOrigen(pago.getsUltimos4CuentaOrigen());
		dto.setBancoDestino(pago.getsBancoDestino());
		dto.setCuentaDestino(pago.getsCuentaDestino());
		dto.setUltimos4CuentaDestino(pago.getsUltimos4CuentaDestino());
		dto.setTitularCuenta(pago.getsTitularCuenta());
		dto.setTerminal(pago.getsTerminal());
		dto.setNumeroVoucher(pago.getsNumeroVoucher());
		dto.setUltimos4Tarjeta(pago.getsUltimos4Tarjeta());
		dto.setTipoTarjeta(pago.getsTipoTarjeta());
		dto.setRedTarjeta(pago.getsRedTarjeta());
		dto.setComprobanteUrl(pago.getsComprobanteUrl());
		dto.setObservaciones(pago.getsObservaciones());
		dto.setnIdUsuarioRegistro(pago.getnIdUsuarioRegistro());
		dto.setnIdCaja(pago.getnIdCaja());
		dto.setnIdCorteCaja(pago.getnIdCorteCaja());
		dto.setEstatus(pago.getsEstatus());
		dto.setFacturarPago(resolveFacturarPago(pago.getnFacturarRep()));
		enriquecerEstadoRepCanonico(dto, aplicaciones);
		dto.setConciliado(pago.getnConciliado());
		dto.setFechaConciliacion(pago.getdFechaConciliacion());

		List<PagoAplicacionLineaDto> lineas = new ArrayList<PagoAplicacionLineaDto>();
		for (TwPagoAplicacion aplicacion : aplicaciones) {
			lineas.add(toAplicacionLineaDto(aplicacion));
		}
		dto.setAplicaciones(lineas);
		return dto;
	}

	private PagoAplicacionLineaDto toAplicacionLineaDto(TwPagoAplicacion aplicacion) {
		PagoAplicacionLineaDto linea = new PagoAplicacionLineaDto();
		if (aplicacion == null) {
			return linea;
		}
		linea.setnId(aplicacion.getnId());
		linea.setnIdPagoCliente(aplicacion.getnIdPagoCliente());
		linea.setnIdVenta(aplicacion.getnIdVenta());
		linea.setnIdFacturacion(aplicacion.getnIdFacturacion());
		linea.setMontoAplicado(aplicacion.getnMontoAplicado());
		linea.setSaldoAnterior(aplicacion.getnSaldoAnterior());
		linea.setSaldoInsoluto(aplicacion.getnSaldoInsoluto());
		linea.setParcialidad(aplicacion.getnParcialidad());
		linea.setEstatus(aplicacion.getsEstatus());
		linea.setOrdenAplicacion(aplicacion.getnOrdenAplicacion());
		linea.setOrigenRegistro(aplicacion.getsOrigenRegistro());
		linea.setFechaAplicacion(aplicacion.getdFechaAplicacion());
		TwVenta venta = null;
		if (aplicacion.getnIdVenta() != null) {
			venta = ventasRepository.findBynId(aplicacion.getnIdVenta());
			if (venta != null) {
				linea.setFolioVenta(venta.getsFolioVenta());
				linea.setFechaVenta(venta.getdFechaVenta());
				if (linea.getnIdFacturacion() == null || linea.getnIdFacturacion().longValue() <= 0L) {
					linea.setnIdFacturacion(venta.getnIdFacturacion());
				}
			}
		}

		Long nIdFacturacion = linea.getnIdFacturacion();
		if (nIdFacturacion != null && nIdFacturacion.longValue() > 0L) {
			TwFacturacion facturacion = facturaRepository.findById(nIdFacturacion).orElse(null);
			if (facturacion != null) {
				linea.setUuidFactura(facturacion.getsUuid());
				linea.setEstadoFactura(facturacion.getsEstado());
				linea.setMetodoPagoFiscal(facturacion.getsMetodoPagoFiscal());
				linea.setFormaPagoFiscal(facturacion.getsFormaPagoFiscal());
				linea.setEstadoComplemento(facturacion.getsEstadoComplemento());
			}
		}

		TwFacturacionComplementoPago complementoAplicacion = aplicacion.getnId() != null
				? facturacionComplementoPagoRepository
						.findActivosByOrigenPago(aplicacion.getnIdVenta(), "TW_PAGO_CLIENTE_APLICACION", aplicacion.getnId())
						.stream()
						.findFirst()
						.orElse(null)
				: null;
		if (complementoAplicacion == null && aplicacion.getnIdVenta() != null) {
			complementoAplicacion = facturacionComplementoPagoRepository.findActivosByVenta(aplicacion.getnIdVenta())
					.stream()
					.filter(complemento -> complemento != null && "TW_PAGO_CLIENTE_APLICACION".equalsIgnoreCase(complemento.getsOrigenPago()))
					.findFirst()
					.orElse(null);
		}
		if (complementoAplicacion != null) {
			linea.setnIdComplementoRepCanonico(complementoAplicacion.getnId());
			linea.setUuidRepCanonico(complementoAplicacion.getsUuidComplementoPago());
		}

		if (aplicacion.getTwPagoCliente() != null) {
			linea.setFechaPago(aplicacion.getTwPagoCliente().getdFechaPago());
			linea.setFormaPagoSat(aplicacion.getTwPagoCliente().getsFormaPagoSat());
			linea.setDescripcionFormaPago(aplicacion.getTwPagoCliente().getsDescripcionFormaPago());
			linea.setReferenciaPago(aplicacion.getTwPagoCliente().getsReferencia());
			linea.setObservacionesPago(aplicacion.getTwPagoCliente().getsObservaciones());
		} else if (aplicacion.getnIdPagoCliente() != null) {
			TwPagoCliente pagoCliente = twPagoClienteRepository.findBynId(aplicacion.getnIdPagoCliente());
			if (pagoCliente != null) {
				linea.setFechaPago(pagoCliente.getdFechaPago());
				linea.setFormaPagoSat(pagoCliente.getsFormaPagoSat());
				linea.setDescripcionFormaPago(pagoCliente.getsDescripcionFormaPago());
				linea.setReferenciaPago(pagoCliente.getsReferencia());
				linea.setObservacionesPago(pagoCliente.getsObservaciones());
			}
		}
		return linea;
	}

	private void enriquecerEstadoRepCanonico(PagoClienteDetalleDto dto, List<TwPagoAplicacion> aplicaciones) {
		if (aplicaciones == null || aplicaciones.isEmpty()) {
			dto.setEstadoRepCanonico("SIN_APLICACIONES");
			return;
		}

		java.util.Set<Long> idsAplicacion = new java.util.HashSet<Long>();
		java.util.Set<Long> idsVenta = new java.util.HashSet<Long>();
		boolean tieneAplicacionesSinFactura = false;
		for (TwPagoAplicacion aplicacion : aplicaciones) {
			if (aplicacion == null) {
				continue;
			}
			if (aplicacion.getnId() != null) {
				idsAplicacion.add(aplicacion.getnId());
			}
			if (aplicacion.getnIdVenta() != null) {
				idsVenta.add(aplicacion.getnIdVenta());
			}
			if (aplicacion.getnIdFacturacion() == null || aplicacion.getnIdFacturacion().longValue() <= 0L) {
				tieneAplicacionesSinFactura = true;
			}
		}

		TwFacturacionComplementoPago ultimoTimbrado = null;
		TwFacturacionComplementoPago ultimoFallido = null;
		for (Long nIdVenta : idsVenta) {
			List<TwFacturacionComplementoPago> complementos = facturacionComplementoPagoRepository.findByVenta(nIdVenta);
			for (TwFacturacionComplementoPago complemento : complementos) {
				if (complemento == null || !"TW_PAGO_CLIENTE_APLICACION".equalsIgnoreCase(complemento.getsOrigenPago())
						|| complemento.getnIdPagoOrigen() == null || !idsAplicacion.contains(complemento.getnIdPagoOrigen())) {
					continue;
				}
				if (complemento.getnEstatus() != null && complemento.getnEstatus().intValue() == 1) {
					ultimoTimbrado = complemento;
				}
				if (complemento.getnEstatus() != null && complemento.getnEstatus().intValue() == 0) {
					ultimoFallido = complemento;
				}
			}
		}

		if (ultimoTimbrado != null) {
			dto.setEstadoRepCanonico("TIMBRADO");
			dto.setUuidRepCanonico(ultimoTimbrado.getsUuidComplementoPago());
			dto.setnIdComplementoRepCanonico(ultimoTimbrado.getnId());
			return;
		}

		if (ultimoFallido != null) {
			dto.setEstadoRepCanonico("FALLIDO");
			return;
		}

		boolean tienePendiente = false;
		boolean tieneNoFacturada = false;
		for (Long nIdVenta : idsVenta) {
			String estado = resolveEstadoRepSinAplicaciones(nIdVenta);
			if ("PENDIENTE".equalsIgnoreCase(estado)) {
				tienePendiente = true;
				break;
			}
			if ("NO_FACTURADA".equalsIgnoreCase(estado)) {
				tieneNoFacturada = true;
			}
		}

		if (tienePendiente) {
			dto.setEstadoRepCanonico("PENDIENTE");
			return;
		}
		if (tieneAplicacionesSinFactura || tieneNoFacturada) {
			dto.setEstadoRepCanonico("PENDIENTE_FACTURACION");
			return;
		}
		dto.setEstadoRepCanonico("NO_REQUIERE");
	}

	private String resolveEstadoRepSinAplicaciones(Long nIdVenta) {
		TwVenta venta = ventasRepository.findBynId(nIdVenta);
		if (venta == null || venta.getnIdFacturacion() == null || venta.getnIdFacturacion().longValue() <= 0L) {
			return "NO_FACTURADA";
		}
		TwFacturacion facturacion = facturaRepository.findById(venta.getnIdFacturacion()).orElse(null);
		if (facturacion == null) {
			return "NO_FACTURADA";
		}
		if (esFacturaPpd99(facturacion)) {
			return "PENDIENTE";
		}
		return "NO_REQUIERE";
	}

	private boolean esFacturaPpd99(TwFacturacion facturacion) {
		return facturacion != null
				&& esMetodoPagoPpd(facturacion.getsMetodoPagoFiscal())
				&& esFormaPago99PorDefinir(facturacion.getsFormaPagoFiscal());
	}

	private boolean esMetodoPagoPpd(String metodoPagoFiscal) {
		String metodo = trimToNull(metodoPagoFiscal);
		return metodo != null && "PPD".equalsIgnoreCase(metodo);
	}

	private boolean esFormaPago99PorDefinir(String formaPagoFiscal) {
		String forma = trimToNull(formaPagoFiscal);
		if (forma == null) {
			return false;
		}
		String normalized = forma.toUpperCase();
		return "99".equals(normalized)
				|| normalized.startsWith("99 ")
				|| normalized.startsWith("99-")
				|| normalized.startsWith("99 -")
				|| normalized.contains("POR DEFINIR");
	}

	private String trimToNull(String value) {
		if (value == null) {
			return null;
		}
		String trimmed = value.trim();
		return trimmed.isEmpty() ? null : trimmed;
	}
}