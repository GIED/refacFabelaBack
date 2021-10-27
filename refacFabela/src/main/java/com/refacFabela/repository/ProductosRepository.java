package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcProducto;

@Repository
public interface ProductosRepository extends JpaRepository<TcProducto, Long> {

}
