package com.refacFabela.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.enums.RolNombre;
import com.refacFabela.model.TcRol;
@Repository
public interface RolRepository extends JpaRepository<TcRol, Long> {
	
	Optional<TcRol> findBysRol(RolNombre sRol);
	
}
