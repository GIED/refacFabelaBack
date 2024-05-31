package com.refacFabela.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tw_venta_producto_cancela")
@NamedQuery(name = "TwVentaProductoCancela.findAll", query = "SELECT t FROM TwVentaProductoCancela t")
public class TwVentaProductoCancela  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;	
	@Column(name = "n_idVenta")
	private Long nIdVenta;	
	@Column(name = "n_idProductos")
	private Long nIdProductos;	
	@Column(name = "n_cantidad")
	private Integer nCantidad;	
	@Column(name = "n_precioUnitario")
	private Double nPrecioUnitario;	
	@Column(name = "n_ivaUnitario")
	private Double nIvaUnitario;	
	@Column(name = "n_totalUnitario")
	private Double nTotalUnitario;	
	@Column(name = "n_precioPartida")
	private Double nPrecioPartida;	
	@Column(name = "n_idUsuario")
	private Long nIdUsuario;	
	@Column(name = "d_fecha")
	private Date dFecha;	
	@Column(name = "n_idCaja")
	private Long nIdCaja;	
	@Column(name = "n_total_penaliza")
	private Double penaliza;
	@Column(name = "s_motivo")
	private String sMotivo;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "n_idCaja", updatable = false, insertable = false, nullable = false)
	private TwCaja twCaja;	
	
	@ManyToOne
	@JoinColumn(name = "n_idUsuario", updatable = false, insertable = false)
	private TcUsuario tcUsuario;
	
	@ManyToOne
	@JoinColumn(name = "n_idVenta", insertable = false , updatable = false)
	private TwVenta twVenta;
	@ManyToOne
	@JoinColumn(name = "n_idProductos", insertable = false , updatable = false)
	private  TcProducto tcProducto;
	
	
	public TwVentaProductoCancela() {
		
	}




	public String getsMotivo() {
		return sMotivo;
	}




	public void setsMotivo(String sMotivo) {
		this.sMotivo = sMotivo;
	}




	public Double getPenaliza() {
		return penaliza;
	}


	public void setPenaliza(Double penaliza) {
		this.penaliza = penaliza;
	}


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


	public Long getnIdProductos() {
		return nIdProductos;
	}


	public void setnIdProductos(Long nIdProductos) {
		this.nIdProductos = nIdProductos;
	}


	public Integer getnCantidad() {
		return nCantidad;
	}


	public void setnCantidad(Integer nCantidad) {
		this.nCantidad = nCantidad;
	}


	public Double getnPrecioUnitario() {
		return nPrecioUnitario;
	}


	public void setnPrecioUnitario(Double nPrecioUnitario) {
		this.nPrecioUnitario = nPrecioUnitario;
	}


	public Double getnIvaUnitario() {
		return nIvaUnitario;
	}


	public void setnIvaUnitario(Double nIvaUnitario) {
		this.nIvaUnitario = nIvaUnitario;
	}


	public Double getnTotalUnitario() {
		return nTotalUnitario;
	}


	public void setnTotalUnitario(Double nTotalUnitario) {
		this.nTotalUnitario = nTotalUnitario;
	}


	public Double getnPrecioPartida() {
		return nPrecioPartida;
	}


	public void setnPrecioPartida(Double nPrecioPartida) {
		this.nPrecioPartida = nPrecioPartida;
	}


	public Long getnIdUsuario() {
		return nIdUsuario;
	}


	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}


	public Date getdFecha() {
		return dFecha;
	}


	public void setdFecha(Date dFecha) {
		this.dFecha = dFecha;
	}


	public Long getnIdCaja() {
		return nIdCaja;
	}


	public void setnIdCaja(Long nIdCaja) {
		this.nIdCaja = nIdCaja;
	}

	public TwCaja getTwCaja() {
		return twCaja;
	}


	public void setTwCaja(TwCaja twCaja) {
		this.twCaja = twCaja;
	}


	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}


	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}


	public TwVenta getTwVenta() {
		return twVenta;
	}


	public void setTwVenta(TwVenta twVenta) {
		this.twVenta = twVenta;
	}


	public TcProducto getTcProducto() {
		return tcProducto;
	}


	public void setTcProducto(TcProducto tcProducto) {
		this.tcProducto = tcProducto;
	}


	@Override
	public String toString() {
		return "TwVentaProductoCancela [nId=" + nId + ", nIdVenta=" + nIdVenta + ", nIdProductos=" + nIdProductos
				+ ", nCantidad=" + nCantidad + ", nPrecioUnitario=" + nPrecioUnitario + ", nIvaUnitario=" + nIvaUnitario
				+ ", nTotalUnitario=" + nTotalUnitario + ", nPrecioPartida=" + nPrecioPartida + ", nIdUsuario="
				+ nIdUsuario + ", dFecha=" + dFecha + ", nIdCaja=" + nIdCaja + ", twCaja=" + twCaja + ", tcUsuario="
				+ tcUsuario + ", twVenta=" + twVenta + "]";
	}
	
	
	
	
	
	
	
	

}
