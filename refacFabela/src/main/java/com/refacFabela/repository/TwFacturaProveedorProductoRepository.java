package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwFacturaProveedorProducto;

@Repository
public interface TwFacturaProveedorProductoRepository extends JpaRepository<TwFacturaProveedorProducto, Long> {
	
	@Query(value = "select c from TwFacturaProveedorProducto c where c.nIdFacturaProveedor=:nIdFactura ")
	public List<TwFacturaProveedorProducto> getProductosFactura(Long nIdFactura);

}
