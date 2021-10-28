package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "tc_bodegas")
@NamedQuery(name = "TcBodega.findAll", query = "SELECT t FROM TcBodega t")
public class TcBodega implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "n_estatus")
	private int nEstatus;

	@Column(name = "s_bodega")
	private String sBodega;

	// bi-directional many-to-one association to TwProductobodega

	public TcBodega() {
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

	public String getsBodega() {
		return sBodega;
	}

	public void setsBodega(String sBodega) {
		this.sBodega = sBodega;
	}

}