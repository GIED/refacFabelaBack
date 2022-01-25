package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcProveedore;
@Repository
public interface ProveedoresRepository extends JpaRepository<TcProveedore, Long> {
			public List<TcProveedore> findBynEstatus(int estatus);
}
