package com.refacFabela.service;

import java.util.List;
import java.util.Optional;

import com.refacFabela.model.TcUsuario;

public interface UsuarioService {

	public List<TcUsuario> obtenerUsuarios();
	public TcUsuario consultaUsuario(Long id);
	public TcUsuario guardaUsuario(TcUsuario tcUsuario);
	public String eliminarUsuario(Long id);
	
	public Optional<TcUsuario> getByNombreUsuario(String usuario);
	public boolean existsByNombreUsuario (String usuario);
	public void save(TcUsuario usuario);
	
}
