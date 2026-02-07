package com.refacFabela.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwInventarioUbicacionDet;

@Repository
public interface TwInventarioUbicacionDetRepository extends JpaRepository<TwInventarioUbicacionDet, Long> {

    /**
     * Buscar detalle de inventario por inventario ID.
     */
    @Query("SELECT d FROM TwInventarioUbicacionDet d WHERE d.nIdInventario = :nIdInventario " +
           "ORDER BY d.tcProducto.sNoParte")
    List<TwInventarioUbicacionDet> findByInventarioId(@Param("nIdInventario") Long nIdInventario);

    /**
     * Buscar línea específica de producto en un inventario.
     */
    @Query("SELECT d FROM TwInventarioUbicacionDet d WHERE d.nIdInventario = :nIdInventario " +
           "AND d.nIdProducto = :nIdProducto")
    Optional<TwInventarioUbicacionDet> findByInventarioAndProducto(
        @Param("nIdInventario") Long nIdInventario,
        @Param("nIdProducto") Long nIdProducto
    );

    /**
     * Contar líneas pendientes (cantidad contada NULL) de un inventario.
     */
    @Query("SELECT COUNT(d) FROM TwInventarioUbicacionDet d WHERE d.nIdInventario = :nIdInventario " +
           "AND d.nCantidadContada IS NULL")
    Long countPendientesByInventario(@Param("nIdInventario") Long nIdInventario);

    /**
     * Buscar líneas pendientes (cantidad contada NULL) de un inventario.
     */
    @Query("SELECT d FROM TwInventarioUbicacionDet d WHERE d.nIdInventario = :nIdInventario " +
           "AND d.nCantidadContada IS NULL ORDER BY d.tcProducto.sNoParte")
    List<TwInventarioUbicacionDet> findPendientesByInventario(@Param("nIdInventario") Long nIdInventario);

    /**
     * Contar líneas por estatus de línea.
     */
    @Query("SELECT COUNT(d) FROM TwInventarioUbicacionDet d WHERE d.nIdInventario = :nIdInventario " +
           "AND d.nEstatusLinea = :nEstatus")
    Long countByInventarioAndEstatus(
        @Param("nIdInventario") Long nIdInventario,
        @Param("nEstatus") Integer nEstatus
    );

    /**
     * Eliminar detalle de un inventario (para casos de cancelación).
     */
    @Modifying
    @Query("DELETE FROM TwInventarioUbicacionDet d WHERE d.nIdInventario = :nIdInventario")
    void deleteByInventarioId(@Param("nIdInventario") Long nIdInventario);
}
