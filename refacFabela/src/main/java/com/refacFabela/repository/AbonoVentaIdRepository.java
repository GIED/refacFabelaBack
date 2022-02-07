package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwAbono;

@Repository
public interface AbonoVentaIdRepository extends JpaRepository<TwAbono, Long> {
	
	public List<TwAbono> findBynIdVenta(Long id);
	

}
