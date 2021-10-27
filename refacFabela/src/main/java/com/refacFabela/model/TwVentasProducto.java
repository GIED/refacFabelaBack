package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tw_ventas_producto database table.
 * 
 */
@Entity
@Table(name="tw_ventas_producto")
@NamedQuery(name="TwVentasProducto.findAll", query="SELECT t FROM TwVentasProducto t")
public class TwVentasProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Temporal(TemporalType.DATE)
	private Date d_fechaEntregaAlmacen;

	@Temporal(TemporalType.DATE)
	private Date d_fechaEntregaEstimada;

	@Column(name="n_cantidad")
	private int nCantidad;

	private int n_estatusEntregaAlmacen;

	private double n_ivaPartida;

	private double n_ivaUnitario;

	private double n_precioPartida;

	private double n_precioUnitario;

	private double n_totalPartida;

	private double n_totalUnitario;

	//bi-directional many-to-one association to TcProducto
	@ManyToOne
	@JoinColumn(name="n_idProductos")
	private TcProducto tcProducto;

	//bi-directional many-to-one association to TcUsuario
	@ManyToOne
	@JoinColumn(name="n_idUsuario")
	private TcUsuario tcUsuario;

	//bi-directional many-to-one association to TwVenta
	@ManyToOne
	@JoinColumn(name="n_idVenta")
	private TwVenta twVenta;

	public TwVentasProducto() {
	}

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Date getD_fechaEntregaAlmacen() {
		return d_fechaEntregaAlmacen;
	}

	public void setD_fechaEntregaAlmacen(Date d_fechaEntregaAlmacen) {
		this.d_fechaEntregaAlmacen = d_fechaEntregaAlmacen;
	}

	public Date getD_fechaEntregaEstimada() {
		return d_fechaEntregaEstimada;
	}

	public void setD_fechaEntregaEstimada(Date d_fechaEntregaEstimada) {
		this.d_fechaEntregaEstimada = d_fechaEntregaEstimada;
	}

	public int getnCantidad() {
		return nCantidad;
	}

	public void setnCantidad(int nCantidad) {
		this.nCantidad = nCantidad;
	}

	public int getN_estatusEntregaAlmacen() {
		return n_estatusEntregaAlmacen;
	}

	public void setN_estatusEntregaAlmacen(int n_estatusEntregaAlmacen) {
		this.n_estatusEntregaAlmacen = n_estatusEntregaAlmacen;
	}

	public double getN_ivaPartida() {
		return n_ivaPartida;
	}

	public void setN_ivaPartida(double n_ivaPartida) {
		this.n_ivaPartida = n_ivaPartida;
	}

	public double getN_ivaUnitario() {
		return n_ivaUnitario;
	}

	public void setN_ivaUnitario(double n_ivaUnitario) {
		this.n_ivaUnitario = n_ivaUnitario;
	}

	public double getN_precioPartida() {
		return n_precioPartida;
	}

	public void setN_precioPartida(double n_precioPartida) {
		this.n_precioPartida = n_precioPartida;
	}

	public double getN_precioUnitario() {
		return n_precioUnitario;
	}

	public void setN_precioUnitario(double n_precioUnitario) {
		this.n_precioUnitario = n_precioUnitario;
	}

	public double getN_totalPartida() {
		return n_totalPartida;
	}

	public void setN_totalPartida(double n_totalPartida) {
		this.n_totalPartida = n_totalPartida;
	}

	public double getN_totalUnitario() {
		return n_totalUnitario;
	}

	public void setN_totalUnitario(double n_totalUnitario) {
		this.n_totalUnitario = n_totalUnitario;
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

	
}