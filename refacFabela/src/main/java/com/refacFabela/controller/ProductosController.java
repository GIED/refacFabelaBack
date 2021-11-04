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

import com.refacFabela.model.TcHistoriaPrecioProducto;
import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.service.ProductosService;
import com.refacFabela.service.impl.UtilisServiceImp;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class ProductosController {
	private static final Logger logger = LogManager.getLogger("errorLogger");

	@Autowired
	private ProductosService productosService;
	@Autowired
	private UtilisServiceImp utilisServiceImp;

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
	
	@PostMapping("/simuladorPrecioProducto")
	public TcProducto simuladorPrecioProducto(@RequestBody TcProducto tcProducto) {

		try {
			return utilisServiceImp.calcularPrecio(tcProducto);
		} catch (Exception e) {

			logger.error("Error al obtener el precio simulado" + e);
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
	
	@GetMapping("/obtenerHistoriaPrecioProducto")
	public List<TcHistoriaPrecioProducto> obtenerHistoriaPrecioProducto(@RequestParam() Long n_id) {

		try {
			return productosService.historiaPrecioProducto(n_id);
		} catch (Exception e) {

			logger.error("Error al obtener Historia precio Priducto" + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerProductoBodegas")
	public List<TwProductobodega> obtenerProductoBodegas(@RequestParam() Long id) {

		try {
			return productosService.consultaProductoBodega(id);
		} catch (Exception e) {

			logger.error("Error al obtener el stock de bodegas" + e);
		}
		return null;
	}
	@GetMapping("/obtenerInventarioEsp")
	public List<TwProductobodega> obtenerInventaroEsp(@RequestParam() Long idBodega, @RequestParam() Long idAnaquel, @RequestParam() Long idNivel) {

		try {
			return productosService.obtenerInventaroEsp(idBodega, idAnaquel, idNivel);
		} catch (Exception e) {

			logger.error("Error al obtener los productos d el ubicación " + e);
		}
		return null;
	}

}
