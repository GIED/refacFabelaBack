package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TwSaldoUtilizado;

@Repository
public interface TwSaldoUtilizadoRepository extends JpaRepository<TwSaldoUtilizado, Long> {
	
	@Query("Select c from TwSaldoUtilizado c where c.nIdVenta=:n_idVenta  ")
	public List<TwSaldoUtilizado> consultaSaldosUtilizados(Long n_idVenta);

}
