package com.refacFabela.repository;


import java.util.List;
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
	@Query("Select e from TcUsuario e where e.sUsuario= :usuario")
	public TcUsuario obtenerUsuarioNombre(String usuario);	
	Optional<TcUsuario> findBysUsuario(String nombreUsuario);
	@Query("Select e from TcUsuario e where e.nEstatus=1")
	public List<TcUsuario> obtenerUsuariosActivos();
	
	boolean existsBysUsuario(String usuario);
}
