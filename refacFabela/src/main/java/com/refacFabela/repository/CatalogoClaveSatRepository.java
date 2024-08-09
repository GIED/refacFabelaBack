package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcClavesat;
import com.refacFabela.model.TwCaja;

@Repository
public interface CatalogoClaveSatRepository extends JpaRepository<TcClavesat, Long> {
	

	@Query("Select e from TcClavesat e where e.nEstatus=1 order by sDescripcion asc ")
	public List<TcClavesat> obtenerClaveSat();

}
