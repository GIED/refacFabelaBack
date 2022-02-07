package com.refacFabela.dto;

import java.io.Serializable;

public class AbonosDto implements Serializable {

	private Long id;
	private Long idVenta;
	private Double abono;
	private Long usuario;
	private Long formaPago;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdVenta() {
		return idVenta;
	}
	public void setIdVenta(Long idVenta) {
		this.idVenta = idVenta;
	}
	public Double getAbono() {
		return abono;
	}
	public void setAbono(Double abono) {
		this.abono = abono;
	}
	public Long getUsuario() {
		return usuario;
	}
	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}
	public Long getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(Long formaPago) {
		this.formaPago = formaPago;
	}
	
	
	
	
	
	
}
