package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tc_bodegas database table.
 * 
 */
@Entity
@Table(name="tc_bodegas")
@NamedQuery(name="TcBodega.findAll", query="SELECT t FROM TcBodega t")
public class TcBodega implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Column(name="n_estatus")
	private int nEstatus;

	@Column(name="s_bodega")
	private String sBodega;

	//bi-directional many-to-one association to TwProductobodega
	@OneToMany(mappedBy="tcBodega")
	private List<TwProductobodega> twProductobodegas;

	public TcBodega() {
	}

	public Long getNId() {
		return this.nId;
	}

	public void setNId(Long nId) {
		this.nId = nId;
	}

	public int getNEstatus() {
		return this.nEstatus;
	}

	public void setNEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}

	public String getSBodega() {
		return this.sBodega;
	}

	public void setSBodega(String sBodega) {
		this.sBodega = sBodega;
	}

	public List<TwProductobodega> getTwProductobodegas() {
		return this.twProductobodegas;
	}

	public void setTwProductobodegas(List<TwProductobodega> twProductobodegas) {
		this.twProductobodegas = twProductobodegas;
	}

	public TwProductobodega addTwProductobodega(TwProductobodega twProductobodega) {
		getTwProductobodegas().add(twProductobodega);
		twProductobodega.setTcBodega(this);

		return twProductobodega;
	}

	public TwProductobodega removeTwProductobodega(TwProductobodega twProductobodega) {
		getTwProductobodegas().remove(twProductobodega);
		twProductobodega.setTcBodega(null);

		return twProductobodega;
	}

}