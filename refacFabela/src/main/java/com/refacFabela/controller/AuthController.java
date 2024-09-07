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
	public ResponseEntity<JwtDto> login (@RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return new ResponseEntity(new Mensaje("campos incorrectos"), HttpStatus.BAD_REQUEST);
		}
		
		Authentication authentication = 
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getsUsuario(), loginUsuario.getsPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtProvider.generateToken(authentication);
		JwtDto jwtDto = new JwtDto(jwt);
		return new ResponseEntity(jwtDto, HttpStatus.OK);
		
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<JwtDto> refresh(@RequestBody JwtDto jwtDto) throws ParseException{
		System.out.println(jwtDto.getToken());
		String token = jwtProvider.refreshToken(jwtDto);
		JwtDto jwt = new JwtDto(token);
		return new ResponseEntity(jwt, HttpStatus.OK);
	}

}

