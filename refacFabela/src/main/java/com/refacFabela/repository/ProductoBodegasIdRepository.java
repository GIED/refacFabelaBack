package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TvStockProducto;
@Repository
public interface ProductoBodegasIdRepository extends JpaRepository<TvStockProducto, Long> {
	
	public TvStockProducto findBynIdProducto(Long id);

}
