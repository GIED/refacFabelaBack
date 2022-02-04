package com.refacFabela.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tw_productos_alternativos")
public class TwProductosAlternativo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "n_estatus")
	private int nEstatus;
	@Column(name = "n_idProductoAlternativo")
	private Long nIdProductoAlternativo;
	@Column(name = "n_idProducto")
	private Long nIdProducto;

	// bi-directional many-to-one association to TcProducto
	@ManyToOne()
	@JoinColumn(name = "n_idProductoAlternativo", insertable = false, updatable = false)
	private TcProducto tcProductoAlternativo;

	public TwProductosAlternativo() {
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

	public Long getnIdProductoAlternativo() {
		return nIdProductoAlternativo;
	}

	public void setnIdProductoAlternativo(Long nIdProductoAlternativo) {
		this.nIdProductoAlternativo = nIdProductoAlternativo;
	}

	public Long getnIdProducto() {
		return nIdProducto;
	}

	public void setnIdProducto(Long nIdProducto) {
		this.nIdProducto = nIdProducto;
	}

	public TcProducto getTcProductoAlternativo() {
		return tcProductoAlternativo;
	}

	public void setTcProductoAlternativo(TcProducto tcProductoAlternativo) {
		this.tcProductoAlternativo = tcProductoAlternativo;
	}

	@Override
	public String toString() {
		return "TwProductosAlternativo [nId=" + nId + ", nEstatus=" + nEstatus + ", nIdProductoAlternativo="
				+ nIdProductoAlternativo + ", nIdProducto=" + nIdProducto + ", tcProductoAlternativo="
				+ tcProductoAlternativo + "]";
	}
	
	

}