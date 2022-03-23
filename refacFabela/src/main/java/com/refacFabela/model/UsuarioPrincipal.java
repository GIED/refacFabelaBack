package com.refacFabela.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioPrincipal implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long nId;
	private String sClaveUser;
	private String sUsuario;
	private String sPassword;
	private String sNombreUsuario;
	private Integer nEstatus;
	private Collection<? extends GrantedAuthority> authorities;
	
	
	
	
	public UsuarioPrincipal(Long nId, String sClaveUser , String sUsuario, String sPassword, String sNombreUsuario
			, Integer nEstatus , Collection<? extends GrantedAuthority> authorities) {
		this.nId=nId;
		this.sClaveUser = sClaveUser;
		this.sUsuario = sUsuario;
		this.sPassword = sPassword;
		this.sNombreUsuario = sNombreUsuario;
		this.nEstatus=nEstatus;
		this.authorities = authorities;
	}
	
	/**
	 * se encarga de asignar los privilegios a cada usuario ya sea admin o user 
	 * @param usuario
	 * @return
	 */
	public static  UsuarioPrincipal build(TcUsuario usuario) {
		
		//obtine los roles
		List<GrantedAuthority> authorities = usuario.getRoles().stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getsRol().name()))
				.collect(Collectors.toList());
		
		//regresa el usuario con sus privilegios
		return new UsuarioPrincipal(usuario.getnId(),usuario.getsClaveUser(), usuario.getsUsuario(), usuario.getsPassword(), usuario.getsNombreUsuario(),usuario.getnEstatus(), authorities);
	}
	
	 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return sPassword;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return sUsuario;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getsClaveUser() {
		return sClaveUser;
	}


	public String getsNombreUsuario() {
		return sNombreUsuario;
	}

	public Integer getnEstatus() {
		return nEstatus;
	}

	public Long getnId() {
		return nId;
	}

	
	
	

	
	
	
	
	

}

