SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_abonos')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_id')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_abonos' AND COLUMN_NAME = 'n_id_pago_cliente_canonico'),
        CONCAT(
            'ALTER TABLE tw_abonos ADD COLUMN n_id_pago_cliente_canonico ',
            (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_id'),
            ' NULL AFTER n_idVenta'
        ),
        'SELECT ''skip add column tw_abonos.n_id_pago_cliente_canonico'''
    )
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_abonos' AND COLUMN_NAME = 'n_id_pago_cliente_canonico')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_abonos' AND INDEX_NAME = 'idx_tw_abonos_pago_cliente_canonico'),
        'ALTER TABLE tw_abonos ADD INDEX idx_tw_abonos_pago_cliente_canonico (n_id_pago_cliente_canonico)',
        'SELECT ''skip index idx_tw_abonos_pago_cliente_canonico'''
    )
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_abonos' AND COLUMN_NAME = 'n_id_pago_cliente_canonico')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_id')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_abonos' AND COLUMN_NAME = 'n_id_pago_cliente_canonico') =
            (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_id')
        AND NOT EXISTS(
            SELECT 1
            FROM tw_abonos a
            LEFT JOIN tw_pago_cliente p ON p.n_id = a.n_id_pago_cliente_canonico
            WHERE a.n_id_pago_cliente_canonico IS NOT NULL
              AND p.n_id IS NULL
        )
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_abonos' AND CONSTRAINT_NAME = 'fk_tw_abonos_pago_cliente_canonico'),
        'ALTER TABLE tw_abonos ADD CONSTRAINT fk_tw_abonos_pago_cliente_canonico FOREIGN KEY (n_id_pago_cliente_canonico) REFERENCES tw_pago_cliente (n_id)',
        'SELECT ''skip fk_tw_abonos_pago_cliente_canonico'''
    )
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;