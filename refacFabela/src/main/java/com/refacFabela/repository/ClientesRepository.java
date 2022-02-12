package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcCliente;
@Repository
public interface ClientesRepository extends JpaRepository<TcCliente, Long> {

	public List<TcCliente> findBynEstatus(int estatus);
	
    @Query("Select c from TcCliente c where c.sRfc like %:clienteBuscar% or c.sRazonSocial like %:clienteBuscar%")
	public List<TcCliente> buscarClineteLike(String clienteBuscar);
    
    public TcCliente findBysRfc(String rfc);
    
    public TcCliente findBynIdUsuario(Long id);
}
