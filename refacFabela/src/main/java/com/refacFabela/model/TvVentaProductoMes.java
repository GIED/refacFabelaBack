package com.refacFabela.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;



@Entity
@Table(name = "tv_venta_producto_mes")
@Immutable
public class TvVentaProductoMes implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long nId;
	
	@Column(name = "n_idProductos")
	private Long idProductos;
	
	@Column(name = "s_fechaVenta")
	private String fechaVenta;
	
	@Column(name = "n_totalVentas")
	private int totalVentas;
	
	@Column(name = "s_fechaVentaNumero")
	private String fechaVentaNumero;
	
	@Column(name = "n_cantidad")
	private int cantidad;
	
	public TvVentaProductoMes(){
		
	}

	public Long getIdProductos() {
		return idProductos;
	}

	public void setIdProductos(Long idProductos) {
		this.idProductos = idProductos;
	}

	public String getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(String fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public int getTotalVentas() {
		return totalVentas;
	}

	public void setTotalVentas(int totalVentas) {
		this.totalVentas = totalVentas;
	}

	public String getFechaVentaNumero() {
		return fechaVentaNumero;
	}

	public void setFechaVentaNumero(String fechaVentaNumero) {
		this.fechaVentaNumero = fechaVentaNumero;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "TvVentaProductoMes [idProductos=" + idProductos + ", fechaVenta=" + fechaVenta + ", totalVentas="
				+ totalVentas + ", fechaVentaNumero=" + fechaVentaNumero + ", cantidad=" + cantidad + "]";
	}

	
	
	
	
	
	
	
}
