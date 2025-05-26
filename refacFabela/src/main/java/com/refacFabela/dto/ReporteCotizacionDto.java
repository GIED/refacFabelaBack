package com.refacFabela.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReporteCotizacionDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nombreEmpresa;
	private String rfcEmpresa;
	private String nombreCliente;
	private String rfcCliente;
	private LocalDateTime fecha;
	private Long folioCotizacion;
	private String claveSat;
	private Integer cantidad;
	private Long noIdentificacion;
	private String nombreProducto;
	private BigDecimal precioUnitario;
	private BigDecimal importe;
	private BigDecimal subTotal;
	private BigDecimal ivaTotal;
	private BigDecimal total;
	private String descripcionCatSat;
	private String correo;
	private String condicionEntrega;
	private String nombreVendedor;
	private String noParte;
	
	
	
	
	
	
	
	public ReporteCotizacionDto() {
	}
	
	public ReporteCotizacionDto(String claveSat, int cantidad, Long noIdentificacion, String nombreProducto,
			BigDecimal precioUnitario, BigDecimal importe) {
		
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
	
	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
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
	

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getIvaTotal() {
		return ivaTotal;
	}

	public void setIvaTotal(BigDecimal ivaTotal) {
		this.ivaTotal = ivaTotal;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getDescripcionCatSat() {
		return descripcionCatSat;
	}

	public void setDescripcionCatSat(String descripcionCatSat) {
		this.descripcionCatSat = descripcionCatSat;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getCondicionEntrega() {
		return condicionEntrega;
	}

	public void setCondicionEntrega(String condicionEntrega) {
		this.condicionEntrega = condicionEntrega;
	}

	public String getNombreVendedor() {
		return nombreVendedor;
	}

	public void setNombreVendedor(String nombreVendedor) {
		this.nombreVendedor = nombreVendedor;
	}

	public String getNoParte() {
		return noParte;
	}

	public void setNoParte(String noParte) {
		this.noParte = noParte;
	}
	
	
	
	
		

}
