package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwProductobodega;

@Repository
public interface TwProductoBodegaRepository extends JpaRepository<TwProductobodega, Long> {

	@Query("select c from TwProductobodega c where c.nIdProducto=:nIdProducto order by c.nIdBodega asc ")
	public List<TwProductobodega> productoBogas(Long nIdProducto);
	

}
