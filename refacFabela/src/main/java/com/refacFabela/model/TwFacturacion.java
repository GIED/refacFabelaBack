package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;



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

	
	

	public TwFacturacion() {
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

	public int getN_idVenta() {
		return n_idVenta;
	}

	public void setN_idVenta(int n_idVenta) {
		this.n_idVenta = n_idVenta;
	}

	public String getsEstatus() {
		return sEstatus;
	}

	public void setsEstatus(String sEstatus) {
		this.sEstatus = sEstatus;
	}

	public String getS_noCertificadoSat() {
		return s_noCertificadoSat;
	}

	public void setS_noCertificadoSat(String s_noCertificadoSat) {
		this.s_noCertificadoSat = s_noCertificadoSat;
	}

	public String getS_selloCfd() {
		return s_selloCfd;
	}

	public void setS_selloCfd(String s_selloCfd) {
		this.s_selloCfd = s_selloCfd;
	}

	public String getS_selloSat() {
		return s_selloSat;
	}

	public void setS_selloSat(String s_selloSat) {
		this.s_selloSat = s_selloSat;
	}

	public String getsUuid() {
		return sUuid;
	}

	public void setsUuid(String sUuid) {
		this.sUuid = sUuid;
	}


}