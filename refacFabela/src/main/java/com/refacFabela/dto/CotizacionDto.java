package com.refacFabela.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CotizacionDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long nIdCliente;
	private Long nIdUsuario;
	private Long nIdProducto;
	private Integer nCantidad;
	private BigDecimal nPrecioUnitario;
	private BigDecimal nIvaUnitario;
	private BigDecimal nTotalUnitario;
	private String sFolio;
	private Integer nInDescuento;
	
	public Long getnIdCliente() {
		return nIdCliente;
	}
	public void setnIdCliente(Long nIdCliente) {
		this.nIdCliente = nIdCliente;
	}
	public Long getnIdUsuario() {
		return nIdUsuario;
	}
	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}
	public Long getnIdProducto() {
		return nIdProducto;
	}
	public void setnIdProducto(Long nIdProducto) {
		this.nIdProducto = nIdProducto;
	}
	public Integer getnCantidad() {
		return nCantidad;
	}
	public void setnCantidad(Integer nCantidad) {
		this.nCantidad = nCantidad;
	}
	
	
	
	public BigDecimal getnPrecioUnitario() {
		return nPrecioUnitario;
	}
	public void setnPrecioUnitario(BigDecimal nPrecioUnitario) {
		this.nPrecioUnitario = nPrecioUnitario;
	}
	public BigDecimal getnIvaUnitario() {
		return nIvaUnitario;
	}
	public void setnIvaUnitario(BigDecimal nIvaUnitario) {
		this.nIvaUnitario = nIvaUnitario;
	}
	public BigDecimal getnTotalUnitario() {
		return nTotalUnitario;
	}
	public void setnTotalUnitario(BigDecimal nTotalUnitario) {
		this.nTotalUnitario = nTotalUnitario;
	}
	public String getsFolio() {
		return sFolio;
	}
	public void setsFolio(String sFolio) {
		this.sFolio = sFolio;
	}
	
	
	public Integer getnInDescuento() {
		return nInDescuento;
	}
	public void setnInDescuento(Integer nInDescuento) {
		this.nInDescuento = nInDescuento;
	}
	@Override
	public String toString() {
		return "CotizacionDto [nIdCliente=" + nIdCliente + ", nIdUsuario=" + nIdUsuario + ", nIdProducto=" + nIdProducto
				+ ", nCantidad=" + nCantidad + ", nPrecioUnitario=" + nPrecioUnitario + ", nIvaUnitario=" + nIvaUnitario
				+ ", nTotalUnitario=" + nTotalUnitario + ", sFolio=" + sFolio + "]";
	}
	
	
	
	
	
	

}
