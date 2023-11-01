package com.refacFabela.service;

import java.util.List;

import com.refacFabela.dto.TvVentaDetalleDto;
import com.refacFabela.dto.VentaDto;
import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TvVentaDetalle;
import com.refacFabela.model.TvVentasFactura;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwVenta;

public interface VentasService {

	public List<TwVenta> consltaVentas();
	public List<TvVentaDetalle> consultaVentaslike(String buscar);
	public List<TvVentaDetalle> consultaVentasTop();
	public TwVenta consltaVentasId(Long nIdVenta);
	public TwVenta consltaVentasIdCotizacion(Long nIdCotizacion);
	public List<TvVentaDetalle> consultaVentaDetalle();
	public List<TvVentaDetalle> consultaVentaDetalleEntrega();
	public List<TvVentaDetalle> consultaVentaDetalleId(Long n_idCliente, Long nTipoPago);
	public List<TvVentaDetalleDto> consultaVentaDetalleIdEstatusVenta( Long nEstatusVenta);
	public List<TvVentaDetalle> consultaVentaDetalleCajaVigente();
	public TwVenta guardarVenta(VentaDto ventaDto);
	public TwVenta updateStatusVenta(TwVenta twVenta);
	public TvVentaDetalle guardarVentaDetalle(TvVentaDetalle tvVentaDetalle) throws InterruptedException;
	public TvVentaDetalle guardarDescuento(TvVentaDetalle tvVentaDetalle) throws InterruptedException;

	public List<TwAbono> consultaAbonoVentaId(Long nId);
	public List<TvVentasFactura> consultaVentasParaFactura();
	public List<TvVentasFactura> consultaVentasFacturadas();
	public List<TrVentaCobro> consultarPagoId(Long idVenta);
	public void eliminarCobroIdVenta(Long idVenta);

	
	
}
