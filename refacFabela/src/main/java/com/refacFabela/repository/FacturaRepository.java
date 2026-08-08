package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwFacturacion;

@Repository
public interface FacturaRepository extends JpaRepository<TwFacturacion, Long> {

	@Query("SELECT f FROM TwFacturacion f WHERE f.n_idVenta = :nIdVenta AND f.nEstatus = 1 ORDER BY f.nId ASC")
	List<TwFacturacion> findActivasByVenta(@Param("nIdVenta") Long nIdVenta);

	@Query("SELECT f FROM TwFacturacion f WHERE f.n_idVenta = :nIdVenta ORDER BY f.nId DESC")
	List<TwFacturacion> findByVenta(@Param("nIdVenta") Long nIdVenta);

}
