package com.refacFabela.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refacFabela.model.TvVentasFactura;
import com.refacFabela.model.TwVenta;

public interface VentasFacturaRepository extends JpaRepository<TvVentasFactura, Long> {
	
	
	@Query(value="Select * from tv_ventasFactura e where (d_fechaVenta + INTERVAL 15 DAY) >= SYSDATE() and e.n_estatusVenta > 1  order by e.n_id desc  ", nativeQuery=true)
	public List<TvVentasFactura> obtenerFacturas();
	
	@Query(value="Select * from tv_ventasFactura e where  e.n_idFactura > 0  order by e.n_id desc  ", nativeQuery=true)
	public List<TvVentasFactura> obtenerVentasFacturadas();

	@Query(value = "Select * from tv_ventasFactura e "
			+ "where e.n_idFactura > 0 "
			+ "and (:fechaInicio is null or e.d_fechaVenta >= :fechaInicio) "
			+ "and (:fechaFin is null or e.d_fechaVenta <= :fechaFin) "
			+ "and (:buscar is null or :buscar = '' "
			+ "or cast(e.n_id as char) like concat('%', :buscar, '%') "
			+ "or cast(e.n_idFactura as char) like concat('%', :buscar, '%') "
			+ "or e.s_folioVenta like concat('%', :buscar, '%')) "
			+ "order by e.d_fechaVenta desc, e.n_id desc",
			nativeQuery = true)
	public List<TvVentasFactura> obtenerVentasFacturadasFiltradas(@Param("fechaInicio") LocalDateTime fechaInicio,
			@Param("fechaFin") LocalDateTime fechaFin, @Param("buscar") String buscar);

}
