package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwPagoComprobanteInternet;

@Repository
public interface PagoComprobanteRepository extends JpaRepository<TwPagoComprobanteInternet, Long> {

}
