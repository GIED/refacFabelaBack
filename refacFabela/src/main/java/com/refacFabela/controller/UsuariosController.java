package com.refacFabela.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.refacFabela.dto.Mensaje;
import com.refacFabela.dto.NuevoUsuario;
import com.refacFabela.enums.RolNombre;
import com.refacFabela.model.TcRol;
import com.refacFabela.model.TcUsuario;
import com.refacFabela.service.RolService;
import com.refacFabela.service.UsuarioService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class UsuariosController {

	private static final Logger logger = LogManager.getLogger("errorLogger");
	
	@Autowired
	private UsuarioService usuarioService;
	
	

	@GetMapping("/obtenerUsuarios")
	public List<TcUsuario> obtenerUsuarios() {
		try {

			return usuarioService.obtenerUsuarios();

		} catch (Exception e) {
			logger.error("Error al recuperar Usuarios" + e);
		}

		return null;
	}
	
	

	@GetMapping("/consultarUsuarioId")
	public TcUsuario consultaUsuarioId(HttpServletResponse response, @RequestParam() Long id) {
		try {

			return usuarioService.consultaUsuario(id);

		} catch (Exception e) {
			logger.error("Error al consultar Usuario" + e);
		}

		return null;
	}
	
	@PostMapping("/nuevo")
	public ResponseEntity<?> nuevo(@RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
		
		System.out.println(nuevoUsuario);
		
		if (bindingResult.hasErrors()) {
			
			return new ResponseEntity<>(new Mensaje("campos invalidos"), HttpStatus.BAD_REQUEST);
			
		}
		
		
		if (usuarioService.existsByNombreUsuario(nuevoUsuario.getsUsuario())) {
	return new ResponseEntity<>(new Mensaje("ese nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
	}
		
		
		usuarioService.save(nuevoUsuario);
		
		return new ResponseEntity<>(new Mensaje("usuario guardado"), HttpStatus.CREATED);
		
	}

	@PostMapping("/guardarUsuario")
	public NuevoUsuario guardarUsuario(@RequestBody NuevoUsuario tcUsuario) {
		try {
		
             
			return usuarioService.guardaUsuario(tcUsuario);

		} catch (Exception e) {
			logger.error("Error al guardar Usuario" + e);
		}

		return null;
	}

	@GetMapping("/eliminarUsuario")
	public String eliminarUsuario(HttpServletResponse response, @RequestParam() Long id) {
		try {

			return usuarioService.eliminarUsuario(id);

		} catch (Exception e) {
			logger.error("Error al eliminarUsuario" + e);
		}

		return null;
	}

}
