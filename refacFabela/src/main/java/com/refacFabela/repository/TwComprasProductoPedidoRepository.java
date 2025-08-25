package com.refacFabela.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.refacFabela.model.TwCarritoCompraPedido;
import com.refacFabela.model.TwPedidoProducto;

public interface TwComprasProductoPedidoRepository extends JpaRepository<TwCarritoCompraPedido, Long> {
	
	@Query("select c from TwCarritoCompraPedido c where c.nIdUsuario=:nIdUsuario and nEstatus=1  ")
	public List<TwCarritoCompraPedido> obtenerCarritoProductosPedido(Long nIdUsuario);
	
	
	@Query("Select c from TwCarritoCompraPedido c where c.nIdUsuario= :nIdUsuario and nEstatus=1")
	List<TwCarritoCompraPedido> obtenerProductosComprasUsuario(Long nIdUsuario);
	
	public void deleteById(Long id);
	
	
	@Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE TwCarritoCompraPedido c SET c.nEstatus = 0 WHERE c.nEstatus = 1 and nIdUsuario=:usuario")
    int logicalDeleteAllActivos(Long usuario);
}
