package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.dto.ProductoDto;
import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TwProductobodega;

@Repository
public interface ProductosRepository extends JpaRepository<TcProducto, Long> {
	
	public TcProducto findBysNoParte(String No_parte );
	
	public List<TcProducto> findBynEstatus(int estatus);
	
	
	@Query("Select c from TcProducto c where c.sNoParte like %:producto% or c.sProducto like %:producto% or c.sDescripcion like %:producto%")
	public List<TcProducto> ConsultaProductoLike(String producto);
	
	@Query("Select c from TcProducto c where c.sNoParte like %:No_Parte%")
	public List<TcProducto> ConsultaNoParteLike(String No_Parte);
	
	
	

}
