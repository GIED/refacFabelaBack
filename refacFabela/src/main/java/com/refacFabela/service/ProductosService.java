package com.refacFabela.service;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.refacFabela.dto.AbonosDto;
import com.refacFabela.dto.CalculaPrecioDto;
import com.refacFabela.dto.ProductoDescuentoDto;
import com.refacFabela.dto.TvStockProductoDto;
import com.refacFabela.dto.TvVentaDetalleDto;
import com.refacFabela.dto.VentaProductoCancelaDto;
import com.refacFabela.dto.VentaProductoDto;
import com.refacFabela.model.TcHistoriaPrecioProducto;
import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TvStockProducto;
import com.refacFabela.model.TvStockProductoHist;
import com.refacFabela.model.TvVentaDetalle;
import com.refacFabela.model.TvVentaProductoMes;
import com.refacFabela.model.TvVentaStock;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwAjustesInventario;
import com.refacFabela.model.TwHistoriaIngresoProducto;
import com.refacFabela.model.TwMaquinaCliente;
import com.refacFabela.model.TwPedidoProducto;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.model.TwProductosAlternativo;
import com.refacFabela.model.TwSaldoUtilizado;
import com.refacFabela.model.TwVentaProductoCancela;
import com.refacFabela.model.TwVentaProductosTraer;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.model.VwSaldoVentaFavorDisponible;
import com.refacFabela.repository.TwHistoriaIngresoProductoRepository;

public interface ProductosService {
	public List<TcProducto> obtenerProductos();

	public TcProducto obtenerProductoNoParte(String No_Parte);
	
	public List<TcProducto> obtenerProductoId(Long nId);
	
	public TcProducto obtenerProductoBeanId(Long nId);


	public List<TcProducto> obtenerProductoLike(String Producto);

	public List<TcProducto> obtenerNoParteLike(String No_Parte);

	public TcProducto guardarProducto(TcProducto tcProducto);

	public TcProducto guardarProductoGeneral(TcProducto tcProducto);

	public List<TcHistoriaPrecioProducto> historiaPrecioProducto(Long n_id);

	public List<TwHistoriaIngresoProducto> historiaIngresoProducto(Long n_id);

	public List<TwProductobodega> consultaProductoBodega(Long id);
	
	public TwProductobodega consultaProductoBod(Long id, Long nIdBodega);
	
	public TwProductobodega guardarProductoBodega(TwProductobodega twProductobodega);


	public List<TwProductobodega> obtenerInventaroEsp(Long idBodega, Long idAnaquel, Long idNivel);

	public List<TwProductosAlternativo> obtenerProductosAlternativos(Long nId);

	public List<TwProductosAlternativo> obtenerProductosAlternativosDescuento(Long nId, Long nIdCliente);

	public TwProductosAlternativo guardarProductoAlternativo(TwProductosAlternativo twProductosAlternativo);

	public TvStockProducto obtenerStockProductoId(Long id);

	public List<TvStockProductoHist> obtenerStockProductoHist(Long id);

	public List<TwVentaProductoCancela> obtenerProductosCancelaId(Long id);

	public List<VentaProductoDto> obtenerProductosVentaId(Long id);

	public String guardaVentaProducto(VentaProductoDto ventaProductoDto);

	public List<TvVentaProductoMes> obtenerProductoVentaMesId(Long id);
	
	public TwVentasProducto obtenerVentaProductoId( Long nIdVenta, Long nIdProducto);

	public List<TvVentaStock> obtenerVentasStockFecha(String dFechaInicio, String dFechaFinal);

	public List<TrVentaCobro> obtenerPägosParciales(Long nIdVenta);

	public List<TwMaquinaCliente> obtenerMaquinasCliente(Long nIdClinete);

	public List<TwVentaProductosTraer> obtenerProductosTraer(Long nIdClinete);

	public List<TwSaldoUtilizado> obtenerSaldosUtilizados(Long nIdClinete);

	public VwSaldoVentaFavorDisponible obtenerSaldoVentaCancela(Long nIdVenta);

	public List<TwVentaProductoCancela> obtenerVentaProductoCanela(String fechaInicio, String fechaTermino);
	
	public List<TwAjustesInventario> obtenerVentaProductoAjusteInventario(String fechaInicio, String fechaTermino);
	
	public TvVentaDetalle obtenerVentaDetalleId(Long nIdVenta);
	
	public List<TwVentaProductoCancela> obtenerVentaProductoCancelaId(Long nIdVenta);


	public VentaProductoDto cacelarVentaProducto(VentaProductoCancelaDto ventaProductoCancelaDto);

	public TwVentaProductosTraer ventaProductosTraer(TwVentaProductosTraer ventaProductoDto);

	public TwSaldoUtilizado guardarSaldoUtilizado(TwSaldoUtilizado twSaldoUtilizado);
	
	public TrVentaCobro guardarVentaCobro(TrVentaCobro trVentaCobro);

	public String consultaVentaProductoId(TwVentasProducto ventaProductoDto);
	
	public CalculaPrecioDto calcularNuevoPrecioAjustado(CalculaPrecioDto calculaPrecioDto);
	
	public TwVentasProducto actualizaVentaProducto(TwVentasProducto twVentasProducto);
	
	public TwPedidoProducto pedidoProductoIngreso(TwPedidoProducto wPedidoProducto);


	public TwAbono guardarAbono(TwAbono abonoDto);

	public TcProducto calcularNuevoPrecio(ProductoDescuentoDto productoDescuentoDto);

	public TwMaquinaCliente guardarMaquina(TwMaquinaCliente twMaquinaCliente);
	
	public String obtenerImagenBase64(String ruta);

	public TcProducto getProductoByNoParteAndIdMarca(String noParte, Long nIdMarca);

}
