package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tc_ganancia database table.
 * 
 */
@Entity
@Table(name="tc_ganancia")
@NamedQuery(name="TcGanancia.findAll", query="SELECT t FROM TcGanancia t")
public class TcGanancia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Column(name="n_estatus")
	private int nEstatus;

	@Column(name="n_ganancia")
	private double nGanancia;

	//bi-directional many-to-one association to TcProducto
	

	public TcGanancia() {
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

	public double getNGanancia() {
		return this.nGanancia;
	}

	public void setNGanancia(double nGanancia) {
		this.nGanancia = nGanancia;
	}

	

}