package com.refacFabela.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcUsuario;
import com.refacFabela.model.TwCaja;

@Repository
public interface UsuariosRepository extends JpaRepository<TcUsuario, Long> {

	
	@Query("Select e from TcUsuario e where e.nId= :nIdUsuario")
	public TcUsuario obtenerUsuario(Long nIdUsuario);

	
	Optional<TcUsuario> findBysUsuario(String nombreUsuario);
	
	/**
	 * consulta si existe el nombre de usuario
	 * @param nombreUsuario
	 * @return
	 */
	boolean existsBysUsuario(String usuario);
}
