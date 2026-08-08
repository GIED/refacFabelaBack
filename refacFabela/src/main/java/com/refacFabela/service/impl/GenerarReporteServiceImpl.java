package com.refacFabela.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.refacFabela.model.TwFacturacion;
import com.refacFabela.model.TwFacturacionPacAudit;
import com.refacFabela.model.TwFacturacionPacAuditDetalle;
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
import com.refacFabela.repository.FacturacionComplementoPagoRepository;
import com.refacFabela.repository.FacturaRepository;
import com.refacFabela.repository.FacturacionPacAuditDetalleRepository;
import com.refacFabela.repository.FacturacionPacAuditRepository;
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
import com.refacFabela.utils.DateTimeUtil;
import com.refacFabela.utils.envioMail;
import com.refacFabela.utils.utils;

import antlr.Utils;

@Service
public class GenerarReporteServiceImpl implements GeneraReporteService {

	private static final Logger logger = LogManager.getLogger("errorLogger");
	private static final String RUTA_FACTURAS_OBLIGATORIA = "/opt/webserver/backEnd/refacFabela";

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
	private DatosFacturaStorageResolver datosFacturaStorageResolver;
	
	@Autowired
	private CotizacionRepository cotizacionRepository;

	@Autowired
	private FacturacionPacAuditRepository facturacionPacAuditRepository;

	@Autowired
	private FacturacionPacAuditDetalleRepository facturacionPacAuditDetalleRepository;

	@Autowired
	private FacturacionComplementoPagoRepository facturacionComplementoPagoRepository;

	@Autowired
	private FacturaRepository facturaRepository;
	

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
		reporteCotizacion.setnIdCliente(listaProductos.get(0).getTwCotizaciones().getTcCliente().getnId());
		reporteCotizacion.setFolioCotizacion(listaProductos.get(0).getTwCotizaciones().getnId());
		reporteCotizacion.setFecha(listaProductos.get(0).getTwCotizaciones().getdFecha());
		reporteCotizacion.setCorreo(listaProductos.get(0).getTwCotizaciones().getTcCliente().getsCorreo());
		reporteCotizacion.setNombreVendedor(listaProductos.get(0).getTwCotizaciones().getTcUsuario().getsNombreUsuario());
		


		List<ReporteCotizacionDto> listaProducto = new ArrayList<ReporteCotizacionDto>();

		BigDecimal subtotal = BigDecimal.ZERO;
		BigDecimal iva = BigDecimal.ZERO;

		for (TwCotizacionesProducto twCotizacionesProducto : listaProductos) {

			ReporteCotizacionDto reporte = new ReporteCotizacionDto();
		

			reporte.setCantidad(twCotizacionesProducto.getnCantidad() );
			reporte.setNoIdentificacion(twCotizacionesProducto.getTcProducto().getnId());
			
			if(twCotizacionesProducto.getnIdDescuento() != null && twCotizacionesProducto.getnIdDescuento() > 0 ) {
				reporte.setNombreProducto(twCotizacionesProducto.getTcProducto().getsProducto()+"- dto");
				
			}
			else {
				reporte.setNombreProducto(twCotizacionesProducto.getTcProducto().getsProducto());
				
			}
		
			reporte.setClaveSat(twCotizacionesProducto.getTcProducto().getTcClavesat().getsClavesat());
			reporte.setPrecioUnitario(DateTimeUtil.truncarDosDecimales(twCotizacionesProducto.getnTotalUnitario()));
			reporte.setImporte(DateTimeUtil.truncarDosDecimales(twCotizacionesProducto.getnTotalPartida()));
			reporte.setDescripcionCatSat(twCotizacionesProducto.getTcProducto().getTcClavesat().getsDescripcion());
			reporte.setCondicionEntrega(twCotizacionesProducto.getsCondicionEntrega());
			reporte.setNoParte(twCotizacionesProducto.getTcProducto().getsNoParte());

			listaProducto.add(reporte);

			subtotal = subtotal.add(twCotizacionesProducto.getnPrecioPartida());
			iva = iva.add(twCotizacionesProducto.getnIvaPartida());

		}

		reporteCotizacion.setSubTotal(DateTimeUtil.truncarDosDecimales(subtotal));
		reporteCotizacion.setIvaTotal(DateTimeUtil.truncarDosDecimales(iva));
		reporteCotizacion.setTotal(DateTimeUtil.truncarDosDecimales(subtotal.add(iva)) );

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
		reporteVenta.setDescuento(listaProductos.get(0).getTwVenta().getDescuento());
		reporteVenta.setNombreVendedor(listaProductos.get(0).getTcUsuario().getsNombreUsuario());
		

		List<ReporteVentaDto> listaProducto = new ArrayList<ReporteVentaDto>();

		BigDecimal subtotal = new BigDecimal("0");
		BigDecimal iva = new BigDecimal("0");
		BigDecimal totalAbonos=new BigDecimal("0");
		
		for(TwAbono twAbono: listaAbonos) {
			
			totalAbonos= totalAbonos.add(twAbono.getnAbono());
			
		}
		

		for (TwVentasProducto twVentaProducto : listaProductos) {

			ReporteVentaDto reporte = new ReporteVentaDto();

			reporte.setCantidad(twVentaProducto.getnCantidad());
			reporte.setNoIdentificacion(twVentaProducto.getTcProducto().getnId());
			if(twVentaProducto.getnIdDescuento() != null && twVentaProducto.getnIdDescuento()>0 ) {
				reporte.setNombreProducto(twVentaProducto.getTcProducto().getsProducto()+" - dto");
				
			}
			else {
				reporte.setNombreProducto(twVentaProducto.getTcProducto().getsProducto());
				
			}
		
			reporte.setClaveSat(twVentaProducto.getTcProducto().getTcClavesat().getsClavesat());
			reporte.setPrecioUnitario(DateTimeUtil.truncarDosDecimales(twVentaProducto.getnTotalUnitario()));
			reporte.setImporte(DateTimeUtil.truncarDosDecimales(twVentaProducto.getnTotalPartida()));
			reporte.setDescripcionCatSat(twVentaProducto.getTcProducto().getTcClavesat().getsDescripcion());
			reporte.setCondicionEntrega(twVentaProducto.getsCondicionEntrega());

			listaProducto.add(reporte);

			subtotal = subtotal.add( twVentaProducto.getnPrecioPartida());
			iva = iva.add( twVentaProducto.getnIvaPartida());

		}

		reporteVenta.setSubTotal(DateTimeUtil.truncarDosDecimales(subtotal));
		reporteVenta.setIvaTotal(DateTimeUtil.truncarDosDecimales(iva));
		
		BigDecimal total = subtotal
			    .add(iva)
			    .subtract(reporteVenta.getDescuento())
			    .subtract(totalAbonos);

			reporteVenta.setTotal(DateTimeUtil.truncarDosDecimales(total));
			
		
		if(reporteVenta.getTotal().compareTo(BigDecimal.ZERO) == 0) {
			
			reporteVenta.setTotal(BigDecimal.ZERO);
			
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
		reporteVenta.setFecha(DateTimeUtil.obtenerHoraExactaDeMexico());
		reporteVenta.setTipoPago(twVenta.getnTipoPago());
		reporteVenta.setDescuento(twVenta.getDescuento());
		reporteVenta.setNombreVendedor(tcUsuario.getsNombreUsuario());
		

		List<ReporteVentaDto> listaProducto = new ArrayList<ReporteVentaDto>();

		BigDecimal subtotal = BigDecimal.ZERO;
		BigDecimal iva = BigDecimal.ZERO;
		BigDecimal totalAbonos=BigDecimal.ZERO;
		BigDecimal totalSaldoUsado=BigDecimal.ZERO;
		BigDecimal saldoFinalSaldo=BigDecimal.ZERO;
		
		for(TwAbono twAbono: listaAbonos) {
			
			totalAbonos= totalAbonos.add(twAbono.getnAbono());
			
		}
		

		for (TwVentaProductoCancela twVentaProducto : listaTwVentaProductoCancela) {

			ReporteVentaDto reporte = new ReporteVentaDto();

			reporte.setCantidad(twVentaProducto.getnCantidad());
			reporte.setNoIdentificacion(twVentaProducto.getTcProducto().getnId());		
			reporte.setClaveSat(twVentaProducto.getTcProducto().getTcClavesat().getsClavesat());
			reporte.setPrecioUnitario(DateTimeUtil.truncarDosDecimales(twVentaProducto.getnTotalUnitario()));
			reporte.setImporte(DateTimeUtil.truncarDosDecimales(twVentaProducto.getnPrecioPartida()));
			reporte.setDescripcionCatSat(twVentaProducto.getTcProducto().getTcClavesat().getsDescripcion());
			reporte.setNombreProducto(twVentaProducto.getTcProducto().getsProducto());
			reporte.setCondicionEntrega("PRODUCTO CANCELADO");

			listaProducto.add(reporte);

			subtotal = subtotal.add(twVentaProducto.getnPrecioPartida());
			iva = iva.add(twVentaProducto.getnIvaUnitario());

		}
		
		for (TwSaldoUtilizado saldoUtilizadoDto : listaSaldoUtilizado) {
			
			TwSaldoUtilizadoDto saldo = new TwSaldoUtilizadoDto();
			
			saldo.setnIdVenta(saldoUtilizadoDto.getnIdVenta());
			saldo.setdFecha(DateTimeUtil.formatearFechaHoraMx(saldoUtilizadoDto.getdFecha()));
			saldo.setnEstatus(saldoUtilizadoDto.getnEstatus());
			saldo.setnIdCaja(saldoUtilizadoDto.getnIdCaja());
			saldo.setnIdUsuario(saldoUtilizadoDto.getnIdUsuario());
			saldo.setnSaldoUtilizado(saldoUtilizadoDto.getnSaldoUtilizado());
			saldo.setnSaldoTotal(saldoUtilizadoDto.getnSaldoTotal());
			saldo.setnIdVentaUtilizado(saldoUtilizadoDto.getnIdVentaUtilizado());
			
			listaTwSaldoUtilizadoDto.add(saldo);
			
			
			totalSaldoUsado = totalSaldoUsado.add(DateTimeUtil.truncarDosDecimales(saldoUtilizadoDto.getnSaldoUtilizado())  );
			
		}
		
		
		

		reporteVenta.setSubTotal(DateTimeUtil.truncarDosDecimales(subtotal));
		reporteVenta.setIvaTotal(DateTimeUtil.truncarDosDecimales(iva));
		reporteVenta.setTotal(DateTimeUtil.truncarDosDecimales(subtotal));
		saldoFinalSaldo= DateTimeUtil.truncarDosDecimales( subtotal.subtract(totalSaldoUsado));
		totalSaldoUsado=DateTimeUtil.truncarDosDecimales(totalSaldoUsado);
		

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

		BigDecimal subtotal = BigDecimal.ZERO;
		BigDecimal iva =  BigDecimal.ZERO;
		BigDecimal totalAbonos= BigDecimal.ZERO;
		int totalProductosEntrga=0;
		
		for(TwAbono twAbono: listaAbonos) {
			
			totalAbonos= totalAbonos.add(twAbono.getnAbono())  ;
			
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
			reporte.setPrecioUnitario(DateTimeUtil.truncarDosDecimales(twVentaProducto.getnTotalUnitario()) );
			reporte.setImporte(DateTimeUtil.truncarDosDecimales(twVentaProducto.getnTotalPartida()));
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

			subtotal = subtotal.add(twVentaProducto.getnPrecioPartida());
			iva = iva.add(twVentaProducto.getnIvaPartida());

		}

		reporteVenta.setSubTotal(DateTimeUtil.truncarDosDecimales(subtotal));
		reporteVenta.setIvaTotal(DateTimeUtil.truncarDosDecimales(iva));
		reporteVenta.setTotal(DateTimeUtil.truncarDosDecimales(subtotal.add(iva)));
		reporteVenta.setTotalEntrega(totalProductosEntrga);

		return reporteService.generaVentaAlmacenPDF(reporteVenta, listaProducto, totalAbonos);
	}
	
	@Override
	public byte[] getVentaPedidoPDF(Long nIdVentaPedido) {
		
		List<TwVentasProducto> listaProductos = ventasProductoRepository.findBynIdVenta(nIdVentaPedido);
		List<TrVentaCobro> listaVentaCobro= trVentaCobroRepository.findBynIdVenta(nIdVentaPedido);
		TwVenta twVenta=ventasRepository.findBynId(nIdVentaPedido);		
		TcDatosFactura tcDatosFactura =tcDatosFacturaRepository.getById(twVenta.getTcCliente().getnIdDatoFactura());
		BigDecimal subtotal = BigDecimal.ZERO;
		BigDecimal iva = BigDecimal.ZERO;
		BigDecimal abonos=BigDecimal.ZERO;
		
		
		ReporteVentaDto reporteVenta = new ReporteVentaDto();
		utils util=new utils();
		
		
		for (int i = 0; i < listaVentaCobro.size(); i++) {
			
			abonos = abonos.add(listaVentaCobro.get(i).getnMonto());
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
		reporteVenta.setAnticipo( DateTimeUtil.truncarDosDecimales(abonos)  );
		reporteVenta.setDescuento(listaProductos.get(0).getTwVenta().getDescuento() );
		reporteVenta.setNombreVendedor(listaProductos.get(0).getTcUsuario().getsNombreUsuario());
		
		
		List<ReporteVentaDto> listaProducto = new ArrayList<ReporteVentaDto>();
		
	
		
		
		for (TwVentasProducto twVentaProducto : listaProductos) {
			
			ReporteVentaDto reporte = new ReporteVentaDto();
			
			reporte.setCantidad(twVentaProducto.getnCantidad());
			reporte.setNoIdentificacion(twVentaProducto.getTcProducto().getnId());
			if(twVentaProducto.getnIdDescuento() != null && twVentaProducto.getnIdDescuento()>0) {
				reporte.setNombreProducto(twVentaProducto.getTcProducto().getsProducto()+" - dto");
				
			}
			else {
				reporte.setNombreProducto(twVentaProducto.getTcProducto().getsProducto());
				
			}
		
			reporte.setClaveSat(twVentaProducto.getTcProducto().getTcClavesat().getsClavesat()+'-'+twVentaProducto.getTcProducto().getTcClavesat().getsDescripcion());
			reporte.setPrecioUnitario(DateTimeUtil.truncarDosDecimales(twVentaProducto.getnTotalUnitario()));
			reporte.setImporte(DateTimeUtil.truncarDosDecimales(twVentaProducto.getnTotalPartida()));
			
			listaProducto.add(reporte);
			
			subtotal = DateTimeUtil.truncarDosDecimales(subtotal.add(twVentaProducto.getnPrecioPartida()));
				iva = DateTimeUtil.truncarDosDecimales(iva.add(twVentaProducto.getnIvaPartida()));
			
		}
		
		reporteVenta.setSubTotal(DateTimeUtil.truncarDosDecimales(subtotal));
		reporteVenta.setIvaTotal(DateTimeUtil.truncarDosDecimales(iva));
		reporteVenta.setTotal(DateTimeUtil.truncarDosDecimales(subtotal.add(iva)));
		
	    if(reporteVenta.getTotal().compareTo(BigDecimal.ZERO) == 0) {
			
			reporteVenta.setTotal(BigDecimal.ZERO);
			
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
		reporteVenta.setDescuento(DateTimeUtil.truncarDosDecimales(listaProductos.get(0).getTwVenta().getDescuento() ));
		
		

		List<ReporteVentaDto> listaProducto = new ArrayList<ReporteVentaDto>();

		BigDecimal subtotal = BigDecimal.ZERO;
		BigDecimal iva = BigDecimal.ZERO;
		

		for (TwVentasProducto twVentaProducto : listaProductos) {
		

			subtotal = subtotal.add(twVentaProducto.getnPrecioPartida());
			iva = iva.add(twVentaProducto.getnIvaPartida());

		}

		reporteVenta.setSubTotal(DateTimeUtil.truncarDosDecimales(subtotal));
		reporteVenta.setIvaTotal(DateTimeUtil.truncarDosDecimales(iva));
		reporteVenta.setTotal(DateTimeUtil.truncarDosDecimales(subtotal.add(iva)));
		
		if(reporteVenta.getTotal().compareTo(BigDecimal.ZERO) == 0) {
			reporteVenta.setTotal(BigDecimal.ZERO);
			
		}
		
		List<TwAbono> listaAbonos =abonoVentaIdRepository.findBynIdVenta(nIdVenta);
		
		List<AbonosDto> listaAbonosDto=new ArrayList<AbonosDto>();
		
	
		
		BigDecimal abonos=BigDecimal.ZERO;
		
	
		
		for(TwAbono twAbono : listaAbonos) {
			
			AbonosDto abono = new AbonosDto();
			
			abono.setId(twAbono.getnId());
			abono.setAbono(DateTimeUtil.truncarDosDecimales(twAbono.getnAbono()));
			abono.setFecha(DateTimeUtil.formatearFechaHoraMx(twAbono.getdFecha()));
			abono.setFormaPago(twAbono.getTcFormapago().getsDescripcion());
			abono.setUsuario(twAbono.getTcUsuario().getsNombreUsuario());
			
			listaAbonosDto.add(abono);
			
			abonos = abonos.add(twAbono.getnAbono());
			
		}
		
		abonos=DateTimeUtil.truncarDosDecimales(abonos);
		

		return reporteService.generaAbonoVentaPDF(reporteVenta, listaAbonosDto, abonos);
	}
	
	@Override
	public byte[] getAbonoVentaIdClientePDF(Long nIdCliente) {
	
		
		TcCliente cliente=clientesRepository.getById(nIdCliente);
		
		utils util=new utils();
		
		 List<TvVentaDetalle> listaVentaDetalleCredito =tvVentaDetalleRepository.consultaVentaDetalleId(nIdCliente, 1);
		List<ReporteAbonoVentaCreditoDto> listaReporteVentaAbomo = new ArrayList<ReporteAbonoVentaCreditoDto>();
		TcCliente tcCliente=clientesRepository.getById(nIdCliente);
		TcDatosFactura tcDatosFactura =tcDatosFacturaRepository.getById(tcCliente.getnIdDatoFactura());
	
	           BigDecimal totalGeneral=BigDecimal.ZERO;
	           BigDecimal descuento=BigDecimal.ZERO;
	           BigDecimal totalAbonos=BigDecimal.ZERO;
		
			for (TvVentaDetalle listaVentaDetalle : listaVentaDetalleCredito) {
				ReporteAbonoVentaCreditoDto ventaAbomo = new ReporteAbonoVentaCreditoDto();

				ventaAbomo.setIdCliente(listaVentaDetalle.getnIdCliente());
				ventaAbomo.setIdVenta(listaVentaDetalle.getnId());
				ventaAbomo.setFolioVenta(listaVentaDetalle.getsFolioVenta());
				ventaAbomo.setFechaVenta(DateTimeUtil.formatearFechaHoraMx(listaVentaDetalle.getdFechaVenta()));
				ventaAbomo.setFechaInicioCredito(DateTimeUtil.formatearFechaHoraMx(listaVentaDetalle.getdFechaInicioCredito()));
				ventaAbomo.setFechaTerminoCredito(DateTimeUtil.formatearFechaHoraMx(listaVentaDetalle.getdFechaTerminoCredito()));
				ventaAbomo.setTotalVenta(DateTimeUtil.truncarDosDecimales(listaVentaDetalle.getnTotalVenta()));
				ventaAbomo.setTotalAbono(DateTimeUtil.truncarDosDecimales(listaVentaDetalle.getnTotalAbono()));
				ventaAbomo.setSaldoTotal(DateTimeUtil.truncarDosDecimales(listaVentaDetalle.getnSaldoTotal()));
				ventaAbomo.setAvancePago(listaVentaDetalle.getnAvancePago());
				ventaAbomo.setDescuento(DateTimeUtil.truncarDosDecimales(listaVentaDetalle.getDescuento()));
				totalGeneral = totalGeneral.add(listaVentaDetalle.getnTotalVenta());
				descuento = descuento.add(listaVentaDetalle.getDescuento());
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
					abono.setAbono(DateTimeUtil.truncarDosDecimales(abonosDto.getnAbono()));
					abono.setFormaPago(abonosDto.getTcFormapago().getsDescripcion());
					abono.setFecha(DateTimeUtil.formatearFechaHoraMx(abonosDto.getdFecha()));
					abono.setUsuario(abonosDto.getTcUsuario().getsNombreUsuario());
					listaAbonosDto.add(abono);
					totalAbonos=totalAbonos.add(abonosDto.getnAbono());

				}
				
				totalGeneral=DateTimeUtil.truncarDosDecimales(totalGeneral);
				descuento=DateTimeUtil.truncarDosDecimales(descuento);
				totalAbonos=DateTimeUtil.truncarDosDecimales(totalAbonos);
				
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
		reporteVenta.setTotal(DateTimeUtil.truncarDosDecimales(totalGeneral));
		reporteVenta.setDescuento(DateTimeUtil.truncarDosDecimales(descuento));
		reporteVenta.setAbonos(DateTimeUtil.truncarDosDecimales(totalAbonos));
	   
				
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
	         
		
			for (TwPedidoProducto twPedidoProducto : listaPedioPedidoProducto) {
				PedidoProductoDto pedidoProductoDto= new PedidoProductoDto();
				

				pedidoProductoDto.setNoParte(twPedidoProducto.getTcProducto().getsNoParte());
				pedidoProductoDto.setProducto(twPedidoProducto.getTcProducto().getsProducto());
				pedidoProductoDto.setCantidad(twPedidoProducto.getnCantidadPedida());
				pedidoProductoDto.setFechaPedido(twPedidoProducto.getdFechaPedido());
				pedidoProductoDto.setProveedor(twPedidoProducto.getTcProveedore().getsRazonSocial());
				
				
				if(twPedidoProducto.getdFechaPedido()!=null) {
					pedidoProductoDto.setFechaRecibida(twPedidoProducto.getdFechaPedido());
				}
				else
				{
					pedidoProductoDto.setFechaRecibida(null);
				}
				pedidoProductoDto.setPrecio(twPedidoProducto.getTcProducto().getnPrecio());
						
				pedidoProductoDto.setEstatus(twPedidoProducto.getTcEstatusPedidoProducto().getsEstatus());				
				// SE AGREGA EL OBJETO A LA LISTA 
				System.err.println(pedidoProductoDto);
				
				listaPedidoProducto.add(pedidoProductoDto);

			}
		
			System.err.println(listaPedidoProducto);
		
				
		return reporteService.generaPedidoPDF(twPedido, listaPedidoProducto);
	}
	
	public byte[] getReporteCaja(Long nIdCaja) {
		
		// Totales generales
		BigDecimal totalIngresosVenta = BigDecimal.ZERO;
		BigDecimal totalIngresosAbono = BigDecimal.ZERO;
		BigDecimal totalVentaCaja = BigDecimal.ZERO;
		Integer totalEntregadas = 0;
		Integer totalNoEntregadas = 0;
		Integer totalEntegasParciales = 0;
		BigDecimal totalReitegros = BigDecimal.ZERO;
		BigDecimal totalGastos = BigDecimal.ZERO;
		BigDecimal totalCredito = BigDecimal.ZERO;
		BigDecimal totalVenta = BigDecimal.ZERO;

		// Variables de contado
		BigDecimal efectivoContado = BigDecimal.ZERO;
		BigDecimal chequeContado = BigDecimal.ZERO;
		BigDecimal transferenciaContado = BigDecimal.ZERO;
		BigDecimal tarjetaCreditoContado = BigDecimal.ZERO;
		BigDecimal tarjetaDebitoContado = BigDecimal.ZERO;
		BigDecimal CondonacionContado = BigDecimal.ZERO;

		// Variables de crédito
		BigDecimal efectivoAbono = BigDecimal.ZERO;
		BigDecimal chequeAbono = BigDecimal.ZERO;
		BigDecimal transferenciaAbono = BigDecimal.ZERO;
		BigDecimal tarjetaCreditoAbono = BigDecimal.ZERO;
		BigDecimal tarjetaDebitoAbono = BigDecimal.ZERO;
		BigDecimal CondonacionAbono = BigDecimal.ZERO;

		BigDecimal totalCencela = BigDecimal.ZERO;
		BigDecimal saldoFavor = BigDecimal.ZERO;
		BigDecimal totalDescuento = BigDecimal.ZERO;

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
		totalReitegros=totalReitegros==null?BigDecimal.ZERO:totalReitegros;
		
		for (int i = 0; i < listaTwGastos.size(); i++) {			
			
			 totalGastos=totalGastos.add(listaTwGastos.get(i).getnMonto());
			 GastosDto gastoDto=new GastosDto();		 
			 gastoDto.setnId(listaTwGastos.get(i).getnId());
			 gastoDto.setFecha(DateTimeUtil.formatearFechaHoraMx(listaTwGastos.get(i).getdFecha()));
			 gastoDto.setDescripcion(listaTwGastos.get(i).getsDescripcion());
			 gastoDto.setGasto(listaTwGastos.get(i).getTcGasto().getsGasto());
			 gastoDto.setUsuario(listaTwGastos.get(i).getTcUsuario().getsNombreUsuario());
			 gastoDto.setMonto(DateTimeUtil.truncarDosDecimales(listaTwGastos.get(i).getnMonto()) );
			 
			 auxListaGastos.add(gastoDto);
			
			
		}
		
	
		 
		for (int i = 0; i < trVentasCobro.size(); i++) {			
			totalIngresosVenta=totalIngresosVenta.add(trVentasCobro.get(i).getnMonto());				
			
			// Variables de contado POR TIPO DE PAGO
			
			if (trVentasCobro.get(i).getTwVenta().getnTipoPago() == 0L && trVentasCobro.get(i).getnIdFormaPago() == 1L) {
			    efectivoContado = efectivoContado.add(trVentasCobro.get(i).getnMonto());
			}

			if (trVentasCobro.get(i).getTwVenta().getnTipoPago() == 0L && trVentasCobro.get(i).getnIdFormaPago() == 2L) {
			    chequeContado = chequeContado.add(trVentasCobro.get(i).getnMonto());
			}

			if (trVentasCobro.get(i).getTwVenta().getnTipoPago() == 0L && trVentasCobro.get(i).getnIdFormaPago() == 3L) {
			    transferenciaContado = transferenciaContado.add(trVentasCobro.get(i).getnMonto());
			}

			if (trVentasCobro.get(i).getTwVenta().getnTipoPago() == 0L && trVentasCobro.get(i).getnIdFormaPago() == 4L) {
			    tarjetaCreditoContado = tarjetaCreditoContado.add(trVentasCobro.get(i).getnMonto());
			}

			if (trVentasCobro.get(i).getTwVenta().getnTipoPago() == 0L && trVentasCobro.get(i).getnIdFormaPago() == 18L) {
			    tarjetaDebitoContado = tarjetaDebitoContado.add(trVentasCobro.get(i).getnMonto());
			}

			if (trVentasCobro.get(i).getTwVenta().getnTipoPago() == 0L && trVentasCobro.get(i).getnIdFormaPago() == 11L) {
			    CondonacionAbono = CondonacionAbono.add(trVentasCobro.get(i).getnMonto());
			}
					
		}
		
		for (int i = 0; i < twAbono.size(); i++) {			
			totalIngresosAbono=totalIngresosAbono.add(twAbono.get(i).getnAbono()) ;
			AbonoDto abonosDto=new AbonoDto();
			abonosDto.setAbono(DateTimeUtil.truncarDosDecimales(twAbono.get(i).getnAbono()));
			abonosDto.setFechaAbono(DateTimeUtil.formatearFechaHoraMx(twAbono.get(i).getdFecha()));
			abonosDto.setIdVenta(twAbono.get(i).getnIdVenta());
			abonosDto.setFormaPago(twAbono.get(i).getTcFormapago().getsDescripcion());
			abonosDto.setUsuario(twAbono.get(i).getTcUsuario().getsNombreUsuario());
			abonosDto.setFechaVenta(DateTimeUtil.formatearFechaHoraMx(twAbono.get(i).getTwVenta().getdFechaVenta()));
			
			auxListaAbonos.add(abonosDto);
			
			//COBROS POR TIPO DE PAGO DE ABONOS
			if (twAbono.get(i).getTcFormapago().getnId() == 1L) {	
			    efectivoAbono = efectivoAbono.add(twAbono.get(i).getnAbono());				
			}
			if (twAbono.get(i).getTcFormapago().getnId() == 2L) {
			    chequeAbono = chequeAbono.add(twAbono.get(i).getnAbono());				
			}
			if (twAbono.get(i).getTcFormapago().getnId() == 3L) {				
			    transferenciaAbono = transferenciaAbono.add(twAbono.get(i).getnAbono());				
			}
			if (twAbono.get(i).getTcFormapago().getnId() == 4L) {				
			    tarjetaCreditoAbono = tarjetaCreditoAbono.add(twAbono.get(i).getnAbono());				
			}
			if (twAbono.get(i).getTcFormapago().getnId() == 18L) {				
			    tarjetaDebitoAbono = tarjetaDebitoAbono.add(twAbono.get(i).getnAbono());				
			}
			if (twAbono.get(i).getTcFormapago().getnId() == 11L) {				
			    CondonacionAbono = CondonacionAbono.add(twAbono.get(i).getnAbono());				
			}
			
		}
		
		
		for (int i = 0; i < trReporteDetalleVentas.size(); i++) {			
			totalVentaCaja=totalVentaCaja.add(trReporteDetalleVentas.get(i).getnTotalVenta()) ;	
			
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

						formapago += "EF:" + DateTimeUtil.truncarDosDecimales(listaTrVentaCobro.get(j).getnMonto()) + "/";
					}
					if (listaTrVentaCobro.get(j).getTcFormapago().getnId() == 2) {

						formapago += "CH:" + DateTimeUtil.truncarDosDecimales(listaTrVentaCobro.get(j).getnMonto()) + "/";
					}
					if (listaTrVentaCobro.get(j).getTcFormapago().getnId() == 3) {

						formapago += "TE:" + DateTimeUtil.truncarDosDecimales(listaTrVentaCobro.get(j).getnMonto()) + "/";
					}
					if (listaTrVentaCobro.get(j).getTcFormapago().getnId() == 4) {

						formapago += "TC:" + DateTimeUtil.truncarDosDecimales(listaTrVentaCobro.get(j).getnMonto()) + "/";
					}
					if (listaTrVentaCobro.get(j).getTcFormapago().getnId() == 11) {

						formapago += "CO:" + DateTimeUtil.truncarDosDecimales(listaTrVentaCobro.get(j).getnMonto()) + "/";
					}
					if (listaTrVentaCobro.get(j).getTcFormapago().getnId() == 18) {

						formapago += "TD:" + DateTimeUtil.truncarDosDecimales(listaTrVentaCobro.get(j).getnMonto()) + "/";
					}
					if (listaTrVentaCobro.get(j).getTcFormapago().getnId() == 20) {

						formapago += "AN:" + DateTimeUtil.truncarDosDecimales(listaTrVentaCobro.get(j).getnMonto()) + "/";
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
			cancelaVentaDto.setFechaVenta(DateTimeUtil.formatearFechaHoraMx(listaTwVentaProductoCancela.get(i).getTwVenta().getdFechaVenta()));
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
				saldoFavor=saldoFavor.add(listaTwVentaProductoCancela.get(i).getnPrecioPartida())   ;
				
			}
			else {
				cancelaVentaDto.setSaldoFavor("NO");
				
			}
			
				totalCencela= totalCencela.add(listaTwVentaProductoCancela.get(i).getnPrecioPartida());
			
			
			
			auxCancelaVenta.add(cancelaVentaDto);
			
		}
		
		for (int i = 0; i < listaVentasProducto.size(); i++) {		
			
			totalVenta=totalVenta.add(listaVentasProducto.get(i).getnTotalPartida());			
		}
		
		for (int i = 0; i < listaVentas.size(); i++) {
			
			totalDescuento=totalDescuento.add(listaVentas.get(i).getDescuento())  ;
			
		}
		
		
		
		/*LLENAOD DE OBJETO BALANCE CAJA*/
		balanceCajaDto.setCaja(caja.getnId());
		balanceCajaDto.setFechaInicioCaja(caja.getdFechaApertura());
		balanceCajaDto.setTotalIngresoVenta(DateTimeUtil.truncarDosDecimales(totalIngresosVenta));
		balanceCajaDto.setTotalIngresoAbonos(DateTimeUtil.truncarDosDecimales(totalIngresosAbono));		
		BigDecimal totalGeneralIngresos = totalIngresosVenta.add(totalIngresosAbono).subtract(totalReitegros);
		balanceCajaDto.setTotalGeneralIngresos(DateTimeUtil.truncarDosDecimales(totalGeneralIngresos));	
		balanceCajaDto.setTotalVentas(DateTimeUtil.truncarDosDecimales(totalVenta));
		balanceCajaDto.setNoVentas(trReporteDetalleVentas.size());
		balanceCajaDto.setNoAbonos(twAbono.size());
		balanceCajaDto.setUsuarioCaja(usuario.getsNombreUsuario());
		balanceCajaDto.setTotalEntregadas(totalEntregadas);
		balanceCajaDto.setTotalNoEntregadas(totalNoEntregadas);
		balanceCajaDto.setTotalEntregasParciales(totalEntegasParciales);
		balanceCajaDto.setFechaGeneraReporte(DateTimeUtil.obtenerHoraExactaDeMexico());
		balanceCajaDto.setTvReporteDetalleVenta(trReporteDetalleVentas);
		balanceCajaDto.setTvReporteCajaFormaPago(tvReporteCajaFormaPago);
		balanceCajaDto.setTotalReintegro(DateTimeUtil.truncarDosDecimales(totalReitegros));
		balanceCajaDto.setListaGastos(auxListaGastos);
		balanceCajaDto.setTotalGastos(DateTimeUtil.truncarDosDecimales(totalGastos));
		balanceCajaDto.setListaAbonos(auxListaAbonos);
		balanceCajaDto.setListaCancelados(auxCancelaVenta);
		balanceCajaDto.setTotalCredito(DateTimeUtil.truncarDosDecimales(totalCredito));
		balanceCajaDto.setTotalVenta(DateTimeUtil.truncarDosDecimales(totalVenta));	
		
		// variables de contado
		balanceCajaDto.setEfectivoContado(DateTimeUtil.truncarDosDecimales(efectivoContado));
		balanceCajaDto.setChequeContado(DateTimeUtil.truncarDosDecimales(chequeContado));
		balanceCajaDto.setTransferenciaContado(DateTimeUtil.truncarDosDecimales(transferenciaContado));
		balanceCajaDto.setTarjetaCreditoContado(DateTimeUtil.truncarDosDecimales(tarjetaCreditoContado));
		balanceCajaDto.setTarjetaDebitoContad(DateTimeUtil.truncarDosDecimales(tarjetaDebitoContado));
		balanceCajaDto.setCondonacionContado(DateTimeUtil.truncarDosDecimales(CondonacionContado));
		
		// variables de crédito
		balanceCajaDto.setEfectivoAbono(DateTimeUtil.truncarDosDecimales(efectivoAbono));
		balanceCajaDto.setChequeAbono(DateTimeUtil.truncarDosDecimales(chequeAbono));
		balanceCajaDto.setTransferenciaAbono(DateTimeUtil.truncarDosDecimales(transferenciaAbono));
		balanceCajaDto.setTarjetaCreditoAbono(DateTimeUtil.truncarDosDecimales(tarjetaCreditoAbono));
		balanceCajaDto.setTarjetaDebitoAbon(DateTimeUtil.truncarDosDecimales(tarjetaDebitoAbono));
		balanceCajaDto.setCondonacionAbono(DateTimeUtil.truncarDosDecimales(CondonacionAbono));
		balanceCajaDto.setSaldosFavor(DateTimeUtil.truncarDosDecimales(saldoFavor));
		balanceCajaDto.setTotalCancelado(DateTimeUtil.truncarDosDecimales(totalCencela));
		balanceCajaDto.setTotalDescuento(DateTimeUtil.truncarDosDecimales(totalDescuento));
		
		 
		System.err.println(auxListaGastos.size());
		System.err.println(auxListaAbonos.size());
		System.err.println(auxCancelaVenta.size());

		

		return reporteService.generarReporteCajaPDF(balanceCajaDto);
	}

	@Override
	public byte[] getDocumento(Long nIdVenta, TipoDoc TipoDoc) {
		try {
			if (TipoDoc.equals(com.refacFabela.enums.TipoDoc.XML_ACUSE_CANCELACION)) {
				byte[] local = readDocumentoLocal(pathAcuseCancelacionVenta(nIdVenta, "xml"));
				if (local != null) {
					return local;
				}
				byte[] remoto = obtenerAcuseCancelacionDesdeAuditoria(nIdVenta);
				if (remoto != null && remoto.length > 0) {
					writeDocumentoLocal(pathAcuseCancelacionVenta(nIdVenta, "xml"), remoto);
				}
				return remoto;
			}

			if (TipoDoc.equals(com.refacFabela.enums.TipoDoc.PDF_ACUSE_CANCELACION)) {
				byte[] local = readDocumentoLocal(pathAcuseCancelacionVenta(nIdVenta, "pdf"));
				if (local != null) {
					return local;
				}
				byte[] remoto = obtenerPdfAcuseCancelacionDesdeAuditoria(nIdVenta);
				if (remoto != null && remoto.length > 0) {
					writeDocumentoLocal(pathAcuseCancelacionVenta(nIdVenta, "pdf"), remoto);
				}
				return remoto;
			}

			if (TipoDoc.equals(com.refacFabela.enums.TipoDoc.ZIP_COMPLEMENTOS_PAGO)) {
				return obtenerZipComplementosPago(nIdVenta);
			}

			if (nIdVenta == null || nIdVenta.longValue() <= 0L) {
				return null;
			}

			String extension;
			if (TipoDoc.equals(com.refacFabela.enums.TipoDoc.PDF_FACTURA)) {
				extension = "pdf";
			} else if (TipoDoc.equals(com.refacFabela.enums.TipoDoc.XML_FACTURA)) {
				extension = "xml";
			} else {
				return null;
			}

			byte[] documentoDirectoVenta = obtenerDocumentoDirectoPorVentaId(nIdVenta, extension);
			if (documentoDirectoVenta != null && documentoDirectoVenta.length > 0) {
				return documentoDirectoVenta;
			}

			List<String> rutasCandidatas = resolveRutasFacturaCandidatas(nIdVenta, TipoDoc);
			for (String ruta : rutasCandidatas) {
				List<Path> documentosFactura = obtenerDocumentosFacturaVenta(ruta, nIdVenta, extension);
				if (documentosFactura.isEmpty()) {
					continue;
				}
				if (documentosFactura.size() == 1) {
					return Files.readAllBytes(documentosFactura.get(0));
				}
				return zipDocumentosFactura(documentosFactura, nIdVenta, extension);
			}

			byte[] porUuid = obtenerDocumentoFacturaPorUuidEnRutas(rutasCandidatas, nIdVenta, extension);
			if (porUuid != null && porUuid.length > 0) {
				return porUuid;
			}

			Path pathFactura = pathFacturaVenta(nIdVenta, TipoDoc);
			if (pathFactura != null) {
				byte[] localMirror = readDocumentoLocal(pathFactura);
				if (localMirror != null) {
					return localMirror;
				}
			}

			logger.warn("No se encontro comprobante de factura para venta {} tipo {}. Rutas candidatas: {}",
					nIdVenta, TipoDoc, rutasCandidatas);

			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private byte[] obtenerDocumentoDirectoPorVentaId(Long nIdVenta, String extension) {
		if (nIdVenta == null || extension == null || extension.trim().isEmpty()) {
			return null;
		}

		String normalizedExt = extension.toLowerCase();
		String rutaRaizObligatoria = ensureTrailingSlash(RUTA_FACTURAS_OBLIGATORIA);
		String base = rutaRaizObligatoria + ("xml".equalsIgnoreCase(normalizedExt) ? "xml/" : "pdf/");
		Path path = Paths.get(base + nIdVenta + "." + normalizedExt);
		byte[] direct = readDocumentoLocal(path);
		if (direct != null && direct.length > 0) {
			return direct;
		}

		List<Path> documentos = obtenerDocumentosFacturaVenta(base, nIdVenta, normalizedExt);
		if (!documentos.isEmpty()) {
			try {
				if (documentos.size() == 1) {
					return Files.readAllBytes(documentos.get(0));
				}
				return zipDocumentosFactura(documentos, nIdVenta, normalizedExt);
			} catch (IOException e) {
				return null;
			}
		}

		Path recursivo = buscarArchivoPorVentaIdEnRuta(base, nIdVenta, normalizedExt);
		if (recursivo != null) {
			try {
				return Files.readAllBytes(recursivo);
			} catch (IOException ignored) {
				return null;
			}
		}

		Path recursivoRaizObligatoria = buscarArchivoPorVentaIdEnRuta(rutaRaizObligatoria, nIdVenta, normalizedExt);
		if (recursivoRaizObligatoria != null) {
			try {
				return Files.readAllBytes(recursivoRaizObligatoria);
			} catch (IOException ignored) {
				return null;
			}
		}

		String rutaRaizLegacy = datosFacturaStorageResolver.resolveRutaRaizLegacy(null);
		Path recursivoRaizLegacy = buscarArchivoPorVentaIdEnRuta(rutaRaizLegacy, nIdVenta, normalizedExt);
		if (recursivoRaizLegacy != null) {
			try {
				return Files.readAllBytes(recursivoRaizLegacy);
			} catch (IOException ignored) {
				return null;
			}
		}

		return null;
	}

	private Path buscarArchivoPorVentaIdEnRuta(String ruta, Long nIdVenta, String extension) {
		if (ruta == null || ruta.trim().isEmpty() || nIdVenta == null || extension == null || extension.trim().isEmpty()) {
			return null;
		}

		Path directorio = Paths.get(ruta);
		if (!Files.exists(directorio) || !Files.isDirectory(directorio)) {
			return null;
		}

		String ventaToken = String.valueOf(nIdVenta).toLowerCase();
		String extLower = "." + extension.toLowerCase();
		String nombrePrincipal = ventaToken + extLower;

		try (Stream<Path> stream = Files.walk(directorio, 4)) {
			return stream
					.filter(Files::isRegularFile)
					.filter(path -> {
						String fileName = path.getFileName().toString().toLowerCase();
						if (!fileName.endsWith(extLower)) {
							return false;
						}
						return fileName.equals(nombrePrincipal)
								|| fileName.startsWith(ventaToken + "_")
								|| fileName.startsWith(ventaToken + "_p");
					})
					.sorted((a, b) -> {
						try {
							return Files.getLastModifiedTime(b).compareTo(Files.getLastModifiedTime(a));
						} catch (IOException ex) {
							return 0;
						}
					})
					.findFirst()
					.orElse(null);
		} catch (IOException e) {
			logger.warn("No se pudo recorrer ruta {} para venta {} con extension {}: {}", ruta, nIdVenta,
					extension, e.getMessage());
			return null;
		}
	}

	private List<String> resolveRutasFacturaCandidatas(Long nIdVenta, TipoDoc tipoDoc) {
		Set<String> rutas = new LinkedHashSet<String>();
		boolean esXml = TipoDoc.XML_FACTURA.equals(tipoDoc);
		String rutaRaizObligatoria = ensureTrailingSlash(RUTA_FACTURAS_OBLIGATORIA);
		String rutaObligatoria = rutaRaizObligatoria + (esXml ? "xml/" : "pdf/");
		rutas.add(rutaObligatoria);
		rutas.add(rutaRaizObligatoria);

		appendRutaFactura(rutas, esXml, null, false);

		if (nIdVenta != null) {
			try {
				List<TwFacturacion> facturas = facturaRepository.findActivasByVenta(nIdVenta);
				if (facturas != null) {
					for (TwFacturacion facturacion : facturas) {
						if (facturacion == null || facturacion.getnIdDatoFactura() == null) {
							continue;
						}
						TcDatosFactura datosFactura = tcDatosFacturaRepository.findById(facturacion.getnIdDatoFactura())
								.orElse(null);
						appendRutaFactura(rutas, esXml, datosFactura, false);
						appendRutaFactura(rutas, esXml, datosFactura, true);
					}
				}
			} catch (Exception ignored) {
				// Si BD falla, se mantiene la ruta unificada para servir archivos locales.
			}

			try {
				TwVenta venta = ventasRepository.findBynId(nIdVenta);
				if (venta != null && venta.getTcCliente() != null && venta.getTcCliente().getnIdDatoFactura() != null) {
					TcDatosFactura datosCliente = tcDatosFacturaRepository.findById(venta.getTcCliente().getnIdDatoFactura())
							.orElse(null);
					appendRutaFactura(rutas, esXml, datosCliente, false);
					appendRutaFactura(rutas, esXml, datosCliente, true);
				}
			} catch (Exception ignored) {
				// Si BD falla, se mantiene la ruta unificada para servir archivos locales.
			}
		}

		return new ArrayList<String>(rutas);
	}

	private void appendRutaFactura(Set<String> rutas, boolean esXml, TcDatosFactura datosFactura, boolean legacy) {
		String ruta = null;
		if (legacy) {
			ruta = esXml ? datosFacturaStorageResolver.resolveRutaXmlLegacy(datosFactura)
					: datosFacturaStorageResolver.resolveRutaPdfLegacy(datosFactura);
		} else {
			ruta = esXml ? datosFacturaStorageResolver.resolveRutaXml(datosFactura)
					: datosFacturaStorageResolver.resolveRutaPdf(datosFactura);
		}
		if (ruta != null && !ruta.trim().isEmpty()) {
			rutas.add(ruta.trim());
		}
	}

	private byte[] obtenerDocumentoFacturaPorUuidEnRutas(List<String> rutas, Long nIdVenta, String extension) {
		if (rutas == null || rutas.isEmpty() || nIdVenta == null || extension == null || extension.trim().isEmpty()) {
			return null;
		}

		Set<String> uuidTokens = new LinkedHashSet<String>();
		try {
			List<TwFacturacion> facturas = facturaRepository.findByVenta(nIdVenta);
			if (facturas != null) {
				for (TwFacturacion facturacion : facturas) {
					if (facturacion == null || facturacion.getsUuid() == null || facturacion.getsUuid().trim().isEmpty()) {
						continue;
					}
					uuidTokens.add(facturacion.getsUuid().trim());
				}
			}
		} catch (Exception ignored) {
			return null;
		}

		if (uuidTokens.isEmpty()) {
			return null;
		}

		for (String ruta : rutas) {
			if (ruta == null || ruta.trim().isEmpty()) {
				continue;
			}
			Path encontrado = buscarArchivoPorTokensEnRuta(ruta, extension, uuidTokens);
			if (encontrado == null) {
				continue;
			}
			try {
				return Files.readAllBytes(encontrado);
			} catch (IOException ignored) {
				// Continua con el siguiente candidato.
			}
		}

		logger.warn("No se encontro documento por UUID en rutas candidatas para venta {} extension {}. UUIDs evaluados: {} Rutas: {}",
				nIdVenta, extension, uuidTokens, rutas);

		return null;
	}

	private Path buscarArchivoPorTokensEnRuta(String ruta, String extension, Set<String> tokens) {
		if (ruta == null || ruta.trim().isEmpty() || extension == null || extension.trim().isEmpty()
				|| tokens == null || tokens.isEmpty()) {
			return null;
		}

		Path directorio = Paths.get(ruta);
		if (!Files.exists(directorio) || !Files.isDirectory(directorio)) {
			return null;
		}

		String extLower = "." + extension.toLowerCase();
		Set<String> tokensLower = new HashSet<String>();
		for (String token : tokens) {
			if (token != null && !token.trim().isEmpty()) {
				tokensLower.add(token.trim().toLowerCase());
			}
		}

		try (Stream<Path> stream = Files.walk(directorio, 3)) {
			return stream
					.filter(Files::isRegularFile)
					.filter(path -> {
						String fileName = path.getFileName().toString().toLowerCase();
						if (!fileName.endsWith(extLower)) {
							return false;
						}
						for (String token : tokensLower) {
							if (fileName.equals(token + extLower)
									|| fileName.startsWith(token + "_")
									|| fileName.contains("_" + token + "_")) {
								return true;
							}
						}
						return false;
					})
					.sorted((a, b) -> {
						try {
							return Files.getLastModifiedTime(b).compareTo(Files.getLastModifiedTime(a));
						} catch (IOException ex) {
							return 0;
						}
					})
					.findFirst()
					.orElse(null);
		} catch (IOException e) {
			return null;
		}
	}

	private List<Path> obtenerDocumentosFacturaVenta(String ruta, Long nIdVenta, String extension) {
		if (ruta == null || ruta.trim().isEmpty() || nIdVenta == null || extension == null || extension.trim().isEmpty()) {
			return Collections.emptyList();
		}

		Path directorio = Paths.get(ruta);
		if (!Files.exists(directorio) || !Files.isDirectory(directorio)) {
			return Collections.emptyList();
		}

		String ventaToken = String.valueOf(nIdVenta);
		String ventaTokenLower = ventaToken.toLowerCase();
		String normalizedExt = extension.toLowerCase();
		String nombrePrincipal = ventaTokenLower + "." + normalizedExt;
		String prefijoParcial = ventaTokenLower + "_p";
		String prefijoVersion = ventaTokenLower + "_";
		String sufijoExtension = "." + normalizedExt;

		List<Path> documentos = new ArrayList<Path>();
		try (Stream<Path> stream = Files.walk(directorio, 4)) {
			stream.filter(Files::isRegularFile)
					.filter(path -> {
						String fileName = path.getFileName().toString().toLowerCase();
						return fileName.equals(nombrePrincipal)
								|| (fileName.startsWith(prefijoParcial) && fileName.endsWith(sufijoExtension))
								|| (fileName.startsWith(prefijoVersion) && fileName.endsWith(sufijoExtension));
					})
					.sorted(Comparator.comparingInt(path ->
						extraerIndiceParcial(path.getFileName().toString(), ventaTokenLower, normalizedExt)))
					.forEach(documentos::add);
		} catch (IOException e) {
			logger.warn("No se pudieron listar documentos de factura en {} para venta {} extension {}: {}", ruta,
					nIdVenta, extension, e.getMessage());
			return Collections.emptyList();
		}

		return documentos;
	}

	private int extraerIndiceParcial(String fileName, String ventaToken, String extension) {
		if (fileName == null || ventaToken == null || extension == null) {
			return Integer.MAX_VALUE;
		}

		String fileNameLower = fileName.toLowerCase();
		String ventaTokenLower = ventaToken.toLowerCase();
		String extensionLower = extension.toLowerCase();

		String nombrePrincipal = ventaTokenLower + "." + extensionLower;
		if (fileNameLower.equals(nombrePrincipal)) {
			return 1;
		}

		String prefijoParcial = ventaTokenLower + "_p";
		String sufijoExtension = "." + extensionLower;
		if (!fileNameLower.startsWith(prefijoParcial) || !fileNameLower.endsWith(sufijoExtension)) {
			return Integer.MAX_VALUE;
		}

		String indice = fileNameLower.substring(prefijoParcial.length(), fileNameLower.length() - sufijoExtension.length());
		try {
			return Integer.parseInt(indice);
		} catch (NumberFormatException ex) {
			return Integer.MAX_VALUE;
		}
	}

	private byte[] zipDocumentosFactura(List<Path> documentos, Long nIdVenta, String extension) {
		if (documentos == null || documentos.isEmpty()) {
			return null;
		}

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ZipOutputStream zipOutputStream = new ZipOutputStream(baos)) {
			boolean hasEntries = false;
			for (Path path : documentos) {
				if (path == null || !Files.exists(path) || !Files.isRegularFile(path)) {
					continue;
				}
				byte[] data = Files.readAllBytes(path);
				if (data == null || data.length == 0) {
					continue;
				}
				agregarEntradaZip(zipOutputStream, path.getFileName().toString(), data);
				hasEntries = true;
			}
			zipOutputStream.finish();
			return hasEntries ? baos.toByteArray() : null;
		} catch (IOException e) {
			return null;
		}
	}

	private byte[] obtenerZipComplementosPago(Long nIdVenta) {
		if (nIdVenta == null) {
			return null;
		}

		List<com.refacFabela.model.TwFacturacionComplementoPago> complementos = facturacionComplementoPagoRepository.findByVenta(nIdVenta);
		if (complementos == null || complementos.isEmpty()) {
			return null;
		}

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ZipOutputStream zipOutputStream = new ZipOutputStream(baos)) {
			boolean hasEntries = false;
			for (com.refacFabela.model.TwFacturacionComplementoPago complemento : complementos) {
				String suffix = complemento.getnParcialidad() != null
						? String.format("%03d", complemento.getnParcialidad().intValue())
						: String.valueOf(complemento.getnId());
				byte[] xml = decodeBase64OrPlain(complemento.getsXmlTimbrado());
				if (xml != null && xml.length > 0) {
					agregarEntradaZip(zipOutputStream, "rep_parcialidad_" + suffix + ".xml", xml);
					hasEntries = true;
				}
				byte[] pdf = obtenerPdfComplementoDesdeAuditoria(complemento.getsCorrelationId());
				if (pdf != null && pdf.length > 0) {
					agregarEntradaZip(zipOutputStream, "rep_parcialidad_" + suffix + ".pdf", pdf);
					hasEntries = true;
				}
			}
			zipOutputStream.finish();
			return hasEntries ? baos.toByteArray() : null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public byte[] getDocumentoComplemento(Long nIdComplemento, TipoDoc TipoDoc) {
		if (nIdComplemento == null || TipoDoc == null) {
			return null;
		}

		Optional<com.refacFabela.model.TwFacturacionComplementoPago> complementoOptional = facturacionComplementoPagoRepository.findById(nIdComplemento);
		if (!complementoOptional.isPresent()) {
			return null;
		}

		com.refacFabela.model.TwFacturacionComplementoPago complemento = complementoOptional.get();
		if (TipoDoc.equals(com.refacFabela.enums.TipoDoc.XML_COMPLEMENTO_PAGO)) {
			byte[] local = readDocumentoLocal(pathComplementoDocumento(nIdComplemento, "xml"));
			if (local != null) {
				return local;
			}
			byte[] xml = decodeBase64OrPlain(complemento.getsXmlTimbrado());
			if (xml != null && xml.length > 0) {
				writeDocumentoLocal(pathComplementoDocumento(nIdComplemento, "xml"), xml);
			}
			return xml;
		}

		if (TipoDoc.equals(com.refacFabela.enums.TipoDoc.PDF_COMPLEMENTO_PAGO)) {
			byte[] local = readDocumentoLocal(pathComplementoDocumento(nIdComplemento, "pdf"));
			if (local != null) {
				return local;
			}
			byte[] pdf = obtenerPdfComplementoDesdeAuditoria(complemento.getsCorrelationId());
			if (pdf != null && pdf.length > 0) {
				writeDocumentoLocal(pathComplementoDocumento(nIdComplemento, "pdf"), pdf);
			}
			return pdf;
		}

		return null;
	}

	private byte[] obtenerAcuseCancelacionDesdeAuditoria(Long nIdVenta) {
		if (nIdVenta == null) {
			return null;
		}

		Optional<TwFacturacionPacAudit> auditoriaOptional = facturacionPacAuditRepository
				.findUltimaCancelacionExitosa(nIdVenta, "cancelacion");
		if (!auditoriaOptional.isPresent()) {
			return null;
		}

		TwFacturacionPacAudit auditoria = auditoriaOptional.get();
		String acuse = obtenerValorAuditoriaDetalle(auditoria.getnId(), "RESPONSE", "acuseBase64", "acuse",
				"xmlAcuse");
		if (acuse == null || acuse.trim().isEmpty()) {
			return null;
		}

		String trimmed = acuse.trim();
		if (trimmed.startsWith("<")) {
			return trimmed.getBytes(StandardCharsets.UTF_8);
		}

		try {
			return Base64.getDecoder().decode(trimmed);
		} catch (IllegalArgumentException e) {
			return trimmed.getBytes(StandardCharsets.UTF_8);
		}
	}

	private byte[] obtenerPdfAcuseCancelacionDesdeAuditoria(Long nIdVenta) {
		if (nIdVenta == null) {
			return null;
		}

		Optional<TwFacturacionPacAudit> auditoriaOptional = facturacionPacAuditRepository
				.findUltimaCancelacionExitosa(nIdVenta, "cancelacion");
		if (!auditoriaOptional.isPresent()) {
			return null;
		}

		TwFacturacionPacAudit auditoria = auditoriaOptional.get();
		String pdf = obtenerValorAuditoriaDetalle(auditoria.getnId(), "RESPONSE",
				"pdfAcuseBase64", "acusePdfBase64", "pdfCancelacionBase64", "pdfBase64",
				"pdfAcuse", "acusePdf", "pdfCancelacion", "pdf");
		byte[] pdfBytes = decodeBase64OrPlain(pdf);
		if (pdfBytes != null && pdfBytes.length > 0) {
			return pdfBytes;
		}

		String urlPdf = obtenerValorAuditoriaDetalle(auditoria.getnId(), "RESPONSE",
				"urlPdfAcuse", "acusePdfUrl", "urlPdfCancelacion", "pdfAcuseUrl", "pdfUrl", "urlPdf", "pdf");
		return downloadBytes(urlPdf);
	}

	private byte[] obtenerPdfComplementoDesdeAuditoria(String correlationId) {
		if (correlationId == null || correlationId.trim().isEmpty()) {
			return null;
		}

		Optional<TwFacturacionPacAudit> auditoriaOptional = facturacionPacAuditRepository.findTopByCorrelationId(correlationId);
		if (!auditoriaOptional.isPresent()) {
			return null;
		}

		TwFacturacionPacAudit auditoria = auditoriaOptional.get();
		String pdf = obtenerValorAuditoriaDetalle(auditoria.getnId(), "RESPONSE", "pdfBase64", "pdf");
		byte[] pdfBytes = decodeBase64OrPlain(pdf);
		if (pdfBytes != null && pdfBytes.length > 0) {
			return pdfBytes;
		}

		String urlPdf = obtenerValorAuditoriaDetalle(auditoria.getnId(), "RESPONSE", "urlpdf", "urlPdf");
		return downloadBytes(urlPdf);
	}

	private String obtenerValorAuditoriaDetalle(Long nIdAuditoria, String bloque, String... claves) {
		if (nIdAuditoria == null || bloque == null || claves == null || claves.length == 0) {
			return null;
		}

		List<TwFacturacionPacAuditDetalle> detalles = facturacionPacAuditDetalleRepository
				.findDetalleAuditoriaByBloque(nIdAuditoria, bloque);
		if (detalles == null || detalles.isEmpty()) {
			return null;
		}

		Map<String, String> valoresExactos = new LinkedHashMap<String, String>();
		Map<String, String> valoresPorSuffix = new LinkedHashMap<String, String>();
		for (TwFacturacionPacAuditDetalle detalle : detalles) {
			if (detalle == null || detalle.getsClave() == null) {
				continue;
			}
			String clave = detalle.getsClave();
			if (!valoresExactos.containsKey(clave)) {
				valoresExactos.put(clave, detalle.getsValor());
			}
			String suffix = extractSuffixKey(clave);
			if (suffix != null && !suffix.isEmpty() && !valoresPorSuffix.containsKey(suffix)) {
				valoresPorSuffix.put(suffix, detalle.getsValor());
			}
		}

		for (String clave : claves) {
			String valor = valoresExactos.get(clave);
			if (valor != null && !valor.trim().isEmpty()) {
				return valor;
			}

			valor = valoresPorSuffix.get(clave);
			if (valor != null && !valor.trim().isEmpty()) {
				return valor;
			}
		}
		return null;
	}

	private String extractSuffixKey(String rutaClave) {
		if (rutaClave == null) {
			return null;
		}
		String trimmed = rutaClave.trim();
		if (trimmed.isEmpty()) {
			return trimmed;
		}

		int dotIndex = trimmed.lastIndexOf('.');
		if (dotIndex >= 0 && dotIndex + 1 < trimmed.length()) {
			trimmed = trimmed.substring(dotIndex + 1);
		}

		int bracketIndex = trimmed.lastIndexOf(']');
		if (bracketIndex == trimmed.length() - 1) {
			int open = trimmed.lastIndexOf('[');
			if (open > 0) {
				trimmed = trimmed.substring(0, open);
			}
		}

		return trimmed;
	}

	private byte[] decodeBase64OrPlain(String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}

		String trimmed = value.trim();
		if (trimmed.startsWith("<")) {
			return trimmed.getBytes(StandardCharsets.UTF_8);
		}

		try {
			return Base64.getDecoder().decode(trimmed);
		} catch (IllegalArgumentException e) {
			return trimmed.getBytes(StandardCharsets.UTF_8);
		}
	}

	private Path pathFacturaVenta(Long nIdVenta, TipoDoc tipoDoc) {
		if (nIdVenta == null || tipoDoc == null) {
			return null;
		}
		List<String> rutas = resolveRutasFacturaCandidatas(nIdVenta, tipoDoc);
		if (rutas == null || rutas.isEmpty()) {
			return null;
		}
		String ruta = rutas.get(0);
		String ext = TipoDoc.XML_FACTURA.equals(tipoDoc) ? "xml" : "pdf";
		return ruta != null ? Paths.get(ruta + nIdVenta + "." + ext) : null;
	}

	private Path pathComplementoDocumento(Long nIdComplemento, String ext) {
		if (nIdComplemento == null) {
			return null;
		}
		Optional<com.refacFabela.model.TwFacturacionComplementoPago> complementoOptional = facturacionComplementoPagoRepository.findById(nIdComplemento);
		if (!complementoOptional.isPresent()) {
			return null;
		}
		com.refacFabela.model.TwFacturacionComplementoPago complemento = complementoOptional.get();
		TwFacturacion factura = complemento.getnIdFacturacion() != null ? facturaRepository.findById(complemento.getnIdFacturacion()).orElse(null) : null;
		if (factura == null || factura.getnIdDatoFactura() == null) {
			return null;
		}
		TcDatosFactura datosFactura = tcDatosFacturaRepository.findById(factura.getnIdDatoFactura()).orElse(null);
		if (datosFactura == null) {
			return null;
		}
		String rutaRaiz = datosFacturaStorageResolver.resolveRutaRaiz(datosFactura);
		return rutaRaiz != null ? Paths.get(rutaRaiz, "complementos", ext, String.valueOf(nIdComplemento) + "." + ext) : null;
	}

	private Path pathAcuseCancelacionVenta(Long nIdVenta, String ext) {
		TwVenta venta = nIdVenta != null ? ventasRepository.findBynId(nIdVenta) : null;
		if (venta == null || venta.getTcCliente() == null || venta.getTcCliente().getnIdDatoFactura() == null) {
			return null;
		}
		TcDatosFactura datosFactura = tcDatosFacturaRepository.findById(venta.getTcCliente().getnIdDatoFactura()).orElse(null);
		if (datosFactura == null) {
			return null;
		}
		String rutaRaiz = datosFacturaStorageResolver.resolveRutaRaiz(datosFactura);
		return rutaRaiz != null && ext != null && !ext.trim().isEmpty()
				? Paths.get(rutaRaiz, "cancelaciones", ext, String.valueOf(nIdVenta) + "." + ext)
				: null;
	}

	private byte[] readDocumentoLocal(Path path) {
		if (path == null) {
			return null;
		}
		try {
			if (!Files.exists(path)) {
				return null;
			}
			return Files.readAllBytes(path);
		} catch (IOException e) {
			logger.warn("No se pudo leer documento local {}: {}", path, e.getMessage());
			return null;
		}
	}

	private boolean hasText(String value) {
		return value != null && !value.trim().isEmpty();
	}

	private String ensureTrailingSlash(String value) {
		if (!hasText(value)) {
			return value;
		}
		String trimmed = value.trim();
		if (trimmed.endsWith("/") || trimmed.endsWith("\\")) {
			return trimmed;
		}
		return trimmed + "/";
	}

	private void writeDocumentoLocal(Path path, byte[] data) {
		if (path == null || data == null || data.length == 0) {
			return;
		}
		try {
			if (Files.exists(path)) {
				return;
			}
			if (path.getParent() != null) {
				Files.createDirectories(path.getParent());
			}
			Files.write(path, data, java.nio.file.StandardOpenOption.CREATE_NEW);
		} catch (IOException ignored) {
			// Best effort mirror write; serving should continue with in-memory bytes.
		}
	}

	private boolean mismaRuta(String rutaA, String rutaB) {
		if (rutaA == null || rutaB == null) {
			return false;
		}
		try {
			Path a = Paths.get(rutaA).normalize();
			Path b = Paths.get(rutaB).normalize();
			return a.equals(b);
		} catch (Exception ex) {
			return rutaA.trim().equalsIgnoreCase(rutaB.trim());
		}
	}

	private void agregarEntradaZip(ZipOutputStream zipOutputStream, String entryName, byte[] content) throws IOException {
		ZipEntry entry = new ZipEntry(entryName);
		zipOutputStream.putNextEntry(entry);
		zipOutputStream.write(content);
		zipOutputStream.closeEntry();
	}

	private byte[] downloadBytes(String url) {
		if (url == null || url.trim().isEmpty()) {
			return null;
		}
		try {
			return new org.springframework.web.client.RestTemplate().getForObject(url, byte[].class);
		} catch (Exception e) {
			return null;
		}
	}

	private String firstNonEmpty(String... values) {
		for (String value : values) {
			if (value != null && !value.trim().isEmpty()) {
				return value;
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



