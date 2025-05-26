package com.refacFabela.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

@Entity
@Table(name = "tw_cotizaciones_producto")
@NamedQuery(name = "TwCotizacionesProducto.findAll", query = "SELECT t FROM TwCotizacionesProducto t")
public class TwCotizacionesProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name= "n_idProductos")
	private Long nIdProducto;
	
	@Column(name = "n_idCotizaciones")
	private Long nIdCotizacion;

	@Column(name = "n_cantidad")
	private int nCantidad;
	
	@Column(name = "n_ivaPartida")
	private BigDecimal nIvaPartida;
	
	@Column(name = "n_ivaUnitario")
	private BigDecimal nIvaUnitario;
	
	@Column(name = "n_precioPartida")
	private BigDecimal nPrecioPartida;
	
	@Column(name = "n_precioUnitario")
	private BigDecimal nPrecioUnitario;
	
	@Column(name = "n_totalPartida")
	private BigDecimal nTotalPartida;
	
	@Column(name = "n_totalUnitario")
	private BigDecimal nTotalUnitario;
	
	@Column(name = "s_condicionEntrega")
	private String sCondicionEntrega;
	
	@Column(name = "n_id_descuento")
	private Integer nIdDescuento;
	
	

	// bi-directional many-to-one association to TcProducto
	@ManyToOne
	@JoinColumn(name = "n_idProductos", insertable = false, updatable = false)
	private TcProducto tcProducto;

	// bi-directional many-to-one association to TwCotizacione
	@ManyToOne
	@JoinColumn(name = "n_idCotizaciones", insertable = false, updatable = false)
	private TwCotizaciones twCotizaciones;

	public TwCotizacionesProducto() {
	}

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

	public Long getnIdCotizacion() {
		return nIdCotizacion;
	}

	public void setnIdCotizacion(Long nIdCotizacion) {
		this.nIdCotizacion = nIdCotizacion;
	}

	public int getnCantidad() {
		return nCantidad;
	}

	public void setnCantidad(int nCantidad) {
		this.nCantidad = nCantidad;
	}
	

	public BigDecimal getnIvaPartida() {
		return nIvaPartida;
	}

	public void setnIvaPartida(BigDecimal nIvaPartida) {
		this.nIvaPartida = nIvaPartida;
	}

	public BigDecimal getnIvaUnitario() {
		return nIvaUnitario;
	}

	public void setnIvaUnitario(BigDecimal nIvaUnitario) {
		this.nIvaUnitario = nIvaUnitario;
	}

	public BigDecimal getnPrecioPartida() {
		return nPrecioPartida;
	}

	public void setnPrecioPartida(BigDecimal nPrecioPartida) {
		this.nPrecioPartida = nPrecioPartida;
	}

	public BigDecimal getnPrecioUnitario() {
		return nPrecioUnitario;
	}

	public void setnPrecioUnitario(BigDecimal nPrecioUnitario) {
		this.nPrecioUnitario = nPrecioUnitario;
	}

	public BigDecimal getnTotalPartida() {
		return nTotalPartida;
	}

	public void setnTotalPartida(BigDecimal nTotalPartida) {
		this.nTotalPartida = nTotalPartida;
	}

	public BigDecimal getnTotalUnitario() {
		return nTotalUnitario;
	}

	public void setnTotalUnitario(BigDecimal nTotalUnitario) {
		this.nTotalUnitario = nTotalUnitario;
	}

	public TcProducto getTcProducto() {
		return tcProducto;
	}

	public void setTcProducto(TcProducto tcProducto) {
		this.tcProducto = tcProducto;
	}

	
	public TwCotizaciones getTwCotizaciones() {
		return twCotizaciones;
	}

	public void setTwCotizaciones(TwCotizaciones twCotizaciones) {
		this.twCotizaciones = twCotizaciones;
	}

	public String getsCondicionEntrega() {
		return sCondicionEntrega;
	}

	public void setsCondicionEntrega(String sCondicionEntrega) {
		this.sCondicionEntrega = sCondicionEntrega;
	}

	public Integer getnIdDescuento() {
		return nIdDescuento;
	}

	public void setnIdDescuento(Integer nIdDescuento) {
		this.nIdDescuento = nIdDescuento;
	}
	
	

	

}