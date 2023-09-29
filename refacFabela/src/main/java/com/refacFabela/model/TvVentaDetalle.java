package com.refacFabela.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tv_ventadetalle")

public class TvVentaDetalle implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id	
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name = "n_idCliente")
	private long nIdCliente;
	
	@Column(name = "n_idUsuario")
	private long nIdUsuario;
	
	@Column(name = "s_folioVenta")
	private String sFolioVenta;
	
	@Column(name = "d_fechaVenta")
	private Date dFechaVenta;	
	
	@Column(name = "n_tipoPago")
	private long nTipoPago;		

	@Column(name = "d_fechaInicioCredito")
	private Date dFechaInicioCredito;
	
	@Column(name = "d_fechaTerminoCredito")
	private Date dFechaTerminoCredito;
	
	@Column(name = "d_fechaPagoCredito")
	private Date dFechaPagoCredito;
	
	@Column(name = "n_totalVenta")
	private Double nTotalVenta;
	
	@Column(name = "n_anticipo")
	private Double nAnticipo;
	
	@Column(name = "n_totalAbono")
	private Double nTotalAbono;
	
	@Column(name = "n_saldoTotal")
	private Double nSaldoTotal;
	
	@Column(name = "n_avancePago")
	private Double nAvancePago;
	
	@Column(name = "s_estatus")
	private String sEstatus;
	
	@Column(name = "nDescuento")
	private Double descuento;
	
	@Column(name = "n_idTipoVenta")
	private Long nIdTipoVenta;
	
	@Column(name = "n_saldo_favor")
	private Double nSaldoFavor;	
	
	@Column(name = "n_id_venta_utilizado")
	private Long nIdVentaUtilizado;
	
	@Column(name = "n_saldo")
	private Boolean nSaldo;
	
	
	
	
	@ManyToOne
	@JoinColumn(name = "n_idCliente", updatable = false, insertable = false )
	private TcCliente tcCliente;

	
	@ManyToOne
	@JoinColumn(name = "n_idUsuario", updatable = false, insertable = false)
	private TcUsuario tcUsuario;
	
	@ManyToOne
	@JoinColumn(name = "n_estatusVenta", updatable = false, insertable = false)
	private TcEstatusVenta tcEstatusVenta;
	
	@ManyToOne
	@JoinColumn(name = "n_idFormaPago", updatable = false, insertable = false )
	private TcFormapago tcFormapago ;
	
	@ManyToOne
	@JoinColumn(name = "n_idCaja", updatable = false, insertable = false )
	private TwCaja twCaja ;
	
	@ManyToOne
	@JoinColumn(name = "n_idTipoVenta", updatable = false, insertable = false )
	private TcTipoVenta tcTipoVenta ;
	
	  

	public TvVentaDetalle() {
		
	}	
	
	public TcFormapago getTcFormapago() {
		return tcFormapago;
	}





	public void setTcFormapago(TcFormapago tcFormapago) {
		this.tcFormapago = tcFormapago;
	}





	public TwCaja getTwCaja() {
		return twCaja;
	}

	public void setTwCaja(TwCaja twCaja) {
		this.twCaja = twCaja;
	}

	public double getDescuento() {
		return descuento;
	}


	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}


	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
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

	public long getnTipoPago() {
		return nTipoPago;
	}

	public void setnTipoPago(long nTipoPago) {
		this.nTipoPago = nTipoPago;
	}

	public Date getdFechaInicioCredito() {
		return dFechaInicioCredito;
	}

	public void setdFechaInicioCredito(Date dFechaInicioCredito) {
		this.dFechaInicioCredito = dFechaInicioCredito;
	}

	public Date getdFechaTerminoCredito() {
		return dFechaTerminoCredito;
	}

	public void setdFechaTerminoCredito(Date dFechaTerminoCredito) {
		this.dFechaTerminoCredito = dFechaTerminoCredito;
	}

	public Date getdFechaPagoCredito() {
		return dFechaPagoCredito;
	}

	public void setdFechaPagoCredito(Date dFechaPagoCredito) {
		this.dFechaPagoCredito = dFechaPagoCredito;
	}

	public double getnTotalVenta() {
		return nTotalVenta;
	}



	public Double getnTotalAbono() {
		return nTotalAbono;
	}

	public void setnTotalAbono(Double nTotalAbono) {
		this.nTotalAbono = nTotalAbono;
	}

	public Double getnSaldoTotal() {
		return nSaldoTotal;
	}

	public void setnSaldoTotal(Double nSaldoTotal) {
		this.nSaldoTotal = nSaldoTotal;
	}

	public Double getnAvancePago() {
		return nAvancePago;
	}

	public void setnAvancePago(Double nAvancePago) {
		this.nAvancePago = nAvancePago;
	}

	public void setnTotalVenta(Double nTotalVenta) {
		this.nTotalVenta = nTotalVenta;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	public String getsEstatus() {
		return sEstatus;
	}

	public void setsEstatus(String sEstatus) {
		this.sEstatus = sEstatus;
	}

	public TcCliente getTcCliente() {
		return tcCliente;
	}

	public void setTcCliente(TcCliente tcCliente) {
		this.tcCliente = tcCliente;
	}

	

	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}

	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}

	public TcEstatusVenta getTcEstatusVenta() {
		return tcEstatusVenta;
	}

	public void setTcEstatusVenta(TcEstatusVenta tcEstatusVenta) {
		this.tcEstatusVenta = tcEstatusVenta;
	}
	
	

	public Long getnIdTipoVenta() {
		return nIdTipoVenta;
	}

	public void setnIdTipoVenta(Long nIdTipoVenta) {
		this.nIdTipoVenta = nIdTipoVenta;
	}

	public TcTipoVenta getTcTipoVenta() {
		return tcTipoVenta;
	}

	public void setTcTipoVenta(TcTipoVenta tcTipoVenta) {
		this.tcTipoVenta = tcTipoVenta;
	}
	
	

	public Double getnAnticipo() {
		return nAnticipo;
	}

	public void setnAnticipo(Double nAnticipo) {
		this.nAnticipo = nAnticipo;
	}
	
	

	public Double getnSaldoFavor() {
		return nSaldoFavor;
	}

	public void setnSaldoFavor(Double nSaldoFavor) {
		this.nSaldoFavor = nSaldoFavor;
	}
	
	

	public Long getnIdVentaUtilizado() {
		return nIdVentaUtilizado;
	}

	public void setnIdVentaUtilizado(Long nIdVentaUtilizado) {
		this.nIdVentaUtilizado = nIdVentaUtilizado;
	}
	
	
	

	public Boolean getnSaldo() {
		return nSaldo;
	}

	public void setnSaldo(Boolean nSaldo) {
		this.nSaldo = nSaldo;
	}

	@Override
	public String toString() {
		return "TvVentaDetalle [nId=" + nId + ", nIdCliente=" + nIdCliente + ", nIdUsuario=" + nIdUsuario
				+ ", sFolioVenta=" + sFolioVenta + ", dFechaVenta=" + dFechaVenta + ", nTipoPago=" + nTipoPago
				+ ", dFechaInicioCredito=" + dFechaInicioCredito + ", dFechaTerminoCredito=" + dFechaTerminoCredito
				+ ", dFechaPagoCredito=" + dFechaPagoCredito + ", nTotalVenta=" + nTotalVenta + ", nTotalAbono="
				+ nTotalAbono + ", nSaldoTotal=" + nSaldoTotal + ", nAvancePago=" + nAvancePago + ", sEstatus="
				+ sEstatus + ", descuento=" + descuento + ", tcCliente=" + tcCliente + ", tcUsuario=" + tcUsuario
				+ ", tcEstatusVenta=" + tcEstatusVenta + ", tcFormapago=" + tcFormapago + ", twCaja=" + twCaja + "]";
	}

	
	
	
	
}
