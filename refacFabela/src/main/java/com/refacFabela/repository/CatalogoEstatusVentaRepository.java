package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcEstatusVenta;
import com.refacFabela.model.TcNivel;

@Repository
public interface CatalogoEstatusVentaRepository extends JpaRepository<TcEstatusVenta, Long> {
	
	public TcEstatusVenta findBynId(Long nId);
	

}
