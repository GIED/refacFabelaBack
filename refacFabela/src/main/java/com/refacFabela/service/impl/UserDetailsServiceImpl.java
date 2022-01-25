package com.refacFabela.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TcUsuario;
import com.refacFabela.model.UsuarioPrincipal;
import com.refacFabela.service.UsuarioService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public UserDetails loadUserByUsername(String sUsuario) throws UsernameNotFoundException {
		TcUsuario usuario = usuarioService.getByNombreUsuario(sUsuario).get();
		return UsuarioPrincipal.build(usuario);
	}  

}
