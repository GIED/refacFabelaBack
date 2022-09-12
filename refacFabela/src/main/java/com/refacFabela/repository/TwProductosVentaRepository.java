package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.dto.VentaProductoDto;
import com.refacFabela.model.TwVentasProducto;

@Repository
public interface TwProductosVentaRepository extends JpaRepository<TwVentasProducto, Long> {
	
	@Query("Select new com.refacFabela.dto.VentaProductoDto (e) from TwVentasProducto e where twVenta.nId=:id")	
	public List<VentaProductoDto> obtenerPrpductosVentaId(Long id);
	public List<TwVentasProducto> findBynIdVenta(Long id);
	@Query("Select e from TwVentasProducto e where nId=:id")	
	public TwVentasProducto obtenerPrpductosId(Long id);	
	@Query("Select e from TwVentasProducto e where nIdVenta=:idVenta and nIdProducto=:idProducto")	
	public TwVentasProducto obtenerProductoVenta(Long idVenta, Long idProducto);


}
