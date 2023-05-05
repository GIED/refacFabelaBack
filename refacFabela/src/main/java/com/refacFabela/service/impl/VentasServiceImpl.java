package com.refacFabela.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.refacFabela.dto.TvVentaDetalleDto;
import com.refacFabela.dto.VentaDto;
import com.refacFabela.model.ThStockProducto;
import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TvVentaDetalle;
import com.refacFabela.model.TvVentasFactura;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwCaja;
import com.refacFabela.model.TwCotizaciones;
import com.refacFabela.model.TwPedido;
import com.refacFabela.model.TwPedidoProducto;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.model.TwVenta;
import com.refacFabela.model.TwVentaProductosTraer;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.repository.AbonoVentaIdRepository;
import com.refacFabela.repository.CajaRepository;
import com.refacFabela.repository.CotizacionRepository;
import com.refacFabela.repository.PedidosProductoRepository;
import com.refacFabela.repository.ProductoBodegaRepository;
import com.refacFabela.repository.ThStockProductoRepository;
import com.refacFabela.repository.TrVentaCobroRepository;
import com.refacFabela.repository.TvVentaDetalleRepository;
import com.refacFabela.repository.TwPedidoRepository;
import com.refacFabela.repository.TwVentaProductosTraerRepository;
import com.refacFabela.repository.VentasFacturaRepository;
import com.refacFabela.repository.VentasProductoRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.service.VentasService;
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
	private TrVentaCobroRepository trVentaCobroRepository;
	
	@Autowired
	private TwVentaProductosTraerRepository twVentaProductosTraerRepository;
	
	@Autowired
	private ThStockProductoRepository thStockProductoRepository;

	@Override
	public List<TwVenta> consltaVentas() {

		return ventasRepository.findAll();
	}
	

	@Override
	public List<TvVentaDetalle> consultaVentaDetalleId(Long nIdCliente, Long nTipoPago) {

		return tvVentaDetalleRepository.consultaVentaDetalleId(nIdCliente, nTipoPago);
	}
	
	@Override
	public TwVenta guardarVenta(VentaDto ventaDto) {

		System.out.println(ventaDto);

		TwVenta twVenta = new TwVenta();
		utils utils=new utils();
		TwPedido twPedido=new TwPedido();
		TwPedido respuesta=new TwPedido();
		TwPedidoProducto twPedidoProducto=new TwPedidoProducto();

		twVenta.setnIdCliente(ventaDto.getIdCliente());
		twVenta.setnIdUsuario(ventaDto.getIdUsuario());
		twVenta.setsFolioVenta(ventaDto.getsFolioVenta());
		twVenta.setnIdTipoVenta(ventaDto.getIdTipoVenta().longValue());
		twVenta.setnTipoPago(ventaDto.getTipoPago().longValue());
		twVenta.setdFechaInicioCredito(ventaDto.getFechaIniCredito());
		twVenta.setdFechaTerminoCredito(ventaDto.getFechaFinCredito());
		System.err.println(ventaDto.getTipoPago());
				
		twVenta.setdFechaVenta(new Date());
		if (twVenta.getnIdTipoVenta() == 2L) {
			twVenta.setnIdEstatusVenta(2L);
		}else {
			twVenta.setnIdEstatusVenta(1L);			
		}
		
		if(ventaDto.getTipoPago()==1L){
			twVenta.setnIdEstatusVenta(2L);
			
		}	
		
		twVenta.setnIdFacturacion(0L);
		twVenta.setnIdCaja(utils.cajaActivaId(cajaRepository.obtenerCajaVigente()));
		twVenta.setnIdCotizacion(ventaDto.getTwCotizacion().getnId());
		twVenta.setAnticipo(ventaDto.getAnticipo());
		twVenta.setDescuento(0.0);
	

		TwVenta ventaRegistrada = new TwVenta();
		ventaRegistrada = ventasRepository.save(twVenta);


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
			/*condición de entrega*/
			
			twProductobodega=productoBodegaRepository.obtenerProductoBodega(ventaDto.getListaValidada().get(i).getnIdProducto(), "LOCAL");
			
			if(twProductobodega.getnCantidad()!=null) {
				if(twProductobodega.getnCantidad()>=ventaDto.getListaValidada().get(i).getnCantidad()) {
					twVentaProducto.setsCondicionEntrega("ENTREGA INMEDIATA");					
				}
				if(twProductobodega.getnCantidad()<ventaDto.getListaValidada().get(i).getnCantidad()) {
					twVentaProducto.setsCondicionEntrega("TRASPASO DE MERCANCIA");					
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
			twPedido.setdFechaPedido(new Date());
			twPedido.setnIdUsuario(ventaDto.getIdUsuario());
			twPedido.setnEstatus(0L);
			twPedido.setnIdVenta(ventaRegistrada.getnId());
			respuesta=twPedidoRepository.save(twPedido);		
		
			
			for (int i = 0; i < ventaDto.getListaValidada().size(); i++) {
				
			    twPedidoProducto.setsClavePedido(respuesta.getsCvePedido());
				twPedidoProducto.setdFechaPedido(new Date());
				twPedidoProducto.setnMotivoPedido(2L);
				twPedidoProducto.setnIdProducto(ventaDto.getListaValidada().get(i).getnIdProducto());
				twPedidoProducto.setnCantidadPedida(ventaDto.getListaValidada().get(i).getnCantidad());
				twPedidoProducto.setnIdProveedor(ventaDto.getListaValidada().get(i).getnIdProveedor());
				twPedidoProducto.setnIdUsuario(ventaDto.getIdUsuario());
				twPedidoProducto.setnIdPedido(respuesta.getnId());	
				twPedidoProducto.setnEstatus(false);
				
				pedidosProductoRepository.save(twPedidoProducto);
				
				
			}
			
			
			
		}
		
		return ventaRegistrada;

	}

	private void descuentaStock(TwVentasProducto twVentaProducto) {

		

			List<TwProductobodega> listaStock = new ArrayList<TwProductobodega>();
			
			listaStock = productoBodegaRepository.findBynIdProducto(twVentaProducto.getnIdProducto());
			
			int cantidad = twVentaProducto.getnCantidad();	

		

			while (cantidad != 0) {
				
				for (TwProductobodega listaStockBodega : listaStock) {
					
					
					ThStockProducto  thStockProducto= new ThStockProducto();
					thStockProducto.setdFecha(new Date());
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
							productosTraer.setdFecha(new Date());
							
							
							
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
							productosTraer.setdFecha(new Date());
							
						
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
		
		List<TvVentaDetalle> ventas =tvVentaDetalleRepository.consultaVentaDetalleIdEstatusVenta( nEstatusVenta);
		
		List<TvVentaDetalleDto> ventasNew =new ArrayList<TvVentaDetalleDto>();
		
		
		for (TvVentaDetalle detalleVenta : ventas) {
			
			TvVentaDetalleDto ventaDetalleNew =new TvVentaDetalleDto();
			double totalPagos = 0.0;
			
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
				ventaDetalleNew.setnAnticipo(0.0);				
				
			}
			
			
			
			List<TrVentaCobro> listaVentaCobro = this.trVentaCobroRepository.findBynIdVenta(ventaDetalleNew.getnId());
			
			if (listaVentaCobro.size()>0) {
				
				ventaDetalleNew.setnTotalAbono((double) listaVentaCobro.size());		
				
				for (int i = 0; i < listaVentaCobro.size(); i++) {
					
					totalPagos=totalPagos+listaVentaCobro.get(i).getnMonto();
				}
				
				ventaDetalleNew.setnSaldoTotal(ventaDetalleNew.getnTotalVenta()-totalPagos);
				ventaDetalleNew.setnAvancePago(totalPagos);
 				
			}else {
				ventaDetalleNew.setnTotalAbono(0.0);	
				ventaDetalleNew.setnSaldoTotal(ventaDetalleNew.getnTotalVenta());
				ventaDetalleNew.setnAvancePago(0.0);
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
	public TvVentaDetalle guardarVentaDetalle(TvVentaDetalle tvVentaDetalle) {
		
		
		
		TwVenta venta = ventasRepository.getById(tvVentaDetalle.getnId());
		TwCaja caja = cajaRepository.obtenerCajaVigente();
		TrVentaCobro ventaCobro = new TrVentaCobro();
		boolean cambio=false;
		
		
		
		if(tvVentaDetalle.getnTotalVenta()==tvVentaDetalle.getnAvancePago()+tvVentaDetalle.getnAnticipo() || (tvVentaDetalle.getnTotalVenta()-(tvVentaDetalle.getnAvancePago()+tvVentaDetalle.getnAnticipo()))<=0.01) {
			
			cambio=true;
			
		}
		else {
			cambio=false;
		}
		
		
		venta.setDescuento(tvVentaDetalle.getDescuento());
		
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
		
		
		
	
		System.out.println(venta);
	
		ventaCobro.setnIdVenta(venta.getnId());
		ventaCobro.setnIdCaja(venta.getnIdCaja());
		if(venta.getTcTipoVenta().getnId()!=3) {
			ventaCobro.setnMonto(tvVentaDetalle.getnTotalVenta());
			
		}
		else {
			ventaCobro.setnMonto(tvVentaDetalle.getnAnticipo());
		}
		
		ventaCobro.setnIdFormaPago(tvVentaDetalle.getTcFormapago().getnId());
		ventaCobro.setdFecha(new Date());
		ventaCobro.setnEstatus(1L);
		ventaCobro.setnIdCaja(caja.getnId());
		
				
		
		System.err.println(ventaCobro);
		
		ventasRepository.save(venta);
		trVentaCobroRepository.save(ventaCobro);
		
		
		return tvVentaDetalle;
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
				return this.VentasFacturaRepository.obtenerFacturas();
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
	public List<TvVentasFactura> consultaVentasFacturadas() {

		return this.VentasFacturaRepository.obtenerVentasFacturadas();
	}


	@Override
	public List<TrVentaCobro> consultarPagoId(Long idVenta) {
		
		return this.trVentaCobroRepository.findBynIdVenta(idVenta);
	}


	@Override
	public void eliminarCobroIdVenta(Long idVenta) {
		this.trVentaCobroRepository.deleteBynIdVenta(idVenta);
		
	}

	
}
