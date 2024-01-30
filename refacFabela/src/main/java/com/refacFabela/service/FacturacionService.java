package com.refacFabela.service;

import com.refacFabela.model.TwFacturacion;
import com.refacFabela.model.factura.CabeceraPagosXml;

public interface FacturacionService {
	
	public String venta(Long idVenta, String cveCfdi) throws Exception;
	
	public String complemento(Long idVenta, String cveCfdi) throws Exception;
	
	public TwFacturacion guardar(TwFacturacion twFacturacion);
	
	public int consultaCreditos(Long nDatoFactura);


}
