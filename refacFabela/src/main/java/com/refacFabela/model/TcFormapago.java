package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tc_formapago database table.
 * 
 */
@Entity
@Table(name="tc_formapago")
@NamedQuery(name="TcFormapago.findAll", query="SELECT t FROM TcFormapago t")
public class TcFormapago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Column(name="n_estatus")
	private int nEstatus;

	@Column(name="s_clave")
	private String sClave;

	@Column(name="s_descripcion")
	private String sDescripcion;

	//bi-directional many-to-one association to TwAbono
	@OneToMany(mappedBy="tcFormapago")
	private List<TwAbono> twAbonos;

	public TcFormapago() {
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

	public String getSClave() {
		return this.sClave;
	}

	public void setSClave(String sClave) {
		this.sClave = sClave;
	}

	public String getSDescripcion() {
		return this.sDescripcion;
	}

	public void setSDescripcion(String sDescripcion) {
		this.sDescripcion = sDescripcion;
	}

	public List<TwAbono> getTwAbonos() {
		return this.twAbonos;
	}

	public void setTwAbonos(List<TwAbono> twAbonos) {
		this.twAbonos = twAbonos;
	}

	public TwAbono addTwAbono(TwAbono twAbono) {
		getTwAbonos().add(twAbono);
		twAbono.setTcFormapago(this);

		return twAbono;
	}

	public TwAbono removeTwAbono(TwAbono twAbono) {
		getTwAbonos().remove(twAbono);
		twAbono.setTcFormapago(null);

		return twAbono;
	}

}