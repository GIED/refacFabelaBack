package com.refacFabela.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TcCatalogogeneral;
import com.refacFabela.repository.CatalogosRepository;
import com.refacFabela.service.CatalagosService;

@Service
public class CatalogosServiceImp implements CatalagosService {

	@Autowired
	private CatalogosRepository catalogosRepository;
	@Override
	public TcCatalogogeneral actualizarTipoCambio(TcCatalogogeneral ccCatalogogeneral) {
		// TODO Auto-generated method stub
		return catalogosRepository.save(ccCatalogogeneral);
	}

	@Override
	public TcCatalogogeneral consultaTipoCambioId(TcCatalogogeneral ccCatalogogeneral) {
		// TODO Auto-generated method stub
		return catalogosRepository.findBysClave(ccCatalogogeneral.getSClave());
	}

}
