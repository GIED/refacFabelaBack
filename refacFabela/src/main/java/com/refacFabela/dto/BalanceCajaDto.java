package com.refacFabela.dto;

import java.util.Date;
import java.util.List;

import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TvReporteCajaFormaPago;
import com.refacFabela.model.TvReporteDetalleVenta;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwCaja;
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
	
	
	
	
	
	public BalanceCajaDto() {
		
		
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
				+ ", totalIngresoVenta=" + totalIngresoVenta + ", usuarioCaja=" + usuarioCaja
				+ ", tvReporteDetalleVenta=" + tvReporteDetalleVenta + ", fechaGeneraReporte=" + fechaGeneraReporte
				+ ", totalEntregadas=" + totalEntregadas + ", totalNoEntregadas=" + totalNoEntregadas
				+ ", totalEntregasParciales=" + totalEntregasParciales + ", tvReporteCajaFormaPago="
				+ tvReporteCajaFormaPago + "]";
	}
	
	
	
	

	
	

}
