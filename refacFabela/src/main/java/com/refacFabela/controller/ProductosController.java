package com.refacFabela.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.refacFabela.model.TcHistoriaPrecioProducto;
import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TvStockProducto;
import com.refacFabela.model.TvStockProductoHist;
import com.refacFabela.model.TwHistoriaIngresoProducto;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.model.TwProductosAlternativo;
import com.refacFabela.model.TwVentaProductoCancela;
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
	@GetMapping("/obtenerProductoId")
	public List<TcProducto> obtenerProductoId(@RequestParam() Long nId) {

		try {
			return productosService.obtenerProductoId(nId);
		} catch (Exception e) {

			logger.error("Error al obtener los Productos" + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerProductoBeanId")
	public TcProducto obtenerProductoBeanId(@RequestParam() Long nId) {

		try {
			return productosService.obtenerProductoBeanId(nId);
		} catch (Exception e) {

			logger.error("Error al obtener el producto" + e);
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
	public List<TcProducto> obtenerProductosLike(@RequestParam() String producto) {

		try {
			System.out.println("producto: " + producto);
			return productosService.obtenerProductoLike(producto);
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
	
	@GetMapping("/obtenerHistoriaIngresoProducto")
	public List<TwHistoriaIngresoProducto> obtenerHistoriaIngresoProducto(@RequestParam() Long n_id) {

		try {
			return productosService.historiaIngresoProducto(n_id);
		} catch (Exception e) {

			logger.error("Error al obtener Historia ingreso Producto" + e);
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
	@GetMapping("/obtenerProductoBodega")
	public TwProductobodega obtenerProductoBodega(@RequestParam() Long id, @RequestParam() Long idBodega) {

		try {
			return productosService.consultaProductoBod(id, idBodega);
		} catch (Exception e) {

			logger.error("Error al obtener el stock de bodegas" + e);
		}
		return null;
	}
	
	@PostMapping("/guardarProductoBodega")
	public TwProductobodega guardarProductoBodega(@RequestBody TwProductobodega twProductobodega) {

		try {
			return productosService.guardarProductoBodega(twProductobodega);
		} catch (Exception e) {

			logger.error("Error al guardar producto factura" + e);
		}
		return null;
	}

	@GetMapping("/obtenerInventarioEsp")
	public List<TwProductobodega> obtenerInventaroEsp(@RequestParam() Long idBodega, @RequestParam() Long idAnaquel,
			@RequestParam() Long idNivel) {

		try {
			return productosService.obtenerInventaroEsp(idBodega, idAnaquel, idNivel);
		} catch (Exception e) {

			logger.error("Error al obtener los productos d el ubicación " + e);
		}
		return null;
	}

	@GetMapping("/obtenerProductosAlternativos")
	public List<TwProductosAlternativo> obtenerProductosAlternativos(@RequestParam() Long nId) {

		try {
			return productosService.obtenerProductosAlternativos(nId);
		} catch (Exception e) {

			logger.error("Error al obtener los Productos alternativos" + e);
		}
		return null;
	}
	@GetMapping("/obtenerProductosAlternativosDescuento")
	public List<TwProductosAlternativo> obtenerProductosAlternativosDescueto(@RequestParam() Long nId, @RequestParam() Long nIdCliente) {

		try {
			
			
			return productosService.obtenerProductosAlternativosDescuento(nId, nIdCliente);
		} catch (Exception e) {

			logger.error("Error al obtener los Productos alternativos" + e);
		}
		return null;
	}

	@PostMapping("/guardarProductoAlternativo")
	public TwProductosAlternativo obtenerProductosNoParteLike(@RequestBody TwProductosAlternativo twProductosAlternativo) {

		try {

			System.out.println(twProductosAlternativo);

			return productosService.guardarProductoAlternativo(twProductosAlternativo);
		} catch (Exception e) {

			logger.error("Error al obtener los Productos" + e);
		}
		return null;
	}

	@GetMapping("/obtenerTotalBodegasIdProducto")
	public TvStockProducto obtenerProductoIdBodegas(@RequestParam() Long id) {

		try {
			return productosService.obtenerStockProductoId(id);
		} catch (Exception e) {

			logger.error("Error al obtener el stock de bodegas" + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerHistorialStockProducto")
	public List<TvStockProductoHist> obtenerHistorialStockProducto(@RequestParam() Long id) {

		try {
			return productosService.obtenerStockProductoHist(id);
		} catch (Exception e) {

			logger.error("Error al obtener el stock de bodegas" + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerProductosCancelaId")
	public List<TwVentaProductoCancela> obtenerProductosCancelaId(@RequestParam() Long id) {

		try {
			return productosService.obtenerProductosCancelaId(id);
		} catch (Exception e) {

			logger.error("Error al obtener los productos cancelados" + e);
		}
		return null;
	}
	
	@PostMapping("/guardarProductoGeneral")
	public TcProducto guardaProducto(@RequestBody TcProducto tcProducto) {

		try {

			return productosService.guardarProductoGeneral(tcProducto);
		} catch (Exception e) {

			logger.error("Error al obtener los Productos" + e);
		}
		return null;
	}

	
	@GetMapping(value = "/obtenerImagenProducto", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> obtenerImagenBase64(@RequestParam String ruta) {
	    String base64 = productosService.obtenerImagenBase64(ruta);
	    if (base64 != null) {
	        Map<String, String> respuesta = new HashMap<>();
	        respuesta.put("imagenBase64", "data:image/jpeg;base64," + base64);
	        return ResponseEntity.ok(respuesta);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/getProductByNoParteAndIdMarca")
	public TcProducto getProductByNoParteAndIdMarca(@RequestParam() String noParte, Long nIdMarca) {

		try {
			return productosService.getProductoByNoParteAndIdMarca(noParte, nIdMarca);
		} catch (Exception e) {

			logger.error("Error al obtener Producto por noParte y idMarca" + e);
		}
		return null;
	}
	
	
	

}
