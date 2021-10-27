package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tw_pedidos database table.
 * 
 */
@Entity
@Table(name="tw_pedidos")
@NamedQuery(name="TwPedido.findAll", query="SELECT t FROM TwPedido t")
public class TwPedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Temporal(TemporalType.DATE)
	private Date d_fechaPedido;

	@Temporal(TemporalType.DATE)
	private Date d_fechaRecibida;

	private int n_cantidadPedida;

	private int n_cantidaRecibida;

	@Column(name="n_estatus")
	private int nEstatus;

	private int n_MotivoPedido;

	private String s_clavePedido;

	@Column(name="s_observaciones")
	private String sObservaciones;

	//bi-directional many-to-one association to TcProducto
	@ManyToOne
	@JoinColumn(name="n_idProducto")
	private TcProducto tcProducto;

	//bi-directional many-to-one association to TcProveedore
	@ManyToOne
	@JoinColumn(name="n_idProveedor")
	private TcProveedore tcProveedore;

	public TwPedido() {
	}

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Date getD_fechaPedido() {
		return d_fechaPedido;
	}

	public void setD_fechaPedido(Date d_fechaPedido) {
		this.d_fechaPedido = d_fechaPedido;
	}

	public Date getD_fechaRecibida() {
		return d_fechaRecibida;
	}

	public void setD_fechaRecibida(Date d_fechaRecibida) {
		this.d_fechaRecibida = d_fechaRecibida;
	}

	public int getN_cantidadPedida() {
		return n_cantidadPedida;
	}

	public void setN_cantidadPedida(int n_cantidadPedida) {
		this.n_cantidadPedida = n_cantidadPedida;
	}

	public int getN_cantidaRecibida() {
		return n_cantidaRecibida;
	}

	public void setN_cantidaRecibida(int n_cantidaRecibida) {
		this.n_cantidaRecibida = n_cantidaRecibida;
	}

	public int getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}

	public int getN_MotivoPedido() {
		return n_MotivoPedido;
	}

	public void setN_MotivoPedido(int n_MotivoPedido) {
		this.n_MotivoPedido = n_MotivoPedido;
	}

	public String getS_clavePedido() {
		return s_clavePedido;
	}

	public void setS_clavePedido(String s_clavePedido) {
		this.s_clavePedido = s_clavePedido;
	}

	public String getsObservaciones() {
		return sObservaciones;
	}

	public void setsObservaciones(String sObservaciones) {
		this.sObservaciones = sObservaciones;
	}

	public TcProducto getTcProducto() {
		return tcProducto;
	}

	public void setTcProducto(TcProducto tcProducto) {
		this.tcProducto = tcProducto;
	}

	public TcProveedore getTcProveedore() {
		return tcProveedore;
	}

	public void setTcProveedore(TcProveedore tcProveedore) {
		this.tcProveedore = tcProveedore;
	}

	

}