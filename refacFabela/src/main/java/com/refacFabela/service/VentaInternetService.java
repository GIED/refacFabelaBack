package com.refacFabela.service;

import java.util.List;

import com.refacFabela.model.TwPagoComprobanteInternet;

public interface VentaInternetService {
	
	public TwPagoComprobanteInternet guardarComprobante(TwPagoComprobanteInternet comprobanteInternet);
	
	public TwPagoComprobanteInternet consultarSiComprobanteExiste(Long idCotizacion, Long idCliente);
	
	public List<TwPagoComprobanteInternet> consultaRegistros(Integer estatus);

}
