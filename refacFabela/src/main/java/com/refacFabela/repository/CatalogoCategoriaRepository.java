package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcCategoria;

@Repository
public interface CatalogoCategoriaRepository extends JpaRepository<TcCategoria, Long> {
	
	
	@Query("Select e from TcCategoria e where nIdCategoriaGeneral=:id order by sDescripcion asc ")
	public List<TcCategoria> findBynIdCategoriaGeneral(int id);

}
