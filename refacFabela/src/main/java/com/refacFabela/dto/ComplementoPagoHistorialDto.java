package com.refacFabela.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ComplementoPagoHistorialDto {

	private Long nId;
	private Long nIdVenta;
	private Long nIdFacturacion;
	private String uuidFacturaIngreso;
	private String uuidComplementoPago;
	private String origenPago;
	private Long nIdPagoOrigen;
	private Integer parcialidad;
	private String formaPagoSat;
	private String descripcionFormaPago;
	private BigDecimal montoPagado;
	private BigDecimal saldoAnterior;
	private BigDecimal saldoInsoluto;
	private LocalDateTime fechaPago;
	private String proveedor;
	private String estado;
	private Integer estatus;
	private String codigoError;
	private String errorPac;
	private String correlationId;
	private LocalDateTime fechaRegistro;

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
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

	public String getUuidFacturaIngreso() {
		return uuidFacturaIngreso;
	}

	public void setUuidFacturaIngreso(String uuidFacturaIngreso) {
		this.uuidFacturaIngreso = uuidFacturaIngreso;
	}

	public String getUuidComplementoPago() {
		return uuidComplementoPago;
	}

	public void setUuidComplementoPago(String uuidComplementoPago) {
		this.uuidComplementoPago = uuidComplementoPago;
	}

	public String getOrigenPago() {
		return origenPago;
	}

	public void setOrigenPago(String origenPago) {
		this.origenPago = origenPago;
	}

	public Long getnIdPagoOrigen() {
		return nIdPagoOrigen;
	}

	public void setnIdPagoOrigen(Long nIdPagoOrigen) {
		this.nIdPagoOrigen = nIdPagoOrigen;
	}

	public Integer getParcialidad() {
		return parcialidad;
	}

	public void setParcialidad(Integer parcialidad) {
		this.parcialidad = parcialidad;
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

	public BigDecimal getMontoPagado() {
		return montoPagado;
	}

	public void setMontoPagado(BigDecimal montoPagado) {
		this.montoPagado = montoPagado;
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

	public LocalDateTime getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDateTime fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public String getErrorPac() {
		return errorPac;
	}

	public void setErrorPac(String errorPac) {
		this.errorPac = errorPac;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
}
