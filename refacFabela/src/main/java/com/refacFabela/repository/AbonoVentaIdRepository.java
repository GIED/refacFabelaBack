package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwCaja;

@Repository
public interface AbonoVentaIdRepository extends JpaRepository<TwAbono, Long> {
	
	public List<TwAbono> findBynIdVenta(Long id);
	
	@Query("Select e from TwAbono e where e.twCaja.nId= :nIdCaja ")
	public List<TwAbono> obtenerAbonosCaja(Long nIdCaja);
	 @Query(value = "Select truncate(ifnull(sum(e.n_abono),0),2) from tw_abonos e where e.n_idCaja=:nIdCaja and e.n_idFormaPago=1 ",   nativeQuery = true) 
	public Double TotalAbonoEfectivo(Long nIdCaja);
	 @Query(value = "select truncate(ifnull(sum(e.n_abono),0),2) from tw_abonos e where e.n_idCaja=:nIdCaja and (e.n_idFormaPago=2 or e.n_idFormaPago=3  or e.n_idFormaPago=4  or e.n_idFormaPago=18)",   nativeQuery = true) 
	public Double TotalAbonoElectronico(Long nIdCaja);
	

}
