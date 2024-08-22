package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcEstatusFacturaProveedor;
import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TwFacturasProveedor;

@Repository
public interface TwFacturasProveedorRepository extends JpaRepository<TwFacturasProveedor, Long> {
	

	@Query("Select c from TwFacturasProveedor c where c.nEstatusFacturaProveedor=:status  ")
	public List<TwFacturasProveedor> findBynEstatusFacturaProveedor(Long status);
	
	@Query("Select c from TwFacturasProveedor c where c.nIdProveedor=:id  ")
	public List<TwFacturasProveedor> findBynIdProveedor(Long id);


}