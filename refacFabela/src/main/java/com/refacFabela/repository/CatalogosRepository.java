package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcCatalogogeneral;
import com.refacFabela.model.TcEstatusVenta;

@Repository
public interface CatalogosRepository extends JpaRepository<TcCatalogogeneral, Long> {
	
	public TcCatalogogeneral findBysClave(String s_clave );


}


