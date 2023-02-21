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
@Table(name = "vw_total_venta_producto_ano")
@NamedQuery(name = "VwVentaProductoAno.findAll", query = "SELECT t FROM VwVentaProductoAno t")
public class VwVentaProductoAno implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "s_no_parte")
	private String sNoParte;

	@Column(name = "s_producto")
	private String sProducto;
	
	@Column(name = "s_marca")
	private String sMarca;
	
	@Column(name = "ano")
	private String sAno;
	
	@Column(name = "total_venta")
	private Integer  nTotalVenta;
	
	
	public VwVentaProductoAno() {
		
	}


	public Long getnId() {
		return nId;
	}


	public void setnId(Long nId) {
		this.nId = nId;
	}


	public String getsNoParte() {
		return sNoParte;
	}


	public void setsNoParte(String sNoParte) {
		this.sNoParte = sNoParte;
	}


	public String getsProducto() {
		return sProducto;
	}


	public void setsProducto(String sProducto) {
		this.sProducto = sProducto;
	}


	public String getsMarca() {
		return sMarca;
	}


	public void setsMarca(String sMarca) {
		this.sMarca = sMarca;
	}


	public String getsAno() {
		return sAno;
	}


	public void setsAno(String sAno) {
		this.sAno = sAno;
	}


	


	


	public Integer getnTotalVenta() {
		return nTotalVenta;
	}


	public void setnTotalVenta(Integer nTotalVenta) {
		this.nTotalVenta = nTotalVenta;
	}


	

	
	
	
	

}
