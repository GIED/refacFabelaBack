package com.refacFabela.service;

import java.util.List;

import com.refacFabela.model.TcAnaquel;
import com.refacFabela.model.TcBodega;
import com.refacFabela.model.TcCatalogogeneral;
import com.refacFabela.model.TcCategoria;
import com.refacFabela.model.TcCategoriaGeneral;
import com.refacFabela.model.TcClavesat;
import com.refacFabela.model.TcEstatusVenta;
import com.refacFabela.model.TcFormapago;
import com.refacFabela.model.TcGanancia;
import com.refacFabela.model.TcNivel;
import com.refacFabela.model.TcTipoVenta;
import com.refacFabela.model.TcUsocfdi;
import com.refacFabela.model.TwCaja;

public interface CatalagosService {
	
	public TcCatalogogeneral actualizarTipoCambio(TcCatalogogeneral ccCatalogogeneral);
	public  TcCatalogogeneral consultaTipoCambioId(TcCatalogogeneral ccCatalogogeneral);
	public List<TcClavesat> catalogoClaveSat();
	public List<TcCategoriaGeneral> catalogoCategoriaGeneral();
	public List<TcCategoria> catalogoCategoriaId(int id);
	public List<TcGanancia> catalogoGanancia();
	public TcGanancia catalogoGananciaId(Long nId);
	public List<TcAnaquel> catalogoAnaquel();
	public List<TcNivel> catalogoNivel();
	public List<TcBodega> catalogoBodegas();
	public List<TcFormapago> catalogoFormaPago();
	public TcFormapago catalogoFormaPagoId(Long nId);
	public List<TcUsocfdi> catalagoUsoCfdi();
	public TwCaja cajaActiva();
	public List<TcTipoVenta> catalagoTipoVenta();
	public TcEstatusVenta catalagoEstatusVentaId(Long nId);
}
