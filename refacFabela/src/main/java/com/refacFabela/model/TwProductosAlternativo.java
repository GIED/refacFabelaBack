package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "tw_productos_alternativos")
@NamedQuery(name = "TwProductosAlternativo.findAll", query = "SELECT t FROM TwProductosAlternativo t")
public class TwProductosAlternativo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "n_estatus")
	private int nEstatus;

	private int n_idProductoAlternativo;

	// bi-directional many-to-one association to TcProducto
	@ManyToOne
	@JoinColumn(name = "n_idProducto")
	private TcProducto tcProducto;

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

	public int getN_idProductoAlternativo() {
		return n_idProductoAlternativo;
	}

	public void setN_idProductoAlternativo(int n_idProductoAlternativo) {
		this.n_idProductoAlternativo = n_idProductoAlternativo;
	}

	public TcProducto getTcProducto() {
		return tcProducto;
	}

	public void setTcProducto(TcProducto tcProducto) {
		this.tcProducto = tcProducto;
	}

}