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

import com.refacFabela.dto.CotizacionDto;
import com.refacFabela.dto.TvStockProductoDto;
import com.refacFabela.model.TwCotizaciones;
import com.refacFabela.model.TwCotizacionesDetalle;
import com.refacFabela.model.TwCotizacionesProducto;
import com.refacFabela.service.CotizacionService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class CotizacionController {
	
	@Autowired
	private CotizacionService cotizacionService;
	
	private static final Logger logger = LogManager.getLogger("errorLogger");
	
	@PostMapping("/guardarCotizacion")
	public TwCotizaciones guardarCotizacion(@RequestBody List<CotizacionDto> listaCotizacion) {

		try {
			
			return cotizacionService.guardaCorizacion(listaCotizacion);
		} catch (Exception e) {

			logger.error("Error al guardar la cotización" + e);
		}
		return null;
	}
	@GetMapping("/consultaCotizaciones")
	public List<TwCotizacionesDetalle> consultaCotizaciones() {

		try {
			
			return cotizacionService.consultaCotizaciones();
		} catch (Exception e) {

			logger.error("Error al consultar las cotizaciones" + e);
		}
		return null;
	}
	
	@GetMapping("/consultaCotizacionesLike")
	public List<TwCotizacionesDetalle> consultaCotizacionesLike(@RequestParam String buscar) {

		try {
		
			return cotizacionService.consultaCotizacionesLike(buscar);
		} catch (Exception e) {

			logger.error("Error al consultar las cotizaciones de la palabra clabe" + e);
		}
		return null;
	}
	
	@GetMapping("/consultaCotizacionId")
	public List<TvStockProductoDto> consultaCotizacionId(@RequestParam Long id) {

		try {
			
			return cotizacionService.consultaCotizacionId(id);
		} catch (Exception e) {

			logger.error("Error al guardar la cotización" + e);
		}
		return null;
	}

	@GetMapping("/consultaCotizacionIdCotizacion")
	public List<TwCotizacionesProducto> consultaCotizacionIdCotizacion(@RequestParam Long id) {

		try {
			
			return cotizacionService.consultaCotizacionIdCotizacion(id);
		} catch (Exception e) {

			logger.error("Error al guardar la cotización" + e);
		}
		return null;
	}
	
	
	@GetMapping("/consultaCotizacionClienteProducto")
	public List<TwCotizacionesProducto> consultaCotizacionClienteProducto(@RequestParam Long nIdCliente, @RequestParam Long nIdProducto) {

		try {
			
			return cotizacionService.consultaCotizacionClienteProducto(nIdCliente, nIdProducto);
		} catch (Exception e) {

			logger.error("Error al obtener los productos cotizados" + e);
		}
		return null;
	}
	
	
	@GetMapping("/consultaCotizacionesClienteVigente")
	public List<TwCotizacionesDetalle> consultaCotizacionesClienteVigente(@RequestParam Long nIdCliente) {

		try {			
			return cotizacionService.consultaCotizacionesClienteVigente(nIdCliente);
		} catch (Exception e) {

			logger.error("Error al obtener las cotizaciones vigentes " + e);
		}
		return null;
	}
	
	
	
	
}
