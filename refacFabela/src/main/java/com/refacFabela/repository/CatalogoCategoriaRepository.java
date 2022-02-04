package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcCategoria;

@Repository
public interface CatalogoCategoriaRepository extends JpaRepository<TcCategoria, Long> {
	
	public List<TcCategoria> findBynIdCategoriaGeneral(int id);

}
