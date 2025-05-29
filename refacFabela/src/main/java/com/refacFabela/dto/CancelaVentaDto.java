package com.refacFabela.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class CancelaVentaDto {
	
	Long venta;
	LocalDateTime fechaVenta;
	String cliente;
	String noParte;
	String producto;
	Integer cantidad;
	BigDecimal totalCancela;
	String usuario;
	String saldoFavor;
	String tipoPago;
	
	
	public CancelaVentaDto() {
		
	}
	
	
	
	public String getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}

	public Long getVenta() {
		return venta;
	}
	public void setVenta(Long venta) {
		this.venta = venta;
	}
	
	public LocalDateTime getFechaVenta() {
		return fechaVenta;
	}
	public void setFechaVenta(LocalDateTime fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getNoParte() {
		return noParte;
	}
	public void setNoParte(String noParte) {
		this.noParte = noParte;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public BigDecimal getTotalCancela() {
		return totalCancela;
	}



	public void setTotalCancela(BigDecimal totalCancela) {
		this.totalCancela = totalCancela;
	}



	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSaldoFavor() {
		return saldoFavor;
	}
	public void setSaldoFavor(String saldoFavor) {
		this.saldoFavor = saldoFavor;
	}

	@Override
	public String toString() {
		return "CancelaVentaDto [venta=" + venta + ", fechaVenta=" + fechaVenta + ", cliente=" + cliente + ", noParte="
				+ noParte + ", producto=" + producto + ", cantidad=" + cantidad + ", totalCancela=" + totalCancela
				+ ", usuario=" + usuario + ", saldoFavor=" + saldoFavor + "]";
	}
	
	
	

}
