package com.refacFabela.dto;

import java.util.Date;

public class ProductoBodegaDto {
	
	public String noParte;
	public String producto;
	public Integer cantidad;
	public String bodega;
	public String anaquel;
	public String nivel;
	public Date fecha;
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
	public String getBodega() {
		return bodega;
	}
	public void setBodega(String bodega) {
		this.bodega = bodega;
	}
	public String getAnaquel() {
		return anaquel;
	}
	public void setAnaquel(String anaquel) {
		this.anaquel = anaquel;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	@Override
	public String toString() {
		return "ProductoBodegaDto [noParte=" + noParte + ", producto=" + producto + ", cantidad=" + cantidad
				+ ", bodega=" + bodega + ", anaquel=" + anaquel + ", nivel=" + nivel + ", fecha=" + fecha + "]";
	}
	
	
	
	
	

}
