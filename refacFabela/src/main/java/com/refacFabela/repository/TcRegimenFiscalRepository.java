package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcRegimenFiscal;

@Repository
public interface TcRegimenFiscalRepository extends JpaRepository<TcRegimenFiscal, Long> {

	
}
