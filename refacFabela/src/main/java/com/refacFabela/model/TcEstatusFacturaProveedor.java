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
@Table(name = "tc_estatus_factura_proveedor")
@NamedQuery(name = "TcEstatusFacturaProveedor.findAll", query = "SELECT t FROM TcEstatusFacturaProveedor t")
public class TcEstatusFacturaProveedor implements Serializable {
	private static final long serialVersionUID = 1L; 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name = "s_estatus_factura")
	private String sMoneda;

	@Column(name = "n_estatus")
	private int nEstatus;
	
	public TcEstatusFacturaProveedor(){
		
	}

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
		return "TcEstatusFacturaProveedor [nId=" + nId + ", sMoneda=" + sMoneda + ", nEstatus=" + nEstatus + "]";
	}
	
	


}
