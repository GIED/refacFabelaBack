package com.refacFabela.service;

import com.refacFabela.model.TwFacturacion;

public interface FacturacionService {
	
	public String venta(Long idVenta, String cveCfdi) throws Exception;
	
	public TwFacturacion guardar(TwFacturacion twFacturacion);

}
