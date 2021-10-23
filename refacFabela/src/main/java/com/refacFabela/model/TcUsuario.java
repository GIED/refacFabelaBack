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
@NamedQuery(name="TcUsuario.findAll", query="SELECT t FROM TcUsuario t")
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

	//bi-directional many-to-one association to TcCliente
	@OneToMany(mappedBy="tcUsuario")
	private List<TcCliente> tcClientes;

	//bi-directional many-to-one association to TcProducto
	@OneToMany(mappedBy="tcUsuario")
	private List<TcProducto> tcProductos;

	//bi-directional many-to-one association to TwAbono
	@OneToMany(mappedBy="tcUsuario")
	private List<TwAbono> twAbonos;

	//bi-directional many-to-one association to TwCaja
	@OneToMany(mappedBy="tcUsuario")
	private List<TwCaja> twCajas;

	//bi-directional many-to-one association to TwCotizacione
	@OneToMany(mappedBy="tcUsuario")
	private List<TwCotizacione> twCotizaciones;

	//bi-directional many-to-one association to TwVenta
	@OneToMany(mappedBy="tcUsuario")
	private List<TwVenta> twVentas;

	//bi-directional many-to-one association to TwVentasProducto
	@OneToMany(mappedBy="tcUsuario")
	private List<TwVentasProducto> twVentasProductos;

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

	public List<TcCliente> getTcClientes() {
		return tcClientes;
	}

	public void setTcClientes(List<TcCliente> tcClientes) {
		this.tcClientes = tcClientes;
	}

	public List<TcProducto> getTcProductos() {
		return tcProductos;
	}

	public void setTcProductos(List<TcProducto> tcProductos) {
		this.tcProductos = tcProductos;
	}

	public List<TwAbono> getTwAbonos() {
		return twAbonos;
	}

	public void setTwAbonos(List<TwAbono> twAbonos) {
		this.twAbonos = twAbonos;
	}

	public List<TwCaja> getTwCajas() {
		return twCajas;
	}

	public void setTwCajas(List<TwCaja> twCajas) {
		this.twCajas = twCajas;
	}

	public List<TwCotizacione> getTwCotizaciones() {
		return twCotizaciones;
	}

	public void setTwCotizaciones(List<TwCotizacione> twCotizaciones) {
		this.twCotizaciones = twCotizaciones;
	}

	public List<TwVenta> getTwVentas() {
		return twVentas;
	}

	public void setTwVentas(List<TwVenta> twVentas) {
		this.twVentas = twVentas;
	}

	public List<TwVentasProducto> getTwVentasProductos() {
		return twVentasProductos;
	}

	public void setTwVentasProductos(List<TwVentasProducto> twVentasProductos) {
		this.twVentasProductos = twVentasProductos;
	}

	
}