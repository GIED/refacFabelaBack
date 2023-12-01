package com.refacFabela.dto;

public class CalculaPrecioDto {
	
	Integer cantidad;
	double precioUnitario;
	double ivaUnitario;
	double totalUnitario;
	double precioPartida;
	double ivaPartida;
	double totalPartida;
	
	
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public double getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public double getIvaUnitario() {
		return ivaUnitario;
	}
	public void setIvaUnitario(double ivaUnitario) {
		this.ivaUnitario = ivaUnitario;
	}
	public double getTotalUnitario() {
		return totalUnitario;
	}
	public void setTotalUnitario(double totalUnitario) {
		this.totalUnitario = totalUnitario;
	}
	public double getPrecioPartida() {
		return precioPartida;
	}
	public void setPrecioPartida(double precioPartida) {
		this.precioPartida = precioPartida;
	}
	public double getIvaPartida() {
		return ivaPartida;
	}
	public void setIvaPartida(double ivaPartida) {
		this.ivaPartida = ivaPartida;
	}
	public double getTotalPartida() {
		return totalPartida;
	}
	public void setTotalPartida(double totalPartida) {
		this.totalPartida = totalPartida;
	}
	@Override
	public String toString() {
		return "CalculaPrecioDto [cantidad=" + cantidad + ", precioUnitario=" + precioUnitario + ", ivaUnitario="
				+ ivaUnitario + ", totalUnitario=" + totalUnitario + ", precioPartida=" + precioPartida
				+ ", ivaPartida=" + ivaPartida + ", totalPartida=" + totalPartida + "]";
	}
	
	
	

}
