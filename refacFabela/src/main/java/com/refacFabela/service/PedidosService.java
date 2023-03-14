package com.refacFabela.service;

import java.util.List;

import com.refacFabela.dto.PedidoDto;
import com.refacFabela.model.TvPedidoDetalle;
import com.refacFabela.model.TwPedido;
import com.refacFabela.model.TwPedidoProducto;


public interface PedidosService {
	
	public List<TwPedidoProducto> obtenerPedidosRegistrados(Long nIdPedido);
	public List<TvPedidoDetalle> obtenerPedidosEstatus(Long nEstatus);
	public PedidoDto guaradarPedido(PedidoDto pedidoDto);
	public TwPedidoProducto borrarPedidoProducto(TwPedidoProducto twPedidoProducto);
	public TwPedidoProducto ingresoProducto(TwPedidoProducto twPedidoProducto);


}
