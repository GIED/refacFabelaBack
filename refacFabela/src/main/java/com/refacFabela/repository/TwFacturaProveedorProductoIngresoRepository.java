package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwFacturaProveedorProductoIngreso;

@Repository
public interface TwFacturaProveedorProductoIngresoRepository extends JpaRepository<TwFacturaProveedorProductoIngreso, Long> {
	
	@Query(value="select c from TwFacturaProveedorProductoIngreso c where c.nIdFacturaProveedorProducto=:nId order by  c.dFechaIngreso desc")
	public List<TwFacturaProveedorProductoIngreso> obtenerFacturaProductoIngreso(Long nId); 

}
