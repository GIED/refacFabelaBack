package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.refacFabela.model.TwCarritoCompraPedido;

public interface TwComprasProductoPedidoRepository extends JpaRepository<TwCarritoCompraPedido, Long> {
	
	@Query("select c from TwCarritoCompraPedido c where c.nIdUsuario=:nIdUsuario ")
	public List<TwCarritoCompraPedido> obtenerCarritoProductosPedido(Long nIdUsuario);
	

}
