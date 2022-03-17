package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "tw_productobodega")
@NamedQuery(name = "TwProductobodega.findAll", query = "SELECT t FROM TwProductobodega t")
public class TwProductobodega implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name = "n_idbodega")
	private Long nIdBodega;
	
	@Column(name = "n_idproducto")
	private Long nIdProducto;	

	@Column(name = "n_cantidad")
	private Integer nCantidad;

	@Column(name = "n_estatus")
	private Long nEstatus;
	
	@Column(name = "n_idnivel")
	private Long nIdNivel;
	
	@Column(name = "n_idanaquel")
	private Long nIdAnaquel;
	
	// bi-directional many-to-one association to TcAnaquel
	@ManyToOne
	@JoinColumn(name = "n_idanaquel", insertable=false, updatable=false)
	private TcAnaquel tcAnaquel;

	// bi-directional many-to-one association to TcBodega
	@ManyToOne
	@JoinColumn(name = "n_idbodega", insertable=false, updatable=false)
	private TcBodega tcBodega;

	// bi-directional many-to-one association to TcNivel
	@ManyToOne
	@JoinColumn(name = "n_idnivel", insertable=false, updatable=false)
	private TcNivel tcNivel;

	// bi-directional many-to-one association to TcProducto
	@ManyToOne
	@JoinColumn(name = "n_idproducto", insertable=false, updatable=false)
	private TcProducto tcProducto;

	public TwProductobodega() {
	}

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Long getnIdBodega() {
		return nIdBodega;
	}

	public void setnIdBodega(Long nIdBodega) {
		this.nIdBodega = nIdBodega;
	}

	public Long getnIdProducto() {
		return nIdProducto;
	}

	public void setnIdProducto(Long nIdProducto) {
		this.nIdProducto = nIdProducto;
	}

	

	public Integer getnCantidad() {
		return nCantidad;
	}

	public void setnCantidad(Integer nCantidad) {
		this.nCantidad = nCantidad;
	}

	public Long getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(Long nEstatus) {
		this.nEstatus = nEstatus;
	}

	public Long getnIdNivel() {
		return nIdNivel;
	}

	public void setnIdNivel(Long nIdNivel) {
		this.nIdNivel = nIdNivel;
	}

	public Long getnIdAnaquel() {
		return nIdAnaquel;
	}

	public void setnIdAnaquel(Long nIdAnaquel) {
		this.nIdAnaquel = nIdAnaquel;
	}

	public TcAnaquel getTcAnaquel() {
		return tcAnaquel;
	}

	public void setTcAnaquel(TcAnaquel tcAnaquel) {
		this.tcAnaquel = tcAnaquel;
	}

	public TcBodega getTcBodega() {
		return tcBodega;
	}

	public void setTcBodega(TcBodega tcBodega) {
		this.tcBodega = tcBodega;
	}

	public TcNivel getTcNivel() {
		return tcNivel;
	}

	public void setTcNivel(TcNivel tcNivel) {
		this.tcNivel = tcNivel;
	}

	public TcProducto getTcProducto() {
		return tcProducto;
	}

	public void setTcProducto(TcProducto tcProducto) {
		this.tcProducto = tcProducto;
	}

	@Override
	public String toString() {
		return "TwProductobodega [nId=" + nId + ", nIdBodega=" + nIdBodega + ", nIdProducto=" + nIdProducto
				+ ", nCantidad=" + nCantidad + ", nEstatus=" + nEstatus + ", nIdNivel=" + nIdNivel + ", nIdAnaquel="
				+ nIdAnaquel + ", tcAnaquel=" + tcAnaquel + ", tcBodega=" + tcBodega + ", tcNivel=" + tcNivel
				+ ", tcProducto=" + tcProducto + "]";
	}
	
	

	
}