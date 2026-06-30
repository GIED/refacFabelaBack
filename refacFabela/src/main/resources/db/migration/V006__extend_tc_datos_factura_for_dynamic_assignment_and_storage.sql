-- V006__extend_tc_datos_factura_for_dynamic_assignment_and_storage.sql
-- Agrega control explicito de asignacion y rutas dinamicas por razon social.

ALTER TABLE tc_datos_factura
    ADD COLUMN n_asignable TINYINT(1) NOT NULL DEFAULT 1 AFTER n_predeterminado,
    ADD COLUMN s_ruta_raiz VARCHAR(1000) DEFAULT NULL AFTER n_asignable,
    ADD COLUMN s_ruta_pdf VARCHAR(1000) DEFAULT NULL AFTER s_ruta_raiz,
    ADD COLUMN s_ruta_xml VARCHAR(1000) DEFAULT NULL AFTER s_ruta_pdf;

UPDATE tc_datos_factura
SET n_asignable = 0
WHERE LOWER(TRIM(COALESCE(s_rfc_emisor, ''))) = 'eku9003173c9'
   OR UPPER(TRIM(COALESCE(s_nombre_emisor, ''))) = 'ESCUELA KEMPER URGATE';