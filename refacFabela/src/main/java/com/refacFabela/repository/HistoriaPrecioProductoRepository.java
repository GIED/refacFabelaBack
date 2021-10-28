package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcHistoriaPrecioProducto;
@Repository
public interface HistoriaPrecioProductoRepository extends JpaRepository<TcHistoriaPrecioProducto, Long> {

}
