package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcGasto;

@Repository
public interface TcGastoRepository extends JpaRepository<TcGasto, Long> {
	

}
