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

	@Column(name="s_categoria_general")
	private String sCategoriaGeneral;

	@Column(name="n_estatus")
	private int nEstatus;

	//bi-directional many-to-one association to TcProducto
	@OneToMany(mappedBy="tcCategoria")
	private List<TcProducto> tcProductos;

	public TcCategoria() {
	}

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public String getsCategoria() {
		return sCategoria;
	}

	public void setsCategoria(String sCategoria) {
		this.sCategoria = sCategoria;
	}

	public String getsCategoriaGeneral() {
		return sCategoriaGeneral;
	}

	public void setsCategoriaGeneral(String sCategoriaGeneral) {
		this.sCategoriaGeneral = sCategoriaGeneral;
	}

	public int getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}

	public List<TcProducto> getTcProductos() {
		return tcProductos;
	}

	public void setTcProductos(List<TcProducto> tcProductos) {
		this.tcProductos = tcProductos;
	}

	

}