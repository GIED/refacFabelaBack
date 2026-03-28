package com.refacFabela.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import com.refacFabela.dto.JwtDto;
import com.refacFabela.dto.LoginUsuario;
import com.refacFabela.dto.Mensaje;
import com.refacFabela.jwt.JwtProvider;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(new Mensaje("Campos incorrectos o vacíos"), HttpStatus.BAD_REQUEST);
		}
		try {
			Authentication authentication =
					authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(loginUsuario.getsUsuario(), loginUsuario.getsPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtProvider.generateToken(authentication);
			JwtDto jwtDto = new JwtDto(jwt);
			return new ResponseEntity<>(jwtDto, HttpStatus.OK);
		} catch (BadCredentialsException e) {
			return new ResponseEntity<>(new Mensaje("Usuario o contraseña incorrectos. Verifica tus datos e intenta nuevamente."), HttpStatus.UNAUTHORIZED);
		} catch (DisabledException e) {
			return new ResponseEntity<>(new Mensaje("Tu cuenta está desactivada. Contacta al administrador."), HttpStatus.UNAUTHORIZED);
		} catch (LockedException e) {
			return new ResponseEntity<>(new Mensaje("Tu cuenta está bloqueada. Contacta al administrador."), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return new ResponseEntity<>(new Mensaje("Error al iniciar sesión. Intenta de nuevo."), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(@RequestBody JwtDto jwtDto) {
		try {
			String token = jwtProvider.refreshToken(jwtDto);
			JwtDto jwt = new JwtDto(token);
			return new ResponseEntity<>(jwt, HttpStatus.OK);
		} catch (IllegalArgumentException | ParseException e) {
			return new ResponseEntity<>(new Mensaje("La sesión ya no es válida. Inicia sesión nuevamente."), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return new ResponseEntity<>(new Mensaje("No fue posible refrescar la sesión. Intenta nuevamente."), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

