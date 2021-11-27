package com.refacFabela.service;

import java.util.List;

import com.refacFabela.dto.CotizacionDto;
import com.refacFabela.model.TwCotizaciones;
import com.refacFabela.model.TwCotizacionesDetalle;
import com.refacFabela.model.TwCotizacionesProducto;

public interface CotizacionService {
	
	public void guardaCorizacion(List<CotizacionDto> listaCotizacion);
	public List<TwCotizacionesDetalle> consultaCotizaciones();

}
