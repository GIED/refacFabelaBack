package com.refacFabela.model;

import java.io.Serializable;

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

	
	
}