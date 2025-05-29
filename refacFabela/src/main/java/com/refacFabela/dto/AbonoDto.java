package com.refacFabela.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class AbonoDto {
	
	Long idVenta;
	LocalDateTime fechaVenta;
	BigDecimal abono;
	String formaPago;
	String usuario;
	LocalDateTime fechaAbono;
	
	
	public AbonoDto() {
		
	}


	public Long getIdVenta() {
		return idVenta;
	}


	public void setIdVenta(Long idVenta) {
		this.idVenta = idVenta;
	}


	


	

	public BigDecimal getAbono() {
		return abono;
	}


	public void setAbono(BigDecimal abono) {
		this.abono = abono;
	}


	public String getFormaPago() {
		return formaPago;
	}


	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public LocalDateTime getFechaVenta() {
		return fechaVenta;
	}


	public void setFechaVenta(LocalDateTime fechaVenta) {
		this.fechaVenta = fechaVenta;
	}


	public LocalDateTime getFechaAbono() {
		return fechaAbono;
	}


	public void setFechaAbono(LocalDateTime fechaAbono) {
		this.fechaAbono = fechaAbono;
	}




}
