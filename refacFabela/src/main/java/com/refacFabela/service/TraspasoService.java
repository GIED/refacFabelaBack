package com.refacFabela.service;

import java.util.List;

import com.refacFabela.model.TwAjustesInventario;
import com.refacFabela.model.TwProductobodega;

public interface TraspasoService {
	
	public TwProductobodega guardar(TwProductobodega productoBodega);
	public TwProductobodega guardar2(TwProductobodega productoBodega);
	public TwAjustesInventario guardarAjusteInventario(TwAjustesInventario twAjustesInventario);
	public List<TwProductobodega> guardarExterno(List<TwProductobodega> listProductoBodega);

}
