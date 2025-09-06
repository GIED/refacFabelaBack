package com.refacFabela.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwClienteDireccion;

@Repository
public interface TwClienteDireccionRepository extends JpaRepository<TwClienteDireccion, Long> {

	List<TwClienteDireccion> findBynIdCliente(Long nIdCliente, Sort sort);

	@Query("SELECT d FROM TwClienteDireccion d WHERE d.nId = :id AND d.nIdCliente = :clienteId")
	Optional<TwClienteDireccion> findOneByIdAndCliente(@Param("id") Long id, @Param("clienteId") Long clienteId);


	long countBynIdCliente(Long nIdCliente);

	@Modifying
	@Query("UPDATE TwClienteDireccion d SET d.bPredeterminada = false WHERE d.nIdCliente = :clienteId")
	int clearPredeterminada(@Param("clienteId") Long clienteId);

	@Modifying
	@Query("UPDATE TwClienteDireccion d SET d.bPredeterminada = true WHERE d.nId = :id AND d.nIdCliente = :clienteId")
	int setPredeterminada(@Param("clienteId") Long clienteId, @Param("id") Long id);

}
