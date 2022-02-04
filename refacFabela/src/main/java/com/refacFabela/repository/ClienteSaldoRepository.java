package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TvSaldoGeneralCliente;

@Repository
public interface ClienteSaldoRepository extends JpaRepository<TvSaldoGeneralCliente, Long> {

	public TvSaldoGeneralCliente findBynIdCliente(Long id);
	
	@Query("Select c from TvSaldoGeneralCliente c where c.nLimiteCredito>0 and c.nSaldoTotal>0")
	public List<TvSaldoGeneralCliente> obtenerClienteSaldo();
	
	
}
