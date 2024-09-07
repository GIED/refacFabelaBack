package com.refacFabela.service.impl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.refacFabela.controller.BalanceAbonoProveedor;

public interface BalanceFacturaProveedorRepository extends JpaRepository<BalanceAbonoProveedor, Long> {
	
	@Query("Select c from BalanceAbonoProveedor c where c.twFacturasProveedor.nIdProveedor=:nIdProveedor and c.twFacturasProveedor.nIdMoneda=:nIdMoneda and c.twFacturasProveedor.dFechaPagoFactura is null ")
	public List<BalanceAbonoProveedor> findByBalance(Long nIdProveedor, Long nIdMoneda);

}
