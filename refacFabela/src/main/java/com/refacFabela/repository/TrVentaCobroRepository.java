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
    
    @Query(value = "select truncate(ifnull(sum(c.n_monto),0),2)  from tr_venta_cobro c where c.n_id_caja=:nIdCaja and n_id_forma_pago=1",   nativeQuery = true) 
    public Double TotalPagoEfectivoVenta(Long nIdCaja);
    @Query(value = "select truncate(ifnull(sum(c.n_monto),0),2) from tr_venta_cobro c where c.n_id_caja=:nIdCaja and (n_id_forma_pago=2 or  n_id_forma_pago=3 or  n_id_forma_pago=4 or  n_id_forma_pago=18)",   nativeQuery = true) 
    public Double TotalPagoElectronico(Long nIdCaja);
    

}
