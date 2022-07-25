package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TrVentaCobro;

@Repository
public interface TrVentaCobroRepository extends JpaRepository<TrVentaCobro, Long> {
	
    @Query("Select c from TrVentaCobro c where c.nIdVenta=:nIdVenta")
    public List<TrVentaCobro> obtenerPagosParciales(Long nIdVenta);
   
    @Query("Select c from TrVentaCobro c where c.nIdCaja=:nIdCaja")
    public List<TrVentaCobro> obtenerPagosCaja(Long nIdCaja);
    

}
