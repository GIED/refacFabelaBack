package com.refacFabela.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.refacFabela.dto.CancelacionFacturaDto;
import com.refacFabela.dto.SolicitudCancelacionAccionDto;
import com.refacFabela.dto.SubirFacturaDto;
import com.refacFabela.config.FacturacionProperties;
import com.refacFabela.dto.CancelacionResponse;
import com.refacFabela.dto.CfdiRelacionadosResponse;
import com.refacFabela.dto.ComplementoPagoHistorialDto;
import com.refacFabela.dto.FacturaReenvioCorreoRequestDto;
import com.refacFabela.dto.FacturaReenvioCorreoResponseDto;
import com.refacFabela.dto.FacturacionVentaDivididaRequestDto;
import com.refacFabela.dto.FacturacionVentasRequestDto;
import com.refacFabela.dto.ResultadoFacturacionVentaDto;
import com.refacFabela.dto.SolicitudCancelacionDto;
import com.refacFabela.dto.StatusCfdiResponse;
import com.refacFabela.dto.TimbradoResponse;
import com.refacFabela.exception.FacturacionException;
import com.refacFabela.exception.PacFacturacionClientException;
import com.refacFabela.service.PacFacturacionClient;
import com.refacFabela.service.GeneraReporteService;
import com.refacFabela.service.impl.ComplementoPagoService;
import com.refacFabela.service.impl.CancelacionFacturacionService;
import com.refacFabela.service.impl.ConsultaFacturacionService;
import com.refacFabela.service.impl.TimbradoVentaService;
import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TcDatosFactura;
import com.refacFabela.model.TwFacturacion;
import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TwVenta;
import com.refacFabela.repository.ClientesRepository;
import com.refacFabela.repository.FacturaRepository;
import com.refacFabela.repository.TcDatosFacturaRepository;
import com.refacFabela.repository.TrVentaCobroRepository;
import com.refacFabela.repository.TwPedidoRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.service.FacturacionService;
import com.refacFabela.utils.subirArchivo;
import com.refacFabela.utils.envioMail;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacturacionServiceImpl implements FacturacionService {

	private static final Logger logger = LogManager.getLogger("errorLogger");

	private final VentasRepository ventaRepository;
	private final ClientesRepository clientesRepository;
	private final FacturaRepository facturaRepository;
	private final TcDatosFacturaRepository tcDatosFacturaRepository;
	private final TwPedidoRepository twPedidoRepository;
	private final TrVentaCobroRepository trVentaCobroRepository;
	private final FacturacionProperties facturacionProperties;
	private final TimbradoVentaService timbradoVentaService;
	private final CancelacionFacturacionService cancelacionFacturacionService;
	private final ComplementoPagoService complementoPagoService;
	private final ConsultaFacturacionService consultaFacturacionService;
	private final PacFacturacionClient pacFacturacionClient;
	private final DatosFacturaStorageResolver datosFacturaStorageResolver;
	private final CorreoClienteService correoClienteService;
	private final GeneraReporteService generaReporteService;
	private final envioMail mailSender;

	public FacturacionServiceImpl(VentasRepository ventaRepository,
			ClientesRepository clientesRepository,
			FacturaRepository facturaRepository,
			TcDatosFacturaRepository tcDatosFacturaRepository,
			TwPedidoRepository twPedidoRepository,
			TrVentaCobroRepository trVentaCobroRepository,
			FacturacionProperties facturacionProperties,
			TimbradoVentaService timbradoVentaService,
			CancelacionFacturacionService cancelacionFacturacionService,
			ComplementoPagoService complementoPagoService,
			ConsultaFacturacionService consultaFacturacionService,
			PacFacturacionClient pacFacturacionClient,
			DatosFacturaStorageResolver datosFacturaStorageResolver,
			CorreoClienteService correoClienteService,
			GeneraReporteService generaReporteService,
			envioMail mailSender) {
		this.ventaRepository = ventaRepository;
		this.clientesRepository = clientesRepository;
		this.facturaRepository = facturaRepository;
		this.tcDatosFacturaRepository = tcDatosFacturaRepository;
		this.twPedidoRepository = twPedidoRepository;
		this.trVentaCobroRepository = trVentaCobroRepository;
		this.facturacionProperties = facturacionProperties;
		this.timbradoVentaService = timbradoVentaService;
		this.cancelacionFacturacionService = cancelacionFacturacionService;
		this.complementoPagoService = complementoPagoService;
		this.consultaFacturacionService = consultaFacturacionService;
		this.pacFacturacionClient = pacFacturacionClient;
		this.datosFacturaStorageResolver = datosFacturaStorageResolver;
		this.correoClienteService = correoClienteService;
		this.generaReporteService = generaReporteService;
		this.mailSender = mailSender;
	}

	@Override
	public ResultadoFacturacionVentaDto venta(Long idVenta, String cveCfdi) throws Exception {
		ResultadoFacturacionVentaDto resultado = new ResultadoFacturacionVentaDto();
		try {
			TimbradoResponse timbradoIngreso = timbradoVentaService.timbrarVenta(idVenta, cveCfdi);
			TwVenta ventaActualizada = ventaRepository.findBynId(idVenta);
			TcCliente clienteActualizado = null;
			if (ventaActualizada != null && ventaActualizada.getnIdCliente() != null) {
				clienteActualizado = clientesRepository.findById(ventaActualizada.getnIdCliente()).orElse(null);
			}

			resultado.setSuccess(true);
			resultado.setClasificacionFiscal(timbradoIngreso.getClasificacionFiscal());
			resultado.setMetodoPagoFiscal(timbradoIngreso.getMetodoPagoFiscal());
			resultado.setFormaPagoFiscal(timbradoIngreso.getFormaPagoFiscal());
			resultado.setUuidFacturaIngreso(timbradoIngreso.getUuid());
			resultado.setEstadoFacturacion(timbradoIngreso.getEstatus());

			TwFacturacion facturacion = ventaActualizada != null && ventaActualizada.getnIdFacturacion() != null
					? facturaRepository.findById(ventaActualizada.getnIdFacturacion()).orElse(null)
					: null;

			if (timbradoIngreso.getComplementoInmediatoRequerido() != null
					&& timbradoIngreso.getComplementoInmediatoRequerido().booleanValue()
					&& facturacionProperties != null
					&& facturacionProperties.getPagosMixtos() != null
					&& facturacionProperties.getPagosMixtos().isGenerarComplementoInmediato()) {
				try {
					TimbradoResponse complemento = complementoPagoService.timbrarComplemento(idVenta, cveCfdi);
					resultado.setUuidComplementoPago(complemento.getUuid());
					resultado.setEstadoComplemento("FACTURADA_CON_COMPLEMENTO_PAGO");
					resultado.setMensaje("Factura y complemento de pago generados correctamente.");
				} catch (Exception complementoError) {
					logger.error("Factura ingreso generada pero complemento de pago quedo pendiente para venta {}", idVenta,
							complementoError);
					resultado.setEstadoComplemento("PENDIENTE_COMPLEMENTO_PAGO");
					resultado.setCodigoError("REP_ERROR");
					resultado.setMensajeError(complementoError.getMessage());
					resultado.setMensaje("Factura generada correctamente; el complemento de pago quedÃ³ pendiente.");
				}
			} else {
				boolean repCanonicoProcesado = false;
				if (esPedidoConAnticipoRegistrado(ventaActualizada)) {
					try {
						TimbradoResponse complemento = complementoPagoService.timbrarComplemento(idVenta, cveCfdi);
						resultado.setUuidComplementoPago(complemento.getUuid());
						resultado.setEstadoComplemento("PENDIENTE_COMPLEMENTO_PAGO");
						resultado.setMensaje("Factura de pedido y complemento del anticipo generados correctamente.");
						repCanonicoProcesado = true;
					} catch (Exception complementoPedidoError) {
						logger.error("Factura de pedido generada pero el complemento del anticipo quedo pendiente para venta {}", idVenta,
								complementoPedidoError);
						resultado.setEstadoComplemento("PENDIENTE_COMPLEMENTO_PAGO");
						resultado.setCodigoError("REP_ANTICIPO_PEDIDO_ERROR");
						resultado.setMensajeError(complementoPedidoError.getMessage());
						resultado.setMensaje("Factura de pedido generada correctamente; el complemento del anticipo quedo pendiente.");
						repCanonicoProcesado = true;
					}
				}
				if (!repCanonicoProcesado && esFacturaPpd99(timbradoIngreso)) {
					try {
						java.util.List<TimbradoResponse> complementosCanonicos = complementoPagoService
								.timbrarComplementosPagoClientePendientesVentas(java.util.Collections.singletonList(idVenta));
						if (complementosCanonicos != null && !complementosCanonicos.isEmpty()) {
							TimbradoResponse ultimoComplemento = complementosCanonicos.get(complementosCanonicos.size() - 1);
							resultado.setUuidComplementoPago(ultimoComplemento.getUuid());
							resultado.setEstadoComplemento("FACTURADA_CON_COMPLEMENTO_PAGO");
							resultado.setMensaje("Venta facturada y complemento(s) de pago canÃ³nico generado(s) correctamente.");
							repCanonicoProcesado = true;
						}
					} catch (Exception complementoCanonicoError) {
						logger.error("Venta facturada pero el REP del pago global quedÃ³ pendiente para venta {}", idVenta,
								complementoCanonicoError);
						resultado.setEstadoComplemento("PENDIENTE_COMPLEMENTO_PAGO");
						resultado.setCodigoError("REP_PAGO_CLIENTE_ERROR");
						resultado.setMensajeError(complementoCanonicoError.getMessage());
						resultado.setMensaje("Venta facturada correctamente; el REP del pago global quedÃ³ pendiente.");
						repCanonicoProcesado = true;
					}
				}

				if (!repCanonicoProcesado) {
					if (facturacion != null) {
						resultado.setEstadoComplemento(facturacion.getsEstadoComplemento());
						resultado.setUuidComplementoPago(facturacion.getsUuidComplementoPago());
					} else {
						resultado.setEstadoComplemento("NO_REQUIERE_COMPLEMENTO");
					}
					resultado.setMensaje("Venta Facturada");
				}
			}

			if (clienteActualizado != null && Boolean.TRUE.equals(clienteActualizado.getnCorreoBloqueado())) {
				resultado.setAvisoCorreo("La factura se generÃ³ correctamente pero el correo del cliente estÃ¡ bloqueado. No se enviÃ³ la notificaciÃ³n por correo.");
			}

			facturacion = ventaActualizada != null && ventaActualizada.getnIdFacturacion() != null
					? facturaRepository.findById(ventaActualizada.getnIdFacturacion()).orElse(null)
					: facturacion;
			if (facturacion != null) {
				resultado.setEstadoFacturacion(facturacion.getsEstado());
				resultado.setEstadoComplemento(facturacion.getsEstadoComplemento());
				resultado.setUuidComplementoPago(facturacion.getsUuidComplementoPago());
			}

			return resultado;
		} catch (Exception e) {
			logger.error("Error al facturar venta {} con cveCfdi {} usando proveedor activo {}", idVenta, cveCfdi,
					facturacionProperties != null ? facturacionProperties.getProveedorActivo() : null, e);
			resultado.setSuccess(false);
			resultado.setMensaje("Error al facturar la venta");
			resultado.setCodigoError("TIMBRADO_ERROR");
			resultado.setMensajeError(e.getMessage());
			return resultado;
		}
	}

	private boolean esPedidoConAnticipoRegistrado(TwVenta venta) {
		if (venta == null || venta.getnId() == null || twPedidoRepository.pedido(venta.getnId()) == null) {
			return false;
		}
		java.util.List<TrVentaCobro> cobros = trVentaCobroRepository.findBynIdVenta(venta.getnId());
		return cobros != null && !cobros.isEmpty();
	}

	@Override
	public ResultadoFacturacionVentaDto ventaDividida(FacturacionVentaDivididaRequestDto requestDto) throws Exception {
		ResultadoFacturacionVentaDto resultado = new ResultadoFacturacionVentaDto();
		try {
			if (requestDto == null || requestDto.getnIdVenta() == null) {
				throw new FacturacionException("Debes indicar la venta a facturar en modo dividido.");
			}
			resultado = timbradoVentaService.timbrarVentaDivididaEfectivo(requestDto.getnIdVenta(), requestDto.getCveCfdi());
			return resultado;
		} catch (Exception e) {
			logger.error("Error al facturar venta {} en modo dividido usando proveedor activo {}",
					requestDto != null ? requestDto.getnIdVenta() : null,
					facturacionProperties != null ? facturacionProperties.getProveedorActivo() : null, e);
			resultado.setSuccess(false);
			resultado.setMensaje("Error al facturar la venta en modo dividido");
			resultado.setCodigoError("TIMBRADO_DIVIDIDO_ERROR");
			resultado.setMensajeError(e.getMessage());
			return resultado;
		}
	}

	@Override
	public ResultadoFacturacionVentaDto ventaConsolidada(FacturacionVentasRequestDto requestDto) throws Exception {
		ResultadoFacturacionVentaDto resultado = new ResultadoFacturacionVentaDto();
		try {
			TimbradoResponse timbradoIngreso = timbradoVentaService.timbrarVentasConsolidadas(
					requestDto != null ? requestDto.getnIdsVenta() : null,
					requestDto != null ? requestDto.getCveCfdi() : null);

			resultado.setSuccess(true);
			resultado.setClasificacionFiscal(timbradoIngreso.getClasificacionFiscal());
			resultado.setMetodoPagoFiscal(timbradoIngreso.getMetodoPagoFiscal());
			resultado.setFormaPagoFiscal(timbradoIngreso.getFormaPagoFiscal());
			resultado.setUuidFacturaIngreso(timbradoIngreso.getUuid());
			resultado.setEstadoFacturacion(timbradoIngreso.getEstatus());

			boolean repCanonicoProcesado = false;
			if (esFacturaPpd99(timbradoIngreso)) {
				try {
					java.util.List<TimbradoResponse> complementosCanonicos = complementoPagoService
							.timbrarComplementosPagoClientePendientesVentas(
									requestDto != null ? requestDto.getnIdsVenta() : null);
					if (complementosCanonicos != null && !complementosCanonicos.isEmpty()) {
						TimbradoResponse ultimoComplemento = complementosCanonicos.get(complementosCanonicos.size() - 1);
						resultado.setUuidComplementoPago(ultimoComplemento.getUuid());
						resultado.setEstadoComplemento("FACTURADA_CON_COMPLEMENTO_PAGO");
						resultado.setMensaje("Factura consolidada y complemento(s) de pago canÃ³nico generado(s) correctamente.");
						repCanonicoProcesado = true;
					}
				} catch (Exception complementoCanonicoError) {
					logger.error("Factura consolidada generada pero el REP del pago global quedÃ³ pendiente para ventas {}",
							requestDto != null ? requestDto.getnIdsVenta() : null, complementoCanonicoError);
					resultado.setEstadoComplemento("PENDIENTE_COMPLEMENTO_PAGO");
					resultado.setCodigoError("REP_PAGO_CLIENTE_ERROR");
					resultado.setMensajeError(complementoCanonicoError.getMessage());
					resultado.setMensaje("Factura consolidada generada correctamente; el REP del pago global quedÃ³ pendiente.");
					repCanonicoProcesado = true;
				}
			}

			if (!repCanonicoProcesado) {
				resultado.setEstadoComplemento("PPD".equalsIgnoreCase(timbradoIngreso.getMetodoPagoFiscal())
						? "PENDIENTE_COMPLEMENTO_PAGO"
						: "NO_REQUIERE_COMPLEMENTO");
				resultado.setMensaje("Factura consolidada generada correctamente.");
			}
			return resultado;
		} catch (Exception e) {
			logger.error("Error al facturar ventas consolidadas usando proveedor activo {}",
					facturacionProperties != null ? facturacionProperties.getProveedorActivo() : null, e);
			resultado.setSuccess(false);
			resultado.setMensaje("Error al facturar las ventas consolidadas");
			resultado.setCodigoError("TIMBRADO_CONSOLIDADO_ERROR");
			resultado.setMensajeError(e.getMessage());
			return resultado;
		}
	}

	private boolean esFacturaPpd99(TimbradoResponse timbradoIngreso) {
		return timbradoIngreso != null
				&& "PPD".equalsIgnoreCase(timbradoIngreso.getMetodoPagoFiscal())
				&& "99".equalsIgnoreCase(timbradoIngreso.getFormaPagoFiscal());
	}

	@Override
	public String cancelaFactura(Long idVenta, String cveCfdi) throws Exception {
		try {
			cancelacionFacturacionService.cancelarVenta(idVenta, "02", null);
			return "ok";
		} catch (Exception e) {
			logger.error("Error al cancelar venta {} usando proveedor activo {}", idVenta,
					facturacionProperties != null ? facturacionProperties.getProveedorActivo() : null, e);
			return "Error al facturar";
		}
	}

	@Override
	public String cancelaFactura(CancelacionFacturaDto cancelacionFacturaDto) throws Exception {
		try {
			cancelacionFacturacionService.cancelarVenta(
					cancelacionFacturaDto.getnIdVenta(),
					cancelacionFacturaDto.getMotivo(),
					cancelacionFacturaDto.getFolioFiscalSustitucion());
			return "ok";
		} catch (Exception e) {
			logger.error("Error al cancelar venta {} con motivo {} usando proveedor activo {}",
					cancelacionFacturaDto.getnIdVenta(), cancelacionFacturaDto.getMotivo(),
					facturacionProperties != null ? facturacionProperties.getProveedorActivo() : null, e);
			return e.getMessage() != null ? e.getMessage() : "No fue posible cancelar la factura.";
		}
	}

	@Override
	public ResultadoFacturacionVentaDto complemento(Long idVenta, String cveCfdi) throws Exception {
		ResultadoFacturacionVentaDto resultado = new ResultadoFacturacionVentaDto();
		try {
			TimbradoResponse complemento = complementoPagoService.timbrarComplemento(idVenta, cveCfdi);
			TwVenta ventaActualizada = ventaRepository.findBynId(idVenta);
			TwFacturacion facturacion = ventaActualizada != null && ventaActualizada.getnIdFacturacion() != null
					? facturaRepository.findById(ventaActualizada.getnIdFacturacion()).orElse(null)
					: null;
			resultado.setSuccess(true);
			resultado.setMensaje("Complemento registrado");
			resultado.setUuidComplementoPago(complemento.getUuid());
			resultado.setUuidFacturaIngreso(facturacion != null ? facturacion.getsUuid() : null);
			resultado.setEstadoFacturacion(facturacion != null ? facturacion.getsEstado() : null);
			resultado.setEstadoComplemento(facturacion != null ? facturacion.getsEstadoComplemento() : "FACTURADA_CON_COMPLEMENTO_PAGO");
			resultado.setClasificacionFiscal(facturacion != null ? facturacion.getsClasificacionFiscal() : null);
			resultado.setMetodoPagoFiscal(facturacion != null ? facturacion.getsMetodoPagoFiscal() : null);
			resultado.setFormaPagoFiscal(facturacion != null ? facturacion.getsFormaPagoFiscal() : null);
			return resultado;
		} catch (Exception e) {
			logger.error("Error al timbrar complemento de pago para venta {} usando proveedor activo {}", idVenta,
					facturacionProperties != null ? facturacionProperties.getProveedorActivo() : null, e);
			resultado.setSuccess(false);
			resultado.setMensaje("Error al registrar complemento de pago");
			resultado.setCodigoError("REP_ERROR");
			resultado.setMensajeError(e.getMessage());
			return resultado;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoFacturacionVentaDto complementoPagoCliente(Long nIdPagoCliente) throws Exception {
		ResultadoFacturacionVentaDto resultado = new ResultadoFacturacionVentaDto();
		try {
			TimbradoResponse complemento = complementoPagoService.timbrarComplementoPagoCliente(nIdPagoCliente);
			resultado.setSuccess(true);
			resultado.setMensaje("Complemento de pago global registrado");
			resultado.setUuidComplementoPago(complemento.getUuid());
			resultado.setEstadoComplemento("FACTURADA_CON_COMPLEMENTO_PAGO");
			return resultado;
		} catch (Exception e) {
			logger.error("Error al timbrar complemento de pago global para pago cliente {} usando proveedor activo {}",
					nIdPagoCliente,
					facturacionProperties != null ? facturacionProperties.getProveedorActivo() : null, e);
			resultado.setSuccess(false);
			resultado.setMensaje("Error al registrar complemento de pago global");
			resultado.setCodigoError("REP_PAGO_CLIENTE_ERROR");
			resultado.setMensajeError(e.getMessage());
			return resultado;
		}
	}

	@Override
	public ResultadoFacturacionVentaDto reintentarComplemento(Long nIdComplemento) throws Exception {
		ResultadoFacturacionVentaDto resultado = new ResultadoFacturacionVentaDto();
		try {
			TimbradoResponse complemento = complementoPagoService.reintentarComplemento(nIdComplemento);
			resultado.setSuccess(true);
			resultado.setMensaje("Complemento reintentado correctamente");
			resultado.setUuidComplementoPago(complemento.getUuid());
			resultado.setEstadoComplemento("FACTURADA_CON_COMPLEMENTO_PAGO");
			return resultado;
		} catch (Exception e) {
			logger.error("Error al reintentar complemento {} usando proveedor activo {}", nIdComplemento,
					facturacionProperties != null ? facturacionProperties.getProveedorActivo() : null, e);
			resultado.setSuccess(false);
			resultado.setMensaje("Error al reintentar complemento de pago");
			resultado.setCodigoError("REP_RETRY_ERROR");
			resultado.setMensajeError(e.getMessage());
			return resultado;
		}
	}

	@Override
	public FacturaReenvioCorreoResponseDto reenviarFacturaCorreo(Long nIdVenta,
			FacturaReenvioCorreoRequestDto requestDto) throws Exception {
		FacturaReenvioCorreoResponseDto response = new FacturaReenvioCorreoResponseDto();
		response.setnIdVenta(nIdVenta);

		if (nIdVenta == null) {
			response.setEnviado(Boolean.FALSE);
			response.setDetalle("Debes indicar la venta a reenviar.");
			return response;
		}

		TwVenta venta = ventaRepository.findBynId(nIdVenta);
		if (venta == null) {
			response.setEnviado(Boolean.FALSE);
			response.setDetalle("La venta no existe.");
			return response;
		}

		if (venta.getnIdFacturacion() == null || venta.getnIdFacturacion().longValue() <= 0L) {
			response.setEnviado(Boolean.FALSE);
			response.setDetalle("La venta no tiene factura timbrada.");
			return response;
		}

		TcCliente cliente = venta.getTcCliente() != null ? venta.getTcCliente()
				: (venta.getnIdCliente() != null ? clientesRepository.findById(venta.getnIdCliente()).orElse(null) : null);

		boolean usarCorreoRegistrado = requestDto == null || requestDto.getUsarCorreoRegistrado() == null
				|| Boolean.TRUE.equals(requestDto.getUsarCorreoRegistrado());
		String correoDestino = usarCorreoRegistrado && cliente != null ? cliente.getsCorreo() : null;
		if (!usarCorreoRegistrado && requestDto != null) {
			correoDestino = requestDto.getCorreoDestino();
		}

		if (!mailSender.esCorreoValido(correoDestino)) {
			response.setEnviado(Boolean.FALSE);
			response.setCorreoDestino(correoDestino);
			response.setDetalle("Debes capturar un correo electrónico válido.");
			return response;
		}

		List<envioMail.AdjuntoCorreo> adjuntos = construirAdjuntosFactura(venta);
		if (adjuntos.isEmpty()) {
			response.setEnviado(Boolean.FALSE);
			response.setCorreoDestino(correoDestino);
			response.setDetalle("No se localizaron archivos de factura para reenviar.");
			return response;
		}

		envioMail.ResultadoEnvioCorreo resultado = correoClienteService.enviarCorreoDirectoConAdjuntos(correoDestino,
				"Factura_" + venta.getnId(),
				"<p>Adjuntamos los documentos fiscales de la venta <strong>#" + venta.getnId()
						+ "</strong>.</p><p>Se incluyen los archivos PDF/XML disponibles y, cuando aplica, sus parciales en un ZIP.</p>",
				adjuntos);

		response.setCorreoDestino(correoDestino != null ? correoDestino.trim() : null);
		response.setEnviado(Boolean.valueOf(resultado != null && resultado.isEnviado()));
		response.setDetalle(resultado != null ? resultado.getDetalle() : "No fue posible reenviar la factura.");
		return response;
	}

	private List<envioMail.AdjuntoCorreo> construirAdjuntosFactura(TwVenta venta) {
		List<envioMail.AdjuntoCorreo> adjuntos = new ArrayList<envioMail.AdjuntoCorreo>();
		if (venta == null || venta.getnId() == null) {
			return adjuntos;
		}

		TcDatosFactura datosFactura = venta.getTcCliente() != null && venta.getTcCliente().getnIdDatoFactura() != null
				? tcDatosFacturaRepository.obtenerDatos(venta.getTcCliente().getnIdDatoFactura())
				: null;

		String rutaRaiz = datosFacturaStorageResolver.resolveRutaRaiz(datosFactura);
		byte[] zipFacturas = envioMail.crearZipFacturasRelacionadas(rutaRaiz, String.valueOf(venta.getnId()));
		if (zipFacturas != null && zipFacturas.length > 0) {
			adjuntos.add(new envioMail.AdjuntoCorreo("factura_venta_" + venta.getnId() + ".zip", "application/zip",
					zipFacturas));
			return adjuntos;
		}

		byte[] pdf = generaReporteService.getDocumento(venta.getnId(), com.refacFabela.enums.TipoDoc.PDF_FACTURA);
		if (pdf != null && pdf.length > 0) {
			adjuntos.add(new envioMail.AdjuntoCorreo("factura_" + venta.getnId() + ".pdf", "application/pdf", pdf));
		}

		byte[] xml = generaReporteService.getDocumento(venta.getnId(), com.refacFabela.enums.TipoDoc.XML_FACTURA);
		if (xml != null && xml.length > 0) {
			adjuntos.add(new envioMail.AdjuntoCorreo("factura_" + venta.getnId() + ".xml", "application/xml", xml));
		}

		return adjuntos;
	}

	@Override
	public TwFacturacion guardar(TwFacturacion twFacturacion) {
		return facturaRepository.save(twFacturacion);
	}

	@Override
	public int consultaCreditos(Long nDatoFactura) {
		try {
			TcDatosFactura tcDatosFactura = tcDatosFacturaRepository.obtenerDatos(nDatoFactura);
			if (tcDatosFactura == null || tcDatosFactura.getsRfcEmisor() == null
					|| tcDatosFactura.getsRfcEmisor().trim().isEmpty()) {
				return 0;
			}
			return pacFacturacionClient.consultarCreditosDisponibles(tcDatosFactura.getsRfcEmisor());
		} catch (PacFacturacionClientException e) {
			logger.warn("No fue posible consultar crÃ©ditos FacturoPorTi para nDatoFactura {}: {}", nDatoFactura, e.getMessage());
			return 0;
		} catch (Exception e) {
			logger.error("Error al consultar crÃ©ditos FacturoPorTi para nDatoFactura {}", nDatoFactura, e);
			return 0;
		}
	}

	@Override
	public StatusCfdiResponse consultarEstatusCfdi(Long nIdVenta) throws Exception {
		return consultaFacturacionService.consultarEstatusCfdi(nIdVenta);
	}

	@Override
	public CfdiRelacionadosResponse consultarCfdiRelacionados(Long nIdVenta) throws Exception {
		return consultaFacturacionService.consultarCfdiRelacionados(nIdVenta);
	}

	@Override
	public List<SolicitudCancelacionDto> consultarSolicitudesPendientesCancelacion(Long nIdDatoFactura) throws Exception {
		return consultaFacturacionService.consultarSolicitudesPendientesCancelacion(nIdDatoFactura);
	}

	@Override
	public CancelacionResponse aceptarSolicitudCancelacion(SolicitudCancelacionAccionDto solicitudCancelacionAccionDto) throws Exception {
		return consultaFacturacionService.aceptarSolicitudCancelacion(solicitudCancelacionAccionDto);
	}

	@Override
	public CancelacionResponse rechazarSolicitudCancelacion(SolicitudCancelacionAccionDto solicitudCancelacionAccionDto) throws Exception {
		return consultaFacturacionService.rechazarSolicitudCancelacion(solicitudCancelacionAccionDto);
	}

	@Override
	public List<ComplementoPagoHistorialDto> consultarComplementosPago(Long nIdVenta) throws Exception {
		return complementoPagoService.consultarComplementosPago(nIdVenta);
	}

	@Override
	public SubirFacturaDto subirArchivo(MultipartFile file, MultipartFile fileXml, String venta, String uuid) throws Exception {
		try {
			subirArchivo cargarArchivo = new subirArchivo();
			SubirFacturaDto subirFacturaDto = new SubirFacturaDto();
			Long idVenta = Long.parseLong(venta);
			TwVenta twVenta = ventaRepository.findBynId(idVenta);

			if (twVenta == null || twVenta.getTcCliente() == null || twVenta.getTcCliente().getnIdDatoFactura() == null) {
				subirFacturaDto.setMensaje("No se encontrÃ³ configuraciÃ³n fiscal para la venta");
				return subirFacturaDto;
			}

			TcDatosFactura tcDatosFactura = tcDatosFacturaRepository.obtenerDatos(twVenta.getTcCliente().getnIdDatoFactura());
			if (tcDatosFactura == null) {
				subirFacturaDto.setMensaje("No se encontrÃ³ configuraciÃ³n fiscal para la venta");
				return subirFacturaDto;
			}

			boolean pdfGuardado = cargarArchivo.subirArchivoFactura(file, Integer.valueOf(venta),
					datosFacturaStorageResolver.resolveRutaPdf(tcDatosFactura));
			boolean xmlGuardado = cargarArchivo.subirArchivoFactura(fileXml, Integer.valueOf(venta),
					datosFacturaStorageResolver.resolveRutaXml(tcDatosFactura));
			if (!pdfGuardado || !xmlGuardado) {
				subirFacturaDto.setMensaje("No se guardaron los documentos");
				return subirFacturaDto;
			}

			TwFacturacion twFacturacion = new TwFacturacion();
			twFacturacion.setsUuid(uuid);
			twFacturacion.setnEstatus(1);
			twFacturacion.setnIdDatoFactura(tcDatosFactura.getnId());
			twFacturacion.setN_idVenta(idVenta);
			twFacturacion = facturaRepository.save(twFacturacion);

			twVenta.setnIdFacturacion(twFacturacion.getnId());
			ventaRepository.save(twVenta);

			subirFacturaDto.setMensaje("Se guardaron los documentos");
			subirFacturaDto.setnIdVenta(idVenta);
			subirFacturaDto.setSuuid(uuid);
			subirFacturaDto.setEstatus(Boolean.TRUE);
			return subirFacturaDto;
		} catch (Exception e) {
			logger.error("Error al subir documentos CFDI para venta {}", venta, e);
			throw new Exception(e.getMessage(), e);
		}
	}
}


