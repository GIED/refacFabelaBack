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
import javax.persistence.Table;

@Entity
@Table(name = "th_stock_producto")
public class ThStockProducto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name = "n_id_producto")
	private Long nIdProducto;
	
	@Column(name = "n_stock")
	private Integer nStock;
	
	@Column(name = "n_cantidad")
	private Integer nCantidad;
	
	@Column(name = "n_stock_final")
	private Integer nStockFinal;	
	
	@Column(name = "d_fecha")
	private LocalDateTime dFecha;		

	@Column(name = "n_id_bodega")
	private Long nIdBodega;
	
	@Column(name = "n_id_anaquel")
	private Long nIdAnaquel;
	
	@Column(name = "n_id_nivel")
	private Long nIdNivel;
	
	@Column(name = "n_id_venta")
	private Long nIdVenta;
	
	
	@ManyToOne
	@JoinColumn(name = "n_id_producto" , insertable = false , updatable = false)
	private TcProducto tcProducto;	

	// bi-directional many-to-one association to TwVenta
	@ManyToOne
	@JoinColumn(name = "n_id_venta", insertable = false , updatable = false)
	private TwVenta twVenta;
	
	@ManyToOne
	@JoinColumn(name = "n_id_anaquel", insertable=false, updatable=false)
	private TcAnaquel tcAnaquel;

	// bi-directional many-to-one association to TcBodega
	@ManyToOne
	@JoinColumn(name = "n_id_bodega", insertable=false, updatable=false)
	private TcBodega tcBodega;

	// bi-directional many-to-one association to TcNivel
	@ManyToOne
	@JoinColumn(name = "n_id_nivel", insertable=false, updatable=false)
	private TcNivel tcNivel;

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Long getnIdProducto() {
		return nIdProducto;
	}

	public void setnIdProducto(Long nIdProducto) {
		this.nIdProducto = nIdProducto;
	}

	public Integer getnStock() {
		return nStock;
	}

	public void setnStock(Integer nStock) {
		this.nStock = nStock;
	}

	public Integer getnCantidad() {
		return nCantidad;
	}

	public void setnCantidad(Integer nCantidad) {
		this.nCantidad = nCantidad;
	}

	public Integer getnStockFinal() {
		return nStockFinal;
	}

	public void setnStockFinal(Integer nStockFinal) {
		this.nStockFinal = nStockFinal;
	}


	public LocalDateTime getdFecha() {
		return dFecha;
	}

	public void setdFecha(LocalDateTime dFecha) {
		this.dFecha = dFecha;
	}

	public Long getnIdBodega() {
		return nIdBodega;
	}

	public void setnIdBodega(Long nIdBodega) {
		this.nIdBodega = nIdBodega;
	}

	public Long getnIdAnaquel() {
		return nIdAnaquel;
	}

	public void setnIdAnaquel(Long nIdAnaquel) {
		this.nIdAnaquel = nIdAnaquel;
	}

	public Long getnIdNivel() {
		return nIdNivel;
	}

	public void setnIdNivel(Long nIdNivel) {
		this.nIdNivel = nIdNivel;
	}

	public Long getnIdVenta() {
		return nIdVenta;
	}

	public void setnIdVenta(Long nIdVenta) {
		this.nIdVenta = nIdVenta;
	}

	public TcProducto getTcProducto() {
		return tcProducto;
	}

	public void setTcProducto(TcProducto tcProducto) {
		this.tcProducto = tcProducto;
	}

	public TwVenta getTwVenta() {
		return twVenta;
	}

	public void setTwVenta(TwVenta twVenta) {
		this.twVenta = twVenta;
	}

	public TcAnaquel getTcAnaquel() {
		return tcAnaquel;
	}

	public void setTcAnaquel(TcAnaquel tcAnaquel) {
		this.tcAnaquel = tcAnaquel;
	}

	public TcBodega getTcBodega() {
		return tcBodega;
	}

	public void setTcBodega(TcBodega tcBodega) {
		this.tcBodega = tcBodega;
	}

	public TcNivel getTcNivel() {
		return tcNivel;
	}

	public void setTcNivel(TcNivel tcNivel) {
		this.tcNivel = tcNivel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	


}
