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
@Table(name = "tc_cp")
@NamedQuery(name = "TcCp.findAll", query = "SELECT t FROM TcCp t")
public class TcCp implements Serializable {
	private static final long serialVersionUID = 1L; 
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name = "s_cp")
	private String sCp;

	@Column(name = "s_cve_estado")
	private String sCveEstado;
	
	@Column(name = "s_cve_municipio")
	private String sCveMunicipio;
	
	@Column(name = "s_cve_localidad")
	private String sCveLocalidad;
	
	
	
	
	public Long getnId() {
		return nId;
	}




	public void setnId(Long nId) {
		this.nId = nId;
	}

	


	public String getsCp() {
		return sCp;
	}




	public void setsCp(String sCp) {
		this.sCp = sCp;
	}




	public String getsCveEstado() {
		return sCveEstado;
	}




	public void setsCveEstado(String sCveEstado) {
		this.sCveEstado = sCveEstado;
	}




	public String getsCveMunicipio() {
		return sCveMunicipio;
	}




	public void setsCveMunicipio(String sCveMunicipio) {
		this.sCveMunicipio = sCveMunicipio;
	}




	public String getsCveLocalidad() {
		return sCveLocalidad;
	}




	public void setsCveLocalidad(String sCveLocalidad) {
		this.sCveLocalidad = sCveLocalidad;
	}




	public TcCp() {
		
	}
	

}
