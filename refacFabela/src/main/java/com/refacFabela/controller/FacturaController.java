package com.refacFabela.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.refacFabela.dto.CancelacionFacturaDto;
import com.refacFabela.dto.SolicitudCancelacionAccionDto;
import com.refacFabela.dto.SubirFacturaDto;
import com.refacFabela.enums.TipoDoc;
import com.refacFabela.dto.CancelacionResponse;
import com.refacFabela.dto.CfdiRelacionadosResponse;
import com.refacFabela.dto.ComplementoPagoHistorialDto;
import com.refacFabela.dto.FacturaReenvioCorreoRequestDto;
import com.refacFabela.dto.FacturaReenvioCorreoResponseDto;
import com.refacFabela.dto.FacturacionVentasRequestDto;
import com.refacFabela.dto.FacturacionVentaDivididaRequestDto;
import com.refacFabela.dto.ResultadoFacturacionVentaDto;
import com.refacFabela.dto.SolicitudCancelacionDto;
import com.refacFabela.dto.StatusCfdiResponse;
import com.refacFabela.model.TvVentasFactura;
import com.refacFabela.service.FacturacionService;
import com.refacFabela.service.GeneraReporteService;
import com.refacFabela.service.VentasService;

import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/facturacion/")
public class FacturaController {
	
	private static final Logger logger = LogManager.getLogger("errorLogger");
	
	@Autowired
	private FacturacionService facturaService;
	
	@Autowired
	private VentasService ventasService;
	
	@Autowired
	private GeneraReporteService  generaReporteService;
	
	
	@PostMapping("/upload")
	public ResponseEntity<SubirFacturaDto> subirFactura(@RequestParam("file") MultipartFile file, @RequestParam("fileXml") MultipartFile fileXml,  @RequestParam("venta") String venta,  @RequestParam("uuid") String uuid  ) throws Exception{
		System.err.println(venta);
		System.err.println(uuid);
		
		return new ResponseEntity<SubirFacturaDto>(facturaService.subirArchivo(file, fileXml, venta, uuid), HttpStatus.OK);
	}
	 
	
	@GetMapping("venta")
	public ResponseEntity<?> venta(@RequestParam(required = false) Long nIdVenta , String cveCfdi) throws Exception {
		ResultadoFacturacionVentaDto resultado = facturaService.venta(nIdVenta, cveCfdi);
		return new ResponseEntity<ResultadoFacturacionVentaDto>(resultado,
				resultado != null && resultado.isSuccess() ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("venta/dividida")
	public ResponseEntity<?> ventaDividida(@RequestBody FacturacionVentaDivididaRequestDto requestDto) throws Exception {
		ResultadoFacturacionVentaDto resultado = facturaService.ventaDividida(requestDto);
		return new ResponseEntity<ResultadoFacturacionVentaDto>(resultado,
				resultado != null && resultado.isSuccess() ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("ventas/consolidada")
	public ResponseEntity<?> ventaConsolidada(@RequestBody FacturacionVentasRequestDto requestDto) throws Exception {
		ResultadoFacturacionVentaDto resultado = facturaService.ventaConsolidada(requestDto);
		return new ResponseEntity<ResultadoFacturacionVentaDto>(resultado,
				resultado != null && resultado.isSuccess() ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("cancelaFactura")
	public ResponseEntity<?> cancelaFactura(@RequestParam(required = false) Long nIdVenta , String cveCfdi) throws Exception {
		
		Map<String, Object> response = new HashMap();
		
		if (facturaService.cancelaFactura(nIdVenta, cveCfdi).equals("ok")) {
			
			response.put("mensaje", "Facturacancelada");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
			
		}else {		
			
			response.put("mensaje", "error al facturar la venta");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("cancelar")
	public ResponseEntity<?> cancelar(@RequestBody CancelacionFacturaDto cancelacionFacturaDto) throws Exception {
		Map<String, Object> response = new HashMap();
		String resultado = facturaService.cancelaFactura(cancelacionFacturaDto);
		if ("ok".equals(resultado)) {
			response.put("mensaje", "Factura cancelada");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}
		response.put("mensaje", resultado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("complemento")
	public ResponseEntity<?> complemento(@RequestParam(required = false) Long nIdVenta , String cveCfdi) throws Exception {
		ResultadoFacturacionVentaDto resultado = facturaService.complemento(nIdVenta, cveCfdi);
		return new ResponseEntity<ResultadoFacturacionVentaDto>(resultado,
				resultado != null && resultado.isSuccess() ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("complemento/pago-cliente")
	public ResponseEntity<?> complementoPagoCliente(@RequestParam(required = true) Long nIdPagoCliente) throws Exception {
		ResultadoFacturacionVentaDto resultado = facturaService.complementoPagoCliente(nIdPagoCliente);
		return new ResponseEntity<ResultadoFacturacionVentaDto>(resultado,
				resultado != null && resultado.isSuccess() ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("complementos/reintentar")
	public ResponseEntity<?> reintentarComplemento(@RequestParam(required = true) Long nIdComplemento) throws Exception {
		ResultadoFacturacionVentaDto resultado = facturaService.reintentarComplemento(nIdComplemento);
		return new ResponseEntity<ResultadoFacturacionVentaDto>(resultado,
				resultado != null && resultado.isSuccess() ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("ventas/{nIdVenta}/reenviar-correo")
	public ResponseEntity<FacturaReenvioCorreoResponseDto> reenviarFacturaCorreo(
			@PathVariable("nIdVenta") Long nIdVenta,
			@RequestBody(required = false) FacturaReenvioCorreoRequestDto requestDto) {
		try {
			FacturaReenvioCorreoResponseDto response = facturaService.reenviarFacturaCorreo(nIdVenta, requestDto);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("Error al reenviar factura por correo para venta {}", nIdVenta, e);
			FacturaReenvioCorreoResponseDto response = new FacturaReenvioCorreoResponseDto();
			response.setnIdVenta(nIdVenta);
			response.setEnviado(Boolean.FALSE);
			response.setDetalle(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@GetMapping("ventasParaFactura")
	public ResponseEntity<List<TvVentasFactura>> consultaVentasFactura(){
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.ventasService.consultaVentasParaFactura());
	}
	
	@GetMapping("ventasFacturadas")
	public ResponseEntity<List<TvVentasFactura>> consultaVentasFacturadas(
			@RequestParam(required = false) String periodo,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
			@RequestParam(required = false) String estatus,
			@RequestParam(required = false) String buscar){
		
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(this.ventasService.consultaVentasFacturadas(periodo, fechaInicio, fechaFin, estatus, buscar));
	}

	@GetMapping("complementos")
	public ResponseEntity<List<ComplementoPagoHistorialDto>> consultarComplementos(@RequestParam(required = true) Long nIdVenta) {
		try {
			return ResponseEntity.ok(facturaService.consultarComplementosPago(nIdVenta));
		} catch (Exception e) {
			logger.error("Error al consultar complementos de pago para venta {}", nIdVenta, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	
	
	@GetMapping(value = "getDocumento")
	public @ResponseBody byte[] getDocumento(HttpServletResponse response, @RequestParam(required = false) Long nIdVenta, @RequestParam(required = false) TipoDoc TipoDoc) {
		
		// Descargar Comprobantes
		try {
			byte[] documento = generaReporteService.getDocumento(nIdVenta, TipoDoc );
			if (documento == null || documento.length == 0) {
				logger.warn("Documento no encontrado para venta {} tipo {}", nIdVenta, TipoDoc);
			}
			return documento;
		} catch (Exception e) {
			logger.error("Error al descargar documento para venta {} tipo {}", nIdVenta, TipoDoc, e);
			return null;
		}
	}

	@GetMapping(value = "getDocumentoComplemento")
	public @ResponseBody byte[] getDocumentoComplemento(HttpServletResponse response,
			@RequestParam(required = true) Long nIdComplemento,
			@RequestParam(required = true) TipoDoc TipoDoc) {
		try {
			return generaReporteService.getDocumentoComplemento(nIdComplemento, TipoDoc);
		} catch (Exception e) {
			logger.error("Error al desargar documento de complemento", e);
			return null;
		}
	}
	
	@GetMapping(value = "consultaCreditos")
	public int consultaCreditos(@RequestParam(required = true) Long nDatoFactura) {
		try {
			return this.facturaService.consultaCreditos(nDatoFactura);
		} catch (Exception e) {
			logger.error("Error al consultar creditos ", e);
			return 0;
		}
	}

	@GetMapping(value = "estatusSat")
	public ResponseEntity<StatusCfdiResponse> consultarEstatusCfdi(@RequestParam(required = true) Long nIdVenta) {
		try {
			return ResponseEntity.ok(facturaService.consultarEstatusCfdi(nIdVenta));
		} catch (Exception e) {
			logger.error("Error al consultar estatus SAT para venta {}", nIdVenta, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping(value = "cfdiRelacionados")
	public ResponseEntity<CfdiRelacionadosResponse> consultarCfdiRelacionados(@RequestParam(required = true) Long nIdVenta) {
		try {
			return ResponseEntity.ok(facturaService.consultarCfdiRelacionados(nIdVenta));
		} catch (Exception e) {
			logger.error("Error al consultar CFDI relacionados para venta {}", nIdVenta, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping(value = "solicitudesPendientes")
	public ResponseEntity<List<SolicitudCancelacionDto>> consultarSolicitudesPendientes(@RequestParam(required = true) Long nIdDatoFactura) {
		try {
			return ResponseEntity.ok(facturaService.consultarSolicitudesPendientesCancelacion(nIdDatoFactura));
		} catch (Exception e) {
			logger.error("Error al consultar solicitudes pendientes para razón social {}", nIdDatoFactura, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping(value = "solicitudesPendientes/aceptar")
	public ResponseEntity<CancelacionResponse> aceptarSolicitudPendiente(@RequestBody SolicitudCancelacionAccionDto solicitudCancelacionAccionDto) {
		try {
			return ResponseEntity.ok(facturaService.aceptarSolicitudCancelacion(solicitudCancelacionAccionDto));
		} catch (Exception e) {
			logger.error("Error al aceptar solicitud pendiente para UUID {}", solicitudCancelacionAccionDto != null ? solicitudCancelacionAccionDto.getUuid() : null, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping(value = "solicitudesPendientes/rechazar")
	public ResponseEntity<CancelacionResponse> rechazarSolicitudPendiente(@RequestBody SolicitudCancelacionAccionDto solicitudCancelacionAccionDto) {
		try {
			return ResponseEntity.ok(facturaService.rechazarSolicitudCancelacion(solicitudCancelacionAccionDto));
		} catch (Exception e) {
			logger.error("Error al rechazar solicitud pendiente para UUID {}", solicitudCancelacionAccionDto != null ? solicitudCancelacionAccionDto.getUuid() : null, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	
	

}

