package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tw_facturacion database table.
 * 
 */
@Entity
@Table(name="tw_facturacion")
@NamedQuery(name="TwFacturacion.findAll", query="SELECT t FROM TwFacturacion t")
public class TwFacturacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Column(name="n_estatus")
	private int nEstatus;

	private int n_idVenta;

	@Column(name="s_estatus")
	private String sEstatus;

	private String s_noCertificadoSat;

	private String s_selloCfd;

	private String s_selloSat;

	@Column(name="s_uuid")
	private String sUuid;

	//bi-directional many-to-one association to TwVenta
	@OneToMany(mappedBy="twFacturacion")
	private List<TwVenta> twVentas;

	public TwFacturacion() {
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

	public int getN_idVenta() {
		return this.n_idVenta;
	}

	public void setN_idVenta(int n_idVenta) {
		this.n_idVenta = n_idVenta;
	}

	public String getSEstatus() {
		return this.sEstatus;
	}

	public void setSEstatus(String sEstatus) {
		this.sEstatus = sEstatus;
	}

	public String getS_noCertificadoSat() {
		return this.s_noCertificadoSat;
	}

	public void setS_noCertificadoSat(String s_noCertificadoSat) {
		this.s_noCertificadoSat = s_noCertificadoSat;
	}

	public String getS_selloCfd() {
		return this.s_selloCfd;
	}

	public void setS_selloCfd(String s_selloCfd) {
		this.s_selloCfd = s_selloCfd;
	}

	public String getS_selloSat() {
		return this.s_selloSat;
	}

	public void setS_selloSat(String s_selloSat) {
		this.s_selloSat = s_selloSat;
	}

	public String getSUuid() {
		return this.sUuid;
	}

	public void setSUuid(String sUuid) {
		this.sUuid = sUuid;
	}

	public List<TwVenta> getTwVentas() {
		return this.twVentas;
	}

	public void setTwVentas(List<TwVenta> twVentas) {
		this.twVentas = twVentas;
	}

	public TwVenta addTwVenta(TwVenta twVenta) {
		getTwVentas().add(twVenta);
		twVenta.setTwFacturacion(this);

		return twVenta;
	}

	public TwVenta removeTwVenta(TwVenta twVenta) {
		getTwVentas().remove(twVenta);
		twVenta.setTwFacturacion(null);

		return twVenta;
	}

}