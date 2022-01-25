package com.refacFabela.dto;

import java.io.Serializable;
import java.util.Date;

public class ReporteCotizacionDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nombreEmpresa;
	private String rfcEmpresa;
	private String nombreCliente;
	private String rfcCliente;
	private Date fecha;
	private Long folioCotizacion;
	private String claveSat;
	private Integer cantidad;
	private Long noIdentificacion;
	private String nombreProducto;
	private Double precioUnitario;
	private Double importe;
	private Double subTotal;
	private Double ivaTotal;
	private Double total;
	private String descripcionCatSat;
	
	
	
	
	
	
	public ReporteCotizacionDto() {
	}
	
	public ReporteCotizacionDto(String claveSat, int cantidad, Long noIdentificacion, String nombreProducto,
			Double precioUnitario, Double importe) {
		
		this.cantidad = cantidad;
		this.claveSat = claveSat;
		this.noIdentificacion = noIdentificacion;
		this.nombreProducto = nombreProducto;
		this.precioUnitario = precioUnitario;
		this.importe = importe;
	}
	public String getNombreEmpresa() {
		return nombreEmpresa;
	}
	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}
	public String getRfcEmpresa() {
		return rfcEmpresa;
	}
	public void setRfcEmpresa(String rfcEmpresa) {
		this.rfcEmpresa = rfcEmpresa;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getRfcCliente() {
		return rfcCliente;
	}
	public void setRfcCliente(String rfcCliente) {
		this.rfcCliente = rfcCliente;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Long getFolioCotizacion() {
		return folioCotizacion;
	}
	public void setFolioCotizacion(Long folioCotizacion) {
		this.folioCotizacion = folioCotizacion;
	}
	public String getClaveSat() {
		return claveSat;
	}
	public void setClaveSat(String claveSat) {
		this.claveSat = claveSat;
	}
	
	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Long getNoIdentificacion() {
		return noIdentificacion;
	}
	public void setNoIdentificacion(Long noIdentificacion) {
		this.noIdentificacion = noIdentificacion;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public Double getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public Double getImporte() {
		return importe;
	}
	public void setImporte(Double importe) {
		this.importe = importe;
	}
	public Double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}
	public Double getIvaTotal() {
		return ivaTotal;
	}
	public void setIvaTotal(Double ivaTotal) {
		this.ivaTotal = ivaTotal;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}

	public String getDescripcionCatSat() {
		return descripcionCatSat;
	}

	public void setDescripcionCatSat(String descripcionCatSat) {
		this.descripcionCatSat = descripcionCatSat;
	}
		

}
