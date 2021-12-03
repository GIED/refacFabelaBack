package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TvVentaDetalle;

@Repository
public interface TvVentaDetalleRepository extends JpaRepository<TvVentaDetalle, Long> {
	
	@Query("Select c from TvVentaDetalle c where c.nIdCliente=:nIdCliente and c.nTipoPago=:nTipoPago")
	public List<TvVentaDetalle> consultaVentaDetalleId(Long nIdCliente, long nTipoPago);
	
	
	
}
