package com.refacFabela.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/facturacion/")
public class FacturaController {
	
	@GetMapping("venta")
	public @ResponseBody byte[] venta(HttpServletResponse response, @RequestParam(required = false) Long nIdVenta) {
		
		return null;
	}

}
