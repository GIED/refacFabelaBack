package com.refacFabela.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.refacFabela.dto.VentaCotizacionProductoAnoDto;
import com.refacFabela.model.TwCarritoCompraPedido;
import com.refacFabela.model.TwPedidoProducto;
import com.refacFabela.model.VwProductoMetaCompra;
import com.refacFabela.service.ComprasService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class ComprasController {
	private static final Logger logger = LogManager.getLogger("errorLogger");
	
	
	@Autowired
	private ComprasService comprasService;
	
	
	
	@GetMapping("/productosVendidosFechaCompra")
	public List<VwProductoMetaCompra> productosVendidosFechaCompra(HttpServletResponse response, String FechaIncio, String FechaTermino) {
		try {	

			return comprasService.obtenerProductosVendidosFechaCompra( FechaIncio, FechaTermino);

		} catch (Exception e) {

			logger.error("Error al obtener los productos vendidos " + e);
		}
		return null;
	}
	
	@GetMapping("/ventaCotizaProdAno")
	public List<VentaCotizacionProductoAnoDto> ventaCotizaProdAno(HttpServletResponse response, Integer idProducto) {
		try {			
		

			return comprasService.obtenerVentaCotizacionProductoAnoDto( idProducto);

		} catch (Exception e) {

			logger.error("Error al obtener los productos vendidos cotizados ano " + e);
		}
		return null;
	}
	
	@GetMapping("/productosVendidosCotizadosIdProducto")
	public List<VwProductoMetaCompra> productosVendidosCotizadosIdProducto(HttpServletResponse response, Long idProducto) {
		try {

			return comprasService.obtenerProductosVendidosIdProducto(idProducto);

		} catch (Exception e) {

			logger.error("Error al obtener el pruducto" + e);
		}
		return null;
	}
	
	
	
	@GetMapping("/productosCarritoCompraUsuario")
	public List<TwCarritoCompraPedido> productosCarritoCompraUsuario(HttpServletResponse response, Long idUsuario) {
		try {

			return comprasService.obtenerProductosCarritoCompraUsuario(idUsuario);

		} catch (Exception e) {

			logger.error("Error al obtener el pruducto carrito compra" + e);
		}
		return null;
	}
	
	
	@PostMapping("/guardarProductoCarritoCompraUsuario")
	public TwCarritoCompraPedido guardarPedidoProductoUsuario(@RequestBody TwCarritoCompraPedido twCarritoCompraPedido) {

		try {
			return comprasService.guardarProductosCarritoCompraUsuario(twCarritoCompraPedido);

		} catch (Exception e) {
			logger.error("Error al guardar el producto carrito pedido" + e);
		}

		return null;
	}
	
	
	@GetMapping("/borrarProductoCarritoCompraUsuario")
	public Boolean borrarPedidoProductoUsuario(HttpServletResponse response, Long nId) {

		try {
			return comprasService.borrarProductosCarritoCompraUsuario(nId);

		} catch (Exception e) {
			logger.error("Error al borrar el producto carrito" + e);
		}

		return null;
	}
	
	


	
	
}
