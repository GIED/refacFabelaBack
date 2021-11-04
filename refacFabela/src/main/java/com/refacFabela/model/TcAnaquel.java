package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "tc_anaquel")
@NamedQuery(name = "TcAnaquel.findAll", query = "SELECT t FROM TcAnaquel t")
public class TcAnaquel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "n_estatus")
	private int nEstatus;

	@Column(name = "s_anaquel")
	private String sAnaquel;

	// bi-directional many-to-one association to TwProductobodega

	public TcAnaquel() {
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

	public String getsAnaquel() {
		return sAnaquel;
	}

	public void setsAnaquel(String sAnaquel) {
		this.sAnaquel = sAnaquel;
	}

	

}