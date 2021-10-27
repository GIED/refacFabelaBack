package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcGanancia;

@Repository
public interface CatalogoGananciaRepository extends JpaRepository<TcGanancia, Long> {

}
