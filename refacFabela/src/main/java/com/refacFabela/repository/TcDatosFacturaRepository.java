package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcDatosFactura;
import com.refacFabela.model.TwPedidoProducto;

@Repository
public interface TcDatosFacturaRepository extends JpaRepository<TcDatosFactura, Long> {

	
	@Query("Select c from TcDatosFactura c where c.nId= :nId")
	TcDatosFactura obtenerDatos(Long nId);
	
	
}
