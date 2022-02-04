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
@Table(name = "tc_catalogo_venta")
@NamedQuery(name = "TcEstatusVenta.findAll", query = "SELECT t FROM TcEstatusVenta t")
public class TcEstatusVenta implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long nId;
	
	@Column(name = "s_descripcion")
	private String sDescripcion;

	@Column(name = "n_clave")
	private Long nClave;

	@Column(name = "n_estatus")
	private Long nEstatus;
	
	public TcEstatusVenta(){
		
	}

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public String getsDescripcion() {
		return sDescripcion;
	}

	public void setsDescripcion(String sDescripcion) {
		this.sDescripcion = sDescripcion;
	}

	public Long getnClave() {
		return nClave;
	}

	public void setnClave(Long nClave) {
		this.nClave = nClave;
	}

	public Long getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(Long nEstatus) {
		this.nEstatus = nEstatus;
	}
	
	
	
	

}
