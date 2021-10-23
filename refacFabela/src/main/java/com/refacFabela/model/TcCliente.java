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
	
	@Column(name="s_clave")
	private String sClave;

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

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Date getD_fechaCredito() {
		return d_fechaCredito;
	}

	public void setD_fechaCredito(Date d_fechaCredito) {
		this.d_fechaCredito = d_fechaCredito;
	}

	public int getnEstatus() {
		return nEstatus;
	}
	
	public String getsClave() {
		return sClave;
	}

	public void setsClave(String sClave) {
		this.sClave = sClave;
	}

	public void setnEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}

	public Long getN_idUsuarioCredito() {
		return n_idUsuarioCredito;
	}

	public void setN_idUsuarioCredito(Long n_idUsuarioCredito) {
		this.n_idUsuarioCredito = n_idUsuarioCredito;
	}

	public double getN_limiteCredito() {
		return n_limiteCredito;
	}

	public void setN_limiteCredito(double n_limiteCredito) {
		this.n_limiteCredito = n_limiteCredito;
	}

	public String getsCorreo() {
		return sCorreo;
	}

	public void setsCorreo(String sCorreo) {
		this.sCorreo = sCorreo;
	}

	public String getsDireccion() {
		return sDireccion;
	}

	public void setsDireccion(String sDireccion) {
		this.sDireccion = sDireccion;
	}

	public String getsRazonSocial() {
		return sRazonSocial;
	}

	public void setsRazonSocial(String sRazonSocial) {
		this.sRazonSocial = sRazonSocial;
	}

	public String getsRfc() {
		return sRfc;
	}

	public void setsRfc(String sRfc) {
		this.sRfc = sRfc;
	}

	public String getsTelefono() {
		return sTelefono;
	}

	public void setsTelefono(String sTelefono) {
		this.sTelefono = sTelefono;
	}

	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}

	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
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

	

}