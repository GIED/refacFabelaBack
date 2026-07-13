package com.refacFabela.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PagoClienteDetalleDto {

	private Long nId;
	private Long nIdCliente;
	private Long nIdDatoFactura;
	private LocalDateTime fechaRegistro;
	private LocalDateTime fechaPago;
	private BigDecimal importeTotal;
	private BigDecimal importeAplicado;
	private BigDecimal importeDisponible;
	private String moneda;
	private Long nIdFormaPago;
	private String formaPagoSat;
	private String descripcionFormaPago;
	private String referencia;
	private String numeroAutorizacion;
	private String folioOperacion;
	private String claveRastreo;
	private String bancoOrigen;
	private String cuentaOrigen;
	private String ultimos4CuentaOrigen;
	private String bancoDestino;
	private String cuentaDestino;
	private String ultimos4CuentaDestino;
	private String titularCuenta;
	private String terminal;
	private String numeroVoucher;
	private String ultimos4Tarjeta;
	private String tipoTarjeta;
	private String redTarjeta;
	private String comprobanteUrl;
	private String observaciones;
	private Long nIdUsuarioRegistro;
	private Long nIdCaja;
	private Long nIdCorteCaja;
	private String estatus;
	private String estadoRepCanonico;
	private String uuidRepCanonico;
	private Boolean conciliado;
	private LocalDateTime fechaConciliacion;
	private List<PagoAplicacionLineaDto> aplicaciones = new ArrayList<PagoAplicacionLineaDto>();

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Long getnIdCliente() {
		return nIdCliente;
	}

	public void setnIdCliente(Long nIdCliente) {
		this.nIdCliente = nIdCliente;
	}

	public Long getnIdDatoFactura() {
		return nIdDatoFactura;
	}

	public void setnIdDatoFactura(Long nIdDatoFactura) {
		this.nIdDatoFactura = nIdDatoFactura;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public LocalDateTime getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDateTime fechaPago) {
		this.fechaPago = fechaPago;
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

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public Long getnIdFormaPago() {
		return nIdFormaPago;
	}

	public void setnIdFormaPago(Long nIdFormaPago) {
		this.nIdFormaPago = nIdFormaPago;
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

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getNumeroAutorizacion() {
		return numeroAutorizacion;
	}

	public void setNumeroAutorizacion(String numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}

	public String getFolioOperacion() {
		return folioOperacion;
	}

	public void setFolioOperacion(String folioOperacion) {
		this.folioOperacion = folioOperacion;
	}

	public String getClaveRastreo() {
		return claveRastreo;
	}

	public void setClaveRastreo(String claveRastreo) {
		this.claveRastreo = claveRastreo;
	}

	public String getBancoOrigen() {
		return bancoOrigen;
	}

	public void setBancoOrigen(String bancoOrigen) {
		this.bancoOrigen = bancoOrigen;
	}

	public String getCuentaOrigen() {
		return cuentaOrigen;
	}

	public void setCuentaOrigen(String cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}

	public String getUltimos4CuentaOrigen() {
		return ultimos4CuentaOrigen;
	}

	public void setUltimos4CuentaOrigen(String ultimos4CuentaOrigen) {
		this.ultimos4CuentaOrigen = ultimos4CuentaOrigen;
	}

	public String getBancoDestino() {
		return bancoDestino;
	}

	public void setBancoDestino(String bancoDestino) {
		this.bancoDestino = bancoDestino;
	}

	public String getCuentaDestino() {
		return cuentaDestino;
	}

	public void setCuentaDestino(String cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}

	public String getUltimos4CuentaDestino() {
		return ultimos4CuentaDestino;
	}

	public void setUltimos4CuentaDestino(String ultimos4CuentaDestino) {
		this.ultimos4CuentaDestino = ultimos4CuentaDestino;
	}

	public String getTitularCuenta() {
		return titularCuenta;
	}

	public void setTitularCuenta(String titularCuenta) {
		this.titularCuenta = titularCuenta;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getNumeroVoucher() {
		return numeroVoucher;
	}

	public void setNumeroVoucher(String numeroVoucher) {
		this.numeroVoucher = numeroVoucher;
	}

	public String getUltimos4Tarjeta() {
		return ultimos4Tarjeta;
	}

	public void setUltimos4Tarjeta(String ultimos4Tarjeta) {
		this.ultimos4Tarjeta = ultimos4Tarjeta;
	}

	public String getTipoTarjeta() {
		return tipoTarjeta;
	}

	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public String getRedTarjeta() {
		return redTarjeta;
	}

	public void setRedTarjeta(String redTarjeta) {
		this.redTarjeta = redTarjeta;
	}

	public String getComprobanteUrl() {
		return comprobanteUrl;
	}

	public void setComprobanteUrl(String comprobanteUrl) {
		this.comprobanteUrl = comprobanteUrl;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Long getnIdUsuarioRegistro() {
		return nIdUsuarioRegistro;
	}

	public void setnIdUsuarioRegistro(Long nIdUsuarioRegistro) {
		this.nIdUsuarioRegistro = nIdUsuarioRegistro;
	}

	public Long getnIdCaja() {
		return nIdCaja;
	}

	public void setnIdCaja(Long nIdCaja) {
		this.nIdCaja = nIdCaja;
	}

	public Long getnIdCorteCaja() {
		return nIdCorteCaja;
	}

	public void setnIdCorteCaja(Long nIdCorteCaja) {
		this.nIdCorteCaja = nIdCorteCaja;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getEstadoRepCanonico() {
		return estadoRepCanonico;
	}

	public void setEstadoRepCanonico(String estadoRepCanonico) {
		this.estadoRepCanonico = estadoRepCanonico;
	}

	public String getUuidRepCanonico() {
		return uuidRepCanonico;
	}

	public void setUuidRepCanonico(String uuidRepCanonico) {
		this.uuidRepCanonico = uuidRepCanonico;
	}

	public Boolean getConciliado() {
		return conciliado;
	}

	public void setConciliado(Boolean conciliado) {
		this.conciliado = conciliado;
	}

	public LocalDateTime getFechaConciliacion() {
		return fechaConciliacion;
	}

	public void setFechaConciliacion(LocalDateTime fechaConciliacion) {
		this.fechaConciliacion = fechaConciliacion;
	}

	public List<PagoAplicacionLineaDto> getAplicaciones() {
		return aplicaciones;
	}

	public void setAplicaciones(List<PagoAplicacionLineaDto> aplicaciones) {
		this.aplicaciones = aplicaciones;
	}
}