package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcTipoRevendedor;

@Repository
public interface TcTipoRevendedorRepository extends JpaRepository<TcTipoRevendedor, Long> {

	@Query(value = "select c from TcTipoRevendedor c where c.nEstatus = 1 order by c.nId asc")
	public List<TcTipoRevendedor> getTipoRevendedor();
}
