package com.refacFabela.dto;

import java.io.Serializable;
import java.math.BigDecimal;
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
	private BigDecimal precioUnitario;
	private BigDecimal importe;
	private BigDecimal subTotal;
	private BigDecimal ivaTotal;
	private BigDecimal total;
	private BigDecimal anticipo;
	private String descripcionCatSat;
	private BigDecimal saldoFinal;
	private Long tipoPago;
	private String correo;
	private BigDecimal descuento;
	private BigDecimal abonos;
	private String condicionEntrega;
	private String ubicacion;
	private String noParte;
	private String nombreVendedor;
	private Integer totalEntrega;
	private Long idPedido;
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
	public BigDecimal getAnticipo() {
		return anticipo;
	}
	public void setAnticipo(BigDecimal anticipo) {
		this.anticipo = anticipo;
	}
	public String getDescripcionCatSat() {
		return descripcionCatSat;
	}
	public void setDescripcionCatSat(String descripcionCatSat) {
		this.descripcionCatSat = descripcionCatSat;
	}
	public BigDecimal getSaldoFinal() {
		return saldoFinal;
	}
	public void setSaldoFinal(BigDecimal saldoFinal) {
		this.saldoFinal = saldoFinal;
	}
	public Long getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(Long tipoPago) {
		this.tipoPago = tipoPago;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public BigDecimal getDescuento() {
		return descuento;
	}
	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}
	public BigDecimal getAbonos() {
		return abonos;
	}
	public void setAbonos(BigDecimal abonos) {
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
	public Integer getTotalEntrega() {
		return totalEntrega;
	}
	public void setTotalEntrega(Integer totalEntrega) {
		this.totalEntrega = totalEntrega;
	}
	public Long getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}
	@Override
	public String toString() {
		return "ReporteVentaDto [nombreEmpresa=" + nombreEmpresa + ", rfcEmpresa=" + rfcEmpresa + ", nombreCliente="
				+ nombreCliente + ", rfcCliente=" + rfcCliente + ", fecha=" + fecha + ", folioVenta=" + folioVenta
				+ ", claveSat=" + claveSat + ", cantidad=" + cantidad + ", noIdentificacion=" + noIdentificacion
				+ ", nombreProducto=" + nombreProducto + ", precioUnitario=" + precioUnitario + ", importe=" + importe
				+ ", subTotal=" + subTotal + ", ivaTotal=" + ivaTotal + ", total=" + total + ", anticipo=" + anticipo
				+ ", descripcionCatSat=" + descripcionCatSat + ", saldoFinal=" + saldoFinal + ", tipoPago=" + tipoPago
				+ ", correo=" + correo + ", descuento=" + descuento + ", abonos=" + abonos + ", condicionEntrega="
				+ condicionEntrega + ", ubicacion=" + ubicacion + ", noParte=" + noParte + ", nombreVendedor="
				+ nombreVendedor + ", totalEntrega=" + totalEntrega + ", idPedido=" + idPedido + "]";
	}
	
	
	
	
	
	
	
	
		

}
