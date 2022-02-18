package com.refacFabela.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TwCaja;
import com.refacFabela.repository.CajaRepository;
import com.refacFabela.service.CajaService;
@Service
public class CajaServiceImpl implements CajaService {
	
	@Autowired
	public CajaRepository cajaRepository;
	

	@Override
	public TwCaja obtenerCajaActiva() {
		// TODO Auto-generated method stub
		return cajaRepository.obtenerCajaVigente();
	}

	

	

}
