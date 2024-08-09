package com.refacFabela.tipoCambio;

import java.util.List;

public class Serie {
	private String idSerie;
	
	private String titulo;
	
	private List<DataSerie>datos;

	public String getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(String idSerie) {
		this.idSerie = idSerie;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<DataSerie> getDatos() {
		return datos;
	}

	public void setDatos(List<DataSerie> datos) {
		this.datos = datos;
	}

	@Override
	public String toString() {
		return "Serie [idSerie=" + idSerie + ", titulo=" + titulo + ", datos=" + datos + "]";
	}
	
	
	
}
