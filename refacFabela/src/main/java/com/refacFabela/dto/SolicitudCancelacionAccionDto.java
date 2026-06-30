package com.refacFabela.dto;

public class SolicitudCancelacionAccionDto {

	private Long nIdDatoFactura;
	private String uuid;

	public Long getnIdDatoFactura() {
		return nIdDatoFactura;
	}

	public void setnIdDatoFactura(Long nIdDatoFactura) {
		this.nIdDatoFactura = nIdDatoFactura;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}