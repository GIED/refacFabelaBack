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
import com.refacFabela.model.TcProducto;
import com.refacFabela.service.ProductosService;


@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class ProductosController {
	private static final Logger logger = LogManager.getLogger("errorLogger");
	
	 @Autowired
	 private ProductosService productosService;
	
	@GetMapping("/obtenerProductos")
	public List<TcProducto> obtenerProductos() {

		try {
			return productosService.obtenerProductos();
		} catch (Exception e) {

			logger.error("Error al obtener los Productos" + e);
		}
		return null;
	}

	@GetMapping("/obtenerProductosNoParte")
	public TcProducto obtenerProductos(@RequestParam() String No_Parte) {

		try {
			return productosService.obtenerProductoNoParte(No_Parte);
		} catch (Exception e) {

			logger.error("Error al obtener los Productos" + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerProductosLike")
	public List<TcProducto> obtenerProductosLike(@RequestParam() String Producto) {

		try {
			return productosService.obtenerProductoLike(Producto);
		} catch (Exception e) {

			logger.error("Error al obtener los Productos" + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerProductosNoParteLike")
	public List<TcProducto> obtenerProductosNoParteLike(@RequestParam() String No_Parte) {

		try {
			return productosService.obtenerNoParteLike(No_Parte);
		} catch (Exception e) {

			logger.error("Error al obtener los Productos" + e);
		}
		return null;
	}
	@PostMapping("/guardarProducto")
	public TcProducto obtenerProductosNoParteLike(@RequestBody TcProducto tcProducto) {

		try {
			return productosService.guardarProducto(tcProducto);
		} catch (Exception e) {

			logger.error("Error al obtener los Productos" + e);
		}
		return null;
	}

}
