package com.refacFabela.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.refacFabela.dto.CancelacionFacturaDto;
import com.refacFabela.dto.SolicitudCancelacionAccionDto;
import com.refacFabela.dto.SubirFacturaDto;
import com.refacFabela.config.FacturacionProperties;
import com.refacFabela.dto.CancelacionResponse;
import com.refacFabela.dto.CfdiRelacionadosResponse;
import com.refacFabela.dto.ComplementoPagoHistorialDto;
import com.refacFabela.dto.FacturacionVentasRequestDto;
import com.refacFabela.dto.ResultadoFacturacionVentaDto;
import com.refacFabela.dto.SolicitudCancelacionDto;
import com.refacFabela.dto.StatusCfdiResponse;
import com.refacFabela.dto.TimbradoResponse;
import com.refacFabela.exception.PacFacturacionClientException;
import com.refacFabela.service.PacFacturacionClient;
import com.refacFabela.service.impl.ComplementoPagoService;
import com.refacFabela.service.impl.CancelacionFacturacionService;
import com.refacFabela.service.impl.ConsultaFacturacionService;
import com.refacFabela.service.impl.TimbradoVentaService;
import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TcDatosFactura;
import com.refacFabela.model.TwFacturacion;
import com.refacFabela.model.TwVenta;
import com.refacFabela.repository.ClientesRepository;
import com.refacFabela.repository.FacturaRepository;
import com.refacFabela.repository.TcDatosFacturaRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.service.FacturacionService;
import com.refacFabela.utils.subirArchivo;

import java.util.List;

@Service
public class FacturacionServiceImpl implements FacturacionService {

	private static final Logger logger = LogManager.getLogger("errorLogger");

	private final VentasRepository ventaRepository;
	private final ClientesRepository clientesRepository;
	private final FacturaRepository facturaRepository;
	private final TcDatosFacturaRepository tcDatosFacturaRepository;
	private final FacturacionProperties facturacionProperties;
	private final TimbradoVentaService timbradoVentaService;
	private final CancelacionFacturacionService cancelacionFacturacionService;
	private final ComplementoPagoService complementoPagoService;
	private final ConsultaFacturacionService consultaFacturacionService;
	private final PacFacturacionClient pacFacturacionClient;
	private final DatosFacturaStorageResolver datosFacturaStorageResolver;

	public FacturacionServiceImpl(VentasRepository ventaRepository,
			ClientesRepository clientesRepository,
			FacturaRepository facturaRepository,
			TcDatosFacturaRepository tcDatosFacturaRepository,
			FacturacionProperties facturacionProperties,
			TimbradoVentaService timbradoVentaService,
			CancelacionFacturacionService cancelacionFacturacionService,
			ComplementoPagoService complementoPagoService,
			ConsultaFacturacionService consultaFacturacionService,
			PacFacturacionClient pacFacturacionClient,
			DatosFacturaStorageResolver datosFacturaStorageResolver) {
		this.ventaRepository = ventaRepository;
		this.clientesRepository = clientesRepository;
		this.facturaRepository = facturaRepository;
		this.tcDatosFacturaRepository = tcDatosFacturaRepository;
		this.facturacionProperties = facturacionProperties;
		this.timbradoVentaService = timbradoVentaService;
		this.cancelacionFacturacionService = cancelacionFacturacionService;
		this.complementoPagoService = complementoPagoService;
		this.consultaFacturacionService = consultaFacturacionService;
		this.pacFacturacionClient = pacFacturacionClient;
		this.datosFacturaStorageResolver = datosFacturaStorageResolver;
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
					resultado.setMensaje("Factura generada correctamente; el complemento de pago quedó pendiente.");
				}
			} else {
				boolean repCanonicoProcesado = false;
				if (esFacturaPpd99(timbradoIngreso)) {
					try {
						java.util.List<TimbradoResponse> complementosCanonicos = complementoPagoService
								.timbrarComplementosPagoClientePendientesVentas(java.util.Collections.singletonList(idVenta));
						if (complementosCanonicos != null && !complementosCanonicos.isEmpty()) {
							TimbradoResponse ultimoComplemento = complementosCanonicos.get(complementosCanonicos.size() - 1);
							resultado.setUuidComplementoPago(ultimoComplemento.getUuid());
							resultado.setEstadoComplemento("FACTURADA_CON_COMPLEMENTO_PAGO");
							resultado.setMensaje("Venta facturada y complemento(s) de pago canónico generado(s) correctamente.");
							repCanonicoProcesado = true;
						}
					} catch (Exception complementoCanonicoError) {
						logger.error("Venta facturada pero el REP del pago global quedó pendiente para venta {}", idVenta,
								complementoCanonicoError);
						resultado.setEstadoComplemento("PENDIENTE_COMPLEMENTO_PAGO");
						resultado.setCodigoError("REP_PAGO_CLIENTE_ERROR");
						resultado.setMensajeError(complementoCanonicoError.getMessage());
						resultado.setMensaje("Venta facturada correctamente; el REP del pago global quedó pendiente.");
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
				resultado.setAvisoCorreo("La factura se generó correctamente pero el correo del cliente está bloqueado. No se envió la notificación por correo.");
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
						resultado.setMensaje("Factura consolidada y complemento(s) de pago canónico generado(s) correctamente.");
						repCanonicoProcesado = true;
					}
				} catch (Exception complementoCanonicoError) {
					logger.error("Factura consolidada generada pero el REP del pago global quedó pendiente para ventas {}",
							requestDto != null ? requestDto.getnIdsVenta() : null, complementoCanonicoError);
					resultado.setEstadoComplemento("PENDIENTE_COMPLEMENTO_PAGO");
					resultado.setCodigoError("REP_PAGO_CLIENTE_ERROR");
					resultado.setMensajeError(complementoCanonicoError.getMessage());
					resultado.setMensaje("Factura consolidada generada correctamente; el REP del pago global quedó pendiente.");
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
			return "Error al facturar";
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
			logger.warn("No fue posible consultar créditos FacturoPorTi para nDatoFactura {}: {}", nDatoFactura, e.getMessage());
			return 0;
		} catch (Exception e) {
			logger.error("Error al consultar créditos FacturoPorTi para nDatoFactura {}", nDatoFactura, e);
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
				subirFacturaDto.setMensaje("No se encontró configuración fiscal para la venta");
				return subirFacturaDto;
			}

			TcDatosFactura tcDatosFactura = tcDatosFacturaRepository.obtenerDatos(twVenta.getTcCliente().getnIdDatoFactura());
			if (tcDatosFactura == null) {
				subirFacturaDto.setMensaje("No se encontró configuración fiscal para la venta");
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

