package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwProductosAlternativo;
@Repository
public interface ProductosAlternativosRepository extends JpaRepository<TwProductosAlternativo, Long> {
	
	@Query(value = "select p from TwProductosAlternativo p where p.nIdProducto=:nIdProducto and p.nEstatus=:nEsatus order by tcProductoAlternativo.sNoParte asc ")
	public List<TwProductosAlternativo> consultaProductosAlternativos(Long nIdProducto, int nEsatus);
	

}
