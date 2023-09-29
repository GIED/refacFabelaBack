package com.refacFabela.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

public class TwSaldoUtilizadoDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long nIdVenta;

	
	private Double nSaldoTotal;

	
	private Double nSaldoUtilizado;
	
	
	private Long nIdUsuario;
	
	
	private Boolean nEstatus;
	
	
	private Date dFecha;
	
	
	private Long nIdCaja;
	
	
	private Long nIdVentaUtilizado;


	public Long getnIdVenta() {
		return nIdVenta;
	}


	public void setnIdVenta(Long nIdVenta) {
		this.nIdVenta = nIdVenta;
	}


	public Double getnSaldoTotal() {
		return nSaldoTotal;
	}


	public void setnSaldoTotal(Double nSaldoTotal) {
		this.nSaldoTotal = nSaldoTotal;
	}


	public Double getnSaldoUtilizado() {
		return nSaldoUtilizado;
	}


	public void setnSaldoUtilizado(Double nSaldoUtilizado) {
		this.nSaldoUtilizado = nSaldoUtilizado;
	}


	public Long getnIdUsuario() {
		return nIdUsuario;
	}


	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}


	public Boolean getnEstatus() {
		return nEstatus;
	}


	public void setnEstatus(Boolean nEstatus) {
		this.nEstatus = nEstatus;
	}


	public Date getdFecha() {
		return dFecha;
	}


	public void setdFecha(Date dFecha) {
		this.dFecha = dFecha;
	}


	public Long getnIdCaja() {
		return nIdCaja;
	}


	public void setnIdCaja(Long nIdCaja) {
		this.nIdCaja = nIdCaja;
	}


	


	public Long getnIdVentaUtilizado() {
		return nIdVentaUtilizado;
	}


	public void setnIdVentaUtilizado(Long nIdVentaUtilizado) {
		this.nIdVentaUtilizado = nIdVentaUtilizado;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	

}
