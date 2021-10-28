package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "tc_catalogogeneral")
@NamedQuery(name = "TcCatalogogeneral.findAll", query = "SELECT t FROM TcCatalogogeneral t")
public class TcCatalogogeneral implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "n_valor")
	private double nValor;

	@Column(name = "s_clave")
	private String sClave;

	@Column(name = "s_descripcion")
	private String sDescripcion;

	public TcCatalogogeneral() {
	}

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public double getnValor() {
		return nValor;
	}

	public void setnValor(double nValor) {
		this.nValor = nValor;
	}

	public String getsClave() {
		return sClave;
	}

	public void setsClave(String sClave) {
		this.sClave = sClave;
	}

	public String getsDescripcion() {
		return sDescripcion;
	}

	public void setsDescripcion(String sDescripcion) {
		this.sDescripcion = sDescripcion;
	}

}