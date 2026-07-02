package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwFacturacionComplementoPago;

@Repository
public interface FacturacionComplementoPagoRepository extends JpaRepository<TwFacturacionComplementoPago, Long> {

	@Query("SELECT e FROM TwFacturacionComplementoPago e WHERE e.nIdVenta = :nIdVenta AND e.nEstatus = 1 ORDER BY e.nParcialidad ASC, e.nId ASC")
	List<TwFacturacionComplementoPago> findActivosByVenta(@Param("nIdVenta") Long nIdVenta);

	@Query("SELECT e FROM TwFacturacionComplementoPago e WHERE e.nIdVenta = :nIdVenta ORDER BY e.nParcialidad ASC, e.nId ASC")
	List<TwFacturacionComplementoPago> findByVenta(@Param("nIdVenta") Long nIdVenta);

	@Query("SELECT e FROM TwFacturacionComplementoPago e WHERE e.nIdFacturacion = :nIdFacturacion ORDER BY e.nParcialidad ASC, e.nId ASC")
	List<TwFacturacionComplementoPago> findByFacturacion(@Param("nIdFacturacion") Long nIdFacturacion);

	@Query("SELECT e FROM TwFacturacionComplementoPago e WHERE e.nIdVenta = :nIdVenta AND e.sOrigenPago = :sOrigenPago AND e.nIdPagoOrigen = :nIdPagoOrigen AND e.nEstatus = 1")
	List<TwFacturacionComplementoPago> findActivosByOrigenPago(@Param("nIdVenta") Long nIdVenta,
			@Param("sOrigenPago") String sOrigenPago, @Param("nIdPagoOrigen") Long nIdPagoOrigen);
}