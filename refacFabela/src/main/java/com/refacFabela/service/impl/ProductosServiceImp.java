package com.refacFabela.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ibm.icu.text.SimpleDateFormat;
import com.refacFabela.dto.CalculaPrecioDto;
import com.refacFabela.dto.ProductoDescuentoDto;
import com.refacFabela.dto.VentaProductoCancelaDto;
import com.refacFabela.dto.VentaProductoDto;
import com.refacFabela.model.TcBodega;
import com.refacFabela.model.TcCatalogogeneral;
import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TcHistoriaPrecioProducto;
import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TvStockProducto;
import com.refacFabela.model.TvStockProductoHist;
import com.refacFabela.model.TvVentaDetalle;
import com.refacFabela.model.TvVentaProductoMes;
import com.refacFabela.model.TvVentaStock;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwAjustesInventario;
import com.refacFabela.model.TwCaja;
import com.refacFabela.model.TwHistoriaIngresoProducto;
import com.refacFabela.model.TwMaquinaCliente;
import com.refacFabela.model.TwPedidoProducto;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.model.TwProductosAlternativo;
import com.refacFabela.model.TwSaldoUtilizado;
import com.refacFabela.model.TwVenta;
import com.refacFabela.model.TwVentaProductoCancela;
import com.refacFabela.model.TwVentaProductosTraer;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.model.VwSaldoVentaFavorDisponible;
import com.refacFabela.repository.AbonoVentaIdRepository;
import com.refacFabela.repository.CajaRepository;
import com.refacFabela.repository.CatalagoFormaPagoRepository;
import com.refacFabela.repository.CatalogoAnaquelRepository;
import com.refacFabela.repository.CatalogoBodegasRepository;
import com.refacFabela.repository.CatalogoNivelesRepository;
import com.refacFabela.repository.CatalogosRepository;
import com.refacFabela.repository.ClientesRepository;
import com.refacFabela.repository.HistoriaPrecioProductoRepository;
import com.refacFabela.repository.ProductoBodegaRepository;
import com.refacFabela.repository.ProductoBodegasIdRepository;
import com.refacFabela.repository.ProductosAlternativosRepository;
import com.refacFabela.repository.ProductosRepository;
import com.refacFabela.repository.TrVentaCobroRepository;
import com.refacFabela.repository.TvStockProductoHistRepository;
import com.refacFabela.repository.TvVentaDetalleRepository;
import com.refacFabela.repository.TvVentasStockRepository;
import com.refacFabela.repository.TwAjusteInventarioRepository;
import com.refacFabela.repository.TwHistoriaIngresoProductoRepository;
import com.refacFabela.repository.TwMaquinaClienteRepository;
import com.refacFabela.repository.TwProductoBodegaRepository;
import com.refacFabela.repository.TwProductosVentaRepository;
import com.refacFabela.repository.TwSaldoUtilizadoRepository;

import com.refacFabela.repository.TwSaldosRepository;
import com.refacFabela.repository.TwVentaProductoCancelaRepository;
import com.refacFabela.repository.TwVentaProductosTraerRepository;
import com.refacFabela.repository.UsuariosRepository;
import com.refacFabela.repository.VentaProductoMesRepository;
import com.refacFabela.repository.VentasProductoRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.repository.VwSaldoVentaFavorDisponibleRepository;
import com.refacFabela.service.ProductosService;
import com.refacFabela.utils.DateTimeUtil;
import com.refacFabela.utils.subirArchivo;
import com.refacFabela.utils.utils;

@Service
public class ProductosServiceImp implements ProductosService {

	@Autowired
	private ProductosRepository productosRepository;
	@Autowired
	private HistoriaPrecioProductoRepository historiaPrecioProductoRepository;
	@Autowired
	private ProductoBodegaRepository productoBodegaRepository;
	@Autowired
	private ProductosAlternativosRepository productosAlternativosRepository;
	@Autowired
	private UtilisServiceImp utilisServiceImp;
	@Autowired
	private ProductoBodegasIdRepository productoBodegasIdRepository;
	@Autowired
	private AbonoVentaIdRepository abonoVentaIdRepository;
	@Autowired
	private TwProductosVentaRepository twProductosVentaRepository;
	@Autowired
	private VentaProductoMesRepository ventaProductoMesRepository;
	@Autowired
	private VentasRepository ventasRepository;
	@Autowired
	private CatalagoFormaPagoRepository catalagoFormaPagoRepository;
	@Autowired
	private UsuariosRepository usuariosRepository;
	@Autowired
	private CatalogoBodegasRepository catalogoBodegasRepository;
	@Autowired 
	private CatalogoAnaquelRepository catalogoAnaquelRepository;
	@Autowired 
	private CatalogoNivelesRepository catalogoNivelesRepository;
	@Autowired 
	private TwHistoriaIngresoProductoRepository twHistoriaIngresoProductoRepository;
	@Autowired
	private TvVentasStockRepository tvVentasStockRepository;
	@Autowired
	private TrVentaCobroRepository trVentaCobroRepository;
	@Autowired 
	private TwMaquinaClienteRepository twMaquinaClienteRepository;
	@Autowired
	private TwSaldosRepository twSaldosRepository;
	@Autowired
	private CajaRepository cajaRepository;	
	
	@Autowired
	private CatalogosRepository catalogosRepository;
	
	@Autowired
	private TwVentaProductosTraerRepository twVentaProductosTraerRepository;

	@Autowired
	private TvStockProductoHistRepository tvStockProductoHistRepository;
	
	@Autowired
	private ClientesRepository clientesRepository;
	
	@Autowired
	private TwSaldoUtilizadoRepository twSaldoUtilizadoRepository;
	@Autowired
	private VwSaldoVentaFavorDisponibleRepository vwSaldoVentaFavorDisponibleRepository;
	
	@Autowired
	private TwVentaProductoCancelaRepository twVentaProductoCancelaRepository;
	
	@Autowired	
	private TwAjusteInventarioRepository twAjusteInventarioRepository;
	
	@Autowired
	private TvVentaDetalleRepository tvVentaDetalleRepository;
	
	@Autowired
	private TwProductoBodegaRepository twProductoBodegaRepository;

 
	 private final subirArchivo archivoHelper = new subirArchivo(); // O inyectado si se convierte a @Component

	    private static final String RUTA_IMAGENES = "/opt/imgprod/";


	
	

	@Override
	public List<TcProducto> obtenerProductos() {
		

		return productosRepository.findBynEstatus(1);
	}

	@Override
	public TcProducto obtenerProductoNoParte(String No_parte) {

		return productosRepository.findBysNoParte(No_parte);
	}

	@Override
	public List<TcProducto> obtenerProductoLike(String producto) {

		return productosRepository.ConsultaProductoLike(producto);
	}

	@Override
	public List<TcProducto> obtenerNoParteLike(String No_Parte) {

		return productosRepository.ConsultaNoParteLike(No_Parte);
	}

	@Override
	public TcProducto guardarProducto(TcProducto tcProducto) {

		// se asigana la fecha de la aplicación
		tcProducto.setdFecha(DateTimeUtil.obtenerHoraExactaDeMexico());

		// Se manda calcular el precio final, precio sin iva y precio peso del producto
		tcProducto = utilisServiceImp.calcularPrecio(tcProducto);
		
		
		try {
		    boolean imagenGuardada = archivoHelper.procesarImagenProducto(tcProducto, RUTA_IMAGENES);

		    if (imagenGuardada) {
		        String nombreArchivo = tcProducto.getsNoParte() != null ? tcProducto.getsNoParte() : "sin_nombre";
		        String rutaFinal = RUTA_IMAGENES + nombreArchivo + ".jpg";

		        // Guarda solo la ruta relativa o nombre según lo que necesites
		        tcProducto.setsRutaImagen(rutaFinal); 
		    } else {
		        System.out.println("No se guardó imagen o no era válida.");
		    }
		} catch (Exception e) {
		    // Registra y propaga la excepción de forma controlada
		    System.err.println("Error al guardar la imagen del producto: " + e.getMessage());
		    throw new RuntimeException("Error al procesar la imagen del producto", e);
		}

		// Guarda o actualiza el producto nuevo o existente
		TcProducto nuevoProducto = productosRepository.save(tcProducto);
		List<TcBodega> bodegas= new ArrayList<TcBodega>();
		System.err.println(nuevoProducto);
		TwProductobodega bodegaExiste= new TwProductobodega();
		
		
		bodegas=catalogoBodegasRepository.findAll();
		
		for (int i = 0; i < bodegas.size(); i++) {
		
			
			bodegaExiste=productoBodegaRepository.obtenerProductoBodega(nuevoProducto.getnId(), bodegas.get(i).getsBodega());
			System.err.println(bodegaExiste);
			if(bodegaExiste==null) {
				TwProductobodega bodegaNuevo= new TwProductobodega();				
			
				bodegaNuevo.setnId(null);
				bodegaNuevo.setnIdBodega(bodegas.get(i).getnId());
				bodegaNuevo.setnIdProducto(nuevoProducto.getnId());
				bodegaNuevo.setnEstatus(1L);
				bodegaNuevo.setnCantidad(0);
				bodegaNuevo.setnIdNivel(1L);
				bodegaNuevo.setnIdAnaquel(1L);
				bodegaNuevo.setTcAnaquel(catalogoAnaquelRepository.findBynId(1L));
				bodegaNuevo.setTcBodega(catalogoBodegasRepository.findBynId(bodegas.get(i).getnId()));
				bodegaNuevo.setTcNivel(catalogoNivelesRepository.findBynId(1L));
				bodegaNuevo.setTcProducto(productosRepository.findBynId(nuevoProducto.getnId()));		
				
				productoBodegaRepository.save(bodegaNuevo);
				
			}
			
		}

		// Se asiganan los valores al objeto tctcHistoriaPrecioProducto para guardar el
		// historio de precio de productos
		TcHistoriaPrecioProducto tcHistoriaPrecioProducto = new TcHistoriaPrecioProducto();
		tcHistoriaPrecioProducto.setnIdProducto(nuevoProducto.getnId());
		tcHistoriaPrecioProducto.setnPrecio(nuevoProducto.getnPrecio());
		tcHistoriaPrecioProducto.setsMoneda(nuevoProducto.getsMoneda());
		tcHistoriaPrecioProducto.setnIdGanancia(nuevoProducto.getnIdGanancia());
		tcHistoriaPrecioProducto.setnIdusuario(nuevoProducto.getnIdusuario());
		tcHistoriaPrecioProducto.setdFecha(nuevoProducto.getdFecha());

		// se guarda el historio de precio de los productos
		historiaPrecioProductoRepository.save(tcHistoriaPrecioProducto);

		return nuevoProducto;
	}
	
	@Override
	public TcProducto guardarProductoGeneral(TcProducto tcProducto) {

		

		return productosRepository.save(tcProducto);
	}

	@Override
	public List<TcHistoriaPrecioProducto> historiaPrecioProducto(Long n_id) {

		return historiaPrecioProductoRepository.productoIdHistoria(n_id);
	}

	@Override
	public List<TwProductobodega> consultaProductoBodega(Long id) {
		
		return productoBodegaRepository.findBynIdProducto(id);
	}
public TwProductobodega consultaProductoBod(Long id, Long nidBodega) {
		
		return productoBodegaRepository.findBynIdProductoIdBodega(id, nidBodega);
	}

	@Override
	public List<TwProductobodega> obtenerInventaroEsp(Long idBodega, Long idAnaquel, Long idNivel) {
		//
		return productoBodegaRepository.obtenerInventaroEsp(idBodega, idAnaquel, idNivel);
	}

	@Override
	@Transactional
	public List<TwProductosAlternativo> obtenerProductosAlternativos(Long nId) {
		
		return productosAlternativosRepository.consultaProductosAlternativos(nId, 1);
	}
public List<TwProductosAlternativo> obtenerProductosAlternativosDescuento(Long nId, Long nIdCliente) {
		
	TcCliente cliente =new TcCliente();
	TcCatalogogeneral tipoCambio=new TcCatalogogeneral();
	
	utils util=new  utils();
	tipoCambio=catalogosRepository.findBysClave("ValorCambio");
	
	cliente=clientesRepository.getById(nIdCliente);
	
	 List<TwProductosAlternativo> twProductosAlternativo=productosAlternativosRepository.consultaProductosAlternativos(nId, 1);
	
	if(cliente.getnDescuento().equals(true)) {
		
		for (int i = 0; i < twProductosAlternativo.size(); i++) {					
			twProductosAlternativo.get(i).setTcProductoAlternativo(util.calcularPrecio(twProductosAlternativo.get(i).getTcProductoAlternativo(), tipoCambio.getnValor(), 0.0, 1, true));
	     }		
	}
	else {	
		
         for (int j = 0; j < twProductosAlternativo.size(); j++) {			
			twProductosAlternativo.get(j).setTcProductoAlternativo(util.calcularPrecio(twProductosAlternativo.get(j).getTcProductoAlternativo(), tipoCambio.getnValor(), 0.0, 1, false));		
		}
		
	}
	
	
	
	
	
	
	
	
	

		return twProductosAlternativo;
	}

	@Override
	@Transactional
	public TwProductosAlternativo guardarProductoAlternativo(TwProductosAlternativo twProductosAlternativo) {
		
		TcProducto newProducto = new TcProducto();
		newProducto.setnId(twProductosAlternativo.getTcProductoAlternativo().getnId());
		newProducto.setsNoParte(twProductosAlternativo.getTcProductoAlternativo().getsNoParte());
		newProducto.setsProducto(twProductosAlternativo.getTcProductoAlternativo().getsProducto());
		newProducto.setsDescripcion(twProductosAlternativo.getTcProductoAlternativo().getsDescripcion());
		newProducto.setsMarca(twProductosAlternativo.getTcProductoAlternativo().getsMarca());
		newProducto.setnIdCategoria(twProductosAlternativo.getTcProductoAlternativo().getnIdCategoria());
		newProducto.setnIdCategoriaGeneral(twProductosAlternativo.getTcProductoAlternativo().getnIdCategoriaGeneral());
		newProducto.setnPrecio(twProductosAlternativo.getTcProductoAlternativo().getnPrecio());
		newProducto.setsMoneda(twProductosAlternativo.getTcProductoAlternativo().getsMoneda());
		newProducto.setnIdGanancia(twProductosAlternativo.getTcProductoAlternativo().getnIdGanancia());
		newProducto.setnIdusuario(twProductosAlternativo.getTcProductoAlternativo().getnIdusuario());
		newProducto.setnEstatus(twProductosAlternativo.getTcProductoAlternativo().getnEstatus());
		newProducto.setdFecha(DateTimeUtil.obtenerHoraExactaDeMexico());
		newProducto.setnIdclavesat(twProductosAlternativo.getTcProductoAlternativo().getnIdclavesat());
		newProducto.setnIdMarca(twProductosAlternativo.getTcProductoAlternativo().getnIdMarca());
		newProducto.setnIdDescuento(twProductosAlternativo.getTcProductoAlternativo().getnIdDescuento());
		newProducto.setsIdBar(twProductosAlternativo.getTcProductoAlternativo().getsIdBar());

		
		// Se manda calcular el precio final, precio sin iva y precio peso del producto
		newProducto = utilisServiceImp.calcularPrecio(newProducto);
		// Guarda o actualiza el producto nuevo o existente
		TcProducto productoAlternativo = productosRepository.save(newProducto);
		
		System.out.println(productoAlternativo);
		
		
		
		twProductosAlternativo.setnIdProductoAlternativo(productoAlternativo.getnId());
		twProductosAlternativo.setTcProductoAlternativo(productoAlternativo);
		
		System.out.println("twProductosAlternativo: "+twProductosAlternativo);
		
		TwProductosAlternativo newproductoAlternativo = productosAlternativosRepository.save(twProductosAlternativo);
		
		return newproductoAlternativo;
	}

	@Override
	public TvStockProducto obtenerStockProductoId(Long id) {
		
		return productoBodegasIdRepository.findBynIdProducto(id) ;
	}

	@Override
	public List<VentaProductoDto> obtenerProductosVentaId(Long id) {
		List<VentaProductoDto> ventaProductoDto= new ArrayList<VentaProductoDto>();
		TwProductobodega productoBodega = new TwProductobodega();
		
		ventaProductoDto=twProductosVentaRepository.obtenerPrpductosVentaId(id);
		
		for (int i = 0; i < ventaProductoDto.size(); i++) {
			productoBodega=productoBodegaRepository.obtenerProductoBodega(ventaProductoDto.get(i).getnIdProducto(),"LOCAL");
			ventaProductoDto.get(i).setsBodega(productoBodega.getTcBodega().getsBodega());
			ventaProductoDto.get(i).setsAnaquel(productoBodega.getTcAnaquel().getsAnaquel());
			ventaProductoDto.get(i).setsNivel(productoBodega.getTcNivel().getsNivel());
			
		}
	
		System.err.println(ventaProductoDto);
		
		return ventaProductoDto;
	}

	@Override
	public List<TvVentaProductoMes> obtenerProductoVentaMesId(Long id) {
			
		return ventaProductoMesRepository.obtenerVentaProductoMesId(id);
	}

	@Override
	public String consultaVentaProductoId(TwVentasProducto ventaProductoDto) {
		
	
		
		TwVentasProducto twVentasProductoDTO=new TwVentasProducto();
		
		System.err.println(ventaProductoDto.getnId());
		
		twVentasProductoDTO=twProductosVentaRepository.obtenerPrpductosId(ventaProductoDto.getnId());
		twVentasProductoDTO.setdFechaEntregaAlmacen(DateTimeUtil.obtenerHoraExactaDeMexico());
		twVentasProductoDTO.setnEstatusEntregaAlmacen(ventaProductoDto.getnEstatusEntregaAlmacen());
		
		twProductosVentaRepository.save(twVentasProductoDTO); 
		
		 String mensaje="Se guardo el estatus de entrega";
		return mensaje ;
	}

	@Override
	public TwAbono guardarAbono(TwAbono abonoDto) {
	    abonoDto.setdFecha(DateTimeUtil.obtenerHoraExactaDeMexico());
	    abonoVentaIdRepository.save(abonoDto);

	    BigDecimal total_venta = BigDecimal.ZERO;
	    BigDecimal total_abonos = BigDecimal.ZERO;

	    // Se consulta el total de la venta
	    List<TwVentasProducto> twVentasProducto = twProductosVentaRepository.findBynIdVenta(abonoDto.getnIdVenta());
	    for (TwVentasProducto producto : twVentasProducto) {
	        total_venta = total_venta.add(producto.getnTotalPartida());
	    }
	    System.err.println("Total venta: " + total_venta);

	    // Se consulta el total de abonos
	    List<TwAbono> twAbonos = abonoVentaIdRepository.abonosVenta(abonoDto.getnIdVenta());
	    for (TwAbono abono : twAbonos) {
	        total_abonos = total_abonos.add(abono.getnAbono());
	    }
	    System.err.println("Total abonos: " + total_abonos);

	    // Si el total de abonos es igual al total de la venta, se registra la fecha de pago
	    if (total_venta.compareTo(total_abonos) == 0) {
	        TwVenta twVenta = ventasRepository.findBynId(abonoDto.getnIdVenta());
	        twVenta.setdFechaPagoCredito(DateTimeUtil.obtenerHoraExactaDeMexico());
	        ventasRepository.save(twVenta);
	    }

	    return abonoDto;
	}

	@Override
	public String guardaVentaProducto(VentaProductoDto ventaProductoDto) {
		TwVentasProducto twVentasProducto=new TwVentasProducto() ;
		TwVenta twVenta=new TwVenta() ;
		List<TwVentasProducto> listaVentasProducto=new ArrayList<TwVentasProducto>();
		
		int totalEntregados=0;
		
		twVentasProducto=twProductosVentaRepository.getById(ventaProductoDto.getnId());
		twVentasProducto.setnEstatusEntregaAlmacen(ventaProductoDto.getnEstatusEntregaAlmacen());
		System.err.println(twVentasProducto );
				 
		twVentasProducto=twProductosVentaRepository.save(twVentasProducto);
		twVenta=ventasRepository.findBynId(twVentasProducto.getnIdVenta());
		
		listaVentasProducto=twProductosVentaRepository.findBynIdVenta(twVentasProducto.getnIdVenta());
		
		for (int i = 0; i < listaVentasProducto.size(); i++) {
			
			if(listaVentasProducto.get(i).getnEstatusEntregaAlmacen()==1) {
				totalEntregados=totalEntregados+1;
			}
			
		}
		
		if(totalEntregados==listaVentasProducto.size()) {
			
			twVenta.setnIdEstatusVenta(3L);			
			ventasRepository.save(twVenta);
			
		}
		
		

		
			return "Se guardo con éxito";
	}

	@Override
	public List<TwHistoriaIngresoProducto> historiaIngresoProducto(Long n_id) {
		
		
		
		return twHistoriaIngresoProductoRepository.obtenerIngresoProductos(n_id);
	}


	public TcProducto calcularNuevoPrecio( ProductoDescuentoDto productoDescuentoDto) {
		System.err.println("entre a calcular el precio");
		utils util= new utils();
		TcCatalogogeneral tipoCambio=new TcCatalogogeneral();
		boolean descuento = false;
		
		tipoCambio=catalogosRepository.findBysClave("ValorCambio");
		System.err.println(productoDescuentoDto);
		
		if(productoDescuentoDto.getTcCliente().getnDescuento().equals(true) ) {
			System.err.println("Entre a cambiar la badera para hacer el descuento");
			descuento=true;
			
		}
		
		 
		
		TcProducto tcProducto=new TcProducto();
		tcProducto=productoDescuentoDto.getTcProducto();
		System.err.println("entre a calcular el precio del producto");
		
		tcProducto=util.calcularPrecio(tcProducto, tipoCambio.getnValor(),0.0,1, descuento);
		
		
		return tcProducto;
	}

	@Override
	public List<TvVentaStock> obtenerVentasStockFecha(String dFechaInicio, String dFechaFinal) {
		
		
//		String fechaDeLanzamiento = "2022-05-20"; 
//		String fechaDeLanzamiento2 = "2022-01-20"; 
//
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dataFormateada = formato.parse(dFechaInicio);
			Date dataFormateada2 = formato.parse(dFechaFinal);
			
			return  tvVentasStockRepository.obtenerVentasStock(dataFormateada, dataFormateada2);
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			
		} 

		
		
	}

	@Override
	public List<TrVentaCobro> obtenerPägosParciales(Long nIdVenta) {
		
		return trVentaCobroRepository.findBynIdVenta(nIdVenta);
	}

	@Override
	public List<TwMaquinaCliente> obtenerMaquinasCliente(Long nIdClinete) {
		// TODO Auto-generated method stub
		return twMaquinaClienteRepository.buscarMaquinasCliente(nIdClinete);
	}

	@Override
	public TwMaquinaCliente guardarMaquina(TwMaquinaCliente twMaquinaCliente) {
		// TODO Auto-generated method stub
		return twMaquinaClienteRepository.save(twMaquinaCliente);
	}

	@Override
	public VentaProductoDto cacelarVentaProducto(VentaProductoCancelaDto ventaProductoCancelaDto) {
		
		System.err.println(ventaProductoCancelaDto);
		
		TwVentasProducto twVentasProducto= new TwVentasProducto();	
		List<TwVentasProducto> twListaVentasProducto= new ArrayList<TwVentasProducto>();	
		TwProductobodega twProductobodega = new TwProductobodega();
		TwVentaProductoCancela twVentaProductoCancela= new TwVentaProductoCancela();
		TwSaldoUtilizado twSaldoUtilizado= new TwSaldoUtilizado();
		TcProducto tcProducto=new TcProducto();
		TwVentasProducto twVentasProductoCalculado= new TwVentasProducto();	
		
		TwVenta twVenta= new TwVenta();
		utils util= new utils();
		TwCaja caja = new TwCaja();
		
		BigDecimal totalUnitario=new BigDecimal("0");
		BigDecimal totalPartida=new BigDecimal("0");
		
		
		
		List<TrVentaCobro> listaTrVentaCobro=new ArrayList<TrVentaCobro>();

		
		twVentasProducto=twProductosVentaRepository.obtenerProductoVenta(ventaProductoCancelaDto.VentaProductoDto.getnIdVenta(), ventaProductoCancelaDto.VentaProductoDto.getnIdProducto());
		
		/*Se obtiene la caja activa*/
		caja=cajaRepository.obtenerCajaVigente();
		/*Se obtiene el stock del producto en bodega*/
		twProductobodega=productoBodegaRepository.obtenerProductoBodega(ventaProductoCancelaDto.VentaProductoDto.getnIdProducto(), "LOCAL");
		/*Se obiene los datos generales de la venta*/
		twVenta=ventasRepository.findBynId(ventaProductoCancelaDto.VentaProductoDto.getnIdVenta());
		
		/*Se valida la cantidad de productos a cancelar*/
		
		if(ventaProductoCancelaDto.nCancela==twVentasProducto.getnCantidad()) {
			twVentasProducto.setnEstatus(0);	
			ventaProductoCancelaDto.VentaProductoDto.setnEstatus(0);
			/*Se gaurda la cancelación del produycto*/
			//twProductosVentaRepository.save(twVentasProducto);
			
			/*Se re integra el producto a la bodega*/
			twProductobodega.setnCantidad(twProductobodega.getnCantidad()+ventaProductoCancelaDto.VentaProductoDto.getnCantidad());			
			//productoBodegaRepository.save(twProductobodega);	
			
			/*Se guarda el producto cancelado en productos cancela*/
			if(twVenta.getnIdEstatusVenta()>1) {
				
		    /*verificamos si hay penalización*/
				if(ventaProductoCancelaDto.penaliza) {
					
					totalUnitario = twVentasProducto.getnTotalUnitario().subtract(twVentasProducto.getnTotalUnitario().multiply(BigDecimal.valueOf(0.20)));
					totalPartida = twVentasProducto.getnTotalPartida().subtract(twVentasProducto.getnTotalPartida().multiply(BigDecimal.valueOf(0.20)));
					
					BigDecimal precioUnitario = totalUnitario.divide(BigDecimal.valueOf(1.16), 10, RoundingMode.DOWN);
					twVentaProductoCancela.setnPrecioUnitario(precioUnitario);

					// IVA unitario = precioUnitario * 0.16
					BigDecimal ivaUnitario = precioUnitario.multiply(BigDecimal.valueOf(0.16));
					twVentaProductoCancela.setnIvaUnitario(ivaUnitario);

					// Total unitario (con IVA incluido)
					twVentaProductoCancela.setnTotalUnitario(totalUnitario);

					// Precio de la partida
					twVentaProductoCancela.setnPrecioPartida(totalPartida);

					// Penalización = total original - total cancelado
					BigDecimal penaliza = twVentasProducto.getnTotalPartida().subtract(totalPartida);
					twVentaProductoCancela.setPenaliza(penaliza);
				
					
				}
				else {
					
					twVentaProductoCancela.setnPrecioUnitario(DateTimeUtil.truncarDosDecimales(twVentasProducto.getnPrecioUnitario()));
					twVentaProductoCancela.setnIvaUnitario(DateTimeUtil.truncarDosDecimales(twVentasProducto.getnIvaUnitario()));
					twVentaProductoCancela.setnTotalUnitario(DateTimeUtil.truncarDosDecimales(twVentasProducto.getnTotalUnitario()));
					twVentaProductoCancela.setnPrecioPartida(DateTimeUtil.truncarDosDecimales(twVentasProducto.getnTotalPartida()));
				
					
					
				}
				
				
			twVentaProductoCancela.setnIdVenta(twVentasProducto.getnIdVenta());
			twVentaProductoCancela.setnIdProductos(twVentasProducto.getnIdProducto());
			twVentaProductoCancela.setnCantidad(twVentasProducto.getnCantidad());
			twVentaProductoCancela.setsMotivo(ventaProductoCancelaDto.sMotivo);
		
			twVentaProductoCancela.setnIdUsuario(twVentasProducto.getnIdUsuario());
			twVentaProductoCancela.setdFecha(DateTimeUtil.obtenerHoraExactaDeMexico());
			twVentaProductoCancela.setnIdCaja(caja.getnId());			
			twSaldosRepository.save(twVentaProductoCancela);
			}
			
			if(twVenta.getnTipoPago()==1L && twVenta.getnIdEstatusVenta()>1) {
							
				twSaldoUtilizado.setnIdUsuario(twVenta.getnIdUsuario());
				twSaldoUtilizado.setnIdCaja(caja.getnId());
				twSaldoUtilizado.setnIdVenta(twVenta.getnId());
				twSaldoUtilizado.setnSaldoTotal(twVentasProducto.getnTotalPartida());
				twSaldoUtilizado.setnSaldoUtilizado(twVentasProducto.getnTotalPartida());
				twSaldoUtilizado.setdFecha(DateTimeUtil.obtenerHoraExactaDeMexico());
				twSaldoUtilizado.setnEstatus(true);				
			twSaldoUtilizadoRepository.save(twSaldoUtilizado);					
			}			
		}
		else {
				
			ventaProductoCancelaDto.VentaProductoDto.setnEstatus(1);
			
			System.err.println("Entre a cancelar parcalemte");
		    
		    /*Se consulta el producto para mandar a calcular el precio del producto*/
		    
		    tcProducto=productosRepository.findBynId(twVentasProducto.getnIdProducto()); 
		    tcProducto.setnPrecioSinIva(twVentasProducto.getnPrecioUnitario());		    
		    
		    twVentasProductoCalculado=util.calcularPrecioGuardar(tcProducto, twVentasProducto.getnCantidad()-ventaProductoCancelaDto.nCancela);		    
		    twVentasProducto.setnEstatus(1);
		    twVentasProducto.setnCantidad(twVentasProducto.getnCantidad()-ventaProductoCancelaDto.nCancela);
		    twVentasProducto.setnPrecioUnitario(twVentasProductoCalculado.getnPrecioUnitario());
		    twVentasProducto.setnIvaUnitario(twVentasProductoCalculado.getnIvaUnitario());
		    twVentasProducto.setnTotalUnitario(twVentasProductoCalculado.getnTotalUnitario());
		    twVentasProducto.setnPrecioPartida(twVentasProductoCalculado.getnPrecioPartida());
		    twVentasProducto.setnIvaPartida(twVentasProductoCalculado.getnIvaPartida());
		    twVentasProducto.setnTotalPartida(twVentasProductoCalculado.getnTotalPartida());
		    /*Se gaurda la cancelación del producto*/
		    twProductosVentaRepository.save(twVentasProducto);	
			if(twVenta.getnIdEstatusVenta()>1) {
			twVentasProductoCalculado=util.calcularPrecioGuardar(tcProducto, ventaProductoCancelaDto.nCancela);			
			twVentaProductoCancela.setnIdVenta(twVentasProducto.getnIdVenta());
			twVentaProductoCancela.setnIdProductos(twVentasProducto.getnIdProducto());
			twVentaProductoCancela.setnCantidad(ventaProductoCancelaDto.nCancela);
			
			if(ventaProductoCancelaDto.penaliza) {
				
				// Descuento del 20%
				BigDecimal descuento = BigDecimal.valueOf(0.20);

				// totalUnitario = totalUnitario - (totalUnitario * 0.20)
				 totalUnitario = twVentasProducto.getnTotalUnitario()
				    .subtract(twVentasProducto.getnTotalUnitario().multiply(descuento));

				// totalPartida = totalPartida - (totalPartida * 0.20)
				 totalPartida = twVentasProducto.getnTotalPartida()
				    .subtract(twVentasProducto.getnTotalPartida().multiply(descuento));

				// Precio unitario sin IVA: totalUnitario / 1.16
				BigDecimal precioUnitario = totalUnitario.divide(BigDecimal.valueOf(1.16), 10, RoundingMode.DOWN);
				twVentaProductoCancela.setnPrecioUnitario(precioUnitario);

				// IVA unitario: precioUnitario * 0.16
				BigDecimal ivaUnitario = precioUnitario.multiply(BigDecimal.valueOf(0.16));
				twVentaProductoCancela.setnIvaUnitario(ivaUnitario);

				// Total unitario con IVA
				twVentaProductoCancela.setnTotalUnitario(totalUnitario);

				// Precio partida final
				twVentaProductoCancela.setnPrecioPartida(totalPartida);

				// Penalización = original - ajustado
				
				BigDecimal penaliza = twVentasProducto.getnTotalPartida().subtract(totalPartida);
				twVentaProductoCancela.setPenaliza(penaliza);

				
				
			
				
			}
			else {
				
				twVentaProductoCancela.setnPrecioUnitario(DateTimeUtil.truncarDosDecimales(twVentasProducto.getnPrecioUnitario()));
				twVentaProductoCancela.setnIvaUnitario(DateTimeUtil.truncarDosDecimales( twVentasProducto.getnIvaUnitario()));
				twVentaProductoCancela.setnTotalUnitario(DateTimeUtil.truncarDosDecimales( twVentasProducto.getnTotalUnitario()));
				twVentaProductoCancela.setnPrecioPartida(DateTimeUtil.truncarDosDecimales( twVentasProducto.getnTotalPartida()));
			
				
				
			}
			
			

		    twVentaProductoCancela.setsMotivo(ventaProductoCancelaDto.sMotivo);
			twVentaProductoCancela.setnIdUsuario(twVentasProducto.getnIdUsuario());
			twVentaProductoCancela.setdFecha(DateTimeUtil.obtenerHoraExactaDeMexico());
			twVentaProductoCancela.setnIdCaja(caja.getnId());
			twSaldosRepository.save(twVentaProductoCancela);
			}
			
			/*Se re integra el producto a la bodega*/
			twProductobodega.setnCantidad(twProductobodega.getnCantidad()+ventaProductoCancelaDto.nCancela);			
		    //productoBodegaRepository.save(twProductobodega);
			
			if(twVenta.getnTipoPago()==1L && twVenta.getnIdEstatusVenta()>1) {
			
				
				twSaldoUtilizado.setnIdUsuario(twVenta.getnIdUsuario());
				twSaldoUtilizado.setnIdCaja(caja.getnId());
				twSaldoUtilizado.setnIdVenta(twVenta.getnId());
				twSaldoUtilizado.setnSaldoTotal(twVentasProducto.getnTotalPartida());
				twSaldoUtilizado.setnSaldoUtilizado(twVentasProducto.getnTotalPartida());
				twSaldoUtilizado.setdFecha(DateTimeUtil.obtenerHoraExactaDeMexico());
				twSaldoUtilizado.setnEstatus(true);				
				twSaldoUtilizadoRepository.save(twSaldoUtilizado);						
			}			
		}	
		
		
		/*Se cambia el estatus del saldo para generar el saldo a favor*/
		
				
		listaTrVentaCobro=trVentaCobroRepository.findBynIdVenta(twVenta.getnId());
		System.err.println(listaTrVentaCobro);
		if(listaTrVentaCobro.size()>0) {								
						twVenta.setnSaldo(true);				
						ventasRepository.save(twVenta);	
									
			}
			
		
		/*Se cabia el estatus de cancelación*/
		
		twListaVentasProducto=twProductosVentaRepository.findBynIdVenta(ventaProductoCancelaDto.VentaProductoDto.getnIdVenta());		
		/*Se valida que este cobrada*/
		if(twVenta.getnIdEstatusVenta()>1) {		
				if(twListaVentasProducto.size()>0) {			
					twVenta.setnIdEstatusVenta(6L);			
				}
				else {			
					twVenta.setnIdEstatusVenta(5L);			
				}					
		}
		
		if(twVenta.getnIdEstatusVenta()<2 && twListaVentasProducto.size()==0) {
			twVenta.setnIdEstatusVenta(5L);		
		}
		
		ventasRepository.save(twVenta);
		return ventaProductoCancelaDto.VentaProductoDto;
	}

	@Override
	public TwVentaProductosTraer ventaProductosTraer(TwVentaProductosTraer ventaProductosTraer) {
		
		return twVentaProductosTraerRepository.save(ventaProductosTraer);
	}

	@Override
	public List<TwVentaProductosTraer> obtenerProductosTraer(Long nIdVenta) {
	
	
		
		return twVentaProductosTraerRepository.findBynIdVenta(nIdVenta);
	}

	@Override
	public List<TvStockProductoHist> obtenerStockProductoHist(Long id) {
		
		return tvStockProductoHistRepository.obtenerHistoriaStockProducto(id);
	}

	@Override
	public List<TwVentaProductoCancela> obtenerProductosCancelaId(Long id) {
		
		return twSaldosRepository.productosCancelados(id);
	}

	@Override
	public TwSaldoUtilizado guardarSaldoUtilizado(TwSaldoUtilizado twSaldoUtilizado) {
		
		return twSaldoUtilizadoRepository.save(twSaldoUtilizado);
	}

	@Override
	public List<TwSaldoUtilizado> obtenerSaldosUtilizados(Long nIdClinete) {
		
		
		
		return twSaldoUtilizadoRepository.consultaSaldosUtilizados(nIdClinete);
	}

	@Override
	public VwSaldoVentaFavorDisponible obtenerSaldoVentaCancela(Long nIdVenta) {
		
		VwSaldoVentaFavorDisponible vwSaldoVentaFavorDisponible=new VwSaldoVentaFavorDisponible();
		vwSaldoVentaFavorDisponible=vwSaldoVentaFavorDisponibleRepository.buscarSaldoVenta(nIdVenta);
		
		
		
		return vwSaldoVentaFavorDisponible;
	}

	@Override
	public List<TwVentaProductoCancela> obtenerVentaProductoCanela(String fechaInicio, String fechaTermino) {
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dataFormateada = formato.parse(fechaInicio);
			Date dataFormateada2 = formato.parse(fechaTermino);
			
			return twVentaProductoCancelaRepository.findByBuscar(dataFormateada, dataFormateada2);
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			
		} 
	
		
		
		
	
	}

	@Override
	public List<TcProducto> obtenerProductoId(Long nId) {
		
		return productosRepository.consultarPorId(nId);
	}

	@Override
	public List<TwAjustesInventario> obtenerVentaProductoAjusteInventario(String fechaInicio, String fechaTermino) {
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		try {
			
			
			
			Date dataFormateada = formato.parse(fechaInicio);
			Date dataFormateada2 = formato.parse(fechaTermino);
			
			System.err.println(dataFormateada);
			System.err.println(dataFormateada2);
			
			return twAjusteInventarioRepository.findByBuscar(dataFormateada, dataFormateada2);
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			
		} 
	}

	@Override
	public TwVentasProducto obtenerVentaProductoId(Long nIdVenta, Long nIdProducto) {
		
		
		
		
		return twProductosVentaRepository.obtenerProductoVenta(nIdVenta, nIdProducto);
	}

	@Override
	public CalculaPrecioDto calcularNuevoPrecioAjustado(CalculaPrecioDto calculaPrecioDto) {
		System.err.println(calculaPrecioDto);
		TcProducto tcProducto=new TcProducto();
		utils util =new utils();
		TwVentasProducto twVentaProducto=new TwVentasProducto();
		
		tcProducto.setnPrecioSinIva(calculaPrecioDto.getPrecioUnitario());
		
		twVentaProducto=util.calcularPrecioGuardar(tcProducto, calculaPrecioDto.getCantidad());
		
		calculaPrecioDto.setIvaUnitario(twVentaProducto.getnIvaUnitario());
		calculaPrecioDto.setTotalUnitario(twVentaProducto.getnTotalUnitario());
		calculaPrecioDto.setPrecioPartida(twVentaProducto.getnPrecioPartida());
		calculaPrecioDto.setIvaPartida(twVentaProducto.getnIvaPartida());
		calculaPrecioDto.setTotalPartida(twVentaProducto.getnTotalPartida());
		
		
		return calculaPrecioDto;
	}

	@Override
	public TwVentasProducto actualizaVentaProducto(TwVentasProducto twVentasProducto) {
		// TODO Auto-generated method stub
		return twProductosVentaRepository.save(twVentasProducto);
	}

	@Override
	public TvVentaDetalle obtenerVentaDetalleId(Long nIdVenta) {
		
		return tvVentaDetalleRepository.consultaVentaDetalleId(nIdVenta);
	}

	@Override
	public TrVentaCobro guardarVentaCobro(TrVentaCobro trVentaCobro) {
		
		return trVentaCobroRepository.save(trVentaCobro);
	}

	@Override
	public List<TwVentaProductoCancela> obtenerVentaProductoCancelaId(Long nIdVenta) {		
		
		
		return twVentaProductoCancelaRepository.findByVenta(nIdVenta);
	}

	@Override
	public TcProducto obtenerProductoBeanId(Long nId) {
		
		return productosRepository.getById(nId);
	}

	@Override
	public TwProductobodega guardarProductoBodega(TwProductobodega twProductobodega) {
		
		return twProductoBodegaRepository.save(twProductobodega);
	}

	@Override
	public TwPedidoProducto pedidoProductoIngreso(TwPedidoProducto wPedidoProducto) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
    /*obtiene la imagen del producto*/
	@Override
	public String obtenerImagenBase64(String ruta) {				
		
		return archivoHelper.obtenerImagenBase64(ruta);
	}

	
	

}
