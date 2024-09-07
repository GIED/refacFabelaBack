package com.refacFabela.service.impl;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwAbonoFacturaProveedor;

@Repository
public interface TwAbonoFacturaProveedorRepository extends JpaRepository<TwAbonoFacturaProveedor, Long> {

	@Query("Select c from TwAbonoFacturaProveedor c where c.nIdFacturaProveedor=:nIdFactura  ")
	public TwAbonoFacturaProveedor buscarAbono(Long nIdFactura);
}
