package com.refacFabela.dto;

import java.util.List;

import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwCaja;
import com.refacFabela.model.TwVenta;

public class BalanceCajaDto {
	
	public Long caja;	
	public List<TwCaja> twCaja;
	public List<TwAbono> twAbonos;
	public List<TrVentaCobro> trVentaCobro;
	
	
	
	
	public BalanceCajaDto() {
		
		
	}
	
	
	public Long getCaja() {
		return caja;
	}
	public void setCaja(Long caja) {
		this.caja = caja;
	}
	public List<TwCaja> getTwCaja() {
		return twCaja;
	}
	public void setTwCaja(List<TwCaja> twCaja) {
		this.twCaja = twCaja;
	}
	public List<TwAbono> getTwAbonos() {
		return twAbonos;
	}
	public void setTwAbonos(List<TwAbono> twAbonos) {
		this.twAbonos = twAbonos;
	}
	public List<TrVentaCobro> getTrVentaCobro() {
		return trVentaCobro;
	}
	public void setTrVentaCobro(List<TrVentaCobro> trVentaCobro) {
		this.trVentaCobro = trVentaCobro;
	}
	
	

}
