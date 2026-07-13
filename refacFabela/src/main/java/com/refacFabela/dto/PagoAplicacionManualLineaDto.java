package com.refacFabela.dto;

import java.math.BigDecimal;

public class PagoAplicacionManualLineaDto {

	private Long nIdVenta;
	private BigDecimal montoAplicar;

	public Long getnIdVenta() {
		return nIdVenta;
	}

	public void setnIdVenta(Long nIdVenta) {
		this.nIdVenta = nIdVenta;
	}

	public BigDecimal getMontoAplicar() {
		return montoAplicar;
	}

	public void setMontoAplicar(BigDecimal montoAplicar) {
		this.montoAplicar = montoAplicar;
	}
}