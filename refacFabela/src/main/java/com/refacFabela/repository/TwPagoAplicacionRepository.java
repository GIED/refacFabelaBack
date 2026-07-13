package com.refacFabela.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwPagoAplicacion;

@Repository
public interface TwPagoAplicacionRepository extends JpaRepository<TwPagoAplicacion, Long> {

	@Query("select p from TwPagoAplicacion p where p.nIdPagoCliente = :nIdPagoCliente and p.nEstatus = 1 order by p.nOrdenAplicacion asc, p.nId asc")
	List<TwPagoAplicacion> findActivasByPago(Long nIdPagoCliente);

	@Query("select p from TwPagoAplicacion p where p.nIdVenta = :nIdVenta and p.nEstatus = 1 order by p.nOrdenAplicacion asc, p.nId asc")
	List<TwPagoAplicacion> findActivasByVenta(Long nIdVenta);

	@Query("select coalesce(sum(p.nMontoAplicado), 0) from TwPagoAplicacion p where p.nIdVenta = :nIdVenta and p.nEstatus = 1 and p.sEstatus <> 'CANCELADA'")
	BigDecimal totalAplicadoActivoVenta(Long nIdVenta);

	@Query("select coalesce(max(p.nParcialidad), 0) from TwPagoAplicacion p where p.nIdVenta = :nIdVenta and p.nEstatus = 1 and p.sEstatus <> 'CANCELADA'")
	Integer maxParcialidadActivaVenta(Long nIdVenta);
}