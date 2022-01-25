package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwCotizacionesProducto;
@Repository
public interface CotizacionProductoRepository extends JpaRepository<TwCotizacionesProducto, Long> {
	
	public List<TwCotizacionesProducto> findBynIdCotizacion(Long idCotizacion);

}
