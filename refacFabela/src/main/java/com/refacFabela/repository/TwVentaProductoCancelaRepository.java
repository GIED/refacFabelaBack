package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwVentaProductoCancela;

@Repository
public interface TwVentaProductoCancelaRepository extends JpaRepository<TwVentaProductoCancela, Long> {

	
	
}
