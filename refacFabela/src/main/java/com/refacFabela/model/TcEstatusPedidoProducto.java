package com.refacFabela.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tc_estatus_pedido_producto")
@NamedQuery(name = "TcEstatusPedidoProducto.findAll", query = "SELECT t FROM TcEstatusPedidoProducto t")
public class TcEstatusPedidoProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name = "s_estatus")
	private String sEstatus;
	
	
	public TcEstatusPedidoProducto() {
		
	}

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public String getsEstatus() {
		return sEstatus;
	}

	public void setsEstatus(String sEstatus) {
		this.sEstatus = sEstatus;
	}
	
	
}
