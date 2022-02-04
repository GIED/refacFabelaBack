package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwVentasProducto;

@Repository
public interface VentasProductoRepository extends JpaRepository<TwVentasProducto, Long> {
	
	public List<TwVentasProducto> findBynIdVenta(Long idVenta);

}
