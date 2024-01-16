package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "tw_facturacion")
@NamedQuery(name = "TwFacturacion.findAll", query = "SELECT t FROM TwFacturacion t")
public class TwFacturacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "n_estatus")
	private int nEstatus;

	private Long n_idVenta;

	@Column(name = "s_estado")
	private String sEstado;

	private String s_noCertificadoSat;

	private String s_selloCfd;

	private String s_selloSat;
	
	private String s_cadenaOriginal;
	
	@Column(name = "n_id_dato_factura")
	private Long nIdDatoFactura;

	@Column(name = "s_uuid")
	private String sUuid;

	public TwFacturacion() {
	}

	public Long getnId() {
		return nId;
	}
	
	

	

	public Long getnIdDatoFactura() {
		return nIdDatoFactura;
	}

	public void setnIdDatoFactura(Long nIdDatoFactura) {
		this.nIdDatoFactura = nIdDatoFactura;
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

	

	public Long getN_idVenta() {
		return n_idVenta;
	}

	public void setN_idVenta(Long n_idVenta) {
		this.n_idVenta = n_idVenta;
	}

	

	public String getsEstado() {
		return sEstado;
	}

	public void setsEstado(String sEstado) {
		this.sEstado = sEstado;
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

	public String getS_cadenaOriginal() {
		return s_cadenaOriginal;
	}

	public void setS_cadenaOriginal(String s_cadenaOriginal) {
		this.s_cadenaOriginal = s_cadenaOriginal;
	}
	
	

}