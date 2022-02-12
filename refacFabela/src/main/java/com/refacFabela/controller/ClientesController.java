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
import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TvSaldoGeneralCliente;
import com.refacFabela.service.ClienteService;


@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class ClientesController {
	private static final Logger logger = LogManager.getLogger("errorLogger");

	@Autowired
	private ClienteService clienteService;

	@GetMapping("/obtenerClientes")
	public List<TcCliente> obtenerClientes() {

		try {
			
			return clienteService.obtenerCliente();

		} catch (Exception e) {
			logger.error("Error al recuperar clientes" + e);
		}

		return null;
	}

	@PostMapping("guardarCliente")
	public TcCliente guardarCliente(@RequestBody TcCliente tcCliente) {

		try {
			return clienteService.guardarCliente(tcCliente);

		} catch (Exception e) {
			logger.error("Error al guardar cliente" + e);
		}

		return null;
	}

	@GetMapping("consultaClienteId")
	public TcCliente consultaClienteId(HttpServletResponse response, @RequestParam() Long id) {

		try {
			return clienteService.consultaClienteId(id);

		} catch (Exception e) {
			logger.error("Error al consultar cliente" + e);
		}

		return null;
	}
	
	@GetMapping("consultaClienteIdUsuario")
	public TcCliente consultaClienteIdUsuario(HttpServletResponse response, @RequestParam() Long id) {

		try {
			return clienteService.consultaClienteIdUsuario(id);

		} catch (Exception e) {
			logger.error("Error al consultar cliente" + e);
		}

		return null;
	}
	
	@GetMapping("consultaClienteLike")
	public List<TcCliente> consultaClienteLike(HttpServletResponse response, @RequestParam() String clienteBuscar) {

		try {
			return clienteService.consultaClienteLike(clienteBuscar);

		} catch (Exception e) {
			logger.error("Error al consultar cliente" + e);
		}

		return null;
	}

	@GetMapping("eliminaClienteId")
	public String eliminarClienteId(HttpServletResponse response, @RequestParam() Long id) {

		try {
			return clienteService.eliminarClienteId(id);

		} catch (Exception e) {
			logger.error("Error al eliminar cliente" + e);
		}

		return null;
	}
	@GetMapping("consultaClienteIdSaldo")
	public TvSaldoGeneralCliente consultaClienteIdSaldo(HttpServletResponse response, @RequestParam() Long id) {

		try {
			return clienteService.consultaClienteIdSaldo(id);

		} catch (Exception e) {
			logger.error("Error al consultar cliente" + e);
		}

		return null;
	}
	@GetMapping("consultaClienteSaldo")
	public List<TvSaldoGeneralCliente> consultaClienteIdSaldo() {

		try {
			return clienteService.consultaClienteSaldo();

		} catch (Exception e) {
			logger.error("Error al consultar cliente" + e);
		}

		return null;
	}
	

}
