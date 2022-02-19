package com.refacFabela.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TwPagoComprobanteInternet;
import com.refacFabela.repository.PagoComprobanteRepository;
import com.refacFabela.service.VentaInternetService;

@Service
public class VentaInternetServiceImpl implements VentaInternetService {
	
	@Autowired
	private PagoComprobanteRepository pagoCOmprobanteRepository;

	@Override
	public TwPagoComprobanteInternet guardarComprobante(TwPagoComprobanteInternet comprobanteInternet) {
		return this.pagoCOmprobanteRepository.save(comprobanteInternet);
	}

}
