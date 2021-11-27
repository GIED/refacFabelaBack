package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TvTotalesGeneralesTablero;

@Repository
public interface TableroTotalesGeneralesRepository extends JpaRepository<TvTotalesGeneralesTablero, Long> {

	public TvTotalesGeneralesTablero findBynId(Long num);
}
