package com.refacFabela.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TcEstatusVenta;
import com.refacFabela.model.TcFormapago;
import com.refacFabela.model.TcTipoVenta;
import com.refacFabela.model.TcUsuario;
import com.refacFabela.model.TwCaja;

public class TvVentaDetalleDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long nId;

	private long nIdCliente;

	private long nIdUsuario;

	private String sFolioVenta;

	private Date dFechaVenta;

	private long nTipoPago;

	private Date dFechaInicioCredito;

	private Date dFechaTerminoCredito;

	private Date dFechaPagoCredito;

	private BigDecimal nTotalVenta;

	private BigDecimal nAnticipo;

	private BigDecimal nTotalAbono;

	private BigDecimal nSaldoTotal;

	private BigDecimal nAvancePago;

	private String sEstatus;

	private BigDecimal descuento;

	private Long nIdTipoVenta;

	private TcCliente tcCliente;

	private TcUsuario tcUsuario;

	private TcEstatusVenta tcEstatusVenta;

	private TcFormapago tcFormapago;

	private TwCaja twCaja;

	private TcTipoVenta tcTipoVenta;

	private BigDecimal nSaldoFavor;

	private Long nIdVentaUtilizado;

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

	public String getsEstatus() {
		return sEstatus;
	}

	public void setsEstatus(String sEstatus) {
		this.sEstatus = sEstatus;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public Long getnIdTipoVenta() {
		return nIdTipoVenta;
	}

	public void setnIdTipoVenta(Long nIdTipoVenta) {
		this.nIdTipoVenta = nIdTipoVenta;
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

	public TcTipoVenta getTcTipoVenta() {
		return tcTipoVenta;
	}

	public void setTcTipoVenta(TcTipoVenta tcTipoVenta) {
		this.tcTipoVenta = tcTipoVenta;
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

}
