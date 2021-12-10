package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tw_ventas")
@NamedQuery(name = "TwVenta.findAll", query = "SELECT t FROM TwVenta t")
public class TwVenta implements Serializable {
	private static final Long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name = "n_idCliente")
	private Long nIdCliente;
	
	@Column(name = "n_idUsuario")
	private Long nIdUsuario;
	
	@Column(name = "s_folioVenta")
	private String sFolioVenta;

	@Column(name = "d_fechaVenta")
	private Date dFechaVenta;
	
	@Column(name = "n_estatusVenta")
	private int nEstatusVenta;
	
	@Column(name = "n_idFacturacion")
	private Long nIdFacturacion;
	
	@Column(name = "n_idTipoVenta")
	private Long nIdTipoVenta;
	
	@Column(name = "n_tipoPago")
	private Long nTipoPago;
	
	@Column(name = "n_idCaja")
	private Long nIdCaja;
	
	@Column(name = "d_fechaInicioCredito")
	private Date dFechaInicioCredito;
	
	@Column(name = "d_fechaTerminoCredito")
	private Date dFechaTerminoCredito;
	
	@Column(name = "d_fechaPagoCredito")
	private Date dFechaPagoCredito;
	
	@Column(name = "n_idCotizacion")
	private Long nIdCotizacion;
	


	// bi-directional many-to-one association to TcCliente
	@ManyToOne
	@JoinColumn(name = "n_idCliente", updatable = false, insertable = false )
	private TcCliente tcCliente;

	// bi-directional many-to-one association to TcTipoVenta
	@ManyToOne
	@JoinColumn(name = "n_idTipoVenta", updatable = false, insertable = false)
	private TcTipoVenta tcTipoVenta;

	// bi-directional many-to-one association to TcUsuario
	@ManyToOne
	@JoinColumn(name = "n_idUsuario", updatable = false, insertable = false)
	private TcUsuario tcUsuario;

	// bi-directional many-to-one association to TwCaja
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "n_idCaja", updatable = false, insertable = false)
	private TwCaja twCaja;	
	

	public TwVenta() {
	}


	public Long getnId() {
		return nId;
	}


	public void setnId(Long nId) {
		this.nId = nId;
	}


	public Long getnIdCliente() {
		return nIdCliente;
	}


	public void setnIdCliente(Long nIdCliente) {
		this.nIdCliente = nIdCliente;
	}


	public Long getnIdUsuario() {
		return nIdUsuario;
	}


	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}


	public String getsFolioVenta() {
		return sFolioVenta;
	}


	public void setsFolioVenta(String sFolioVenta) {
		this.sFolioVenta = sFolioVenta;
	}


	


	public Date getdFechaVenta() {
		return dFechaVenta;
	}


	public void setdFechaVenta(Date dFechaVenta) {
		this.dFechaVenta = dFechaVenta;
	}


	public int getnEstatusVenta() {
		return nEstatusVenta;
	}


	public void setnEstatusVenta(int nEstatusVenta) {
		this.nEstatusVenta = nEstatusVenta;
	}


	public Long getnIdFacturacion() {
		return nIdFacturacion;
	}


	public void setnIdFacturacion(Long nIdFacturacion) {
		this.nIdFacturacion = nIdFacturacion;
	}


	public Long getnIdTipoVenta() {
		return nIdTipoVenta;
	}


	public void setnIdTipoVenta(Long nIdTipoVenta) {
		this.nIdTipoVenta = nIdTipoVenta;
	}


	public Long getnTipoPago() {
		return nTipoPago;
	}


	public void setnTipoPago(Long nTipoPago) {
		this.nTipoPago = nTipoPago;
	}


	public Long getnIdCaja() {
		return nIdCaja;
	}


	public void setnIdCaja(Long nIdCaja) {
		this.nIdCaja = nIdCaja;
	}


	public Date getdFechaInicioCredito() {
		return dFechaInicioCredito;
	}


	public void setdFechaInicioCredito(Date dFechaInicioCredito) {
		this.dFechaInicioCredito = dFechaInicioCredito;
	}


	public Date getdFechaPagoCredito() {
		return dFechaPagoCredito;
	}


	public void setdFechaPagoCredito(Date dFechaPagoCredito) {
		this.dFechaPagoCredito = dFechaPagoCredito;
	}


	public TcCliente getTcCliente() {
		return tcCliente;
	}


	public void setTcCliente(TcCliente tcCliente) {
		this.tcCliente = tcCliente;
	}


	public TcTipoVenta getTcTipoVenta() {
		return tcTipoVenta;
	}


	public void setTcTipoVenta(TcTipoVenta tcTipoVenta) {
		this.tcTipoVenta = tcTipoVenta;
	}


	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}


	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}


	public TwCaja getTwCaja() {
		return twCaja;
	}


	public void setTwCaja(TwCaja twCaja) {
		this.twCaja = twCaja;
	}


	public Date getdFechaTerminoCredito() {
		return dFechaTerminoCredito;
	}


	public void setdFechaTerminoCredito(Date dFechaTerminoCredito) {
		this.dFechaTerminoCredito = dFechaTerminoCredito;
	}


	public Long getnIdCotizacion() {
		return nIdCotizacion;
	}


	public void setnIdCotizacion(Long nIdCotizacion) {
		this.nIdCotizacion = nIdCotizacion;
	}



	
	
	
	
	

	
}