package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.refacFabela.model.VwVentaProductoAno;

public interface VwVentaProductoRepository extends JpaRepository<VwVentaProductoAno, Long> {
	
	@Query(value="SELECT * FROM vw_total_venta_producto_ano a where a.ano=:ano ORDER BY a.total_venta DESC LIMIT 100 ",  nativeQuery = true)
	public List<VwVentaProductoAno> obtenerVentaProductoAno(String ano);

}
