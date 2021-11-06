package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.refacFabela.model.TwProductosAlternativo;

public interface ProductosAlternativosRepository extends JpaRepository<TwProductosAlternativo, Long> {
	
	public List<TwProductosAlternativo> findBynIdProducto(Long nId);
	

}
