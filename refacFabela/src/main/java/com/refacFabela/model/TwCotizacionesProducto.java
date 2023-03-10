package com.refacFabela.model;

import java.io.Serializable;
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
	private double nIvaPartida;
	
	@Column(name = "n_ivaUnitario")
	private double nIvaUnitario;
	
	@Column(name = "n_precioPartida")
	private double nPrecioPartida;
	
	@Column(name = "n_precioUnitario")
	private double nPrecioUnitario;
	
	@Column(name = "n_totalPartida")
	private double nTotalPartida;
	
	@Column(name = "n_totalUnitario")
	private double nTotalUnitario;
	
	@Column(name = "s_condicionEntrega")
	private String sCondicionEntrega;

	// bi-directional many-to-one association to TcProducto
	@ManyToOne
	@JoinColumn(name = "n_idProductos", insertable = false, updatable = false)
	private TcProducto tcProducto;

	// bi-directional many-to-one association to TwCotizacione
	@ManyToOne
	@JoinColumn(name = "n_idCotizaciones", insertable = false, updatable = false)
	private TwCotizaciones twCotizacione;

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

	public double getnIvaPartida() {
		return nIvaPartida;
	}

	public void setnIvaPartida(double nIvaPartida) {
		this.nIvaPartida = nIvaPartida;
	}

	public double getnIvaUnitario() {
		return nIvaUnitario;
	}

	public void setnIvaUnitario(double nIvaUnitario) {
		this.nIvaUnitario = nIvaUnitario;
	}

	public double getnPrecioPartida() {
		return nPrecioPartida;
	}

	public void setnPrecioPartida(double nPrecioPartida) {
		this.nPrecioPartida = nPrecioPartida;
	}

	public double getnPrecioUnitario() {
		return nPrecioUnitario;
	}

	public void setnPrecioUnitario(double nPrecioUnitario) {
		this.nPrecioUnitario = nPrecioUnitario;
	}

	public double getnTotalPartida() {
		return nTotalPartida;
	}

	public void setnTotalPartida(double nTotalPartida) {
		this.nTotalPartida = nTotalPartida;
	}

	public double getnTotalUnitario() {
		return nTotalUnitario;
	}

	public void setnTotalUnitario(double nTotalUnitario) {
		this.nTotalUnitario = nTotalUnitario;
	}

	public TcProducto getTcProducto() {
		return tcProducto;
	}

	public void setTcProducto(TcProducto tcProducto) {
		this.tcProducto = tcProducto;
	}

	public TwCotizaciones getTwCotizacione() {
		return twCotizacione;
	}

	public void setTwCotizacione(TwCotizaciones twCotizacione) {
		this.twCotizacione = twCotizacione;
	}

	public String getsCondicionEntrega() {
		return sCondicionEntrega;
	}

	public void setsCondicionEntrega(String sCondicionEntrega) {
		this.sCondicionEntrega = sCondicionEntrega;
	}
	
	

	

}