package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "tc_ganancia")
@NamedQuery(name = "TcGanancia.findAll", query = "SELECT t FROM TcGanancia t")
public class TcGanancia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "n_estatus")
	private int nEstatus;

	@Column(name = "n_ganancia")
	private double nGanancia;

	// bi-directional many-to-one association to TcProducto

	public TcGanancia() {
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

	public double getnGanancia() {
		return nGanancia;
	}

	public void setnGanancia(double nGanancia) {
		this.nGanancia = nGanancia;
	}

}