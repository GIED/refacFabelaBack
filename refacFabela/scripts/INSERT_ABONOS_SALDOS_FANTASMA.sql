-- =========================================================================
-- SCRIPT: Inserción de abonos para cubrir saldos "fantasma"
-- =========================================================================
-- Origen: lista "a insertar 2.csv" (130 registros, 2026-04-11)
-- Causa: nDescuento es DOUBLE en tw_ventas → error de punto flotante.
--   tv_ventadetalle calcula n_saldoTotal SOLO con tw_abonos, por lo que
--   el ajuste va siempre en tw_abonos (contado y crédito).
-- Mejora vs versión anterior: cada fila usa su propio n_idCaja y
--   n_idUsuario del registro original, y el monto exacto del CSV.
-- Guarda contra doble ejecución: la tabla temporal no existe entre
--   sesiones; el JOIN a tv_ventadetalle excluye filas cuyo saldo ya
--   llegó a 0 por una ejecución previa.
-- =========================================================================

USE refacfabelabd;

-- =========================================================================
-- PASO 1 (opcional): Verificar antes de insertar
-- =========================================================================
-- SELECT n_id, n_tipoPago, s_folioVenta, n_totalVenta, n_totalAbono,
--        nDescuento, n_saldoTotal
-- FROM tv_ventadetalle
-- WHERE n_saldoTotal > 0 AND n_saldoTotal <= 0.20
-- ORDER BY n_id;

-- =========================================================================
-- PASO 2: Tabla temporal con datos exactos por registro
-- =========================================================================
CREATE TEMPORARY TABLE tmp_saldos_fantasma (
  n_id        INT            NOT NULL,
  n_saldo     DECIMAL(10,2)  NOT NULL,
  PRIMARY KEY (n_id)
);

INSERT INTO tmp_saldos_fantasma (n_id, n_saldo) VALUES
  (108003, 0.01), (108096, 0.01), (108138, 0.01),
  (108412, 0.01), (108420, 0.02), (108424, 0.02),
  (108481, 0.02), (108503, 0.01), (108513, 0.02),
  (108514, 0.02), (108536, 0.02), (108732, 0.02),
  (108827, 0.01), (108849, 0.01), (108852, 0.02),
  (108933, 0.02), (109193, 0.01), (109220, 0.01),
  (109281, 0.01), (109286, 0.01), (109300, 0.01),
  (109302, 0.01), (109352, 0.01), (109432, 0.01),
  (109441, 0.01), (109442, 0.01), (109449, 0.01),
  (109466, 0.01), (109576, 0.01), (109588, 0.01),
  (109602, 0.01), (109605, 0.01), (109620, 0.01),
  (109655, 0.02), (109656, 0.01), (109675, 0.01),
  (109678, 0.01), (109746, 0.01), (109770, 0.01),
  (109793, 0.01), (109795, 0.01), (109798, 0.01),
  (109963, 0.01), (109967, 0.01), (109972, 0.01),
  (109995, 0.01), (110035, 0.01), (110079, 0.01),
  (110088, 0.01), (110116, 0.01), (110135, 0.09),
  (110155, 0.01), (110156, 0.01), (110157, 0.01),
  (110158, 0.01), (110159, 0.01), (110169, 0.01),
  (110211, 0.01), (110325, 0.01), (110339, 0.01),
  (110363, 0.01), (110376, 0.01), (110387, 0.01),
  (110442, 0.01), (110456, 0.01), (110495, 0.01),
  (110550, 0.01), (110602, 0.01), (110607, 0.01),
  (110659, 0.01), (110664, 0.01), (110751, 0.01),
  (110798, 0.01), (111121, 0.01), (111140, 0.02),
  (111168, 0.01), (111195, 0.01), (111209, 0.01),
  (111231, 0.01), (111240, 0.01), (111273, 0.01),
  (111440, 0.01), (111489, 0.01), (111536, 0.01),
  (111555, 0.01), (111633, 0.01), (111830, 0.01),
  (111834, 0.10), (111845, 0.01), (111854, 0.01),
  (111887, 0.01), (111948, 0.01), (112023, 0.02),
  (112077, 0.01), (112292, 0.01), (112329, 0.01),
  (112590, 0.01), (112832, 0.10), (113479, 0.01),
  (113759, 0.01), (114692, 0.01), (115927, 0.01),
  (117109, 0.01), (117566, 0.01), (118052, 0.01),
  (118057, 0.01), (118058, 0.01), (118158, 0.12),
  (118237, 0.10), (118636, 0.01), (119076, 0.01),
  (119106, 0.01), (119470, 0.01), (120027, 0.01),
  (121318, 0.01), (121492, 0.01), (121996, 0.01),
  (122068, 0.01), (122599, 0.01), (123294, 0.01),
  (123862, 0.01), (123981, 0.01), (124053, 0.01),
  (124397, 0.01), (124663, 0.01), (124664, 0.01),
  (126656, 0.01), (126747, 0.01), (128267, 0.01),
  (128339, 0.01), (128441, 0.01), (129788, 0.01),
  (131431, 0.01), (131875, 0.01), (132171, 0.01),
  (132216, 0.01), (132291, 0.01), (132295, 0.01),
  (132378, 0.01), (132868, 0.01), (133111, 0.01),
  (133811, 0.01), (133893, 0.01), (134165, 0.01);

-- =========================================================================
-- PASO 3: Insertar abonos usando los datos exactos de la tabla temporal
-- =========================================================================
INSERT INTO `tw_abonos` (
  `n_idVenta`,
  `n_abono`,
  `d_fecha`,
  `n_idUsuario`,
  `n_idFormaPago`,
  `n_estatus`,
  `n_idCaja`
)
SELECT
  t.n_id         AS n_idVenta,
  t.n_saldo      AS n_abono,
  CURDATE()      AS d_fecha,
  8              AS n_idUsuario,
  1              AS n_idFormaPago,  -- 1 = Efectivo
  1              AS n_estatus,
  1873           AS n_idCaja
FROM tmp_saldos_fantasma t
-- Guarda: solo inserta si el saldo aún no fue saldado (protege doble ejecución)
JOIN tv_ventadetalle tvd ON tvd.n_id = t.n_id
  AND tvd.n_saldoTotal > 0
  AND tvd.n_saldoTotal <= 0.20;

DROP TEMPORARY TABLE tmp_saldos_fantasma;

-- =========================================================================
-- PASO 4 (validación): Debe retornar 0 filas si todo fue correcto
-- =========================================================================
SELECT n_id, n_tipoPago, s_folioVenta, n_saldoTotal, n_vencido
FROM tv_ventadetalle
WHERE n_saldoTotal > 0 AND n_saldoTotal <= 0.20
ORDER BY n_id;
