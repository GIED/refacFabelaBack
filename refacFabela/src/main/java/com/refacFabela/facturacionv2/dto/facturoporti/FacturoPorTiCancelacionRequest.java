package com.refacFabela.facturacionv2.dto.facturoporti;

import java.math.BigDecimal;
import java.util.Map;

public class FacturoPorTiCancelacionRequest {

	private String rfcEmisor;
	private String rfcReceptor;
	private String uuid;
	private BigDecimal total;
	private String motivo;
	private String folioFiscalSustitucion;
	private String sello;
	private String certificado;
	private String llavePrivada;
	private String password;
	private Map<String, Object> adicionalData;

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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getFolioFiscalSustitucion() {
		return folioFiscalSustitucion;
	}

	public void setFolioFiscalSustitucion(String folioFiscalSustitucion) {
		this.folioFiscalSustitucion = folioFiscalSustitucion;
	}

	public String getSello() {
		return sello;
	}

	public void setSello(String sello) {
		this.sello = sello;
	}

	public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	public String getLlavePrivada() {
		return llavePrivada;
	}

	public void setLlavePrivada(String llavePrivada) {
		this.llavePrivada = llavePrivada;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String, Object> getAdicionalData() {
		return adicionalData;
	}

	public void setAdicionalData(Map<String, Object> adicionalData) {
		this.adicionalData = adicionalData;
	}
}