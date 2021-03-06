package com.refacFabela.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import com.refacFabela.dto.AbonosDto;
import com.refacFabela.dto.BalanceCajaDto;
import com.refacFabela.dto.PedidoDto;
import com.refacFabela.dto.PedidoProductoDto;
import com.refacFabela.dto.ReporteAbonoVentaCreditoDto;
import com.refacFabela.dto.ReporteCotizacionDto;
import com.refacFabela.dto.ReporteVentaDto;
import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TcUsuario;
import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TvReporteCajaFormaPago;
import com.refacFabela.model.TvReporteDetalleVenta;
import com.refacFabela.model.TvVentaDetalle;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwCaja;
import com.refacFabela.model.TwCotizacionesProducto;
import com.refacFabela.model.TwPedido;
import com.refacFabela.model.TwPedidoProducto;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.repository.AbonoVentaIdRepository;
import com.refacFabela.repository.CajaRepository;
import com.refacFabela.repository.ClientesRepository;
import com.refacFabela.repository.CotizacionProductoRepository;
import com.refacFabela.repository.PedidosProductoRepository;
import com.refacFabela.repository.TrVentaCobroRepository;
import com.refacFabela.repository.TvReporteCajaFormaPagoRepository;
import com.refacFabela.repository.TvReporteDetalleVentaRepository;
import com.refacFabela.repository.TvVentaDetalleRepository;
import com.refacFabela.repository.TwPedidoRepository;
import com.refacFabela.repository.UsuariosRepository;
import com.refacFabela.repository.VentasProductoRepository;
import com.refacFabela.service.GeneraReporteService;
import com.refacFabela.service.ReporteService;
import com.refacFabela.utils.utils;

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
	
	

	@Override
	public byte[] getCotizacionPDF(Long nIdCotizacion) {

		List<TwCotizacionesProducto> listaProductos = cotizacionProductoRepository.findBynIdCotizacion(nIdCotizacion);

		ReporteCotizacionDto reporteCotizacion = new ReporteCotizacionDto();
		utils util=new utils();

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
			reporte.setPrecioUnitario(util.truncarDecimales(twCotizacionesProducto.getnTotalUnitario()));
			reporte.setImporte(util.truncarDecimales(twCotizacionesProducto.getnTotalPartida()));
			reporte.setDescripcionCatSat(twCotizacionesProducto.getTcProducto().getTcClavesat().getsDescripcion());
		

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
		utils util=new utils();

		ReporteVentaDto reporteVenta = new ReporteVentaDto();

		reporteVenta.setNombreEmpresa("Refaccionaria Fabela");
		reporteVenta.setRfcEmpresa("FAMJ810312FY6");
		reporteVenta.setNombreCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRazonSocial());
		reporteVenta.setRfcCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRfc());
		reporteVenta.setFolioVenta(listaProductos.get(0).getTwVenta().getnId());
		reporteVenta.setFecha(listaProductos.get(0).getTwVenta().getdFechaVenta());
		reporteVenta.setTipoPago(listaProductos.get(0).getTwVenta().getnTipoPago());
		reporteVenta.setDescuento(listaProductos.get(0).getTwVenta().getDescuento());
		

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
			reporte.setNombreProducto(twVentaProducto.getTcProducto().getsDescripcion());
			reporte.setClaveSat(twVentaProducto.getTcProducto().getTcClavesat().getsClavesat());
			reporte.setPrecioUnitario(util.truncarDecimales(twVentaProducto.getnTotalUnitario()));
			reporte.setImporte(util.truncarDecimales(twVentaProducto.getnTotalPartida()));
			reporte.setDescripcionCatSat(twVentaProducto.getTcProducto().getTcClavesat().getsDescripcion());

			listaProducto.add(reporte);

			subtotal = subtotal + twVentaProducto.getnPrecioPartida();
			iva = iva + twVentaProducto.getnIvaPartida();

		}

		reporteVenta.setSubTotal(util.truncarDecimales(subtotal));
		reporteVenta.setIvaTotal(util.truncarDecimales(iva));
		reporteVenta.setTotal(util.truncarDecimales(subtotal+iva));

		return reporteService.generaVentaPDF(reporteVenta, listaProducto, totalAbonos);
	}
	@Override
	public byte[] getVentaPedidoPDF(Long nIdVentaPedido) {
		
		List<TwVentasProducto> listaProductos = ventasProductoRepository.findBynIdVenta(nIdVentaPedido);
		
		ReporteVentaDto reporteVenta = new ReporteVentaDto();
		utils util=new utils();
		
		reporteVenta.setNombreEmpresa("Refaccionaria Fabela");
		reporteVenta.setRfcEmpresa("FAMJ810312FY6");
		reporteVenta.setNombreCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRazonSocial());
		reporteVenta.setRfcCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRfc());
		reporteVenta.setFolioVenta(listaProductos.get(0).getTwVenta().getnId());
		reporteVenta.setFecha(listaProductos.get(0).getTwVenta().getdFechaVenta());
		reporteVenta.setAnticipo(listaProductos.get(0).getTwVenta().getAnticipo());
		reporteVenta.setDescuento(listaProductos.get(0).getTwVenta().getDescuento());
		
		
		List<ReporteVentaDto> listaProducto = new ArrayList<ReporteVentaDto>();
		
		double subtotal = 0.0;
		double iva = 0.0;
		
		
		for (TwVentasProducto twVentaProducto : listaProductos) {
			
			ReporteVentaDto reporte = new ReporteVentaDto();
			
			reporte.setCantidad(twVentaProducto.getnCantidad());
			reporte.setNoIdentificacion(twVentaProducto.getTcProducto().getnId());
			reporte.setNombreProducto(twVentaProducto.getTcProducto().getsDescripcion());
			reporte.setClaveSat(twVentaProducto.getTcProducto().getTcClavesat().getsClavesat());
			reporte.setPrecioUnitario(util.truncarDecimales(twVentaProducto.getnTotalUnitario()));
			reporte.setImporte(util.truncarDecimales(twVentaProducto.getnTotalPartida()));
			
			listaProducto.add(reporte);
			
			subtotal = subtotal + twVentaProducto.getnPrecioPartida();
			iva = iva + twVentaProducto.getnIvaPartida();
			
		}
		
		reporteVenta.setSubTotal(util.truncarDecimales(subtotal));
		reporteVenta.setIvaTotal(util.truncarDecimales(iva));
		reporteVenta.setTotal(util.truncarDecimales(subtotal+iva));
		
		return reporteService.generaVentaPedidoPDF(reporteVenta, listaProducto);
	}

	


	@Override
	public byte[] getAbonoVentaIdPDF(Long nIdVenta) {

		List<TwVentasProducto> listaProductos = ventasProductoRepository.findBynIdVenta(nIdVenta);

		ReporteVentaDto reporteVenta = new ReporteVentaDto();
		utils util=new utils();

		reporteVenta.setNombreEmpresa("Refaccionaria Fabela");
		reporteVenta.setRfcEmpresa("FAMJ810312FY6");
		reporteVenta.setNombreCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRazonSocial());
		reporteVenta.setRfcCliente(listaProductos.get(0).getTwVenta().getTcCliente().getsRfc());
		reporteVenta.setFolioVenta(listaProductos.get(0).getTwVenta().getnId());
		reporteVenta.setFecha(listaProductos.get(0).getTwVenta().getdFechaVenta());
		reporteVenta.setTipoPago(listaProductos.get(0).getTwVenta().getnTipoPago());
		reporteVenta.setCorreo(listaProductos.get(0).getTwVenta().getTcCliente().getsCorreo());
		reporteVenta.setDescuento(listaProductos.get(0).getTwVenta().getDescuento());
		
		

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

		reporteVenta.setNombreEmpresa("Refaccionaria Fabela");
		reporteVenta.setRfcEmpresa("FAMJ810312FY6");
		reporteVenta.setTotal(util.truncarDecimales(totalGeneral));
		reporteVenta.setDescuento(util.truncarDecimales(descuento));
		reporteVenta.setAbonos(util.truncarDecimales(totalAbonos));
				
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

		List<TrVentaCobro> trVentasCobro = trVentaCobroRepository.obtenerPagosCaja(nIdCaja);
		List<TwAbono> twAbono = abonoVentaIdRepository.obtenerAbonosCaja(nIdCaja);
		List<TvReporteDetalleVenta> trReporteDetalleVentas = tvReporteDetalleVentaRepository.obtenerVentasCajaReporte(nIdCaja);		
		List<TvReporteCajaFormaPago> tvReporteCajaFormaPago=tvReporteCajaFormaPagoRepository.obtenerFormaPagoCaja(nIdCaja);		
		 TwCaja caja = cajaRepository.getById(nIdCaja); 
		 TcUsuario usuario = usuariosRepository.obtenerUsuario(caja.getTcUsuario().getnId());
		 utils util=new utils();				
		BalanceCajaDto balanceCajaDto= new BalanceCajaDto();		
	
		 
		for (int i = 0; i < trVentasCobro.size(); i++) {			
			totalIngresosVenta+= trVentasCobro.get(i).getnMonto();			
			
		}
		
		for (int i = 0; i < twAbono.size(); i++) {			
			totalIngresosAbono+= twAbono.get(i).getnAbono();			
			
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
			
	
			
		}
		
		
		/*LLENAOD DE OBJETO BALANCE CAJA*/
		balanceCajaDto.setCaja(caja.getnId());
		balanceCajaDto.setFechaInicioCaja(util.formatoFecha(caja.getdFechaApertura()));
		balanceCajaDto.setTotalIngresoVenta(totalIngresosVenta);
		balanceCajaDto.setTotalIngresoAbonos(totalIngresosAbono);
		balanceCajaDto.setTotalGeneralIngresos(totalIngresosVenta+totalIngresosAbono);
		balanceCajaDto.setTotalVentas(totalVentaCaja);
		balanceCajaDto.setNoVentas(trReporteDetalleVentas.size());
		balanceCajaDto.setNoAbonos(twAbono.size());
		balanceCajaDto.setUsuarioCaja(usuario.getsNombreUsuario());
		balanceCajaDto.setTotalEntregadas(totalEntregadas);
		balanceCajaDto.setTotalNoEntregadas(totalNoEntregadas);
		balanceCajaDto.setTotalEntregasParciales(totalEntegasParciales);
		balanceCajaDto.setFechaGeneraReporte(util.formatoFecha(util.fechaSistema));
		balanceCajaDto.setTvReporteDetalleVenta(trReporteDetalleVentas);
		balanceCajaDto.setTvReporteCajaFormaPago(tvReporteCajaFormaPago);



		return reporteService.generarReporteCajaPDF(balanceCajaDto);
	}
	
	
}



