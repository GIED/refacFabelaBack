package com.refacFabela.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "tw_facturacion_complemento_pago")
public class TwFacturacionComplementoPago implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "n_id_venta")
	private Long nIdVenta;

	@Column(name = "n_id_facturacion")
	private Long nIdFacturacion;

	@Column(name = "s_uuid_factura_ingreso")
	private String sUuidFacturaIngreso;

	@Column(name = "s_uuid_complemento_pago")
	private String sUuidComplementoPago;

	@Column(name = "s_origen_pago")
	private String sOrigenPago;

	@Column(name = "n_id_pago_origen")
	private Long nIdPagoOrigen;

	@Column(name = "n_parcialidad")
	private Integer nParcialidad;

	@Column(name = "s_forma_pago_sat")
	private String sFormaPagoSat;

	@Column(name = "s_descripcion_forma_pago")
	private String sDescripcionFormaPago;

	@Column(name = "n_monto_pagado")
	private BigDecimal nMontoPagado;

	@Column(name = "n_saldo_anterior")
	private BigDecimal nSaldoAnterior;

	@Column(name = "n_saldo_insoluto")
	private BigDecimal nSaldoInsoluto;

	@Column(name = "d_fecha_pago")
	private LocalDateTime dFechaPago;

	@Column(name = "s_proveedor")
	private String sProveedor;

	@Column(name = "s_estado")
	private String sEstado;

	@Column(name = "n_estatus")
	private Integer nEstatus;

	@Lob
	@Column(name = "s_xml_timbrado", columnDefinition = "LONGTEXT")
	private String sXmlTimbrado;

	@Column(name = "s_codigo_error")
	private String sCodigoError;

	@Column(name = "s_error_pac")
	private String sErrorPac;

	@Column(name = "s_correlation_id")
	private String sCorrelationId;

	@Column(name = "d_fecha_registro")
	private LocalDateTime dFechaRegistro;

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

	public String getsUuidFacturaIngreso() {
		return sUuidFacturaIngreso;
	}

	public void setsUuidFacturaIngreso(String sUuidFacturaIngreso) {
		this.sUuidFacturaIngreso = sUuidFacturaIngreso;
	}

	public String getsUuidComplementoPago() {
		return sUuidComplementoPago;
	}

	public void setsUuidComplementoPago(String sUuidComplementoPago) {
		this.sUuidComplementoPago = sUuidComplementoPago;
	}

	public String getsOrigenPago() {
		return sOrigenPago;
	}

	public void setsOrigenPago(String sOrigenPago) {
		this.sOrigenPago = sOrigenPago;
	}

	public Long getnIdPagoOrigen() {
		return nIdPagoOrigen;
	}

	public void setnIdPagoOrigen(Long nIdPagoOrigen) {
		this.nIdPagoOrigen = nIdPagoOrigen;
	}

	public Integer getnParcialidad() {
		return nParcialidad;
	}

	public void setnParcialidad(Integer nParcialidad) {
		this.nParcialidad = nParcialidad;
	}

	public String getsFormaPagoSat() {
		return sFormaPagoSat;
	}

	public void setsFormaPagoSat(String sFormaPagoSat) {
		this.sFormaPagoSat = sFormaPagoSat;
	}

	public String getsDescripcionFormaPago() {
		return sDescripcionFormaPago;
	}

	public void setsDescripcionFormaPago(String sDescripcionFormaPago) {
		this.sDescripcionFormaPago = sDescripcionFormaPago;
	}

	public BigDecimal getnMontoPagado() {
		return nMontoPagado;
	}

	public void setnMontoPagado(BigDecimal nMontoPagado) {
		this.nMontoPagado = nMontoPagado;
	}

	public BigDecimal getnSaldoAnterior() {
		return nSaldoAnterior;
	}

	public void setnSaldoAnterior(BigDecimal nSaldoAnterior) {
		this.nSaldoAnterior = nSaldoAnterior;
	}

	public BigDecimal getnSaldoInsoluto() {
		return nSaldoInsoluto;
	}

	public void setnSaldoInsoluto(BigDecimal nSaldoInsoluto) {
		this.nSaldoInsoluto = nSaldoInsoluto;
	}

	public LocalDateTime getdFechaPago() {
		return dFechaPago;
	}

	public void setdFechaPago(LocalDateTime dFechaPago) {
		this.dFechaPago = dFechaPago;
	}

	public String getsProveedor() {
		return sProveedor;
	}

	public void setsProveedor(String sProveedor) {
		this.sProveedor = sProveedor;
	}

	public String getsEstado() {
		return sEstado;
	}

	public void setsEstado(String sEstado) {
		this.sEstado = sEstado;
	}

	public Integer getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(Integer nEstatus) {
		this.nEstatus = nEstatus;
	}

	public String getsXmlTimbrado() {
		return sXmlTimbrado;
	}

	public void setsXmlTimbrado(String sXmlTimbrado) {
		this.sXmlTimbrado = sXmlTimbrado;
	}

	public String getsCodigoError() {
		return sCodigoError;
	}

	public void setsCodigoError(String sCodigoError) {
		this.sCodigoError = sCodigoError;
	}

	public String getsErrorPac() {
		return sErrorPac;
	}

	public void setsErrorPac(String sErrorPac) {
		this.sErrorPac = sErrorPac;
	}

	public String getsCorrelationId() {
		return sCorrelationId;
	}

	public void setsCorrelationId(String sCorrelationId) {
		this.sCorrelationId = sCorrelationId;
	}

	public LocalDateTime getdFechaRegistro() {
		return dFechaRegistro;
	}

	public void setdFechaRegistro(LocalDateTime dFechaRegistro) {
		this.dFechaRegistro = dFechaRegistro;
	}
}