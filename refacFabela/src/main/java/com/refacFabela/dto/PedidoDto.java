package com.refacFabela.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.refacFabela.model.TwPedidoProducto;

public class PedidoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long nId;
	private Long nIdUsuario;
	private String sCvePedido;
	private LocalDateTime dFechaPedido;
	private String sObservaciones;
	private Long nEstatus;
	private LocalDateTime dFechaPedidoCierre;
	private List<TwPedidoProducto> twPedidoProducto;

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

	public List<TwPedidoProducto> getTwPedidoProducto() {
		return twPedidoProducto;
	}

	public void setTwPedidoProducto(List<TwPedidoProducto> twPedidoProducto) {
		this.twPedidoProducto = twPedidoProducto;
	}

	@Override
	public String toString() {
		return "PedidoDto [nId=" + nId + ", nIdUsuario=" + nIdUsuario + ", sCvePedido=" + sCvePedido + ", dFechaPedido="
				+ dFechaPedido + ", sObservaciones=" + sObservaciones + ", nEstatus=" + nEstatus
				+ ", dFechaPedidoCierre=" + dFechaPedidoCierre + ", twPedidoProducto=" + twPedidoProducto + "]";
	}
	
	

}
