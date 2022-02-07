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

	@Temporal(TemporalType.TIMESTAMP)
	private Date d_fechaApertura;

	@Temporal(TemporalType.TIMESTAMP)
	private Date d_fechaCierre;

	@Column(name = "n_estatus")
	private int nEstatus;

	private double n_pagoEfectivo;

	private double n_pagoElectronico;

	private double n_saldoCierre;

	private double n_saldoFinal;

	private double n_saldoInicial;

	// bi-directional many-to-one association to TcUsuario
	@ManyToOne
	@JoinColumn(name = "n_idUsuario")
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

	public Date getD_fechaApertura() {
		return d_fechaApertura;
	}

	public void setD_fechaApertura(Date d_fechaApertura) {
		this.d_fechaApertura = d_fechaApertura;
	}

	public Date getD_fechaCierre() {
		return d_fechaCierre;
	}

	public void setD_fechaCierre(Date d_fechaCierre) {
		this.d_fechaCierre = d_fechaCierre;
	}

	public int getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}

	public double getN_pagoEfectivo() {
		return n_pagoEfectivo;
	}

	public void setN_pagoEfectivo(double n_pagoEfectivo) {
		this.n_pagoEfectivo = n_pagoEfectivo;
	}

	public double getN_pagoElectronico() {
		return n_pagoElectronico;
	}

	public void setN_pagoElectronico(double n_pagoElectronico) {
		this.n_pagoElectronico = n_pagoElectronico;
	}

	public double getN_saldoCierre() {
		return n_saldoCierre;
	}

	public void setN_saldoCierre(double n_saldoCierre) {
		this.n_saldoCierre = n_saldoCierre;
	}

	public double getN_saldoFinal() {
		return n_saldoFinal;
	}

	public void setN_saldoFinal(double n_saldoFinal) {
		this.n_saldoFinal = n_saldoFinal;
	}

	public double getN_saldoInicial() {
		return n_saldoInicial;
	}

	public void setN_saldoInicial(double n_saldoInicial) {
		this.n_saldoInicial = n_saldoInicial;
	}

	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}

	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}

	@Override
	public String toString() {
		return "TwCaja [nId=" + nId + ", d_fechaApertura=" + d_fechaApertura + ", d_fechaCierre=" + d_fechaCierre
				+ ", nEstatus=" + nEstatus + ", n_pagoEfectivo=" + n_pagoEfectivo + ", n_pagoElectronico="
				+ n_pagoElectronico + ", n_saldoCierre=" + n_saldoCierre + ", n_saldoFinal=" + n_saldoFinal
				+ ", n_saldoInicial=" + n_saldoInicial + ", tcUsuario=" + tcUsuario + "]";
	}
	
	

}