package com.refacFabela.dto;

import java.math.BigDecimal;

public class CalculaPrecioDto {
	
	Integer cantidad;
	BigDecimal precioUnitario;
	BigDecimal ivaUnitario;
	BigDecimal totalUnitario;
	BigDecimal precioPartida;
	BigDecimal ivaPartida;
	BigDecimal totalPartida;
	
	
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	
	
	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public BigDecimal getIvaUnitario() {
		return ivaUnitario;
	}
	public void setIvaUnitario(BigDecimal ivaUnitario) {
		this.ivaUnitario = ivaUnitario;
	}
	public BigDecimal getTotalUnitario() {
		return totalUnitario;
	}
	public void setTotalUnitario(BigDecimal totalUnitario) {
		this.totalUnitario = totalUnitario;
	}
	public BigDecimal getPrecioPartida() {
		return precioPartida;
	}
	public void setPrecioPartida(BigDecimal precioPartida) {
		this.precioPartida = precioPartida;
	}
	public BigDecimal getIvaPartida() {
		return ivaPartida;
	}
	public void setIvaPartida(BigDecimal ivaPartida) {
		this.ivaPartida = ivaPartida;
	}
	public BigDecimal getTotalPartida() {
		return totalPartida;
	}
	public void setTotalPartida(BigDecimal totalPartida) {
		this.totalPartida = totalPartida;
	}
	@Override
	public String toString() {
		return "CalculaPrecioDto [cantidad=" + cantidad + ", precioUnitario=" + precioUnitario + ", ivaUnitario="
				+ ivaUnitario + ", totalUnitario=" + totalUnitario + ", precioPartida=" + precioPartida
				+ ", ivaPartida=" + ivaPartida + ", totalPartida=" + totalPartida + "]";
	}
	
	
	

}
