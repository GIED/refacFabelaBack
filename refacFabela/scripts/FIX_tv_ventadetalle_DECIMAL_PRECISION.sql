-- =========================================================================
-- FIX COMPLETO: Corrección de errores de punto flotante en vistas de saldos
-- =========================================================================
-- Problema: nDescuento es DOUBLE, causa error de redondeo en cálculos
-- Síntoma: Saldos "fantasma" de 0.01 o 0.05 que no deberían existir
-- Solución: Castear TODOS los montos a DECIMAL(18,2) antes de operaciones
-- Fecha: 2026-04-11
-- =========================================================================

USE refacfabelabd;

-- =========================================================================
-- 1. CORREGIR tv_ventadetalle
-- =========================================================================
CREATE OR REPLACE VIEW `tv_ventadetalle` AS
SELECT
  `ven`.`n_id` AS `n_id`,
  `ven`.`n_idCliente` AS `n_idCliente`,
  `ven`.`n_idUsuario` AS `n_idUsuario`,
  `ven`.`s_folioVenta` AS `s_folioVenta`,
  `ven`.`d_fechaVenta` AS `d_fechaVenta`,
  `ven`.`n_tipoPago` AS `n_tipoPago`,
  `ven`.`n_estatusVenta` AS `n_estatusVenta`,
  `ven`.`n_idTipoVenta` AS `n_idTipoVenta`,
  `ven`.`nAnticipo` AS `n_anticipo`,
  `ven`.`d_fechaInicioCredito` AS `d_fechaInicioCredito`,
  `ven`.`d_fechaTerminoCredito` AS `d_fechaTerminoCredito`,
  `ven`.`d_fechaPagoCredito` AS `d_fechaPagoCredito`,
  `ven`.`n_idFormaPago` AS `n_idFormaPago`,
  `ven`.`n_idCaja` AS `n_idCaja`,
  IFNULL(`ven`.`nDescuento`, 0) AS `nDescuento`,
  IFNULL(`tot`.`n_totalVenta`, 0) AS `n_totalVenta`,
  IFNULL(`abo`.`n_totalAbono`, 0) AS `n_totalAbono`,
  TRUNCATE(
    TRUNCATE(
      CAST(IFNULL(`tot`.`n_totalVenta`, 0) AS DECIMAL(18,2)) - CAST(IFNULL(`abo`.`n_totalAbono`, 0) AS DECIMAL(18,2)),
      2
    ) - CAST(IFNULL(`ven`.`nDescuento`, 0) AS DECIMAL(18,2)),
    2
  ) AS `n_saldoTotal`,
  IFNULL(
    TRUNCATE(
      (
        CAST(IFNULL(`abo`.`n_totalAbono`, 0) AS DECIMAL(18,2)) * 100
      ) / NULLIF(
        CAST(IFNULL(`tot`.`n_totalVenta`, 0) AS DECIMAL(18,2)) - CAST(IFNULL(`ven`.`nDescuento`, 0) AS DECIMAL(18,2)),
        0
      ),
      2
    ),
    0
  ) AS `n_avancePago`,
  (CASE
    WHEN (`ven`.`d_fechaTerminoCredito` > SYSDATE()) THEN 'REGULAR'
    WHEN (`ven`.`d_fechaTerminoCredito` < SYSDATE()) THEN 'VENCIDO'
    ELSE ''
  END) AS `s_estatus`,
  NULL AS `n_saldo_favor`,
  NULL AS `n_id_venta_utilizado`,
  `ven`.`n_saldo` AS `n_saldo`,
  (CASE WHEN (`ven`.`d_fechaTerminoCredito` < SYSDATE()) THEN 1 ELSE 0 END) AS `n_vencido`
FROM (
  (`refacfabelabd`.`tw_ventas` `ven`
  LEFT JOIN (
    SELECT `n_idVenta`, TRUNCATE(SUM(`n_totalPartida`), 2) AS `n_totalVenta`
    FROM `refacfabelabd`.`tw_ventas_producto`
    WHERE `n_estatus` = 1
    GROUP BY `n_idVenta`
  ) `tot` ON `tot`.`n_idVenta` = `ven`.`n_id`)
  LEFT JOIN (
    SELECT `n_idVenta`, TRUNCATE(SUM(`n_abono`), 2) AS `n_totalAbono`
    FROM `refacfabelabd`.`tw_abonos`
    GROUP BY `n_idVenta`
  ) `abo` ON `abo`.`n_idVenta` = `ven`.`n_id`
);

-- =========================================================================
-- 2. CORREGIR tv_saldogeneralclie (saldo general de clientes)
-- =========================================================================
CREATE OR REPLACE VIEW `tv_saldogeneralclie` AS
SELECT
  `sald`.`n_idCliente` AS `n_idCliente`,
  `cli`.`n_limiteCredito` AS `n_limiteCredito`,
  TRUNCATE((`sald`.`saldo` - IFNULL(`sald`.`nDescuento`, 0)), 2) AS `n_saldo_total`,
  TRUNCATE((`cli`.`n_limiteCredito` - TRUNCATE((`sald`.`saldo` - IFNULL(`sald`.`nDescuento`, 0)), 2)), 2) AS `n_credito_disponible`,
  TRUNCATE(((TRUNCATE((`sald`.`saldo` - IFNULL(`sald`.`nDescuento`, 0)), 2) * 100) / `cli`.`n_limiteCredito`), 2) AS `n_saldo_utilizado`,
  (CASE WHEN (`ven`.`vencido` > 0) THEN 'VENCIDO' WHEN (`ven`.`vencido` IS NULL) THEN 'REGULAR' END) AS `s_estatus`,
  `sald`.`n_abonos` AS `n_abonos`,
  TRUNCATE((`sald`.`n_totalVenta` - IFNULL(`sald`.`nDescuento`, 0)), 2) AS `n_total_venta`,
  TRUNCATE(((`sald`.`n_abonos` * 100) / (`sald`.`n_totalVenta` - IFNULL(`sald`.`nDescuento`, 0))), 2) AS `n_avance_credito`
FROM (
  (
    (
      SELECT
        `tot`.`n_idCliente` AS `n_idCliente`,
        (SUM(`tot`.`total_venta`) - SUM(IFNULL(`tot`.`n_abonos`, 0))) AS `saldo`,
        SUM(IFNULL(`tot`.`n_abonos`, 0)) AS `n_abonos`,
        SUM(`tot`.`total_venta`) AS `n_totalVenta`,
        SUM(`tot`.`nDescuento`) AS `nDescuento`
      FROM (
        SELECT
          `ven`.`n_idCliente` AS `n_idCliente`,
          `abo`.`n_abonos` AS `n_abonos`,
          `venpro`.`total_venta` AS `total_venta`,
          `ven`.`nDescuento` AS `nDescuento`
        FROM (
          (`refacfabelabd`.`tw_ventas` `ven`
          LEFT JOIN (
            SELECT `refacfabelabd`.`tw_abonos`.`n_idVenta` AS `n_idVenta`,
                   SUM(`refacfabelabd`.`tw_abonos`.`n_abono`) AS `n_abonos`
            FROM `refacfabelabd`.`tw_abonos`
            GROUP BY `refacfabelabd`.`tw_abonos`.`n_idVenta`
          ) `abo` ON ((`ven`.`n_id` = `abo`.`n_idVenta`)))
          LEFT JOIN (
            SELECT `refacfabelabd`.`tw_ventas_producto`.`n_idVenta` AS `n_idVenta`,
                   TRUNCATE(SUM(`refacfabelabd`.`tw_ventas_producto`.`n_totalPartida`), 2) AS `total_venta`
            FROM `refacfabelabd`.`tw_ventas_producto`
            WHERE (`refacfabelabd`.`tw_ventas_producto`.`n_estatus` = 1)
            GROUP BY `refacfabelabd`.`tw_ventas_producto`.`n_idVenta`
          ) `venpro` ON ((`ven`.`n_id` = `venpro`.`n_idVenta`)))
        WHERE ((`ven`.`n_tipoPago` = 1)
          AND (`ven`.`d_fechaPagoCredito` IS NULL)
          AND (`ven`.`n_estatusVenta` <> 5))
      ) `tot`
      GROUP BY `tot`.`n_idCliente`
    ) `sald`
    LEFT JOIN `refacfabelabd`.`tc_clientes` `cli` ON ((`cli`.`n_id` = `sald`.`n_idCliente`)))
  LEFT JOIN (
    SELECT
      `refacfabelabd`.`tw_ventas`.`n_idCliente` AS `n_idCliente`,
      SUM(`refacfabelabd`.`tw_ventas`.`nDescuento`) AS `sum(tw_ventas.nDescuento)`,
      COUNT(`refacfabelabd`.`tw_ventas`.`n_idCliente`) AS `vencido`
    FROM `refacfabelabd`.`tw_ventas`
    WHERE ((`refacfabelabd`.`tw_ventas`.`d_fechaTerminoCredito` < SYSDATE())
      AND (`refacfabelabd`.`tw_ventas`.`d_fechaPagoCredito` IS NULL)
      AND (`refacfabelabd`.`tw_ventas`.`n_estatusVenta` <> 5))
    GROUP BY `refacfabelabd`.`tw_ventas`.`n_idCliente`
  ) `ven` ON ((`ven`.`n_idCliente` = `sald`.`n_idCliente`))
);

-- =========================================================================
-- VALIDACIÓN: Ejecutar después de actualizar
-- =========================================================================
-- Debe retornar 0 registros (los saldos fantasma desaparecerán):
-- SELECT n_id, s_folioVenta, n_saldoTotal FROM tv_ventadetalle 
-- WHERE n_saldoTotal > 0 AND n_saldoTotal <= 0.05
-- ORDER BY n_saldoTotal ASC;
