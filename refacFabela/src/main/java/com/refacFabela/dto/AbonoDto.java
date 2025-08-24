package com.refacFabela.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class AbonoDto {
	
	Long idVenta;
	String fechaVenta;
	BigDecimal abono;
	String formaPago;
	String usuario;
	String fechaAbono;
	
	
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


	public String getFechaVenta() {
		return fechaVenta;
	}


	public void setFechaVenta(String fechaVenta) {
		this.fechaVenta = fechaVenta;
	}


	public String getFechaAbono() {
		return fechaAbono;
	}


	public void setFechaAbono(String fechaAbono) {
		this.fechaAbono = fechaAbono;
	}


	




}
