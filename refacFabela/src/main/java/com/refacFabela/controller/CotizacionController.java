package com.refacFabela.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.refacFabela.dto.CotizacionDto;
import com.refacFabela.service.CotizacionService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class CotizacionController {
	
	@Autowired
	private CotizacionService cotizacionService;
	
	private static final Logger logger = LogManager.getLogger("errorLogger");
	
	@PostMapping("/guardarCotizacion")
	public String guardarCotizacion(@RequestBody List<CotizacionDto> listaCotizacion) {

		try {
			cotizacionService.guardaCorizacion(listaCotizacion);
			return "registrado";
		} catch (Exception e) {

			logger.error("Error al guardar la cotización" + e);
		}
		return null;
	}

}
