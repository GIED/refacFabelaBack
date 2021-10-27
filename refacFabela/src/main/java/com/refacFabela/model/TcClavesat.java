package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tc_clavesat database table.
 * 
 */
@Entity
@Table(name="tc_clavesat")
@NamedQuery(name="TcClavesat.findAll", query="SELECT t FROM TcClavesat t")
public class TcClavesat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Column(name="n_estatus")
	private int nEstatus;

	@Column(name="s_clavesat")
	private String sClavesat;

	@Column(name="s_descripcion")
	private String sDescripcion;

	

	public TcClavesat() {
	}

	public Long getNId() {
		return this.nId;
	}

	public void setNId(Long nId) {
		this.nId = nId;
	}

	public int getNEstatus() {
		return this.nEstatus;
	}

	public void setNEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}

	public String getSClavesat() {
		return this.sClavesat;
	}

	public void setSClavesat(String sClavesat) {
		this.sClavesat = sClavesat;
	}

	public String getSDescripcion() {
		return this.sDescripcion;
	}

	public void setSDescripcion(String sDescripcion) {
		this.sDescripcion = sDescripcion;
	}

	

}