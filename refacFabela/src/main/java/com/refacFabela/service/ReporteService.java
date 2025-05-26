package com.refacFabela.service;

import java.math.BigDecimal;
import java.util.List;

import com.refacFabela.dto.AbonosDto;
import com.refacFabela.dto.BalanceCajaDto;
import com.refacFabela.dto.PedidoProductoDto;
import com.refacFabela.dto.ProductoBodegaDto;
import com.refacFabela.dto.ReporteAbonoVentaCreditoDto;
import com.refacFabela.dto.ReporteCotizacionDto;
import com.refacFabela.dto.ReporteVentaDto;
import com.refacFabela.dto.TwSaldoUtilizadoDto;
import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TwPedido;
import com.refacFabela.model.TwProductobodega;

public interface ReporteService {
	
	 byte[] generaCotizacionPDF(ReporteCotizacionDto reporteCotizacion,  List<ReporteCotizacionDto> listaProducto); 
	 byte[] generaVentaPDF(ReporteVentaDto reporteVenta,  List<ReporteVentaDto> listaProducto, BigDecimal totalAbono);
	 byte[] generaSaldoFavorPDF(ReporteVentaDto reporteVenta,  List<ReporteVentaDto> listaProducto, BigDecimal totalAbono,  BigDecimal saldoFinalSaldo, BigDecimal totalSaldoUsado, List<TwSaldoUtilizadoDto> listaTwSaldoUtilizadoDto);
	 byte[] generaVentaAlmacenPDF(ReporteVentaDto reporteVenta,  List<ReporteVentaDto> listaProducto, BigDecimal totalAbono);
	 byte[] generaAbonoVentaPDF(ReporteVentaDto reporteVenta,  List<AbonosDto> listaProducto, BigDecimal totalAbonos);
	 byte[] generaAbonoVentaClientePDF(TcCliente cliente,  List<ReporteAbonoVentaCreditoDto> listaAbonoVenta, ReporteVentaDto reporteVenta);	 
	 byte[] generaVentaPedidoPDF(ReporteVentaDto reporteVenta,  List<ReporteVentaDto> listaProducto);	 
	 byte[] generaPedidoPDF(TwPedido twPedido,  List<PedidoProductoDto> listaPedidoProducto);
	 byte[] generarReporteCajaPDF(BalanceCajaDto balanceCajaDto);	 
	 byte[] generarReporteInventarioPDF(List<ProductoBodegaDto> listaProductoBodeda);

}
