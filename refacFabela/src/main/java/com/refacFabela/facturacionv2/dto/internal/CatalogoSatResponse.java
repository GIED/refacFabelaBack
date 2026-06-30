package com.refacFabela.facturacionv2.dto.internal;

import java.util.List;
import java.util.Map;

public class CatalogoSatResponse {

	private Boolean success;
	private String catalogo;
	private List<Map<String, Object>> elementos;
	private String codigoError;
	private String mensajeError;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(String catalogo) {
		this.catalogo = catalogo;
	}

	public List<Map<String, Object>> getElementos() {
		return elementos;
	}

	public void setElementos(List<Map<String, Object>> elementos) {
		this.elementos = elementos;
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