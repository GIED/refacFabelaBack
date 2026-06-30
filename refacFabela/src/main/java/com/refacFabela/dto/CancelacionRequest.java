package com.refacFabela.dto;

import java.math.BigDecimal;
import java.util.Map;

public class CancelacionRequest {

	private Long razonSocialId;
	private String rfcEmisor;
	private String rfcReceptor;
	private String uuid;
	private BigDecimal total;
	private String motivo;
	private String folioFiscalSustitucion;
	private String sello;
	private Map<String, Object> metadata;

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

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}
}
