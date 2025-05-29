package com.refacFabela.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;

public class TwSaldoUtilizadoDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long nIdVenta;

	
	private BigDecimal nSaldoTotal;

	
	private BigDecimal nSaldoUtilizado;
	
	
	private Long nIdUsuario;
	
	
	private Boolean nEstatus;
	
	
	private LocalDateTime dFecha;
	
	
	private Long nIdCaja;
	
	
	private Long nIdVentaUtilizado;


	public Long getnIdVenta() {
		return nIdVenta;
	}


	public void setnIdVenta(Long nIdVenta) {
		this.nIdVenta = nIdVenta;
	}


	


	public BigDecimal getnSaldoTotal() {
		return nSaldoTotal;
	}


	public void setnSaldoTotal(BigDecimal nSaldoTotal) {
		this.nSaldoTotal = nSaldoTotal;
	}


	public BigDecimal getnSaldoUtilizado() {
		return nSaldoUtilizado;
	}


	public void setnSaldoUtilizado(BigDecimal nSaldoUtilizado) {
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

    
	
   


	public LocalDateTime getdFecha() {
		return dFecha;
	}


	public void setdFecha(LocalDateTime dFecha) {
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


	@Override
	public String toString() {
		return "TwSaldoUtilizadoDto [nIdVenta=" + nIdVenta + ", nSaldoTotal=" + nSaldoTotal + ", nSaldoUtilizado="
				+ nSaldoUtilizado + ", nIdUsuario=" + nIdUsuario + ", nEstatus=" + nEstatus + ", dFecha=" + dFecha
				+ ", nIdCaja=" + nIdCaja + ", nIdVentaUtilizado=" + nIdVentaUtilizado + "]";
	}
	
	
	
	
	

}
