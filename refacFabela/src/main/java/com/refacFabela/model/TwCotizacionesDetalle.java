package com.refacFabela.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tv_cotizacionDetalle")
@NamedQuery(name = "TwCotizacionesDetalle.findAll", query = "SELECT t FROM TwCotizacionesDetalle t")
public class TwCotizacionesDetalle implements Serializable {
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
	
	@Column(name = "n_totalCotizacion")
	private BigDecimal nTotalCotizacion;
	
	@Column(name = "n_vigencia")
	private int nVigencia;
	

	// bi-directional many-to-one association to TcCliente
	@ManyToOne
	@JoinColumn(name = "n_idCliente", insertable = false, updatable = false)
	private TcCliente tcCliente;

	// bi-directional many-to-one association to TcUsuario
	@ManyToOne
	@JoinColumn(name = "n_idUsuario", insertable = false, updatable = false)
	private TcUsuario tcUsuario;

	public TwCotizacionesDetalle() {
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

	

	
	public LocalDateTime getdFecha() {
		return dFecha;
	}

	public void setdFecha(LocalDateTime dFecha) {
		this.dFecha = dFecha;
	}

	public BigDecimal getnTotalCotizacion() {
		return nTotalCotizacion;
	}

	public void setnTotalCotizacion(BigDecimal nTotalCotizacion) {
		this.nTotalCotizacion = nTotalCotizacion;
	}

	public int getnVigencia() {
		return nVigencia;
	}

	public void setnVigencia(int nVigencia) {
		this.nVigencia = nVigencia;
	}

	
	

	
}