package com.refacFabela.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
	private LocalDateTime dFechaVenta;	
	
	@Column(name = "n_tipoPago")
	private long nTipoPago;		

	@Column(name = "d_fechaInicioCredito")
	private LocalDateTime dFechaInicioCredito;
	
	@Column(name = "d_fechaTerminoCredito")
	private LocalDateTime dFechaTerminoCredito;
	
	@Column(name = "d_fechaPagoCredito")
	private LocalDateTime dFechaPagoCredito;
	
	@Column(name = "n_totalVenta")
	private BigDecimal nTotalVenta;
	
	@Column(name = "n_anticipo")
	private BigDecimal nAnticipo;
	
	@Column(name = "n_totalAbono")
	private BigDecimal nTotalAbono;
	
	@Column(name = "n_saldoTotal")
	private BigDecimal nSaldoTotal;
	
	@Column(name = "n_avancePago")
	private BigDecimal nAvancePago;
	
	@Column(name = "s_estatus")
	private String sEstatus;
	
	@Column(name = "nDescuento")
	private BigDecimal descuento;
	
	@Column(name = "n_idTipoVenta")
	private Long nIdTipoVenta;
	
	@Column(name = "n_saldo_favor")
	private BigDecimal nSaldoFavor;	
	
	@Column(name = "n_id_venta_utilizado")
	private Long nIdVentaUtilizado;
	
	@Column(name = "n_saldo")
	private Boolean nSaldo;
	
	@Column(name = "n_vencido")
	private Boolean nVencido;
	
	
	
	
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





	public Boolean getnVencido() {
		return nVencido;
	}

	public void setnVencido(Boolean nVencido) {
		this.nVencido = nVencido;
	}

	public TwCaja getTwCaja() {
		return twCaja;
	}

	public void setTwCaja(TwCaja twCaja) {
		this.twCaja = twCaja;
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

	

	public long getnTipoPago() {
		return nTipoPago;
	}

	public void setnTipoPago(long nTipoPago) {
		this.nTipoPago = nTipoPago;
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
	
	

	
	
	

	public BigDecimal getnTotalVenta() {
		return nTotalVenta;
	}

	public void setnTotalVenta(BigDecimal nTotalVenta) {
		this.nTotalVenta = nTotalVenta;
	}

	public BigDecimal getnAnticipo() {
		return nAnticipo;
	}

	public void setnAnticipo(BigDecimal nAnticipo) {
		this.nAnticipo = nAnticipo;
	}

	public BigDecimal getnTotalAbono() {
		return nTotalAbono;
	}

	public void setnTotalAbono(BigDecimal nTotalAbono) {
		this.nTotalAbono = nTotalAbono;
	}

	public BigDecimal getnSaldoTotal() {
		return nSaldoTotal;
	}

	public void setnSaldoTotal(BigDecimal nSaldoTotal) {
		this.nSaldoTotal = nSaldoTotal;
	}

	public BigDecimal getnAvancePago() {
		return nAvancePago;
	}

	public void setnAvancePago(BigDecimal nAvancePago) {
		this.nAvancePago = nAvancePago;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getnSaldoFavor() {
		return nSaldoFavor;
	}

	public void setnSaldoFavor(BigDecimal nSaldoFavor) {
		this.nSaldoFavor = nSaldoFavor;
	}

	public Long getnIdVentaUtilizado() {
		return nIdVentaUtilizado;
	}

	public void setnIdVentaUtilizado(Long nIdVentaUtilizado) {
		this.nIdVentaUtilizado = nIdVentaUtilizado;
	}
	
	
	

	
	public LocalDateTime getdFechaVenta() {
		return dFechaVenta;
	}

	public void setdFechaVenta(LocalDateTime dFechaVenta) {
		this.dFechaVenta = dFechaVenta;
	}

	public LocalDateTime getdFechaInicioCredito() {
		return dFechaInicioCredito;
	}

	public void setdFechaInicioCredito(LocalDateTime dFechaInicioCredito) {
		this.dFechaInicioCredito = dFechaInicioCredito;
	}

	public LocalDateTime getdFechaTerminoCredito() {
		return dFechaTerminoCredito;
	}

	public void setdFechaTerminoCredito(LocalDateTime dFechaTerminoCredito) {
		this.dFechaTerminoCredito = dFechaTerminoCredito;
	}

	public LocalDateTime getdFechaPagoCredito() {
		return dFechaPagoCredito;
	}

	public void setdFechaPagoCredito(LocalDateTime dFechaPagoCredito) {
		this.dFechaPagoCredito = dFechaPagoCredito;
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
