package com.refacFabela.service;

import com.refacFabela.enums.TipoDoc;

public interface GeneraReporteService {
	
	public byte[] getCotizacionPDF(Long nIdCotizacion);
	public byte[] getVentaPDF(Long nIdVenta);
	public byte[] getVentaAlmacenPDF(Long nIdVenta);
	public byte[] getVentaPedidoPDF(Long nIdVentaPedido);
	public byte[] getAbonoVentaIdPDF(Long nIdVenta);
	public byte[] getAbonoVentaIdClientePDF(Long nIdCliente);
	public byte[] getPedidoIdPDF(Long nIdPedido);
	public byte[] getReporteCaja(Long nIdCaja);
	public byte[] getReporteInventario(Long nIBodega, Long nIdNivel, Long nIdAnaquel);
	public byte[] getDocumento(Long nIdCaja, TipoDoc TipoDoc);

}
