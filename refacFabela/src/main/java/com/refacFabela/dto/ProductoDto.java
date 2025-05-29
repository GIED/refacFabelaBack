package com.refacFabela.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductoDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long nId;
	private String sNoParte;
	private String sProducto;
	private String sMarca;
	private BigDecimal nPrecioConIva;
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
	public BigDecimal getnPrecioConIva() {
		return nPrecioConIva;
	}
	public void setnPrecioConIva(BigDecimal nPrecioConIva) {
		this.nPrecioConIva = nPrecioConIva;
	}
	
	
	
	

}
