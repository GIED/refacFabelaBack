package com.refacFabela.facturacionv2.dto.internal;

import java.util.Map;

public class StatusCfdiResponse {

	private Boolean success;
	private String uuid;
	private String estatusSat;
	private String estatusCancelacion;
	private String codigoError;
	private String mensajeError;
	private Map<String, Object> rawResponse;

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

	public String getEstatusSat() {
		return estatusSat;
	}

	public void setEstatusSat(String estatusSat) {
		this.estatusSat = estatusSat;
	}

	public String getEstatusCancelacion() {
		return estatusCancelacion;
	}

	public void setEstatusCancelacion(String estatusCancelacion) {
		this.estatusCancelacion = estatusCancelacion;
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