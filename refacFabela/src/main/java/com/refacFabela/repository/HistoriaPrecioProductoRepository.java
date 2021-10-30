package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcHistoriaPrecioProducto;
@Repository
public interface HistoriaPrecioProductoRepository extends JpaRepository<TcHistoriaPrecioProducto, Long> {
	
	

	public List<TcHistoriaPrecioProducto> findBynIdProducto(Long n_id);

}
