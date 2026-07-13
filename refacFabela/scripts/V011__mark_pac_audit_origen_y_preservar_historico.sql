/*
  AMBIENTE DE PRUEBAS
  Objetivo:
  1) NO conservar histórico legacy JSON.
  2) Marcar en facturación que el detalle histórico fue depurado.
  3) Dejar listas las tablas para operar solo con modelo relacional indexado.
*/

/* Evita error 1175 en clientes con safe updates activo (ej. Workbench) */
SET @OLD_SQL_SAFE_UPDATES := @@SQL_SAFE_UPDATES;
SET SQL_SAFE_UPDATES = 0;

/* 1) Columna de origen de traza para operación nueva */
SET @sql := (
    SELECT IF(
        EXISTS(
            SELECT 1
            FROM INFORMATION_SCHEMA.COLUMNS
            WHERE TABLE_SCHEMA = DATABASE()
              AND TABLE_NAME = 'tw_facturacion_pac_audit'
              AND COLUMN_NAME = 's_origen_traza'
        ),
        'SELECT ''skip add column s_origen_traza''',
        'ALTER TABLE tw_facturacion_pac_audit ADD COLUMN s_origen_traza VARCHAR(30) NULL AFTER s_usuario'
    )
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql := (
    SELECT IF(
        EXISTS(
            SELECT 1
            FROM INFORMATION_SCHEMA.STATISTICS
            WHERE TABLE_SCHEMA = DATABASE()
              AND TABLE_NAME = 'tw_facturacion_pac_audit'
              AND INDEX_NAME = 'idx_tw_facturacion_pac_audit_origen_traza'
        ),
        'SELECT ''skip index idx_tw_facturacion_pac_audit_origen_traza''',
        'ALTER TABLE tw_facturacion_pac_audit ADD INDEX idx_tw_facturacion_pac_audit_origen_traza (s_origen_traza)'
    )
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

/*
  2) Marcar facturas previas: ya no tendrán detalle histórico
  (esto evita confusión de datos incompletos de proceso anterior)
*/
SET @sql := (
    SELECT IF(
        EXISTS(
            SELECT 1
            FROM INFORMATION_SCHEMA.TABLES
            WHERE TABLE_SCHEMA = DATABASE()
              AND TABLE_NAME = 'tw_facturacion'
        ),
        'UPDATE tw_facturacion '
        'SET s_uuid_complemento_pago = NULL, '
        '    s_estado_complemento = ''SIN_DETALLE_HISTORICO_PRUEBAS'', '
        '    s_error_complemento = ''HISTORICO_DEPURADO_EN_PRUEBAS'' '
        'WHERE n_id > 0 '
        '  AND (s_estado_complemento IS NOT NULL '
        '   OR s_uuid_complemento_pago IS NOT NULL '
        '   OR s_error_complemento IS NOT NULL)',
        'SELECT ''skip update tw_facturacion marker'''
    )
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

/* 3) Purga total de datos legacy/auditoría/complementos en pruebas */
SET FOREIGN_KEY_CHECKS = 0;

/* Purga detalle indexado */
SET @sql := (
    SELECT IF(
        EXISTS(
            SELECT 1
            FROM INFORMATION_SCHEMA.TABLES
            WHERE TABLE_SCHEMA = DATABASE()
              AND TABLE_NAME = 'tw_facturacion_pac_audit_detalle'
        ),
        'DELETE FROM tw_facturacion_pac_audit_detalle WHERE n_id > 0',
        'SELECT ''skip purge tw_facturacion_pac_audit_detalle'''
    )
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

/* Purga auditoría maestra */
SET @sql := (
    SELECT IF(
        EXISTS(
            SELECT 1
            FROM INFORMATION_SCHEMA.TABLES
            WHERE TABLE_SCHEMA = DATABASE()
              AND TABLE_NAME = 'tw_facturacion_pac_audit'
        ),
        'DELETE FROM tw_facturacion_pac_audit WHERE n_id > 0',
        'SELECT ''skip purge tw_facturacion_pac_audit'''
    )
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

/* Purga complementos de pago */
SET @sql := (
    SELECT IF(
        EXISTS(
            SELECT 1
            FROM INFORMATION_SCHEMA.TABLES
            WHERE TABLE_SCHEMA = DATABASE()
              AND TABLE_NAME = 'tw_facturacion_complemento_pago'
        ),
        'DELETE FROM tw_facturacion_complemento_pago WHERE n_id > 0',
        'SELECT ''skip purge tw_facturacion_complemento_pago'''
    )
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

/* Si existía tabla legacy de respaldo, también se limpia */
SET @sql := (
    SELECT IF(
        EXISTS(
            SELECT 1
            FROM INFORMATION_SCHEMA.TABLES
            WHERE TABLE_SCHEMA = DATABASE()
              AND TABLE_NAME = 'tw_facturacion_pac_audit_legacy'
        ),
        'DELETE FROM tw_facturacion_pac_audit_legacy WHERE n_id > 0',
        'SELECT ''skip purge tw_facturacion_pac_audit_legacy'''
    )
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET FOREIGN_KEY_CHECKS = 1;
SET SQL_SAFE_UPDATES = @OLD_SQL_SAFE_UPDATES;
