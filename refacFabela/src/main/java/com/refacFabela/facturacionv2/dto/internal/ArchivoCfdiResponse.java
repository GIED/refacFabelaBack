package com.refacFabela.facturacionv2.dto.internal;

public class ArchivoCfdiResponse {

	private Boolean success;
	private String uuid;
	private String nombreArchivo;
	private String contentType;
	private String contenidoBase64;
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

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContenidoBase64() {
		return contenidoBase64;
	}

	public void setContenidoBase64(String contenidoBase64) {
		this.contenidoBase64 = contenidoBase64;
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