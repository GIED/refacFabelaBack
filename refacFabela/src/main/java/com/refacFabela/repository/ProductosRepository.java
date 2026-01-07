package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.dto.ProductoDto;
import com.refacFabela.model.TcNivel;
import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TwProductobodega;

@Repository
public interface ProductosRepository extends JpaRepository<TcProducto, Long> {
	
	public TcProducto findBysNoParte(String No_parte );
	
	public List<TcProducto> findBynEstatus(int estatus);
	
	
	@Query("Select c from TcProducto c where (c.sNoParte like %:producto% or c.sProducto like %:producto% or c.sDescripcion like %:producto% or c.sMarca like %:producto%) and nEstatus=1 order by c.sMarca asc ")
	public List<TcProducto> ConsultaProductoLike(String producto);
	
	@Query("Select c from TcProducto c where (c.sNoParte like %:No_Parte% or c.sProducto like %:No_Parte% or c.sDescripcion like %:No_Parte% or c.sMarca like %:No_Parte%) and nEstatus=1 order by c.sMarca asc")
	public List<TcProducto> ConsultaNoParteLike(String No_Parte);
	
	@Query("Select c from TcProducto c where c.nId=:nId and nEstatus=1")
	public TcProducto findBynId(Long nId);
	
	@Query("Select c from TcProducto c where c.nId=:nId and nEstatus=1")
	public List<TcProducto> consultarPorId(Long nId);
	
	@Query("Select c from TcProducto c where c.sNoParte=:noParte AND (c.nIdMarca = :nIdMarca OR c.nIdMarca IS NULL)")
	public TcProducto findBysNoParteAndIdMarca(String noParte, Long nIdMarca);
	
	@Query("Select c from TcProducto c where c.nEstatus = 1 AND c.sMarca = 'CTP' AND (c.sRutaImagen IS NULL OR c.sRutaImagen = '') ")
	public List<TcProducto> findProductosCTPSinImagen();
	
	

}
