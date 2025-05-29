package com.refacFabela.model;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@Table(name = "tw_pedido")
@NamedQuery(name = "TwPedido.findAll", query = "SELECT t FROM TwPedido t")
public class TwPedido  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;	
	
	@Column(name = "n_idUsuario")
	private Long nIdUsuario;	
	
	@Column(name = "s_cve_pedido")
	private String sCvePedido;	
	
	@Column(name = "d_fecha_pedido")
	private LocalDateTime dFechaPedido;	
	
	@Column(name = "s_observaciones")
	private String sObservaciones;	
	
	@Column(name = "n_estatus")
	private Long nEstatus;	
	
	@Column(name = "d_fecha_pedido_cierre")
	private LocalDateTime dFechaPedidoCierre;
	
	@Column(name = "n_idVenta")
	private Long nIdVenta;	
	
	
	// bi-directional many-to-one association to TcProducto

	@ManyToOne
	@JoinColumn(name = "n_idUsuario",  insertable = false, updatable = false)
	private TcUsuario tcUsuario;
	
	@ManyToOne
	@JoinColumn(name = "n_idVenta", insertable = false , updatable = false)
	private TwVenta twVenta;
	
	
	
	public TwPedido() {
		
		
	}



	public Long getnId() {
		return nId;
	}



	public void setnId(Long nId) {
		this.nId = nId;
	}



	public Long getnIdUsuario() {
		return nIdUsuario;
	}



	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}



	public String getsCvePedido() {
		return sCvePedido;
	}



	public void setsCvePedido(String sCvePedido) {
		this.sCvePedido = sCvePedido;
	}






	public LocalDateTime getdFechaPedido() {
		return dFechaPedido;
	}



	public void setdFechaPedido(LocalDateTime dFechaPedido) {
		this.dFechaPedido = dFechaPedido;
	}



	public LocalDateTime getdFechaPedidoCierre() {
		return dFechaPedidoCierre;
	}



	public void setdFechaPedidoCierre(LocalDateTime dFechaPedidoCierre) {
		this.dFechaPedidoCierre = dFechaPedidoCierre;
	}



	public String getsObservaciones() {
		return sObservaciones;
	}



	public void setsObservaciones(String sObservaciones) {
		this.sObservaciones = sObservaciones;
	}



	public Long getnEstatus() {
		return nEstatus;
	}



	public void setnEstatus(Long nEstatus) {
		this.nEstatus = nEstatus;
	}



	



	



	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}



	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}
	
	



	public Long getnIdVenta() {
		return nIdVenta;
	}



	public void setnIdVenta(Long nIdVenta) {
		this.nIdVenta = nIdVenta;
	}



	public TwVenta getTwVenta() {
		return twVenta;
	}



	public void setTwVenta(TwVenta twVenta) {
		this.twVenta = twVenta;
	}



	@Override
	public String toString() {
		return "TwPedido [nId=" + nId + ", nIdUsuario=" + nIdUsuario + ", sCvePedido=" + sCvePedido + ", dFechaPedido="
				+ dFechaPedido + ", sObservaciones=" + sObservaciones + ", nEstatus=" + nEstatus
				+ ", dFechaPedidoCierre=" + dFechaPedidoCierre + ", tcUsuario=" + tcUsuario + "]";
	}



	
	
	
	
	

}
