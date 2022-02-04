package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.refacFabela.model.TcTipoVenta;

@Repository
public interface CatalagoTipoVentaRepository extends JpaRepository<TcTipoVenta, Long> {

}
