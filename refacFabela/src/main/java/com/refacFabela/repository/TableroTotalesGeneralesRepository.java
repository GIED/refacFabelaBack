package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TvSaldoGeneralCliente;
import com.refacFabela.model.TvTotalesGeneralesTablero;
import com.refacFabela.model.VwVentaMesAno;
import com.refacFabela.model.VwVentaProductoAno;
import com.refacFabela.model.VwVentasAnoMesVendedores;
import com.refacFabela.model.VwVentasAnoVendedor;

@Repository
public interface TableroTotalesGeneralesRepository extends JpaRepository<TvTotalesGeneralesTablero, Long> {

	public TvTotalesGeneralesTablero findBynId(Long num);
	
	@Query("Select c from VwVentaMesAno c where c.sAno=:ano")
	public List<VwVentaMesAno> obtenerVentaMesAno(String ano);
	
	@Query("Select c from VwVentasAnoVendedor c where c.sAno=:ano")
	public List<VwVentasAnoVendedor> obtenerVentaAnoVendedor(String ano);
	
	@Query("Select c from VwVentasAnoMesVendedores c where c.sAno=:ano and c.nIdUsuario=:id")
	public List<VwVentasAnoMesVendedores> obtenerVentaAnoMesVendedor(String ano, Long id);
	


	
	
}
