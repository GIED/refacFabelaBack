package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.refacFabela.model.TvStockProductoHist;
import com.refacFabela.model.TwCaja;

public interface TvStockProductoHistRepository extends JpaRepository<TvStockProductoHist, Long> {
	
	@Query("Select e from TvStockProductoHist e where e.nIdProducto=:producto order by nIdVenta desc  ")
	public List<TvStockProductoHist> obtenerHistoriaStockProducto( Long producto);
	

}
