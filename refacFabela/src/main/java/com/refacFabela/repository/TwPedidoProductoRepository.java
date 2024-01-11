package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwPedidoProducto;

@Repository
public interface TwPedidoProductoRepository extends JpaRepository<TwPedidoProducto, Long> {

}
