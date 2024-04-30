package com.refacFabela.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import com.refacFabela.dto.AbonoDto;
import com.refacFabela.dto.AbonosDto;
import com.refacFabela.dto.BalanceCajaDto;
import com.refacFabela.dto.CancelaVentaDto;
import com.refacFabela.dto.GastosDto;
import com.refacFabela.dto.PedidoDto;
import com.refacFabela.dto.PedidoProductoDto;
import com.refacFabela.dto.ProductoBodegaDto;
import com.refacFabela.dto.ReporteAbonoVentaCreditoDto;
import com.refacFabela.dto.ReporteCotizacionDto;
import com.refacFabela.dto.ReporteVentaDto;
import com.refacFabela.dto.TwSaldoUtilizadoDto;
import com.refacFabela.enums.TipoDoc;
import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TcDatosFactura;
import com.refacFabela.model.TcUsuario;
import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TvReporteCajaFormaPago;
import com.refacFabela.model.TvReporteDetalleVenta;
import com.refacFabela.model.TvVentaDetalle;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwCaja;
import com.refacFabela.model.TwCotizaciones;
import com.refacFabela.model.TwCotizacionesProducto;
import com.refacFabela.model.TwGasto;
import com.refacFabela.model.TwPedido;
import com.refacFabela.model.TwPedidoProducto;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.model.TwSaldoUtilizado;
import com.refacFabela.model.TwVenta;
import com.refacFabela.model.TwVentaProductoCancela;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.repository.AbonoVentaIdRepository;
import com.refacFabela.repository.CajaRepository;
import com.refacFabela.repository.ClientesRepository;
import com.refacFabela.repository.CotizacionProductoRepository;
import com.refacFabela.repository.CotizacionRepository;
import com.refacFabela.repository.PedidosProductoRepository;
import com.refacFabela.repository.ProductoBodegaRepository;
import com.refacFabela.repository.TcDatosFacturaRepository;
import com.refacFabela.repository.TrVentaCobroRepository;
import com.refacFabela.repository.TvReporteCajaFormaPagoRepository;
import com.refacFabela.repository.TvReporteDetalleVentaRepository;
import com.refacFabela.repository.TvVentaDetalleRepository;
import com.refacFabela.repository.TwGastoRepository;
import com.refacFabela.repository.TwPedidoRepository;
import com.refacFabela.repository.TwSaldoUtilizadoRepository;
import com.refacFabela.repository.TwSaldosRepository;
import com.refacFabela.repository.TwVentaProductoCancelaRepository;
import com.refacFabela.repository.UsuariosRepository;
import com.refacFabela.repository.VentasProductoRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.service.GeneraReporteService;
import com.refacFabela.service.ReporteService;
import com.refacFabela.utils.envioMail;
import com.refacFabela.utils.utils;
import com.refacFabela.utils.factura.ConstantesFactura;

import antlr.Utils;

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
	
	@Autowired
	private TwPedidoRepository  twPedidoRepository;
	
	@Autowired	
	private PedidosProductoRepository pedidosProductoRepository;
	@Autowired
	public TrVentaCobroRepository trVentaCobroRepository;
	@Autowired 
	public TvReporteDetalleVentaRepository tvReporteDetalleVentaRepository;
	@Autowired
	public TvReporteCajaFormaPagoRepository tvReporteCajaFormaPagoRepository;
	@Autowired
	public CajaRepository cajaRepository;
	@Autowired
	public UsuariosRepository usuariosRepository;
	@Autowired
	public TwSaldosRepository  twSaldosRepository;
	
	@Autowired
	public  ProductoBodegaRepository  productoBodegaRepository;
	
	@Autowired
	private VentasRepository ventasRepository;
	
	@Autowired
	private TwSaldoUtilizadoRepository twSaldoUtilizadoRepository;
	
	@Autowired
	private TwVentaProductoCancelaRepository twVentaProductoCancelaRepository;
	
	@Autowired
	private TwGastoRepository twGastoRepository;
	
	@Autowired
	private TcDatosFacturaRepository tcDatosFacturaRepository;
	
	@Autowired
	private CotizacionRepository cotizacionRepository;
	

	@Override
	public byte[] getCotizacionPDF(Long nIdCotizacion) {

		List<TwCotizacionesProducto> listaProductos = cotizacionProductoRepository.findBynIdCotizacion(nIdCotizacion);
		TwCotizaciones twCotizaciones=cotizacionRepository.getById(nIdCotizacion);		
		TcDatosFactura tcDatosFactura =tcDatosFacturaRepository.getById(twCotizaciones.getTcCliente().getnIdDatoFactura());

		ReporteCotizacionDto reporteCotizacion = new ReporteCotizacionDto();
		utils util=new utils();
		
		if(tcDatosFactura.getnId()==1L) {
			reporteCotizacion.setNombreEmpresa("Refacciones Fabela");
			
		}
		else {
			reporteCotizacion.setNombreEmpresa(tcDatosFactura.getsNombreEmisor());			
		}

		
		reporteCotizacion.setRfcEmpresa(tcDatosFactura.getsRfcEmisor());
	
		reporteCotizacion.setNombreCliente(listaProductos.get(0).getTwCotizaciones().getTcCliente().getsRazonSocial());
		reporteCotizacion.setRfcCliente(listaProductos.get(0).getTwCotizaciones().getTcCliente().getsRfc());
		reporteCotizacion.setFolioCotizacion(listaProductos.get(0).getTwCotizaciones().getnId());
		reporteCotizacion.setFecha(listaProductos.get(0).getTwCotizaciones().getdFecha());
		reporteCotizacion.setCorreo(listaProductos.get(0).getTwCotizaciones().getTcCliente().getsCorreo());
		reporteCotizacion.setNombreVendedor(listaProductos.get(0).getTwCotizaciones().getTcUsuario().getsNombreUsuario());
		


		List<ReporteCotizacionDto> listaProducto = new ArrayList<ReporteCotizacionDto>();

		double subtotal = 0.0;
		double iva = 0.0;
	

		for (TwCotizacionesProducto twCotizacionesProducto : listaProductos) {

			ReporteCotizacionDto reporte = new ReporteCotizacionDto();
		

			reporte.setCantidad(twCotizacionesProducto.getnCantidad() );
			reporte.setNoIdentificacion(twCotizacionesProducto.getTcProducto().getnId());
			
			if(twCotizacionesProducto.getnIdDescuento()>0 ) {
				reporte.setNombreProducto(twCotizacionesProducto.getTcProducto().getsProducto()+"- dto");
				
			}
			else {
				reporte.setNombreProducto(twCotizacionesProducto.getTcProducto().getsProducto());
				
			}
		
			reporte.setClaveSat(twCotizacionesProducto.getTcProducto().getTcClavesat().getsClavesat());
			reporte.setPrecioUnitario(util.truncarDecimales(twCotizacionesProducto.getnTotalUnitario()));
			reporte.setImporte(util.truncarDecimales(twCotizacionesProducto.getnTotalPartida()));
			reporte.setDescripcionCatSat(twCotizacionesProducto.getTcProducto().getTcClavesat().getsDescripcion());
			reporte.setCondicionEntrega(twCotizacionesProducto.getsCondicionEntrega());

			listaProducto.add(reporte);

			subtotal = subtotal + twCotizacionesProducto.getnPrecioPartida();
			iva = iva + twCotizacionesProducto.getnIvaPartida();

		}

		reporteCotizacion.setSubTotal(util.truncarDecimales(subtotal));
		reporteCotizacion.setIvaTotal(util.truncarDecimales(iva));
		reporteCotizacion.setTotal(util.truncarDecimales(subtotal+iva) );

		return reporteService.generaCotizacionPDF(reporteCotizacion, listaProducto);
	}

	@Override
	public byte[] getVentaPDF(Long nIdVenta) {

		List<TwVentasProducto> listaProductos = ventasProductoRepository.findBynIdVenta(nIdVenta);
		List<TwAbono> listaAbonos=abonoVentaIdRepository.findBynIdVenta(nIdVenta);
		TwVenta twVenta=ventasRepository.findBynId(nIdVenta);		
		TcDatosFactura tcDatosFactura =tcDatosFacturaRepository.getById(twVenta.getTcCliente().getnIdDatoFactura());
		
		utils util=new utils();

		ReporteVentaDto reporteVenta = new ReporteVentaDto();
		
		if(tcDatosFactura.getnId()==1L) {
			reporteVenta.setNombreEmpresa("Refacciones Fabela");
			
		}
		else {
			reporteVenta.setNombreEmpresa(tcDatosFactura.getsNombreEmisor());			
		}

		
		reporteVenta.setRfcEmpresa(tcDatosFactura.getsRfcEmisor());
		reporteVenta.setNombreCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRazonSocial());
		reporteVenta.setRfcCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRfc());
		reporteVenta.setFolioVenta(listaProductos.get(0).getTwVenta().getnId());
		reporteVenta.setFecha(listaProductos.get(0).getTwVenta().getdFechaVenta());
		reporteVenta.setTipoPago(listaProductos.get(0).getTwVenta().getnTipoPago());
		reporteVenta.setDescuento(util.truncaValor(listaProductos.get(0).getTwVenta().getDescuento()));
		reporteVenta.setNombreVendedor(listaProductos.get(0).getTcUsuario().getsNombreUsuario());
		

		List<ReporteVentaDto> listaProducto = new ArrayList<ReporteVentaDto>();

		double subtotal = 0.0;
		double iva = 0.0;
		double totalAbonos=0.0;
		
		for(TwAbono twAbono: listaAbonos) {
			
			totalAbonos= totalAbonos+twAbono.getnAbono();
			
		}
		

		for (TwVentasProducto twVentaProducto : listaProductos) {

			ReporteVentaDto reporte = new ReporteVentaDto();

			reporte.setCantidad(twVentaProducto.getnCantidad());
			reporte.setNoIdentificacion(twVentaProducto.getTcProducto().getnId());
			if(twVentaProducto.getnIdDescuento()>0 ) {
				reporte.setNombreProducto(twVentaProducto.getTcProducto().getsProducto()+" - dto");
				
			}
			else {
				reporte.setNombreProducto(twVentaProducto.getTcProducto().getsProducto());
				
			}
		
			reporte.setClaveSat(twVentaProducto.getTcProducto().getTcClavesat().getsClavesat());
			reporte.setPrecioUnitario(util.truncarDecimales(twVentaProducto.getnTotalUnitario()));
			reporte.setImporte(util.truncarDecimales(twVentaProducto.getnTotalPartida()));
			reporte.setDescripcionCatSat(twVentaProducto.getTcProducto().getTcClavesat().getsDescripcion());
			reporte.setCondicionEntrega(twVentaProducto.getsCondicionEntrega());

			listaProducto.add(reporte);

			subtotal = subtotal + twVentaProducto.getnPrecioPartida();
			iva = iva + twVentaProducto.getnIvaPartida();

		}

		reporteVenta.setSubTotal(util.truncarDecimales(subtotal));
		reporteVenta.setIvaTotal(util.truncarDecimales(iva));
		reporteVenta.setTotal(util.truncarDecimales(subtotal+iva-reporteVenta.getDescuento()-totalAbonos));
		System.err.println("Este es el total"+reporteVenta.getTotal());
		
		if(reporteVenta.getTotal()<0.02) {
			
			reporteVenta.setTotal(0.0);
			
		}

		return reporteService.generaVentaPDF(reporteVenta, listaProducto, totalAbonos);
	}
	
	public byte[] getSaldoFavorPDF(Long nIdVenta) {

		List<TwVentasProducto> listaProductos = ventasProductoRepository.buscarProductosCancelados(nIdVenta);
		List<TwAbono> listaAbonos=abonoVentaIdRepository.findBynIdVenta(nIdVenta);
		List<TwSaldoUtilizado> listaSaldoUtilizado=twSaldoUtilizadoRepository.consultaSaldosUtilizados(nIdVenta);
		List<TwSaldoUtilizadoDto> listaTwSaldoUtilizadoDto=new ArrayList<TwSaldoUtilizadoDto>();		
		TwVenta twVenta=ventasRepository.getById(nIdVenta);
		TcDatosFactura tcDatosFactura =tcDatosFacturaRepository.getById(twVenta.getTcCliente().getnIdDatoFactura());
		TcCliente cliente=clientesRepository.buscarCliente(twVenta.getnIdCliente());
		TcUsuario tcUsuario=usuariosRepository.getById(twVenta.getnIdUsuario());
		 List<TwVentaProductoCancela>   listaTwVentaProductoCancela=  twVentaProductoCancelaRepository.findByVenta(nIdVenta);
		
	  

		utils util=new utils();

		ReporteVentaDto reporteVenta = new ReporteVentaDto();
		if(tcDatosFactura.getnId()==1L) {
			reporteVenta.setNombreEmpresa("Refacciones Fabela");
			
		}
		else {
			reporteVenta.setNombreEmpresa(tcDatosFactura.getsNombreEmisor());			
		}

		reporteVenta.setRfcEmpresa(tcDatosFactura.getsRfcEmisor());
		reporteVenta.setNombreCliente(cliente.getsRazonSocial());
		reporteVenta.setRfcCliente(cliente.getsRfc());
		reporteVenta.setFolioVenta(twVenta.getnId());
		reporteVenta.setFecha(new Date());
		reporteVenta.setTipoPago(twVenta.getnTipoPago());
		reporteVenta.setDescuento(util.truncaValor(twVenta.getDescuento()));
		reporteVenta.setNombreVendedor(tcUsuario.getsNombreUsuario());
		

		List<ReporteVentaDto> listaProducto = new ArrayList<ReporteVentaDto>();

		double subtotal = 0.0;
		double iva = 0.0;
		double totalAbonos=0.0;
		double totalSaldoUsado=0.0;
		double saldoFinalSaldo=0.0;
		
		for(TwAbono twAbono: listaAbonos) {
			
			totalAbonos= totalAbonos+twAbono.getnAbono();
			
		}
		

		for (TwVentaProductoCancela twVentaProducto : listaTwVentaProductoCancela) {

			ReporteVentaDto reporte = new ReporteVentaDto();

			reporte.setCantidad(twVentaProducto.getnCantidad());
			reporte.setNoIdentificacion(twVentaProducto.getTcProducto().getnId());		
			reporte.setClaveSat(twVentaProducto.getTcProducto().getTcClavesat().getsClavesat());
			reporte.setPrecioUnitario(util.truncarDecimales(twVentaProducto.getnTotalUnitario()));
			reporte.setImporte(util.truncarDecimales(twVentaProducto.getnPrecioPartida()));
			reporte.setDescripcionCatSat(twVentaProducto.getTcProducto().getTcClavesat().getsDescripcion());
			reporte.setNombreProducto(twVentaProducto.getTcProducto().getsProducto());
			reporte.setCondicionEntrega("PRODUCTO CANCELADO");

			listaProducto.add(reporte);

			subtotal = subtotal + twVentaProducto.getnPrecioPartida();
			iva = iva + twVentaProducto.getnIvaUnitario();

		}
		
		for (TwSaldoUtilizado saldoUtilizadoDto : listaSaldoUtilizado) {
			
			TwSaldoUtilizadoDto saldo = new TwSaldoUtilizadoDto();
			
			saldo.setnIdVenta(saldoUtilizadoDto.getnIdVenta());
			saldo.setdFecha(saldoUtilizadoDto.getdFecha());
			saldo.setnEstatus(saldoUtilizadoDto.getnEstatus());
			saldo.setnIdCaja(saldoUtilizadoDto.getnIdCaja());
			saldo.setnIdUsuario(saldoUtilizadoDto.getnIdUsuario());
			saldo.setnSaldoUtilizado(saldoUtilizadoDto.getnSaldoUtilizado());
			saldo.setnSaldoTotal(saldoUtilizadoDto.getnSaldoTotal());
			saldo.setnIdVentaUtilizado(saldoUtilizadoDto.getnIdVentaUtilizado());
			
			listaTwSaldoUtilizadoDto.add(saldo);
			
			
			totalSaldoUsado+=util.truncaValor(saldoUtilizadoDto.getnSaldoUtilizado());
			
		}
		
		
		

		reporteVenta.setSubTotal(util.truncarDecimales(subtotal));
		reporteVenta.setIvaTotal(util.truncarDecimales(iva));
		reporteVenta.setTotal(util.truncarDecimales(subtotal));
		saldoFinalSaldo= util.truncarDecimales(subtotal-totalSaldoUsado);
		totalSaldoUsado=util.truncarDecimales(totalSaldoUsado);
		

		return reporteService.generaSaldoFavorPDF(reporteVenta, listaProducto, totalAbonos, saldoFinalSaldo,totalSaldoUsado, listaTwSaldoUtilizadoDto );
	}
	
	
	@Override
	public byte[] getVentaAlmacenPDF(Long nIdVenta) {

		List<TwVentasProducto> listaProductos = ventasProductoRepository.findBynIdVenta(nIdVenta);
		List<TwAbono> listaAbonos=abonoVentaIdRepository.findBynIdVenta(nIdVenta);
		utils util=new utils();
		TwVenta twVenta=ventasRepository.findBynId(nIdVenta);		
		TcDatosFactura tcDatosFactura =tcDatosFacturaRepository.getById(twVenta.getTcCliente().getnIdDatoFactura());

		ReporteVentaDto reporteVenta = new ReporteVentaDto();

		if(tcDatosFactura.getnId()==1L) {
			reporteVenta.setNombreEmpresa("Refacciones Fabela");
			
		}
		else {
			reporteVenta.setNombreEmpresa(tcDatosFactura.getsNombreEmisor());			
		}

		
		reporteVenta.setRfcEmpresa(tcDatosFactura.getsRfcEmisor());
		reporteVenta.setNombreCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRazonSocial());
		reporteVenta.setRfcCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRfc());
		reporteVenta.setFolioVenta(listaProductos.get(0).getTwVenta().getnId());
		reporteVenta.setFecha(listaProductos.get(0).getTwVenta().getdFechaVenta());
		reporteVenta.setTipoPago(listaProductos.get(0).getTwVenta().getnTipoPago());
		reporteVenta.setDescuento(listaProductos.get(0).getTwVenta().getDescuento());
		reporteVenta.setNombreVendedor(listaProductos.get(0).getTcUsuario().getsNombreUsuario());
		

		List<ReporteVentaDto> listaProducto = new ArrayList<ReporteVentaDto>();

		double subtotal = 0.0;
		double iva = 0.0;
		double totalAbonos=0.0;
		int totalProductosEntrga=0;
		
		for(TwAbono twAbono: listaAbonos) {
			
			totalAbonos= totalAbonos+twAbono.getnAbono();
			
		}
		

		for (TwVentasProducto twVentaProducto : listaProductos) {

			ReporteVentaDto reporte = new ReporteVentaDto();
			TwProductobodega productoBodega= new TwProductobodega();
			
			System.err.println("llegue a generar el archivo de ventas Almacen ");
			
			productoBodega=  productoBodegaRepository.obtenerProductoBodega(twVentaProducto.getTcProducto().getnId(),"LOCAL");
			
			totalProductosEntrga+=twVentaProducto.getnCantidad();

			reporte.setCantidad(twVentaProducto.getnCantidad());
			reporte.setNoIdentificacion(twVentaProducto.getTcProducto().getnId());
			reporte.setNombreProducto(twVentaProducto.getTcProducto().getsProducto()+'-'+twVentaProducto.getTcProducto().getsMarca());
			reporte.setClaveSat(twVentaProducto.getTcProducto().getTcClavesat().getsClavesat());
			reporte.setPrecioUnitario(util.truncarDecimales(twVentaProducto.getnTotalUnitario()));
			reporte.setImporte(util.truncarDecimales(twVentaProducto.getnTotalPartida()));
			reporte.setDescripcionCatSat(twVentaProducto.getTcProducto().getTcClavesat().getsDescripcion());
			if(twVentaProducto.getTwVenta().getnIdTipoVenta()==3L) {
				reporte.setCondicionEntrega("VENTA POR PEDIDO");
				
			}
			else {
				reporte.setCondicionEntrega(twVentaProducto.getsCondicionEntrega());
			}
		
			reporte.setUbicacion(productoBodega.getTcAnaquel().getsAnaquel()+productoBodega.getTcNivel().getsNivel());
			reporte.setNoParte(twVentaProducto.getTcProducto().getsNoParte());
			
					

			listaProducto.add(reporte);

			subtotal = subtotal + twVentaProducto.getnPrecioPartida();
			iva = iva + twVentaProducto.getnIvaPartida();

		}

		reporteVenta.setSubTotal(util.truncarDecimales(subtotal));
		reporteVenta.setIvaTotal(util.truncarDecimales(iva));
		reporteVenta.setTotal(util.truncarDecimales(subtotal+iva));
		reporteVenta.setTotalEntrega(totalProductosEntrga);

		return reporteService.generaVentaAlmacenPDF(reporteVenta, listaProducto, totalAbonos);
	}
	
	@Override
	public byte[] getVentaPedidoPDF(Long nIdVentaPedido) {
		
		List<TwVentasProducto> listaProductos = ventasProductoRepository.findBynIdVenta(nIdVentaPedido);
		List<TrVentaCobro> listaVentaCobro= trVentaCobroRepository.findBynIdVenta(nIdVentaPedido);
		TwVenta twVenta=ventasRepository.findBynId(nIdVentaPedido);		
		TcDatosFactura tcDatosFactura =tcDatosFacturaRepository.getById(twVenta.getTcCliente().getnIdDatoFactura());
		double subtotal = 0.0;
		double iva = 0.0;
		double abonos=0.0;
		
		
		ReporteVentaDto reporteVenta = new ReporteVentaDto();
		utils util=new utils();
		
		
		for (int i = 0; i < listaVentaCobro.size(); i++) {
			
			abonos+=listaVentaCobro.get(i).getnMonto();
		}
		
		if(tcDatosFactura.getnId()==1L) {
			reporteVenta.setNombreEmpresa("Refacciones Fabela");
			
		}
		else {
			reporteVenta.setNombreEmpresa(tcDatosFactura.getsNombreEmisor());			
		}

		
		reporteVenta.setRfcEmpresa(tcDatosFactura.getsRfcEmisor());			
		reporteVenta.setNombreCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRazonSocial());
		reporteVenta.setRfcCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRfc());
		reporteVenta.setFolioVenta(listaProductos.get(0).getTwVenta().getnId());
		reporteVenta.setFecha(listaProductos.get(0).getTwVenta().getdFechaVenta());
		reporteVenta.setAnticipo( util.truncarDecimales(abonos));
		reporteVenta.setDescuento(listaProductos.get(0).getTwVenta().getDescuento());
		reporteVenta.setNombreVendedor(listaProductos.get(0).getTcUsuario().getsNombreUsuario());
		
		
		List<ReporteVentaDto> listaProducto = new ArrayList<ReporteVentaDto>();
		
	
		
		
		for (TwVentasProducto twVentaProducto : listaProductos) {
			
			ReporteVentaDto reporte = new ReporteVentaDto();
			
			reporte.setCantidad(twVentaProducto.getnCantidad());
			reporte.setNoIdentificacion(twVentaProducto.getTcProducto().getnId());
			if(twVentaProducto.getnIdDescuento()>0) {
				reporte.setNombreProducto(twVentaProducto.getTcProducto().getsProducto()+" - dto");
				
			}
			else {
				reporte.setNombreProducto(twVentaProducto.getTcProducto().getsProducto());
				
			}
		
			reporte.setClaveSat(twVentaProducto.getTcProducto().getTcClavesat().getsClavesat()+'-'+twVentaProducto.getTcProducto().getTcClavesat().getsDescripcion());
			reporte.setPrecioUnitario(util.truncarDecimales(twVentaProducto.getnTotalUnitario()));
			reporte.setImporte(util.truncarDecimales(twVentaProducto.getnTotalPartida()));
			
			listaProducto.add(reporte);
			
			subtotal = subtotal + twVentaProducto.getnPrecioPartida();
			iva = iva + twVentaProducto.getnIvaPartida();
			
		}
		
		reporteVenta.setSubTotal(util.truncarDecimales(subtotal));
		reporteVenta.setIvaTotal(util.truncarDecimales(iva));
		reporteVenta.setTotal(util.truncarDecimales(subtotal+iva));
		
	    if(reporteVenta.getTotal()<0.02) {
			
			reporteVenta.setTotal(0.0);
			
		}
	    
	    if(twVenta.getnIdTipoVenta()==3L) {
	    	
	    	TwPedido twPedido=new TwPedido();
	    	
	    	twPedido=twPedidoRepository.pedido(nIdVentaPedido);
	    	
	    	if(twPedido!=null) {	    	
	    	reporteVenta.setIdPedido(twPedido.getnId());
	    	}
	    	else {
	    		reporteVenta.setIdPedido(null);
	    		
	    	}
	    	
	    }
	    else {
	    	
	    	reporteVenta.setIdPedido(null);
	    	
	    }
		
		
		
		return reporteService.generaVentaPedidoPDF(reporteVenta, listaProducto);
	}

	


	@Override
	public byte[] getAbonoVentaIdPDF(Long nIdVenta) {

		List<TwVentasProducto> listaProductos = ventasProductoRepository.findBynIdVenta(nIdVenta);
		TwVenta twVenta=ventasRepository.findBynId(nIdVenta);		
		TcDatosFactura tcDatosFactura =tcDatosFacturaRepository.getById(twVenta.getTcCliente().getnIdDatoFactura());

		ReporteVentaDto reporteVenta = new ReporteVentaDto();
		utils util=new utils();

		if(tcDatosFactura.getnId()==1L) {
			reporteVenta.setNombreEmpresa("Refacciones Fabela");
			
		}
		else {
			reporteVenta.setNombreEmpresa(tcDatosFactura.getsNombreEmisor());			
		}

		
		reporteVenta.setRfcEmpresa(tcDatosFactura.getsRfcEmisor());	
		reporteVenta.setNombreCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRazonSocial());
		reporteVenta.setRfcCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRfc());
		reporteVenta.setFolioVenta(listaProductos.get(0).getTwVenta().getnId());
		reporteVenta.setFecha(listaProductos.get(0).getTwVenta().getdFechaVenta());
		reporteVenta.setTipoPago(listaProductos.get(0).getTwVenta().getnTipoPago());
		reporteVenta.setCorreo(listaProductos.get(0).getTwVenta().getTcCliente().getsCorreo());
		reporteVenta.setDescuento(util.truncaValor(listaProductos.get(0).getTwVenta().getDescuento()));
		
		

		List<ReporteVentaDto> listaProducto = new ArrayList<ReporteVentaDto>();

		double subtotal = 0.0;
		double iva = 0.0;
		

		for (TwVentasProducto twVentaProducto : listaProductos) {
		

			subtotal = subtotal + twVentaProducto.getnPrecioPartida();
			iva = iva + twVentaProducto.getnIvaPartida();

		}

		reporteVenta.setSubTotal(util.truncarDecimales(subtotal));
		reporteVenta.setIvaTotal(util.truncarDecimales(iva));
		reporteVenta.setTotal(util.truncarDecimales(subtotal+iva));
		
		if(reporteVenta.getTotal()<0.02) {
			reporteVenta.setTotal(0.0);
			
		}
		
		List<TwAbono> listaAbonos =abonoVentaIdRepository.findBynIdVenta(nIdVenta);
		
		List<AbonosDto> listaAbonosDto=new ArrayList<AbonosDto>();
		
	
		
		double abonos=0.0;
		
	
		
		for(TwAbono twAbono : listaAbonos) {
			
			AbonosDto abono = new AbonosDto();
			
			abono.setId(twAbono.getnId());
			abono.setAbono(util.truncarDecimales(twAbono.getnAbono()));
			abono.setFecha(util.formatoFecha(twAbono.getdFecha()));
			abono.setFormaPago(twAbono.getTcFormapago().getsDescripcion());
			abono.setUsuario(twAbono.getTcUsuario().getsNombreUsuario());
			
			listaAbonosDto.add(abono);
			
			abonos=abonos+twAbono.getnAbono();
			
		}
		
		abonos=util.truncarDecimales(abonos);
		

		return reporteService.generaAbonoVentaPDF(reporteVenta, listaAbonosDto,abonos);
	}
	
	@Override
	public byte[] getAbonoVentaIdClientePDF(Long nIdCliente) {
	
		
		TcCliente cliente=clientesRepository.getById(nIdCliente);
		
		utils util=new utils();
		
		 List<TvVentaDetalle> listaVentaDetalleCredito =tvVentaDetalleRepository.consultaVentaDetalleId(nIdCliente, 1);
		List<ReporteAbonoVentaCreditoDto> listaReporteVentaAbomo = new ArrayList<ReporteAbonoVentaCreditoDto>();
		TcCliente tcCliente=clientesRepository.getById(nIdCliente);
		TcDatosFactura tcDatosFactura =tcDatosFacturaRepository.getById(tcCliente.getnIdDatoFactura());
	
	           double totalGeneral=0.0;
	           double descuento=0.0;
	           double totalAbonos=0.0;
		
			for (TvVentaDetalle listaVentaDetalle : listaVentaDetalleCredito) {
				ReporteAbonoVentaCreditoDto ventaAbomo = new ReporteAbonoVentaCreditoDto();

				ventaAbomo.setIdCliente(listaVentaDetalle.getnIdCliente());
				ventaAbomo.setIdVenta(listaVentaDetalle.getnId());
				ventaAbomo.setFolioVenta(listaVentaDetalle.getsFolioVenta());
				ventaAbomo.setFechaVenta(util.formatoFecha(listaVentaDetalle.getdFechaVenta()));
				ventaAbomo.setFechaInicioCredito(util.formatoFecha(listaVentaDetalle.getdFechaInicioCredito()));
				ventaAbomo.setFechaTerminoCredito(util.formatoFecha(listaVentaDetalle.getdFechaTerminoCredito()));
				ventaAbomo.setTotalVenta(util.truncarDecimales(listaVentaDetalle.getnTotalVenta()));
				ventaAbomo.setTotalAbono(util.truncarDecimales(listaVentaDetalle.getnTotalAbono()));
				ventaAbomo.setSaldoTotal(util.truncarDecimales(listaVentaDetalle.getnSaldoTotal()));
				ventaAbomo.setAvancePago(listaVentaDetalle.getnAvancePago());
				ventaAbomo.setDescuento(util.truncarDecimales(listaVentaDetalle.getDescuento()));
				totalGeneral=totalGeneral+listaVentaDetalle.getnTotalVenta();
				descuento=descuento+listaVentaDetalle.getDescuento();
				  Date fechaActual = new Date();
				if(listaVentaDetalle.getnVencido()) {
					ventaAbomo.setVencido(true);
					
				 } else {					
					 ventaAbomo.setVencido(false);
			     } 
				
				System.err.println(ventaAbomo.getVencido());

				List<TwAbono> listaAbonos = new ArrayList<TwAbono>();
				listaAbonos = abonoVentaIdRepository.findBynIdVenta(listaVentaDetalle.getnId());
				List<AbonosDto> listaAbonosDto = new ArrayList<AbonosDto>();

				for (TwAbono abonosDto : listaAbonos) {
					AbonosDto abono = new AbonosDto();
					
					abono.setId(abonosDto.getnId());
					abono.setAbono(util.truncarDecimales(abonosDto.getnAbono()));
					abono.setFormaPago(abonosDto.getTcFormapago().getsDescripcion());
					abono.setFecha(util.formatoFecha(abonosDto.getdFecha()));
					abono.setUsuario(abonosDto.getTcUsuario().getsNombreUsuario());
					listaAbonosDto.add(abono);
					totalAbonos=totalAbonos+abonosDto.getnAbono();

				}
				
				totalGeneral=util.truncarDecimales(totalGeneral);
				descuento=util.truncarDecimales(descuento);
				totalAbonos=util.truncarDecimales(totalAbonos);
				
				ventaAbomo.setAbonoDto(listaAbonosDto);

				listaReporteVentaAbomo.add(ventaAbomo);

			}
		
		ReporteVentaDto reporteVenta = new ReporteVentaDto();

		if(tcDatosFactura.getnId()==1L) {
			reporteVenta.setNombreEmpresa("Refacciones Fabela");
			
		}
		else {
			reporteVenta.setNombreEmpresa(tcDatosFactura.getsNombreEmisor());			
		}

		
		reporteVenta.setRfcEmpresa(tcDatosFactura.getsRfcEmisor());	
		reporteVenta.setTotal(util.truncarDecimales(totalGeneral));
		reporteVenta.setDescuento(util.truncarDecimales(descuento));
		reporteVenta.setAbonos(util.truncarDecimales(totalAbonos));
	    if(reporteVenta.getTotal()<0.02) {
			
			reporteVenta.setTotal(0.0);
			
		}
				
		return reporteService.generaAbonoVentaClientePDF(cliente, listaReporteVentaAbomo,reporteVenta);
	
	}

	@Override
	public byte[] getPedidoIdPDF(Long nIdPedido) {
		

	utils util=new utils();		
	String estatus="";

	//CONSULTA DE OBJETOS Y LISTAS DEL PEDIDO
	TwPedido twPedido=twPedidoRepository.getById(nIdPedido);
	List<TwPedidoProducto> listaPedioPedidoProducto = pedidosProductoRepository.obtenerPedidosRegistrados(nIdPedido);	
	List<PedidoProductoDto> listaPedidoProducto= new ArrayList<PedidoProductoDto>();	
	System.err.println();
	         
		
			for (TwPedidoProducto twPedidoProducto : listaPedioPedidoProducto) {
				PedidoProductoDto pedidoProductoDto= new PedidoProductoDto();
				

				pedidoProductoDto.setNoParte(twPedidoProducto.getTcProducto().getsNoParte());
				pedidoProductoDto.setProducto(twPedidoProducto.getTcProducto().getsProducto());
				pedidoProductoDto.setCantidad(twPedidoProducto.getnCantidadPedida());
				pedidoProductoDto.setFechaPedido(util.formatoFecha(twPedidoProducto.getdFechaPedido()));
				pedidoProductoDto.setProveedor(twPedidoProducto.getTcProveedore().getsRazonSocial());
				
				
				if(twPedidoProducto.getdFechaPedido()!=null) {
					pedidoProductoDto.setFechaRecibida(util.formatoFecha(twPedidoProducto.getdFechaPedido()));
				}
				else
				{
					pedidoProductoDto.setFechaRecibida(null);
				}
				pedidoProductoDto.setPrecio(twPedidoProducto.getTcProducto().getnPrecio());
				
				 switch (twPedidoProducto.getTcProducto().getnEstatus()) 
			        {   
				        case 0:  estatus = "PEDIDO REALIZADO";
	                    break;	                    
			            case 1:  estatus = "PEDIDO RECIBIDO";
			                     break;
			            case 2:  estatus = "PEDIDO CANCELADO";
			                     break;			          
			           
			        }
				
				pedidoProductoDto.setEstatus(estatus);				
				// SE AGREGA EL OBJETO A LA LISTA 
				System.err.println(pedidoProductoDto);
				
				listaPedidoProducto.add(pedidoProductoDto);

			}
		
			System.err.println(listaPedidoProducto);
		
				
		return reporteService.generaPedidoPDF(twPedido, listaPedidoProducto);
	}
	
	public byte[] getReporteCaja(Long nIdCaja) {
		
		Double totalIngresosVenta = 0.0;
		Double totalIngresosAbono = 0.0;
		Double totalVentaCaja = 0.0;
		Integer totalEntregadas=0;
		Integer totalNoEntregadas=0;
		Integer totalEntegasParciales=0;
		Double totalReitegros=0.0;
		Double totalGastos=0.0;
		Double totalCredito=0.0;
		Double totalVenta=0.0;
		
		// variables de contado
		Double efectivoContado=0.0;
		Double chequeContado=0.0;
		Double transferenciaContado=0.0;
		Double tarjetaCreditoContado=0.0;
		Double tarjetaDebitoContado=0.0;
		Double CondonacionContado=0.0;
		
		// variables de crédito
		Double efectivoAbono=0.0;
		Double chequeAbono=0.0;
		Double transferenciaAbono=0.0;
		Double tarjetaCreditoAbono=0.0;
		Double tarjetaDebitoAbono=0.0;
		Double CondonacionAbono=0.0; 
		
		Double totalCencela=0.0;
		Double saldoFavor=0.0;
		Double totalDescuento=0.0;

		List<TrVentaCobro> trVentasCobro = trVentaCobroRepository.obtenerPagosCaja(nIdCaja);
		List<TwAbono> twAbono = abonoVentaIdRepository.obtenerAbonosCaja(nIdCaja);
		List<TvReporteDetalleVenta> trReporteDetalleVentas = tvReporteDetalleVentaRepository.obtenerVentasCajaReporte(nIdCaja);		
		List<TvReporteCajaFormaPago> tvReporteCajaFormaPago=tvReporteCajaFormaPagoRepository.obtenerFormaPagoCaja(nIdCaja);
		List<TwVentaProductoCancela> listaTwVentaProductoCancela=twSaldosRepository.productosCanceladosCaja(nIdCaja);
		List<TwVentasProducto> listaVentasProducto=ventasProductoRepository.buscarProductosVenta(nIdCaja);		
		List<TwGasto> listaTwGastos=twGastoRepository.obtenerGastosCaja(nIdCaja);
		 TwCaja caja = cajaRepository.getById(nIdCaja); 
		 TcUsuario usuario = usuariosRepository.obtenerUsuario(caja.getTcUsuario().getnId());
		List<GastosDto> auxListaGastos=new ArrayList<GastosDto>();
		List <AbonoDto> auxListaAbonos= new ArrayList<AbonoDto>();
		List <CancelaVentaDto> auxCancelaVenta= new ArrayList<CancelaVentaDto>();
		List <TwVenta> listaVentas =ventasRepository.obtnerVentasIdCaja(nIdCaja);
		
		 //System.err.println(listaTwGastos);
		 utils util=new utils();				
		BalanceCajaDto balanceCajaDto= new BalanceCajaDto();	
		totalReitegros=twSaldosRepository.totalCancela(nIdCaja);
		totalReitegros=totalReitegros==null?0:totalReitegros;
		
		for (int i = 0; i < listaTwGastos.size(); i++) {			
			
			 totalGastos+=listaTwGastos.get(i).getnMonto();
			 GastosDto gastoDto=new GastosDto();		 
			 gastoDto.setnId(listaTwGastos.get(i).getnId());
			 gastoDto.setFecha(listaTwGastos.get(i).getdFecha());
			 gastoDto.setDescripcion(listaTwGastos.get(i).getsDescripcion());
			 gastoDto.setGasto(listaTwGastos.get(i).getTcGasto().getsGasto());
			 gastoDto.setUsuario(listaTwGastos.get(i).getTcUsuario().getsNombreUsuario());
			 gastoDto.setMonto(util.truncarDecimales(listaTwGastos.get(i).getnMonto()) );
			 
			 auxListaGastos.add(gastoDto);
			
			
		}
		
	
		 
		for (int i = 0; i < trVentasCobro.size(); i++) {			
			totalIngresosVenta+= trVentasCobro.get(i).getnMonto();				
			
			// Variables de contado POR TIPO DE PAGO
			
			if(trVentasCobro.get(i).getTwVenta().getnTipoPago()==0L && trVentasCobro.get(i).getnIdFormaPago()==1L ) {				
				efectivoContado+=trVentasCobro.get(i).getnMonto();
				
			}
			if(trVentasCobro.get(i).getTwVenta().getnTipoPago()==0L && trVentasCobro.get(i).getnIdFormaPago()==2L ) {				
				chequeContado+=trVentasCobro.get(i).getnMonto();				
			}
			if(trVentasCobro.get(i).getTwVenta().getnTipoPago()==0L && trVentasCobro.get(i).getnIdFormaPago()==3L ) {				
				transferenciaContado+=trVentasCobro.get(i).getnMonto();				
			}
			if(trVentasCobro.get(i).getTwVenta().getnTipoPago()==0L && trVentasCobro.get(i).getnIdFormaPago()==4L ) {				
				tarjetaCreditoContado+=trVentasCobro.get(i).getnMonto();				
			}
			if(trVentasCobro.get(i).getTwVenta().getnTipoPago()==0L && trVentasCobro.get(i).getnIdFormaPago()==18L ) {				
				tarjetaDebitoContado+=trVentasCobro.get(i).getnMonto();				
			}			
			if(trVentasCobro.get(i).getTwVenta().getnTipoPago()==0L && trVentasCobro.get(i).getnIdFormaPago()==11L ) {				
				CondonacionAbono+=trVentasCobro.get(i).getnMonto();				
			}			
					
		}
		
		for (int i = 0; i < twAbono.size(); i++) {			
			totalIngresosAbono+= twAbono.get(i).getnAbono();
			AbonoDto abonosDto=new AbonoDto();
			abonosDto.setAbono(util.truncaValor(twAbono.get(i).getnAbono()));
			abonosDto.setFechaAbono(twAbono.get(i).getdFecha());
			abonosDto.setIdVenta(twAbono.get(i).getnIdVenta());
			abonosDto.setFormaPago(twAbono.get(i).getTcFormapago().getsDescripcion());
			abonosDto.setUsuario(twAbono.get(i).getTcUsuario().getsNombreUsuario());
			abonosDto.setFechaVenta(twAbono.get(i).getTwVenta().getdFechaVenta());
			
			auxListaAbonos.add(abonosDto);
			
			//COBROS POR TIPO DE PAGO DE ABONOS
			
			if(twAbono.get(i).getTcFormapago().getnId()==1L) {	
				efectivoAbono+=twAbono.get(i).getnAbono();				
			}
	        if(twAbono.get(i).getTcFormapago().getnId()==2L) {
	        	chequeAbono+=twAbono.get(i).getnAbono();				
			}
	        if(twAbono.get(i).getTcFormapago().getnId()==3L) {				
	        	transferenciaAbono+=twAbono.get(i).getnAbono();				

			}
	        if(twAbono.get(i).getTcFormapago().getnId()==4L) {				
	        	tarjetaCreditoAbono+=twAbono.get(i).getnAbono();				

			}
	        if(twAbono.get(i).getTcFormapago().getnId()==18L) {				
	        	tarjetaDebitoAbono+=twAbono.get(i).getnAbono();				

			}
	        if(twAbono.get(i).getTcFormapago().getnId()==11L) {				
	        	CondonacionAbono+=twAbono.get(i).getnAbono();				

			}
			
		}
		
		
		for (int i = 0; i < trReporteDetalleVentas.size(); i++) {			
			totalVentaCaja+= trReporteDetalleVentas.get(i).getnTotalVenta();	
			
			if(trReporteDetalleVentas.get(i).getsEstatusEntrega().equals("ENTREGADA")) {
				totalEntregadas+=1;
			}
			
			if(trReporteDetalleVentas.get(i).getsEstatusEntrega().equals("NO ENTREGADA")) {
				totalNoEntregadas+=1;
			}
			if(trReporteDetalleVentas.get(i).getsEstatusEntrega().equals("ENTREGADA PARCIAL")) {
				totalEntegasParciales+=1;
			}
			
			List<TrVentaCobro> listaTrVentaCobro=new ArrayList<TrVentaCobro>();
			
			listaTrVentaCobro=trVentaCobroRepository.findBynIdVenta(trReporteDetalleVentas.get(i).getnIdVenta());
			String formapago="";
			
			if(listaTrVentaCobro.size()>1) {
						
				
				for (int j = 0; j < listaTrVentaCobro.size(); j++) {
					
					if (listaTrVentaCobro.get(j).getTcFormapago().getnId() == 1) {

						formapago += "EF:" + util.truncaValor(listaTrVentaCobro.get(j).getnMonto()) + "/";
					}
					if (listaTrVentaCobro.get(j).getTcFormapago().getnId() == 2) {

						formapago += "CH:" + util.truncaValor(listaTrVentaCobro.get(j).getnMonto()) + "/";
					}
					if (listaTrVentaCobro.get(j).getTcFormapago().getnId() == 3) {

						formapago += "TE:" + util.truncaValor(listaTrVentaCobro.get(j).getnMonto()) + "/";
					}
					if (listaTrVentaCobro.get(j).getTcFormapago().getnId() == 4) {

						formapago += "TC:" + util.truncaValor(listaTrVentaCobro.get(j).getnMonto()) + "/";
					}
					if (listaTrVentaCobro.get(j).getTcFormapago().getnId() == 11) {

						formapago += "CO:" + util.truncaValor(listaTrVentaCobro.get(j).getnMonto()) + "/";
					}
					if (listaTrVentaCobro.get(j).getTcFormapago().getnId() == 18) {

						formapago += "TD:" + util.truncaValor(listaTrVentaCobro.get(j).getnMonto()) + "/";
					}
					if (listaTrVentaCobro.get(j).getTcFormapago().getnId() == 20) {

						formapago += "AN:" + util.truncaValor(listaTrVentaCobro.get(j).getnMonto()) + "/";
					}
					
					
					
				}
				
				
				
				
				
			trReporteDetalleVentas.get(i).setsFormaPago(formapago);
				
			}
			
           
			if(listaTrVentaCobro.size()==1) {
										
				for (int j = 0; j < listaTrVentaCobro.size(); j++) {
								
					formapago +=  listaTrVentaCobro.get(j).getTcFormapago().getsDescripcion() ;
									
				}
						
				
			trReporteDetalleVentas.get(i).setsFormaPago(formapago);
				
			}
			
			
		}
		
		
		
		for (int i = 0; i < listaTwVentaProductoCancela.size(); i++) {
			
	
			
			CancelaVentaDto cancelaVentaDto=new CancelaVentaDto();
			
			cancelaVentaDto.setCantidad(listaTwVentaProductoCancela.get(i).getnCantidad());
			cancelaVentaDto.setCliente(listaTwVentaProductoCancela.get(i).getTwVenta().getTcCliente().getsRazonSocial());
			cancelaVentaDto.setFechaVenta(listaTwVentaProductoCancela.get(i).getTwVenta().getdFechaVenta());
			cancelaVentaDto.setNoParte(listaTwVentaProductoCancela.get(i).getTcProducto().getsNoParte());
			cancelaVentaDto.setProducto(listaTwVentaProductoCancela.get(i).getTcProducto().getsProducto());
			cancelaVentaDto.setTotalCancela(listaTwVentaProductoCancela.get(i).getnPrecioPartida());
			cancelaVentaDto.setUsuario(listaTwVentaProductoCancela.get(i).getTcUsuario().getsNombreUsuario());
			cancelaVentaDto.setVenta(listaTwVentaProductoCancela.get(i).getnIdVenta());
			
			if(listaTwVentaProductoCancela.get(i).getTwVenta().getnTipoPago()==1L) {
				cancelaVentaDto.setTipoPago("CRÉDITO");
				
			}
			else {
				cancelaVentaDto.setTipoPago("CONTADO");
				
			}
			
			if(listaTwVentaProductoCancela.get(i).getTwVenta().getnSaldo()==true) {
				cancelaVentaDto.setSaldoFavor("SI");
				saldoFavor+=listaTwVentaProductoCancela.get(i).getnPrecioPartida();
				
			}
			else {
				cancelaVentaDto.setSaldoFavor("NO");
				
			}
			
				totalCencela+=listaTwVentaProductoCancela.get(i).getnPrecioPartida();
			
			
			
			auxCancelaVenta.add(cancelaVentaDto);
			
		}
		
		for (int i = 0; i < listaVentasProducto.size(); i++) {		
			
			totalVenta+=listaVentasProducto.get(i).getnTotalPartida();			
		}
		
		for (int i = 0; i < listaVentas.size(); i++) {
			
			totalDescuento+=listaVentas.get(i).getDescuento();
			
		}
		
		
		
		/*LLENAOD DE OBJETO BALANCE CAJA*/
		balanceCajaDto.setCaja(caja.getnId());
		balanceCajaDto.setFechaInicioCaja(util.formatoFecha(caja.getdFechaApertura()));
		balanceCajaDto.setTotalIngresoVenta(util.truncaValor(totalIngresosVenta));
		balanceCajaDto.setTotalIngresoAbonos(util.truncaValor(totalIngresosAbono));		
		balanceCajaDto.setTotalGeneralIngresos(util.truncaValor(totalIngresosVenta+totalIngresosAbono-totalReitegros)  );	
		balanceCajaDto.setTotalVentas(util.truncaValor(totalVenta));
		balanceCajaDto.setNoVentas(trReporteDetalleVentas.size());
		balanceCajaDto.setNoAbonos(twAbono.size());
		balanceCajaDto.setUsuarioCaja(usuario.getsNombreUsuario());
		balanceCajaDto.setTotalEntregadas(totalEntregadas);
		balanceCajaDto.setTotalNoEntregadas(totalNoEntregadas);
		balanceCajaDto.setTotalEntregasParciales(totalEntegasParciales);
		balanceCajaDto.setFechaGeneraReporte(util.formatoFecha(new Date()));
		balanceCajaDto.setTvReporteDetalleVenta(trReporteDetalleVentas);
		balanceCajaDto.setTvReporteCajaFormaPago(tvReporteCajaFormaPago);
		balanceCajaDto.setTotalReintegro(util.truncaValor(util.truncaValor(totalReitegros)));
		balanceCajaDto.setListaGastos(auxListaGastos);
		balanceCajaDto.setTotalGastos(util.truncaValor(totalGastos));
		balanceCajaDto.setListaAbonos(auxListaAbonos);
		balanceCajaDto.setListaCancelados(auxCancelaVenta);
		balanceCajaDto.setTotalCredito(util.truncaValor(totalCredito));
		balanceCajaDto.setTotalVenta(util.truncaValor(totalVenta));	
		
		// variables de contado
		balanceCajaDto.setEfectivoContado(util.truncaValor(efectivoContado));
		balanceCajaDto.setChequeContado(util.truncaValor(chequeContado));
		balanceCajaDto.setTransferenciaContado(util.truncaValor(transferenciaContado));
		balanceCajaDto.setTarjetaCreditoContado(util.truncaValor(tarjetaCreditoContado));
		balanceCajaDto.setTarjetaDebitoContad(util.truncaValor(tarjetaDebitoContado));
		balanceCajaDto.setCondonacionContado(util.truncaValor(CondonacionContado));
		
		// variables de crédito
		balanceCajaDto.setEfectivoAbono(util.truncaValor(efectivoAbono));
		balanceCajaDto.setChequeAbono(util.truncaValor(chequeAbono));
		balanceCajaDto.setTransferenciaAbono(util.truncaValor(transferenciaAbono));
		balanceCajaDto.setTarjetaCreditoAbono(util.truncaValor(tarjetaCreditoAbono));
		balanceCajaDto.setTarjetaDebitoAbon(util.truncaValor(tarjetaDebitoAbono));
		balanceCajaDto.setCondonacionAbono(util.truncaValor(CondonacionAbono));
		balanceCajaDto.setSaldosFavor(util.truncaValor(saldoFavor));
		balanceCajaDto.setTotalCancelado(util.truncaValor(totalCencela));
		balanceCajaDto.setTotalDescuento(util.truncaValor(totalDescuento));
		
		 
		System.err.println(auxListaGastos.size());
		System.err.println(auxListaAbonos.size());
		System.err.println(auxCancelaVenta.size());

		

		return reporteService.generarReporteCajaPDF(balanceCajaDto);
	}

	@Override
	public byte[] getDocumento(Long nIdVenta, TipoDoc TipoDoc) {
		String ruta="";
		String rutaRaiz="";
		File pdfFile = null;
		
		
		if (TipoDoc.equals(com.refacFabela.enums.TipoDoc.PDF_FACTURA)) {
			
			ruta=com.refacFabela.enums.TipoDoc.PDF_FACTURA.getPath();
			rutaRaiz=ConstantesFactura.rutaRaiz;
						    		    
			 byte[] bytesReporte = null;
			try {
				  bytesReporte = Files.readAllBytes(Paths.get(ruta+ nIdVenta + ".pdf"));
				  
				 TwVenta twVenta= this.ventasRepository.getById(nIdVenta);
				  
				 /*
				    envioMail enviar=new envioMail();
       				enviar.enviarCorreo(twVenta.getTcCliente().getsCorreo(), 
       						"Factura_"+nIdVenta.toString(),
       						"<p>Adjunto al presente factura No. "+nIdVenta.toString()+"</p><p> Sin m&aacute;s por el momento envi&oacute; un cordial saludo.</p>",
       						rutaRaiz,
       						nIdVenta.toString(),
       						2
       						); */
				
			      return bytesReporte;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		} 
		
		else if(TipoDoc.equals(com.refacFabela.enums.TipoDoc.XML_FACTURA)) {
			ruta=com.refacFabela.enums.TipoDoc.XML_FACTURA.getPath();
		    
			 byte[] bytesReporte = null;
			try {
				  bytesReporte = Files.readAllBytes(Paths.get(ruta+ nIdVenta + ".xml"));
				
			      return bytesReporte;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		return null;
	}

	@Override
	public byte[] getReporteInventario(Long nIBodega, Long nIdNivel, Long nIdAnaquel) {
		
		
		
	
		List <TwProductobodega> listaProductoInventario=productoBodegaRepository.obtenerInventaroEsp(nIBodega, nIdAnaquel, nIdNivel);
		
		
		List <ProductoBodegaDto> listaProductoBodegaDto  =new ArrayList<ProductoBodegaDto>(); ;
		System.err.println(listaProductoInventario);
		
		
		for (int i = 0; i < listaProductoInventario.size(); i++) {
			ProductoBodegaDto productoBodegaDto = new ProductoBodegaDto();
			productoBodegaDto.setNoParte(listaProductoInventario.get(i).getTcProducto().getsNoParte());
			productoBodegaDto.setProducto(listaProductoInventario.get(i).getTcProducto().getsProducto());
			productoBodegaDto.setCantidad(listaProductoInventario.get(i).getnCantidad());
			productoBodegaDto.setBodega(listaProductoInventario.get(i).getTcBodega().getsBodega());
			productoBodegaDto.setAnaquel(listaProductoInventario.get(i).getTcAnaquel().getsAnaquel());
			productoBodegaDto.setNivel(listaProductoInventario.get(i).getTcNivel().getsNivel());

			
			
			
			
			listaProductoBodegaDto.add(productoBodegaDto);
			
		}
		
		System.err.println(listaProductoBodegaDto);
		
		
		
		return reporteService.generarReporteInventarioPDF(listaProductoBodegaDto);
	}
	
	
}



