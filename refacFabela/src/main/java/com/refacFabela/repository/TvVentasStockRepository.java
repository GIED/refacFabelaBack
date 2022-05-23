package com.refacFabela.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TvVentaStock;

@Repository
public interface TvVentasStockRepository extends JpaRepository<TvVentaStock, Long> {

	@Query("Select c from TvVentaStock c where c.dFechaVenta>=STR_TO_DATE(:dFechaInicio, '%Y-%m-%d') and c.dFechaVenta<=STR_TO_DATE(:dFechaFinal, '%Y-%m-%d') order by c.nCantidadTotal   ")
	public List<TvVentaStock> obtenerVentasStock(Date dFechaInicio, Date dFechaFinal);
	 
	
}
