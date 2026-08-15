package com.refacFabela.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.refacFabela.dto.TvVentaDetalleDto;
import com.refacFabela.dto.VentaDto;
import com.refacFabela.model.TcCliente;
import com.refacFabela.model.ThStockProducto;
import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TvReporteDetalleVenta;
import com.refacFabela.model.TvVentaDetalle;
import com.refacFabela.model.TvVentasFactura;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwCaja;
import com.refacFabela.model.TwCotizaciones;
import com.refacFabela.model.TwFacturacion;
import com.refacFabela.model.TwPedido;
import com.refacFabela.model.TwPedidoProducto;
import com.refacFabela.model.TwPagoAplicacion;
import com.refacFabela.model.TwPagoCliente;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.model.TwSaldoUtilizado;
import com.refacFabela.model.TwVenta;
import com.refacFabela.model.TwVentaProductosTraer;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.repository.AbonoVentaIdRepository;
import com.refacFabela.repository.CajaRepository;
import com.refacFabela.repository.CatalagoFormaPagoRepository;
import com.refacFabela.repository.ClientesRepository;
import com.refacFabela.repository.CotizacionRepository;
import com.refacFabela.repository.FacturacionComplementoPagoRepository;
import com.refacFabela.repository.FacturaRepository;
import com.refacFabela.repository.PedidosProductoRepository;
import com.refacFabela.repository.ProductoBodegaRepository;
import com.refacFabela.repository.ThStockProductoRepository;
import com.refacFabela.repository.TrVentaCobroRepository;
import com.refacFabela.repository.TvReporteDetalleVentaRepository;
import com.refacFabela.repository.TvVentaDetalleRepository;
import com.refacFabela.repository.TwPagoAplicacionRepository;
import com.refacFabela.repository.TwPagoClienteRepository;
import com.refacFabela.repository.TwPedidoRepository;
import com.refacFabela.repository.TwSaldoUtilizadoRepository;
import com.refacFabela.repository.TwVentaProductosTraerRepository;
import com.refacFabela.repository.VentasFacturaRepository;
import com.refacFabela.repository.VentasProductoRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.service.VentasService;
import com.refacFabela.utils.DateTimeUtil;
import com.refacFabela.utils.utils;


@Service
@Transactional
public class VentasServiceImpl implements VentasService {
	@Autowired
	private VentasRepository ventasRepository;

	@Autowired
	private VentasProductoRepository ventasProductoRepository;

	@Autowired
	private TvVentaDetalleRepository tvVentaDetalleRepository;

	@Autowired
	private ProductoBodegaRepository productoBodegaRepository;

	@Autowired
	private AbonoVentaIdRepository abonoVentaIdRepository;

	@Autowired
	private CotizacionRepository cotizacionRepository;
	
	@Autowired
	
	private CajaRepository cajaRepository;
	
	@Autowired
	private TwPedidoRepository twPedidoRepository;
	
	@Autowired
	private PedidosProductoRepository pedidosProductoRepository;
	
	@Autowired
	private VentasFacturaRepository VentasFacturaRepository;

	@Autowired
	private FacturaRepository facturaRepository;
	
	@Autowired
	private TrVentaCobroRepository trVentaCobroRepository;
	
	@Autowired
	private TwVentaProductosTraerRepository twVentaProductosTraerRepository;
	
	@Autowired
	private ThStockProductoRepository thStockProductoRepository;
	
	@Autowired
	private ClientesRepository clientesRepository;
	
	@Autowired
	private TwSaldoUtilizadoRepository twSaldoUtilizadoRepository;
	
	@Autowired
	private TvReporteDetalleVentaRepository tvReporteDetalleVentaRepository;

	@Autowired
	private CatalagoFormaPagoRepository catalagoFormaPagoRepository;

	@Autowired
	private TwPagoClienteRepository twPagoClienteRepository;

	@Autowired
	private TwPagoAplicacionRepository twPagoAplicacionRepository;

	@Autowired
	private FacturacionComplementoPagoRepository facturacionComplementoPagoRepository;

	
	
	
	
	
	@Override
	public List<TwVenta> consltaVentas() {

		return ventasRepository.findAll();
	}
	

	@Override
	public List<TvVentaDetalle> consultaVentaDetalleId(Long nIdCliente, Long nTipoPago) {
		List<TvVentaDetalle> ventasDetalle = tvVentaDetalleRepository.consultaVentaDetalleId(nIdCliente, nTipoPago);
		enriquecerEstadosCanonicos(ventasDetalle);
		return ventasDetalle;
	}

	@Override
	public List<TvVentaDetalle> consultaHistorialVentasCliente(Long nIdCliente) {
		return tvVentaDetalleRepository.findHistorialOptimizadoByNIdCliente(nIdCliente);
	}

	@Override
	public List<TvVentaDetalle> consultaHistorialVentasCliente(Long nIdCliente, Integer nMeses) {
		if (nMeses == null || nMeses <= 0) {
			return tvVentaDetalleRepository.findHistorialOptimizadoByNIdCliente(nIdCliente);
		}

		String fechaInicio = LocalDate.now().minusMonths(nMeses).atStartOfDay().toString().replace('T', ' ');
		return tvVentaDetalleRepository.findHistorialOptimizadoByNIdClienteAndFecha(nIdCliente, fechaInicio);
	}

	@Override
	public List<TvVentaDetalle> consultaPendientesVentasCliente(Long nIdCliente) {
		return tvVentaDetalleRepository.findPendientesOptimizadoByNIdCliente(nIdCliente);
	}
	
	@Override
	public TwVenta guardarVenta(VentaDto ventaDto) {

		/*Declaración de objetos*/
		
		TwVenta twVenta = new TwVenta();
		utils utils=new utils();
		TwPedido twPedido=new TwPedido();
		TwPedido respuesta=new TwPedido();		
		TcCliente tcCliente= new TcCliente();		
		tcCliente=clientesRepository.buscarCliente(ventaDto.getIdCliente());
		
		/*Llenado de objeto venta*/
		twVenta.setnIdCliente(ventaDto.getIdCliente());
		twVenta.setnIdUsuario(ventaDto.getIdUsuario());
		twVenta.setsFolioVenta(ventaDto.getsFolioVenta());
		twVenta.setnIdTipoVenta(ventaDto.getIdTipoVenta().longValue());
		twVenta.setnTipoPago(ventaDto.getTipoPago().longValue());
		twVenta.setdFechaInicioCredito(ventaDto.getFechaIniCredito());
		twVenta.setdFechaTerminoCredito(ventaDto.getFechaFinCredito());		
		twVenta.setnSaldo(false);
		twVenta.setnIdFacturacion(0L);
		twVenta.setnIdCaja(utils.cajaActivaId(cajaRepository.obtenerCajaVigente()));
		twVenta.setnIdCotizacion(ventaDto.getTwCotizacion().getnId());
		twVenta.setAnticipo(ventaDto.getAnticipo());
		twVenta.setDescuento(BigDecimal.ZERO);	
		/*Se integra la fecha de la venta del producto*/
		twVenta.setdFechaVenta(DateTimeUtil.obtenerHoraExactaDeMexico());
		
		
		// Para las ventas por internet
		if (twVenta.getnIdTipoVenta() == 2L) {
			twVenta.setnIdEstatusVenta(2L);
		}else {
			twVenta.setnIdEstatusVenta(1L);			
		} 
		
		/*Si las ventas es a crédito */
		if(ventaDto.getTipoPago()==1L){
			twVenta.setnIdEstatusVenta(2L);
			twVenta.setnIdFormaPago(22L);
			
		}	
	
        /*Se guarga la venta en tw_venta*/
		TwVenta ventaRegistrada = new TwVenta();
		ventaRegistrada = ventasRepository.save(twVenta);
        
		
		/*Se guarda recorre la lista validada*/

		for (int i= 0 ; i < ventaDto.getListaValidada().size(); i++) {

			TwVentasProducto twVentaProducto = new TwVentasProducto();
			TwProductobodega twProductobodega=new TwProductobodega();

			twVentaProducto = utils.calcularPrecioGuardar(ventaDto.getListaValidada().get(i).getTcProducto(), ventaDto.getListaValidada().get(i).getnCantidad());
			twVentaProducto.setnIdVenta(ventaRegistrada.getnId());
			twVentaProducto.setnIdProducto(ventaDto.getListaValidada().get(i).getnIdProducto());
			twVentaProducto.setnCantidad(ventaDto.getListaValidada().get(i).getnCantidad());
			twVentaProducto.setnEstatusEntregaAlmacen(0);
			twVentaProducto.setnIdUsuario(ventaDto.getIdUsuario());
			twVentaProducto.setnEstatus(1); 
			if(tcCliente.getnDescuento()) {
			twVentaProducto.setnIdDescuento(ventaDto.getListaValidada().get(i).getTcProducto().getnIdDescuento());
			}
			else
			{
			twVentaProducto.setnIdDescuento(0L);
				
			}
			
		
			/*condición de entrega*/
			
			twProductobodega=productoBodegaRepository.obtenerProductoBodega(ventaDto.getListaValidada().get(i).getnIdProducto(), "LOCAL");
			
			if(twProductobodega.getnCantidad()!=null) {
				if(twProductobodega.getnCantidad()>=ventaDto.getListaValidada().get(i).getnCantidad()) {
					twVentaProducto.setsCondicionEntrega("ENTREGA INMEDIATA");					
				}
				if(twProductobodega.getnCantidad()<ventaDto.getListaValidada().get(i).getnCantidad()) {
					twVentaProducto.setsCondicionEntrega("TRASPASO DE MERCANCIA");					
				}
				if(twProductobodega.getnCantidad()<ventaDto.getListaValidada().get(i).getnCantidad() &&  twVenta.getnIdTipoVenta() == 3L  ) {
					twVentaProducto.setsCondicionEntrega("VENTA POR PEDIDO");					
				}
				
			}
			
				
			TwVentasProducto twVentaProductoNew = this.ventasProductoRepository.save(twVentaProducto);
			
			if (twVenta.getnIdTipoVenta()== 1L) {
				this.descuentaStock(twVentaProductoNew);
			}
		}

		
		if (twVenta.getnIdTipoVenta()== 1L) {
			TwCotizaciones twCotizaciones = ventaDto.getTwCotizacion();
			twCotizaciones.setnEstatus(2);
			this.cotizacionRepository.save(twCotizaciones);			
		}
		
		if(ventaDto.getIdTipoVenta()==3L) {
			twPedido.setsCvePedido("VP-"+utils.formatoFecha(new Date())+Math.random()*1000000+1);
			twPedido.setdFechaPedido(DateTimeUtil.obtenerHoraExactaDeMexico());
			twPedido.setnIdUsuario(ventaDto.getIdUsuario());
			twPedido.setnEstatus(0L);
			twPedido.setnIdVenta(ventaRegistrada.getnId());
			respuesta=twPedidoRepository.save(twPedido);		
		
			
			for (int i = 0; i < ventaDto.getListaValidada().size(); i++) {
				TwPedidoProducto twPedidoProducto=new TwPedidoProducto();
				List<TwProductobodega> twProductoBodega=new ArrayList<TwProductobodega>();

				
			    twPedidoProducto.setsClavePedido(respuesta.getsCvePedido());
				twPedidoProducto.setdFechaPedido(DateTimeUtil.obtenerHoraExactaDeMexico());
				twPedidoProducto.setnMotivoPedido(2L);
				twPedidoProducto.setnIdProducto(ventaDto.getListaValidada().get(i).getnIdProducto());
				twPedidoProducto.setnCantidadPedida(ventaDto.getListaValidada().get(i).getnCantidad());
				twPedidoProducto.setnIdProveedor(ventaDto.getListaValidada().get(i).getnIdProveedor());
				twPedidoProducto.setnIdUsuario(ventaDto.getIdUsuario());
				twPedidoProducto.setnIdPedido(respuesta.getnId());	
				twPedidoProducto.setnEstatus(2);	
				twProductoBodega=productoBodegaRepository.findBynIdProducto(ventaDto.getListaValidada().get(i).getnIdProducto());
				/*
				for (int j = 0; j < twProductoBodega.size(); j++) {
					if(twProductoBodega.get(j).getTcBodega().getnId().equals(1L)) {
						
						twProductoBodega.get(j).setnCantidad(-ventaDto.getListaValidada().get(i).getnCantidad());
						productoBodegaRepository.save(twProductoBodega.get(j)); 					
						
					}
					
				}*/
			
				pedidosProductoRepository.save(twPedidoProducto);
				
				
			}
			
			
			
		}
		
		return ventaRegistrada;

	}

	private void descuentaStock(TwVentasProducto twVentaProducto) {

		

			List<TwProductobodega> listaStock = new ArrayList<TwProductobodega>();
			
			// Leer con bloqueo pesimista para evitar race condition con traspasos
			listaStock = productoBodegaRepository.findBynIdProductoForUpdate(twVentaProducto.getnIdProducto());
			
			int cantidad = twVentaProducto.getnCantidad();	

		

			while (cantidad != 0) {
				
				for (TwProductobodega listaStockBodega : listaStock) {
					
					
					ThStockProducto  thStockProducto= new ThStockProducto();
					thStockProducto.setdFecha(DateTimeUtil.obtenerHoraExactaDeMexico());
					thStockProducto.setnIdProducto(twVentaProducto.getnIdProducto());
					thStockProducto.setnIdVenta(twVentaProducto.getnIdVenta());
					

					if (listaStockBodega.getnIdBodega() == 1) { // inicia en bodega 1

						if (listaStockBodega.getnCantidad() > 0) {// valida si hay stock en bodega 1
							
						
							thStockProducto.setnIdAnaquel(listaStockBodega.getnIdAnaquel());
							thStockProducto.setnIdNivel(listaStockBodega.getnIdNivel());
							thStockProducto.setnIdBodega(listaStockBodega.getnIdBodega());								
							
							

							if (listaStockBodega.getnCantidad() >= cantidad) {	
								System.err.println("Entre a descontar de la bodega 1");
								
								/*Se agregan los valos para guardar la historia con stock del producto*/
								thStockProducto.setnStock(listaStockBodega.getnCantidad());
								thStockProducto.setnCantidad(cantidad);
								thStockProducto.setnStockFinal(listaStockBodega.getnCantidad() - cantidad);							
								
								/*Se resta el stock de la bodega*/
								listaStockBodega.setnCantidad(listaStockBodega.getnCantidad() - cantidad);						
							
								productoBodegaRepository.save(listaStockBodega);// actualizamos stock
								
								cantidad = 0;
								twVentaProducto.setdFechaEntregaEstimada(utils.today); // se agrega una fecha estimada de entrega
								this.ventasProductoRepository.save(twVentaProducto);  // seguarda laventa del producto
								this.thStockProductoRepository.save(thStockProducto); // se guarda la historia del producto con stock

							} else {
								
								System.err.println("Entre a descontar de la bodega 1");
								
								thStockProducto.setnStock(listaStockBodega.getnCantidad());
								thStockProducto.setnCantidad(listaStockBodega.getnCantidad());
								thStockProducto.setnStockFinal(0);		

								cantidad = cantidad - listaStockBodega.getnCantidad();
								listaStockBodega.setnCantidad(0);
								productoBodegaRepository.save(listaStockBodega); // actualizamos stock
								twVentaProducto.setdFechaEntregaEstimada(utils.tomorrow);
								this.ventasProductoRepository.save(twVentaProducto);
								this.thStockProductoRepository.save(thStockProducto); // se guarda la historia del producto con stock


							}

						}

					} else if (listaStockBodega.getnIdBodega() == 2) { // si no hay stock en bodega 1 entra a bodega 2
						

						if (listaStockBodega.getnCantidad() > 0) {// valida si hay stock en bodega 2
						
							
							thStockProducto.setnIdAnaquel(listaStockBodega.getnIdAnaquel());
							thStockProducto.setnIdNivel(listaStockBodega.getnIdNivel());
							thStockProducto.setnIdBodega(listaStockBodega.getnIdBodega());								
							
							
							
							
							//guardamos datos de traer bodega					
							
							TwVentaProductosTraer productosTraer = new TwVentaProductosTraer();
							String ubicacion = "";
						
							
							productosTraer.setnIdProducto(twVentaProducto.getnIdProducto());
							productosTraer.setnIdVenta(twVentaProducto.getnIdVenta());																				
							ubicacion= listaStockBodega.getTcBodega().getsBodega()+"-"+listaStockBodega.getTcAnaquel().getsAnaquel()+"-"+listaStockBodega.getTcNivel().getsNivel();
							productosTraer.setsUbicacion(ubicacion);
							productosTraer.setnEstatus(0L);
							productosTraer.setdFecha(DateTimeUtil.obtenerHoraExactaDeMexico());
							
							
							
							//descontamos stock

							if (listaStockBodega.getnCantidad() >= cantidad) {
								System.err.println("Entre a descontar de la bodega 2");
								
								thStockProducto.setnStock(listaStockBodega.getnCantidad());
								thStockProducto.setnCantidad(cantidad);
								thStockProducto.setnStockFinal(listaStockBodega.getnCantidad() - cantidad);			

								listaStockBodega.setnCantidad(listaStockBodega.getnCantidad() - cantidad);
								
								productosTraer.setnCantidad(cantidad);	

								productoBodegaRepository.save(listaStockBodega);// actualizamos stock
								cantidad = 0;
								twVentaProducto.setdFechaEntregaEstimada(utils.tomorrow);
								this.ventasProductoRepository.save(twVentaProducto);
								this.thStockProductoRepository.save(thStockProducto); // se guarda la historia del producto con stock


							} else {
								
								System.err.println("Entre a descontar de la bodega 2");
								thStockProducto.setnStock(listaStockBodega.getnCantidad());
								thStockProducto.setnCantidad(listaStockBodega.getnCantidad());
								thStockProducto.setnStockFinal(0);	

								cantidad = cantidad - listaStockBodega.getnCantidad();
								productosTraer.setnCantidad(listaStockBodega.getnCantidad());	
								listaStockBodega.setnCantidad(0);
								productoBodegaRepository.save(listaStockBodega); // actualizamos stock
								twVentaProducto.setdFechaEntregaEstimada(utils.tomorrow);
								this.ventasProductoRepository.save(twVentaProducto);
								this.thStockProductoRepository.save(thStockProducto); // se guarda la historia del producto con stock


							}
							
							twVentaProductosTraerRepository.save(productosTraer);
							
						}
					}

					else if (listaStockBodega.getnIdBodega() == 3) {

						if (listaStockBodega.getnCantidad() > 0) {// valida si hay stock en bodega 3
							
							
							thStockProducto.setnIdAnaquel(listaStockBodega.getnIdAnaquel());
							thStockProducto.setnIdNivel(listaStockBodega.getnIdNivel());
							thStockProducto.setnIdBodega(listaStockBodega.getnIdBodega());
							//guardamos datos de traer bodega	
							
							TwVentaProductosTraer productosTraer = new TwVentaProductosTraer();
							String ubicacion = "";
							
							productosTraer.setnIdProducto(twVentaProducto.getnIdProducto());
							productosTraer.setnIdVenta(twVentaProducto.getnIdVenta());					
																	
							ubicacion= listaStockBodega.getTcBodega().getsBodega()+"-"+listaStockBodega.getTcAnaquel().getsAnaquel()+"-"+listaStockBodega.getTcNivel().getsNivel();
							productosTraer.setsUbicacion(ubicacion);
							productosTraer.setnEstatus(0L);
							productosTraer.setdFecha(DateTimeUtil.obtenerHoraExactaDeMexico());
							
						
							if (listaStockBodega.getnCantidad() >= cantidad) {
								
								System.err.println("Entre a descontar de la bodega 3");
								
								thStockProducto.setnStock(listaStockBodega.getnCantidad());
								thStockProducto.setnCantidad(cantidad);
								thStockProducto.setnStockFinal(listaStockBodega.getnCantidad() - cantidad);			

								listaStockBodega.setnCantidad(listaStockBodega.getnCantidad() - cantidad);
								productosTraer.setnCantidad(cantidad);	

								productoBodegaRepository.save(listaStockBodega);// actualizamos stock
								cantidad = 0;
								twVentaProducto.setdFechaEntregaEstimada(utils.tomorrow);
								this.ventasProductoRepository.save(twVentaProducto);
								this.thStockProductoRepository.save(thStockProducto); // se guarda la historia del producto con stock


							} else {
								
								System.err.println("Entre a descontar de la bodega 3");
								
								thStockProducto.setnStock(listaStockBodega.getnCantidad());
								thStockProducto.setnCantidad(listaStockBodega.getnCantidad());
								thStockProducto.setnStockFinal(0);	

								cantidad = cantidad - listaStockBodega.getnCantidad();
								productosTraer.setnCantidad(listaStockBodega.getnCantidad());
								listaStockBodega.setnCantidad(0);
								productoBodegaRepository.save(listaStockBodega); // actualizamos stock
								twVentaProducto.setdFechaEntregaEstimada(utils.tomorrow);
								this.ventasProductoRepository.save(twVentaProducto);
								this.thStockProductoRepository.save(thStockProducto); // se guarda la historia del producto con stock


							}

							if (productosTraer.getnCantidad()>0) {
								twVentaProductosTraerRepository.save(productosTraer);								
							}
							
						}

					}

				}

			} 
		

	}

	public List<TwAbono> consultaAbonoVentaId(Long nId) {

		return abonoVentaIdRepository.findBynIdVenta(nId);

	}

	@Override
	public List<TvVentaDetalle> consultaVentaDetalle() {
	
		return tvVentaDetalleRepository.findAll();
	}


	@Override
	public List<TvVentaDetalle> consultaVentaDetalleEntrega() {
		
		return tvVentaDetalleRepository.consultaVentaDetalleEntrega();
	}


	public TwVenta consltaVentasId(Long nIdVenta) {
		
		return ventasRepository.findBynId(nIdVenta);
	}
   public TwVenta consltaVentasIdCotizacion(Long nIdCotizacion) {
		
		return ventasRepository.obtnerVentaIdCotizacion(nIdCotizacion);
	}

	@Override
	public List<TvVentaDetalleDto> consultaVentaDetalleIdEstatusVenta( Long nEstatusVenta) {
		
		List<TvVentaDetalle> ventas=new ArrayList<TvVentaDetalle>();
		
		if(nEstatusVenta!=8L) {
			 ventas =tvVentaDetalleRepository.consultaVentaDetalleIdEstatusVenta( nEstatusVenta);
		}
		else {
			nEstatusVenta=1L;
			 ventas =tvVentaDetalleRepository.consultaVentaDetalleIdEstatusVentaFechaCredito( nEstatusVenta);			
		}
		
		
		
		List<TvVentaDetalleDto> ventasNew =new ArrayList<TvVentaDetalleDto>();
		
		
		for (TvVentaDetalle detalleVenta : ventas) {
			
			TvVentaDetalleDto ventaDetalleNew =new TvVentaDetalleDto();
			BigDecimal totalPagos =BigDecimal.ZERO;
			
			ventaDetalleNew.setnId(detalleVenta.getnId());
			ventaDetalleNew.setnIdCliente(detalleVenta.getnIdCliente());
			ventaDetalleNew.setnIdUsuario(detalleVenta.getnIdUsuario());
			ventaDetalleNew.setsFolioVenta(detalleVenta.getsFolioVenta());
			ventaDetalleNew.setdFechaVenta(detalleVenta.getdFechaVenta());
			ventaDetalleNew.setnTipoPago(detalleVenta.getnTipoPago());
			ventaDetalleNew.setdFechaInicioCredito(detalleVenta.getdFechaInicioCredito());
			ventaDetalleNew.setdFechaTerminoCredito(detalleVenta.getdFechaTerminoCredito());
			ventaDetalleNew.setdFechaPagoCredito(detalleVenta.getdFechaPagoCredito());
			ventaDetalleNew.setnTotalVenta(detalleVenta.getnTotalVenta());
			
			if (detalleVenta.getnAnticipo() != null) {
				ventaDetalleNew.setnAnticipo(detalleVenta.getnAnticipo());				
			}else {
				ventaDetalleNew.setnAnticipo(BigDecimal.ZERO);				
				
			}
			
			
			
			List<TrVentaCobro> listaVentaCobro = this.trVentaCobroRepository.findBynIdVenta(ventaDetalleNew.getnId());
			
			if (listaVentaCobro.size()>0) {
				
				ventaDetalleNew.setnTotalAbono(BigDecimal.valueOf(listaVentaCobro.size()) );		
				
				for (int i = 0; i < listaVentaCobro.size(); i++) {
					
					totalPagos=totalPagos.add(listaVentaCobro.get(i).getnMonto()) ;
				}
				
				ventaDetalleNew.setnSaldoTotal(ventaDetalleNew.getnTotalVenta().subtract(totalPagos));
				ventaDetalleNew.setnAvancePago(totalPagos);
 				
			}else {
				ventaDetalleNew.setnTotalAbono(BigDecimal.ZERO);	
				ventaDetalleNew.setnSaldoTotal(ventaDetalleNew.getnTotalVenta());
				ventaDetalleNew.setnAvancePago(BigDecimal.ZERO);
			}
			
			
			
			ventaDetalleNew.setsEstatus(detalleVenta.getsEstatus());
			ventaDetalleNew.setDescuento(detalleVenta.getDescuento());
			ventaDetalleNew.setnIdTipoVenta(detalleVenta.getnIdTipoVenta());
			ventaDetalleNew.setTcCliente(detalleVenta.getTcCliente());
			ventaDetalleNew.setTcUsuario(detalleVenta.getTcUsuario());
			ventaDetalleNew.setTcEstatusVenta(detalleVenta.getTcEstatusVenta());
			ventaDetalleNew.setTcFormapago(detalleVenta.getTcFormapago());
			ventaDetalleNew.setTwCaja(detalleVenta.getTwCaja());
			ventaDetalleNew.setTcTipoVenta(detalleVenta.getTcTipoVenta());
			
			
			
			ventasNew.add(ventaDetalleNew);
			
			
		}
		
		
		
		
		return ventasNew;
	}
	
	


	@Override
	public TvVentaDetalle guardarVentaDetalle(TvVentaDetalle tvVentaDetalle) throws InterruptedException {
		
		
		TwVenta venta = ventasRepository.getById(tvVentaDetalle.getnId());
		TwCaja caja = cajaRepository.obtenerCajaVigente();
		TrVentaCobro ventaCobro = new TrVentaCobro();
		TrVentaCobro ventaCobroSaldo = new TrVentaCobro();
		TwSaldoUtilizado twSaldoUtilizado= new TwSaldoUtilizado();

		boolean cambio=false;
		
		venta.setDescuento(tvVentaDetalle.getDescuento());
				
		 cambio = 
			    tvVentaDetalle.getnTotalVenta().compareTo(
			        tvVentaDetalle.getnAvancePago().add(tvVentaDetalle.getnAnticipo())
			    ) == 0
			    || tvVentaDetalle.getnTotalVenta().subtract(
			        tvVentaDetalle.getnAvancePago().add(tvVentaDetalle.getnAnticipo())
			    ).compareTo(BigDecimal.ZERO) == 0
			    || tvVentaDetalle.getnTotalVenta().subtract(
			        tvVentaDetalle.getnAnticipo()
			    ).subtract(
			        tvVentaDetalle.getnSaldoFavor()
			    ).compareTo(BigDecimal.ZERO) == 0;
		
		
		
		
		if(tvVentaDetalle.getTcTipoVenta().getnId()!=3) {
						
		venta.setnIdFormaPago(tvVentaDetalle.getTcFormapago().getnId());
		venta.setnIdEstatusVenta(2L);
		
		}
		
		if(tvVentaDetalle.getTcTipoVenta().getnId()==3) {
			venta.setnIdFormaPago(tvVentaDetalle.getTcFormapago().getnId());
			if(cambio) {
				venta.setnIdEstatusVenta(2L);
			}
			else {
				venta.setnIdEstatusVenta(1L);				
			}
			
		}		
	
		
	
	
		
			
		
		
		if(venta.getTcTipoVenta().getnId()!=3) {
			
			if(tvVentaDetalle.getnSaldoFavor().compareTo(BigDecimal.ZERO) > 0) {
				System.err.println("Entre a guardar los datos del pago");
				ventaCobro.setnIdVenta(venta.getnId());
				ventaCobro.setnIdCaja(venta.getnIdCaja());
				ventaCobro.setnIdFormaPago(tvVentaDetalle.getTcFormapago().getnId());
				ventaCobro.setdFecha(DateTimeUtil.obtenerHoraExactaDeMexico());
				ventaCobro.setnEstatus(1L);
				ventaCobro.setnIdCaja(caja.getnId());
				ventaCobro.setnMonto(tvVentaDetalle.getnAnticipo());
				ventaCobro = trVentaCobroRepository.save(ventaCobro);
				registrarPagoCanonicoDesdeCobro(ventaCobro, "LEGACY_VENTA_COBRO");
				System.err.println("Sali de guardar el anticipo");
				
				ventaCobroSaldo.setnIdVenta(venta.getnId());
				ventaCobroSaldo.setnIdCaja(venta.getnIdCaja());				
				ventaCobroSaldo.setdFecha(DateTimeUtil.obtenerHoraExactaDeMexico());
				ventaCobroSaldo.setnEstatus(1L);
				ventaCobroSaldo.setnIdCaja(caja.getnId());			
				ventaCobroSaldo.setnMonto(tvVentaDetalle.getnSaldoFavor());
				ventaCobroSaldo.setnIdFormaPago(11L);			
				ventaCobroSaldo = trVentaCobroRepository.save(ventaCobroSaldo);
				registrarPagoCanonicoDesdeCobro(ventaCobroSaldo, "LEGACY_VENTA_COBRO");
				System.err.println("Sali de guardar el saldo a favor");
				
				
				twSaldoUtilizado.setnIdVenta(tvVentaDetalle.getnIdVentaUtilizado());
				twSaldoUtilizado.setnSaldoUtilizado(tvVentaDetalle.getnSaldoFavor());
				twSaldoUtilizado.setnSaldoTotal(tvVentaDetalle.getnSaldoFavor());
                twSaldoUtilizado.setnIdUsuario(tvVentaDetalle.getnIdUsuario());
                twSaldoUtilizado.setnEstatus(true);
                twSaldoUtilizado.setdFecha(DateTimeUtil.obtenerHoraExactaDeMexico());
                twSaldoUtilizado.setnIdCaja(caja.getnId());
                twSaldoUtilizado.setnIdVentaUtilizado(tvVentaDetalle.getnId());
                
                twSaldoUtilizadoRepository.save(twSaldoUtilizado);
                
                
				
			}
			
			else {
				System.err.println("entre a guardar el cobre de venta sin saldo a favor");
				ventaCobro.setnIdVenta(venta.getnId());
				ventaCobro.setnIdCaja(venta.getnIdCaja());
				ventaCobro.setnIdFormaPago(tvVentaDetalle.getTcFormapago().getnId());
				ventaCobro.setdFecha(DateTimeUtil.obtenerHoraExactaDeMexico());
				ventaCobro.setnEstatus(1L);
				ventaCobro.setnIdCaja(caja.getnId());
				ventaCobro.setnMonto(tvVentaDetalle.getnTotalVenta());
				ventaCobro = trVentaCobroRepository.save(ventaCobro);
				registrarPagoCanonicoDesdeCobro(ventaCobro, "LEGACY_VENTA_COBRO");
				
			}
			
		}
		else {
			ventaCobro.setnIdVenta(venta.getnId());
			ventaCobro.setnIdCaja(venta.getnIdCaja());
			ventaCobro.setnIdFormaPago(tvVentaDetalle.getTcFormapago().getnId());
			ventaCobro.setdFecha(DateTimeUtil.obtenerHoraExactaDeMexico());
			ventaCobro.setnEstatus(1L);
			ventaCobro.setnIdCaja(caja.getnId());
			ventaCobro.setnMonto(tvVentaDetalle.getnAnticipo());
			ventaCobro = trVentaCobroRepository.save(ventaCobro);
			registrarPagoCanonicoDesdeCobro(ventaCobro, "LEGACY_VENTA_COBRO");
		}					

		System.err.println("El descuento es de "+tvVentaDetalle.getDescuento());
		
		ventasRepository.save(venta);
		
		
		
		return tvVentaDetalle;
	}

	private void registrarPagoCanonicoDesdeCobro(TrVentaCobro ventaCobro, String origenRegistro) {
		if (ventaCobro == null || ventaCobro.getnIdVenta() == null || ventaCobro.getnMonto() == null
				|| ventaCobro.getnMonto().compareTo(BigDecimal.ZERO) <= 0) {
			return;
		}

		TwVenta venta = ventasRepository.findBynId(ventaCobro.getnIdVenta());
		if (venta == null || venta.getTcCliente() == null || venta.getTcCliente().getnIdDatoFactura() == null) {
			return;
		}

		BigDecimal totalVenta = BigDecimal.ZERO;
		List<TwVentasProducto> productosVenta = ventasProductoRepository.findBynIdVenta(ventaCobro.getnIdVenta());
		for (TwVentasProducto producto : productosVenta) {
			if (producto.getnTotalPartida() != null) {
				totalVenta = totalVenta.add(producto.getnTotalPartida());
			}
		}

		BigDecimal totalCobros = BigDecimal.ZERO;
		List<TrVentaCobro> cobrosVenta = trVentaCobroRepository.findBynIdVenta(ventaCobro.getnIdVenta());
		for (TrVentaCobro cobro : cobrosVenta) {
			if (cobro.getnMonto() != null) {
				totalCobros = totalCobros.add(cobro.getnMonto());
			}
		}

		BigDecimal totalCobrosPrevios = totalCobros.subtract(ventaCobro.getnMonto());
		if (totalCobrosPrevios.compareTo(BigDecimal.ZERO) < 0) {
			totalCobrosPrevios = BigDecimal.ZERO;
		}

		BigDecimal saldoAnterior = totalVenta.subtract(totalCobrosPrevios);
		if (saldoAnterior.compareTo(BigDecimal.ZERO) < 0) {
			saldoAnterior = BigDecimal.ZERO;
		}

		BigDecimal saldoInsoluto = totalVenta.subtract(totalCobros);
		if (saldoInsoluto.compareTo(BigDecimal.ZERO) < 0) {
			saldoInsoluto = BigDecimal.ZERO;
		}

		TwPagoCliente pagoCliente = new TwPagoCliente();
		pagoCliente.setnIdCliente(venta.getnIdCliente());
		pagoCliente.setnIdDatoFactura(venta.getTcCliente().getnIdDatoFactura());
		pagoCliente.setdFechaRegistro(DateTimeUtil.obtenerHoraExactaDeMexico());
		pagoCliente.setdFechaPago(DateTimeUtil.normalizarFechaMxPosibleUtc(ventaCobro.getdFecha()));
		pagoCliente.setnImporteTotal(ventaCobro.getnMonto());
		pagoCliente.setnImporteAplicado(ventaCobro.getnMonto());
		pagoCliente.setnImporteDisponible(BigDecimal.ZERO);
		pagoCliente.setsMoneda("MXN");
		pagoCliente.setnIdFormaPago(ventaCobro.getnIdFormaPago());
		if (ventaCobro.getnIdFormaPago() != null) {
			com.refacFabela.model.TcFormapago formaPago = catalagoFormaPagoRepository.findById(ventaCobro.getnIdFormaPago())
					.orElse(null);
			if (formaPago != null) {
				pagoCliente.setsFormaPagoSat(formaPago.getsClave());
				pagoCliente.setsDescripcionFormaPago(formaPago.getsDescripcion());
			}
		}
		pagoCliente.setsObservaciones("Registro generado desde flujo legacy de cobros de venta.");
		pagoCliente.setnIdUsuarioRegistro(venta.getnIdUsuario());
		pagoCliente.setnIdCaja(ventaCobro.getnIdCaja());
		pagoCliente.setnIdCorteCaja(ventaCobro.getnIdCaja());
		pagoCliente.setsEstatus("APLICADO_TOTAL");
		pagoCliente.setnConciliado(Boolean.FALSE);
		pagoCliente.setnFacturarRep(Boolean.TRUE);
		pagoCliente.setnEstatus(1);
		pagoCliente = twPagoClienteRepository.save(pagoCliente);

		TwPagoAplicacion aplicacion = new TwPagoAplicacion();
		aplicacion.setnIdPagoCliente(pagoCliente.getnId());
		aplicacion.setnIdCliente(venta.getnIdCliente());
		aplicacion.setnIdVenta(venta.getnId());
		aplicacion.setnIdFacturacion(venta.getnIdFacturacion());
		aplicacion.setnIdDatoFactura(venta.getTcCliente().getnIdDatoFactura());
		aplicacion.setnMontoAplicado(ventaCobro.getnMonto());
		aplicacion.setnSaldoAnterior(saldoAnterior);
		aplicacion.setnSaldoInsoluto(saldoInsoluto);
		Integer parcialidad = twPagoAplicacionRepository.maxParcialidadActivaVenta(venta.getnId());
		aplicacion.setnParcialidad(parcialidad == null || parcialidad.intValue() <= 0 ? 1 : parcialidad + 1);
		aplicacion.setsEstatus("APLICADA");
		aplicacion.setdFechaAplicacion(DateTimeUtil.obtenerHoraExactaDeMexico());
		aplicacion.setnIdUsuario(venta.getnIdUsuario());
		aplicacion.setnOrdenAplicacion(cobrosVenta.size());
		aplicacion.setsOrigenRegistro(origenRegistro);
		aplicacion.setnEstatus(1);
		twPagoAplicacionRepository.save(aplicacion);
	}

	private void enriquecerEstadosCanonicos(List<TvVentaDetalle> ventasDetalle) {
		if (ventasDetalle == null) {
			return;
		}
		for (TvVentaDetalle detalle : ventasDetalle) {
			enriquecerEstadoCanonico(detalle);
		}
	}

	private void enriquecerEstadoCanonico(TvVentaDetalle detalle) {
		if (detalle == null || detalle.getnId() == null) {
			return;
		}

		List<TwPagoAplicacion> aplicaciones = twPagoAplicacionRepository.findActivasByVenta(detalle.getnId());
		if (aplicaciones == null || aplicaciones.isEmpty()) {
			detalle.setsEstadoPagoCanonico("SIN_PAGO_CANONICO");
			detalle.setsEstadoRepCanonico(resolveEstadoRepSinAplicaciones(detalle.getnId()));
			return;
		}

		TwPagoAplicacion ultimaAplicacion = aplicaciones.get(aplicaciones.size() - 1);
		detalle.setnIdPagoClienteCanonico(ultimaAplicacion.getnIdPagoCliente());
		TwPagoCliente pagoCliente = ultimaAplicacion.getTwPagoCliente() != null
				? ultimaAplicacion.getTwPagoCliente()
				: twPagoClienteRepository.findBynId(ultimaAplicacion.getnIdPagoCliente());
		detalle.setsEstadoPagoCanonico(pagoCliente != null && pagoCliente.getsEstatus() != null
				? pagoCliente.getsEstatus()
				: "APLICADA");

		java.util.Set<Long> idsAplicacion = new java.util.HashSet<Long>();
		for (TwPagoAplicacion aplicacion : aplicaciones) {
			if (aplicacion != null && aplicacion.getnId() != null) {
				idsAplicacion.add(aplicacion.getnId());
			}
		}

		java.util.List<com.refacFabela.model.TwFacturacionComplementoPago> complementos = facturacionComplementoPagoRepository.findByVenta(detalle.getnId());
		com.refacFabela.model.TwFacturacionComplementoPago ultimoTimbrado = null;
		com.refacFabela.model.TwFacturacionComplementoPago ultimoFallido = null;
		for (com.refacFabela.model.TwFacturacionComplementoPago complemento : complementos) {
			if (complemento == null || !"TW_PAGO_CLIENTE_APLICACION".equalsIgnoreCase(complemento.getsOrigenPago())
					|| complemento.getnIdPagoOrigen() == null || !idsAplicacion.contains(complemento.getnIdPagoOrigen())) {
				continue;
			}
			if (complemento.getnEstatus() != null && complemento.getnEstatus().intValue() == 1) {
				ultimoTimbrado = complemento;
			}
			if (complemento.getnEstatus() != null && complemento.getnEstatus().intValue() == 0) {
				ultimoFallido = complemento;
			}
		}

		if (ultimoTimbrado != null) {
			detalle.setsEstadoRepCanonico("TIMBRADO");
			detalle.setsUuidRepCanonico(ultimoTimbrado.getsUuidComplementoPago());
			return;
		}

		if (ultimoFallido != null) {
			detalle.setsEstadoRepCanonico("FALLIDO");
			return;
		}

		detalle.setsEstadoRepCanonico(resolveEstadoRepSinAplicaciones(detalle.getnId()));
	}

	private String resolveEstadoRepSinAplicaciones(Long nIdVenta) {
		TwVenta venta = ventasRepository.findBynId(nIdVenta);
		if (venta == null || venta.getnIdFacturacion() == null || venta.getnIdFacturacion().longValue() <= 0L) {
			return "NO_FACTURADA";
		}
		TwFacturacion facturacion = facturaRepository.findById(venta.getnIdFacturacion()).orElse(null);
		if (facturacion == null) {
			return "NO_FACTURADA";
		}
		if ("PPD".equalsIgnoreCase(facturacion.getsMetodoPagoFiscal()) && "99".equalsIgnoreCase(facturacion.getsFormaPagoFiscal())) {
			return "PENDIENTE";
		}
		return "NO_REQUIERE";
	}
	
	

	@Override
	public List<TvVentaDetalle> consultaVentaDetalleCajaVigente() {
		TwCaja caja =new TwCaja();				
		caja=cajaRepository.obtenerCajaVigente();		
		return tvVentaDetalleRepository.consultaVentaDetalleCajaVigente(caja.getnId());
	}

	@Override
	public TwVenta updateStatusVenta(TwVenta twVenta) {	
		return this.ventasRepository.save(twVenta);
	}

	@Override
	public List<TvVentasFactura> consultaVentasParaFactura() {
				return completarEstatusFacturacion(this.VentasFacturaRepository.obtenerFacturas());
	}


	@Override
	public List<TvVentaDetalle> consultaVentaslike(String buscar) {
	
		return tvVentaDetalleRepository.findByLike(buscar);
	}


	@Override
	public List<TvVentaDetalle> consultaVentasTop() {
		
		return tvVentaDetalleRepository.findByTop();
	}


	@Override
	public List<TvVentasFactura> consultaVentasFacturadas(String periodo, LocalDate fechaInicio, LocalDate fechaFin,
			String estatus, String buscar) {
		LocalDate[] rangoConsulta = resolveRangoConsultaFacturadas(periodo, fechaInicio, fechaFin);
		LocalDate fechaInicioConsulta = rangoConsulta[0];
		LocalDate fechaFinConsulta = rangoConsulta[1];
		LocalDateTime inicio = fechaInicioConsulta != null ? fechaInicioConsulta.atStartOfDay() : null;
		LocalDateTime fin = fechaFinConsulta != null ? fechaFinConsulta.atTime(23, 59, 59) : null;
		List<TvVentasFactura> ventas = completarEstatusFacturacion(
				this.VentasFacturaRepository.obtenerVentasFacturadasFiltradas(inicio, fin, trimToNull(buscar)));
		return filtrarVentasFacturadasPorEstatus(ventas, estatus);
	}

	private LocalDate[] resolveRangoConsultaFacturadas(String periodo, LocalDate fechaInicio, LocalDate fechaFin) {
		LocalDate fechaHasta = fechaFin != null ? fechaFin : DateTimeUtil.obtenerHoraExactaDeMexico().toLocalDate();
		String periodoNormalizado = periodo != null ? periodo.trim().toUpperCase() : "60D";
		LocalDate fechaDesde;

		switch (periodoNormalizado) {
		case "3M":
			fechaDesde = fechaHasta.minusMonths(3);
			break;
		case "6M":
			fechaDesde = fechaHasta.minusMonths(6);
			break;
		case "CUSTOM":
			fechaDesde = fechaInicio != null ? fechaInicio : fechaHasta.minusDays(60);
			break;
		case "60D":
		default:
			fechaDesde = fechaHasta.minusDays(60);
			break;
		}

		if (fechaDesde != null && fechaDesde.isAfter(fechaHasta)) {
			LocalDate temporal = fechaDesde;
			fechaDesde = fechaHasta;
			fechaHasta = temporal;
		}

		return new LocalDate[] { fechaDesde, fechaHasta };
	}

	private List<TvVentasFactura> filtrarVentasFacturadasPorEstatus(List<TvVentasFactura> ventas, String estatus) {
		String estatusNormalizado = trimToNull(estatus);
		if (ventas == null || ventas.isEmpty() || estatusNormalizado == null || "TODOS".equalsIgnoreCase(estatusNormalizado)) {
			return ventas;
		}

		List<TvVentasFactura> filtradas = new ArrayList<TvVentasFactura>();
		for (TvVentasFactura venta : ventas) {
			if (venta == null) {
				continue;
			}

			String estadoFacturacion = venta.getsEstadoFacturacion() != null ? venta.getsEstadoFacturacion().trim().toUpperCase() : "";
			String estadoComplemento = venta.getsEstadoComplemento() != null ? venta.getsEstadoComplemento().trim().toUpperCase() : "";
			String estadoRepCanonico = venta.getsEstadoRepCanonico() != null ? venta.getsEstadoRepCanonico().trim().toUpperCase() : "";
			boolean cancelada = estadoFacturacion.contains("CANCEL");

			if ("FACTURADA".equalsIgnoreCase(estatusNormalizado) && venta.getIdFactura() != null
					&& venta.getIdFactura().longValue() > 0L && !cancelada) {
				filtradas.add(venta);
				continue;
			}

			if ("CANCELADA".equalsIgnoreCase(estatusNormalizado) && cancelada) {
				filtradas.add(venta);
				continue;
			}

			if ("REP_TIMBRADO".equalsIgnoreCase(estatusNormalizado)
					&& ("TIMBRADO".equalsIgnoreCase(estadoRepCanonico)
							|| "FACTURADA_CON_COMPLEMENTO_PAGO".equalsIgnoreCase(estadoComplemento))) {
				filtradas.add(venta);
				continue;
			}

			if ("REP_PENDIENTE".equalsIgnoreCase(estatusNormalizado)
					&& ("PENDIENTE".equalsIgnoreCase(estadoRepCanonico)
							|| "PENDIENTE_COMPLEMENTO_PAGO".equalsIgnoreCase(estadoComplemento))) {
				filtradas.add(venta);
				continue;
			}

			if ("REP_FALLIDO".equalsIgnoreCase(estatusNormalizado)
					&& "FALLIDO".equalsIgnoreCase(estadoRepCanonico)) {
				filtradas.add(venta);
				continue;
			}

			if ("REP_PENDIENTE_FACTURACION".equalsIgnoreCase(estatusNormalizado)
					&& "PENDIENTE_FACTURACION".equalsIgnoreCase(estadoRepCanonico)) {
				filtradas.add(venta);
			}
		}

		return filtradas;
	}

	private String trimToNull(String valor) {
		if (valor == null) {
			return null;
		}
		String normalizado = valor.trim();
		return normalizado.isEmpty() ? null : normalizado;
	}

	private List<TvVentasFactura> completarEstatusFacturacion(List<TvVentasFactura> ventas) {
		if (ventas == null || ventas.isEmpty()) {
			return ventas;
		}

		Set<Long> idsFacturacion = new HashSet<Long>();
		for (TvVentasFactura venta : ventas) {
			if (venta != null && venta.getIdFactura() != null && venta.getIdFactura().longValue() > 0L) {
				idsFacturacion.add(venta.getIdFactura());
			}
		}

		Map<Long, String> estadoPorFacturaId = new HashMap<Long, String>();
		Map<Long, String> estadoComplementoPorFacturaId = new HashMap<Long, String>();
		Map<Long, String> clasificacionPorFacturaId = new HashMap<Long, String>();
		Map<Long, String> uuidComplementoPorFacturaId = new HashMap<Long, String>();
		if (!idsFacturacion.isEmpty()) {
			List<TwFacturacion> facturas = facturaRepository.findAllById(idsFacturacion);
			for (TwFacturacion factura : facturas) {
				if (factura != null && factura.getnId() != null) {
					estadoPorFacturaId.put(factura.getnId(), factura.getsEstado());
					estadoComplementoPorFacturaId.put(factura.getnId(), factura.getsEstadoComplemento());
					clasificacionPorFacturaId.put(factura.getnId(), factura.getsClasificacionFiscal());
					uuidComplementoPorFacturaId.put(factura.getnId(), factura.getsUuidComplementoPago());
				}
			}
		}

		for (TvVentasFactura venta : ventas) {
			if (venta == null) {
				continue;
			}
			if (venta.getIdFactura() == null || venta.getIdFactura().longValue() <= 0L) {
				venta.setsEstadoFacturacion(null);
				venta.setsEstadoComplemento(null);
				venta.setsClasificacionFiscal(null);
				venta.setsUuidComplementoPago(null);
				continue;
			}
			venta.setsEstadoFacturacion(estadoPorFacturaId.get(venta.getIdFactura()));
			venta.setsEstadoComplemento(estadoComplementoPorFacturaId.get(venta.getIdFactura()));
			venta.setsClasificacionFiscal(clasificacionPorFacturaId.get(venta.getIdFactura()));
			venta.setsUuidComplementoPago(uuidComplementoPorFacturaId.get(venta.getIdFactura()));
		}

		enriquecerEstadosCanonicosFacturacion(ventas);

		return ventas;
	}

	private void enriquecerEstadosCanonicosFacturacion(List<TvVentasFactura> ventas) {
		if (ventas == null || ventas.isEmpty()) {
			return;
		}

		List<Long> idsVenta = new ArrayList<Long>();
		for (TvVentasFactura venta : ventas) {
			if (venta != null && venta.getnId() != null) {
				idsVenta.add(venta.getnId());
			}
		}
		if (idsVenta.isEmpty()) {
			return;
		}

		Map<Long, List<TwPagoAplicacion>> aplicacionesPorVenta = new HashMap<Long, List<TwPagoAplicacion>>();
		Set<Long> idsPagoCliente = new HashSet<Long>();
		for (TwPagoAplicacion aplicacion : twPagoAplicacionRepository.findActivasByVentas(idsVenta)) {
			if (aplicacion == null || aplicacion.getnIdVenta() == null) {
				continue;
			}
			List<TwPagoAplicacion> aplicaciones = aplicacionesPorVenta.get(aplicacion.getnIdVenta());
			if (aplicaciones == null) {
				aplicaciones = new ArrayList<TwPagoAplicacion>();
				aplicacionesPorVenta.put(aplicacion.getnIdVenta(), aplicaciones);
			}
			aplicaciones.add(aplicacion);
			if (aplicacion.getnIdPagoCliente() != null) {
				idsPagoCliente.add(aplicacion.getnIdPagoCliente());
			}
		}

		Map<Long, TwPagoCliente> pagosPorId = new HashMap<Long, TwPagoCliente>();
		if (!idsPagoCliente.isEmpty()) {
			for (TwPagoCliente pago : twPagoClienteRepository.findAllById(idsPagoCliente)) {
				if (pago != null && pago.getnId() != null) {
					pagosPorId.put(pago.getnId(), pago);
				}
			}
		}

		Map<Long, List<com.refacFabela.model.TwFacturacionComplementoPago>> complementosPorVenta = new HashMap<Long, List<com.refacFabela.model.TwFacturacionComplementoPago>>();
		for (com.refacFabela.model.TwFacturacionComplementoPago complemento : facturacionComplementoPagoRepository.findByVentas(idsVenta)) {
			if (complemento == null || complemento.getnIdVenta() == null) {
				continue;
			}
			List<com.refacFabela.model.TwFacturacionComplementoPago> complementos = complementosPorVenta.get(complemento.getnIdVenta());
			if (complementos == null) {
				complementos = new ArrayList<com.refacFabela.model.TwFacturacionComplementoPago>();
				complementosPorVenta.put(complemento.getnIdVenta(), complementos);
			}
			complementos.add(complemento);
		}

		Set<Long> ventasPedido = new HashSet<Long>();
		for (TwPedido pedido : twPedidoRepository.findByVentas(idsVenta)) {
			if (pedido != null && pedido.getnIdVenta() != null) {
				ventasPedido.add(pedido.getnIdVenta());
			}
		}

		Map<Long, List<TrVentaCobro>> cobrosPorVenta = new HashMap<Long, List<TrVentaCobro>>();
		for (TrVentaCobro cobro : trVentaCobroRepository.findByVentas(idsVenta)) {
			if (cobro == null || cobro.getnIdVenta() == null) {
				continue;
			}
			List<TrVentaCobro> cobros = cobrosPorVenta.get(cobro.getnIdVenta());
			if (cobros == null) {
				cobros = new ArrayList<TrVentaCobro>();
				cobrosPorVenta.put(cobro.getnIdVenta(), cobros);
			}
			cobros.add(cobro);
		}

		Set<Long> idsFacturacion = new HashSet<Long>();
		for (TvVentasFactura venta : ventas) {
			if (venta != null && venta.getIdFactura() != null && venta.getIdFactura().longValue() > 0L) {
				idsFacturacion.add(venta.getIdFactura());
			}
		}
		Map<Long, TwFacturacion> facturasPorId = new HashMap<Long, TwFacturacion>();
		if (!idsFacturacion.isEmpty()) {
			for (TwFacturacion factura : facturaRepository.findAllById(idsFacturacion)) {
				if (factura != null && factura.getnId() != null) {
					facturasPorId.put(factura.getnId(), factura);
				}
			}
		}

		for (TvVentasFactura venta : ventas) {
			venta.setEsVentaPedido(Boolean.valueOf(ventasPedido.contains(venta.getnId())));
			venta.setTieneCobroPendienteComplemento(Boolean.valueOf(tieneCobroPendienteComplemento(
					cobrosPorVenta.get(venta.getnId()), complementosPorVenta.get(venta.getnId()))));
			enriquecerEstadoCanonicoFacturacion(venta, aplicacionesPorVenta.get(venta.getnId()),
					complementosPorVenta.get(venta.getnId()), pagosPorId, facturasPorId);
		}
	}

	private boolean tieneCobroPendienteComplemento(List<TrVentaCobro> cobros,
			List<com.refacFabela.model.TwFacturacionComplementoPago> complementos) {
		if (cobros == null || cobros.isEmpty()) {
			return false;
		}
		for (TrVentaCobro cobro : cobros) {
			boolean tieneComplementoTimbrado = false;
			if (complementos != null) {
				for (com.refacFabela.model.TwFacturacionComplementoPago complemento : complementos) {
					if (complemento != null && "TR_VENTA_COBRO".equalsIgnoreCase(complemento.getsOrigenPago())
							&& cobro.getnId() != null && cobro.getnId().equals(complemento.getnIdPagoOrigen())
							&& complemento.getnEstatus() != null && complemento.getnEstatus().intValue() == 1) {
						tieneComplementoTimbrado = true;
						break;
					}
				}
			}
			if (!tieneComplementoTimbrado) {
				return true;
			}
		}
		return false;
	}

	private void enriquecerEstadoCanonicoFacturacion(TvVentasFactura venta, List<TwPagoAplicacion> aplicaciones,
			List<com.refacFabela.model.TwFacturacionComplementoPago> complementos,
			Map<Long, TwPagoCliente> pagosPorId, Map<Long, TwFacturacion> facturasPorId) {
		if (venta == null || venta.getnId() == null) {
			return;
		}

		if (aplicaciones == null || aplicaciones.isEmpty()) {
			venta.setnIdPagoClienteCanonico(null);
			venta.setsEstadoPagoCanonico("SIN_PAGO_CANONICO");
			venta.setsEstadoRepCanonico(null);
			venta.setsUuidRepCanonico(null);
			return;
		}

		TwPagoAplicacion ultimaAplicacion = aplicaciones.get(aplicaciones.size() - 1);
		venta.setnIdPagoClienteCanonico(ultimaAplicacion.getnIdPagoCliente());
		TwPagoCliente pagoCliente = ultimaAplicacion.getnIdPagoCliente() != null
				? pagosPorId.get(ultimaAplicacion.getnIdPagoCliente())
				: null;
		venta.setsEstadoPagoCanonico(pagoCliente != null && pagoCliente.getsEstatus() != null
				? pagoCliente.getsEstatus()
				: "APLICADA");
		venta.setsUuidRepCanonico(null);

		java.util.Set<Long> idsAplicacion = new java.util.HashSet<Long>();
		boolean tieneAplicacionesSinFactura = false;
		for (TwPagoAplicacion aplicacion : aplicaciones) {
			if (aplicacion == null) {
				continue;
			}
			if (aplicacion.getnId() != null) {
				idsAplicacion.add(aplicacion.getnId());
			}
			if (aplicacion.getnIdFacturacion() == null || aplicacion.getnIdFacturacion().longValue() <= 0L) {
				tieneAplicacionesSinFactura = true;
			}
		}

		com.refacFabela.model.TwFacturacionComplementoPago ultimoTimbrado = null;
		com.refacFabela.model.TwFacturacionComplementoPago ultimoFallido = null;
		for (com.refacFabela.model.TwFacturacionComplementoPago complemento : complementos != null
				? complementos : java.util.Collections.<com.refacFabela.model.TwFacturacionComplementoPago>emptyList()) {
			if (complemento == null || !"TW_PAGO_CLIENTE_APLICACION".equalsIgnoreCase(complemento.getsOrigenPago())
					|| complemento.getnIdPagoOrigen() == null || !idsAplicacion.contains(complemento.getnIdPagoOrigen())) {
				continue;
			}
			if (complemento.getnEstatus() != null && complemento.getnEstatus().intValue() == 1) {
				ultimoTimbrado = complemento;
			}
			if (complemento.getnEstatus() != null && complemento.getnEstatus().intValue() == 0) {
				ultimoFallido = complemento;
			}
		}

		if (ultimoTimbrado != null) {
			venta.setsEstadoRepCanonico("TIMBRADO");
			venta.setsUuidRepCanonico(ultimoTimbrado.getsUuidComplementoPago());
			return;
		}

		if (ultimoFallido != null) {
			venta.setsEstadoRepCanonico("FALLIDO");
			return;
		}

		if (tieneAplicacionesSinFactura) {
			venta.setsEstadoRepCanonico("PENDIENTE_FACTURACION");
			return;
		}

		if (venta.getIdFactura() == null || venta.getIdFactura().longValue() <= 0L) {
			venta.setsEstadoRepCanonico("NO_FACTURADA");
			return;
		}

		TwFacturacion facturacion = facturasPorId.get(venta.getIdFactura());
		if (facturacion == null) {
			venta.setsEstadoRepCanonico("NO_FACTURADA");
			return;
		}
		if ("PPD".equalsIgnoreCase(facturacion.getsMetodoPagoFiscal())
				&& "99".equalsIgnoreCase(facturacion.getsFormaPagoFiscal())) {
			venta.setsEstadoRepCanonico("PENDIENTE");
			return;
		}
		venta.setsEstadoRepCanonico("NO_REQUIERE");
	}


	@Override
	public List<TrVentaCobro> consultarPagoId(Long idVenta) {
		
		return this.trVentaCobroRepository.findBynIdVenta(idVenta);
	}


	@Override
	public void eliminarCobroIdVenta(Long idVenta) {
		this.trVentaCobroRepository.deleteBynIdVenta(idVenta);
		
	}


	@Override
	public TvVentaDetalle guardarDescuento(TvVentaDetalle tvVentaDetalle) throws InterruptedException {
		
		TwVenta twVenta =new TwVenta();		
		twVenta=ventasRepository.findBynId(tvVentaDetalle.getnId());		
		twVenta.setDescuento(tvVentaDetalle.getDescuento());		
		 ventasRepository.save(twVenta);		
		
		return tvVentaDetalle;
	}


	@Override
	public List<TvReporteDetalleVenta> obtenerVentasDetalleCaja(Long nId) {
		
		return tvReporteDetalleVentaRepository.obtenerVentasCajaReporte(nId);
	}


	@Override
	public TwVenta guardarVentaCompleta(TwVenta twVenta) {
		
		return ventasRepository.save(twVenta);
	}

	
}
