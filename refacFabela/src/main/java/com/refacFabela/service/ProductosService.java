package com.refacFabela.service;

import java.util.Date;
import java.util.List;

import com.refacFabela.dto.AbonosDto;
import com.refacFabela.dto.TvStockProductoDto;
import com.refacFabela.dto.VentaProductoDto;
import com.refacFabela.model.TcHistoriaPrecioProducto;
import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TvStockProducto;
import com.refacFabela.model.TvVentaProductoMes;
import com.refacFabela.model.TvVentaStock;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwHistoriaIngresoProducto;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.model.TwProductosAlternativo;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.repository.TwHistoriaIngresoProductoRepository;

public interface ProductosService {
	public List<TcProducto> obtenerProductos();
	public TcProducto obtenerProductoNoParte(String No_Parte);
	public List<TcProducto> obtenerProductoLike(String Producto);
	public List<TcProducto> obtenerNoParteLike(String No_Parte);
	public TcProducto guardarProducto(TcProducto tcProducto);
	public List<TcHistoriaPrecioProducto> historiaPrecioProducto(Long n_id);
	public List<TwHistoriaIngresoProducto> historiaIngresoProducto(Long n_id);
	public List<TwProductobodega> consultaProductoBodega(Long id);
	public List<TwProductobodega> obtenerInventaroEsp(Long idBodega,  Long idAnaquel, Long idNivel);
	public List<TwProductosAlternativo> obtenerProductosAlternativos(Long nId);
	public TwProductosAlternativo guardarProductoAlternativo(TwProductosAlternativo twProductosAlternativo);
	public TvStockProducto obtenerStockProductoId(Long id);
	public List<VentaProductoDto> obtenerProductosVentaId(Long id);
	public String guardaVentaProducto(VentaProductoDto ventaProductoDto);
	public List<TvVentaProductoMes> obtenerProductoVentaMesId(Long id);
	public List<TvVentaStock> obtenerVentasStockFecha(String  dFechaInicio, String dFechaFinal);
	public List<TrVentaCobro> obtenerPägosParciales(Long nIdVenta);
	public String consultaVentaProductoId(TwVentasProducto ventaProductoDto);
	public TwAbono guardarAbono(TwAbono abonoDto);
	public TcProducto calcularNuevoPrecio(TcProducto tcProducto);
	
}
