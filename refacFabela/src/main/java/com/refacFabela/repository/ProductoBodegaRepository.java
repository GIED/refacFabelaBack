package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwProductobodega;

@Repository
public interface ProductoBodegaRepository extends JpaRepository<TwProductobodega, Long> {

	public List<TwProductobodega> findBynIdProducto(Long id);
}
