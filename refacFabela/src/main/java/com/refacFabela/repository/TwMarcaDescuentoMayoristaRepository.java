package com.refacFabela.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwMarcaDescuentoMayorista;

/**
 * Repository para acceder a datos de tw_marca_descuento_mayorista
 * 
 * Maneja consultas de descuentos específicos de marca para mayoristas.
 */
@Repository
public interface TwMarcaDescuentoMayoristaRepository 
        extends JpaRepository<TwMarcaDescuentoMayorista, Long> {

    /**
     * Busca registro de descuento de marca para mayorista (sin filtrar por estado)
     * 
     * @param nIdMarca ID de la marca
     * @return Optional con el registro si existe
     */
    @Query("SELECT m FROM TwMarcaDescuentoMayorista m WHERE m.nIdMarca = :nIdMarca")
    Optional<TwMarcaDescuentoMayorista> findByNIdMarca(Long nIdMarca);
}
