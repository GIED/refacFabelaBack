package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tw_pedido_producto")
@NamedQuery(name = "TwPedidoProducto.findAll", query = "SELECT t FROM TwPedidoProducto t")
public class TwPedidoProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;		
	
	@Column(name = "s_clavePedido")
	private String sClavePedido;
	
	@Column(name = "d_fechaPedido")
	private Date dFechaPedido;
	
	@Column(name = "n_MotivoPedido")
	private Long nMotivoPedido;
	
	@Column(name = "n_idProducto")
	private Long nIdProducto;
	
	@Column(name = "n_cantidadPedida")
	private int nCantidadPedida;
	
	@Column(name = "n_idProveedor")
	private Long nIdProveedor;	
	
	@Column(name = "n_cantidaRecibida")
	private int nCantidaRecibida;
	
	@Column(name = "d_fechaRecibida")
	private Date dFechaRecibida;
	
	@Column(name = "n_estatus")
	private Integer nEstatus;	
	
	@Column(name = "s_observaciones")
	private String sObservaciones;	
	
	@Column(name = "n_idUsuario")
	private Long nIdUsuario;	
	
	@Column(name = "n_idPedido")
	private Long nIdPedido;	

	// bi-directional many-to-one association to TcProducto
	@ManyToOne
	@JoinColumn(name = "n_idProducto", insertable = false, updatable = false)
	private TcProducto tcProducto;

	// bi-directional many-to-one association to TcProveedore
	@ManyToOne
	@JoinColumn(name = "n_idProveedor", insertable = false, updatable = false)
	private TcProveedore tcProveedore;	
	
	@ManyToOne
	@JoinColumn(name = "n_idUsuario",  insertable = false, updatable = false)
	private TcUsuario tcUsuario;
	
	@ManyToOne
	@JoinColumn(name = "n_idPedido",  insertable = false, updatable = false)
	private TwPedido twPedido;
	
	@ManyToOne
	@JoinColumn(name = "n_estatus",  insertable = false, updatable = false)
	private TcEstatusPedidoProducto tcEstatusPedidoProducto;
	
	


	public TwPedidoProducto() {
	}


	public Long getnId() {
		return nId;
	}


	public void setnId(Long nId) {
		this.nId = nId;
	}


	public String getsClavePedido() {
		return sClavePedido;
	}


	public void setsClavePedido(String sClavePedido) {
		this.sClavePedido = sClavePedido;
	}


	public Date getdFechaPedido() {
		return dFechaPedido;
	}


	public void setdFechaPedido(Date dFechaPedido) {
		this.dFechaPedido = dFechaPedido;
	}


	public Long getnMotivoPedido() {
		return nMotivoPedido;
	}


	public void setnMotivoPedido(Long nMotivoPedido) {
		this.nMotivoPedido = nMotivoPedido;
	}


	public Long getnIdProducto() {
		return nIdProducto;
	}


	public void setnIdProducto(Long nIdProducto) {
		this.nIdProducto = nIdProducto;
	}


	public int getnCantidadPedida() {
		return nCantidadPedida;
	}


	public void setnCantidadPedida(int nCantidadPedida) {
		this.nCantidadPedida = nCantidadPedida;
	}


	public Long getnIdProveedor() {
		return nIdProveedor;
	}


	public void setnIdProveedor(Long nIdProveedor) {
		this.nIdProveedor = nIdProveedor;
	}


	public int getnCantidaRecibida() {
		return nCantidaRecibida;
	}


	public void setnCantidaRecibida(int nCantidaRecibida) {
		this.nCantidaRecibida = nCantidaRecibida;
	}


	public Date getdFechaRecibida() {
		return dFechaRecibida;
	}


	public void setdFechaRecibida(Date dFechaRecibida) {
		this.dFechaRecibida = dFechaRecibida;
	}

	public Integer getnEstatus() {
		return nEstatus;
	}


	public void setnEstatus(Integer nEstatus) {
		this.nEstatus = nEstatus;
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


	public Long getnIdUsuario() {
		return nIdUsuario;
	}


	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}


	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}


	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}


	public Long getnIdPedido() {
		return nIdPedido;
	}


	public void setnIdPedido(Long nIdPedido) {
		this.nIdPedido = nIdPedido;
	}


	public TwPedido getTwPedido() {
		return twPedido;
	}


	public void setTwPedido(TwPedido twPedido) {
		this.twPedido = twPedido;
	}


	@Override
	public String toString() {
		return "TwPedidoProducto [nId=" + nId + ", sClavePedido=" + sClavePedido + ", dFechaPedido=" + dFechaPedido
				+ ", nMotivoPedido=" + nMotivoPedido + ", nIdProducto=" + nIdProducto + ", nCantidadPedida="
				+ nCantidadPedida + ", nIdProveedor=" + nIdProveedor + ", nCantidaRecibida=" + nCantidaRecibida
				+ ", dFechaRecibida=" + dFechaRecibida + ", nEstatus=" + nEstatus + ", sObservaciones=" + sObservaciones
				+ ", nIdUsuario=" + nIdUsuario + ", nIdPedido=" + nIdPedido + ", tcProducto=" + tcProducto
				+ ", tcProveedore=" + tcProveedore + ", tcUsuario=" + tcUsuario + ", twPedido=" + twPedido + "]";
	}


	public TcEstatusPedidoProducto getTcEstatusPedidoProducto() {
		return tcEstatusPedidoProducto;
	}


	public void setTcEstatusPedidoProducto(TcEstatusPedidoProducto tcEstatusPedidoProducto) {
		this.tcEstatusPedidoProducto = tcEstatusPedidoProducto;
	}

	
	
	

	
	
	

	

}