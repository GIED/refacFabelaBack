package com.refacFabela.service;

import com.refacFabela.dto.BalanceCajaDto;
import com.refacFabela.model.TwCaja;

public interface CajaService {
	
	public TwCaja obtenerCajaActiva();
	public BalanceCajaDto obtenerBalanceCaja(Long nIdCaja);

}
