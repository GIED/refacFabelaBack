package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwVentasProducto;

@Repository
public interface VentasProductoRepository extends JpaRepository<TwVentasProducto, Long> {
	
	
	@Query("Select c from TwVentasProducto c where c.nIdVenta= :idVenta and c.nEstatus>0 ")
	public List<TwVentasProducto> findBynIdVenta(Long idVenta);
	
	@Query("Select c from TwVentasProducto c where c.nIdVenta= :idVenta and c.nEstatus=0 ")
	public List<TwVentasProducto> buscarProductosCancelados(Long idVenta);

}
