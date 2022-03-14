package com.refacFabela.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.refacFabela.dto.NuevoUsuario;
import com.refacFabela.enums.RolNombre;
import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TcRol;
import com.refacFabela.model.TcUsuario;
import com.refacFabela.repository.UsuariosRepository;
import com.refacFabela.service.ClienteService;
import com.refacFabela.service.RolService;
import com.refacFabela.service.UsuarioService;

@Service
@Transactional
public class UsuarioServiceImp implements UsuarioService {

	@Autowired
	public UsuariosRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private ClienteService clienteService;

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
	public void save(NuevoUsuario nuevoUsuario) {
		
		TcUsuario usuario = new TcUsuario(nuevoUsuario.getsClaveUser(), nuevoUsuario.getsUsuario(), passwordEncoder.encode(nuevoUsuario.getsPassword()), nuevoUsuario.getsNombreUsuario(), nuevoUsuario.getnEstatus() );
		
		usuario.setRoles(asignaRol(nuevoUsuario));
		
		TcUsuario newUsuario = usuarioRepository.save(usuario); 
		
		if (!nuevoUsuario.getRfcDistribuidor().equals("no aplica")) {
			
			TcCliente tcCliente= this.clienteService.consultaClienteRfc(nuevoUsuario.getRfcDistribuidor());
			tcCliente.setnIdUsuario(newUsuario.getnId());
			this.clienteService.guardarCliente(tcCliente);
		}
		
		
		
	}
	
	private Set<TcRol> asignaRol(NuevoUsuario nuevoUsuario){
		
		Set<TcRol> roles = new HashSet<>();	
		
		//roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
		
		if (nuevoUsuario.getRoles().contains("admin")){
			roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
		}
		if (nuevoUsuario.getRoles().contains("venta")){
			roles.add(rolService.getByRolNombre(RolNombre.ROLE_VENTA).get());
		}
		if (nuevoUsuario.getRoles().contains("distribuidor")){
			roles.add(rolService.getByRolNombre(RolNombre.ROLE_DISTRIBUIDOR).get());
		}
		if (nuevoUsuario.getRoles().contains("almacen")){
			roles.add(rolService.getByRolNombre(RolNombre.ROLE_ALMACEN).get());
		}
		if (nuevoUsuario.getRoles().contains("caja")){
			roles.add(rolService.getByRolNombre(RolNombre.ROLE_CAJA).get());
		}
		
		
		return roles;
		
	}

}
