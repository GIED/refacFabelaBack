package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "tc_clavesat")
@NamedQuery(name = "TcClavesat.findAll", query = "SELECT t FROM TcClavesat t")
public class TcClavesat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "n_estatus")
	private int nEstatus;

	@Column(name = "s_clavesat")
	private String sClavesat;

	@Column(name = "s_descripcion")
	private String sDescripcion;

	public TcClavesat() {
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

	public String getsClavesat() {
		return sClavesat;
	}

	public void setsClavesat(String sClavesat) {
		this.sClavesat = sClavesat;
	}

	public String getsDescripcion() {
		return sDescripcion;
	}

	public void setsDescripcion(String sDescripcion) {
		this.sDescripcion = sDescripcion;
	}

	@Override
	public String toString() {
		return "TcClavesat [nId=" + nId + ", nEstatus=" + nEstatus + ", sClavesat=" + sClavesat + ", sDescripcion="
				+ sDescripcion + "]\n";
	}
	
	

}