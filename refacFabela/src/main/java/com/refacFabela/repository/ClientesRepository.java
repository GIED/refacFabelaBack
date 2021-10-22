package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcCliente;
@Repository
public interface ClientesRepository extends JpaRepository<TcCliente, Long> {

	public List<TcCliente> findBynEstatus(int estatus);
}
