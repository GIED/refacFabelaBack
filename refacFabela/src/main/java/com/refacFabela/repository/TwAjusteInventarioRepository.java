package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwAjustesInventario;

@Repository
public interface TwAjusteInventarioRepository extends JpaRepository<TwAjustesInventario, Long> {

}
