package com.refacFabela.service;

public interface GeneraReporteService {
	
	public byte[] getCotizacionPDF(Long nIdCotizacion);
	public byte[] getVentaPDF(Long nIdVenta);
	public byte[] getVentaPedidoPDF(Long nIdVentaPedido);
	public byte[] getAbonoVentaIdPDF(Long nIdVenta);
	public byte[] getAbonoVentaIdClientePDF(Long nIdCliente);
	public byte[] getPedidoIdPDF(Long nIdPedido);
	public byte[] getReporteCaja(Long nIdCaja);

}
