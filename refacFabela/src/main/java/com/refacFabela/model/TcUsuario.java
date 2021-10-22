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

	public Long getn_Id() {
		return this.nId;
	}

	public void setn_Id(Long nId) {
		this.nId = nId;
	}

	public int getn_Perfil() {
		return this.nPerfil;
	}

	public void setn_Perfil(int nPerfil) {
		this.nPerfil = nPerfil;
	}

	public String gets_Claveuser() {
		return this.sClaveuser;
	}

	public void sets_Claveuser(String sClaveuser) {
		this.sClaveuser = sClaveuser;
	}

	public String gets_Nombreusuario() {
		return this.sNombreusuario;
	}

	public void sets_Nombreusuario(String sNombreusuario) {
		this.sNombreusuario = sNombreusuario;
	}

	public String gets_Password() {
		return this.sPassword;
	}

	public void sets_Password(String sPassword) {
		this.sPassword = sPassword;
	}

	public String gets_Usuario() {
		return this.sUsuario;
	}

	public void sets_Usuario(String sUsuario) {
		this.sUsuario = sUsuario;
	}

	public List<TcCliente> getTcClientes() {
		return this.tcClientes;
	}

	public void setTcClientes(List<TcCliente> tcClientes) {
		this.tcClientes = tcClientes;
	}

	public TcCliente addTcCliente(TcCliente tcCliente) {
		getTcClientes().add(tcCliente);
		tcCliente.setTcUsuario(this);

		return tcCliente;
	}

	public TcCliente removeTcCliente(TcCliente tcCliente) {
		getTcClientes().remove(tcCliente);
		tcCliente.setTcUsuario(null);

		return tcCliente;
	}

	public List<TcProducto> getTcProductos() {
		return this.tcProductos;
	}

	public void setTcProductos(List<TcProducto> tcProductos) {
		this.tcProductos = tcProductos;
	}

	public TcProducto addTcProducto(TcProducto tcProducto) {
		getTcProductos().add(tcProducto);
		tcProducto.setTcUsuario(this);

		return tcProducto;
	}

	public TcProducto removeTcProducto(TcProducto tcProducto) {
		getTcProductos().remove(tcProducto);
		tcProducto.setTcUsuario(null);

		return tcProducto;
	}

	public List<TwAbono> getTwAbonos() {
		return this.twAbonos;
	}

	public void setTwAbonos(List<TwAbono> twAbonos) {
		this.twAbonos = twAbonos;
	}

	public TwAbono addTwAbono(TwAbono twAbono) {
		getTwAbonos().add(twAbono);
		twAbono.setTcUsuario(this);

		return twAbono;
	}

	public TwAbono removeTwAbono(TwAbono twAbono) {
		getTwAbonos().remove(twAbono);
		twAbono.setTcUsuario(null);

		return twAbono;
	}

	public List<TwCaja> getTwCajas() {
		return this.twCajas;
	}

	public void setTwCajas(List<TwCaja> twCajas) {
		this.twCajas = twCajas;
	}

	public TwCaja addTwCaja(TwCaja twCaja) {
		getTwCajas().add(twCaja);
		twCaja.setTcUsuario(this);

		return twCaja;
	}

	public TwCaja removeTwCaja(TwCaja twCaja) {
		getTwCajas().remove(twCaja);
		twCaja.setTcUsuario(null);

		return twCaja;
	}

	public List<TwCotizacione> getTwCotizaciones() {
		return this.twCotizaciones;
	}

	public void setTwCotizaciones(List<TwCotizacione> twCotizaciones) {
		this.twCotizaciones = twCotizaciones;
	}

	public TwCotizacione addTwCotizacione(TwCotizacione twCotizacione) {
		getTwCotizaciones().add(twCotizacione);
		twCotizacione.setTcUsuario(this);

		return twCotizacione;
	}

	public TwCotizacione removeTwCotizacione(TwCotizacione twCotizacione) {
		getTwCotizaciones().remove(twCotizacione);
		twCotizacione.setTcUsuario(null);

		return twCotizacione;
	}

	public List<TwVenta> getTwVentas() {
		return this.twVentas;
	}

	public void setTwVentas(List<TwVenta> twVentas) {
		this.twVentas = twVentas;
	}

	public TwVenta addTwVenta(TwVenta twVenta) {
		getTwVentas().add(twVenta);
		twVenta.setTcUsuario(this);

		return twVenta;
	}

	public TwVenta removeTwVenta(TwVenta twVenta) {
		getTwVentas().remove(twVenta);
		twVenta.setTcUsuario(null);

		return twVenta;
	}

	public List<TwVentasProducto> getTwVentasProductos() {
		return this.twVentasProductos;
	}

	public void setTwVentasProductos(List<TwVentasProducto> twVentasProductos) {
		this.twVentasProductos = twVentasProductos;
	}

	public TwVentasProducto addTwVentasProducto(TwVentasProducto twVentasProducto) {
		getTwVentasProductos().add(twVentasProducto);
		twVentasProducto.setTcUsuario(this);

		return twVentasProducto;
	}

	public TwVentasProducto removeTwVentasProducto(TwVentasProducto twVentasProducto) {
		getTwVentasProductos().remove(twVentasProducto);
		twVentasProducto.setTcUsuario(null);

		return twVentasProducto;
	}

}