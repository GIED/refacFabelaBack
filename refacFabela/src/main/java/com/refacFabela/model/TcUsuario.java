package com.refacFabela.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name = "tc_usuarios")
public class TcUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "s_claveuser")
	private String sClaveUser;
	
	@Column(name = "s_usuario")
	private String sUsuario;
	
	@Column(name = "s_password")
	private String sPassword;

	@Column(name = "s_nombreUsuario")
	private String sNombreUsuario;


	@Column(name = "n_estatus")
	private Integer nEstatus;
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name= "tr_usuario_rol", joinColumns =  @JoinColumn(name = "usuario_id") , inverseJoinColumns = @JoinColumn(name = "rol_id"))
	private Set<TcRol> roles = new HashSet<>();

	public TcUsuario() {
	}
	
	public TcUsuario(String sClaveUser, String sUsuario, String sPassword, String sNombreUsuario, Integer nEstatus) {
		this.sClaveUser = sClaveUser;
		this.sUsuario = sUsuario;
		this.sPassword = sPassword;
		this.sNombreUsuario = sNombreUsuario;
		this.nEstatus = nEstatus;
	}



	public Long getnId() {
		return nId;
	}



	public void setnId(Long nId) {
		this.nId = nId;
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



	



	public String getsClaveUser() {
		return sClaveUser;
	}



	public void setsClaveUser(String sClaveUser) {
		this.sClaveUser = sClaveUser;
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



	public Set<TcRol> getRoles() {
		return roles;
	}



	public void setRoles(Set<TcRol> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "TcUsuario [nId=" + nId + ", sClaveUser=" + sClaveUser + ", sUsuario=" + sUsuario + ", sPassword="
				+ sPassword + ", sNombreUsuario=" + sNombreUsuario + ", nEstatus=" + nEstatus + ", roles=" + roles
				+ "]";
	}
	
	



	

}