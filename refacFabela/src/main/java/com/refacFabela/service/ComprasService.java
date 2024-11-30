package com.refacFabela.service;

import java.util.Date;
import java.util.List;

import com.refacFabela.dto.VentaCotizacionProductoAnoDto;
import com.refacFabela.model.VwProductoMetaCompra;

public interface ComprasService {
	
	public List<VwProductoMetaCompra> obtenerProductosVendidosFechaCompra(String FechaIncio, String FechaTermino);
	public List<VentaCotizacionProductoAnoDto> obtenerVentaCotizacionProductoAnoDto(Integer idProducto);


}
