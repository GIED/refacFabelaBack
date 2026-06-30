package com.refacFabela.facturacionv2.dto.internal;

import java.util.Map;

public class ValidacionCertificadoResponse {

	private Boolean success;
	private Boolean valido;
	private String numeroCertificado;
	private String codigoError;
	private String mensajeError;
	private Map<String, Object> rawResponse;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Boolean getValido() {
		return valido;
	}

	public void setValido(Boolean valido) {
		this.valido = valido;
	}

	public String getNumeroCertificado() {
		return numeroCertificado;
	}

	public void setNumeroCertificado(String numeroCertificado) {
		this.numeroCertificado = numeroCertificado;
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