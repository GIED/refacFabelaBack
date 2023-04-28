package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcCp;
import com.refacFabela.model.ThStockProducto;

@Repository
public interface ThStockProductoRepository extends JpaRepository<ThStockProducto, Long> {
	

}
