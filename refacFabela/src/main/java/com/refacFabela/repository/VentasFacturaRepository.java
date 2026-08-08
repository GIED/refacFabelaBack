package com.refacFabela.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refacFabela.model.TvVentasFactura;
import com.refacFabela.model.TwVenta;

public interface VentasFacturaRepository extends JpaRepository<TvVentasFactura, Long> {
	
	
	@Query(value = "Select * from tv_ventasFactura e "
			+ "where e.n_estatusVenta > 1 "
			+ "and (( "
			+ "	YEAR(e.d_fechaVenta) = YEAR(CURDATE()) "
			+ "	and MONTH(e.d_fechaVenta) = MONTH(CURDATE()) "
			+ "	and ( "
			+ "		e.n_idFactura > 0 "
			+ "\t\tor exists (select 1 from tr_venta_cobro cob where cob.n_id_venta = e.n_id) "
			+ "		or exists (select 1 from tw_abonos abo where abo.n_idVenta = e.n_id) "
			+ "	) "
			+ ") "
			+ "or ( "
			+ "	e.n_idFactura > 0 "
			+ "	and e.d_fechaVenta >= DATE_SUB(CURDATE(), INTERVAL 60 DAY) "
			+ "\tand not exists (select 1 from tr_venta_cobro cob2 where cob2.n_id_venta = e.n_id) "
			+ "	and not exists (select 1 from tw_abonos abo2 where abo2.n_idVenta = e.n_id) "
			+ ") "
			+ ") "
			+ "order by e.n_id desc",
			nativeQuery = true)
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
