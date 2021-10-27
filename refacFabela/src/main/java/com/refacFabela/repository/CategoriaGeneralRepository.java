package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcCatalogogeneral;
import com.refacFabela.model.TcCategoriaGeneral;

@Repository
public interface CategoriaGeneralRepository extends JpaRepository<TcCategoriaGeneral, Long> {

	
}
