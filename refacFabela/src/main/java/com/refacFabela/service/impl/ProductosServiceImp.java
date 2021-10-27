package com.refacFabela.service.impl;
import java.util.List;
import com.refacFabela.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.refacFabela.model.TcProducto;
import com.refacFabela.service.ProductosService;

@Service
public class ProductosServiceImp implements ProductosService {

	@Autowired
	private ProductosRepository productosRepository;
	
	@Override
	public List<TcProducto> obtenerProductos() {
		
		return productosRepository.findAll();
	}

	@Override
	public TcProducto obtenerProductoNoParte(String No_parte) {
		
		return productosRepository.findBysNoParte(No_parte);
	}

}
