package com.refacFabela.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TcCatalogogeneral;
import com.refacFabela.model.TcGanancia;
import com.refacFabela.model.TcProducto;
import com.refacFabela.repository.CatalogoGananciaRepository;
import com.refacFabela.repository.CatalogosRepository;
import com.refacFabela.utils.utils;

@Service
public class UtilisServiceImp {

	@Autowired
	private CatalogosRepository catalogosRepository;
	@Autowired
	private CatalogoGananciaRepository catalogoGananciaRepository;

public TcProducto calcularPrecio(TcProducto tcProducto){
	
		System.err.println("producto recibido en tcProducto: "+tcProducto);
		
	     utils util =new utils();		
	
	     TcGanancia ganancia=obtenerGananciaTc(tcProducto.getnIdGanancia());
	     tcProducto.setTcGanancia(ganancia);
	     
	     System.err.println("metodoCalcularPrecii"+util.calcularPrecio(tcProducto, obtenerTipoCambio(), 0.0, 0, false));
					
		
		return util.calcularPrecio(tcProducto, obtenerTipoCambio(), 0.0, 0, false);
	}

	public Double obtenerTipoCambio() {

		TcCatalogogeneral catalogo = catalogosRepository.findBysClave(utils.filtroTipoCambio);

		return catalogo.getnValor();
	}

	public Double obtenerValorIva() {

		TcCatalogogeneral catalogo = catalogosRepository.findBysClave(utils.filtroIva);

		return catalogo.getnValor();
	}
	public Double convertirMoneda(String moneda, Double precio) {
		
		Double precioMoneda=obtenerTipoCambio();
		Double precioPeso=(double) 0;
		
		if(moneda.equals("USD")) {
			
			precioPeso=precio*precioMoneda;
			
			
		}
		else if(moneda.equals("PESO")) {
			
			precioPeso=precio;
			
		}
		
		
		return precioPeso;
		
	}
	public Double obtenerGanancia(Long nId) {
		
		TcGanancia ganancia=catalogoGananciaRepository.getById(nId);
		
		return ganancia.getnGanancia();
	}
	
public TcGanancia obtenerGananciaTc(Long nId) {
		
		TcGanancia ganancia=catalogoGananciaRepository.getById(nId);
		
		return ganancia;
	}

}
