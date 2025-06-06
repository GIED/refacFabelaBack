package com.refacFabela.controller;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.refacFabela.dto.PedidoDto;
import com.refacFabela.model.TvPedidoDetalle;
import com.refacFabela.model.TwPedido;
import com.refacFabela.model.TwPedidoProducto;
import com.refacFabela.service.PedidosService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class PedidosController {
	private static final Logger logger = LogManager.getLogger("errorLogger");
	
	@Autowired
	private PedidosService  pedidosService;
	
	
	@GetMapping("/obtenerProductosPedido")
	public List<TwPedidoProducto> consultaProductosPedido(@RequestParam() Long nIdPedido) {
		try {

			return pedidosService.obtenerPedidosRegistrados(nIdPedido);

		} catch (Exception e) {

			logger.error("Error al obtener los pedidos registrados " + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerProductosIdPedidos")
	public List<TwPedidoProducto> obtenerProductosIdPedidos(@RequestParam() Long nIdProducto) {
		try {

			return pedidosService.obtenerProductosIdPedido(nIdProducto);

		} catch (Exception e) {

			logger.error("Error al obtener los pedidos registrados " + e);
		}
		return null;
	}
	@GetMapping("/obtenerPedidosEstatus")
	public List<TvPedidoDetalle> obtenerPedidosEstatus(@RequestParam() Long nEstatus) {
		try {

			return pedidosService.obtenerPedidosEstatus(nEstatus);

		} catch (Exception e) {

			logger.error("Error al obtener los pedidos registrados " + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerPedidoId")
	public TwPedido obtenerPedidoId(@RequestParam() Long nIdPedido) {
		try {

			return pedidosService.obtenerPedidoId(nIdPedido);

		} catch (Exception e) {

			logger.error("Error al obtener los pedido " + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerPedidos")
	public List<TvPedidoDetalle> obtenerPedidos() {
		try {

			return pedidosService.obtenerPedidos();

		} catch (Exception e) {

			logger.error("Error al obtener los pedidos registrados " + e);
		}
		return null;
	}
	
	@PostMapping("/guardarPedido")
	public PedidoDto guardarPedido(@RequestBody PedidoDto pedidoDto) {

		try {
			return pedidosService.guaradarPedido(pedidoDto);

		} catch (Exception e) {
			logger.error("Error al guardar el pedido" + e);
		}

		return null;
	}
	
	@PostMapping("/ingresoProductoPedido")
	public TwPedidoProducto ingresoProductoPedido(@RequestBody TwPedidoProducto twPedidoProducto) {

		try {
			return pedidosService.ingresoProducto(twPedidoProducto);

		} catch (Exception e) {
			logger.error("Error al guardar el pedido" + e);
		}

		return null;
	}
	
	@PostMapping("/borrarPedidoProducto")
	public TwPedidoProducto borrarPedidoProducto(@RequestBody TwPedidoProducto twPedidoProducto) {

		try {
			return pedidosService.borrarPedidoProducto(twPedidoProducto);

		} catch (Exception e) {
			logger.error("Error al borrar el pedido producto" + e);
		}

		return null;
	}
	
	@PostMapping("/guardaPedidoGeneral")
	public TwPedido guradaPedidoNuevo(@RequestBody TwPedido twPedido) {

		try {
			return pedidosService.guardaPedidoNuevo(twPedido);

		} catch (Exception e) {
			logger.error("Error al guaradar el pedido producto" + e);
		}

		return null;
	}
	
	
	
	
	@PostMapping("/guardaPedidoProducto")
	public TwPedidoProducto guardaPedidoProducto(@RequestBody TwPedidoProducto twPedidoProducto) {

		try {
			return pedidosService.guardaPedidoProducto(twPedidoProducto);

		} catch (Exception e) {
			logger.error("Error al guaradar el pedido producto" + e);
		}

		return null;
	}
	
	
	@GetMapping("/obteneCarritoPedidoUsuario")
	public List<TwPedidoProducto> obteneCarritoPedido(@RequestParam() Long nIdUsuario) {
		try {

			return pedidosService.obtenerPedidoCarritoUsuario(nIdUsuario);

		} catch (Exception e) {

			logger.error("Error al obtener los productos de carrito usuario " + e);
		}
		return null;
	}
	
	@GetMapping("/borrarPedidoProductoId")
	public Boolean borrarPedidoProductoId(@RequestParam() Long nId) {
		try {

			return pedidosService.borrarProductoPedidoId(nId);

		} catch (Exception e) {

			logger.error("Error  " + e);
		}
		return null;
	}
	

}
