package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwFacturacionPacAuditDetalle;

@Repository
public interface FacturacionPacAuditDetalleRepository extends JpaRepository<TwFacturacionPacAuditDetalle, Long> {

	@Query(value = "SELECT * FROM tw_facturacion_pac_audit_detalle "
			+ "WHERE n_id_auditoria = :nIdAuditoria "
			+ "AND s_bloque = :sBloque", nativeQuery = true)
	List<TwFacturacionPacAuditDetalle> findDetalleAuditoriaByBloque(
			@Param("nIdAuditoria") Long nIdAuditoria,
			@Param("sBloque") String sBloque);

	@Query(value = "SELECT * FROM tw_facturacion_pac_audit_detalle "
			+ "WHERE n_id_auditoria = :nIdAuditoria "
			+ "AND s_bloque = :sBloque "
			+ "AND s_clave IN (:claves)", nativeQuery = true)
	List<TwFacturacionPacAuditDetalle> findDetalleAuditoriaByBloqueAndClaves(
			@Param("nIdAuditoria") Long nIdAuditoria,
			@Param("sBloque") String sBloque,
			@Param("claves") List<String> claves);
}