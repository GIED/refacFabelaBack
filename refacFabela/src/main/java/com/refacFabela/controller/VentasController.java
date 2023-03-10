package com.refacFabela.controller;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.refacFabela.dto.AbonosDto;
import com.refacFabela.dto.TvStockProductoDto;
import com.refacFabela.dto.TvVentaDetalleDto;
import com.refacFabela.dto.VentaDto;
import com.refacFabela.dto.VentaProductoDto;
import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TvVentaDetalle;
import com.refacFabela.model.TvVentaProductoMes;
import com.refacFabela.model.TvVentaStock;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwMaquinaCliente;
import com.refacFabela.model.TwVenta;
import com.refacFabela.model.TwVentaProductosTraer;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.service.ProductosService;
import com.refacFabela.service.VentasService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class VentasController {
	private static final Logger logger = LogManager.getLogger("errorLogger");
	
	@Autowired
	private VentasService ventasService;
	@Autowired
	private ProductosService productosService;

	@GetMapping("/obtenerVentas")
	public List<TwVenta> consultaVentas() {
		try {

			return ventasService.consltaVentas();

		} catch (Exception e) {

			logger.error("Error al obtener todas las ventas " + e);
		}
		return null;
	}
	@GetMapping("/obtenerVentaslike")
	public List<TvVentaDetalle> consultaVentasLike(@RequestParam() String buscar) {
		try {

			return ventasService.consultaVentaslike(buscar.toUpperCase());

		} catch (Exception e) {

			logger.error("Error al obtener ventas Like " + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerVentasTop")
	public List<TvVentaDetalle> consultaVentasTop() {
		try {

			return ventasService.consultaVentasTop();

		} catch (Exception e) {

			logger.error("Error al obtener ventas Top " + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerVentaId")
	public TwVenta consultaVentasId(@RequestParam() Long nIdVenta) {
		try {

			return ventasService.consltaVentasId(nIdVenta);

		} catch (Exception e) {

			logger.error("Error al obtener la venta por id " + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerVentaIdCotizacion")
	public TwVenta consultaVentasIdCotizacion(@RequestParam() Long nIdCotizacion) {
		try {

			return ventasService.consltaVentasIdCotizacion(nIdCotizacion);

		} catch (Exception e) {

			logger.error("Error al obtener la venta por id " + e);
		}
		return null;
	}
	
	
	@GetMapping("/obtenerVentasDetalle")
	public List<TvVentaDetalle> consultaVentasDetalle() {
		try {
			return ventasService.consultaVentaDetalle();

		} catch (Exception e) {

			logger.error("Error al obtener todas las ventas " + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerVentasDetalleEntrega")
	public List<TvVentaDetalle> consultaVentasDetalleEntrega() {
		try {
			return ventasService.consultaVentaDetalleEntrega();

		} catch (Exception e) {

			logger.error("Error al obtener todas las ventas " + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerVentasClienteDetalleEstatus") 
	public List<TvVentaDetalle> obtenerVentasClientesDetalleEstatus(@RequestParam() Long nIdCliente, long nTipoPago) {

		try {
			return ventasService.consultaVentaDetalleId(nIdCliente, nTipoPago);
		} catch (Exception e) {

			logger.error("Error al obtener los Productos" + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerVentasClienteDetalleEstatusVenta") 
	public List<TvVentaDetalleDto> obtenerVentasClientesDetalleEstatusVenta(@RequestParam()  long nEstatusVenta) {

		try {
			return ventasService.consultaVentaDetalleIdEstatusVenta( nEstatusVenta);
		} catch (Exception e) {

			logger.error("Error al obtener los Productos" + e);
		}
		return null;
	}
	@GetMapping("/obtenerVentasCajaVigente") 
	public List<TvVentaDetalle> obtenerVentasCajaVigente() {

		try {
			return ventasService.consultaVentaDetalleCajaVigente( );
		} catch (Exception e) {

			logger.error("Error al obtener los Productos de la caja vigente" + e);
		}
		return null;
	}
	@GetMapping("/obtenerAbonosVentaId") 
	public List<TwAbono> obtenerAbonosVentaId(@RequestParam() Long nId) {

		try {
			return ventasService.consultaAbonoVentaId(nId);
		} catch (Exception e) {

			logger.error("Error al obtener los Productos" + e);
		}
		return null;
	}
	
	@PostMapping("/guardarVenta")
	public TwVenta guardarVenta(@RequestBody VentaDto ventaDto) {

		try {		
			
			
			return ventasService.guardarVenta(ventaDto);
		} catch (Exception e) {

			logger.error("Error al guardar la venta" + e);
		}
		return null;
	}
	
	@PostMapping("/guardarVentaDetalle")
	public TvVentaDetalle guardarVentaDescuento(@RequestBody TvVentaDetalle tvVentaDetalle) {

		try {		
			
			
			return ventasService.guardarVentaDetalle(tvVentaDetalle);
		} catch (Exception e) {

			logger.error("Error al guardar la venta" + e);
		}
		return null;
	}
	@GetMapping("/consultaProductoVentaId")
	public List<VentaProductoDto> obtenerProductoVendidosIdVenta(@RequestParam() Long id) {

		try {
			return productosService.obtenerProductosVentaId(id);
		} catch (Exception e) {

			logger.error("Error al obtener el stock de bodegas" + e);
		}
		return null;
	}
	
	@PostMapping("/guardaVentasProducto")
	public String obtenerVentaProducto(@RequestBody VentaProductoDto ventaProductoDto) {

		try {
			return productosService.guardaVentaProducto(ventaProductoDto);
		} catch (Exception e) {

			logger.error("Error al oguardar la entrega del producto" + e);
		}
		return null;
	}
	@GetMapping("/consultaProductoVentaMesId")
	public List<TvVentaProductoMes> obtenerProductoVendidosMesIdVenta(@RequestParam() Long id) {

		try {
			return productosService.obtenerProductoVentaMesId(id);
		} catch (Exception e) {

			logger.error("Error al obtener los productos vendidos por mes" + e);
		}
		return null;
	}
	
	
	
	@PostMapping("/guardaVentaProductoId")
	public String obtenerProductoVendidoId(@RequestBody TwVentasProducto ventaProductoDto) {

		try {
		
			return productosService.consultaVentaProductoId(ventaProductoDto);
		} catch (Exception e) {

			logger.error("Error al obtener los productos vendidos por id" + e);
		}
		return null;
	}
	
	
	@PostMapping("/guardarAbono")
	public TwAbono obtenerProductoVendidoId(@RequestBody TwAbono abonoDto) {

		try {
		
			return productosService.guardarAbono(abonoDto);
		} catch (Exception e) {

			logger.error("Error al obtener los productos vendidos por id" + e);
		}
		return null;
	}
	
	@PostMapping("/calcularNuevoPrecio")
	public TcProducto calcularNuevoPrecio(@RequestBody TcProducto tcProducto) {

		try {
		
			return productosService.calcularNuevoPrecio(tcProducto);
		} catch (Exception e) {

			logger.error("Error al calcular el nuevo precio" + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerVentasStockFecha")
	public List<TvVentaStock> obtenerVentasStockFecha(@RequestParam()  String dFechaInicio, @RequestParam()  String  dFechaFinal) {

		try {
			return productosService.obtenerVentasStockFecha(dFechaInicio,dFechaFinal);
		} catch (Exception e) {

			logger.error("Error al obtener los productos vendidos en el periodo y su stock" + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerPagosParciales")
	public List<TrVentaCobro> obtenerPagosParciales(@RequestParam()  Long nIdVenta) {

		try {
			return productosService.obtenerPägosParciales(nIdVenta);
		} catch (Exception e) {

			logger.error("Error al obtener los pagos parciales" + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerMaquinasCliente")
	public List<TwMaquinaCliente> obtenerMaquinasCliente(@RequestParam()  Long nIdCliente) {

		try {
			return productosService.obtenerMaquinasCliente(nIdCliente);
		} catch (Exception e) {

			logger.error("Error al obtener las maquinas del cliente" + e);
		}
		return null;
	}
	
	@PostMapping("/guardarMaquina")
	public TwMaquinaCliente guardarMaquina(@RequestBody TwMaquinaCliente twMaquinaCliente) {

		try {
		
			return productosService.guardarMaquina(twMaquinaCliente);
		} catch (Exception e) {

			logger.error("Error al guardar la maquina" + e);
		}
		return null;
	}
	
	
	@PostMapping("/cancelaVentaProducto")
	public VentaProductoDto cancelaVentaProducto(@RequestBody  VentaProductoDto ventaProductoDto) {
		try {
			return productosService.cacelarVentaProducto(ventaProductoDto);
		} catch (Exception e) {
			logger.error("Error al cancelar la venta" + e);
		}
		return null;
	}
	
	@PostMapping("/guardarVentaProductosTraer")
	public TwVentaProductosTraer guardaVentaProductosTraer(@RequestBody  TwVentaProductosTraer ventaProductosTraer) {
		try {
			return productosService.ventaProductosTraer(ventaProductosTraer);
		} catch (Exception e) {
			logger.error("Error al guardar los productos traer" + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerProductosTraer")
	public List<TwVentaProductosTraer> obtenerProductosTraerVenta(@RequestParam()  Long nIdVenta) {

		try {
			return productosService.obtenerProductosTraer(nIdVenta);
		} catch (Exception e) {

			logger.error("Error al obtener las maquinas del cliente" + e);
		}
		return null;
	}
	
	

}
