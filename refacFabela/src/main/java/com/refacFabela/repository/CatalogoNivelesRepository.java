package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcBodega;
import com.refacFabela.model.TcNivel;

@Repository
public interface CatalogoNivelesRepository extends JpaRepository<TcNivel, Long> {
	
	
	
	public TcNivel findBynId(Long nId);

}
