package com.refacFabela.service;

import com.refacFabela.model.TwFacturacion;

public interface FacturacionService {
	
	public byte[] venta(Long idVenta) throws Exception;
	
	public TwFacturacion guardar(TwFacturacion twFacturacion);

}
