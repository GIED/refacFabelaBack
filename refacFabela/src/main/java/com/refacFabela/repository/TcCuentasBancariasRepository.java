package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcCuentaBancaria;

@Repository
public interface TcCuentasBancariasRepository extends JpaRepository<TcCuentaBancaria, Long> {
	
	@Query("select c from TcCuentaBancaria c where c.nIdRazonSocial=:nIdRazonSocial and c.nEstatus=1 ")
	public List<TcCuentaBancaria> consultaCuentas(Long nIdRazonSocial);

}
