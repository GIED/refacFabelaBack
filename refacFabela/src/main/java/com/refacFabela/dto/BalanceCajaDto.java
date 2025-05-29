package com.refacFabela.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.refacFabela.model.TvReporteCajaFormaPago;
import com.refacFabela.model.TvReporteDetalleVenta;

public class BalanceCajaDto {
	
	public Long caja;
	public LocalDateTime fechaInicioCaja;
	public BigDecimal totalGeneralIngresos;
	public BigDecimal totalVentas;
	public BigDecimal totalIngresoAbonos;
	public BigDecimal totalIngresoVenta;
	public String usuarioCaja;
	public Integer noVentas;
	public Integer noAbonos;
	public List<TvReporteDetalleVenta> tvReporteDetalleVenta;
	public LocalDateTime fechaGeneraReporte;
	public Integer totalEntregadas;
	public Integer totalNoEntregadas;
	public Integer totalEntregasParciales;
	public List<TvReporteCajaFormaPago> tvReporteCajaFormaPago;
	public BigDecimal totalReintegro;
	public List<GastosDto> listaGastos;
	public BigDecimal totalGastos;
	public List<AbonoDto> listaAbonos;
	public List<CancelaVentaDto> listaCancelados;
	public BigDecimal totalCredito;
	public BigDecimal totalVenta;
	
	
	public BigDecimal efectivoContado;
	public BigDecimal chequeContado;
	public BigDecimal transferenciaContado;
	public BigDecimal tarjetaCreditoContado;
	public BigDecimal tarjetaDebitoContad;
	public BigDecimal CondonacionContado;
	
	// variables de crédito
	public BigDecimal efectivoAbono;
	public BigDecimal chequeAbono;
	public BigDecimal transferenciaAbono;
	public BigDecimal tarjetaCreditoAbono;
	public BigDecimal tarjetaDebitoAbon;
	public BigDecimal CondonacionAbono; 
	
	public BigDecimal totalCancelado;
	public BigDecimal saldosFavor;
	public BigDecimal totalDescuento;
	
	
	public BalanceCajaDto() {
		
		
	}
	
	
	

	



	




	

	





	public LocalDateTime getFechaInicioCaja() {
		return fechaInicioCaja;
	}





















	public void setFechaInicioCaja(LocalDateTime fechaInicioCaja) {
		this.fechaInicioCaja = fechaInicioCaja;
	}





















	public Long getCaja() {
		return caja;
	}





















	public void setCaja(Long caja) {
		this.caja = caja;
	}





















	



















	public BigDecimal getTotalGeneralIngresos() {
		return totalGeneralIngresos;
	}





















	public void setTotalGeneralIngresos(BigDecimal totalGeneralIngresos) {
		this.totalGeneralIngresos = totalGeneralIngresos;
	}





















	public BigDecimal getTotalVentas() {
		return totalVentas;
	}





















	public void setTotalVentas(BigDecimal totalVentas) {
		this.totalVentas = totalVentas;
	}





















	public BigDecimal getTotalIngresoAbonos() {
		return totalIngresoAbonos;
	}





















	public void setTotalIngresoAbonos(BigDecimal totalIngresoAbonos) {
		this.totalIngresoAbonos = totalIngresoAbonos;
	}





















	public BigDecimal getTotalIngresoVenta() {
		return totalIngresoVenta;
	}





















	public void setTotalIngresoVenta(BigDecimal totalIngresoVenta) {
		this.totalIngresoVenta = totalIngresoVenta;
	}





















	public String getUsuarioCaja() {
		return usuarioCaja;
	}





















	public void setUsuarioCaja(String usuarioCaja) {
		this.usuarioCaja = usuarioCaja;
	}





















	public Integer getNoVentas() {
		return noVentas;
	}





















	public void setNoVentas(Integer noVentas) {
		this.noVentas = noVentas;
	}





















	public Integer getNoAbonos() {
		return noAbonos;
	}





















	public void setNoAbonos(Integer noAbonos) {
		this.noAbonos = noAbonos;
	}





















	public List<TvReporteDetalleVenta> getTvReporteDetalleVenta() {
		return tvReporteDetalleVenta;
	}





















	public void setTvReporteDetalleVenta(List<TvReporteDetalleVenta> tvReporteDetalleVenta) {
		this.tvReporteDetalleVenta = tvReporteDetalleVenta;
	}





















	




















	public LocalDateTime getFechaGeneraReporte() {
		return fechaGeneraReporte;
	}





















	public void setFechaGeneraReporte(LocalDateTime fechaGeneraReporte) {
		this.fechaGeneraReporte = fechaGeneraReporte;
	}





















	public Integer getTotalEntregadas() {
		return totalEntregadas;
	}





















	public void setTotalEntregadas(Integer totalEntregadas) {
		this.totalEntregadas = totalEntregadas;
	}





















	public Integer getTotalNoEntregadas() {
		return totalNoEntregadas;
	}





















	public void setTotalNoEntregadas(Integer totalNoEntregadas) {
		this.totalNoEntregadas = totalNoEntregadas;
	}





















	public Integer getTotalEntregasParciales() {
		return totalEntregasParciales;
	}





















	public void setTotalEntregasParciales(Integer totalEntregasParciales) {
		this.totalEntregasParciales = totalEntregasParciales;
	}





















	public List<TvReporteCajaFormaPago> getTvReporteCajaFormaPago() {
		return tvReporteCajaFormaPago;
	}





















	public void setTvReporteCajaFormaPago(List<TvReporteCajaFormaPago> tvReporteCajaFormaPago) {
		this.tvReporteCajaFormaPago = tvReporteCajaFormaPago;
	}





















	public BigDecimal getTotalReintegro() {
		return totalReintegro;
	}





















	public void setTotalReintegro(BigDecimal totalReintegro) {
		this.totalReintegro = totalReintegro;
	}





















	public List<GastosDto> getListaGastos() {
		return listaGastos;
	}





















	public void setListaGastos(List<GastosDto> listaGastos) {
		this.listaGastos = listaGastos;
	}





















	public BigDecimal getTotalGastos() {
		return totalGastos;
	}





















	public void setTotalGastos(BigDecimal totalGastos) {
		this.totalGastos = totalGastos;
	}





















	public List<AbonoDto> getListaAbonos() {
		return listaAbonos;
	}





















	public void setListaAbonos(List<AbonoDto> listaAbonos) {
		this.listaAbonos = listaAbonos;
	}





















	public List<CancelaVentaDto> getListaCancelados() {
		return listaCancelados;
	}





















	public void setListaCancelados(List<CancelaVentaDto> listaCancelados) {
		this.listaCancelados = listaCancelados;
	}





















	public BigDecimal getTotalCredito() {
		return totalCredito;
	}





















	public void setTotalCredito(BigDecimal totalCredito) {
		this.totalCredito = totalCredito;
	}





















	public BigDecimal getTotalVenta() {
		return totalVenta;
	}





















	public void setTotalVenta(BigDecimal totalVenta) {
		this.totalVenta = totalVenta;
	}





















	public BigDecimal getEfectivoContado() {
		return efectivoContado;
	}





















	public void setEfectivoContado(BigDecimal efectivoContado) {
		this.efectivoContado = efectivoContado;
	}





















	public BigDecimal getChequeContado() {
		return chequeContado;
	}





















	public void setChequeContado(BigDecimal chequeContado) {
		this.chequeContado = chequeContado;
	}





















	public BigDecimal getTransferenciaContado() {
		return transferenciaContado;
	}





















	public void setTransferenciaContado(BigDecimal transferenciaContado) {
		this.transferenciaContado = transferenciaContado;
	}





















	public BigDecimal getTarjetaCreditoContado() {
		return tarjetaCreditoContado;
	}





















	public void setTarjetaCreditoContado(BigDecimal tarjetaCreditoContado) {
		this.tarjetaCreditoContado = tarjetaCreditoContado;
	}





















	public BigDecimal getTarjetaDebitoContad() {
		return tarjetaDebitoContad;
	}





















	public void setTarjetaDebitoContad(BigDecimal tarjetaDebitoContad) {
		this.tarjetaDebitoContad = tarjetaDebitoContad;
	}





















	public BigDecimal getCondonacionContado() {
		return CondonacionContado;
	}





















	public void setCondonacionContado(BigDecimal condonacionContado) {
		CondonacionContado = condonacionContado;
	}





















	public BigDecimal getEfectivoAbono() {
		return efectivoAbono;
	}





















	public void setEfectivoAbono(BigDecimal efectivoAbono) {
		this.efectivoAbono = efectivoAbono;
	}





















	public BigDecimal getChequeAbono() {
		return chequeAbono;
	}





















	public void setChequeAbono(BigDecimal chequeAbono) {
		this.chequeAbono = chequeAbono;
	}





















	public BigDecimal getTransferenciaAbono() {
		return transferenciaAbono;
	}





















	public void setTransferenciaAbono(BigDecimal transferenciaAbono) {
		this.transferenciaAbono = transferenciaAbono;
	}





















	public BigDecimal getTarjetaCreditoAbono() {
		return tarjetaCreditoAbono;
	}





















	public void setTarjetaCreditoAbono(BigDecimal tarjetaCreditoAbono) {
		this.tarjetaCreditoAbono = tarjetaCreditoAbono;
	}





















	public BigDecimal getTarjetaDebitoAbon() {
		return tarjetaDebitoAbon;
	}





















	public void setTarjetaDebitoAbon(BigDecimal tarjetaDebitoAbon) {
		this.tarjetaDebitoAbon = tarjetaDebitoAbon;
	}





















	public BigDecimal getCondonacionAbono() {
		return CondonacionAbono;
	}





















	public void setCondonacionAbono(BigDecimal condonacionAbono) {
		CondonacionAbono = condonacionAbono;
	}





















	public BigDecimal getTotalCancelado() {
		return totalCancelado;
	}





















	public void setTotalCancelado(BigDecimal totalCancelado) {
		this.totalCancelado = totalCancelado;
	}





















	public BigDecimal getSaldosFavor() {
		return saldosFavor;
	}





















	public void setSaldosFavor(BigDecimal saldosFavor) {
		this.saldosFavor = saldosFavor;
	}





















	public BigDecimal getTotalDescuento() {
		return totalDescuento;
	}





















	public void setTotalDescuento(BigDecimal totalDescuento) {
		this.totalDescuento = totalDescuento;
	}





















	@Override
	public String toString() {
		return "BalanceCajaDto [caja=" + caja + ", fechaInicioCaja=" + fechaInicioCaja + ", totalGeneralIngresos="
				+ totalGeneralIngresos + ", totalVentas=" + totalVentas + ", totalIngresoAbonos=" + totalIngresoAbonos
				+ ", totalIngresoVenta=" + totalIngresoVenta + ", usuarioCaja=" + usuarioCaja + ", noVentas=" + noVentas
				+ ", noAbonos=" + noAbonos + ", tvReporteDetalleVenta=" + tvReporteDetalleVenta
				+ ", fechaGeneraReporte=" + fechaGeneraReporte + ", totalEntregadas=" + totalEntregadas
				+ ", totalNoEntregadas=" + totalNoEntregadas + ", totalEntregasParciales=" + totalEntregasParciales
				+ ", tvReporteCajaFormaPago=" + tvReporteCajaFormaPago + ", totalReintegro=" + totalReintegro
				+ ", listaGastos=" + listaGastos + ", totalGastos=" + totalGastos + ", listaAbonos=" + listaAbonos
				+ ", listaCancelados=" + listaCancelados + ", totalCredito=" + totalCredito + ", totalVenta="
				+ totalVenta + ", efectivoContado=" + efectivoContado + ", chequeContado=" + chequeContado
				+ ", transferenciaContado=" + transferenciaContado + ", tarjetaCreditoContado=" + tarjetaCreditoContado
				+ ", tarjetaDebitoContad=" + tarjetaDebitoContad + ", CondonacionContado=" + CondonacionContado
				+ ", efectivoAbono=" + efectivoAbono + ", chequeAbono=" + chequeAbono + ", transferenciaAbono="
				+ transferenciaAbono + ", tarjetaCreditoAbono=" + tarjetaCreditoAbono + ", tarjetaDebitoAbon="
				+ tarjetaDebitoAbon + ", CondonacionAbono=" + CondonacionAbono + "]";
	}



	
	
	

	
	

}
