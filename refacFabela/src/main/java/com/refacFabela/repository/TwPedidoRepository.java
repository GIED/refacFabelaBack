package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwPedido;

@Repository
public interface TwPedidoRepository extends JpaRepository<TwPedido, Long> {

}
