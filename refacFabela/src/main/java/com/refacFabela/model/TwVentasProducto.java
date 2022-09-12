package com.refacFabela.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tw_ventas_producto")
public class TwVentasProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name = "n_idVenta")
	private Long nIdVenta;
	
	@Column(name = "n_idProductos")
	private Long nIdProducto;
	
	@Column(name = "n_idUsuario")
	private Long nIdUsuario;
	
	@Column(name = "d_fechaEntregaAlmacen")
	@Temporal(TemporalType.DATE)
	private Date dFechaEntregaAlmacen;

	@Column(name = "d_fechaEntregaEstimada")
	private LocalDateTime dFechaEntregaEstimada;

	@Column(name = "n_cantidad")
	private int nCantidad;
	
	@Column(name = "n_estatusEntregaAlmacen")
	private int nEstatusEntregaAlmacen;
	
	@Column(name = "n_ivaPartida")
	private double nIvaPartida;
	
	@Column(name = "n_ivaUnitario")
	private double nIvaUnitario;
	
	@Column(name = "n_precioPartida")
	private double nPrecioPartida;
	
	@Column(name = "n_precioUnitario")
	private double nPrecioUnitario;
	
	@Column(name = "n_totalPartida")
	private double nTotalPartida;
	
	@Column(name = "n_totalUnitario")
	private double nTotalUnitario;
	
	@Column(name = "n_estatus")
	private Integer nEstatus;

	// bi-directional many-to-one association to TcProducto
	@ManyToOne
	@JoinColumn(name = "n_idProductos" , insertable = false , updatable = false)
	private TcProducto tcProducto;

	// bi-directional many-to-one association to TcUsuario
	@ManyToOne
	@JoinColumn(name = "n_idUsuario", insertable = false , updatable = false)
	private TcUsuario tcUsuario;

	// bi-directional many-to-one association to TwVenta
	@ManyToOne
	@JoinColumn(name = "n_idVenta", insertable = false , updatable = false)
	private TwVenta twVenta;

	public TwVentasProducto() {
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

	public Long getnIdProducto() {
		return nIdProducto;
	}

	public void setnIdProducto(Long nIdProducto) {
		this.nIdProducto = nIdProducto;
	}

	public Long getnIdUsuario() {
		return nIdUsuario;
	}

	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}

	public Date getdFechaEntregaAlmacen() {
		return dFechaEntregaAlmacen;
	}

	public void setdFechaEntregaAlmacen(Date dFechaEntregaAlmacen) {
		this.dFechaEntregaAlmacen = dFechaEntregaAlmacen;
	}

	

	public LocalDateTime getdFechaEntregaEstimada() {
		return dFechaEntregaEstimada;
	}

	public void setdFechaEntregaEstimada(LocalDateTime dFechaEntregaEstimada) {
		this.dFechaEntregaEstimada = dFechaEntregaEstimada;
	}

	public int getnCantidad() {
		return nCantidad;
	}

	public void setnCantidad(int nCantidad) {
		this.nCantidad = nCantidad;
	}

	

	public int getnEstatusEntregaAlmacen() {
		return nEstatusEntregaAlmacen;
	}

	public void setnEstatusEntregaAlmacen(int nEstatusEntregaAlmacen) {
		this.nEstatusEntregaAlmacen = nEstatusEntregaAlmacen;
	}

	public double getnIvaPartida() {
		return nIvaPartida;
	}

	public void setnIvaPartida(double nIvaPartida) {
		this.nIvaPartida = nIvaPartida;
	}

	public double getnIvaUnitario() {
		return nIvaUnitario;
	}

	public void setnIvaUnitario(double nIvaUnitario) {
		this.nIvaUnitario = nIvaUnitario;
	}

	public double getnPrecioPartida() {
		return nPrecioPartida;
	}

	public void setnPrecioPartida(double nPrecioPartida) {
		this.nPrecioPartida = nPrecioPartida;
	}

	public double getnPrecioUnitario() {
		return nPrecioUnitario;
	}

	public void setnPrecioUnitario(double nPrecioUnitario) {
		this.nPrecioUnitario = nPrecioUnitario;
	}

	public double getnTotalPartida() {
		return nTotalPartida;
	}

	public void setnTotalPartida(double nTotalPartida) {
		this.nTotalPartida = nTotalPartida;
	}

	public double getnTotalUnitario() {
		return nTotalUnitario;
	}

	public void setnTotalUnitario(double nTotalUnitario) {
		this.nTotalUnitario = nTotalUnitario;
	}

	public TcProducto getTcProducto() {
		return tcProducto;
	}

	public void setTcProducto(TcProducto tcProducto) {
		this.tcProducto = tcProducto;
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

	@Override
	public String toString() {
		return "TwVentasProducto [nId=" + nId + ", nIdVenta=" + nIdVenta + ", nIdProducto=" + nIdProducto
				+ ", nIdUsuario=" + nIdUsuario + ", dFechaEntregaAlmacen=" + dFechaEntregaAlmacen
				+ ", dFechaEntregaEstimada=" + dFechaEntregaEstimada + ", nCantidad=" + nCantidad
				+ ", nEstatusEntregaAlmacen=" + nEstatusEntregaAlmacen + ", nIvaPartida=" + nIvaPartida
				+ ", nIvaUnitario=" + nIvaUnitario + ", nPrecioPartida=" + nPrecioPartida + ", nPrecioUnitario="
				+ nPrecioUnitario + ", nTotalPartida=" + nTotalPartida + ", nTotalUnitario=" + nTotalUnitario
				+ ", tcProducto=" + tcProducto + ", tcUsuario=" + tcUsuario + ", twVenta=" + twVenta + "]";
	}

	public Integer getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(Integer nEstatus) {
		this.nEstatus = nEstatus;
	}
	
	

	
	

	

}