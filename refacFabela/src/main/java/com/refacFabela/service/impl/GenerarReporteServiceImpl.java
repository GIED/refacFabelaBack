package com.refacFabela.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import com.refacFabela.dto.AbonosDto;
import com.refacFabela.dto.ReporteAbonoVentaCreditoDto;
import com.refacFabela.dto.ReporteCotizacionDto;
import com.refacFabela.dto.ReporteVentaDto;
import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TcUsuario;
import com.refacFabela.model.TvVentaDetalle;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwCotizacionesProducto;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.repository.AbonoVentaIdRepository;
import com.refacFabela.repository.ClientesRepository;
import com.refacFabela.repository.CotizacionProductoRepository;
import com.refacFabela.repository.TvVentaDetalleRepository;
import com.refacFabela.repository.UsuariosRepository;
import com.refacFabela.repository.VentasProductoRepository;
import com.refacFabela.service.GeneraReporteService;
import com.refacFabela.service.ReporteService;
import com.refacFabela.utils.utils;

@Service
public class GenerarReporteServiceImpl implements GeneraReporteService {

	@Autowired
	private CotizacionProductoRepository cotizacionProductoRepository;

	@Autowired
	private VentasProductoRepository ventasProductoRepository;

	@Autowired
	private ReporteService reporteService;
	
	@Autowired
	private AbonoVentaIdRepository abonoVentaIdRepository;
	
	@Autowired
	private ClientesRepository clientesRepository;
	
	@Autowired
	private TvVentaDetalleRepository tvVentaDetalleRepository;
	
	

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
		reporteCotizacion.setCorreo(listaProductos.get(0).getTwCotizacione().getTcCliente().getsCorreo());

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

	


	@Override
	public byte[] getAbonoVentaIdPDF(Long nIdVenta) {

		List<TwVentasProducto> listaProductos = ventasProductoRepository.findBynIdVenta(nIdVenta);

		ReporteVentaDto reporteVenta = new ReporteVentaDto();

		reporteVenta.setNombreEmpresa("Refaccionaria Fabela");
		reporteVenta.setRfcEmpresa("TES030201001");
		reporteVenta.setNombreCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRazonSocial());
		reporteVenta.setRfcCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRfc());
		reporteVenta.setFolioVenta(listaProductos.get(0).getTwVenta().getnId());
		reporteVenta.setFecha(listaProductos.get(0).getTwVenta().getdFechaVenta());
		reporteVenta.setTipoPago(listaProductos.get(0).getTwVenta().getnTipoPago());
		reporteVenta.setCorreo(listaProductos.get(0).getTwVenta().getTcCliente().getsCorreo());
		
		

		List<ReporteVentaDto> listaProducto = new ArrayList<ReporteVentaDto>();

		double subtotal = 0.0;
		double iva = 0.0;
		

		for (TwVentasProducto twVentaProducto : listaProductos) {
		

			subtotal = subtotal + twVentaProducto.getnPrecioPartida();
			iva = iva + twVentaProducto.getnIvaPartida();

		}

		reporteVenta.setSubTotal(subtotal);
		reporteVenta.setIvaTotal(iva);
		reporteVenta.setTotal(subtotal + iva);
		
		List<TwAbono> listaAbonos =abonoVentaIdRepository.findBynIdVenta(nIdVenta);
		
		List<AbonosDto> listaAbonosDto=new ArrayList<AbonosDto>();
		
		utils util = new utils();
		
		double abonos=0.0;
		
	
		
		for(TwAbono twAbono : listaAbonos) {
			
			AbonosDto abono = new AbonosDto();
			
			abono.setId(twAbono.getnId());
			abono.setAbono(twAbono.getnAbono());
			abono.setFecha(util.formatoFecha(twAbono.getdFecha()));
			abono.setFormaPago(twAbono.getTcFormapago().getsDescripcion());
			abono.setUsuario(twAbono.getTcUsuario().getsNombreUsuario());
			
			listaAbonosDto.add(abono);
			
			abonos=abonos+twAbono.getnAbono();
			
		}
		
		
		

		return reporteService.generaAbonoVentaPDF(reporteVenta, listaAbonosDto,abonos);
	}
	
	@Override
	public byte[] getAbonoVentaIdClientePDF(Long nIdCliente) {
	
		
		TcCliente cliente=clientesRepository.getById(nIdCliente);
		
		utils util=new utils();
		
		System.err.println();
		
		 
		
		 List<TvVentaDetalle> listaVentaDetalleCredito =tvVentaDetalleRepository.consultaVentaDetalleId(nIdCliente, 1);
		List<ReporteAbonoVentaCreditoDto> listaReporteVentaAbomo = new ArrayList<ReporteAbonoVentaCreditoDto>();
	
		
		
	
		
	           double totalGeneral=0.0;
		
		

					

			for (TvVentaDetalle listaVentaDetalle : listaVentaDetalleCredito) {
				ReporteAbonoVentaCreditoDto ventaAbomo = new ReporteAbonoVentaCreditoDto();

				ventaAbomo.setIdCliente(listaVentaDetalle.getnIdCliente());
				ventaAbomo.setIdVenta(listaVentaDetalle.getnId());
				ventaAbomo.setFolioVenta(listaVentaDetalle.getsFolioVenta());
				ventaAbomo.setFechaVenta(util.formatoFecha(listaVentaDetalle.getdFechaVenta()));
				ventaAbomo.setFechaInicioCredito(util.formatoFecha(listaVentaDetalle.getdFechaInicioCredito()));
				ventaAbomo.setFechaTerminoCredito(util.formatoFecha(listaVentaDetalle.getdFechaTerminoCredito()));
				ventaAbomo.setTotalVenta(listaVentaDetalle.getnTotalVenta());
				ventaAbomo.setTotalAbono(listaVentaDetalle.getnTotalAbono());
				ventaAbomo.setSaldoTotal(listaVentaDetalle.getnSaldoTotal());
				ventaAbomo.setAvancePago(listaVentaDetalle.getnAvancePago());	
				totalGeneral=totalGeneral+listaVentaDetalle.getnSaldoTotal();

				List<TwAbono> listaAbonos = new ArrayList<TwAbono>();
				listaAbonos = abonoVentaIdRepository.findBynIdVenta(listaVentaDetalle.getnId());
				List<AbonosDto> listaAbonosDto = new ArrayList<AbonosDto>();

				for (TwAbono abonosDto : listaAbonos) {
					AbonosDto abono = new AbonosDto();
					
					abono.setId(abonosDto.getnId());
					abono.setAbono(abonosDto.getnAbono());
					abono.setFormaPago(abonosDto.getTcFormapago().getsDescripcion());
					abono.setFecha(util.formatoFecha(abonosDto.getdFecha()));
					abono.setUsuario(abonosDto.getTcUsuario().getsNombreUsuario());
					listaAbonosDto.add(abono);

				}
				ventaAbomo.setAbonoDto(listaAbonosDto);

				listaReporteVentaAbomo.add(ventaAbomo);

			}
		

		
		
		

		

		ReporteVentaDto reporteVenta = new ReporteVentaDto();

		reporteVenta.setNombreEmpresa("Refaccionaria Fabela");
		reporteVenta.setRfcEmpresa("TES030201001");
		reporteVenta.setTotal(totalGeneral);
		
		
		
		
		
		

		return reporteService.generaAbonoVentaClientePDF(cliente, listaReporteVentaAbomo,reporteVenta);
	
	}
	
	
}



