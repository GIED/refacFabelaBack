package com.refacFabela.service;

import com.refacFabela.model.TwFacturacion;

public interface FacturacionService {
	
	public String venta(Long idVenta, String cveCfdi) throws Exception;
	
	public String complemento(Long idVenta, String cveCfdi) throws Exception;
	
	public TwFacturacion guardar(TwFacturacion twFacturacion);
	
	public int consultaCreditos(Long nDatoFactura);

}
