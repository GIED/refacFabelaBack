package com.refacFabela.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.refacFabela.model.TcClavesat;
import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TvVentaDetalle;
import com.refacFabela.model.TwVenta;
import com.refacFabela.service.VentasService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class VentasController {
	private static final Logger logger = LogManager.getLogger("errorLogger");
	
	@Autowired
	VentasService ventasService;

	@GetMapping("/obtenerVentas")
	public List<TwVenta> consultaVentas() {
		try {

			return ventasService.consltaVentas();

		} catch (Exception e) {

			logger.error("Error al obtener todas las ventas " + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerVentasClienteDetalleEstatus") 
	public List<TvVentaDetalle> obtenerVentasClientesDetalleEstatus(@RequestParam() Long nIdCliente, long nTipoPago) {

		try {
			return ventasService.consultaVentaDetalle(nIdCliente, nTipoPago);
		} catch (Exception e) {

			logger.error("Error al obtener los Productos" + e);
		}
		return null;
	}
	
	

}
