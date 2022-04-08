package com.refacFabela.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tc_regimen_fiscal")
@NamedQuery(name = "TcRegimenFiscal.findAll", query = "SELECT t FROM TcRegimenFiscal t")
public class TcRegimenFiscal implements Serializable {
	private static final long serialVersionUID = 1L; 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "n_estatus")
	private int nEstatus;

	@Column(name = "s_cve_regimen")
	private String sCveRegimen;
	
	@Column(name = "s_descripcion")
	private String sDescripcion;
	
		
	private TcRegimenFiscal() {
		
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


	public String getsCveRegimen() {
		return sCveRegimen;
	}


	public void setsCveRegimen(String sCveRegimen) {
		this.sCveRegimen = sCveRegimen;
	}


	public String getsDescripcion() {
		return sDescripcion;
	}


	public void setsDescripcion(String sDescripcion) {
		this.sDescripcion = sDescripcion;
	}


	
	
	

}
