package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tc_usuarios database table.
 * 
 */
@Entity
@Table(name="tc_usuarios")
public class TcUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Column(name="n_perfil")
	private int nPerfil;

	@Column(name="s_claveuser")
	private String sClaveuser;

	@Column(name="s_nombreusuario")
	private String sNombreusuario;

	@Column(name="s_password")
	private String sPassword;

	@Column(name="s_usuario")
	private String sUsuario;
	
	@Column(name="n_estatus")
	private Integer nEstatus;

	

	public TcUsuario() {
	}

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public int getnPerfil() {
		return nPerfil;
	}

	public void setnPerfil(int nPerfil) {
		this.nPerfil = nPerfil;
	}

	public String getsClaveuser() {
		return sClaveuser;
	}

	public void setsClaveuser(String sClaveuser) {
		this.sClaveuser = sClaveuser;
	}

	public String getsNombreusuario() {
		return sNombreusuario;
	}

	public void setsNombreusuario(String sNombreusuario) {
		this.sNombreusuario = sNombreusuario;
	}

	public String getsPassword() {
		return sPassword;
	}

	public void setsPassword(String sPassword) {
		this.sPassword = sPassword;
	}

	public String getsUsuario() {
		return sUsuario;
	}

	public void setsUsuario(String sUsuario) {
		this.sUsuario = sUsuario;
	}

	public Integer getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(Integer nEstatus) {
		this.nEstatus = nEstatus;
	}

	
	
}