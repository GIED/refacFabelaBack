package com.refacFabela.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TvTotalesGeneralesTablero;
import com.refacFabela.repository.TableroTotalesGeneralesRepository;
import com.refacFabela.service.TableroService;

@Service
public class TableroServiceImpl implements TableroService {

	@Autowired
	private TableroTotalesGeneralesRepository tableroTotalesGeneralesRepository;
	
	@Override
	public TvTotalesGeneralesTablero consultaTotalesTablero() {
		
		return tableroTotalesGeneralesRepository.findBynId(1L);
	}

}
