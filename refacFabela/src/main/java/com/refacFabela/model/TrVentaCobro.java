package com.refacFabela.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tr_venta_cobro")
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
	private BigDecimal nMonto;
	@Column(name = "d_fecha")
	private LocalDateTime dFecha;
	@Column(name = "n_estatus")
	private Long nEstatus;
	@Column(name = "n_id_forma_pago")
	private Long nIdFormaPago;

	@Column(name = "s_referencia")
	private String sReferencia;
	@Column(name = "s_numero_autorizacion")
	private String sNumeroAutorizacion;
	@Column(name = "s_ultimos4_tarjeta")
	private String sUltimos4Tarjeta;
	@Column(name = "n_id_cuenta_bancaria")
	private Long nIdCuentaBancaria;

	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "n_id_caja", updatable = false, insertable = false, nullable = false)
	private TwCaja twCaja;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "n_id_venta", updatable = false, insertable = false, nullable = false)
	private TwVenta twVenta;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "n_id_forma_pago", updatable = false, insertable = false, nullable = false)
	private TcFormapago tcFormapago;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "n_id_cuenta_bancaria", updatable = false, insertable = false)
	private TcCuentaBancaria tcCuentaBancaria;

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

	

	public BigDecimal getnMonto() {
		return nMonto;
	}

	public void setnMonto(BigDecimal nMonto) {
		this.nMonto = nMonto;
	}

	

	public LocalDateTime getdFecha() {
		return dFecha;
	}

	public void setdFecha(LocalDateTime dFecha) {
		this.dFecha = dFecha;
	}

	public Long getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(Long nEstatus) {
		this.nEstatus = nEstatus;
	}

	public Long getnIdFormaPago() {
		return nIdFormaPago;
	}

	public void setnIdFormaPago(Long nIdFormaPago) {
		this.nIdFormaPago = nIdFormaPago;
	}

	public String getsReferencia() {
		return sReferencia;
	}

	public void setsReferencia(String sReferencia) {
		this.sReferencia = sReferencia;
	}

	public String getsNumeroAutorizacion() {
		return sNumeroAutorizacion;
	}

	public void setsNumeroAutorizacion(String sNumeroAutorizacion) {
		this.sNumeroAutorizacion = sNumeroAutorizacion;
	}

	public String getsUltimos4Tarjeta() {
		return sUltimos4Tarjeta;
	}

	public void setsUltimos4Tarjeta(String sUltimos4Tarjeta) {
		this.sUltimos4Tarjeta = sUltimos4Tarjeta;
	}

	public Long getnIdCuentaBancaria() {
		return nIdCuentaBancaria;
	}

	public void setnIdCuentaBancaria(Long nIdCuentaBancaria) {
		this.nIdCuentaBancaria = nIdCuentaBancaria;
	}

	public TcCuentaBancaria getTcCuentaBancaria() {
		return tcCuentaBancaria;
	}

	public void setTcCuentaBancaria(TcCuentaBancaria tcCuentaBancaria) {
		this.tcCuentaBancaria = tcCuentaBancaria;
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

	public TcFormapago getTcFormapago() {
		return tcFormapago;
	}

	public void setTcFormapago(TcFormapago tcFormapago) {
		this.tcFormapago = tcFormapago;
	}
	
	
	
	

}
