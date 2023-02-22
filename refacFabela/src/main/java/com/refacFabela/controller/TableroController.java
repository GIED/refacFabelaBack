package com.refacFabela.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.refacFabela.model.TcClavesat;
import com.refacFabela.model.TvTotalesGeneralesTablero;
import com.refacFabela.model.VwVentaMesAno;
import com.refacFabela.model.VwVentaProductoAno;
import com.refacFabela.model.VwVentasAnoMesVendedores;
import com.refacFabela.model.VwVentasAnoVendedor;
import com.refacFabela.service.TableroService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class TableroController {
	private static final Logger logger = LogManager.getLogger("errorLogger");
	
	@Autowired
	private TableroService tableroService;
	
	@GetMapping("/totales-generales-tablero")
	public TvTotalesGeneralesTablero consultaTotalesGeneralesTablero() {
		try {

			return tableroService.consultaTotalesTablero();

		} catch (Exception e) {

			logger.error("Error al obtener datos generales " + e);
		}
		return null;
	}
	
	@GetMapping("/totales-por-mes-ano")
	public List<VwVentaMesAno> consultaVentasMesAno(@RequestParam String ano) {
		try {

			return tableroService.consultaVentaMesAno(ano);

		} catch (Exception e) {

			logger.error("Error al obtener datos generales " + e);
		}
		return null;
	}
	
	@GetMapping("/total_venta_producto-ano")
	public List<VwVentaProductoAno> consultaVentasProductoAno(@RequestParam String ano) {
		try {

			return tableroService.consultaVentaProductoAno(ano);

		} catch (Exception e) {

			logger.error("Error al obtener datos generales " + e);
		}
		return null;
	}
	
	@GetMapping("/total_venta_ano_vendedor")
	public List<VwVentasAnoVendedor> consultaVentasAnoVendedor(@RequestParam String ano) {
		try {

			return tableroService.consultaVentaAnoVendedor(ano);

		} catch (Exception e) {

			logger.error("Error al obtener datos de venta del año de los vendedores" + e);
		}
		return null;
	}
	
	@GetMapping("/total_venta_ano_mes_vendedor")
	public List<VwVentasAnoMesVendedores> consultaVentasAnoMesVendedor(@RequestParam String ano, @RequestParam Long id) {
		try {

			return tableroService.consultaVentaAnoMesVendedor(ano, id);

		} catch (Exception e) {

			logger.error("Error al obtener datos de venta del año de los vendedores" + e);
		}
		return null;
	}
}