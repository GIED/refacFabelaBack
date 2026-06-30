package com.refacFabela.dto;

public class CancelacionFacturaDto {

	private Long nIdVenta;
	private String motivo;
	private String folioFiscalSustitucion;

	public Long getnIdVenta() {
		return nIdVenta;
	}

	public void setnIdVenta(Long nIdVenta) {
		this.nIdVenta = nIdVenta;
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
}