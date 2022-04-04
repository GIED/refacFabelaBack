package com.refacFabela.model.factura;

import java.io.Serializable;

public class ConceptoXml implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String claveUnidad;
	private String claveProdServ;
	private String noIdentificacion;
	private String cantidad;
	private String unidad;
	private String descripcion;
	private String valorUnitario;
	private String importe;
	private String descuento;
	private String Base;
	private String impuesto;
	private String tipoFactor;
	private String tasaOCuota;
	private String importeImpuesto;
	
	
	
	public ConceptoXml() {
	
	}
	
	public String getClaveUnidad() {
		return claveUnidad;
	}
	public void setClaveUnidad(String claveUnidad) {
		this.claveUnidad = claveUnidad;
	}
	public String getClaveProdServ() {
		return claveProdServ;
	}
	public void setClaveProdServ(String claveProdServ) {
		this.claveProdServ = claveProdServ;
	}
	public String getNoIdentificacion() {
		return noIdentificacion;
	}
	public void setNoIdentificacion(String noIdentificacion) {
		this.noIdentificacion = noIdentificacion;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public String getUnidad() {
		return unidad;
	}
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(String valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getDescuento() {
		return descuento;
	}
	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}
	public String getBase() {
		return Base;
	}
	public void setBase(String base) {
		Base = base;
	}
	public String getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(String impuesto) {
		this.impuesto = impuesto;
	}
	public String getTipoFactor() {
		return tipoFactor;
	}
	public void setTipoFactor(String tipoFactor) {
		this.tipoFactor = tipoFactor;
	}
	public String getTasaOCuota() {
		return tasaOCuota;
	}
	public void setTasaOCuota(String tasaOCuota) {
		this.tasaOCuota = tasaOCuota;
	}
	public String getImporteImpuesto() {
		return importeImpuesto;
	}
	public void setImporteImpuesto(String importeImpuesto) {
		this.importeImpuesto = importeImpuesto;
	}
	
	

}
