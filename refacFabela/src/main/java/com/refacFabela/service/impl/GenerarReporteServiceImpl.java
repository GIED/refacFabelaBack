package com.refacFabela.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.dto.ReporteCotizacionDto;
import com.refacFabela.dto.ReporteVentaDto;
import com.refacFabela.model.TwCotizacionesProducto;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.repository.CotizacionProductoRepository;
import com.refacFabela.repository.VentasProductoRepository;
import com.refacFabela.service.GeneraReporteService;
import com.refacFabela.service.ReporteService;

@Service
public class GenerarReporteServiceImpl implements GeneraReporteService {

	@Autowired
	private CotizacionProductoRepository cotizacionProductoRepository;

	@Autowired
	private VentasProductoRepository ventasProductoRepository;

	@Autowired
	private ReporteService reporteService;

	@Override
	public byte[] getCotizacionPDF(Long nIdCotizacion) {

		List<TwCotizacionesProducto> listaProductos = cotizacionProductoRepository.findBynIdCotizacion(nIdCotizacion);

		ReporteCotizacionDto reporteCotizacion = new ReporteCotizacionDto();

		reporteCotizacion.setNombreEmpresa("Refacciones Fabela");
		reporteCotizacion.setRfcEmpresa("FAMJ810312FY6");
		reporteCotizacion.setNombreCliente(listaProductos.get(0).getTwCotizacione().getTcCliente().getsRazonSocial());
		reporteCotizacion.setRfcCliente(listaProductos.get(0).getTwCotizacione().getTcCliente().getsRfc());
		reporteCotizacion.setFolioCotizacion(listaProductos.get(0).getTwCotizacione().getnId());
		reporteCotizacion.setFecha(listaProductos.get(0).getTwCotizacione().getdFecha());

		List<ReporteCotizacionDto> listaProducto = new ArrayList<ReporteCotizacionDto>();

		double subtotal = 0.0;
		double iva = 0.0;
	

		for (TwCotizacionesProducto twCotizacionesProducto : listaProductos) {

			ReporteCotizacionDto reporte = new ReporteCotizacionDto();

			reporte.setCantidad(twCotizacionesProducto.getnCantidad());
			reporte.setNoIdentificacion(twCotizacionesProducto.getTcProducto().getnId());
			reporte.setNombreProducto(twCotizacionesProducto.getTcProducto().getsDescripcion());
			reporte.setClaveSat(twCotizacionesProducto.getTcProducto().getTcClavesat().getsClavesat());
			reporte.setPrecioUnitario(twCotizacionesProducto.getnPrecioUnitario());
			reporte.setImporte(twCotizacionesProducto.getnPrecioPartida());
			reporte.setDescripcionCatSat(twCotizacionesProducto.getTcProducto().getTcClavesat().getsDescripcion());

			listaProducto.add(reporte);

			subtotal = subtotal + twCotizacionesProducto.getnPrecioPartida();
			iva = iva + twCotizacionesProducto.getnIvaPartida();

		}

		reporteCotizacion.setSubTotal(subtotal);
		reporteCotizacion.setIvaTotal(iva);
		reporteCotizacion.setTotal(subtotal + iva);

		return reporteService.generaCotizacionPDF(reporteCotizacion, listaProducto);
	}

	@Override
	public byte[] getVentaPDF(Long nIdVenta) {

		List<TwVentasProducto> listaProductos = ventasProductoRepository.findBynIdVenta(nIdVenta);

		ReporteVentaDto reporteVenta = new ReporteVentaDto();

		reporteVenta.setNombreEmpresa("Refaccionaria Fabela");
		reporteVenta.setRfcEmpresa("TES030201001");
		reporteVenta.setNombreCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRazonSocial());
		reporteVenta.setRfcCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRfc());
		reporteVenta.setFolioVenta(listaProductos.get(0).getTwVenta().getnId());
		reporteVenta.setFecha(listaProductos.get(0).getTwVenta().getdFechaVenta());
		reporteVenta.setTipoPago(listaProductos.get(0).getTwVenta().getnTipoPago());
		

		List<ReporteVentaDto> listaProducto = new ArrayList<ReporteVentaDto>();

		double subtotal = 0.0;
		double iva = 0.0;
		

		for (TwVentasProducto twVentaProducto : listaProductos) {

			ReporteVentaDto reporte = new ReporteVentaDto();

			reporte.setCantidad(twVentaProducto.getnCantidad());
			reporte.setNoIdentificacion(twVentaProducto.getTcProducto().getnId());
			reporte.setNombreProducto(twVentaProducto.getTcProducto().getsDescripcion());
			reporte.setClaveSat(twVentaProducto.getTcProducto().getTcClavesat().getsClavesat());
			reporte.setPrecioUnitario(twVentaProducto.getnPrecioUnitario());
			reporte.setImporte(twVentaProducto.getnPrecioPartida());
			reporte.setDescripcionCatSat(twVentaProducto.getTcProducto().getTcClavesat().getsDescripcion());

			listaProducto.add(reporte);

			subtotal = subtotal + twVentaProducto.getnPrecioPartida();
			iva = iva + twVentaProducto.getnIvaPartida();

		}

		reporteVenta.setSubTotal(subtotal);
		reporteVenta.setIvaTotal(iva);
		reporteVenta.setTotal(subtotal + iva);

		return reporteService.generaVentaPDF(reporteVenta, listaProducto);
	}
	@Override
	public byte[] getVentaPedidoPDF(Long nIdVentaPedido) {
		
		List<TwVentasProducto> listaProductos = ventasProductoRepository.findBynIdVenta(nIdVentaPedido);
		
		ReporteVentaDto reporteVenta = new ReporteVentaDto();
		
		reporteVenta.setNombreEmpresa("Refaccionaria Fabela");
		reporteVenta.setRfcEmpresa("TES030201001");
		reporteVenta.setNombreCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRazonSocial());
		reporteVenta.setRfcCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRfc());
		reporteVenta.setFolioVenta(listaProductos.get(0).getTwVenta().getnId());
		reporteVenta.setFecha(listaProductos.get(0).getTwVenta().getdFechaVenta());
		reporteVenta.setAnticipo(listaProductos.get(0).getTwVenta().getAnticipo());
		
		
		List<ReporteVentaDto> listaProducto = new ArrayList<ReporteVentaDto>();
		
		double subtotal = 0.0;
		double iva = 0.0;
		
		
		for (TwVentasProducto twVentaProducto : listaProductos) {
			
			ReporteVentaDto reporte = new ReporteVentaDto();
			
			reporte.setCantidad(twVentaProducto.getnCantidad());
			reporte.setNoIdentificacion(twVentaProducto.getTcProducto().getnId());
			reporte.setNombreProducto(twVentaProducto.getTcProducto().getsDescripcion());
			reporte.setClaveSat(twVentaProducto.getTcProducto().getTcClavesat().getsClavesat());
			reporte.setPrecioUnitario(twVentaProducto.getnPrecioUnitario());
			reporte.setImporte(twVentaProducto.getnPrecioPartida());
			
			listaProducto.add(reporte);
			
			subtotal = subtotal + twVentaProducto.getnPrecioPartida();
			iva = iva + twVentaProducto.getnIvaPartida();
			
		}
		
		reporteVenta.setSubTotal(subtotal);
		reporteVenta.setIvaTotal(iva);
		reporteVenta.setTotal(subtotal + iva);
		
		return reporteService.generaVentaPedidoPDF(reporteVenta, listaProducto);
	}

}
