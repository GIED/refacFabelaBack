package com.refacFabela.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.refacFabela.service.GeneraReporteService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class ReportesController {
	
	private static final Logger logger = LogManager.getLogger("errorLogger");
	
	@Autowired
	private GeneraReporteService generaReporteService;
	
	@GetMapping(value = "/getCotizacion")
	public @ResponseBody byte[] getCotizacion(HttpServletResponse response, @RequestParam(required = false) Long nIdCotizacion) {

		// genera el pdf con la cotizacion guardada
		try {
			return generaReporteService.getCotizacionPDF(nIdCotizacion);
		} catch (Exception e) {
			logger.error("Error al generar la cotizacion ", e);
			return null;
		}
	}
	
	@GetMapping(value = "/getVenta")
	public @ResponseBody byte[] getVenta(HttpServletResponse response, @RequestParam(required = false) Long nIdVenta) {
		
		// genera el pdf con la venta guardada
		try {
			return generaReporteService.getVentaPDF(nIdVenta);
		} catch (Exception e) {
			logger.error("Error al generar la venta ", e);
			return null;
		}
	}
	
	@GetMapping(value = "/getVentaPedido")
	public @ResponseBody byte[] getVentaPedido(HttpServletResponse response, @RequestParam(required = false) Long nIdVentaPedido) {
		
		// genera el pdf con la venta guardada
		try {
			return generaReporteService.getVentaPedidoPDF(nIdVentaPedido);
		} catch (Exception e) {
			logger.error("Error al generar la venta ", e);
			return null;
		}
	}
	
	@GetMapping(value = "/getAbonoVentaId")
	public @ResponseBody byte[] getAbonoVentaId(HttpServletResponse response, @RequestParam(required = false) Long nIdVenta) {
		
		// genera el pdf con la venta guardada
		try {
			return generaReporteService.getAbonoVentaIdPDF(nIdVenta);
		} catch (Exception e) {
			logger.error("Error al generar la venta ", e);
			return null;
		}
	}
	
	@GetMapping(value = "/getAbonoVentaIdCliente")
	public @ResponseBody byte[] getAbonoVentaIdCliente(HttpServletResponse response, @RequestParam(required = false) Long nIdCliente) {
		
		// genera el pdf con la venta guardada
		try {
			return generaReporteService.getAbonoVentaIdClientePDF(nIdCliente);
		} catch (Exception e) {
			logger.error("Error al generar la venta ", e);
			return null;
		}
	}
	


}
