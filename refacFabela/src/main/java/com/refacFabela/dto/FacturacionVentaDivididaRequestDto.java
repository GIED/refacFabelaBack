package com.refacFabela.dto;

public class FacturacionVentaDivididaRequestDto {

	private Long nIdVenta;
	private String cveCfdi;

	public Long getnIdVenta() {
		return nIdVenta;
	}

	public void setnIdVenta(Long nIdVenta) {
		this.nIdVenta = nIdVenta;
	}

	public String getCveCfdi() {
		return cveCfdi;
	}

	public void setCveCfdi(String cveCfdi) {
		this.cveCfdi = cveCfdi;
	}
}
