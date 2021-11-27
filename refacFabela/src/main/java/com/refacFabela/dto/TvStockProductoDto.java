package com.refacFabela.dto;

import java.io.Serializable;

import com.refacFabela.model.TcProducto;

public class TvStockProductoDto implements Serializable{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
private Long nIdProducto;
private Integer nCantidadTotal;
private Integer nCantidad;
private TcProducto tcProducto;
private Integer nStatus;

public Long getnIdProducto() {
	return nIdProducto;
}
public void setnIdProducto(Long nIdProducto) {
	this.nIdProducto = nIdProducto;
}
public Integer getnCantidadTotal() {
	return nCantidadTotal;
}
public void setnCantidadTotal(Integer nCantidadTotal) {
	this.nCantidadTotal = nCantidadTotal;
}
public Integer getnCantidad() {
	return nCantidad;
}
public void setnCantidad(Integer nCantidad) {
	this.nCantidad = nCantidad;
}
public TcProducto getTcProducto() {
	return tcProducto;
}
public void setTcProducto(TcProducto tcProducto) {
	this.tcProducto = tcProducto;
}
public Integer getnStatus() {
	return nStatus;
}
public void setnStatus(Integer nStatus) {
	this.nStatus = nStatus;
}
@Override
public String toString() {
	return "TvStockProductoDto [nIdProducto=" + nIdProducto + ", nCantidadTotal=" + nCantidadTotal + ", nCantidad="
			+ nCantidad + ", tcProducto=" + tcProducto + ", nStatus=" + nStatus + "]";
}





}
