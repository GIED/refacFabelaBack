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
import com.refacFabela.model.TcProveedore;
import com.refacFabela.service.ProveedoresService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class ProveedoresController {
	private static final Logger logger = LogManager.getLogger("errorLogger");

	@Autowired
	private ProveedoresService proveedoresService;

	@GetMapping("/obtenerProveedores")
	public List<TcProveedore> obtenerProveedores() {

		try {
			return proveedoresService.obtenerProveedores();
		} catch (Exception e) {

			logger.error("Error al obtener Proveedores" + e);
		}
		return null;
	}

	@GetMapping("/consultarProveedoresId")
	public TcProveedore consultaProveedoresId(HttpServletResponse response, @RequestParam() Long id) {

		try {
			return proveedoresService.consultaProveedorId(id);

		} catch (Exception e) {
			logger.error("Error al consultar Proveedor" + e);
		}
		return null;
	}

	@PostMapping("/guardarProveedores")
	public TcProveedore guardarProveedores(@RequestBody TcProveedore tcProveedores) {
		try {
			return proveedoresService.guardaProveedor(tcProveedores);

		} catch (Exception e) {

			logger.error("Error al obtener Proveedores" + e);
		}
		return null;
	}

	@GetMapping("/eliminarProveedor")
	public String eliminaProveedoresId(HttpServletResponse response, @RequestParam() Long id) {

		try {
			return proveedoresService.eliminaProveedor(id);
		} catch (Exception e) {
			logger.error("Error al eliminar Proveedor" + e);
		}
		return null;
	}

}
