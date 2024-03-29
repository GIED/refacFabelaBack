package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TvVentaProductoMes;

@Repository
public interface VentaProductoMesRepository extends JpaRepository<TvVentaProductoMes, Long> {

	
	@Query(value="Select * from tv_venta_producto_mes where n_idProductos=:id order by substr(s_fechaVentaNumero,4,8), substr(s_fechaVentaNumero,1,2) asc", nativeQuery = true) 
	public List<TvVentaProductoMes> obtenerVentaProductoMesId(Long id);
	
}
