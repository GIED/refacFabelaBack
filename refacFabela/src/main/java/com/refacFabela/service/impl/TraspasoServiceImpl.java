package com.refacFabela.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TwAjustesInventario;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.repository.ProductoBodegaRepository;
import com.refacFabela.repository.TwAjusteInventarioRepository;
import com.refacFabela.service.TraspasoService;

@Service
public class TraspasoServiceImpl implements TraspasoService {
	
	@Autowired
	private ProductoBodegaRepository productoBodegaRepository;
	@Autowired
	private TwAjusteInventarioRepository twAjusteInventarioRepository;
	
	
	@Override
	@Transactional
	public TwProductobodega guardar(TwProductobodega productoBodega) {
		
		TwProductobodega pb =new TwProductobodega();
		
		pb=productoBodegaRepository.obtenerStockBodega(productoBodega.getnIdProducto(), productoBodega.getnIdBodega());
		productoBodega.setnCantidad(productoBodega.getnCantidad());
		
		return this.productoBodegaRepository.save(productoBodega);
	}


	@Override
	public List<TwProductobodega> guardarExterno(List<TwProductobodega> listProductoBodega) {
		
		
		
		return this.productoBodegaRepository.saveAll(listProductoBodega);
	}


	@Override
	public TwAjustesInventario guardarAjusteInventario(TwAjustesInventario twAjustesInventario) {
		 Date date = new Date();
		twAjustesInventario.setsFecha(date);
		
		
		return twAjusteInventarioRepository.save(twAjustesInventario);
	}

}
