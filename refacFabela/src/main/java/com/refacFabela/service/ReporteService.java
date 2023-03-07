package com.refacFabela.service;

import java.util.List;

import com.refacFabela.dto.AbonosDto;
import com.refacFabela.dto.BalanceCajaDto;
import com.refacFabela.dto.PedidoProductoDto;
import com.refacFabela.dto.ReporteAbonoVentaCreditoDto;
import com.refacFabela.dto.ReporteCotizacionDto;
import com.refacFabela.dto.ReporteVentaDto;
import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TwPedido;

public interface ReporteService {
	
	 byte[] generaCotizacionPDF(ReporteCotizacionDto reporteCotizacion,  List<ReporteCotizacionDto> listaProducto); 
	 byte[] generaVentaPDF(ReporteVentaDto reporteVenta,  List<ReporteVentaDto> listaProducto, double totalAbono);
	 byte[] generaVentaAlmacenPDF(ReporteVentaDto reporteVenta,  List<ReporteVentaDto> listaProducto, double totalAbono);
	 byte[] generaAbonoVentaPDF(ReporteVentaDto reporteVenta,  List<AbonosDto> listaProducto, double totalAbonos);
	 byte[] generaAbonoVentaClientePDF(TcCliente cliente,  List<ReporteAbonoVentaCreditoDto> listaAbonoVenta, ReporteVentaDto reporteVenta);	 
	 byte[] generaVentaPedidoPDF(ReporteVentaDto reporteVenta,  List<ReporteVentaDto> listaProducto);	 
	 byte[] generaPedidoPDF(TwPedido twPedido,  List<PedidoProductoDto> listaPedidoProducto);
	 byte[] generarReporteCajaPDF(BalanceCajaDto balanceCajaDto);

}
