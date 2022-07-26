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
@Table(name = "tr_venta_cobro")
@NamedQuery(name = "TrVentaCobro.findAll", query = "SELECT t FROM TrVentaCobro t")
public class TrVentaCobro implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;		
	@Column(name = "n_id_venta")
	private Long nIdVenta;		
	@Column(name = "n_id_caja")
	private Long nIdCaja;
	@Column(name = "n_monto")
	private Double nMonto;
	@Column(name = "d_fecha")
	private Date dFecha;
	@Column(name = "n_estatus")
	private Long nEstatus;
	@Column(name = "n_id_forma_pago")
	private Long nIdFormaPago;

	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "n_id_caja", updatable = false, insertable = false, nullable = false)
	private TwCaja twCaja;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "n_id_venta", updatable = false, insertable = false, nullable = false)
	private TwVenta twVenta;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "n_id_forma_pago", updatable = false, insertable = false, nullable = false)
	private TcFormapago tcFormapago;
	
	
		
	
	public TrVentaCobro() {
		
	}




	public Long getnId() {
		return nId;
	}




	public void setnId(Long nId) {
		this.nId = nId;
	}




	public Long getnIdVenta() {
		return nIdVenta;
	}




	public void setnIdVenta(Long nIdVenta) {
		this.nIdVenta = nIdVenta;
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




	public Date getdFecha() {
		return dFecha;
	}




	public void setdFecha(Date dFecha) {
		this.dFecha = dFecha;
	}




	public Long getnEstatus() {
		return nEstatus;
	}




	public void setnEstatus(Long nEstatus) {
		this.nEstatus = nEstatus;
	}




	public TwCaja getTwCaja() {
		return twCaja;
	}




	public void setTwCaja(TwCaja twCaja) {
		this.twCaja = twCaja;
	}




	public TwVenta getTwVenta() {
		return twVenta;
	}




	public void setTwVenta(TwVenta twVenta) {
		this.twVenta = twVenta;
	}




	public Long getnIdFormaPago() {
		return nIdFormaPago;
	}




	public void setnIdFormaPago(Long nIdFormaPago) {
		this.nIdFormaPago = nIdFormaPago;
	}




	public TcFormapago getTcFormapago() {
		return tcFormapago;
	}




	public void setTcFormapago(TcFormapago tcFormapago) {
		this.tcFormapago = tcFormapago;
	}




	@Override
	public String toString() {
		return "TrVentaCobro [nId=" + nId + ", nIdVenta=" + nIdVenta + ", nIdCaja=" + nIdCaja + ", nMonto=" + nMonto
				+ ", dFecha=" + dFecha + ", nEstatus=" + nEstatus + ", nIdFormaPago=" + nIdFormaPago + ", twCaja="
				+ twCaja + ", twVenta=" + twVenta + ", tcFormapago=" + tcFormapago + "]";
	}




	
	
	
	
	

}
