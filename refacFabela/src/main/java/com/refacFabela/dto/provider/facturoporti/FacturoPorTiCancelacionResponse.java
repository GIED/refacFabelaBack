package com.refacFabela.dto.provider.facturoporti;

import java.util.Map;

public class FacturoPorTiCancelacionResponse {

	private Boolean success;
	private String uuid;
	private String estatus;
	private String acuseBase64;
	private String codigoError;
	private String mensajeError;
	private Map<String, Object> raw;

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

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getAcuseBase64() {
		return acuseBase64;
	}

	public void setAcuseBase64(String acuseBase64) {
		this.acuseBase64 = acuseBase64;
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

	public Map<String, Object> getRaw() {
		return raw;
	}

	public void setRaw(Map<String, Object> raw) {
		this.raw = raw;
	}
}
