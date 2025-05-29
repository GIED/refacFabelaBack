package com.refacFabela.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tv_stockproducto")
@NamedQuery(name = "TvStockProducto.findAll", query = "SELECT t FROM TvStockProducto t")
public class TvStockProducto implements  Serializable {
	private static final long serialVersionUID = 1L;
	
	
	
	@Id	
	@Column(name = "n_idProducto")
	private Long nIdProducto;

	@Column(name = "n_cantidad_total")
	private BigDecimal nCantidadTotal;
	
	
	
	@ManyToOne
	@JoinColumn(name = "n_idProducto", referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcProducto tcProducto;
	

	public  TvStockProducto() {
	}


	public Long getnIdProducto() {
		return nIdProducto;
	}


	public void setnIdProducto(Long nIdProducto) {
		this.nIdProducto = nIdProducto;
	}	


	


	public BigDecimal getnCantidadTotal() {
		return nCantidadTotal;
	}


	public void setnCantidadTotal(BigDecimal nCantidadTotal) {
		this.nCantidadTotal = nCantidadTotal;
	}


	public TcProducto getTcProducto() {
		return tcProducto;
	}


	public void setTcProducto(TcProducto tcProducto) {
		this.tcProducto = tcProducto;
	}
	
	

	
}
