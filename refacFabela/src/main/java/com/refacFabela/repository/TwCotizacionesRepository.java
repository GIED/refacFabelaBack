package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwCotizaciones;
import com.refacFabela.model.TwCotizacionesDetalle;
@Repository
public interface TwCotizacionesRepository extends JpaRepository<TwCotizacionesDetalle, Long> {
	
	public List<TwCotizacionesDetalle> findBynIdUsuario(Long nIdUsuario);

}
