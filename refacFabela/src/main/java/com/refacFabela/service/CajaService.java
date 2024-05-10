package com.refacFabela.service;

import java.util.List;

import com.refacFabela.dto.BalanceCajaDto;
import com.refacFabela.model.TwCaja;
import com.refacFabela.model.TwGasto;

public interface CajaService {
	
	public TwCaja obtenerCajaActiva();
	public List<TwCaja> obteneroCajas();
	public BalanceCajaDto obtenerBalanceCaja(Long nIdCaja);
	public TwCaja nuevaCaja(Double saldoInicial, Long nIdUsuario);
	public TwCaja consultaCaja( Long nidCaja);
	public List<TwGasto> obteberGastosCaja( Long nIdCaja);
	public TwGasto guardarGasto( TwGasto twGasto);
	public TwGasto borrarGasto( TwGasto twGasto);

}
