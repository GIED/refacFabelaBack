package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tc_categoria database table.
 * 
 */
@Entity
@Table(name="tc_categoria")
@NamedQuery(name="TcCategoria.findAll", query="SELECT t FROM TcCategoria t")
public class TcCategoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Column(name="s_categoria")
	private String sCategoria;

	@Column(name="s_categoria_genetal")
	private String sCategoriaGenetal;

	@Column(name="s_estatus")
	private int sEstatus;

	//bi-directional many-to-one association to TcProducto
	@OneToMany(mappedBy="tcCategoria")
	private List<TcProducto> tcProductos;

	public TcCategoria() {
	}

	public Long getNId() {
		return this.nId;
	}

	public void setNId(Long nId) {
		this.nId = nId;
	}

	public String getSCategoria() {
		return this.sCategoria;
	}

	public void setSCategoria(String sCategoria) {
		this.sCategoria = sCategoria;
	}

	public String getSCategoriaGenetal() {
		return this.sCategoriaGenetal;
	}

	public void setSCategoriaGenetal(String sCategoriaGenetal) {
		this.sCategoriaGenetal = sCategoriaGenetal;
	}

	public int getSEstatus() {
		return this.sEstatus;
	}

	public void setSEstatus(int sEstatus) {
		this.sEstatus = sEstatus;
	}

	public List<TcProducto> getTcProductos() {
		return this.tcProductos;
	}

	public void setTcProductos(List<TcProducto> tcProductos) {
		this.tcProductos = tcProductos;
	}

	public TcProducto addTcProducto(TcProducto tcProducto) {
		getTcProductos().add(tcProducto);
		tcProducto.setTcCategoria(this);

		return tcProducto;
	}

	public TcProducto removeTcProducto(TcProducto tcProducto) {
		getTcProductos().remove(tcProducto);
		tcProducto.setTcCategoria(null);

		return tcProducto;
	}

}