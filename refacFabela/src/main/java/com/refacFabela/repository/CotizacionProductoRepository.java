package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwCotizacionesProducto;
@Repository
public interface CotizacionProductoRepository extends JpaRepository<TwCotizacionesProducto, Long> {

}
