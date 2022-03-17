package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.refacFabela.model.TcBodega;
import com.refacFabela.model.TwVenta;

@Repository
public interface CatalogoBodegasRepository extends JpaRepository<TcBodega, Long> {
	
	public TcBodega findBynId(Long nId);

}
