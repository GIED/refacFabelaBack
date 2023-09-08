package com.refacFabela.dto;

import java.io.Serializable;

public class CotizacionDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long nIdCliente;
	private Long nIdUsuario;
	private Long nIdProducto;
	private Integer nCantidad;
	private Double nPrecioUnitario;
	private Double nIvaUnitario;
	private Double nTotalUnitario;
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
	public Double getnPrecioUnitario() {
		return nPrecioUnitario;
	}
	public void setnPrecioUnitario(Double nPrecioUnitario) {
		this.nPrecioUnitario = nPrecioUnitario;
	}
	public Double getnIvaUnitario() {
		return nIvaUnitario;
	}
	public void setnIvaUnitario(Double nIvaUnitario) {
		this.nIvaUnitario = nIvaUnitario;
	}
	public Double getnTotalUnitario() {
		return nTotalUnitario;
	}
	public void setnTotalUnitario(Double nTotalUnitario) {
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
