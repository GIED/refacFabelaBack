package com.refacFabela.service;

import java.util.List;

import com.refacFabela.model.TcHistoriaPrecioProducto;
import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TvStockProducto;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.model.TwProductosAlternativo;

public interface ProductosService {
	public List<TcProducto> obtenerProductos();
	public TcProducto obtenerProductoNoParte(String No_Parte);
	public List<TcProducto> obtenerProductoLike(String Producto);
	public List<TcProducto> obtenerNoParteLike(String No_Parte);
	public TcProducto guardarProducto(TcProducto tcProducto);
	public List<TcHistoriaPrecioProducto> historiaPrecioProducto(Long n_id);
	public List<TwProductobodega> consultaProductoBodega(Long id);
	public List<TwProductobodega> obtenerInventaroEsp(Long idBodega,  Long idAnaquel, Long idNivel);
	public List<TwProductosAlternativo> obtenerProductosAlternativos(Long nId);
	public TwProductosAlternativo guardarProductoAlternativo(TwProductosAlternativo twProductosAlternativo);
	public TvStockProducto obtenerStockProductoId(Long id);
	public List<TwAbono> obtenerAbonoVentaId(Long id);
}
