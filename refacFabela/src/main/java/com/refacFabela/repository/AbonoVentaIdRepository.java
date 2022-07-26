package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwCaja;

@Repository
public interface AbonoVentaIdRepository extends JpaRepository<TwAbono, Long> {
	
	public List<TwAbono> findBynIdVenta(Long id);
	
	@Query("Select e from TwAbono e where e.twCaja.nId= :nIdCaja ")
	public List<TwAbono> obtenerAbonosCaja(Long nIdCaja);
	

}
