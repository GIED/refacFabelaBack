package com.refacFabela.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TwProductobodega;
import com.refacFabela.repository.ProductoBodegaRepository;
import com.refacFabela.service.TraspasoService;

@Service
public class TraspasoServiceImpl implements TraspasoService {
	
	@Autowired
	private ProductoBodegaRepository productoBodegaRepository;
	
	
	@Override
	@Transactional
	public TwProductobodega guardar(TwProductobodega productobodega) {
		
		return this.productoBodegaRepository.save(productobodega);
	}

}
