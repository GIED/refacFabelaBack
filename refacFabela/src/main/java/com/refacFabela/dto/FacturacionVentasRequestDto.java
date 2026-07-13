package com.refacFabela.dto;

import java.util.ArrayList;
import java.util.List;

public class FacturacionVentasRequestDto {

	private List<Long> nIdsVenta = new ArrayList<Long>();
	private String cveCfdi;

	public List<Long> getnIdsVenta() {
		return nIdsVenta;
	}

	public void setnIdsVenta(List<Long> nIdsVenta) {
		this.nIdsVenta = nIdsVenta;
	}

	public String getCveCfdi() {
		return cveCfdi;
	}

	public void setCveCfdi(String cveCfdi) {
		this.cveCfdi = cveCfdi;
	}

}