package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TvVentaDetalle;

@Repository
public interface TvVentaDetalleRepository extends JpaRepository<TvVentaDetalle, Long> {
	
	@Query("Select c from TvVentaDetalle c where c.nIdCliente=:nIdCliente and c.nTipoPago=:nTipoPago")
	public List<TvVentaDetalle> consultaVentaDetalleId(Long nIdCliente, long nTipoPago);
	
	@Query("Select c from TvVentaDetalle c where c.twCaja.nId=:idCaja order by c.tcUsuario.nId asc")
	public List<TvVentaDetalle> consultaVentaDetalleCajaVigente(Long idCaja);
	
	@Query("Select c from TvVentaDetalle c where c.tcEstatusVenta.nId=:nEstatusVenta")
	public List<TvVentaDetalle> consultaVentaDetalleIdEstatusVenta( long nEstatusVenta);
	
	@Query(value = "select de.* from tv_ventadetalle de join (\n" + 
			"select distinct(n_idVenta), count(n_idVenta) as total_no_entregados from  tw_ventas_producto where n_estatusEntregaAlmacen=0 group by n_idVenta)tot on de.n_id=tot.n_idVenta where tot.total_no_entregados>0 and de.n_estatusVenta>0 and de.n_estatusVenta<>3",   nativeQuery = true)
	public List<TvVentaDetalle> consultaVentaDetalleEntrega();
	
	
	
}
