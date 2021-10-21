package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tw_abonos database table.
 * 
 */
@Entity
@Table(name="tw_abonos")
@NamedQuery(name="TwAbono.findAll", query="SELECT t FROM TwAbono t")
public class TwAbono implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Temporal(TemporalType.DATE)
	@Column(name="d_fecha")
	private Date dFecha;

	@Column(name="n_abono")
	private double nAbono;

	@Column(name="n_estatus")
	private int nEstatus;

	//bi-directional many-to-one association to TcFormapago
	@ManyToOne
	@JoinColumn(name="n_idFormaPago")
	private TcFormapago tcFormapago;

	//bi-directional many-to-one association to TcUsuario
	@ManyToOne
	@JoinColumn(name="n_idUsuario")
	private TcUsuario tcUsuario;

	//bi-directional many-to-one association to TwVenta
	@ManyToOne
	@JoinColumn(name="n_idVenta")
	private TwVenta twVenta;

	public TwAbono() {
	}

	public Long getNId() {
		return this.nId;
	}

	public void setNId(Long nId) {
		this.nId = nId;
	}

	public Date getDFecha() {
		return this.dFecha;
	}

	public void setDFecha(Date dFecha) {
		this.dFecha = dFecha;
	}

	public double getNAbono() {
		return this.nAbono;
	}

	public void setNAbono(double nAbono) {
		this.nAbono = nAbono;
	}

	public int getNEstatus() {
		return this.nEstatus;
	}

	public void setNEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}

	public TcFormapago getTcFormapago() {
		return this.tcFormapago;
	}

	public void setTcFormapago(TcFormapago tcFormapago) {
		this.tcFormapago = tcFormapago;
	}

	public TcUsuario getTcUsuario() {
		return this.tcUsuario;
	}

	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}

	public TwVenta getTwVenta() {
		return this.twVenta;
	}

	public void setTwVenta(TwVenta twVenta) {
		this.twVenta = twVenta;
	}

}