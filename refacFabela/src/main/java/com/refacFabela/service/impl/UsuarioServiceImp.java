package com.refacFabela.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TcUsuario;
import com.refacFabela.repository.UsuariosRepository;
import com.refacFabela.service.UsuarioService;

@Service
@Transactional
public class UsuarioServiceImp implements UsuarioService {

	@Autowired
	public UsuariosRepository usuarioRepository;

	@Override
	public List<TcUsuario> obtenerUsuarios() {

		return usuarioRepository.findAll();
	}

	@Override
	public TcUsuario consultaUsuario(Long id) {

		return usuarioRepository.findById(id).get();
	}

	@Override
	public TcUsuario guardaUsuario(TcUsuario tcUsuario) {

		return usuarioRepository.save(tcUsuario);
	}

	@Override
	public String eliminarUsuario(Long id) {
		usuarioRepository.deleteById(id);
		return "Usuario Borrado";
	}
	
	//nuevos métodos para authenticacion

	@Override
	public Optional<TcUsuario> getByNombreUsuario(String usuario) {
		return usuarioRepository.findBysUsuario(usuario);
	}

	@Override
	public boolean existsByNombreUsuario(String usuario) {
		return usuarioRepository.existsBysUsuario(usuario);
	}

	@Override
	public void save(TcUsuario usuario) {
		usuarioRepository.save(usuario); 
		
	}

}
