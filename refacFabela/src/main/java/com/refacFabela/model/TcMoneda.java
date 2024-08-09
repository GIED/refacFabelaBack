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
@Table(name = "tc_moneda")
@NamedQuery(name = "TcMoneda.findAll", query = "SELECT t FROM TcMoneda t")
public class TcMoneda implements Serializable {
	private static final long serialVersionUID = 1L; 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name = "s_moneda")
	private String sMoneda;

	@Column(name = "n_estatus")
	private int nEstatus;

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public String getsMoneda() {
		return sMoneda;
	}

	public void setsMoneda(String sMoneda) {
		this.sMoneda = sMoneda;
	}

	public int getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}
	

	@Override
	public String toString() {
		return "TcMoneda [nId=" + nId + ", sMoneda=" + sMoneda + ", nEstatus=" + nEstatus + "]";
	}
	
	public TcMoneda() {
		
	}
	
	


}
