package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TvSaldoGeneralCliente;
import com.refacFabela.model.TvTotalesGeneralesTablero;
import com.refacFabela.model.VwVentaMesAno;
import com.refacFabela.model.VwVentaProductoAno;

@Repository
public interface TableroTotalesGeneralesRepository extends JpaRepository<TvTotalesGeneralesTablero, Long> {

	public TvTotalesGeneralesTablero findBynId(Long num);
	
	@Query("Select c from VwVentaMesAno c where c.sAno=:ano")
	public List<VwVentaMesAno> obtenerVentaMesAno(String ano);
	


	
	
}
