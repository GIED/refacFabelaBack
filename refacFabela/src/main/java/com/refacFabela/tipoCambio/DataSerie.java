package com.refacFabela.tipoCambio;

public class DataSerie {

	private String fecha;
	
	private String dato;

	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getDato() {
		return dato;
	}


	public void setDato(String dato) {
		this.dato = dato;
	}


	@Override
	public String toString() {
		return "DataSerie [fecha=" + fecha + ", dato=" + dato + "]";
	}
	
	
	
	
}
