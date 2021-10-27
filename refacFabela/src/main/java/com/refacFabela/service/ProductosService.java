package com.refacFabela.service;

import java.util.List;

import com.refacFabela.model.TcProducto;

public interface ProductosService {
	public List<TcProducto> obtenerProductos();
	public TcProducto obtenerProductoNoParte(String No_Parte);

}
