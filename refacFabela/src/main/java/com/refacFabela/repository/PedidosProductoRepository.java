package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwPedidoProducto;

@Repository
public interface PedidosProductoRepository extends JpaRepository<TwPedidoProducto, Long> {
	
	@Query("Select c from TwPedidoProducto c where c.nIdPedido= :nIdPedido")
	List<TwPedidoProducto> obtenerPedidosRegistrados(Long nIdPedido);
	
	@Query("Select c from TwPedidoProducto c where c.nIdUsuario= :nIdUsuario and nIdPedido is null and nEstatus=1")
	List<TwPedidoProducto> obtenerProductosCarritoPedidoUsuario(Long nIdUsuario);
	

}
