package com.refacFabela.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.refacFabela.dto.VentaCotizacionProductoAnoDto;

@Repository
public class VentasCotizacionesProductoAnoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<VentaCotizacionProductoAnoDto> getSalesAndQuotations(int n_idProductos) {
        String sql = "SELECT " +
                     "c.s_ano AS ano, " +
                     "COALESCE(v.total_ventas, 0) AS total_ventas, " +
                     "COALESCE(cot.total_cotizaciones, 0) AS total_cotizaciones " +
                     "FROM tc_ano c " +
                     "LEFT JOIN ( " +
                     "    SELECT " +
                     "        YEAR(ve.d_fechaVenta) AS ano, " +
                     "        SUM(vp.n_cantidad) AS total_ventas " +
                     "    FROM " +
                     "        tw_ventas ve " +
                     "        INNER JOIN tw_ventas_producto vp " +
                     "        ON ve.n_id = vp.n_idVenta " +
                     "        AND vp.n_estatus = 1 " +
                     "        AND vp.n_idProductos = :n_idProductos " +
                     "    GROUP BY " +
                     "        YEAR(ve.d_fechaVenta) " +
                     ") v ON c.s_ano = v.ano " +
                     "LEFT JOIN ( " +
                     "    SELECT " +
                     "        YEAR(cot.d_fecha) AS ano, " +
                     "        SUM(cp.n_cantidad) AS total_cotizaciones " +
                     "    FROM " +
                     "        tw_cotizaciones cot " +
                     "        INNER JOIN tw_cotizaciones_producto cp " +
                     "        ON cot.n_id = cp.n_idCotizaciones " +
                     "        AND cp.n_idProductos = :n_idProductos " +
                     "    GROUP BY " +
                     "        YEAR(cot.d_fecha) " +
                     ") cot ON c.s_ano = cot.ano " +
                     "ORDER BY " +
                     "    c.s_ano";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("n_idProductos", n_idProductos);
        List<Object[]> resultList = query.getResultList();

        List<VentaCotizacionProductoAnoDto> salesList = new ArrayList<>();
        for (Object[] result : resultList) {
        	VentaCotizacionProductoAnoDto salesDTO = new VentaCotizacionProductoAnoDto();
            salesDTO.setAno((String) result[0]);
            salesDTO.setTotalVentas(((Number) result[1]).intValue());
            salesDTO.setTotalCotizaciones(((Number) result[2]).intValue());
            salesList.add(salesDTO);
        }

        return salesList;
    }
}
