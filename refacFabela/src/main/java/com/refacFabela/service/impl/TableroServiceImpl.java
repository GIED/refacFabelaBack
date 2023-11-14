package com.refacFabela.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.refacFabela.model.TvTotalesGeneralesTablero;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.model.VwVentaMesAno;
import com.refacFabela.model.VwVentaProductoAno;
import com.refacFabela.model.VwVentasAnoMesVendedores;
import com.refacFabela.model.VwVentasAnoVendedor;
import com.refacFabela.repository.TableroTotalesGeneralesRepository;
import com.refacFabela.repository.TwProductosVentaRepository;
import com.refacFabela.repository.VwVentaProductoRepository;
import com.refacFabela.service.TableroService;

@Service
public class TableroServiceImpl implements TableroService {

	@Autowired
	private TableroTotalesGeneralesRepository tableroTotalesGeneralesRepository;
	
	@Autowired
	private VwVentaProductoRepository vwVentaProductoRepository;
	
	@Autowired
	private TwProductosVentaRepository twProductosVentaRepository;
	
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

	@Override
	public List<VwVentasAnoVendedor> consultaVentaAnoVendedor(String ano) {
		
		return tableroTotalesGeneralesRepository.obtenerVentaAnoVendedor(ano);
	}

	@Override
	public List<VwVentasAnoMesVendedores> consultaVentaAnoMesVendedor(String ano, Long id) {
		
		return tableroTotalesGeneralesRepository.obtenerVentaAnoMesVendedor(ano, id);
	}

	@Override
	public List<TwVentasProducto> consultaVentasProducto(Long id) {
		
		return twProductosVentaRepository.findBynIdProducto(id);
	}
	
	

}
