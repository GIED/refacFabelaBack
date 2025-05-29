package com.refacFabela.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

@Entity
@Table(name = "tv_venta_stock")
@Immutable
public class TvVentaStock  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name = "n_idVenta")
	private Long nIdVenta;
	
	@Column(name = "n_idproducto")
	private Long nIdProducto;
	
	@Column(name = "n_cantidad")
	private Integer nCantidad;
	
	@Column(name = "d_fechaVenta")
	private BigDecimal dFechaVenta;

	@Column(name = "n_cantidad_total")
	private Integer nCantidadTotal;
	
	@ManyToOne
	@JoinColumn(name = "n_idVenta", updatable = false, insertable = false )
	private TwVenta twVenta;

	// bi-directional many-to-one association to TcTipoVenta
	@ManyToOne
	@JoinColumn(name = "n_idproducto", updatable = false, insertable = false)
	private TcProducto tcProducto;
	
	
	public TvVentaStock() {
		
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

	public Integer getnCantidad() {
		return nCantidad;
	}

	public void setnCantidad(Integer nCantidad) {
		this.nCantidad = nCantidad;
	}


	public BigDecimal getdFechaVenta() {
		return dFechaVenta;
	}



	public void setdFechaVenta(BigDecimal dFechaVenta) {
		this.dFechaVenta = dFechaVenta;
	}



	public Integer getnCantidadTotal() {
		return nCantidadTotal;
	}

	public void setnCantidadTotal(Integer nCantidadTotal) {
		this.nCantidadTotal = nCantidadTotal;
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

	
	
}
