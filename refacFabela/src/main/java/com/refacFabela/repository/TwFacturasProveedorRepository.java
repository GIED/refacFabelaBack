package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.refacFabela.model.TwFacturasProveedor;

@Repository
public interface TwFacturasProveedorRepository extends JpaRepository<TwFacturasProveedor, Long> {
	

	@Query("Select c from TwFacturasProveedor c where c.nEstatusFacturaProveedor=:status and dFechaPagoFactura is null  ")
	public List<TwFacturasProveedor> findBynEstatusFacturaProveedor(Long status);
	
	@Query("Select c from TwFacturasProveedor c where c.nIdProveedor=:nIdProveedor and nIdMoneda=:nIdMoneda and dFechaPagoFactura is null ")
	public List<TwFacturasProveedor> findBynIdProveedor(Long nIdProveedor, Long nIdMoneda);
	
	@Query("Select c from TwFacturasProveedor c where c.nId=:nIdFactura ")
	public TwFacturasProveedor findBynId(Long nIdFactura);
	
	@Query("Select c from TwFacturasProveedor c where c.nEstatusIngresoAlmacen=0  ")
	public List<TwFacturasProveedor> findBynEstatusFacturaIngreso();
	
	
	



}
