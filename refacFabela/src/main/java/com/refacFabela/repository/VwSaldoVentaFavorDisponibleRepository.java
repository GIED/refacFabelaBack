package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwVentaProductoCancela;
import com.refacFabela.model.VwSaldoVentaFavorDisponible;

@Repository
public interface VwSaldoVentaFavorDisponibleRepository extends JpaRepository<VwSaldoVentaFavorDisponible, Long> {

	
	@Query("Select e from VwSaldoVentaFavorDisponible e where e.nIdVenta=:id ") 
	public VwSaldoVentaFavorDisponible buscarSaldoVenta(Long id);
	
}
