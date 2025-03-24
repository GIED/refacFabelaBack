package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.refacFabela.model.VwFacturaProductoBalance;

public interface VwFacturaProductoBalanceRepository extends JpaRepository<VwFacturaProductoBalance, Long> {
	
	@Query("Select c from VwFacturaProductoBalance c where c.nEstatusIngresoAlmacen=:nEstatusAlmacen and c.tcProveedor.nIdTipoProveedor=1 order by c.nId desc ")
	public List<VwFacturaProductoBalance> obtenerFacturasEstatusAlmacen(Integer nEstatusAlmacen);

}
