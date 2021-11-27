package com.refacFabela.service;

import java.util.List;

import com.refacFabela.dto.VentaDto;
import com.refacFabela.model.TvVentaDetalle;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwVenta;

public interface VentasService {

	public List<TwVenta> consltaVentas();
	public List<TvVentaDetalle> consultaVentaDetalle(Long n_idCliente, Long nTipoPago);

	public void guardarVenta(VentaDto ventaDto);

	public List<TwAbono> consultaAbonoVentaId(Long nId);

	
	
}
