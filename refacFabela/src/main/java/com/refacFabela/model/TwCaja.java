package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tw_caja")
@NamedQuery(name = "TwCaja.findAll", query = "SELECT t FROM TwCaja t")
public class TwCaja implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "d_fechaApertura")
	private Date dFechaApertura;

	@Column(name = "d_fechaCierre")
	private Date dFechaCierre;

	@Column(name = "n_estatus")
	private int nEstatus;
	
	@Column(name = "n_pagoEfectivo")
	private double nPagoEfectivo;
    
	@Column(name = "n_pagoElectronico")
	private double nPagoElectronico;
    
	@Column(name = "n_saldoCierre")
	private double nSaldoCierre;
	
	@Column(name = "n_saldoFinal")
	private double nSaldoFinal;

	@Column(name = "n_saldoInicial")
	private double nSaldoInicial;
	
	@Column(name = "n_idUsuario")
	private double nIdUsuario;

	// bi-directional many-to-one association to TcUsuario
	@ManyToOne
	@JoinColumn(name = "n_idUsuario", insertable = false, updatable = false)
	private TcUsuario tcUsuario;

	// bi-directional many-to-one association to TwVenta

	public TwCaja() {
	}

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Date getdFechaApertura() {
		return dFechaApertura;
	}

	public void setdFechaApertura(Date dFechaApertura) {
		this.dFechaApertura = dFechaApertura;
	}

	public Date getdFechaCierre() {
		return dFechaCierre;
	}

	public void setdFechaCierre(Date dFechaCierre) {
		this.dFechaCierre = dFechaCierre;
	}

	public int getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}

	public double getnPagoEfectivo() {
		return nPagoEfectivo;
	}

	public void setnPagoEfectivo(double nPagoEfectivo) {
		this.nPagoEfectivo = nPagoEfectivo;
	}

	

	public double getnPagoElectronico() {
		return nPagoElectronico;
	}

	public void setnPagoElectronico(double nPagoElectronico) {
		this.nPagoElectronico = nPagoElectronico;
	}

	public double getnSaldoCierre() {
		return nSaldoCierre;
	}

	public void setnSaldoCierre(double nSaldoCierre) {
		this.nSaldoCierre = nSaldoCierre;
	}

	public double getnSaldoFinal() {
		return nSaldoFinal;
	}

	public void setnSaldoFinal(double nSaldoFinal) {
		this.nSaldoFinal = nSaldoFinal;
	}

	public double getnSaldoInicial() {
		return nSaldoInicial;
	}

	public void setnSaldoInicial(double nSaldoInicial) {
		this.nSaldoInicial = nSaldoInicial;
	}

	public double getnIdUsuario() {
		return nIdUsuario;
	}

	public void setnIdUsuario(double nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}

	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}

	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}

	
	

}