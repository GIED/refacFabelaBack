package com.refacFabela.facturacionv2.dto.internal;

import java.util.List;

public class CfdiRelacionadosResponse {

	private Boolean success;
	private String uuid;
	private List<String> relacionados;
	private String codigoError;
	private String mensajeError;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<String> getRelacionados() {
		return relacionados;
	}

	public void setRelacionados(List<String> relacionados) {
		this.relacionados = relacionados;
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