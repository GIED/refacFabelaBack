package com.refacFabela.dto;

import java.math.BigDecimal;
import java.util.List;

public class ReporteAbonoVentaCreditoDto {
	
	public Long idCliente;
	public Long idVenta;
	public String folioVenta;
	public String fechaVenta;
	public String fechaInicioCredito;
	public String fechaTerminoCredito;
	public BigDecimal totalVenta;
	public BigDecimal totalAbono;
	public BigDecimal saldoTotal;
	public BigDecimal avancePago;
	public BigDecimal descuento;
	public List<AbonosDto> abonoDto;
	public Boolean vencido;
	
	
	public Boolean getVencido() {
		return vencido;
	}
	public void setVencido(Boolean vencido) {
		this.vencido = vencido;
	}
	
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
	
	
	
	public BigDecimal getTotalVenta() {
		return totalVenta;
	}
	public void setTotalVenta(BigDecimal totalVenta) {
		this.totalVenta = totalVenta;
	}
	public BigDecimal getTotalAbono() {
		return totalAbono;
	}
	public void setTotalAbono(BigDecimal totalAbono) {
		this.totalAbono = totalAbono;
	}
	public BigDecimal getSaldoTotal() {
		return saldoTotal;
	}
	public void setSaldoTotal(BigDecimal saldoTotal) {
		this.saldoTotal = saldoTotal;
	}
	public BigDecimal getAvancePago() {
		return avancePago;
	}
	public void setAvancePago(BigDecimal avancePago) {
		this.avancePago = avancePago;
	}
	public BigDecimal getDescuento() {
		return descuento;
	}
	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
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
