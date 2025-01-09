package com.refacFabela.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tv_pedido_detalle")
@NamedQuery(name = "TvPedidoDetalle.findAll", query = "SELECT t FROM TvPedidoDetalle t")
public class TvPedidoDetalle implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id	
	@Column(name = "n_id")
	private Long nId;		
	@Column(name = "s_cve_pedido")
	private String sCvePedido;		
	@Column(name = "d_fecha_pedido")
	private Date dFechaPedido;		
	@Column(name = "s_observaciones")
	private String sObservaciones;	
	@Column(name = "n_estatus")
	private Long nEstatus;		
	@Column(name = "d_fecha_pedido_cierre")
	private Date dFechaPedidoCierre;		
	@Column(name = "s_nombreUsuario")
	private String sNombreUsuario;		
	@Column(name = "n_total_productos")
	private Long nTotalProductos;	
	@Column(name = "n_total_sin_entregar")
	private Long nTotalSinEntregar;
	@Column(name = "n_total_entregados")
	private Long nTotalEntregados;
	@Column(name = "n_idVenta")
	private Long nIdVenta;
	
	
	
	
		
	public TvPedidoDetalle() {
		
	}


	public Long getnId() {
		return nId;
	}


	public void setnId(Long nId) {
		this.nId = nId;
	}


	public String getsCvePedido() {
		return sCvePedido;
	}


	public void setsCvePedido(String sCvePedido) {
		this.sCvePedido = sCvePedido;
	}


	public Date getdFechaPedido() {
		return dFechaPedido;
	}


	public void setdFechaPedido(Date dFechaPedido) {
		this.dFechaPedido = dFechaPedido;
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


	public Date getdFechaPedidoCierre() {
		return dFechaPedidoCierre;
	}


	public void setdFechaPedidoCierre(Date dFechaPedidoCierre) {
		this.dFechaPedidoCierre = dFechaPedidoCierre;
	}


	public String getsNombreUsuario() {
		return sNombreUsuario;
	}


	public void setsNombreUsuario(String sNombreUsuario) {
		this.sNombreUsuario = sNombreUsuario;
	}


	public Long getnTotalProductos() {
		return nTotalProductos;
	}


	public void setnTotalProductos(Long nTotalProductos) {
		this.nTotalProductos = nTotalProductos;
	}


	public Long getnTotalSinEntregar() {
		return nTotalSinEntregar;
	}


	public void setnTotalSinEntregar(Long nTotalSinEntregar) {
		this.nTotalSinEntregar = nTotalSinEntregar;
	}


	public Long getnTotalEntregados() {
		return nTotalEntregados;
	}


	public void setnTotalEntregados(Long nTotalEntregados) {
		this.nTotalEntregados = nTotalEntregados;
	}


	public Long getnIdVenta() {
		return nIdVenta;
	}


	public void setnIdVenta(Long nIdVenta) {
		this.nIdVenta = nIdVenta;
	}


	
	
	

}
