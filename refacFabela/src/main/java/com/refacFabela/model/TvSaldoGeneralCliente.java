package com.refacFabela.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tv_saldogeneralclie")
@NamedQuery(name = "TvSaldoGeneralCliente.findAll", query = "SELECT t FROM TvSaldoGeneralCliente t")
public class TvSaldoGeneralCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "n_idCliente")
	private Long nIdCliente;

	@Column(name = "n_limiteCredito")
	private BigDecimal nLimiteCredito;

	@Column(name = "n_saldo_total")
	private BigDecimal nSaldoTotal;

	@Column(name = "n_credito_disponible")
	private BigDecimal nCreditoDisponible;

	@Column(name = "n_saldo_utilizado")
	private BigDecimal nSaldoUtilizado;

	@Column(name = "n_avance_credito")
	private BigDecimal nAvanceCredito;

	@Column(name = "s_estatus")
	private String sEstatus;

	@Column(name = "n_abonos")
	private BigDecimal nAbonos;

	@Column(name = "n_total_venta")
	private BigDecimal nTotalVenta;

	@ManyToOne
	@JoinColumn(name = "n_idCliente", referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcCliente tcCliente;
	
	

	public TvSaldoGeneralCliente() {
	}

	

	public String getsEstatus() {
		return sEstatus;
	}

	public void setsEstatus(String sEstatus) {
		this.sEstatus = sEstatus;
	}



	public TcCliente getTcCliente() {
		return tcCliente;
	}

	public void setTcCliente(TcCliente tcCliente) {
		this.tcCliente = tcCliente;
	}

	public Long getnIdCliente() {
		return nIdCliente;
	}

	public void setnIdCliente(Long nIdCliente) {
		this.nIdCliente = nIdCliente;
	}



	public BigDecimal getnLimiteCredito() {
		return nLimiteCredito;
	}



	public void setnLimiteCredito(BigDecimal nLimiteCredito) {
		this.nLimiteCredito = nLimiteCredito;
	}



	public BigDecimal getnSaldoTotal() {
		return nSaldoTotal;
	}



	public void setnSaldoTotal(BigDecimal nSaldoTotal) {
		this.nSaldoTotal = nSaldoTotal;
	}



	public BigDecimal getnCreditoDisponible() {
		return nCreditoDisponible;
	}



	public void setnCreditoDisponible(BigDecimal nCreditoDisponible) {
		this.nCreditoDisponible = nCreditoDisponible;
	}



	public BigDecimal getnSaldoUtilizado() {
		return nSaldoUtilizado;
	}



	public void setnSaldoUtilizado(BigDecimal nSaldoUtilizado) {
		this.nSaldoUtilizado = nSaldoUtilizado;
	}



	public BigDecimal getnAvanceCredito() {
		return nAvanceCredito;
	}



	public void setnAvanceCredito(BigDecimal nAvanceCredito) {
		this.nAvanceCredito = nAvanceCredito;
	}



	public BigDecimal getnAbonos() {
		return nAbonos;
	}



	public void setnAbonos(BigDecimal nAbonos) {
		this.nAbonos = nAbonos;
	}



	public BigDecimal getnTotalVenta() {
		return nTotalVenta;
	}



	public void setnTotalVenta(BigDecimal nTotalVenta) {
		this.nTotalVenta = nTotalVenta;
	}

	

}
