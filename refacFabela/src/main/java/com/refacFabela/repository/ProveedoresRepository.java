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
}
