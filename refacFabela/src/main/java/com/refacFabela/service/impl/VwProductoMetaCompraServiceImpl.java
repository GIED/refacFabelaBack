package com.refacFabela.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.model.VwProductoMetaCompra;
import com.refacFabela.repository.VwProductoMetaCompraRepository;
import com.refacFabela.service.VwProductoMetaCompraService;

@Service
public class VwProductoMetaCompraServiceImpl implements VwProductoMetaCompraService {
	
	@Autowired
	private VwProductoMetaCompraRepository vwProductoMetaCompraRepository;

	@Override
	public Optional<VwProductoMetaCompra> buscarPorNoParte(String sNoParte) {
		
		return vwProductoMetaCompraRepository.findBysNoParte(sNoParte);
	}

}
