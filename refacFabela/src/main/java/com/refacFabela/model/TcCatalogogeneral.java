package com.refacFabela.model;
import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tc_catalogogeneral database table.
 * 
 */
@Entity
@Table(name="tc_catalogogeneral")
@NamedQuery(name="TcCatalogogeneral.findAll", query="SELECT t FROM TcCatalogogeneral t")
public class TcCatalogogeneral implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Column(name="n_valor")
	private double nValor;

	@Column(name="s_clave")
	private String sClave;

	@Column(name="s_descripcion")
	private String sDescripcion;

	public TcCatalogogeneral() {
	}

	public Long getNId() {
		return this.nId;
	}

	public void setNId(Long nId) {
		this.nId = nId;
	}

	public double getNValor() {
		return this.nValor;
	}

	public void setNValor(double nValor) {
		this.nValor = nValor;
	}

	public String getSClave() {
		return this.sClave;
	}

	public void setSClave(String sClave) {
		this.sClave = sClave;
	}

	public String getSDescripcion() {
		return this.sDescripcion;
	}

	public void setSDescripcion(String sDescripcion) {
		this.sDescripcion = sDescripcion;
	}

}