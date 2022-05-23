package com.refacFabela.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.SimpleDateFormat;
import com.refacFabela.dto.AbonosDto;
import com.refacFabela.dto.TvStockProductoDto;
import com.refacFabela.dto.VentaProductoDto;
import com.refacFabela.model.TcBodega;
import com.refacFabela.model.TcHistoriaPrecioProducto;
import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TvStockProducto;
import com.refacFabela.model.TvVentaProductoMes;
import com.refacFabela.model.TvVentaStock;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwHistoriaIngresoProducto;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.model.TwProductosAlternativo;
import com.refacFabela.model.TwVenta;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.repository.AbonoVentaIdRepository;
import com.refacFabela.repository.CatalagoFormaPagoRepository;
import com.refacFabela.repository.CatalogoAnaquelRepository;
import com.refacFabela.repository.CatalogoBodegasRepository;
import com.refacFabela.repository.CatalogoNivelesRepository;
import com.refacFabela.repository.HistoriaPrecioProductoRepository;
import com.refacFabela.repository.ProductoBodegaRepository;
import com.refacFabela.repository.ProductoBodegasIdRepository;
import com.refacFabela.repository.ProductosAlternativosRepository;
import com.refacFabela.repository.ProductosRepository;
import com.refacFabela.repository.TvVentasStockRepository;
import com.refacFabela.repository.TwHistoriaIngresoProductoRepository;
import com.refacFabela.repository.TwProductosVentaRepository;
import com.refacFabela.repository.UsuariosRepository;
import com.refacFabela.repository.VentaProductoMesRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.service.ProductosService;
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
		tcProducto.setdFecha(utils.fechaSistema);

		// Se manda calcular el precio final, precio sin iva y precio peso del producto
		tcProducto = utilisServiceImp.calcularPrecio(tcProducto);

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
	public List<TcHistoriaPrecioProducto> historiaPrecioProducto(Long n_id) {

		return historiaPrecioProductoRepository.productoIdHistoria(n_id);
	}

	@Override
	public List<TwProductobodega> consultaProductoBodega(Long id) {
		
		return productoBodegaRepository.findBynIdProducto(id);
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
		newProducto.setdFecha(utils.fechaSistema);
		newProducto.setnIdclavesat(twProductosAlternativo.getTcProductoAlternativo().getnIdclavesat());
		
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
		twVentasProductoDTO.setdFechaEntregaAlmacen(utils.fechaSistema);
		twVentasProductoDTO.setnEstatusEntregaAlmacen(ventaProductoDto.getnEstatusEntregaAlmacen());
		
		twProductosVentaRepository.save(twVentasProductoDTO); 
		
		 String mensaje="Se guardo el estatus de entrega";
		return mensaje ;
	}

	@Override
	public TwAbono guardarAbono(TwAbono abonoDto) {
		
		
	
		abonoDto.setdFecha(utils.fechaSistema);
	
		
		System.err.println(abonoDto);
		
		abonoVentaIdRepository.save(abonoDto);
	
		return abonoDto ;
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

	@Override
	public TcProducto calcularNuevoPrecio(TcProducto tcProducto) {
		
		utils util= new utils();
		
		tcProducto=util.calcularPrecio(tcProducto);
		
		
		return tcProducto;
	}

	@Override
	public List<TvVentaStock> obtenerVentasStockFecha(Date dFechaInicio, Date dFechaFinal) {
		
		
		

		
		return tvVentasStockRepository.obtenerVentasStock(dFechaInicio, dFechaFinal);
	}

	
	
	
	/*private ProductoDto convertirAProductoDto(final TcProducto tcProducto) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		return modelMapper.map(tcProducto, ProductoDto.class);
		
	}*/

}
