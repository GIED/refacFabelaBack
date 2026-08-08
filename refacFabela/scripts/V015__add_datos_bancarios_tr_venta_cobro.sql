-- Agrega datos minimos de identificacion bancaria a tr_venta_cobro.
-- Aplican solo para formas de pago electronicas (tarjeta credito/debito, transferencia);
-- efectivo y demas formas de pago quedan sin estos datos (columnas NULL).
-- El banco se normaliza via FK a tc_cuentas_bancarias en lugar de texto libre.

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro' AND COLUMN_NAME = 's_referencia'),
        'ALTER TABLE tr_venta_cobro ADD COLUMN s_referencia VARCHAR(255) NULL AFTER n_id_forma_pago',
        'SELECT ''skip add column tr_venta_cobro.s_referencia'''
    )
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro' AND COLUMN_NAME = 's_numero_autorizacion'),
        'ALTER TABLE tr_venta_cobro ADD COLUMN s_numero_autorizacion VARCHAR(255) NULL AFTER s_referencia',
        'SELECT ''skip add column tr_venta_cobro.s_numero_autorizacion'''
    )
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro' AND COLUMN_NAME = 's_ultimos4_tarjeta'),
        'ALTER TABLE tr_venta_cobro ADD COLUMN s_ultimos4_tarjeta VARCHAR(10) NULL AFTER s_numero_autorizacion',
        'SELECT ''skip add column tr_venta_cobro.s_ultimos4_tarjeta'''
    )
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_cuentas_bancarias' AND COLUMN_NAME = 'n_id')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro' AND COLUMN_NAME = 'n_id_cuenta_bancaria'),
        CONCAT(
            'ALTER TABLE tr_venta_cobro ADD COLUMN n_id_cuenta_bancaria ',
            (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_cuentas_bancarias' AND COLUMN_NAME = 'n_id'),
            ' NULL AFTER s_ultimos4_tarjeta'
        ),
        'SELECT ''skip add column tr_venta_cobro.n_id_cuenta_bancaria'''
    )
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro' AND COLUMN_NAME = 'n_id_cuenta_bancaria')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro' AND INDEX_NAME = 'idx_tr_venta_cobro_cuenta_bancaria'),
        'ALTER TABLE tr_venta_cobro ADD INDEX idx_tr_venta_cobro_cuenta_bancaria (n_id_cuenta_bancaria)',
        'SELECT ''skip index idx_tr_venta_cobro_cuenta_bancaria'''
    )
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro' AND COLUMN_NAME = 'n_id_cuenta_bancaria')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_cuentas_bancarias')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro' AND COLUMN_NAME = 'n_id_cuenta_bancaria') =
            (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_cuentas_bancarias' AND COLUMN_NAME = 'n_id')
        AND NOT EXISTS(
            SELECT 1
            FROM tr_venta_cobro c
            LEFT JOIN tc_cuentas_bancarias b ON b.n_id = c.n_id_cuenta_bancaria
            WHERE c.n_id_cuenta_bancaria IS NOT NULL AND b.n_id IS NULL
        )
        AND NOT EXISTS(
            SELECT 1 FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
            WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro' AND CONSTRAINT_NAME = 'fk_tr_venta_cobro_cuenta_bancaria'
        ),
        'ALTER TABLE tr_venta_cobro ADD CONSTRAINT fk_tr_venta_cobro_cuenta_bancaria FOREIGN KEY (n_id_cuenta_bancaria) REFERENCES tc_cuentas_bancarias (n_id)',
        'SELECT ''skip fk fk_tr_venta_cobro_cuenta_bancaria'''
    )
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
