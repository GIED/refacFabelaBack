package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcFormapago;


@Repository
public interface CatalagoFormaPagoRepository extends JpaRepository<TcFormapago, Long> {
	public List<TcFormapago> findBynEstatus(int estatus);

}
