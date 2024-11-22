package com.refacFabela.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.refacFabela.model.VwProductoMetaCompra;

@Repository
public interface VwProductoMetaCompraRepository extends JpaRepository<VwProductoMetaCompra, Long> {
	
	@Query(value = "SELECT * FROM vw_producto_meta_compra c WHERE c.d_ultima_fecha_compra >= STR_TO_DATE(:fechaInicio, '%Y-%m-%d') AND c.d_ultima_fecha_compra <= STR_TO_DATE(:fechaTermino, '%Y-%m-%d' ) order by n_cantidad asc", nativeQuery = true)
	public List<VwProductoMetaCompra> ultimafechaCompra(String fechaInicio, String fechaTermino);

}
