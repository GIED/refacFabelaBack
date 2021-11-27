package com.refacFabela.service;

import java.util.List;

import com.refacFabela.dto.CotizacionDto;
import com.refacFabela.dto.TvStockProductoDto;
import com.refacFabela.model.TwCotizaciones;
import com.refacFabela.model.TwCotizacionesDetalle;
import com.refacFabela.model.TwCotizacionesProducto;

public interface CotizacionService {
	
	public TwCotizaciones guardaCorizacion(List<CotizacionDto> listaCotizacion);
	public List<TwCotizacionesDetalle> consultaCotizaciones();
	public List<TvStockProductoDto> consultaCotizacionId(Long id);

}
