package com.refacFabela.service.impl;

import java.util.Date;
import java.util.List;
import com.refacFabela.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TcCatalogogeneral;
import com.refacFabela.model.TcHistoriaPrecioProducto;
import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.service.ProductosService;
import com.refacFabela.utils.utils;

@Service
public class ProductosServiceImp implements ProductosService {

	@Autowired
	private ProductosRepository productosRepository;
	@Autowired
	private CatalogosRepository catalogosRepository;
	@Autowired
	private HistoriaPrecioProductoRepository historiaPrecioProductoRepository;
	@Autowired
	private UtilisServiceImp utilisServiceImp;
	@Autowired
	private ProductoBodegaRepository productoBodegaRepository;

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

		return historiaPrecioProductoRepository.findBynIdProducto(n_id);
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

}
