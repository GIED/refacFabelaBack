package com.refacFabela.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tw_venta_productos_traer")
@NamedQuery(name = "TwVentaProductosTraer.findAll", query = "SELECT t FROM TwVentaProductosTraer t")
public class TwVentaProductosTraer implements Serializable {
	private static final Long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name = "n_idVenta")
	private Long nIdVenta;
	
	@Column(name = "n_idProducto")
	private Long nIdProducto;
	
	@Column(name = "n_cantidad")
	private Integer nCantidad;
	
	@Column(name = "s_ubicacion")
	private String sUbicacion;

	@Column(name = "n_estatus")
	private Long nEstatus;
	
	@Column(name = "d_fecha")
	private Date dFecha;
	
	@ManyToOne
	@JoinColumn(name = "n_idProducto" , insertable = false , updatable = false)
	private TcProducto tcProducto;
	

	// bi-directional many-to-one association to TwVenta
	@ManyToOne
	@JoinColumn(name = "n_idVenta", insertable = false , updatable = false)
	private TwVenta twVenta;


	public Long getnId() {
		return nId;
	}


	public void setnId(Long nId) {
		this.nId = nId;
	}


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



	


	public Integer getnCantidad() {
		return nCantidad;
	}


	public void setnCantidad(Integer nCantidad) {
		this.nCantidad = nCantidad;
	}


	public String getsUbicacion() {
		return sUbicacion;
	}


	public void setsUbicacion(String sUbicacion) {
		this.sUbicacion = sUbicacion;
	}


	public Long getnEstatus() {
		return nEstatus;
	}


	public void setnEstatus(Long nEstatus) {
		this.nEstatus = nEstatus;
	}


	public Date getdFecha() {
		return dFecha;
	}


	public void setdFecha(Date dFecha) {
		this.dFecha = dFecha;
	}


	public TcProducto getTcProducto() {
		return tcProducto;
	}


	public void setTcProducto(TcProducto tcProducto) {
		this.tcProducto = tcProducto;
	}


	public TwVenta getTwVenta() {
		return twVenta;
	}


	public void setTwVenta(TwVenta twVenta) {
		this.twVenta = twVenta;
	}


	@Override
	public String toString() {
		return "TwVentaProductosTraer [nId=" + nId + ", nIdVenta=" + nIdVenta + ", nIdProducto=" + nIdProducto
				+ ", nCantidad=" + nCantidad + ", sUbicacion=" + sUbicacion + ", nEstatus=" + nEstatus + ", dFecha="
				+ dFecha + ", tcProducto=" + tcProducto + ", twVenta=" + twVenta + "]";
	}


	

	
	
	

}
