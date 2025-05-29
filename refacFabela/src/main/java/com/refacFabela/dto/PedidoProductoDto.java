package com.refacFabela.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PedidoProductoDto {
	
	private String noParte;
	private String producto;
	private LocalDateTime fechaPedido;
	private LocalDateTime fechaRecibida;
	private int cantidad;
	private BigDecimal precio;
	private String estatus;
	private String proveedor;
	
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
	
	public LocalDateTime getFechaPedido() {
		return fechaPedido;
	}
	public void setFechaPedido(LocalDateTime fechaPedido) {
		this.fechaPedido = fechaPedido;
	}
	public LocalDateTime getFechaRecibida() {
		return fechaRecibida;
	}
	public void setFechaRecibida(LocalDateTime fechaRecibida) {
		this.fechaRecibida = fechaRecibida;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
	
	
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	@Override
	public String toString() {
		return "PedidoProductoDto [noParte=" + noParte + ", producto=" + producto + ", fechaPedido=" + fechaPedido
				+ ", fechaRecibida=" + fechaRecibida + ", cantidad=" + cantidad + ", precio=" + precio + ", estatus="
				+ estatus + "]";
	}
	
	
	
	
	

}
