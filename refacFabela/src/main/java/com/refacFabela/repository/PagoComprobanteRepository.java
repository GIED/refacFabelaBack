package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwPagoComprobanteInternet;

@Repository
public interface PagoComprobanteRepository extends JpaRepository<TwPagoComprobanteInternet, Long> {
	
	@Query(value = "from TwPagoComprobanteInternet t where t.nIdCotizacion=:idCotizacion and t.nIdCliente=:idCliente")
	public TwPagoComprobanteInternet consultaSiComprobanteExiste(Long idCotizacion, Long idCliente);
	
	public List<TwPagoComprobanteInternet> findBynStatus(Integer estatus);
	
	public List<TwPagoComprobanteInternet> findBynIdCliente(Long idCliente);

}
