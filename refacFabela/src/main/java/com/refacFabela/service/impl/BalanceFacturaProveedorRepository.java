package com.refacFabela.service.impl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.refacFabela.controller.BalanceAbonoProveedor;

public interface BalanceFacturaProveedorRepository extends JpaRepository<BalanceAbonoProveedor, Long> {
	
	@Query("Select c from BalanceAbonoProveedor c where c.twFacturasProveedor.nIdProveedor=:nIdProveedor and c.twFacturasProveedor.nIdMoneda=:nIdMoneda and c.twFacturasProveedor.dFechaPagoFactura is null and c.twFacturasProveedor.nEstatusFacturaProveedor=1 ")
	public List<BalanceAbonoProveedor> findByBalance(Long nIdProveedor, Long nIdMoneda);
	
	@Query("Select c from BalanceAbonoProveedor c where c.twFacturasProveedor.nIdProveedor=:nIdProveedor and c.twFacturasProveedor.nIdMoneda=:nIdMoneda ")
	public List<BalanceAbonoProveedor> findByBalanceHistoria(Long nIdProveedor, Long nIdMoneda);
	
	@Query("Select c from BalanceAbonoProveedor c where c.twFacturasProveedor.nId=:nIdFactura ")
	public BalanceAbonoProveedor findByBalanceFactura(Long nIdFactura);
	
	@Query("Select c from BalanceAbonoProveedor c where c.twFacturasProveedor.nEstatusFacturaProveedor=1 and c.twFacturasProveedor.dFechaPagoFactura is null ")
	public List<BalanceAbonoProveedor> findByFactutasSinPagar();

}
