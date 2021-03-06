package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwFacturacion;

@Repository
public interface FacturaRepository extends JpaRepository<TwFacturacion, Long> {

}
