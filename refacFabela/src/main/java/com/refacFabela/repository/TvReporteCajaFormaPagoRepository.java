package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcCp;
import com.refacFabela.model.TvReporteCajaFormaPago;

@Repository
public interface TvReporteCajaFormaPagoRepository extends JpaRepository<TvReporteCajaFormaPago, Long> {

	@Query("Select c from TvReporteCajaFormaPago c where c.nIdCaja = :nIdCaja order by c.sFormaPago asc ")
	public List<TvReporteCajaFormaPago> obtenerFormaPagoCaja(Long nIdCaja);
	
	
}
