package com.refacFabela.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.refacFabela.model.TwProductobodega;
import com.refacFabela.service.TraspasoService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class TraspasosController {
	
	private static final Logger logger = LogManager.getLogger("errorLogger");
	
	@Autowired
	private TraspasoService traspasoService;
	
	
	@PostMapping("/movimientoInterno")
	public ResponseEntity<?> guardar(@RequestBody() TwProductobodega twProductobodega){
		
		Map<String, Object>  response = new HashMap<>();
		
		response.put("twProductobodega", this.traspasoService.guardar(twProductobodega));
				
		return new ResponseEntity<Map<String , Object>>(response, HttpStatus.ACCEPTED);
		
	}
	
	
	

	
	
	

}
