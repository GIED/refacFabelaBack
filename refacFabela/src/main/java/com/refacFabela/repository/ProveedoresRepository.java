package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcProveedore;
@Repository
public interface ProveedoresRepository extends JpaRepository<TcProveedore, Long> {
			
	@Query("Select c from TcProveedore c where c.nEstatus=1 order by c.sRazonSocial asc")
	public List<TcProveedore> findBynEstatus(int estatus);
	
	@Query("SELECT c FROM TcProveedore c \r\n"
			+ "WHERE c.nEstatus = 1 \r\n"
			+ "AND (c.sRazonSocial LIKE CONCAT('%', :busqueda, '%') \r\n"
			+ "OR c.sRfc LIKE CONCAT('%', :busqueda, '%')) \r\n"
			+ "ORDER BY c.sRazonSocial ASC")
	public List<TcProveedore> findProveedorLike(String busqueda);
}
