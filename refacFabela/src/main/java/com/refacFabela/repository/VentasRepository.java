package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwCotizacionesDetalle;
import com.refacFabela.model.TwVenta;

@Repository
public interface VentasRepository extends JpaRepository<TwVenta, Long> {

	public TwVenta findBynId(Long nIdVenta);	
	@Query("Select e from TwVenta e where e.nIdCotizacion=:nIdCotizacion ")
	public TwVenta obtnerVentaIdCotizacion(Long nIdCotizacion);
	@Query("Select e from TwVenta e where e.nIdCaja=:nIdCaja ")
	public List<TwVenta> obtnerVentasIdCaja(Long nIdCaja);
	
	
}
