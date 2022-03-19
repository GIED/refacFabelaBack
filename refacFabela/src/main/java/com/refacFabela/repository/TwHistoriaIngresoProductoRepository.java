package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwHistoriaIngresoProducto;

@Repository
public interface TwHistoriaIngresoProductoRepository extends JpaRepository<TwHistoriaIngresoProducto, Long> {
	
	
	@Query("Select e from TwHistoriaIngresoProducto e where e.nIdProducto=:nId")	
	List<TwHistoriaIngresoProducto> obtenerIngresoProductos(Long nId);

}
