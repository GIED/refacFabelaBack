package com.refacFabela.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.model.VwProductoMetaCompra;
import com.refacFabela.repository.VwProductoMetaCompraRepository;
import com.refacFabela.service.ComprasService;

@Service
public class CompraServiceImpl implements ComprasService {
	
	@Autowired
	private VwProductoMetaCompraRepository vwProductoMetaCompraRepository;
	

	public List<VwProductoMetaCompra> obtenerProductosVendidosFechaCompra(String FechaIncio, String FechaTermino) {
		 
		System.err.println("esto es lo que voy a consultar"+FechaIncio);
		System.err.println("esto es lo que voy a consultar"+FechaTermino);

		
		return vwProductoMetaCompraRepository.ultimafechaCompra(FechaIncio, FechaTermino);
	}

}
