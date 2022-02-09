package com.refacFabela.service;

import java.util.List;

import com.refacFabela.dto.AbonosDto;
import com.refacFabela.dto.ReporteAbonoVentaCreditoDto;
import com.refacFabela.dto.ReporteCotizacionDto;
import com.refacFabela.dto.ReporteVentaDto;
import com.refacFabela.model.TcCliente;

public interface ReporteService {
	
	 byte[] generaCotizacionPDF(ReporteCotizacionDto reporteCotizacion,  List<ReporteCotizacionDto> listaProducto); 
	 byte[] generaVentaPDF(ReporteVentaDto reporteVenta,  List<ReporteVentaDto> listaProducto);
	 byte[] generaAbonoVentaPDF(ReporteVentaDto reporteVenta,  List<AbonosDto> listaProducto, double totalAbonos);
	 byte[] generaAbonoVentaClientePDF(TcCliente cliente,  List<ReporteAbonoVentaCreditoDto> listaAbonoVenta, ReporteVentaDto reporteVenta);	 
	 byte[] generaVentaPedidoPDF(ReporteVentaDto reporteVenta,  List<ReporteVentaDto> listaProducto);

}
