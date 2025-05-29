package com.refacFabela.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
	private LocalDateTime dFechaApertura;

	@Column(name = "d_fechaCierre")
	private LocalDateTime dFechaCierre;

	@Column(name = "n_estatus")
	private Integer nEstatus;
	
	@Column(name = "n_pagoEfectivo")
	private BigDecimal nPagoEfectivo;
    
	@Column(name = "n_pagoElectronico")
	private BigDecimal nPagoElectronico;
    
	@Column(name = "n_saldoCierre")
	private BigDecimal nSaldoCierre;
	
	@Column(name = "n_saldoFinal")
	private BigDecimal nSaldoFinal;

	@Column(name = "n_saldoInicial")
	private BigDecimal nSaldoInicial;
	
	@Column(name = "n_idUsuario")
	private Long nIdUsuario;

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

	
	public int getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}

	


	public LocalDateTime getdFechaApertura() {
		return dFechaApertura;
	}

	public void setdFechaApertura(LocalDateTime dFechaApertura) {
		this.dFechaApertura = dFechaApertura;
	}

	public LocalDateTime getdFechaCierre() {
		return dFechaCierre;
	}

	public void setdFechaCierre(LocalDateTime dFechaCierre) {
		this.dFechaCierre = dFechaCierre;
	}

	public BigDecimal getnPagoEfectivo() {
		return nPagoEfectivo;
	}

	public void setnPagoEfectivo(BigDecimal nPagoEfectivo) {
		this.nPagoEfectivo = nPagoEfectivo;
	}

	public BigDecimal getnPagoElectronico() {
		return nPagoElectronico;
	}

	public void setnPagoElectronico(BigDecimal nPagoElectronico) {
		this.nPagoElectronico = nPagoElectronico;
	}

	public BigDecimal getnSaldoCierre() {
		return nSaldoCierre;
	}

	public void setnSaldoCierre(BigDecimal nSaldoCierre) {
		this.nSaldoCierre = nSaldoCierre;
	}

	public BigDecimal getnSaldoFinal() {
		return nSaldoFinal;
	}

	public void setnSaldoFinal(BigDecimal nSaldoFinal) {
		this.nSaldoFinal = nSaldoFinal;
	}

	public BigDecimal getnSaldoInicial() {
		return nSaldoInicial;
	}

	public void setnSaldoInicial(BigDecimal nSaldoInicial) {
		this.nSaldoInicial = nSaldoInicial;
	}

	

	public Long getnIdUsuario() {
		return nIdUsuario;
	}

	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}

	public void setnEstatus(Integer nEstatus) {
		this.nEstatus = nEstatus;
	}

	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}

	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}

	
	

}