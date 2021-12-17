package com.refacFabela.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.dto.VentaProductoDto;
import com.refacFabela.model.TcHistoriaPrecioProducto;
import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TvStockProducto;
import com.refacFabela.model.TvVentaProductoMes;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.model.TwProductosAlternativo;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.repository.AbonoVentaIdRepository;
import com.refacFabela.repository.HistoriaPrecioProductoRepository;
import com.refacFabela.repository.ProductoBodegaRepository;
import com.refacFabela.repository.ProductoBodegasIdRepository;
import com.refacFabela.repository.ProductosAlternativosRepository;
import com.refacFabela.repository.ProductosRepository;
import com.refacFabela.repository.TwProductosVentaRepository;
import com.refacFabela.repository.VentaProductoMesRepository;
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
		
		return twProductosVentaRepository.obtenerPrpductosVentaId(id);
	}

	@Override
	public List<TvVentaProductoMes> obtenerProductoVentaMesId(Long id) {
		
		List<TvVentaProductoMes> listaVentas=ventaProductoMesRepository.obtenerVentaProductoMesId(id);
		 
		for (int i = 0; i < listaVentas.size(); i++) {
			
			System.err.println(listaVentas.get(i));
			
		}
		
		return ventaProductoMesRepository.obtenerVentaProductoMesId(id);
	}

	
	
	
	/*private ProductoDto convertirAProductoDto(final TcProducto tcProducto) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		return modelMapper.map(tcProducto, ProductoDto.class);
		
	}*/

}
