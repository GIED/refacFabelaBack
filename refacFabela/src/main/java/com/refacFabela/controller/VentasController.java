package com.refacFabela.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.refacFabela.dto.VentaDto;
import com.refacFabela.dto.VentaProductoDto;
import com.refacFabela.model.TvVentaDetalle;
import com.refacFabela.model.TvVentaProductoMes;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwVenta;
import com.refacFabela.service.ProductosService;
import com.refacFabela.service.VentasService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class VentasController {
	private static final Logger logger = LogManager.getLogger("errorLogger");
	
	@Autowired
	private VentasService ventasService;
	@Autowired
	private ProductosService productosService;

	@GetMapping("/obtenerVentas")
	public List<TwVenta> consultaVentas() {
		try {

			return ventasService.consltaVentas();

		} catch (Exception e) {

			logger.error("Error al obtener todas las ventas " + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerVentasDetalle")
	public List<TvVentaDetalle> consultaVentasDetalle() {
		try {
			return ventasService.consultaVentaDetalle();

		} catch (Exception e) {

			logger.error("Error al obtener todas las ventas " + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerVentasClienteDetalleEstatus") 
	public List<TvVentaDetalle> obtenerVentasClientesDetalleEstatus(@RequestParam() Long nIdCliente, long nTipoPago) {

		try {
			return ventasService.consultaVentaDetalleId(nIdCliente, nTipoPago);
		} catch (Exception e) {

			logger.error("Error al obtener los Productos" + e);
		}
		return null;
	}
	@GetMapping("/obtenerAbonosVentaId") 
	public List<TwAbono> obtenerAbonosVentaId(@RequestParam() Long nId) {

		try {
			return ventasService.consultaAbonoVentaId(nId);
		} catch (Exception e) {

			logger.error("Error al obtener los Productos" + e);
		}
		return null;
	}
	
	@PostMapping("/guardarVenta")
	public String guardarVenta(@RequestBody VentaDto ventaDto) {

		try {		
			ventasService.guardarVenta(ventaDto);
			
			return "registrado";
		} catch (Exception e) {

			logger.error("Error al guardar la venta" + e);
		}
		return null;
	}
	@GetMapping("/consultaProductoVentaId")
	public List<VentaProductoDto> obtenerProductoVendidosIdVenta(@RequestParam() Long id) {

		try {
			return productosService.obtenerProductosVentaId(id);
		} catch (Exception e) {

			logger.error("Error al obtener el stock de bodegas" + e);
		}
		return null;
	}
	@GetMapping("/consultaProductoVentaMesId")
	public List<TvVentaProductoMes> obtenerProductoVendidosMesIdVenta(@RequestParam() Long id) {

		try {
			return productosService.obtenerProductoVentaMesId(id);
		} catch (Exception e) {

			logger.error("Error al obtener los productos vendidos por mes" + e);
		}
		return null;
	}
	
	

}