package com.refacFabela.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagoAplicacionLineaDto {

	private Long nId;
	private Long nIdPagoCliente;
	private Long nIdVenta;
	private Long nIdFacturacion;
	private BigDecimal montoAplicado;
	private BigDecimal saldoAnterior;
	private BigDecimal saldoInsoluto;
	private Integer parcialidad;
	private String estatus;
	private Integer ordenAplicacion;
	private String origenRegistro;
	private LocalDateTime fechaPago;
	private LocalDateTime fechaAplicacion;
	private String folioVenta;
	private LocalDateTime fechaVenta;
	private String uuidFactura;
	private String estadoFactura;
	private String metodoPagoFiscal;
	private String formaPagoFiscal;
	private String estadoComplemento;
	private String formaPagoSat;
	private String descripcionFormaPago;
	private String referenciaPago;
	private String observacionesPago;

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Long getnIdPagoCliente() {
		return nIdPagoCliente;
	}

	public void setnIdPagoCliente(Long nIdPagoCliente) {
		this.nIdPagoCliente = nIdPagoCliente;
	}

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

	public BigDecimal getMontoAplicado() {
		return montoAplicado;
	}

	public void setMontoAplicado(BigDecimal montoAplicado) {
		this.montoAplicado = montoAplicado;
	}

	public BigDecimal getSaldoAnterior() {
		return saldoAnterior;
	}

	public void setSaldoAnterior(BigDecimal saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}

	public BigDecimal getSaldoInsoluto() {
		return saldoInsoluto;
	}

	public void setSaldoInsoluto(BigDecimal saldoInsoluto) {
		this.saldoInsoluto = saldoInsoluto;
	}

	public Integer getParcialidad() {
		return parcialidad;
	}

	public void setParcialidad(Integer parcialidad) {
		this.parcialidad = parcialidad;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Integer getOrdenAplicacion() {
		return ordenAplicacion;
	}

	public void setOrdenAplicacion(Integer ordenAplicacion) {
		this.ordenAplicacion = ordenAplicacion;
	}

	public String getOrigenRegistro() {
		return origenRegistro;
	}

	public void setOrigenRegistro(String origenRegistro) {
		this.origenRegistro = origenRegistro;
	}

	public LocalDateTime getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDateTime fechaPago) {
		this.fechaPago = fechaPago;
	}

	public LocalDateTime getFechaAplicacion() {
		return fechaAplicacion;
	}

	public void setFechaAplicacion(LocalDateTime fechaAplicacion) {
		this.fechaAplicacion = fechaAplicacion;
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

	public String getUuidFactura() {
		return uuidFactura;
	}

	public void setUuidFactura(String uuidFactura) {
		this.uuidFactura = uuidFactura;
	}

	public String getEstadoFactura() {
		return estadoFactura;
	}

	public void setEstadoFactura(String estadoFactura) {
		this.estadoFactura = estadoFactura;
	}

	public String getMetodoPagoFiscal() {
		return metodoPagoFiscal;
	}

	public void setMetodoPagoFiscal(String metodoPagoFiscal) {
		this.metodoPagoFiscal = metodoPagoFiscal;
	}

	public String getFormaPagoFiscal() {
		return formaPagoFiscal;
	}

	public void setFormaPagoFiscal(String formaPagoFiscal) {
		this.formaPagoFiscal = formaPagoFiscal;
	}

	public String getEstadoComplemento() {
		return estadoComplemento;
	}

	public void setEstadoComplemento(String estadoComplemento) {
		this.estadoComplemento = estadoComplemento;
	}

	public String getFormaPagoSat() {
		return formaPagoSat;
	}

	public void setFormaPagoSat(String formaPagoSat) {
		this.formaPagoSat = formaPagoSat;
	}

	public String getDescripcionFormaPago() {
		return descripcionFormaPago;
	}

	public void setDescripcionFormaPago(String descripcionFormaPago) {
		this.descripcionFormaPago = descripcionFormaPago;
	}

	public String getReferenciaPago() {
		return referenciaPago;
	}

	public void setReferenciaPago(String referenciaPago) {
		this.referenciaPago = referenciaPago;
	}

	public String getObservacionesPago() {
		return observacionesPago;
	}

	public void setObservacionesPago(String observacionesPago) {
		this.observacionesPago = observacionesPago;
	}
}