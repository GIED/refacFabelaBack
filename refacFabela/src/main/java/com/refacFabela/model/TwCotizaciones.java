package com.refacFabela.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;


@Entity
@Table(name = "tw_cotizaciones")
@NamedQuery(name = "TwCotizaciones.findAll", query = "SELECT t FROM TwCotizaciones t")
public class TwCotizaciones implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name = "n_idCliente")
	private Long nIdCliente;
	
	@Column(name = "n_idUsuario")
	private Long nIdUsuario;

	
	@Column(name = "d_fecha")
	private LocalDateTime dFecha;

	@Column(name = "n_estatus")
	private int nEstatus;
	
	@Column(name = "s_folioCotizacion")
	private String sFolioCotizacion;

	// bi-directional many-to-one association to TcCliente
	@ManyToOne
	@JoinColumn(name = "n_idCliente", insertable = false, updatable = false)
	private TcCliente tcCliente;

	// bi-directional many-to-one association to TcUsuario
	@ManyToOne
	@JoinColumn(name = "n_idUsuario", insertable = false, updatable = false)
	private TcUsuario tcUsuario;

	public TwCotizaciones() {
	}

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Long getnIdCliente() {
		return nIdCliente;
	}

	public void setnIdCliente(Long nIdCliente) {
		this.nIdCliente = nIdCliente;
	}

	public Long getnIdUsuario() {
		return nIdUsuario;
	}

	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}

	

	public LocalDateTime getdFecha() {
		return dFecha;
	}

	public void setdFecha(LocalDateTime dFecha) {
		this.dFecha = dFecha;
	}

	public int getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}

	public String getsFolioCotizacion() {
		return sFolioCotizacion;
	}

	public void setsFolioCotizacion(String sFolioCotizacion) {
		this.sFolioCotizacion = sFolioCotizacion;
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