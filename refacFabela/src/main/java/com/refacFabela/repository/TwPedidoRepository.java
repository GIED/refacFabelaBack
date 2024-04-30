package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcHistoriaPrecioProducto;
import com.refacFabela.model.TwPedido;

@Repository
public interface TwPedidoRepository extends JpaRepository<TwPedido, Long> {
	
	   @Query("Select e from TwPedido e where e.nIdVenta=:venta ")
		public TwPedido pedido(Long venta);

}
