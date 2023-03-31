package com.refacFabela.model.factura;

import java.io.Serializable;

public class CabeceraXml implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String version;
	private String serie;
	private String folio;
	private String fecha;
	private String formaPago;
	private String condicionesPago;
	private String subTotal;
	private String moneda;
	private String total;
	private String tipoComprobante;
	private String metodoPago;
	private String lugarExpedicion;
	private String noCertificado;
	private String certificado;
	private String sello;
	private String nombreEmisor;
	private String rfcEmisor;
	private String regimenFiscal;
	private String nombreReceptor;
	private String rfcReceptor;
	private String usoCFDI;
	private String emailReceptor;
	private String regimenFiscalReceptor;
	private String domicilioFiscalReceptor;
	private String exportacion;
	
	
	public CabeceraXml() {
		
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public String getCondicionesPago() {
		return condicionesPago;
	}
	public void setCondicionesPago(String condicionesPago) {
		this.condicionesPago = condicionesPago;
	}
	public String getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getTipoComprobante() {
		return tipoComprobante;
	}
	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}
	public String getMetodoPago() {
		return metodoPago;
	}
	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}
	public String getLugarExpedicion() {
		return lugarExpedicion;
	}
	public void setLugarExpedicion(String lugarExpedicion) {
		this.lugarExpedicion = lugarExpedicion;
	}
	public String getNoCertificado() {
		return noCertificado;
	}
	public void setNoCertificado(String noCertificado) {
		this.noCertificado = noCertificado;
	}
	public String getCertificado() {
		return certificado;
	}
	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}
	public String getSello() {
		return sello;
	}
	public void setSello(String sello) {
		this.sello = sello;
	}
	public String getNombreEmisor() {
		return nombreEmisor;
	}
	public void setNombreEmisor(String nombreEmisor) {
		this.nombreEmisor = nombreEmisor;
	}
	public String getRfcEmisor() {
		return rfcEmisor;
	}
	public void setRfcEmisor(String rfcEmisor) {
		this.rfcEmisor = rfcEmisor;
	}
	public String getRegimenFiscal() {
		return regimenFiscal;
	}
	public void setRegimenFiscal(String regimenFiscal) {
		this.regimenFiscal = regimenFiscal;
	}
	public String getNombreReceptor() {
		return nombreReceptor;
	}
	public void setNombreReceptor(String nombreReceptor) {
		this.nombreReceptor = nombreReceptor;
	}
	public String getRfcReceptor() {
		return rfcReceptor;
	}
	public void setRfcReceptor(String rfcReceptor) {
		this.rfcReceptor = rfcReceptor;
	}
	public String getUsoCFDI() {
		return usoCFDI;
	}
	public void setUsoCFDI(String usoCFDI) {
		this.usoCFDI = usoCFDI;
	}
	public String getEmailReceptor() {
		return emailReceptor;
	}
	public void setEmailReceptor(String emailReceptor) {
		this.emailReceptor = emailReceptor;
	}

	public String getRegimenFiscalReceptor() {
		return regimenFiscalReceptor;
	}

	public void setRegimenFiscalReceptor(String regimenFiscalReceptor) {
		this.regimenFiscalReceptor = regimenFiscalReceptor;
	}

	public String getDomicilioFiscalReceptor() {
		return domicilioFiscalReceptor;
	}

	public void setDomicilioFiscalReceptor(String domicilioFiscalReceptor) {
		this.domicilioFiscalReceptor = domicilioFiscalReceptor;
	}
	
	

	public String getExportacion() {
		return exportacion;
	}

	public void setExportacion(String exportacion) {
		this.exportacion = exportacion;
	}

	@Override
	public String toString() {
		return "CabeceraXml [version=" + version + ", serie=" + serie + ", folio=" + folio + ", fecha=" + fecha
				+ ", formaPago=" + formaPago + ", condicionesPago=" + condicionesPago + ", subTotal=" + subTotal
				+ ", moneda=" + moneda + ", total=" + total + ", tipoComprobante=" + tipoComprobante + ", metodoPago="
				+ metodoPago + ", lugarExpedicion=" + lugarExpedicion + ", noCertificado=" + noCertificado
				+ ", certificado=" + certificado + ", sello=" + sello + ", nombreEmisor=" + nombreEmisor
				+ ", rfcEmisor=" + rfcEmisor + ", regimenFiscal=" + regimenFiscal + ", nombreReceptor=" + nombreReceptor
				+ ", rfcReceptor=" + rfcReceptor + ", usoCFDI=" + usoCFDI + ", emailReceptor=" + emailReceptor
				+ ", regimenFiscalReceptor=" + regimenFiscalReceptor + ", domicilioFiscalReceptor="
				+ domicilioFiscalReceptor + "]";
	}
	
	
	
	

}
