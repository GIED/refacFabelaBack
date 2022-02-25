package com.refacFabela.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TwPagoComprobanteInternet;
import com.refacFabela.repository.PagoComprobanteRepository;
import com.refacFabela.service.VentaInternetService;

@Service
public class VentaInternetServiceImpl implements VentaInternetService {
	
	@Autowired
	private PagoComprobanteRepository pagoComprobanteRepository;

	@Override
	public TwPagoComprobanteInternet guardarComprobante(TwPagoComprobanteInternet comprobanteInternet) {
		return this.pagoComprobanteRepository.save(comprobanteInternet);
	}

	@Override
	public TwPagoComprobanteInternet consultarSiComprobanteExiste(Long idCotizacion, Long idCliente) {
		
		return this.pagoComprobanteRepository.consultaSiComprobanteExiste(idCotizacion, idCliente);
	}

	@Override
	public List<TwPagoComprobanteInternet> consultaRegistros(Integer estatus) {
		
		return this.pagoComprobanteRepository.findBynStatus(estatus);
	}

}
