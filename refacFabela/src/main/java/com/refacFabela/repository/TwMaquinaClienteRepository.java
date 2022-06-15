package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwMaquinaCliente;

@Repository
public interface TwMaquinaClienteRepository extends JpaRepository<TwMaquinaCliente, Long> {
	
	@Query("Select e from TwMaquinaCliente e where nIdCliente=:nIdCliente")	
	List<TwMaquinaCliente> buscarMaquinasCliente(Long nIdCliente);

}
