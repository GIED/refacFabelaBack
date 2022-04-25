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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.refacFabela.model.TcAnaquel;
import com.refacFabela.model.TcBodega;
import com.refacFabela.model.TcCatalogogeneral;
import com.refacFabela.model.TcCategoria;
import com.refacFabela.model.TcCategoriaGeneral;
import com.refacFabela.model.TcClavesat;
import com.refacFabela.model.TcCp;
import com.refacFabela.model.TcEstatusVenta;
import com.refacFabela.model.TcFormapago;
import com.refacFabela.model.TcGanancia;
import com.refacFabela.model.TcNivel;
import com.refacFabela.model.TcRegimenFiscal;
import com.refacFabela.model.TcTipoVenta;
import com.refacFabela.model.TcUsocfdi;
import com.refacFabela.model.TwCaja;
import com.refacFabela.service.CajaService;
import com.refacFabela.service.CatalagosService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class CatalogosController {

	private static final Logger logger = LogManager.getLogger("errorLogger");

	@Autowired
	private CatalagosService catalagosService;
	@Autowired
	private CajaService cajaService;

	@PostMapping("/actualizarTipoCambio")
	public TcCatalogogeneral actualizaTipoCambio(@RequestBody TcCatalogogeneral tcCatalogogeneral) {
		try {
			return catalagosService.actualizarTipoCambio(tcCatalogogeneral);

		} catch (Exception e) {

			logger.error("Error al guardar Tipo de cambio" + e);
		}
		return null;
	}

	@PostMapping("/consultaTipoCambioId")
	public TcCatalogogeneral consultaTipoCambioId(@RequestBody TcCatalogogeneral tcCatalogogeneral) {
		try {
			return catalagosService.consultaTipoCambioId(tcCatalogogeneral);

		} catch (Exception e) {

			logger.error("Error al obtener tipo de cambio" + e);
		}
		return null;
	}

	@GetMapping("/catalogosClaveSat")
	public List<TcClavesat> consultaClaveSat() {
		try {

			return catalagosService.catalogoClaveSat();

		} catch (Exception e) {

			logger.error("Error al obtener catalogo de clave sat " + e);
		}
		return null;
	}

	@GetMapping("/catalogoCategoriaGeneral")
	public List<TcCategoriaGeneral> consultaCategoriaGeneral() {
		try {

			return catalagosService.catalogoCategoriaGeneral();

		} catch (Exception e) {

			logger.error("Error al obtener catalogo de categoria General " + e);
		}
		return null;
	}

	@GetMapping("/catalogoCategoriaId")
	public List<TcCategoria> consultaCategoriaId(@RequestParam() int id) {
		try {

			return catalagosService.catalogoCategoriaId(id);

		} catch (Exception e) {

			logger.error("Error al obtener catalogo de categoria " + e);
		}
		return null;
	}

	@GetMapping("/catalogoGanancia")
	public List<TcGanancia> consultaCatalogoGanancia() {
		try {

			return catalagosService.catalogoGanancia();

		} catch (Exception e) {

			logger.error("Error al obtener catalogo de ganancia " + e);
		}
		return null;
	}
	
	@GetMapping("/catalogoGananciaId")
	public TcGanancia consultaCatalogoGanancia(@RequestParam Long nId) {
		try {

			return catalagosService.catalogoGananciaId(nId);

		} catch (Exception e) {

			logger.error("Error al obtener catalogo de ganancia " + e);
		}
		return null;
	}

	@GetMapping("/catalogoAnaquel")
	public List<TcAnaquel> consultaCatalogoAnaquel() {
		try {

			return catalagosService.catalogoAnaquel();

		} catch (Exception e) {

			logger.error("Error al obtener catalogo de Anaquel " + e);
		}
		return null;
	}

	@GetMapping("/catalogoNivel")
	public List<TcNivel> consultaCatalogoNivel() {
		try {

			return catalagosService.catalogoNivel();

		} catch (Exception e) {

			logger.error("Error al obtener catalogo de Nivel " + e);
		}
		return null;
	}

	@GetMapping("/catalogoBodegas")
	public List<TcBodega> consultaCatalogoBodegas() {
		try {

			return catalagosService.catalogoBodegas();

		} catch (Exception e) {

			logger.error("Error al obtener catalogo de Bodegas " + e);
		}
		return null;
	}

	@GetMapping("/catalogoFormaPago")
	public List<TcFormapago> consultaCatalogoFormaPago() {
		try {

			return catalagosService.catalogoFormaPago();

		} catch (Exception e) {

			logger.error("Error al obtener catalogo de Forma de Pago " + e);
		}
		return null;
	}
	
	@GetMapping("/catalogoFormaPagoId")
	public TcFormapago consultaCatalogoFormaPagoId(@RequestParam Long nId) {
		try {

			return catalagosService.catalogoFormaPagoId(nId);

		} catch (Exception e) {

			logger.error("Error al obtener catalogo de Forma de Pago " + e);
		}
		return null;
	}

	@GetMapping("/catalogoTipoVenta")
	public List<TcTipoVenta> consultaCatalogoTipoVenta() {
		try {

			return catalagosService.catalagoTipoVenta();

		} catch (Exception e) {

			logger.error("Error al obtener catalogo de Tipo de Venta " + e);
		}
		return null;
	}

	@GetMapping("/catalogoUsoCfdi")
	public List<TcUsocfdi> consultaCatalogoUsoCfdi() {
		try {

			return catalagosService.catalagoUsoCfdi();

		} catch (Exception e) {

			logger.error("Error al obtener catalogo de Uso de Cfdi " + e);
		}
		return null;
	}
	
	@GetMapping("/cajaActiva")
	public TwCaja consultaCajaActiva() {
		try {

			return cajaService.obtenerCajaActiva();

		} catch (Exception e) {

			logger.error("Error al obtener la caja activa " + e);
		}
		return null;
	}
	
	@GetMapping("/catalogoEstatusId")
	public TcEstatusVenta consultaCajaActiva(@RequestParam Long nId) {
		try {

			return catalagosService.catalagoEstatusVentaId(nId);

		} catch (Exception e) {

			logger.error("Error al obtener la caja activa " + e);
		}
		return null;
	}
	
	@GetMapping("/catalogoCp")
	public List<TcCp> consultaCp(@RequestParam String cp) {
		try {

			return catalagosService.catalagoCp(cp);

		} catch (Exception e) {

			logger.error("Error al obtener la lista de cp " + e);
		}
		return null;
	}
	
	@GetMapping("/catalogoRegimenFiscal")
	public List<TcRegimenFiscal> consultaRegimenFiscal() {
		try {

			return catalagosService.catalagoRegimenFiscal();

		} catch (Exception e) {

			logger.error("Error al obtener la lista de Regimen Fiscal " + e);
		}
		return null;
	}

}
