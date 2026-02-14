package com.refacFabela.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwInventarioUbicacion;

@Repository
public interface TwInventarioUbicacionRepository extends JpaRepository<TwInventarioUbicacion, Long> {

    /**
     * Buscar inventario abierto o pausado (estatus 1 o 2) para una ubicación específica.
     */
    @Query("SELECT i FROM TwInventarioUbicacion i WHERE i.nIdBodega = :nIdBodega " +
           "AND i.nIdAnaquel = :nIdAnaquel AND i.nIdNivel = :nIdNivel " +
           "AND i.nEstatus IN (1, 2) ORDER BY i.dInicio DESC")
    Optional<TwInventarioUbicacion> findInventarioAbiertoByUbicacion(
        @Param("nIdBodega") Long nIdBodega,
        @Param("nIdAnaquel") Long nIdAnaquel,
        @Param("nIdNivel") Long nIdNivel
    );

    /**
     * Buscar inventario abierto o pausado (estatus 1 o 2) del usuario responsable.
     */
    @Query("SELECT i FROM TwInventarioUbicacion i WHERE i.nIdUsuarioResponsable = :nIdUsuario " +
           "AND i.nEstatus IN (1, 2) ORDER BY i.dInicio DESC")
    Optional<TwInventarioUbicacion> findInventarioAbiertoByUsuario(@Param("nIdUsuario") Long nIdUsuario);

    /**
     * Listar inventarios por estatus.
     */
    @Query("SELECT i FROM TwInventarioUbicacion i WHERE i.nEstatus = :nEstatus " +
           "ORDER BY i.dInicio DESC")
    List<TwInventarioUbicacion> findByEstatus(@Param("nEstatus") Integer nEstatus);

    /**
     * Listar inventarios pendientes de autorización (estatus EN_REVISION = 3).
     */
    @Query("SELECT i FROM TwInventarioUbicacion i WHERE i.nEstatus = 3 " +
           "ORDER BY i.dCierre DESC")
    List<TwInventarioUbicacion> findInventariosPendientesAutorizacion();

    /**
     * Listar todos los inventarios ordenados por fecha de inicio descendente.
     */
    @Query("SELECT i FROM TwInventarioUbicacion i ORDER BY i.dInicio DESC")
    List<TwInventarioUbicacion> findAllOrderByInicio();

    /**
     * Buscar inventarios por bodega.
     */
    @Query("SELECT i FROM TwInventarioUbicacion i WHERE i.nIdBodega = :nIdBodega " +
           "ORDER BY i.dInicio DESC")
    List<TwInventarioUbicacion> findByBodega(@Param("nIdBodega") Long nIdBodega);

    /**
     * Buscar inventarios por ubicación completa (bodega + anaquel + nivel).
     */
    @Query("SELECT i FROM TwInventarioUbicacion i WHERE i.nIdBodega = :nIdBodega " +
           "AND i.nIdAnaquel = :nIdAnaquel AND i.nIdNivel = :nIdNivel " +
           "ORDER BY i.dInicio DESC")
    List<TwInventarioUbicacion> findByUbicacion(
        @Param("nIdBodega") Long nIdBodega,
        @Param("nIdAnaquel") Long nIdAnaquel,
        @Param("nIdNivel") Long nIdNivel
    );

    /**
     * Buscar inventarios por bodega y anaquel.
     */
    @Query("SELECT i FROM TwInventarioUbicacion i WHERE i.nIdBodega = :nIdBodega " +
           "AND i.nIdAnaquel = :nIdAnaquel ORDER BY i.dInicio DESC")
    List<TwInventarioUbicacion> findByBodegaAndAnaquel(
        @Param("nIdBodega") Long nIdBodega,
        @Param("nIdAnaquel") Long nIdAnaquel
    );
}
