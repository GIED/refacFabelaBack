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
import com.refacFabela.dto.SolicitudCancelacionDto;
import com.refacFabela.dto.StatusCfdiResponse;
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
	public String venta(Long idVenta, String cveCfdi) throws Exception {
		try {
			timbradoVentaService.timbrarVenta(idVenta, cveCfdi);
			TwVenta ventaActualizada = ventaRepository.findBynId(idVenta);
			TcCliente clienteActualizado = null;
			if (ventaActualizada != null && ventaActualizada.getnIdCliente() != null) {
				clienteActualizado = clientesRepository.findById(ventaActualizada.getnIdCliente()).orElse(null);
			}
			if (clienteActualizado != null && Boolean.TRUE.equals(clienteActualizado.getnCorreoBloqueado())) {
				return "ok_correo_bloqueado";
			}
			return "ok";
		} catch (Exception e) {
			logger.error("Error al facturar venta {} con cveCfdi {} usando proveedor activo {}", idVenta, cveCfdi,
					facturacionProperties != null ? facturacionProperties.getProveedorActivo() : null, e);
			return "Error al facturar";
		}
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
	public String complemento(Long idVenta, String cveCfdi) throws Exception {
		try {
			complementoPagoService.timbrarComplemento(idVenta, cveCfdi);
			return "ok";
		} catch (Exception e) {
			logger.error("Error al timbrar complemento de pago para venta {} usando proveedor activo {}", idVenta,
					facturacionProperties != null ? facturacionProperties.getProveedorActivo() : null, e);
			return "Error al facturar";
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

