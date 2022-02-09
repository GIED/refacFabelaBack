package com.refacFabela.dto;

import java.util.Date;
import java.util.List;

public class ReporteAbonoVentaCreditoDto {
	
	public Long idCliente;
	public Long idVenta;
	public String folioVenta;
	public String fechaVenta;
	public String fechaInicioCredito;
	public String fechaTerminoCredito;
	public double totalVenta;
	public double totalAbono;
	public double saldoTotal;
	public double avancePago;
	public List<AbonosDto> abonoDto;
	
	
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	public Long getIdVenta() {
		return idVenta;
	}
	public void setIdVenta(Long idVenta) {
		this.idVenta = idVenta;
	}
	public String getFolioVenta() {
		return folioVenta;
	}
	public void setFolioVenta(String folioVenta) {
		this.folioVenta = folioVenta;
	}
	public String getFechaVenta() {
		return fechaVenta;
	}
	public void setFechaVenta(String fechaVenta) {
		this.fechaVenta = fechaVenta;
	}
	
	public String getFechaInicioCredito() {
		return fechaInicioCredito;
	}
	public void setFechaInicioCredito(String fechaInicioCredito) {
		this.fechaInicioCredito = fechaInicioCredito;
	}
	public String getFechaTerminoCredito() {
		return fechaTerminoCredito;
	}
	public void setFechaTerminoCredito(String fechaTerminoCredito) {
		this.fechaTerminoCredito = fechaTerminoCredito;
	}
	public double getTotalVenta() {
		return totalVenta;
	}
	public void setTotalVenta(double totalVenta) {
		this.totalVenta = totalVenta;
	}
	public double getTotalAbono() {
		return totalAbono;
	}
	public void setTotalAbono(double totalAbono) {
		this.totalAbono = totalAbono;
	}
	public double getSaldoTotal() {
		return saldoTotal;
	}
	public void setSaldoTotal(double saldoTotal) {
		this.saldoTotal = saldoTotal;
	}
	public double getAvancePago() {
		return avancePago;
	}
	public void setAvancePago(double avancePago) {
		this.avancePago = avancePago;
	}
	public List<AbonosDto> getAbonoDto() {
		return abonoDto;
	}
	public void setAbonoDto(List<AbonosDto> abonoDto) {
		this.abonoDto = abonoDto;
	}
	@Override
	public String toString() {
		return "ReporteAbonoVentaCreditoDto [idCliente=" + idCliente + ", idVenta=" + idVenta + ", folioVenta="
				+ folioVenta + ", fechaVenta=" + fechaVenta + ", fechaInicioCredito=" + fechaInicioCredito
				+ ", fechaTerminoCredito=" + fechaTerminoCredito + ", totalVenta=" + totalVenta + ", totalAbono="
				+ totalAbono + ", saldoTotal=" + saldoTotal + ", avancePago=" + avancePago + ", abonoDto=" + abonoDto
				+ "]";
	}
	
	
	

}
