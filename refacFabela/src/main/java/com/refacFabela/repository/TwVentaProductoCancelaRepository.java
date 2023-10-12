package com.refacFabela.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.refacFabela.model.TwCotizacionesDetalle;
import com.refacFabela.model.TwVentaProductoCancela;

public interface TwVentaProductoCancelaRepository extends JpaRepository<TwVentaProductoCancela, Long> {

	
	@Query("Select c from TwVentaProductoCancela c where  c.twVenta.dFechaVenta>=:fechaInicio and c.twVenta.dFechaVenta<=:fechaTermino ")
	public List<TwVentaProductoCancela> findByBuscar(Date fechaInicio, Date fechaTermino);
	
	
}
