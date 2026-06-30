package com.refacFabela.dto;

import java.util.Map;

public class CodigoPostalResponse {

	private Boolean success;
	private String codigoPostal;
	private Map<String, Object> datos;
	private String codigoError;
	private String mensajeError;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public Map<String, Object> getDatos() {
		return datos;
	}

	public void setDatos(Map<String, Object> datos) {
		this.datos = datos;
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
}
