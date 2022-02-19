package com.refacFabela.service;

import java.util.List;

import com.refacFabela.dto.VentaDto;
import com.refacFabela.model.TvVentaDetalle;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwVenta;

public interface VentasService {

	public List<TwVenta> consltaVentas();
	public TwVenta consltaVentasId(Long nIdVenta);
	public List<TvVentaDetalle> consultaVentaDetalle();
	public List<TvVentaDetalle> consultaVentaDetalleEntrega();
	public List<TvVentaDetalle> consultaVentaDetalleId(Long n_idCliente, Long nTipoPago);
	public List<TvVentaDetalle> consultaVentaDetalleIdEstatusVenta( Long nEstatusVenta);
	public TwVenta guardarVenta(VentaDto ventaDto);
	public TvVentaDetalle guardarVentaDetalle(TvVentaDetalle tvVentaDetalle);
	public List<TwAbono> consultaAbonoVentaId(Long nId);

	
	
}
