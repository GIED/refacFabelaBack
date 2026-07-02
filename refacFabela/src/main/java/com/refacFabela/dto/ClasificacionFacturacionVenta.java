package com.refacFabela.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.refacFabela.enums.ClasificacionFacturacionPago;

public class ClasificacionFacturacionVenta {

	private Long idVenta;
	private BigDecimal totalVenta;
	private BigDecimal totalPagado;
	private BigDecimal saldoPendiente;
	private Integer numeroPagosRegistrados;
	private Integer numeroFormasPagoSatDistintas;
	private Boolean credito;
	private Boolean totalmenteLiquidada;
	private ClasificacionFacturacionPago clasificacion;
	private String metodoPagoFiscal;
	private String formaPagoFiscal;
	private String formaPagoDescripcionFiscal;
	private List<String> clavesFormaPagoSat;
	private Map<String, BigDecimal> montosPorClaveSat;

	public Long getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(Long idVenta) {
		this.idVenta = idVenta;
	}

	public BigDecimal getTotalVenta() {
		return totalVenta;
	}

	public void setTotalVenta(BigDecimal totalVenta) {
		this.totalVenta = totalVenta;
	}

	public BigDecimal getTotalPagado() {
		return totalPagado;
	}

	public void setTotalPagado(BigDecimal totalPagado) {
		this.totalPagado = totalPagado;
	}

	public BigDecimal getSaldoPendiente() {
		return saldoPendiente;
	}

	public void setSaldoPendiente(BigDecimal saldoPendiente) {
		this.saldoPendiente = saldoPendiente;
	}

	public Integer getNumeroPagosRegistrados() {
		return numeroPagosRegistrados;
	}

	public void setNumeroPagosRegistrados(Integer numeroPagosRegistrados) {
		this.numeroPagosRegistrados = numeroPagosRegistrados;
	}

	public Integer getNumeroFormasPagoSatDistintas() {
		return numeroFormasPagoSatDistintas;
	}

	public void setNumeroFormasPagoSatDistintas(Integer numeroFormasPagoSatDistintas) {
		this.numeroFormasPagoSatDistintas = numeroFormasPagoSatDistintas;
	}

	public Boolean getCredito() {
		return credito;
	}

	public void setCredito(Boolean credito) {
		this.credito = credito;
	}

	public Boolean getTotalmenteLiquidada() {
		return totalmenteLiquidada;
	}

	public void setTotalmenteLiquidada(Boolean totalmenteLiquidada) {
		this.totalmenteLiquidada = totalmenteLiquidada;
	}

	public ClasificacionFacturacionPago getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(ClasificacionFacturacionPago clasificacion) {
		this.clasificacion = clasificacion;
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

	public String getFormaPagoDescripcionFiscal() {
		return formaPagoDescripcionFiscal;
	}

	public void setFormaPagoDescripcionFiscal(String formaPagoDescripcionFiscal) {
		this.formaPagoDescripcionFiscal = formaPagoDescripcionFiscal;
	}

	public List<String> getClavesFormaPagoSat() {
		return clavesFormaPagoSat;
	}

	public void setClavesFormaPagoSat(List<String> clavesFormaPagoSat) {
		this.clavesFormaPagoSat = clavesFormaPagoSat;
	}

	public Map<String, BigDecimal> getMontosPorClaveSat() {
		return montosPorClaveSat;
	}

	public void setMontosPorClaveSat(Map<String, BigDecimal> montosPorClaveSat) {
		this.montosPorClaveSat = montosPorClaveSat;
	}
}