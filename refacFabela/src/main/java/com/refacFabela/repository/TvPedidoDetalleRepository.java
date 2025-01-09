package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TvPedidoDetalle;
import com.refacFabela.model.TwPedidoProducto;

@Repository
public interface TvPedidoDetalleRepository extends JpaRepository<TvPedidoDetalle, Long>{
	
	@Query("Select c from TvPedidoDetalle c where c.nEstatus= :nEstatus order by nId desc")
	List<TvPedidoDetalle> obtenerPedidosDetalleEstatus(long nEstatus);
	@Query("Select c from TvPedidoDetalle c order by c.nId desc ")
	List<TvPedidoDetalle> obtenerPedidosDetalle();
	

}
