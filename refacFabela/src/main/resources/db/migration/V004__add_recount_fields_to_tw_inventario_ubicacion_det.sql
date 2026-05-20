-- V004__add_recount_fields_to_tw_inventario_ubicacion_det.sql
-- Agregar campos para soporte de re-conteo y validación de stock obsoleto

ALTER TABLE tw_inventario_ubicacion_det ADD COLUMN n_diferencia INT DEFAULT 0 AFTER n_cantidad_contada;

ALTER TABLE tw_inventario_ubicacion_det ADD COLUMN b_requiere_reconteo TINYINT DEFAULT 0 AFTER n_id_usuario_ajuste;

ALTER TABLE tw_inventario_ubicacion_det ADD COLUMN n_id_usuario_reconteo INT DEFAULT NULL AFTER b_requiere_reconteo;

ALTER TABLE tw_inventario_ubicacion_det ADD COLUMN d_fecha_reconteo DATETIME DEFAULT NULL AFTER n_id_usuario_reconteo;

ALTER TABLE tw_inventario_ubicacion_det ADD COLUMN s_motivo_reconteo VARCHAR(500) DEFAULT NULL AFTER d_fecha_reconteo;

-- Descripción:
-- n_diferencia: Almacena la diferencia entre cantidad contada y referencia (para queries más rápidas)
-- b_requiere_reconteo: Flag que indica si el stock cambió desde el levantamiento inicial
-- n_id_usuario_reconteo: ID del usuario que hizo el re-conteo
-- d_fecha_reconteo: Timestamp cuando se realizó el re-conteo
-- s_motivo_reconteo: Razón por la cual se hizo el re-conteo (ej: "Stock cambió en tw_productobodega")
