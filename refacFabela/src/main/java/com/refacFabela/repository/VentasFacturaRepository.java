package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.refacFabela.model.TvVentasFactura;
import com.refacFabela.model.TwVenta;

public interface VentasFacturaRepository extends JpaRepository<TvVentasFactura, Long> {
	
	
	@Query(value="Select * from tv_ventasFactura e where (d_fechaVenta + INTERVAL 15 DAY) >= SYSDATE() and e.n_estatusVenta > 1  order by e.n_id desc  ", nativeQuery=true)
	public List<TvVentasFactura> obtenerFacturas();
	
	@Query(value="Select * from tv_ventasFactura e where  e.n_idFactura > 0  order by e.n_id desc  ", nativeQuery=true)
	public List<TvVentasFactura> obtenerVentasFacturadas();

}
