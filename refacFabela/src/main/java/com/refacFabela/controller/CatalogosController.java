package com.refacFabela.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.refacFabela.model.TcCatalogogeneral;
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


}
