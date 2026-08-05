package com.refacFabela.dto;

import java.math.BigDecimal;
import java.util.List;

public class ResultadoFacturacionVentaDto {

	private boolean success;
	private String mensaje;
	private String avisoCorreo;
	private String clasificacionFiscal;
	private String metodoPagoFiscal;
	private String formaPagoFiscal;
	private String uuidFacturaIngreso;
	private String uuidComplementoPago;
	private String estadoFacturacion;
	private String estadoComplemento;
	private String codigoError;
	private String mensajeError;
	private Integer totalFacturasParciales;
	private BigDecimal montoTotalFacturado;
	private List<FacturaParcialDto> facturasParciales;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getAvisoCorreo() {
		return avisoCorreo;
	}

	public void setAvisoCorreo(String avisoCorreo) {
		this.avisoCorreo = avisoCorreo;
	}

	public String getClasificacionFiscal() {
		return clasificacionFiscal;
	}

	public void setClasificacionFiscal(String clasificacionFiscal) {
		this.clasificacionFiscal = clasificacionFiscal;
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

	public String getEstadoFacturacion() {
		return estadoFacturacion;
	}

	public void setEstadoFacturacion(String estadoFacturacion) {
		this.estadoFacturacion = estadoFacturacion;
	}

	public String getEstadoComplemento() {
		return estadoComplemento;
	}

	public void setEstadoComplemento(String estadoComplemento) {
		this.estadoComplemento = estadoComplemento;
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public Integer getTotalFacturasParciales() {
		return totalFacturasParciales;
	}

	public void setTotalFacturasParciales(Integer totalFacturasParciales) {
		this.totalFacturasParciales = totalFacturasParciales;
	}

	public BigDecimal getMontoTotalFacturado() {
		return montoTotalFacturado;
	}

	public void setMontoTotalFacturado(BigDecimal montoTotalFacturado) {
		this.montoTotalFacturado = montoTotalFacturado;
	}

	public List<FacturaParcialDto> getFacturasParciales() {
		return facturasParciales;
	}

	public void setFacturasParciales(List<FacturaParcialDto> facturasParciales) {
		this.facturasParciales = facturasParciales;
	}
}