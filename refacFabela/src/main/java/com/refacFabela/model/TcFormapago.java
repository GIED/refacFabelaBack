package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "tc_formapago")
@NamedQuery(name = "TcFormapago.findAll", query = "SELECT t FROM TcFormapago t")
public class TcFormapago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "n_estatus")
	private int nEstatus;

	@Column(name = "s_clave")
	private String sClave;

	@Column(name = "s_descripcion")
	private String sDescripcion;

	public TcFormapago() {
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

	@Override
	public String toString() {
		return "TcFormapago [nId=" + nId + ", nEstatus=" + nEstatus + ", sClave=" + sClave + ", sDescripcion="
				+ sDescripcion + "]";
	}
	
	
	

}