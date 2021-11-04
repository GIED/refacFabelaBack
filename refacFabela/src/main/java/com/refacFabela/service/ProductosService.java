package com.refacFabela.service;

import java.util.List;

import com.refacFabela.model.TcHistoriaPrecioProducto;
import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TwProductobodega;

public interface ProductosService {
	public List<TcProducto> obtenerProductos();
	public TcProducto obtenerProductoNoParte(String No_Parte);
	public List<TcProducto> obtenerProductoLike(String Producto);
	public List<TcProducto> obtenerNoParteLike(String No_Parte);
	public TcProducto guardarProducto(TcProducto tcProducto);
	public List<TcHistoriaPrecioProducto> historiaPrecioProducto(Long n_id);
	public List<TwProductobodega> consultaProductoBodega(Long id);

}
