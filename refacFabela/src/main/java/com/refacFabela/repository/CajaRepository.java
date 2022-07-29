package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwCaja;

@Repository
public interface CajaRepository extends JpaRepository<TwCaja, Long> {

	
	@Query("Select e from TwCaja e where e.nEstatus=1 ")
	public TwCaja obtenerCajaVigente();
	
	@Query("Select e from TwCaja e  order by e.nEstatus desc ")
	public List<TwCaja> obtenerCajas();
	
}
