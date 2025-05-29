package com.refacFabela.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "vw_saldo_venta_favor_disponible")
@NamedQuery(name = "VwSaldoVentaFavorDisponible.findAll", query = "SELECT t FROM VwSaldoVentaFavorDisponible t")
public class VwSaldoVentaFavorDisponible implements Serializable {
	private static final long serialVersionUID = 1L ;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_idVenta")
	private Long nIdVenta;
	
	@Column(name = "n_total_cancelado")
	private BigDecimal nTotalCancelado;

	@Column(name = "n_total_usado")
	private BigDecimal nTotalUsado;

	@Column(name = "n_saldo_disponible")
	private BigDecimal nSaldoDisponible;
	

	public Long getnIdVenta() {
		return nIdVenta;
	}

	public void setnIdVenta(Long nIdVenta) {
		this.nIdVenta = nIdVenta;
	}

	public BigDecimal getnTotalCancelado() {
		return nTotalCancelado;
	}

	public void setnTotalCancelado(BigDecimal nTotalCancelado) {
		this.nTotalCancelado = nTotalCancelado;
	}

	public BigDecimal getnTotalUsado() {
		return nTotalUsado;
	}

	public void setnTotalUsado(BigDecimal nTotalUsado) {
		this.nTotalUsado = nTotalUsado;
	}

	public BigDecimal getnSaldoDisponible() {
		return nSaldoDisponible;
	}

	public void setnSaldoDisponible(BigDecimal nSaldoDisponible) {
		this.nSaldoDisponible = nSaldoDisponible;
	}

	
	
	
	
	

}
