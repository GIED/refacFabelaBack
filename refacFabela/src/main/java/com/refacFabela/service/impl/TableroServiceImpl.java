package com.refacFabela.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.refacFabela.model.TvTotalesGeneralesTablero;
import com.refacFabela.model.VwVentaMesAno;
import com.refacFabela.model.VwVentaProductoAno;
import com.refacFabela.repository.TableroTotalesGeneralesRepository;
import com.refacFabela.repository.VwVentaProductoRepository;
import com.refacFabela.service.TableroService;

@Service
public class TableroServiceImpl implements TableroService {

	@Autowired
	private TableroTotalesGeneralesRepository tableroTotalesGeneralesRepository;
	
	@Autowired
	private VwVentaProductoRepository vwVentaProductoRepository;
	
	@Override
	public TvTotalesGeneralesTablero consultaTotalesTablero() {
		
		return tableroTotalesGeneralesRepository.findBynId(1L);
	}

	@Override
	public List<VwVentaMesAno> consultaVentaMesAno(String ano) {
		
		return tableroTotalesGeneralesRepository.obtenerVentaMesAno(ano);
	}

	@Override
	public List<VwVentaProductoAno> consultaVentaProductoAno(String ano) {
		
		return vwVentaProductoRepository.obtenerVentaProductoAno(ano);
	}
	
	

}
