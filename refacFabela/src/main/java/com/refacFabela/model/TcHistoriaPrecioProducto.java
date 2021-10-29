package com.refacFabela.model;

import java.io.Serializable;
import java.sql.Timestamp;
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
@Table(name = "tw_histroriaPrecioProd")
@NamedQuery(name = "TcHistoriaPrecioProducto.findAll", query = "SELECT t FROM TcHistoriaPrecioProducto t")
public class TcHistoriaPrecioProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "n_idProducto")
	private Long nIdProducto;

	@Column(name = "n_precio")
	private double nPrecio;

	@Column(name = "s_moneda")
	private String sMoneda;

	@Column(name = "n_idGanancia")
	private Long nIdGanancia;

	@Column(name = "n_idusuario")
	private Long nIdusuario;

	@Column(name = "d_fecha")
	private Date dFecha;

	// bi-directional many-to-one association to TcGanancia
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "n_IdGanancia", referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcGanancia tcGanancia;

	// bi-directional many-to-one association to TcUsuario
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "n_idusuario", referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcUsuario tcUsuario;

	// bi-directional many-to-one association to TcUsuario
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "n_idProducto", referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcProducto tcProducto;

	public TcHistoriaPrecioProducto() {

	}

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	

	public Long getnIdProducto() {
		return nIdProducto;
	}

	public void setnIdProducto(Long nIdProducto) {
		this.nIdProducto = nIdProducto;
	}

	public double getnPrecio() {
		return nPrecio;
	}

	public void setnPrecio(double nPrecio) {
		this.nPrecio = nPrecio;
	}

	public String getsMoneda() {
		return sMoneda;
	}

	public void setsMoneda(String sMoneda) {
		this.sMoneda = sMoneda;
	}

	

	public Long getnIdGanancia() {
		return nIdGanancia;
	}

	public void setnIdGanancia(Long nIdGanancia) {
		this.nIdGanancia = nIdGanancia;
	}

	

	public Long getnIdusuario() {
		return nIdusuario;
	}

	public void setnIdusuario(Long nIdusuario) {
		this.nIdusuario = nIdusuario;
	}

	

	public Date getdFecha() {
		return dFecha;
	}

	public void setdFecha(Date dFecha) {
		this.dFecha = dFecha;
	}

	public TcGanancia getTcGanancia() {
		return tcGanancia;
	}

	public void setTcGanancia(TcGanancia tcGanancia) {
		this.tcGanancia = tcGanancia;
	}

	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}

	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}

	public TcProducto getTcProducto() {
		return tcProducto;
	}

	public void setTcProducto(TcProducto tcProducto) {
		this.tcProducto = tcProducto;
	}

	
	
	

}
