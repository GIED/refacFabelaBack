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
@Table(name = "tc_tipo_proveedor")
@NamedQuery(name = "TcTipoProveedor.findAll", query = "SELECT t FROM TcTipoProveedor t")
public class TcTipoProveedor implements Serializable {
	private static final long serialVersionUID = 1L;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "n_estatus")
	private Boolean nEstatus;

	@Column(name = "s_descripcion")
	private String sDescripcion;

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Boolean getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(Boolean nEstatus) {
		this.nEstatus = nEstatus;
	}

	public String getsDescripcion() {
		return sDescripcion;
	}

	public void setsDescripcion(String sDescripcion) {
		this.sDescripcion = sDescripcion;
	}

	@Override
	public String toString() {
		return "TcTipoProeedor [nId=" + nId + ", nEstatus=" + nEstatus + ", sDescripcion=" + sDescripcion + "]";
	}
	
	
	

	

}
