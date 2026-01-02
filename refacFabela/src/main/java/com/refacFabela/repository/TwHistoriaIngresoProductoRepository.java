package com.refacFabela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.refacFabela.model.TwHistoriaIngresoProducto;

@Repository
public interface TwHistoriaIngresoProductoRepository extends JpaRepository<TwHistoriaIngresoProducto, Long> {
	
	
	@Query("Select e from TwHistoriaIngresoProducto e where e.nIdProducto=:nId order by nIdPedido desc")	
	List<TwHistoriaIngresoProducto> obtenerIngresoProductos(Long nId);
	
	@Query(value = " SELECT \r\n"
			+ "	            uni.n_id_producto       AS nIdProducto,\r\n"
			+ "	            uni.s_folio_factura     AS sFolioFactura,\r\n"
			+ "	            uni.s_razon_social      AS sRazonSocial,\r\n"
			+ "	            uni.n_cantidad          AS nCantidad,\r\n"
			+ "	            uni.d_fecha_ingreso     AS dFechaIngreso,\r\n"
			+ "	            uni.s_ubicacion         AS sUbicacion,\r\n"
			+ "	            uni.s_nombre_usuario    AS sNombreUsuario\r\n"
			+ "	        FROM (\r\n"
			+ "	            SELECT \r\n"
			+ "	                his.n_id_producto                                  AS n_id_producto,\r\n"
			+ "	                CONCAT('P-', ped.n_id)                             AS s_folio_factura,\r\n"
			+ "	                'SIN INFORMACÓN'                                   AS s_razon_social,\r\n"
			+ "	                his.n_cantidad                                     AS n_cantidad,\r\n"
			+ "	                his.d_fecha_ingreso                                AS d_fecha_ingreso,\r\n"
			+ "	                bod.s_bodega                                       AS s_ubicacion,\r\n"
			+ "	                usu.s_nombreUsuario                                AS s_nombre_usuario\r\n"
			+ "	            FROM tw_historia_ingreso_producto his\r\n"
			+ "	            LEFT JOIN tc_bodegas  bod ON his.n_id_bodega  = bod.n_id\r\n"
			+ "	            LEFT JOIN tc_usuarios usu ON his.n_id_usuario = usu.n_id\r\n"
			+ "	            LEFT JOIN tw_pedido   ped ON his.n_id_pedido  = ped.n_id\r\n"
			+ "	            WHERE his.n_id_producto = :idProducto\r\n"
			+ "\r\n"
			+ "	            UNION ALL\r\n"
			+ "\r\n"
			+ "	            SELECT \r\n"
			+ "	                fac.n_id_producto                                  AS n_id_producto,\r\n"
			+ "	                facpro.s_folio_factura                             AS s_folio_factura,\r\n"
			+ "	                prove.s_razon_social                               AS s_razon_social,\r\n"
			+ "	                ing.n_cantidad                                     AS n_cantidad,\r\n"
			+ "	                ing.d_fecha_ingreso                                AS d_fecha_ingreso,\r\n"
			+ "	                CONCAT(bod.s_bodega,'-',ana.s_anaquel,'-',niv.s_nivel) AS s_ubicacion,\r\n"
			+ "	                usu.s_nombreUsuario                                AS s_nombre_usuario\r\n"
			+ "	            FROM tw_factura_proveedor_producto fac\r\n"
			+ "	            INNER JOIN tw_factura_proveedor facpro \r\n"
			+ "	                ON fac.n_id_factura_proveedor = facpro.n_id\r\n"
			+ "	            INNER JOIN tc_proveedores prove \r\n"
			+ "	                ON facpro.n_id_proveedor = prove.n_id\r\n"
			+ "	            INNER JOIN tw_factura_proveedor_producto_ingreso ing \r\n"
			+ "	                ON fac.n_id = ing.n_id_factura_proveedor_producto\r\n"
			+ "	            INNER JOIN tc_anaquel ana ON ana.n_id = ing.n_id_anaquel\r\n"
			+ "	            INNER JOIN tc_nivel niv   ON niv.n_id = ing.n_id_nivel\r\n"
			+ "	            INNER JOIN tc_bodegas bod ON ing.n_id_bodega = bod.n_id\r\n"
			+ "	            INNER JOIN tc_usuarios usu ON usu.n_id = ing.n_id_usuario\r\n"
			+ "	            WHERE fac.n_id_producto = :idProducto\r\n"
			+ "	        ) uni\r\n"
			+ "	        ORDER BY uni.d_fecha_ingreso DESC"   , nativeQuery = true)
	    List<HistoriaIngresoProductoView> obtenerHistoriaIngresoProducto(@Param("idProducto") Long idProducto);

}
