package com.refacFabela.service;

import java.util.List;

import com.refacFabela.model.TcAnaquel;
import com.refacFabela.model.TcBodega;
import com.refacFabela.model.TcCatalogogeneral;
import com.refacFabela.model.TcCategoria;
import com.refacFabela.model.TcCategoriaGeneral;
import com.refacFabela.model.TcClavesat;
import com.refacFabela.model.TcFormapago;
import com.refacFabela.model.TcGanancia;
import com.refacFabela.model.TcNivel;
import com.refacFabela.model.TcTipoVenta;
import com.refacFabela.model.TcUsocfdi;

public interface CatalagosService {
	
	public TcCatalogogeneral actualizarTipoCambio(TcCatalogogeneral ccCatalogogeneral);
	public  TcCatalogogeneral consultaTipoCambioId(TcCatalogogeneral ccCatalogogeneral);
	public List<TcClavesat> catalogoClaveSat();
	public List<TcCategoriaGeneral> catalogoCategoriaGeneral();
	public List<TcCategoria> catalogoCategoriaId(int id);
	public List<TcGanancia> catalogoGanancia();
	public List<TcAnaquel> catalogoAnaquel();
	public List<TcNivel> catalogoNivel();
	public List<TcBodega> catalogoBodegas();
	public List<TcFormapago> catalogoFormaPago();
	public List<TcUsocfdi> catalagoUsoCfdi();
	public List<TcTipoVenta> catalagoTipoVenta();
}
