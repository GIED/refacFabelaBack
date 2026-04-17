package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TvVentaDetalle;
import com.refacFabela.model.TwVenta;

@Repository
public interface TvVentaDetalleRepository extends JpaRepository<TvVentaDetalle, Long> {
	
	@Query("Select c from TvVentaDetalle c where c.nIdCliente=:nIdCliente and c.nTipoPago=:nTipoPago and nSaldoTotal>0")
	public List<TvVentaDetalle> consultaVentaDetalleId(Long nIdCliente, long nTipoPago);
	
	@Query("Select c from TvVentaDetalle c where c.twCaja.nId=:idCaja order by c.tcUsuario.nId asc")
	public List<TvVentaDetalle> consultaVentaDetalleCajaVigente(Long idCaja);
	
	@Query("Select c from TvVentaDetalle c where c.tcEstatusVenta.nId=:nEstatusVenta")
	public List<TvVentaDetalle> consultaVentaDetalleIdEstatusVenta( long nEstatusVenta);
	
	
	@Query("Select c from TvVentaDetalle c where c.tcEstatusVenta.nId=:nEstatusVenta or (c.nTipoPago=1 and date(dFechaVenta)>=CURRENT_DATE - 7 ) order by dFechaVenta desc ")
	public List<TvVentaDetalle> consultaVentaDetalleIdEstatusVentaFechaCredito( long nEstatusVenta);
	
	@Query(value = "select de.* from tv_ventadetalle de join (\n" + 
			"select distinct(n_idVenta), count(n_idVenta) as total_no_entregados from  tw_ventas_producto where n_estatusEntregaAlmacen=0 group by n_idVenta)tot on de.n_id=tot.n_idVenta where tot.total_no_entregados>0 and de.n_estatusVenta>0 and de.n_estatusVenta<>3",   nativeQuery = true)
	public List<TvVentaDetalle> consultaVentaDetalleEntrega();
	

	@Query("Select c from TvVentaDetalle c where   c.tcCliente.sRazonSocial like %:buscar% or c.tcCliente.sRfc like %:buscar% or c.nId like %:buscar% ")
	public List<TvVentaDetalle> findByLike(String buscar);

	@Query(value="Select * from tv_ventadetalle order by n_id desc limit 100",   nativeQuery = true) 
	public List<TvVentaDetalle> findByTop();
	
	@Query("Select c from TvVentaDetalle c where c.nId=:nIdVenta")
	public TvVentaDetalle consultaVentaDetalleId( long nIdVenta);

	@Query(value = "SELECT " +
			"ven.n_id AS n_id, " +
			"ven.n_idCliente AS n_idCliente, " +
			"ven.n_idUsuario AS n_idUsuario, " +
			"ven.s_folioVenta AS s_folioVenta, " +
			"ven.d_fechaVenta AS d_fechaVenta, " +
			"ven.n_tipoPago AS n_tipoPago, " +
			"ven.n_estatusVenta AS n_estatusVenta, " +
			"ven.n_idTipoVenta AS n_idTipoVenta, " +
			"ven.nAnticipo AS n_anticipo, " +
			"ven.d_fechaInicioCredito AS d_fechaInicioCredito, " +
			"ven.d_fechaTerminoCredito AS d_fechaTerminoCredito, " +
			"ven.d_fechaPagoCredito AS d_fechaPagoCredito, " +
			"ven.n_idFormaPago AS n_idFormaPago, " +
			"ven.n_idCaja AS n_idCaja, " +
			"IFNULL(ven.nDescuento, 0) AS nDescuento, " +
			"IFNULL(tot.n_totalVenta, 0) AS n_totalVenta, " +
			"IFNULL(abo.n_totalAbono, 0) AS n_totalAbono, " +
			"TRUNCATE(TRUNCATE(CAST(IFNULL(tot.n_totalVenta, 0) AS DECIMAL(18,2)) - CAST(IFNULL(abo.n_totalAbono, 0) AS DECIMAL(18,2)), 2) - CAST(IFNULL(ven.nDescuento, 0) AS DECIMAL(18,2)), 2) AS n_saldoTotal, " +
			"IFNULL(TRUNCATE((CAST(IFNULL(abo.n_totalAbono, 0) AS DECIMAL(18,2)) * 100) / NULLIF(CAST(IFNULL(tot.n_totalVenta, 0) AS DECIMAL(18,2)) - CAST(IFNULL(ven.nDescuento, 0) AS DECIMAL(18,2)), 0), 2), 0) AS n_avancePago, " +
			"(CASE WHEN (ven.d_fechaTerminoCredito > SYSDATE()) THEN 'REGULAR' WHEN (ven.d_fechaTerminoCredito < SYSDATE()) THEN 'VENCIDO' ELSE '' END) AS s_estatus, " +
			"NULL AS n_saldo_favor, " +
			"NULL AS n_id_venta_utilizado, " +
			"ven.n_saldo AS n_saldo, " +
			"(CASE WHEN (ven.d_fechaTerminoCredito < SYSDATE()) THEN 1 ELSE 0 END) AS n_vencido " +
			"FROM tw_ventas ven " +
			"LEFT JOIN (SELECT v2.n_id AS n_idVenta, TRUNCATE(SUM(IFNULL(vp.n_totalPartida, 0)), 2) AS n_totalVenta FROM tw_ventas v2 LEFT JOIN tw_ventas_producto vp ON vp.n_idVenta = v2.n_id AND vp.n_estatus = 1 WHERE v2.n_idCliente = :nIdCliente GROUP BY v2.n_id) tot ON tot.n_idVenta = ven.n_id " +
			"LEFT JOIN (SELECT v3.n_id AS n_idVenta, TRUNCATE(SUM(IFNULL(a.n_abono, 0)), 2) AS n_totalAbono FROM tw_ventas v3 LEFT JOIN tw_abonos a ON a.n_idVenta = v3.n_id WHERE v3.n_idCliente = :nIdCliente GROUP BY v3.n_id) abo ON abo.n_idVenta = ven.n_id " +
			"WHERE ven.n_idCliente = :nIdCliente " +
			"ORDER BY ven.n_id DESC", nativeQuery = true)
	public List<TvVentaDetalle> findHistorialOptimizadoByNIdCliente(Long nIdCliente);

	@Query(value = "SELECT " +
			"ven.n_id AS n_id, " +
			"ven.n_idCliente AS n_idCliente, " +
			"ven.n_idUsuario AS n_idUsuario, " +
			"ven.s_folioVenta AS s_folioVenta, " +
			"ven.d_fechaVenta AS d_fechaVenta, " +
			"ven.n_tipoPago AS n_tipoPago, " +
			"ven.n_estatusVenta AS n_estatusVenta, " +
			"ven.n_idTipoVenta AS n_idTipoVenta, " +
			"ven.nAnticipo AS n_anticipo, " +
			"ven.d_fechaInicioCredito AS d_fechaInicioCredito, " +
			"ven.d_fechaTerminoCredito AS d_fechaTerminoCredito, " +
			"ven.d_fechaPagoCredito AS d_fechaPagoCredito, " +
			"ven.n_idFormaPago AS n_idFormaPago, " +
			"ven.n_idCaja AS n_idCaja, " +
			"IFNULL(ven.nDescuento, 0) AS nDescuento, " +
			"IFNULL(tot.n_totalVenta, 0) AS n_totalVenta, " +
			"IFNULL(abo.n_totalAbono, 0) AS n_totalAbono, " +
			"TRUNCATE(TRUNCATE(CAST(IFNULL(tot.n_totalVenta, 0) AS DECIMAL(18,2)) - CAST(IFNULL(abo.n_totalAbono, 0) AS DECIMAL(18,2)), 2) - CAST(IFNULL(ven.nDescuento, 0) AS DECIMAL(18,2)), 2) AS n_saldoTotal, " +
			"IFNULL(TRUNCATE((CAST(IFNULL(abo.n_totalAbono, 0) AS DECIMAL(18,2)) * 100) / NULLIF(CAST(IFNULL(tot.n_totalVenta, 0) AS DECIMAL(18,2)) - CAST(IFNULL(ven.nDescuento, 0) AS DECIMAL(18,2)), 0), 2), 0) AS n_avancePago, " +
			"(CASE WHEN (ven.d_fechaTerminoCredito > SYSDATE()) THEN 'REGULAR' WHEN (ven.d_fechaTerminoCredito < SYSDATE()) THEN 'VENCIDO' ELSE '' END) AS s_estatus, " +
			"NULL AS n_saldo_favor, " +
			"NULL AS n_id_venta_utilizado, " +
			"ven.n_saldo AS n_saldo, " +
			"(CASE WHEN (ven.d_fechaTerminoCredito < SYSDATE()) THEN 1 ELSE 0 END) AS n_vencido " +
			"FROM tw_ventas ven " +
			"LEFT JOIN (SELECT v2.n_id AS n_idVenta, TRUNCATE(SUM(IFNULL(vp.n_totalPartida, 0)), 2) AS n_totalVenta FROM tw_ventas v2 LEFT JOIN tw_ventas_producto vp ON vp.n_idVenta = v2.n_id AND vp.n_estatus = 1 WHERE v2.n_idCliente = :nIdCliente GROUP BY v2.n_id) tot ON tot.n_idVenta = ven.n_id " +
			"LEFT JOIN (SELECT v3.n_id AS n_idVenta, TRUNCATE(SUM(IFNULL(a.n_abono, 0)), 2) AS n_totalAbono FROM tw_ventas v3 LEFT JOIN tw_abonos a ON a.n_idVenta = v3.n_id WHERE v3.n_idCliente = :nIdCliente GROUP BY v3.n_id) abo ON abo.n_idVenta = ven.n_id " +
			"WHERE ven.n_idCliente = :nIdCliente " +
			"AND (:fechaInicio IS NULL OR ven.d_fechaVenta >= :fechaInicio) " +
			"ORDER BY ven.n_id DESC", nativeQuery = true)
	public List<TvVentaDetalle> findHistorialOptimizadoByNIdClienteAndFecha(@Param("nIdCliente") Long nIdCliente,
			@Param("fechaInicio") String fechaInicio);

	@Query(value = "SELECT " +
			"ven.n_id AS n_id, " +
			"ven.n_idCliente AS n_idCliente, " +
			"ven.n_idUsuario AS n_idUsuario, " +
			"ven.s_folioVenta AS s_folioVenta, " +
			"ven.d_fechaVenta AS d_fechaVenta, " +
			"ven.n_tipoPago AS n_tipoPago, " +
			"ven.n_estatusVenta AS n_estatusVenta, " +
			"ven.n_idTipoVenta AS n_idTipoVenta, " +
			"ven.nAnticipo AS n_anticipo, " +
			"ven.d_fechaInicioCredito AS d_fechaInicioCredito, " +
			"ven.d_fechaTerminoCredito AS d_fechaTerminoCredito, " +
			"ven.d_fechaPagoCredito AS d_fechaPagoCredito, " +
			"ven.n_idFormaPago AS n_idFormaPago, " +
			"ven.n_idCaja AS n_idCaja, " +
			"IFNULL(ven.nDescuento, 0) AS nDescuento, " +
			"IFNULL(tot.n_totalVenta, 0) AS n_totalVenta, " +
			"IFNULL(abo.n_totalAbono, 0) AS n_totalAbono, " +
			"TRUNCATE(TRUNCATE(CAST(IFNULL(tot.n_totalVenta, 0) AS DECIMAL(18,2)) - CAST(IFNULL(abo.n_totalAbono, 0) AS DECIMAL(18,2)), 2) - CAST(IFNULL(ven.nDescuento, 0) AS DECIMAL(18,2)), 2) AS n_saldoTotal, " +
			"IFNULL(TRUNCATE((CAST(IFNULL(abo.n_totalAbono, 0) AS DECIMAL(18,2)) * 100) / NULLIF(CAST(IFNULL(tot.n_totalVenta, 0) AS DECIMAL(18,2)) - CAST(IFNULL(ven.nDescuento, 0) AS DECIMAL(18,2)), 0), 2), 0) AS n_avancePago, " +
			"(CASE WHEN (ven.d_fechaTerminoCredito > SYSDATE()) THEN 'REGULAR' WHEN (ven.d_fechaTerminoCredito < SYSDATE()) THEN 'VENCIDO' ELSE '' END) AS s_estatus, " +
			"NULL AS n_saldo_favor, " +
			"NULL AS n_id_venta_utilizado, " +
			"ven.n_saldo AS n_saldo, " +
			"(CASE WHEN (ven.d_fechaTerminoCredito < SYSDATE()) THEN 1 ELSE 0 END) AS n_vencido " +
			"FROM tw_ventas ven " +
			"LEFT JOIN (SELECT v2.n_id AS n_idVenta, TRUNCATE(SUM(IFNULL(vp.n_totalPartida, 0)), 2) AS n_totalVenta FROM tw_ventas v2 LEFT JOIN tw_ventas_producto vp ON vp.n_idVenta = v2.n_id AND vp.n_estatus = 1 WHERE v2.n_idCliente = :nIdCliente GROUP BY v2.n_id) tot ON tot.n_idVenta = ven.n_id " +
			"LEFT JOIN (SELECT v3.n_id AS n_idVenta, TRUNCATE(SUM(IFNULL(a.n_abono, 0)), 2) AS n_totalAbono FROM tw_ventas v3 LEFT JOIN tw_abonos a ON a.n_idVenta = v3.n_id WHERE v3.n_idCliente = :nIdCliente GROUP BY v3.n_id) abo ON abo.n_idVenta = ven.n_id " +
			"WHERE ven.n_idCliente = :nIdCliente " +
			"AND ven.n_tipoPago = 1 " +
			"AND ven.d_fechaPagoCredito IS NULL " +
			"AND TRUNCATE(TRUNCATE(CAST(IFNULL(tot.n_totalVenta, 0) AS DECIMAL(18,2)) - CAST(IFNULL(abo.n_totalAbono, 0) AS DECIMAL(18,2)), 2) - CAST(IFNULL(ven.nDescuento, 0) AS DECIMAL(18,2)), 2) > 0 " +
			"ORDER BY ven.d_fechaVenta ASC", nativeQuery = true)
	public List<TvVentaDetalle> findPendientesOptimizadoByNIdCliente(Long nIdCliente);
	
	
}
