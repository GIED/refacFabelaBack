package com.refacFabela.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "vw_venta_mes_ano")
@NamedQuery(name = "VwVentaMesAno.findAll", query = "SELECT t FROM VwVentaMesAno t")
public class VwVentaMesAno implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id	
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "mes")
	private String sMes;

	@Column(name = "ano")
	private String sAno;
	
	@Column(name = "total_ventas_mes")
	private String nTotalVentas;
	
	@Column(name = "total_cotizaciones_mes")
	private String nTotalCorizaciones;
	
	
	public VwVentaMesAno() {
		
	}


	public Long getnId() {
		return nId;
	}


	public void setnId(Long nId) {
		this.nId = nId;
	}


	public String getsMes() {
		return sMes;
	}


	public void setsMes(String sMes) {
		this.sMes = sMes;
	}


	public String getsAno() {
		return sAno;
	}


	public void setsAno(String sAno) {
		this.sAno = sAno;
	}


	public String getnTotalVentas() {
		return nTotalVentas;
	}


	public void setnTotalVentas(String nTotalVentas) {
		this.nTotalVentas = nTotalVentas;
	}


	public String getnTotalCorizaciones() {
		return nTotalCorizaciones;
	}


	public void setnTotalCorizaciones(String nTotalCorizaciones) {
		this.nTotalCorizaciones = nTotalCorizaciones;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "VwVentaMesAno [nId=" + nId + ", sMes=" + sMes + ", sAno=" + sAno + ", nTotalVentas=" + nTotalVentas
				+ ", nTotalCorizaciones=" + nTotalCorizaciones + "]";
	}
	
	

	

}
