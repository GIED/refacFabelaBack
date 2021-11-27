package com.refacFabela.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.refacFabela.model.TcClavesat;
import com.refacFabela.model.TvTotalesGeneralesTablero;
import com.refacFabela.service.TableroService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class TableroController {
	private static final Logger logger = LogManager.getLogger("errorLogger");
	
	@Autowired
	private TableroService tableroService;
	
	@GetMapping("/totales-generales-tablero")
	public TvTotalesGeneralesTablero consultaTotalesGeneralesTablero() {
		try {

			return tableroService.consultaTotalesTablero();

		} catch (Exception e) {

			logger.error("Error al obtener datos generales " + e);
		}
		return null;
	}
}