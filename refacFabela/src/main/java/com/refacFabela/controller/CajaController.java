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

import com.refacFabela.dto.BalanceCajaDto;
import com.refacFabela.model.TcClavesat;
import com.refacFabela.model.TwCaja;
import com.refacFabela.service.CajaService;
import com.refacFabela.service.CatalagosService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class CajaController {
	
	private static final Logger logger = LogManager.getLogger("errorLogger");
	

	@Autowired
	private CatalagosService catalagosService;
	@Autowired
	private CajaService cajaService;
	
	@GetMapping("/detalleCaja")
	public BalanceCajaDto consultaBalanceCaja(@RequestParam Long nIdCaja) {
		try {

			return cajaService.obtenerBalanceCaja( nIdCaja);

		} catch (Exception e) {

			logger.error("Error al el balamce de la caja " + e);
		}
		return null;
	}
	@GetMapping("/nuevaCaja")
	public TwCaja consultaBalanceCaja(@RequestParam Double saldoInicial, @RequestParam Long nIdUsuario) {
		try {

			return cajaService.nuevaCaja( saldoInicial, nIdUsuario);

		} catch (Exception e) {

			logger.error("Error al aperturar la nueva caja " + e);
		}
		return null;
	}

	
	

}
