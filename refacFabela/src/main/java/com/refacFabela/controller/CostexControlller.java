package com.refacFabela.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.refacFabela.model.Part;
import com.refacFabela.service.CtpService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class CostexControlller {
	private static final Logger logger = LogManager.getLogger("errorLogger");
	
	  @Autowired
	    private CtpService ctpService;
	  
	  @GetMapping("/consultarCostex")
	  public Part consultarParte(@RequestParam String numeroParte, @RequestParam(defaultValue = "1") String cantidad) {
	      try {
	            
	          return ctpService.consultarParte(numeroParte, cantidad);
	      } catch (Exception e) {
	    	  logger.error("Error al obtener datos generales " + e);
	      }
		return null;
	  }


}
