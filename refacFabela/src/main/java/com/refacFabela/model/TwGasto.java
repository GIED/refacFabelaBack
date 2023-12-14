package com.refacFabela.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tw_gasto")
@NamedQuery(name = "TwGasto.findAll", query = "SELECT t FROM TwGasto t")
public class TwGasto implements Serializable {
	private static final long serialVersionUID = 1L;
		
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "n_id_caja")
	private Long nIdCaja;

	@Column(name = "n_id_gasto")
	private Long nIdGasto;
	
	@Column(name = "s_descripcion")
	private String sDescripcion;

	@Column(name = "d_fecha")
	private Date dFecha;
	
	@Column(name = "n_estatus")
	private Integer nEstatus;
	
	@Column(name = "n_id_usuario")
	private Long nIdUsuario;
	
	@Column(name = "n_monto")
	private Double nMonto;
	
	@ManyToOne
	@JoinColumn(name = "n_id_usuario", updatable = false, insertable = false)
	private TcUsuario tcUsuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "n_id_caja", updatable = false, insertable = false, nullable = false)
	private TwCaja twCaja;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "n_id_gasto", updatable = false, insertable = false, nullable = false)
	private TcGasto tcGasto;	
	
	
	public TwGasto() {
	}


	public Long getnId() {
		return nId;
	}


	public void setnId(Long nId) {
		this.nId = nId;
	}


	public Long getnIdCaja() {
		return nIdCaja;
	}


	public void setnIdCaja(Long nIdCaja) {
		this.nIdCaja = nIdCaja;
	}


	public Double getnMonto() {
		return nMonto;
	}


	public void setnMonto(Double nMonto) {
		this.nMonto = nMonto;
	}


	public Long getnIdGasto() {
		return nIdGasto;
	}


	public void setnIdGasto(Long nIdGasto) {
		this.nIdGasto = nIdGasto;
	}


	public String getsDescripcion() {
		return sDescripcion;
	}


	public void setsDescripcion(String sDescripcion) {
		this.sDescripcion = sDescripcion;
	}


	public Date getdFecha() {
		return dFecha;
	}


	public void setdFecha(Date dFecha) {
		this.dFecha = dFecha;
	}


	public Integer getnEstatus() {
		return nEstatus;
	}


	public void setnEstatus(Integer nEstatus) {
		this.nEstatus = nEstatus;
	}


	public Long getnIdUsuario() {
		return nIdUsuario;
	}


	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}


	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}


	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}


	public TwCaja getTwCaja() {
		return twCaja;
	}


	public void setTwCaja(TwCaja twCaja) {
		this.twCaja = twCaja;
	}


	public TcGasto getTcGasto() {
		return tcGasto;
	}


	public void setTcGasto(TcGasto tcGasto) {
		this.tcGasto = tcGasto;
	}


	@Override
	public String toString() {
		return "TwGasto [nId=" + nId + ", nIdCaja=" + nIdCaja + ", nIdGasto=" + nIdGasto + ", sDescripcion="
				+ sDescripcion + ", dFecha=" + dFecha + ", nEstatus=" + nEstatus + ", nIdUsuario=" + nIdUsuario
				+ ", tcUsuario=" + tcUsuario + ", twCaja=" + twCaja + ", tcGasto=" + tcGasto + ", getnId()=" + getnId()
				+ ", getnIdCaja()=" + getnIdCaja() + ", getnIdGasto()=" + getnIdGasto() + ", getsDescripcion()="
				+ getsDescripcion() + ", getdFecha()=" + getdFecha() + ", getnEstatus()=" + getnEstatus()
				+ ", getnIdUsuario()=" + getnIdUsuario() + ", getTcUsuario()=" + getTcUsuario() + ", getTwCaja()="
				+ getTwCaja() + ", getTcGasto()=" + getTcGasto() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
	
	

}
