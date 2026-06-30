package com.refacFabela.dto;

import java.math.BigDecimal;
import java.util.Map;

public class TimbradoResponse {

	private Boolean success;
	private String uuid;
	private String serie;
	private String folio;
	private Long razonSocialId;
	private String rfcEmisor;
	private String rfcReceptor;
	private BigDecimal total;
	private String moneda;
	private String tipoComprobante;
	private String fechaTimbrado;
	private String selloCfd;
	private String selloSat;
	private String noCertificadoSat;
	private String noCertificadoEmisor;
	private String cadenaOriginalComplementoSat;
	private String xmlBase64;
	private String pdfBase64;
	private String qrBase64;
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

	public Long getRazonSocialId() {
		return razonSocialId;
	}

	public void setRazonSocialId(Long razonSocialId) {
		this.razonSocialId = razonSocialId;
	}

	public String getRfcEmisor() {
		return rfcEmisor;
	}

	public void setRfcEmisor(String rfcEmisor) {
		this.rfcEmisor = rfcEmisor;
	}

	public String getRfcReceptor() {
		return rfcReceptor;
	}

	public void setRfcReceptor(String rfcReceptor) {
		this.rfcReceptor = rfcReceptor;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
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

	public String getCadenaOriginalComplementoSat() {
		return cadenaOriginalComplementoSat;
	}

	public void setCadenaOriginalComplementoSat(String cadenaOriginalComplementoSat) {
		this.cadenaOriginalComplementoSat = cadenaOriginalComplementoSat;
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
