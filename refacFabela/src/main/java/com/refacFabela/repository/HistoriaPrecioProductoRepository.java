package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcHistoriaPrecioProducto;
@Repository
public interface HistoriaPrecioProductoRepository extends JpaRepository<TcHistoriaPrecioProducto, Long> {
	
	
    @Query("Select e from TcHistoriaPrecioProducto e where e. nIdProducto=:n_id order by e.dFecha asc ")
	public List<TcHistoriaPrecioProducto> productoIdHistoria(Long n_id);

}
