package com.refacFabela.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.refacFabela.enums.TipoDoc;
import com.refacFabela.model.TvVentasFactura;
import com.refacFabela.service.FacturacionService;
import com.refacFabela.service.GeneraReporteService;
import com.refacFabela.service.VentasService;

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
	

	
	@GetMapping("venta")
	public ResponseEntity<?> venta(@RequestParam(required = false) Long nIdVenta , String cveCfdi) throws Exception {
		
		Map<String, Object> response = new HashMap();
		
		if (facturaService.venta(nIdVenta, cveCfdi).equals("ok")) {
			
			response.put("mensaje", "Venta Facturada");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
			
		}else {		
			
			response.put("mensaje", "error al facturar la venta");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("ventasParaFactura")
	public ResponseEntity<List<TvVentasFactura>> consultaVentasFactura(){
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.ventasService.consultaVentasParaFactura());
	}
	
	@GetMapping("ventasFacturadas")
	public ResponseEntity<List<TvVentasFactura>> consultaVentasFacturadas(){
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.ventasService.consultaVentasFacturadas());
	}
	
	
	
	@GetMapping(value = "getDocumento")
	public @ResponseBody byte[] getDocumento(HttpServletResponse response, @RequestParam(required = false) Long nIdVenta, @RequestParam(required = false) TipoDoc TipoDoc) {
		
		// Descargar Comprobantes
		try {
			System.err.println(TipoDoc);
			return generaReporteService.getDocumento(nIdVenta, TipoDoc );
		} catch (Exception e) {
			logger.error("Error al desargar documento ", e);
			return null;
		}
	}
	
	@GetMapping(value = "consultaCreditos")
	public int consultaCreditos() {
		
		try {
			
			return this.facturaService.consultaCreditos();
		} catch (Exception e) {
			logger.error("Error al consultar creditos ", e);
			return 0;
		}
	}
	
	
	

}
