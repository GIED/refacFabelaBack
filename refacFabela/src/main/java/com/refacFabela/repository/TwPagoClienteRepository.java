package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwPagoCliente;

@Repository
public interface TwPagoClienteRepository extends JpaRepository<TwPagoCliente, Long> {

	TwPagoCliente findBynId(Long nId);

	@Query("select p from TwPagoCliente p where p.nIdCliente = :nIdCliente and p.nEstatus = 1 order by p.dFechaPago desc, p.nId desc")
	List<TwPagoCliente> findActivosByCliente(Long nIdCliente);
}