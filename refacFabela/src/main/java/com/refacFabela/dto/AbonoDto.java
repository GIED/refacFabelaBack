package com.refacFabela.dto;

import java.util.Date;

public class AbonoDto {
	
	Long idVenta;
	Date fechaVenta;
	Double abono;
	String formaPago;
	String usuario;
	Date fechaAbono;
	
	
	public AbonoDto() {
		
	}


	public Long getIdVenta() {
		return idVenta;
	}


	public void setIdVenta(Long idVenta) {
		this.idVenta = idVenta;
	}


	public Date getFechaVenta() {
		return fechaVenta;
	}


	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}


	public Double getAbono() {
		return abono;
	}


	public void setAbono(Double abono) {
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


	public Date getFechaAbono() {
		return fechaAbono;
	}


	public void setFechaAbono(Date fechaAbono) {
		this.fechaAbono = fechaAbono;
	}
	

}
