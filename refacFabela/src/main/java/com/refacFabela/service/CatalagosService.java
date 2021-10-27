package com.refacFabela.service;

import java.util.List;

import com.refacFabela.model.TcCatalogogeneral;
import com.refacFabela.model.TcCategoria;
import com.refacFabela.model.TcCategoriaGeneral;
import com.refacFabela.model.TcClavesat;
import com.refacFabela.model.TcGanancia;

public interface CatalagosService {
	
	public TcCatalogogeneral actualizarTipoCambio(TcCatalogogeneral ccCatalogogeneral);
	public  TcCatalogogeneral consultaTipoCambioId(TcCatalogogeneral ccCatalogogeneral);
	public List<TcClavesat> catalogoClaveSat();
	public List<TcCategoriaGeneral> catalogoCategoriaGeneral();
	public List<TcCategoria> catalogoCategoriaId(int id);
	public List<TcGanancia> catalogoGanancia();
}
