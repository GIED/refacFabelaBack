package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwVentaProductosTraer;

@Repository
public interface TwVentaProductosTraerRepository extends JpaRepository<TwVentaProductosTraer, Long> {

	@Query("Select c from TwVentaProductosTraer c where c.nIdVenta=:nIdVenta")
	public List<TwVentaProductosTraer> findBynIdVenta(Long nIdVenta);
}
 
