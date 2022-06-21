package com.refacFabela.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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

import com.refacFabela.model.TvVentasFactura;
import com.refacFabela.service.FacturacionService;
import com.refacFabela.service.VentasService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/facturacion/")
public class FacturaController {
	
	@Autowired
	private FacturacionService facturaService;
	
	@Autowired
	private VentasService ventasService;
	

	
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
	
	

}
