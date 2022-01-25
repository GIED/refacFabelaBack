package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "tc_categoria")
@NamedQuery(name = "TcCategoria.findAll", query = "SELECT t FROM TcCategoria t")
public class TcCategoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "n_idCategoriaGeneral")
	private int nIdCategoriaGeneral;

	@Column(name = "s_descripcion")
	private String sDescripcion;

	@Column(name = "n_estatus")
	private int nEstatus;

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public int getnIdCategoriaGeneral() {
		return nIdCategoriaGeneral;
	}

	public void setnIdCategoriaGeneral(int nIdCategoriaGeneral) {
		this.nIdCategoriaGeneral = nIdCategoriaGeneral;
	}

	public String getsDescripcion() {
		return sDescripcion;
	}

	public void setsDescripcion(String sDescripcion) {
		this.sDescripcion = sDescripcion;
	}

	public int getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}

}