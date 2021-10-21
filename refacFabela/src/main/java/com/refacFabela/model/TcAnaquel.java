package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tc_anaquel database table.
 * 
 */
@Entity
@Table(name="tc_anaquel")
@NamedQuery(name="TcAnaquel.findAll", query="SELECT t FROM TcAnaquel t")
public class TcAnaquel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Column(name="n_estatus")
	private String nEstatus;

	@Column(name="s_anaquel")
	private String sAnaquel;

	//bi-directional many-to-one association to TwProductobodega
	@OneToMany(mappedBy="tcAnaquel")
	private List<TwProductobodega> twProductobodegas;

	public TcAnaquel() {
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

	public String getSAnaquel() {
		return this.sAnaquel;
	}

	public void setSAnaquel(String sAnaquel) {
		this.sAnaquel = sAnaquel;
	}

	public List<TwProductobodega> getTwProductobodegas() {
		return this.twProductobodegas;
	}

	public void setTwProductobodegas(List<TwProductobodega> twProductobodegas) {
		this.twProductobodegas = twProductobodegas;
	}

	public TwProductobodega addTwProductobodega(TwProductobodega twProductobodega) {
		getTwProductobodegas().add(twProductobodega);
		twProductobodega.setTcAnaquel(this);

		return twProductobodega;
	}

	public TwProductobodega removeTwProductobodega(TwProductobodega twProductobodega) {
		getTwProductobodegas().remove(twProductobodega);
		twProductobodega.setTcAnaquel(null);

		return twProductobodega;
	}

}