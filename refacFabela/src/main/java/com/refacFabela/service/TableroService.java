package com.refacFabela.service;

import java.util.List;


import com.refacFabela.model.TvTotalesGeneralesTablero;
import com.refacFabela.model.VwVentaMesAno;
import com.refacFabela.model.VwVentaProductoAno;

public interface TableroService {
	
	public TvTotalesGeneralesTablero consultaTotalesTablero();
	public List<VwVentaMesAno> consultaVentaMesAno(String ano);
	public List<VwVentaProductoAno> consultaVentaProductoAno(String ano);
}
