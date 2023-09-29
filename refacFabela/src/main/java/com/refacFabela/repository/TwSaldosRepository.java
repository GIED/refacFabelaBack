package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TvVentaProductoMes;
import com.refacFabela.model.TwVentaProductoCancela;

@Repository
public interface TwSaldosRepository extends JpaRepository<TwVentaProductoCancela, Long> {

	@Query(value="Select sum(n_precioPartida) from tw_venta_producto_cancela where n_idCaja=:id", nativeQuery = true) 
	public Double totalCancela(Long id);
	
	@Query("Select e from TwVentaProductoCancela e where e.nIdProductos=:id order by e.dFecha desc ") 
	public List<TwVentaProductoCancela> productosCancelados(Long id);
	
}
