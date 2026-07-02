package com.refacFabela.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwFacturacionPacAudit;

@Repository
public interface FacturacionPacAuditRepository extends JpaRepository<TwFacturacionPacAudit, Long> {

	@Query(value = "SELECT * FROM tw_facturacion_pac_audit "
			+ "WHERE n_id_venta = :nIdVenta "
			+ "AND s_operacion = :sOperacion "
			+ "AND b_success = 1 "
			+ "ORDER BY n_id DESC "
			+ "LIMIT 1", nativeQuery = true)
	Optional<TwFacturacionPacAudit> findUltimaCancelacionExitosa(@Param("nIdVenta") Long nIdVenta,
			@Param("sOperacion") String sOperacion);

	@Query(value = "SELECT * FROM tw_facturacion_pac_audit WHERE s_correlation_id = :sCorrelationId ORDER BY n_id DESC LIMIT 1", nativeQuery = true)
	Optional<TwFacturacionPacAudit> findTopByCorrelationId(@Param("sCorrelationId") String sCorrelationId);

	@Query(value = "SELECT * FROM tw_facturacion_pac_audit WHERE n_id_venta = :nIdVenta AND s_operacion = :sOperacion ORDER BY n_id DESC", nativeQuery = true)
	List<TwFacturacionPacAudit> findByVentaAndOperacion(@Param("nIdVenta") Long nIdVenta,
			@Param("sOperacion") String sOperacion);

}