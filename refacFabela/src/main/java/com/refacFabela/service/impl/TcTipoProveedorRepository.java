package com.refacFabela.service.impl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcTipoProveedor;

@Repository
public interface TcTipoProveedorRepository extends JpaRepository<TcTipoProveedor, Long> {

	@Query(value="select c from TcTipoProveedor c where nEstatus=1 order by nId asc")
	public List<TcTipoProveedor> getTipoProveedor();
}
