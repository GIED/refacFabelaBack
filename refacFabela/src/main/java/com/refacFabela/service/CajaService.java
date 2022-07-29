package com.refacFabela.service;

import java.util.List;

import com.refacFabela.dto.BalanceCajaDto;
import com.refacFabela.model.TwCaja;

public interface CajaService {
	
	public TwCaja obtenerCajaActiva();
	public List<TwCaja> obteneroCajas();
	public BalanceCajaDto obtenerBalanceCaja(Long nIdCaja);
	public TwCaja nuevaCaja(Double saldoInicial, Long nIdUsuario);

}
