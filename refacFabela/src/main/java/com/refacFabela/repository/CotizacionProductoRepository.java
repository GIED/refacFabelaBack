package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwCotizacionesProducto;
@Repository
public interface CotizacionProductoRepository extends JpaRepository<TwCotizacionesProducto, Long> {
	
	public List<TwCotizacionesProducto> findBynIdCotizacion(Long idCotizacion);
	
	@Query("Select c from TwCotizacionesProducto c where twCotizaciones.tcCliente.nId=:nIdCliente and tcProducto.nId=:nIdProducto order by nIdCotizacion desc ")
	public List<TwCotizacionesProducto> findByIdClienteProducto(Long nIdCliente, Long nIdProducto);

}
