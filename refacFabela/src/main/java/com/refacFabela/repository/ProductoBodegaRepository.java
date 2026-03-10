package com.refacFabela.repository;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwProductobodega;

@Repository
public interface ProductoBodegaRepository extends JpaRepository<TwProductobodega, Long> {
	
	@Query("Select d from TwProductobodega d  where d.nIdProducto =:id and d.tcProducto.nEstatus = 1 order by d.nIdBodega asc" )
	public List<TwProductobodega> findBynIdProducto(Long id);
	
	@Query("Select d from TwProductobodega d  where d.nIdProducto =:id and d.nIdBodega=:nIdBodega and d.tcProducto.nEstatus = 1 order by d.nIdBodega asc" )
	public TwProductobodega findBynIdProductoIdBodega(Long id, Long nIdBodega);
	
	@Query("Select d from TwProductobodega d  where d.nIdBodega =:idBodega and d.nIdNivel=:idNivel and d.nIdAnaquel=:idAnaquel and d.tcProducto.nEstatus = 1 order by nCantidad desc" )
	public List<TwProductobodega> obtenerInventaroEsp(Long idBodega, Long idAnaquel, Long idNivel);
	
	@Query("Select d from TwProductobodega d  where d.nIdProducto =:nIProducto and d.tcBodega.sBodega =:bodega and d.tcProducto.nEstatus = 1" )
	public TwProductobodega obtenerProductoBodega(Long nIProducto, String bodega);
	
	@Query("Select d from TwProductobodega d  where d.nIdProducto =:producto and d.nIdBodega =:bodega and d.tcProducto.nEstatus = 1" )
	public TwProductobodega obtenerStockBodega(Long producto, Long bodega);
	
	/**
	 * Obtiene todas las bodegas de un producto CON bloqueo pesimista (SELECT FOR UPDATE).
	 * Usar dentro de @Transactional para evitar race conditions en traspasos y ventas.
	 */
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("Select d from TwProductobodega d where d.nIdProducto =:id and d.tcProducto.nEstatus = 1 order by d.nIdBodega asc")
	public List<TwProductobodega> findBynIdProductoForUpdate(Long id);
	
	/**
	 * Obtiene stock de una bodega específica CON bloqueo pesimista (SELECT FOR UPDATE).
	 */
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("Select d from TwProductobodega d where d.nIdProducto =:producto and d.nIdBodega =:bodega and d.tcProducto.nEstatus = 1")
	public TwProductobodega obtenerStockBodegaForUpdate(Long producto, Long bodega);
	  
	
}
