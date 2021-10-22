package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tc_clientes database table.
 * 
 */
@Entity
@Table(name="tc_clientes")
@NamedQuery(name="TcCliente.findAll", query="SELECT t FROM TcCliente t")
public class TcCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Temporal(TemporalType.DATE)
	private Date d_fechaCredito;

	@Column(name="n_estatus")
	private int nEstatus;

	private Long n_idUsuarioCredito;

	private double n_limiteCredito;

	@Column(name="s_correo")
	private String sCorreo;

	@Column(name="s_direccion")
	private String sDireccion;

	@Column(name="s_razon_social")
	private String sRazonSocial;

	@Column(name="s_rfc")
	private String sRfc;

	@Column(name="s_telefono")
	private String sTelefono;

	//bi-directional many-to-one association to TcUsuario
	@ManyToOne
	@JoinColumn(name="n_idUsuario")
	private TcUsuario tcUsuario;

	//bi-directional many-to-one association to TwCotizacione
	@OneToMany(mappedBy="tcCliente")
	private List<TwCotizacione> twCotizaciones;

	//bi-directional many-to-one association to TwVenta
	@OneToMany(mappedBy="tcCliente")
	private List<TwVenta> twVentas;

	public TcCliente() {
	}

	public Long getn_Id() {
		return this.nId;
	}

	public void setn_Id(Long nId) {
		this.nId = nId;
	}

	public Date getd_fechaCredito() {
		return this.d_fechaCredito;
	}

	public void setd_fechaCredito(Date d_fechaCredito) {
		this.d_fechaCredito = d_fechaCredito;
	}

	public int getn_Estatus() {
		return this.nEstatus;
	}

	public void setn_Estatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}

	public Long getn_idUsuarioCredito() {
		return this.n_idUsuarioCredito;
	}

	public void setn_idUsuarioCredito(Long n_idUsuarioCredito) {
		this.n_idUsuarioCredito = n_idUsuarioCredito;
	}

	public double getn_limiteCredito() {
		return this.n_limiteCredito;
	}

	public void setn_limiteCredito(double n_limiteCredito) {
		this.n_limiteCredito = n_limiteCredito;
	}

	public String gets_Correo() {
		return this.sCorreo;
	}

	public void sets_Correo(String sCorreo) {
		this.sCorreo = sCorreo;
	}

	public String gets_Direccion() {
		return this.sDireccion;
	}

	public void sets_Direccion(String sDireccion) {
		this.sDireccion = sDireccion;
	}

	public String gets_RazonSocial() {
		return this.sRazonSocial;
	}

	public void sets_RazonSocial(String sRazonSocial) {
		this.sRazonSocial = sRazonSocial;
	}

	public String gets_Rfc() {
		return this.sRfc;
	}

	public void sets_Rfc(String sRfc) {
		this.sRfc = sRfc;
	}

	public String gets_Telefono() {
		return this.sTelefono;
	}

	public void sets_Telefono(String sTelefono) {
		this.sTelefono = sTelefono;
	}

	public TcUsuario getTcUsuario() {
		return this.tcUsuario;
	}

	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}

	public List<TwCotizacione> getTwCotizaciones() {
		return this.twCotizaciones;
	}

	public void setTwCotizaciones(List<TwCotizacione> twCotizaciones) {
		this.twCotizaciones = twCotizaciones;
	}

	public TwCotizacione addTwCotizacione(TwCotizacione twCotizacione) {
		getTwCotizaciones().add(twCotizacione);
		twCotizacione.setTcCliente(this);

		return twCotizacione;
	}

	public TwCotizacione removeTwCotizacione(TwCotizacione twCotizacione) {
		getTwCotizaciones().remove(twCotizacione);
		twCotizacione.setTcCliente(null);

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
		twVenta.setTcCliente(this);

		return twVenta;
	}

	public TwVenta removeTwVenta(TwVenta twVenta) {
		getTwVentas().remove(twVenta);
		twVenta.setTcCliente(null);

		return twVenta;
	}

}