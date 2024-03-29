package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwProductobodega;

@Repository
public interface ProductoBodegaRepository extends JpaRepository<TwProductobodega, Long> {
	
	@Query("Select d from TwProductobodega d  where d.nIdProducto =:id order by d.nIdBodega asc" )
	public List<TwProductobodega> findBynIdProducto(Long id);
	
	@Query("Select d from TwProductobodega d  where d.nIdProducto =:id and d.nIdBodega=:nIdBodega order by d.nIdBodega asc" )
	public TwProductobodega findBynIdProductoIdBodega(Long id, Long nIdBodega);
	
	@Query("Select d from TwProductobodega d  where d.nIdBodega =:idBodega and d.nIdNivel=:idNivel and d.nIdAnaquel=:idAnaquel order by nCantidad desc" )
	public List<TwProductobodega> obtenerInventaroEsp(Long idBodega, Long idAnaquel, Long idNivel);
	
	@Query("Select d from TwProductobodega d  where d.nIdProducto =:nIProducto and d.tcBodega.sBodega =:bodega " )
	public TwProductobodega obtenerProductoBodega(Long nIProducto, String bodega);
	
	@Query("Select d from TwProductobodega d  where d.nIdProducto =:producto and d.nIdBodega =:bodega " )
	public TwProductobodega obtenerStockBodega(Long producto, Long bodega);
	  
	
}
