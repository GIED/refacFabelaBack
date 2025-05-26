package com.refacFabela.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AbonosDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;	
	private BigDecimal abono;
	private String usuario;
	private String fecha;
	private String formaPago;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	public BigDecimal getAbono() {
		return abono;
	}
	public void setAbono(BigDecimal abono) {
		this.abono = abono;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	@Override
	public String toString() {
		return "AbonosDto [id=" + id + ", abono=" + abono + ", usuario=" + usuario + ", fecha=" + fecha + ", formaPago="
				+ formaPago + "]";
	}
	
	
	
	
	
	
	
	
	
}
