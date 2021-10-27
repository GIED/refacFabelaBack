package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.refacFabela.model.TcCategoria;


public interface CatalogoCategoriaRepository extends JpaRepository<TcCategoria, Long> {
	
	public List<TcCategoria> findBynIdCategoriaGeneral(int id);

}
