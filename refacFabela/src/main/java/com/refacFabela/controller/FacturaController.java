package com.refacFabela.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.refacFabela.service.FacturacionService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/facturacion/")
public class FacturaController {
	
	@Autowired
	private FacturacionService facturaService;
	
	@GetMapping("venta")
	public @ResponseBody byte[] venta(HttpServletResponse response, @RequestParam(required = false) Long nIdVenta) throws Exception {
		
		facturaService.venta(nIdVenta);
		
		return null;
	}

}
