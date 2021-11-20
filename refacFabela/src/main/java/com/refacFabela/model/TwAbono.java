package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tw_abonos")
@NamedQuery(name = "TwAbono.findAll", query = "SELECT t FROM TwAbono t")
public class TwAbono implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name = "n_idVenta")
	private Long nIdVenta;

	@Temporal(TemporalType.DATE)
	@Column(name = "d_fecha")
	private Date dFecha;

	@Column(name = "n_abono")
	private double nAbono;

	@Column(name = "n_estatus")
	private int nEstatus;

	// bi-directional many-to-one association to TcFormapago
	@ManyToOne
	@JoinColumn(name = "n_idFormaPago")
	private TcFormapago tcFormapago;

	// bi-directional many-to-one association to TcUsuario
	@ManyToOne
	@JoinColumn(name = "n_idUsuario")
	private TcUsuario tcUsuario;

	// bi-directional many-to-one association to TwVenta
	@ManyToOne
	@JoinColumn(name = "n_idVenta", updatable = false, insertable = false)
	private TwVenta twVenta;

	public TwAbono() {
	}
	

	public Long getnIdVenta() {
		return nIdVenta;
	}


	public void setnIdVenta(Long nIdVenta) {
		this.nIdVenta = nIdVenta;
	}


	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Date getdFecha() {
		return dFecha;
	}

	public void setdFecha(Date dFecha) {
		this.dFecha = dFecha;
	}

	public double getnAbono() {
		return nAbono;
	}

	public void setnAbono(double nAbono) {
		this.nAbono = nAbono;
	}

	public int getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}

	public TcFormapago getTcFormapago() {
		return tcFormapago;
	}

	public void setTcFormapago(TcFormapago tcFormapago) {
		this.tcFormapago = tcFormapago;
	}

	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}

	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}

	public TwVenta getTwVenta() {
		return twVenta;
	}

	public void setTwVenta(TwVenta twVenta) {
		this.twVenta = twVenta;
	}

}