package com.refacFabela.service;

import java.util.List;

import com.refacFabela.dto.VentaCotizacionProductoAnoDto;
import com.refacFabela.model.TwCarritoCompraPedido;
import com.refacFabela.model.VwProductoMetaCompra;

public interface ComprasService {
	
	public List<VwProductoMetaCompra> obtenerProductosVendidosFechaCompra(String FechaIncio, String FechaTermino);
	public List<VentaCotizacionProductoAnoDto> obtenerVentaCotizacionProductoAnoDto(Integer idProducto);
	public List<VwProductoMetaCompra>  obtenerProductosVendidosIdProducto(Long idProducto);
	public List<TwCarritoCompraPedido>  obtenerProductosCarritoCompraUsuario(Long idUsuario);
	public Boolean  borrarProductosCarritoCompraUsuario(Long nId);
	public TwCarritoCompraPedido  guardarProductosCarritoCompraUsuario(TwCarritoCompraPedido twCarritoCompraPedido);





}
