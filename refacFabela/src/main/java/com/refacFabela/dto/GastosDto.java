package com.refacFabela.dto;

import java.math.BigDecimal;
import java.util.Date;

public class GastosDto {
	
	Long nId;
	String gasto;
	String descripcion;
	Date fecha;
	String usuario;
	BigDecimal monto;
	
	public GastosDto() {
		
	}
	
	
	public Long getnId() {
		return nId;
	}


	public void setnId(Long nId) {
		this.nId = nId;
	}


	public String getGasto() {
		return gasto;
	}
	public void setGasto(String gasto) {
		this.gasto = gasto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	


	public BigDecimal getMonto() {
		return monto;
	}


	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}


	@Override
	public String toString() {
		return "GastosDto [nId=" + nId + ", gasto=" + gasto + ", descripcion=" + descripcion + ", fecha=" + fecha
				+ ", usuario=" + usuario + ", monto=" + monto + "]";
	}
	
	
	
	

}
