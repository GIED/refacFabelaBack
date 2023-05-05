package com.refacFabela.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tv_stock_producto_hist")
@NamedQuery(name = "TvStockProductoHist.findAll", query = "SELECT t FROM TvStockProductoHist t")
public class TvStockProductoHist implements  Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id	
	@Column(name = "n_id_venta")
	private Long nIdVenta;

	@Column(name = "n_id_producto")
	private Long nIdProducto;
	
	@Column(name = "n_stock")
	private Integer nStock;
	
	@Column(name = "n_cantidad")
	private Integer nCantidad;
	
	@Column(name = "n_stock_final")
	private Integer nStockFinal;

	public Long getnIdVenta() {
		return nIdVenta;
	}

	public void setnIdVenta(Long nIdVenta) {
		this.nIdVenta = nIdVenta;
	}

	public Long getnIdProducto() {
		return nIdProducto;
	}

	public void setnIdProducto(Long nIdProducto) {
		this.nIdProducto = nIdProducto;
	}

	public Integer getnStock() {
		return nStock;
	}

	public void setnStock(Integer nStock) {
		this.nStock = nStock;
	}

	public Integer getnCantidad() {
		return nCantidad;
	}

	public void setnCantidad(Integer nCantidad) {
		this.nCantidad = nCantidad;
	}

	public Integer getnStockFinal() {
		return nStockFinal;
	}

	public void setnStockFinal(Integer nStockFinal) {
		this.nStockFinal = nStockFinal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	

}
