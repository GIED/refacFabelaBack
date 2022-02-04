package com.refacFabela.dto;

import java.util.Date;
import java.util.List;

import com.refacFabela.model.TwCotizaciones;

public class VentaDto {
	
	   private Long idCliente;
	   private Long idUsuario;
	   private String sFolioVenta;
	   private Integer idTipoVenta;
	   private Integer tipoPago;
	   private Date fechaIniCredito;
	   private Date fechaFinCredito;
	   private Double anticipo;
	   private List<TvStockProductoDto> listaValidada;
	   private TwCotizaciones twCotizacion;
	   
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getsFolioVenta() {
		return sFolioVenta;
	}
	public void setsFolioVenta(String sFolioVenta) {
		this.sFolioVenta = sFolioVenta;
	}
	public Integer getIdTipoVenta() {
		return idTipoVenta;
	}
	public void setIdTipoVenta(Integer idTipoVenta) {
		this.idTipoVenta = idTipoVenta;
	}
	public Integer getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(Integer tipoPago) {
		this.tipoPago = tipoPago;
	}
	public Date getFechaIniCredito() {
		return fechaIniCredito;
	}
	public void setFechaIniCredito(Date fechaIniCredito) {
		this.fechaIniCredito = fechaIniCredito;
	}
	public Date getFechaFinCredito() {
		return fechaFinCredito;
	}
	public void setFechaFinCredito(Date fechaFinCredito) {
		this.fechaFinCredito = fechaFinCredito;
	}
	public List<TvStockProductoDto> getListaValidada() {
		return listaValidada;
	}
	public void setListaValidada(List<TvStockProductoDto> listaValidada) {
		this.listaValidada = listaValidada;
	}
	
	public TwCotizaciones getTwCotizacion() {
		return twCotizacion;
	}
	public void setTwCotizacion(TwCotizaciones twCotizacion) {
		this.twCotizacion = twCotizacion;
	}
	
	
	public Double getAnticipo() {
		return anticipo;
	}
	public void setAnticipo(Double anticipo) {
		this.anticipo = anticipo;
	}
	@Override
	public String toString() {
		return "VentaDto [idCliente=" + idCliente + ", idUsuario=" + idUsuario + ", sFolioVenta=" + sFolioVenta
				+ ", idTipoVenta=" + idTipoVenta + ", tipoPago=" + tipoPago + ", fechaIniCredito=" + fechaIniCredito
				+ ", fechaFinCredito=" + fechaFinCredito + ", anticipo=" + anticipo + ", listaValidada=" + listaValidada
				+ ", twCotizacion=" + twCotizacion + "]";
	}
	
	
	
	
	
	
	
	
	   
	   

}
