package com.refacFabela.service;

import java.util.List;

import com.refacFabela.dto.ReporteCotizacionDto;
import com.refacFabela.dto.ReporteVentaDto;

public interface ReporteService {
	
	 byte[] generaCotizacionPDF(ReporteCotizacionDto reporteCotizacion,  List<ReporteCotizacionDto> listaProducto); 
	 byte[] generaVentaPDF(ReporteVentaDto reporteVenta,  List<ReporteVentaDto> listaProducto);
	 byte[] generaVentaPedidoPDF(ReporteVentaDto reporteVenta,  List<ReporteVentaDto> listaProducto);

}
