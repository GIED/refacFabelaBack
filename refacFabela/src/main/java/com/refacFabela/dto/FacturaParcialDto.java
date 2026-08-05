package com.refacFabela.dto;

import java.math.BigDecimal;

public class FacturaParcialDto {

	private Integer parcial;
	private String uuid;
	private String folio;
	private String estado;
	private BigDecimal monto;
	private Long nIdFacturacion;

	public Integer getParcial() {
		return parcial;
	}

	public void setParcial(Integer parcial) {
		this.parcial = parcial;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Long getnIdFacturacion() {
		return nIdFacturacion;
	}

	public void setnIdFacturacion(Long nIdFacturacion) {
		this.nIdFacturacion = nIdFacturacion;
	}
}
