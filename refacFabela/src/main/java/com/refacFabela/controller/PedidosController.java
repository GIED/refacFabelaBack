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
import com.refacFabela.model.TcClavesat;
import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TvPedidoDetalle;
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
	@GetMapping("/obtenerPedidosEstatus")
	public List<TvPedidoDetalle> obtenerPedidosEstatus(@RequestParam() Long nEstatus) {
		try {

			return pedidosService.obtenerPedidosEstatus(nEstatus);

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
	

}
