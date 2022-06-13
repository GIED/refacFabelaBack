package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwVenta;

@Repository
public interface VentasRepository extends JpaRepository<TwVenta, Long> {

	public TwVenta findBynId(Long nIdVenta);	
	@Query("Select e from TwVenta e where e.nIdCotizacion=:nIdCotizacion ")
	public TwVenta obtnerVentaIdCotizacion(Long nIdCotizacion);
	
}
