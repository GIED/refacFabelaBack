package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcFormapago;
import com.refacFabela.model.TcMoneda;

@Repository
public interface TcMonedaRepository extends JpaRepository<TcMoneda, Long> {
	
	public List<TcMoneda> findBynEstatus(int estatus);

}
