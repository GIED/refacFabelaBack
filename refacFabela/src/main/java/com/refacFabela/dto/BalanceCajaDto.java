package com.refacFabela.dto;

import java.util.Date;
import java.util.List;

import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TvReporteCajaFormaPago;
import com.refacFabela.model.TvReporteDetalleVenta;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwCaja;
import com.refacFabela.model.TwGasto;
import com.refacFabela.model.TwVenta;

public class BalanceCajaDto {
	
	public Long caja;
	public String fechaInicioCaja;
	public Double totalGeneralIngresos;
	public Double totalVentas;
	public Double totalIngresoAbonos;
	public Double totalIngresoVenta;
	public String usuarioCaja;
	public Integer noVentas;
	public Integer noAbonos;
	public List<TvReporteDetalleVenta> tvReporteDetalleVenta;
	public String fechaGeneraReporte;
	public Integer totalEntregadas;
	public Integer totalNoEntregadas;
	public Integer totalEntregasParciales;
	public List<TvReporteCajaFormaPago> tvReporteCajaFormaPago;
	public Double totalReintegro;
	public List<GastosDto> listaGastos;
	public Double totalGastos;
	public List<AbonoDto> listaAbonos;
	public List<CancelaVentaDto> listaCancelados;
	public Double totalCredito;
	public Double totalVenta;
	
	
	public Double efectivoContado;
	public Double chequeContado;
	public Double transferenciaContado;
	public Double tarjetaCreditoContado;
	public Double tarjetaDebitoContad;
	public Double CondonacionContado;
	
	// variables de crédito
	public Double efectivoAbono;
	public Double chequeAbono;
	public Double transferenciaAbono;
	public Double tarjetaCreditoAbono;
	public Double tarjetaDebitoAbon;
	public Double CondonacionAbono; 
	
	public Double totalCancelado;
	public Double saldosFavor;
	public Double totalDescuento;
	
	
	public BalanceCajaDto() {
		
		
	}
	
	
	

	public Double getTotalDescuento() {
		return totalDescuento;
	}




	public void setTotalDescuento(Double totalDescuento) {
		this.totalDescuento = totalDescuento;
	}




	public Double getTotalCancelado() {
		return totalCancelado;
	}




	public void setTotalCancelado(Double totalCancelado) {
		this.totalCancelado = totalCancelado;
	}




	public Double getSaldosFavor() {
		return saldosFavor;
	}




	public void setSaldosFavor(Double saldosFavor) {
		this.saldosFavor = saldosFavor;
	}




	public Double getTarjetaDebitoContad() {
		return tarjetaDebitoContad;
	}


	public void setTarjetaDebitoContad(Double tarjetaDebitoContad) {
		this.tarjetaDebitoContad = tarjetaDebitoContad;
	}


	public Double getTarjetaDebitoAbon() {
		return tarjetaDebitoAbon;
	}



	public void setTarjetaDebitoAbon(Double tarjetaDebitoAbon) {
		this.tarjetaDebitoAbon = tarjetaDebitoAbon;
	}



	public Double getEfectivoContado() {
		return efectivoContado;
	}

	public void setEfectivoContado(Double efectivoContado) {
		this.efectivoContado = efectivoContado;
	}



	public Double getChequeContado() {
		return chequeContado;
	}





	public void setChequeContado(Double chequeContado) {
		this.chequeContado = chequeContado;
	}





	public Double getTransferenciaContado() {
		return transferenciaContado;
	}





	public void setTransferenciaContado(Double transferenciaContado) {
		this.transferenciaContado = transferenciaContado;
	}





	public Double getTarjetaCreditoContado() {
		return tarjetaCreditoContado;
	}





	public void setTarjetaCreditoContado(Double tarjetaCreditoContado) {
		this.tarjetaCreditoContado = tarjetaCreditoContado;
	}





	




	public Double getCondonacionContado() {
		return CondonacionContado;
	}





	public void setCondonacionContado(Double condonacionContado) {
		CondonacionContado = condonacionContado;
	}





	public Double getEfectivoAbono() {
		return efectivoAbono;
	}





	public void setEfectivoAbono(Double efectivoAbono) {
		this.efectivoAbono = efectivoAbono;
	}





	public Double getChequeAbono() {
		return chequeAbono;
	}





	public void setChequeAbono(Double chequeAbono) {
		this.chequeAbono = chequeAbono;
	}





	public Double getTransferenciaAbono() {
		return transferenciaAbono;
	}





	public void setTransferenciaAbono(Double transferenciaAbono) {
		this.transferenciaAbono = transferenciaAbono;
	}





	public Double getTarjetaCreditoAbono() {
		return tarjetaCreditoAbono;
	}





	public void setTarjetaCreditoAbono(Double tarjetaCreditoAbono) {
		this.tarjetaCreditoAbono = tarjetaCreditoAbono;
	}









	





	public Double getCondonacionAbono() {
		return CondonacionAbono;
	}





	public void setCondonacionAbono(Double condonacionAbono) {
		CondonacionAbono = condonacionAbono;
	}





	public Double getTotalCredito() {
		return totalCredito;
	}



	public void setTotalCredito(Double totalCredito) {
		this.totalCredito = totalCredito;
	}



	public Double getTotalVenta() {
		return totalVenta;
	}



	public void setTotalVenta(Double totalVenta) {
		this.totalVenta = totalVenta;
	}



	public List<CancelaVentaDto> getListaCancelados() {
		return listaCancelados;
	}



	public void setListaCancelados(List<CancelaVentaDto> listaCancelados) {
		this.listaCancelados = listaCancelados;
	}



	public List<GastosDto> getListaGastos() {
		return listaGastos;
	}



	public void setListaGastos(List<GastosDto> listaGastos) {
		this.listaGastos = listaGastos;
	}



	public Double getTotalGastos() {
		return totalGastos;
	}





	public List<AbonoDto> getListaAbonos() {
		return listaAbonos;
	}


	public void setListaAbonos(List<AbonoDto> listaAbonos) {
		this.listaAbonos = listaAbonos;
	}


	public void setTotalGastos(Double totalGastos) {
		this.totalGastos = totalGastos;
	}





	public Long getCaja() {
		return caja;
	}





	public void setCaja(Long caja) {
		this.caja = caja;
	}





	



	public Double getTotalGeneralIngresos() {
		return totalGeneralIngresos;
	}





	public void setTotalGeneralIngresos(Double totalGeneralIngresos) {
		this.totalGeneralIngresos = totalGeneralIngresos;
	}





	public Double getTotalVentas() {
		return totalVentas;
	}





	public void setTotalVentas(Double totalVentas) {
		this.totalVentas = totalVentas;
	}





	public Double getTotalIngresoAbonos() {
		return totalIngresoAbonos;
	}





	public void setTotalIngresoAbonos(Double totalIngresoAbonos) {
		this.totalIngresoAbonos = totalIngresoAbonos;
	}





	public Double getTotalIngresoVenta() {
		return totalIngresoVenta;
	}





	public void setTotalIngresoVenta(Double totalIngresoVenta) {
		this.totalIngresoVenta = totalIngresoVenta;
	}





	public String getUsuarioCaja() {
		return usuarioCaja;
	}





	public void setUsuarioCaja(String usuarioCaja) {
		this.usuarioCaja = usuarioCaja;
	}





	public List<TvReporteDetalleVenta> getTvReporteDetalleVenta() {
		return tvReporteDetalleVenta;
	}





	public void setTvReporteDetalleVenta(List<TvReporteDetalleVenta> tvReporteDetalleVenta) {
		this.tvReporteDetalleVenta = tvReporteDetalleVenta;
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





	public String getFechaInicioCaja() {
		return fechaInicioCaja;
	}





	public void setFechaInicioCaja(String fechaInicioCaja) {
		this.fechaInicioCaja = fechaInicioCaja;
	}





	public String getFechaGeneraReporte() {
		return fechaGeneraReporte;
	}





	public void setFechaGeneraReporte(String fechaGeneraReporte) {
		this.fechaGeneraReporte = fechaGeneraReporte;
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


	public Double getTotalReintegro() {
		return totalReintegro;
	}





	public void setTotalReintegro(Double totalReintegro) {
		this.totalReintegro = totalReintegro;
	}
	
	
	
	

	
	

}
