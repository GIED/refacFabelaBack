package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tw_ventas")
@NamedQuery(name = "TwVenta.findAll", query = "SELECT t FROM TwVenta t")
public class TwVenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private long nId;
	
	@Column(name = "n_idCliente")
	private long nIdCliente;
	
	@Column(name = "n_idUsuario")
	private long nIdUsuario;
	
	@Column(name = "s_folioVenta")
	private String sFolioVenta;

	@Column(name = "d_fechaVenta")
	private Date dFechaVenta;
	
	@Column(name = "n_estatusVenta")
	private int nEstatusVenta;
	
	@Column(name = "n_idFacturacion")
	private long nIdFacturacion;
	
	@Column(name = "n_idTipoVenta")
	private long nIdTipoVenta;
	
	@Column(name = "n_tipoPago")
	private long nTipoPago;
	
	@Column(name = "n_idCaja")
	private long nIdCaja;
	
	@Column(name = "d_fechaInicioCredito")
	private Date dFechaInicioCredito;
	
	@Column(name = "d_fechaTerminoCredito")
	private Date dFechaTerminoCredito;
	
	@Column(name = "d_fechaPagoCredito")
	private Date dFechaPagoCredito;
	


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
	@ManyToOne
	@JoinColumn(name = "n_idCaja", updatable = false, insertable = false)
	private TwCaja twCaja;	
	

	public TwVenta() {
	}


	public long getnId() {
		return nId;
	}


	public void setnId(long nId) {
		this.nId = nId;
	}


	public long getnIdCliente() {
		return nIdCliente;
	}


	public void setnIdCliente(long nIdCliente) {
		this.nIdCliente = nIdCliente;
	}


	public long getnIdUsuario() {
		return nIdUsuario;
	}


	public void setnIdUsuario(long nIdUsuario) {
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


	public long getnIdFacturacion() {
		return nIdFacturacion;
	}


	public void setnIdFacturacion(long nIdFacturacion) {
		this.nIdFacturacion = nIdFacturacion;
	}


	public long getnIdTipoVenta() {
		return nIdTipoVenta;
	}


	public void setnIdTipoVenta(long nIdTipoVenta) {
		this.nIdTipoVenta = nIdTipoVenta;
	}


	public long getnTipoPago() {
		return nTipoPago;
	}


	public void setnTipoPago(long nTipoPago) {
		this.nTipoPago = nTipoPago;
	}


	public long getnIdCaja() {
		return nIdCaja;
	}


	public void setnIdCaja(long nIdCaja) {
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
	
	

	
	

	
}