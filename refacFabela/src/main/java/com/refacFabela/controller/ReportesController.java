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
	
	@GetMapping(value = "/getSaldoFavor")
	public @ResponseBody byte[] getSaldoFavor(HttpServletResponse response, @RequestParam(required = false) Long nIdVenta) {
		
		// genera el pdf con la venta guardada
		try {
			return generaReporteService.getSaldoFavorPDF(nIdVenta);
		} catch (Exception e) {
			logger.error("Error al generar el saldo favor ", e);
			return null;
		}
	}
	
	@GetMapping(value = "/getVentaAlmacen")
	public @ResponseBody byte[] getVentaAlmacen(HttpServletResponse response, @RequestParam(required = false) Long nIdVenta) {
		
		// genera el pdf con la venta guardada
		try {
			return generaReporteService.getVentaAlmacenPDF(nIdVenta);
		} catch (Exception e) {
			logger.error("Error al generar la venta almacen ", e);
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
	@GetMapping(value = "/getPedidoId")
	public @ResponseBody byte[] getPedidoId(HttpServletResponse response, @RequestParam(required = false) Long nIdPedido) {
		
		// genera el pdf con la venta guardada
		try {
			return generaReporteService.getPedidoIdPDF(nIdPedido);
		} catch (Exception e) {
			logger.error("Error al generar el pdf del pedido ", e);
			return null;
		}
	}
	
	@GetMapping(value = "/getReporteCaja")
	public @ResponseBody byte[] getReporteCaja(HttpServletResponse response, @RequestParam(required = false) Long nIdCaja) {
		
		// genera el pdf con la venta guardada
		try {
			return generaReporteService.getReporteCaja(nIdCaja);
		} catch (Exception e) {
			logger.error("Error al generar el pdf del reporte de la caja ", e);
			return null;
		}
	}
	
	@GetMapping(value = "/getReporteInventario")
	public @ResponseBody byte[] getReporteIntario(HttpServletResponse response, @RequestParam(required = false) Long nIdBodega, @RequestParam(required = false) Long nIdAnaquel, @RequestParam(required = false) Long nIdNivel) {
		
		// genera el pdf con la venta guardada
		try {
			return generaReporteService.getReporteInventario(nIdBodega,nIdAnaquel, nIdNivel);
		} catch (Exception e) {
			logger.error("Error al generar el pdf del reporte de la caja ", e);
			return null;
		}
	}
	
	


}
