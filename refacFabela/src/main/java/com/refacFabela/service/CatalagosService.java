package com.refacFabela.service;

import com.refacFabela.model.TcCatalogogeneral;

public interface CatalagosService {
	
	public TcCatalogogeneral actualizarTipoCambio(TcCatalogogeneral ccCatalogogeneral);
	public  TcCatalogogeneral consultaTipoCambioId(TcCatalogogeneral ccCatalogogeneral);
}
