package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwInventarioUbicacionDetHist;

@Repository
public interface TwInventarioUbicacionDetHistRepository extends JpaRepository<TwInventarioUbicacionDetHist, Long> {

    /**
     * Buscar historial de un inventario específico.
     */
    @Query("SELECT h FROM TwInventarioUbicacionDetHist h WHERE h.nIdInventario = :nIdInventario " +
           "ORDER BY h.dEvento DESC")
    List<TwInventarioUbicacionDetHist> findByInventarioId(@Param("nIdInventario") Long nIdInventario);

    /**
     * Buscar historial de un producto específico en un inventario.
     */
    @Query("SELECT h FROM TwInventarioUbicacionDetHist h WHERE h.nIdInventario = :nIdInventario " +
           "AND h.nIdProducto = :nIdProducto ORDER BY h.dEvento DESC")
    List<TwInventarioUbicacionDetHist> findByInventarioAndProducto(
        @Param("nIdInventario") Long nIdInventario,
        @Param("nIdProducto") Long nIdProducto
    );
}
