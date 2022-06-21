package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.refacFabela.model.TvVentasFactura;

public interface VentasFacturaRepository extends JpaRepository<TvVentasFactura, Long> {

}
