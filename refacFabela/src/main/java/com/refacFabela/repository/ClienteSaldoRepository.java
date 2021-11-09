package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TvSaldoGeneralCliente;

@Repository
public interface ClienteSaldoRepository extends JpaRepository<TvSaldoGeneralCliente, Long> {

	public TvSaldoGeneralCliente findBynIdCliente(Long id);
}
