package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwProductobodega;

@Repository
public interface TwProductoBodegaRepository extends JpaRepository<TwProductobodega, Long> {

	@Query("select c from TwProductobodega c where c.nIdProducto=:nIdProducto order by c.nIdBodega asc ")
	public List<TwProductobodega> productoBogas(Long nIdProducto);
	
	/**
	 * Obtener productos de una ubicación específica (bodega + anaquel + nivel)
	 */
	@Query("SELECT pb FROM TwProductobodega pb WHERE pb.nIdBodega = :nIdBodega " +
	       "AND pb.nIdAnaquel = :nIdAnaquel AND pb.nIdNivel = :nIdNivel " +
	       "AND pb.nCantidad > 0 ORDER BY pb.tcProducto.sNoParte")
	List<TwProductobodega> obtenerProductosPorUbicacion(
		@Param("nIdBodega") Long nIdBodega,
		@Param("nIdAnaquel") Long nIdAnaquel,
		@Param("nIdNivel") Long nIdNivel
	);

}
