package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tc_nivel database table.
 * 
 */
@Entity
@Table(name="tc_nivel")
@NamedQuery(name="TcNivel.findAll", query="SELECT t FROM TcNivel t")
public class TcNivel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Column(name="n_estatus")
	private String nEstatus;

	@Column(name="s_nivel")
	private String sNivel;

	//bi-directional many-to-one association to TwProductobodega
	@OneToMany(mappedBy="tcNivel")
	private List<TwProductobodega> twProductobodegas;

	public TcNivel() {
	}

	public Long getNId() {
		return this.nId;
	}

	public void setNId(Long nId) {
		this.nId = nId;
	}

	public String getNEstatus() {
		return this.nEstatus;
	}

	public void setNEstatus(String nEstatus) {
		this.nEstatus = nEstatus;
	}

	public String getSNivel() {
		return this.sNivel;
	}

	public void setSNivel(String sNivel) {
		this.sNivel = sNivel;
	}

	public List<TwProductobodega> getTwProductobodegas() {
		return this.twProductobodegas;
	}

	public void setTwProductobodegas(List<TwProductobodega> twProductobodegas) {
		this.twProductobodegas = twProductobodegas;
	}

	public TwProductobodega addTwProductobodega(TwProductobodega twProductobodega) {
		getTwProductobodegas().add(twProductobodega);
		twProductobodega.setTcNivel(this);

		return twProductobodega;
	}

	public TwProductobodega removeTwProductobodega(TwProductobodega twProductobodega) {
		getTwProductobodegas().remove(twProductobodega);
		twProductobodega.setTcNivel(null);

		return twProductobodega;
	}

}