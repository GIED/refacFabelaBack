package com.refacFabela.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.refacFabela.dto.FacturaCreditoPendienteDto;
import com.refacFabela.dto.PagoAplicacionAutomaticaRequestDto;
import com.refacFabela.dto.PagoAplicacionManualLineaDto;
import com.refacFabela.dto.PagoAplicacionManualRequestDto;
import com.refacFabela.dto.PagoAplicacionLineaDto;
import com.refacFabela.dto.PagoAplicacionResultadoDto;
import com.refacFabela.dto.PagoClienteDetalleDto;
import com.refacFabela.dto.PagoClienteRegistroDto;
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
import com.refacFabela.service.PagoClienteService;
import com.refacFabela.utils.DateTimeUtil;

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
		pago.setdFechaPago(registroDto.getFechaPago() != null ? registroDto.getFechaPago() : DateTimeUtil.obtenerHoraExactaDeMexico());
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
			if (!esVentaElegibleParaAplicacion(venta, nIdCliente, nIdDatoFactura)) {
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
		if (pendientes.isEmpty()) {
			throw new IllegalArgumentException("No hay facturas pendientes elegibles para aplicar este pago.");
		}

		BigDecimal disponible = pago.getnImporteDisponible();
		for (FacturaCreditoPendienteDto pendiente : pendientes) {
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
			if (!esVentaElegibleParaAplicacion(venta, pago.getnIdCliente(), pago.getnIdDatoFactura())) {
				throw new IllegalArgumentException("La venta " + linea.getnIdVenta() + " no es elegible para aplicar este pago.");
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
		if (venta.getnIdFacturacion() == null || venta.getnIdFacturacion().longValue() == 0L) {
			return true;
		}
		TwFacturacion facturacion = facturaRepository.findById(venta.getnIdFacturacion()).orElse(null);
		if (facturacion == null) {
			return true;
		}
		return facturacion.getsEstado() == null || !facturacion.getsEstado().toUpperCase().contains("CANCEL");
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
				: BigDecimal.ZERO;
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
		if ("PPD".equalsIgnoreCase(facturacion.getsMetodoPagoFiscal()) && "99".equalsIgnoreCase(facturacion.getsFormaPagoFiscal())) {
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
		if ("PPD".equalsIgnoreCase(facturacion.getsMetodoPagoFiscal())
				&& "99".equalsIgnoreCase(facturacion.getsFormaPagoFiscal())) {
			return "PENDIENTE";
		}
		return "NO_REQUIERE";
	}

	private String trimToNull(String value) {
		if (value == null) {
			return null;
		}
		String trimmed = value.trim();
		return trimmed.isEmpty() ? null : trimmed;
	}
}