package com.refacFabela.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcUsuario;

@Repository
public interface UsuariosRepository extends JpaRepository<TcUsuario, Long> {

	/**
	 * consulta por nombre de usuario
	 * @param nombreUsuario
	 * @return
	 */
	Optional<TcUsuario> findBysUsuario(String nombreUsuario);
	
	/**
	 * consulta si existe el nombre de usuario
	 * @param nombreUsuario
	 * @return
	 */
	boolean existsBysUsuario(String usuario);
}
