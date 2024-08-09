package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcEstatusFacturaProveedor;



@Repository
public interface TcEstatusFacturaProveedorRepository extends JpaRepository<TcEstatusFacturaProveedor, Long> {
	
	public List<TcEstatusFacturaProveedor> findBynEstatus(int estatus);

}
