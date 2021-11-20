package com.refacFabela.model;

import java.io.Serializable;
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
	private double nLimiteCredito;

	@Column(name = "n_saldo_total")
	private double nSaldoTotal;

	@Column(name = "n_credito_disponible")
	private double nCreditoDisponible;

	@Column(name = "n_saldo_utilizado")
	private double nSaldoUtilizado;

	@Column(name = "n_avance_credito")
	private double nAvanceCredito;

	@Column(name = "s_estatus")
	private String sEstatus;

	@Column(name = "n_abonos")
	private double nAbonos;

	@Column(name = "n_total_venta")
	private double nTotalVenta;

	@ManyToOne
	@JoinColumn(name = "n_idCliente", referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcCliente tcCliente;

	public TvSaldoGeneralCliente() {
	}

	public double getnAbonos() {
		return nAbonos;
	}

	public void setnAbonos(double nAbonos) {
		this.nAbonos = nAbonos;
	}

	public double getnTotalVenta() {
		return nTotalVenta;
	}

	public void setnTotalVenta(double nTotalVenta) {
		this.nTotalVenta = nTotalVenta;
	}

	public String getsEstatus() {
		return sEstatus;
	}

	public void setsEstatus(String sEstatus) {
		this.sEstatus = sEstatus;
	}

	public double getnSaldoUtilizado() {
		return nSaldoUtilizado;
	}

	public void setnSaldoUtilizado(double nSaldoUtilizado) {
		this.nSaldoUtilizado = nSaldoUtilizado;
	}

	public double getnAvanceCredito() {
		return nAvanceCredito;
	}

	public void setnAvanceCredito(double nAvanceCredito) {
		this.nAvanceCredito = nAvanceCredito;
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

	public double getnLimiteCredito() {
		return nLimiteCredito;
	}

	public void setnLimiteCredito(double nLimiteCredito) {
		this.nLimiteCredito = nLimiteCredito;
	}

	public double getnSaldoTotal() {
		return nSaldoTotal;
	}

	public void setnSaldoTotal(double nSaldoTotal) {
		this.nSaldoTotal = nSaldoTotal;
	}

	public double getnCreditoDisponible() {
		return nCreditoDisponible;
	}

	public void setnCreditoDisponible(double nCreditoDisponible) {
		this.nCreditoDisponible = nCreditoDisponible;
	}

}
