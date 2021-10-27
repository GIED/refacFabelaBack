package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tw_cotizaciones database table.
 * 
 */
@Entity
@Table(name="tw_cotizaciones")
@NamedQuery(name="TwCotizacione.findAll", query="SELECT t FROM TwCotizacione t")
public class TwCotizacione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Temporal(TemporalType.DATE)
	@Column(name="d_fecha")
	private Date dFecha;

	@Column(name="n_estatus")
	private int nEstatus;

	private String s_folioCotizacion;

	//bi-directional many-to-one association to TcCliente
	@ManyToOne
	@JoinColumn(name="n_idCliente")
	private TcCliente tcCliente;

	//bi-directional many-to-one association to TcUsuario
	@ManyToOne
	@JoinColumn(name="n_idUsuario")
	private TcUsuario tcUsuario;

	
	public TwCotizacione() {
	}


	public Long getnId() {
		return nId;
	}


	public void setnId(Long nId) {
		this.nId = nId;
	}


	public Date getdFecha() {
		return dFecha;
	}


	public void setdFecha(Date dFecha) {
		this.dFecha = dFecha;
	}


	public int getnEstatus() {
		return nEstatus;
	}


	public void setnEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}


	public String getS_folioCotizacion() {
		return s_folioCotizacion;
	}


	public void setS_folioCotizacion(String s_folioCotizacion) {
		this.s_folioCotizacion = s_folioCotizacion;
	}


	public TcCliente getTcCliente() {
		return tcCliente;
	}


	public void setTcCliente(TcCliente tcCliente) {
		this.tcCliente = tcCliente;
	}


	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}


	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}

	

}