package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcCp;
import com.refacFabela.model.TvReporteDetalleVenta;

@Repository
public interface TvReporteDetalleVentaRepository extends JpaRepository<TvReporteDetalleVenta, Long> {
	
	@Query("Select c from TvReporteDetalleVenta c where c.nIdCaja = :nIdCaja")
	public List<TvReporteDetalleVenta> obtenerVentasCajaReporte(Long nIdCaja);

}
