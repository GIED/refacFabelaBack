package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwCotizaciones;
import com.refacFabela.model.TwCotizacionesDetalle;
@Repository
public interface TwCotizacionesRepository extends JpaRepository<TwCotizacionesDetalle, Long> {
	
	public List<TwCotizacionesDetalle> findBynIdUsuario(Long nIdUsuario);
	
	@Query("Select c from TwCotizacionesDetalle c where   c.tcCliente.sRazonSocial like %:buscar% or c.tcCliente.sRfc like %:buscar% or c.nId like %:buscar%")
	public List<TwCotizacionesDetalle> findByBuscar(String buscar);

	@Query(value="Select * from tv_cotizacionDetalle where (d_fecha + INTERVAL 25 DAY) >= SYSDATE()  order by n_id desc ",   nativeQuery = true) 
	public List<TwCotizacionesDetalle> findByBuscar2();
	
	@Query(value="Select * from tv_cotizacionDetalle where (d_fecha + INTERVAL 25 DAY) >= SYSDATE() and n_idCliente=:Cliente order by n_id desc ",   nativeQuery = true) 
	public List<TwCotizacionesDetalle> findByCotizacionesCliente(Long Cliente);
	


}
