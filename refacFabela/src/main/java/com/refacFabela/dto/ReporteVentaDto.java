package com.refacFabela.dto;

import java.io.Serializable;
import java.util.Date;

public class ReporteVentaDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nombreEmpresa;
	private String rfcEmpresa;
	private String nombreCliente;
	private String rfcCliente;
	private Date fecha;
	private Long folioVenta;
	private String claveSat;
	private Integer cantidad;
	private Long noIdentificacion;
	private String nombreProducto;
	private Double precioUnitario;
	private Double importe;
	private Double subTotal;
	private Double ivaTotal;
	private Double total;
	private Double anticipo;
	private String descripcionCatSat;
	private Double saldoFinal;
	private Long tipoPago;
	private String correo;
	private Double descuento;
	private Double abonos;
	private String condicionEntrega;
	private String ubicacion;
	private String noParte;
	private String nombreVendedor;
	
	
	
	
	
	
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public ReporteVentaDto() {
	}	
	
	public Long getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(Long tipoPago) {
		this.tipoPago = tipoPago;
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
	
	public Long getFolioVenta() {
		return folioVenta;
	}


	public void setFolioVenta(Long folioVenta) {
		this.folioVenta = folioVenta;
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

	public Double getAnticipo() {
		return anticipo;
	}

	public void setAnticipo(Double anticipo) {
		this.anticipo = anticipo;
	}


	public String getDescripcionCatSat() {
		return descripcionCatSat;
	}


	public void setDescripcionCatSat(String descripcionCatSat) {
		this.descripcionCatSat = descripcionCatSat;
	}


	public Double getSaldoFinal() {
		return saldoFinal;
	}


	public void setSaldoFinal(Double saldoFinal) {
		this.saldoFinal = saldoFinal;
	}

	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	public Double getAbonos() {
		return abonos;
	}

	public void setAbonos(Double abonos) {
		this.abonos = abonos;
	}

	public String getCondicionEntrega() {
		return condicionEntrega;
	}

	public void setCondicionEntrega(String condicionEntrega) {
		this.condicionEntrega = condicionEntrega;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getNoParte() {
		return noParte;
	}

	public void setNoParte(String noParte) {
		this.noParte = noParte;
	}

	public String getNombreVendedor() {
		return nombreVendedor;
	}

	public void setNombreVendedor(String nombreVendedor) {
		this.nombreVendedor = nombreVendedor;
	}
	
	
	
		

}
