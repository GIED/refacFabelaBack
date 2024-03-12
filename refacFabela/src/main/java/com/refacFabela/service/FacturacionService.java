package com.refacFabela.service;

import org.springframework.web.multipart.MultipartFile;

import com.refacFabela.dto.SubirFacturaDto;
import com.refacFabela.model.TwFacturacion;
import com.refacFabela.model.factura.CabeceraPagosXml;

public interface FacturacionService {
	
	public String venta(Long idVenta, String cveCfdi) throws Exception;
	
	public String complemento(Long idVenta, String cveCfdi) throws Exception;
	
	public TwFacturacion guardar(TwFacturacion twFacturacion);
	
	public int consultaCreditos(Long nDatoFactura);
	
	public SubirFacturaDto subirArchivo(MultipartFile file, MultipartFile fileXml, String venta, String uuid) throws Exception;


}
