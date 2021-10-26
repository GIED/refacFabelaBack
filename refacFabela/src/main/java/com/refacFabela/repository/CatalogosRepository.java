package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcCatalogogeneral;

@Repository
public interface CatalogosRepository extends JpaRepository<TcCatalogogeneral, String> {
	
	public TcCatalogogeneral findBysClave(String s_clave );

}
