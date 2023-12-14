package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TvReporteCajaFormaPago;
import com.refacFabela.model.TwGasto;

@Repository
public interface TwGastoRepository extends JpaRepository<TwGasto, Long> {

	
	@Query("Select c from TwGasto c where c.nIdCaja = :nIdCaja ")
	public List<TwGasto> obtenerGastosCaja(Long nIdCaja);
	
}
