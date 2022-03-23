package com.refacFabela.dto;

import java.util.HashSet;
import java.util.Set;


public class NuevoUsuario {
	
	
	private String sClaveUser;
	private String sUsuario;
	private String sPassword;
	private String sNombreUsuario;
	private Integer nEstatus;
	private Set<String> roles = new HashSet<>();
	private String rfcDistribuidor;
	
	
	
	
	public String getsClaveUser() {
		return sClaveUser;
	}
	public void setsClaveUser(String sClaveUser) {
		this.sClaveUser = sClaveUser;
	}
	public String getsUsuario() {
		return sUsuario;
	}
	public void setsUsuario(String sUsuario) {
		this.sUsuario = sUsuario;
	}
	public String getsPassword() {
		return sPassword;
	}
	public void setsPassword(String sPassword) {
		this.sPassword = sPassword;
	}
	public String getsNombreUsuario() {
		return sNombreUsuario;
	}
	public void setsNombreUsuario(String sNombreUsuario) {
		this.sNombreUsuario = sNombreUsuario;
	}
	public Integer getnEstatus() {
		return nEstatus;
	}
	public void setnEstatus(Integer nEstatus) {
		this.nEstatus = nEstatus;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
	public String getRfcDistribuidor() {
		return rfcDistribuidor;
	}
	public void setRfcDistribuidor(String rfcDistribuidor) {
		this.rfcDistribuidor = rfcDistribuidor;
	}
	
	@Override
	public String toString() {
		return "NuevoUsuario [sClaveUser=" + sClaveUser + ", sUsuario=" + sUsuario + ", sPassword=" + sPassword
				+ ", sNombreUsuario=" + sNombreUsuario + ", nEstatus=" + nEstatus + ", roles=" + roles
				+ ", rfcDistribuidor=" + rfcDistribuidor + "]";
	}
	
	
	
	
	
	
	

}
