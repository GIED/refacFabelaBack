package com.refacFabela.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PagoAplicacionResultadoDto {

	private Long nIdPagoCliente;
	private BigDecimal importeTotal;
	private BigDecimal importeAplicado;
	private BigDecimal importeDisponible;
	private String estatusPago;
	private List<PagoAplicacionLineaDto> aplicaciones = new ArrayList<PagoAplicacionLineaDto>();

	public Long getnIdPagoCliente() {
		return nIdPagoCliente;
	}

	public void setnIdPagoCliente(Long nIdPagoCliente) {
		this.nIdPagoCliente = nIdPagoCliente;
	}

	public BigDecimal getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(BigDecimal importeTotal) {
		this.importeTotal = importeTotal;
	}

	public BigDecimal getImporteAplicado() {
		return importeAplicado;
	}

	public void setImporteAplicado(BigDecimal importeAplicado) {
		this.importeAplicado = importeAplicado;
	}

	public BigDecimal getImporteDisponible() {
		return importeDisponible;
	}

	public void setImporteDisponible(BigDecimal importeDisponible) {
		this.importeDisponible = importeDisponible;
	}

	public String getEstatusPago() {
		return estatusPago;
	}

	public void setEstatusPago(String estatusPago) {
		this.estatusPago = estatusPago;
	}

	public List<PagoAplicacionLineaDto> getAplicaciones() {
		return aplicaciones;
	}

	public void setAplicaciones(List<PagoAplicacionLineaDto> aplicaciones) {
		this.aplicaciones = aplicaciones;
	}
}