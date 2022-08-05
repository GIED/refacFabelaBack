package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.refacFabela.model.TvVentasFactura;
import com.refacFabela.model.TwVenta;

public interface VentasFacturaRepository extends JpaRepository<TvVentasFactura, Long> {
	
	
	@Query("Select e from TvVentasFactura e order by e.nId desc ")
	public List<TvVentasFactura> obtenerFacturas();

}
