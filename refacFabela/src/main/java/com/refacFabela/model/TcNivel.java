package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "tc_nivel")
@NamedQuery(name = "TcNivel.findAll", query = "SELECT t FROM TcNivel t")
public class TcNivel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "n_estatus")
	private String nEstatus;

	@Column(name = "s_nivel")
	private String sNivel;

	// bi-directional many-to-one association to TwProductobodega

	public TcNivel() {
	}

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public String getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(String nEstatus) {
		this.nEstatus = nEstatus;
	}

	public String getsNivel() {
		return sNivel;
	}

	public void setsNivel(String sNivel) {
		this.sNivel = sNivel;
	}

	@Override
	public String toString() {
		return "TcNivel [nId=" + nId + ", nEstatus=" + nEstatus + ", sNivel=" + sNivel + "]";
	}
	
	

}