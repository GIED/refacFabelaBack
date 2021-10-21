package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tc_usocfdi database table.
 * 
 */
@Entity
@Table(name="tc_usocfdi")
@NamedQuery(name="TcUsocfdi.findAll", query="SELECT t FROM TcUsocfdi t")
public class TcUsocfdi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Column(name="n_estatus")
	private int nEstatus;

	@Column(name="s_clave")
	private String sClave;

	@Column(name="s_descripcion")
	private String sDescripcion;

	public TcUsocfdi() {
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