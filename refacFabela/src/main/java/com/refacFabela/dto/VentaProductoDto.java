package com.refacFabela.dto;

import java.io.Serializable;
import java.util.Date;

import com.refacFabela.model.TwVentasProducto;

public class VentaProductoDto implements  Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long nId;
	private String sNoParte;
	private String sProducto;
	private String sDescripcion;
	private String sMarca;
	private int nCantidad;
	private double nTotalUnitario;
	private double nTotalPartida;
	private Date dFechaVenta;
	private int nEstatusEntregaAlmacen;
	
	public VentaProductoDto(TwVentasProducto twVentasProducto ) {
		super();
		this.nId = twVentasProducto.getnId();
		this.sNoParte = twVentasProducto.getTcProducto().getsNoParte();
		this.sProducto = twVentasProducto.getTcProducto().getsProducto();
		this.sDescripcion = twVentasProducto.getTcProducto().getsDescripcion();
		this.sMarca = twVentasProducto.getTcProducto().getsMarca();
		this.nCantidad = twVentasProducto.getnCantidad();
		this.nTotalUnitario =twVentasProducto.getnTotalUnitario();
		this.nTotalPartida = twVentasProducto.getnTotalPartida();
		this.dFechaVenta = twVentasProducto.getTwVenta().getdFechaVenta();
		this.nEstatusEntregaAlmacen = twVentasProducto.getnEstatusEntregaAlmacen();
	}

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public String getsNoParte() {
		return sNoParte;
	}

	public void setsNoParte(String sNoParte) {
		this.sNoParte = sNoParte;
	}

	public String getsProducto() {
		return sProducto;
	}

	public void setsProducto(String sProducto) {
		this.sProducto = sProducto;
	}

	public String getsDescripcion() {
		return sDescripcion;
	}

	public void setsDescripcion(String sDescripcion) {
		this.sDescripcion = sDescripcion;
	}

	public String getsMarca() {
		return sMarca;
	}

	public void setsMarca(String sMarca) {
		this.sMarca = sMarca;
	}

	public int getnCantidad() {
		return nCantidad;
	}

	public void setnCantidad(int nCantidad) {
		this.nCantidad = nCantidad;
	}

	public double getnTotalUnitario() {
		return nTotalUnitario;
	}

	public void setnTotalUnitario(double nTotalUnitario) {
		this.nTotalUnitario = nTotalUnitario;
	}

	public double getnTotalPartida() {
		return nTotalPartida;
	}

	public void setnTotalPartida(double nTotalPartida) {
		this.nTotalPartida = nTotalPartida;
	}

	public Date getdFechaVenta() {
		return dFechaVenta;
	}

	public void setdFechaVenta(Date dFechaVenta) {
		this.dFechaVenta = dFechaVenta;
	}

	public int getnEstatusEntregaAlmacen() {
		return nEstatusEntregaAlmacen;
	}

	public void setnEstatusEntregaAlmacen(int nEstatusEntregaAlmacen) {
		this.nEstatusEntregaAlmacen = nEstatusEntregaAlmacen;
	}

	@Override
	public String toString() {
		return "VentaProductoDto [nId=" + nId + ", sNoParte=" + sNoParte + ", sProducto=" + sProducto
				+ ", sDescripcion=" + sDescripcion + ", sMarca=" + sMarca + ", nCantidad=" + nCantidad
				+ ", nTotalUnitario=" + nTotalUnitario + ", nTotalPartida=" + nTotalPartida + ", dFechaVenta="
				+ dFechaVenta + ", nEstatusEntregaAlmacen=" + nEstatusEntregaAlmacen + "]";
	}

	
	

	
	
}
