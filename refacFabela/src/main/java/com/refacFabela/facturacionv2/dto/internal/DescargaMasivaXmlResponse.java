package com.refacFabela.facturacionv2.dto.internal;

import java.util.Map;

public class DescargaMasivaXmlResponse {

	private Boolean success;
	private String solicitudId;
	private String estatus;
	private String codigoError;
	private String mensajeError;
	private Map<String, Object> rawResponse;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getSolicitudId() {
		return solicitudId;
	}

	public void setSolicitudId(String solicitudId) {
		this.solicitudId = solicitudId;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
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

	public Map<String, Object> getRawResponse() {
		return rawResponse;
	}

	public void setRawResponse(Map<String, Object> rawResponse) {
		this.rawResponse = rawResponse;
	}
}