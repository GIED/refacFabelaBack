package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tw_productobodega database table.
 * 
 */
@Entity
@Table(name="tw_productobodega")
@NamedQuery(name="TwProductobodega.findAll", query="SELECT t FROM TwProductobodega t")
public class TwProductobodega implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Column(name="n_cantidad")
	private int nCantidad;

	@Column(name="n_estatus")
	private int nEstatus;

	//bi-directional many-to-one association to TcAnaquel
	@ManyToOne
	@JoinColumn(name="n_idanaquel")
	private TcAnaquel tcAnaquel;

	//bi-directional many-to-one association to TcBodega
	@ManyToOne
	@JoinColumn(name="n_idbodega")
	private TcBodega tcBodega;

	//bi-directional many-to-one association to TcNivel
	@ManyToOne
	@JoinColumn(name="n_idnivel")
	private TcNivel tcNivel;

	//bi-directional many-to-one association to TcProducto
	@ManyToOne
	@JoinColumn(name="n_idproducto")
	private TcProducto tcProducto;

	public TwProductobodega() {
	}

	public Long getNId() {
		return this.nId;
	}

	public void setNId(Long nId) {
		this.nId = nId;
	}

	public int getNCantidad() {
		return this.nCantidad;
	}

	public void setNCantidad(int nCantidad) {
		this.nCantidad = nCantidad;
	}

	public int getNEstatus() {
		return this.nEstatus;
	}

	public void setNEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}

	public TcAnaquel getTcAnaquel() {
		return this.tcAnaquel;
	}

	public void setTcAnaquel(TcAnaquel tcAnaquel) {
		this.tcAnaquel = tcAnaquel;
	}

	public TcBodega getTcBodega() {
		return this.tcBodega;
	}

	public void setTcBodega(TcBodega tcBodega) {
		this.tcBodega = tcBodega;
	}

	public TcNivel getTcNivel() {
		return this.tcNivel;
	}

	public void setTcNivel(TcNivel tcNivel) {
		this.tcNivel = tcNivel;
	}

	public TcProducto getTcProducto() {
		return this.tcProducto;
	}

	public void setTcProducto(TcProducto tcProducto) {
		this.tcProducto = tcProducto;
	}

}