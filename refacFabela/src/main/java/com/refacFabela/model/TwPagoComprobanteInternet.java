package com.refacFabela.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tw_pago_comprobante_internet")
public class TwPagoComprobanteInternet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name = "n_idCotizacion")
	private Long nIdCotizacion;
	
	@Column(name = "n_idCliente")
	private Long nIdCliente;
	
	@Column(name= "s_comprobante")
	private String sComprobante;

	@Column(name = "n_status")
	private int nStatus;
	
	@Column(name="s_observaciones")
	private String sObservaciones;
	
	@Column(name="s_fechaCarga")
	private LocalDateTime dFechaCarga;
	
	@Column(name="s_fechaValidacion")
	private LocalDateTime dFechaValidacion;
	
	
	
	

	// bi-directional many-to-one association to TcCliente
	@ManyToOne
	@JoinColumn(name = "n_idCotizacion", insertable = false, updatable = false)
	private TwCotizacionesDetalle twCotizacionesDetalle;
	
	// bi-directional many-to-one association to TcCliente
	@ManyToOne
	@JoinColumn(name = "n_idCliente", insertable = false, updatable = false)
	private TcCliente tcCliente;

	

	public TwPagoComprobanteInternet() {
	}



	public Long getnId() {
		return nId;
	}



	public void setnId(Long nId) {
		this.nId = nId;
	}



	public Long getnIdCotizacion() {
		return nIdCotizacion;
	}



	public void setnIdCotizacion(Long nIdCotizacion) {
		this.nIdCotizacion = nIdCotizacion;
	}



	public Long getnIdCliente() {
		return nIdCliente;
	}



	public void setnIdCliente(Long nIdCliente) {
		this.nIdCliente = nIdCliente;
	}



	public String getsComprobante() {
		return sComprobante;
	}



	public void setsComprobante(String sComprobante) {
		this.sComprobante = sComprobante;
	}



	public int getnStatus() {
		return nStatus;
	}



	public void setnStatus(int nStatus) {
		this.nStatus = nStatus;
	}



	public TwCotizacionesDetalle getTwCotizacionesDetalle() {
		return twCotizacionesDetalle;
	}



	public void setTwCotizacionesDetalle(TwCotizacionesDetalle twCotizacionesDetalle) {
		this.twCotizacionesDetalle = twCotizacionesDetalle;
	}



	public TcCliente getTcCliente() {
		return tcCliente;
	}



	public void setTcCliente(TcCliente tcCliente) {
		this.tcCliente = tcCliente;
	}



	public String getsObservaciones() {
		return sObservaciones;
	}



	public void setsObservaciones(String sObservaciones) {
		this.sObservaciones = sObservaciones;
	}



	



	public LocalDateTime getdFechaCarga() {
		return dFechaCarga;
	}



	public void setdFechaCarga(LocalDateTime dFechaCarga) {
		this.dFechaCarga = dFechaCarga;
	}



	public LocalDateTime getdFechaValidacion() {
		return dFechaValidacion;
	}



	public void setdFechaValidacion(LocalDateTime dFechaValidacion) {
		this.dFechaValidacion = dFechaValidacion;
	}



	@Override
	public String toString() {
		return "TwPagoComprobanteInternet [nId=" + nId + ", nIdCotizacion=" + nIdCotizacion + ", nIdCliente="
				+ nIdCliente + ", sComprobante=" + sComprobante + ", nStatus=" + nStatus + ", sObservaciones="
				+ sObservaciones + ", dFechaCarga=" + dFechaCarga + ", dFechaValidacion=" + dFechaValidacion
				+ ", twCotizacionesDetalle=" + twCotizacionesDetalle + ", tcCliente=" + tcCliente + "]";
	}
	
	

	
	
	
	
}