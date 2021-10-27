package com.refacFabela.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import com.refacFabela.model.TcCatalogogeneral;
import com.refacFabela.model.TcCategoria;
import com.refacFabela.model.TcCategoriaGeneral;
import com.refacFabela.model.TcClavesat;
import com.refacFabela.model.TcGanancia;
import com.refacFabela.service.CatalagosService;


@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class CatalogosController {
	
	private static final Logger logger = LogManager.getLogger("errorLogger");

	@Autowired
	private CatalagosService catalagosService;

	@PostMapping("/actualizarTipoCambio")
	public TcCatalogogeneral actualizaTipoCambio(@RequestBody TcCatalogogeneral tcCatalogogeneral) {
		try {
			return catalagosService.actualizarTipoCambio(tcCatalogogeneral);

		} catch (Exception e) {

			logger.error("Error al guardar Tipo de cambio" + e);
		}
		return null;
	}
	
	@PostMapping("/consultaTipoCambioId")
	public TcCatalogogeneral consultaTipoCambioId(@RequestBody TcCatalogogeneral tcCatalogogeneral) {
		try {
			return catalagosService.consultaTipoCambioId(tcCatalogogeneral);

		} catch (Exception e) {

			logger.error("Error al obtener tipo de cambio" + e);
		}
		return null;
	}
	
	@GetMapping("/catalogosClaveSat")
	public List<TcClavesat> consultaClaveSat(){
		try {			
			
			return catalagosService.catalogoClaveSat();

		} catch (Exception e) {

			logger.error("Error al obtener catalogo de clave sat " + e);
		}
		return null;
	}
	
	@GetMapping("/catalogoCategoriaGeneral")
	public List<TcCategoriaGeneral> consultaCategoriaGeneral(){
		try {			
			
			return catalagosService.catalogoCategoriaGeneral();

		} catch (Exception e) {

			logger.error("Error al obtener catalogo de categoria General " + e);
		}
		return null;
	}
	
	@GetMapping("/catalogoCategoriaId")
	public List<TcCategoria> consultaCategoriaId(@RequestParam() int id){
		try {			
			
			return catalagosService.catalogoCategoriaId(id);

		} catch (Exception e) {

			logger.error("Error al obtener catalogo de categoria " + e);
		}
		return null;
	}
	
	@GetMapping("/catalogoGanancia")
	public List<TcGanancia> consultaCatalogoGanancia(){
		try {			
			
			return catalagosService.catalogoGanancia();

		} catch (Exception e) {

			logger.error("Error al obtener catalogo de ganancia " + e);
		}
		return null;
	}
		


}
