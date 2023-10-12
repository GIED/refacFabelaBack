package com.refacFabela.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwAjustesInventario;
import com.refacFabela.model.TwVentaProductoCancela;

@Repository
public interface TwAjusteInventarioRepository extends JpaRepository<TwAjustesInventario, Long> {

	
	@Query("Select c from TwAjustesInventario c where  c.sFecha>=:fechaInicio and c.sFecha<=:fechaTermino order by c.nId desc")
	public List<TwAjustesInventario> findByBuscar(Date fechaInicio, Date fechaTermino);
	
}
