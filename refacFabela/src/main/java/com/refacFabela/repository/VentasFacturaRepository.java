package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.refacFabela.model.TvVentasFactura;
import com.refacFabela.model.TwVenta;

public interface VentasFacturaRepository extends JpaRepository<TvVentasFactura, Long> {
	
	
	@Query(value="Select * from tv_ventasFactura e where (d_fechaVenta + INTERVAL 7 DAY) >= SYSDATE()   order by e.n_id desc  ", nativeQuery=true)
	public List<TvVentasFactura> obtenerFacturas();

}
