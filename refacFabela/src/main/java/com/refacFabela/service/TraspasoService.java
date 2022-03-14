package com.refacFabela.service;

import java.util.List;

import com.refacFabela.model.TwProductobodega;

public interface TraspasoService {
	
	public TwProductobodega guardar(TwProductobodega productoBodega);
	public List<TwProductobodega> guardarExterno(List<TwProductobodega> listProductoBodega);

}
