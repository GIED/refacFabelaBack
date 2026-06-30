package com.refacFabela.dto.provider.facturoporti;

import java.util.Map;

public class FacturoPorTiTimbradoResponse {

	private Boolean success;
	private String uuid;
	private String serie;
	private String folio;
	private String xmlBase64;
	private String pdfBase64;
	private String qrBase64;
	private String fechaTimbrado;
	private String selloCfd;
	private String selloSat;
	private String noCertificadoSat;
	private String noCertificadoEmisor;
	private String cadenaOriginal;
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

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getXmlBase64() {
		return xmlBase64;
	}

	public void setXmlBase64(String xmlBase64) {
		this.xmlBase64 = xmlBase64;
	}

	public String getPdfBase64() {
		return pdfBase64;
	}

	public void setPdfBase64(String pdfBase64) {
		this.pdfBase64 = pdfBase64;
	}

	public String getQrBase64() {
		return qrBase64;
	}

	public void setQrBase64(String qrBase64) {
		this.qrBase64 = qrBase64;
	}

	public String getFechaTimbrado() {
		return fechaTimbrado;
	}

	public void setFechaTimbrado(String fechaTimbrado) {
		this.fechaTimbrado = fechaTimbrado;
	}

	public String getSelloCfd() {
		return selloCfd;
	}

	public void setSelloCfd(String selloCfd) {
		this.selloCfd = selloCfd;
	}

	public String getSelloSat() {
		return selloSat;
	}

	public void setSelloSat(String selloSat) {
		this.selloSat = selloSat;
	}

	public String getNoCertificadoSat() {
		return noCertificadoSat;
	}

	public void setNoCertificadoSat(String noCertificadoSat) {
		this.noCertificadoSat = noCertificadoSat;
	}

	public String getNoCertificadoEmisor() {
		return noCertificadoEmisor;
	}

	public void setNoCertificadoEmisor(String noCertificadoEmisor) {
		this.noCertificadoEmisor = noCertificadoEmisor;
	}

	public String getCadenaOriginal() {
		return cadenaOriginal;
	}

	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
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
