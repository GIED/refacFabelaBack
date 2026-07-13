package com.refacFabela.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FacturaCreditoPendienteDto {

	private Long nIdVenta;
	private Long nIdFacturacion;
	private String folioVenta;
	private LocalDateTime fechaVenta;
	private BigDecimal totalVenta;
	private BigDecimal totalAplicado;
	private BigDecimal saldoPendiente;
	private Integer parcialidadActual;
	private Boolean facturada;
	private Boolean requiereFacturacion;
	private String estadoFiscal;

	public Long getnIdVenta() {
		return nIdVenta;
	}

	public void setnIdVenta(Long nIdVenta) {
		this.nIdVenta = nIdVenta;
	}

	public Long getnIdFacturacion() {
		return nIdFacturacion;
	}

	public void setnIdFacturacion(Long nIdFacturacion) {
		this.nIdFacturacion = nIdFacturacion;
	}

	public String getFolioVenta() {
		return folioVenta;
	}

	public void setFolioVenta(String folioVenta) {
		this.folioVenta = folioVenta;
	}

	public LocalDateTime getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(LocalDateTime fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public BigDecimal getTotalVenta() {
		return totalVenta;
	}

	public void setTotalVenta(BigDecimal totalVenta) {
		this.totalVenta = totalVenta;
	}

	public BigDecimal getTotalAplicado() {
		return totalAplicado;
	}

	public void setTotalAplicado(BigDecimal totalAplicado) {
		this.totalAplicado = totalAplicado;
	}

	public BigDecimal getSaldoPendiente() {
		return saldoPendiente;
	}

	public void setSaldoPendiente(BigDecimal saldoPendiente) {
		this.saldoPendiente = saldoPendiente;
	}

	public Integer getParcialidadActual() {
		return parcialidadActual;
	}

	public void setParcialidadActual(Integer parcialidadActual) {
		this.parcialidadActual = parcialidadActual;
	}

	public Boolean getFacturada() {
		return facturada;
	}

	public void setFacturada(Boolean facturada) {
		this.facturada = facturada;
	}

	public Boolean getRequiereFacturacion() {
		return requiereFacturacion;
	}

	public void setRequiereFacturacion(Boolean requiereFacturacion) {
		this.requiereFacturacion = requiereFacturacion;
	}

	public String getEstadoFiscal() {
		return estadoFiscal;
	}

	public void setEstadoFiscal(String estadoFiscal) {
		this.estadoFiscal = estadoFiscal;
	}
}