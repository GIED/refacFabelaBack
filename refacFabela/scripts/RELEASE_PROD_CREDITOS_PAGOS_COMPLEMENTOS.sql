-- =============================================
-- RELEASE PRODUCCION: CREDITOS + PAGOS + COMPLEMENTOS
-- Proyecto: refacFabela
-- Motor objetivo: MySQL 5.7/8.0
-- Tipo: Script unico, idempotente (safe re-run)
-- =============================================

-- IMPORTANTE:
-- 1) Este script NO incluye purgas de datos (scripts de pruebas V011/V012).
-- 2) Ejecutar con una cuenta con permisos DDL/DML/FK.
-- 3) Respaldar la BD antes de ejecutar.

SET @OLD_FOREIGN_KEY_CHECKS := @@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_SAFE_UPDATES := @@SQL_SAFE_UPDATES;
SET SQL_SAFE_UPDATES = 0;

-- -------------------------------------------------
-- A) tw_facturacion: columnas de clasificacion/complemento
-- -------------------------------------------------
SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion' AND COLUMN_NAME = 's_clasificacion_fiscal'),
        'ALTER TABLE tw_facturacion ADD COLUMN s_clasificacion_fiscal TEXT NULL AFTER s_uuid',
        'SELECT ''skip tw_facturacion.s_clasificacion_fiscal'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion' AND COLUMN_NAME = 's_metodo_pago_fiscal'),
        'ALTER TABLE tw_facturacion ADD COLUMN s_metodo_pago_fiscal TEXT NULL AFTER s_clasificacion_fiscal',
        'SELECT ''skip tw_facturacion.s_metodo_pago_fiscal'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion' AND COLUMN_NAME = 's_forma_pago_fiscal'),
        'ALTER TABLE tw_facturacion ADD COLUMN s_forma_pago_fiscal TEXT NULL AFTER s_metodo_pago_fiscal',
        'SELECT ''skip tw_facturacion.s_forma_pago_fiscal'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion' AND COLUMN_NAME = 's_uuid_complemento_pago'),
        'ALTER TABLE tw_facturacion ADD COLUMN s_uuid_complemento_pago TEXT NULL AFTER s_forma_pago_fiscal',
        'SELECT ''skip tw_facturacion.s_uuid_complemento_pago'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion' AND COLUMN_NAME = 's_estado_complemento'),
        'ALTER TABLE tw_facturacion ADD COLUMN s_estado_complemento TEXT NULL AFTER s_uuid_complemento_pago',
        'SELECT ''skip tw_facturacion.s_estado_complemento'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion' AND COLUMN_NAME = 's_error_complemento'),
        'ALTER TABLE tw_facturacion ADD COLUMN s_error_complemento TEXT NULL AFTER s_estado_complemento',
        'SELECT ''skip tw_facturacion.s_error_complemento'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- -------------------------------------------------
-- B) Tabla tw_facturacion_complemento_pago
-- -------------------------------------------------
CREATE TABLE IF NOT EXISTS tw_facturacion_complemento_pago (
    n_id BIGINT NOT NULL AUTO_INCREMENT,
    n_id_venta BIGINT NOT NULL,
    n_id_facturacion BIGINT NOT NULL,
    s_uuid_factura_ingreso VARCHAR(2000) NULL,
    s_uuid_complemento_pago VARCHAR(2000) NULL,
    s_origen_pago VARCHAR(40) NOT NULL,
    n_id_pago_origen BIGINT NOT NULL,
    n_parcialidad INT NOT NULL,
    s_forma_pago_sat VARCHAR(10) NULL,
    s_descripcion_forma_pago VARCHAR(255) NULL,
    n_monto_pagado DECIMAL(18,2) NULL,
    n_saldo_anterior DECIMAL(18,2) NULL,
    n_saldo_insoluto DECIMAL(18,2) NULL,
    d_fecha_pago DATETIME NULL,
    s_proveedor VARCHAR(60) NULL,
    s_estado VARCHAR(120) NULL,
    n_estatus INT NULL,
    s_xml_timbrado LONGTEXT NULL,
    s_codigo_error VARCHAR(120) NULL,
    s_error_pac VARCHAR(2000) NULL,
    s_correlation_id VARCHAR(120) NULL,
    d_fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (n_id),
    KEY idx_tw_facturacion_complemento_pago_venta (n_id_venta),
    KEY idx_tw_facturacion_complemento_pago_factura (n_id_facturacion),
    KEY idx_tw_facturacion_complemento_pago_uuid_ingreso (s_uuid_factura_ingreso(255)),
    KEY idx_tw_facturacion_complemento_pago_uuid_rep (s_uuid_complemento_pago(255)),
    KEY idx_tw_facturacion_complemento_pago_origen (n_id_venta, s_origen_pago, n_id_pago_origen)
);

-- -------------------------------------------------
-- C) Tabla tw_pago_cliente
-- -------------------------------------------------
CREATE TABLE IF NOT EXISTS tw_pago_cliente (
    n_id BIGINT NOT NULL AUTO_INCREMENT,
    n_id_cliente BIGINT NOT NULL,
    n_id_dato_factura BIGINT NOT NULL,
    d_fecha_registro DATETIME NOT NULL,
    d_fecha_pago DATETIME NOT NULL,
    n_importe_total DECIMAL(18,2) NOT NULL,
    n_importe_aplicado DECIMAL(18,2) NOT NULL DEFAULT 0.00,
    n_importe_disponible DECIMAL(18,2) NOT NULL DEFAULT 0.00,
    s_moneda VARCHAR(10) NOT NULL DEFAULT 'MXN',
    n_id_forma_pago BIGINT NOT NULL,
    s_forma_pago_sat VARCHAR(10) NULL,
    s_descripcion_forma_pago VARCHAR(120) NULL,
    s_referencia VARCHAR(255) NULL,
    s_numero_autorizacion VARCHAR(255) NULL,
    s_folio_operacion VARCHAR(255) NULL,
    s_clave_rastreo VARCHAR(255) NULL,
    s_banco_origen VARCHAR(255) NULL,
    s_cuenta_origen VARCHAR(255) NULL,
    s_ultimos4_cuenta_origen VARCHAR(10) NULL,
    s_banco_destino VARCHAR(255) NULL,
    s_cuenta_destino VARCHAR(255) NULL,
    s_ultimos4_cuenta_destino VARCHAR(10) NULL,
    s_titular_cuenta VARCHAR(255) NULL,
    s_terminal VARCHAR(255) NULL,
    s_numero_voucher VARCHAR(255) NULL,
    s_ultimos4_tarjeta VARCHAR(10) NULL,
    s_tipo_tarjeta VARCHAR(100) NULL,
    s_red_tarjeta VARCHAR(100) NULL,
    s_comprobante_url VARCHAR(500) NULL,
    s_observaciones TEXT NULL,
    n_id_usuario_registro BIGINT NOT NULL,
    n_id_caja BIGINT NULL,
    n_id_corte_caja BIGINT NULL,
    s_estatus VARCHAR(60) NOT NULL,
    d_fecha_conciliacion DATETIME NULL,
    n_conciliado TINYINT(1) NOT NULL DEFAULT 0,
    n_estatus INT NOT NULL DEFAULT 1,
    PRIMARY KEY (n_id),
    KEY idx_tw_pago_cliente_cliente (n_id_cliente),
    KEY idx_tw_pago_cliente_dato_factura (n_id_dato_factura),
    KEY idx_tw_pago_cliente_forma_pago (n_id_forma_pago),
    KEY idx_tw_pago_cliente_usuario (n_id_usuario_registro),
    KEY idx_tw_pago_cliente_caja (n_id_caja),
    KEY idx_tw_pago_cliente_estatus (s_estatus)
);

-- -------------------------------------------------
-- D) Tabla tw_pago_aplicacion
-- -------------------------------------------------
CREATE TABLE IF NOT EXISTS tw_pago_aplicacion (
    n_id BIGINT NOT NULL AUTO_INCREMENT,
    n_id_pago_cliente BIGINT NOT NULL,
    n_id_cliente BIGINT NOT NULL,
    n_id_venta BIGINT NOT NULL,
    n_id_facturacion BIGINT NULL,
    n_id_dato_factura BIGINT NOT NULL,
    n_monto_aplicado DECIMAL(18,2) NOT NULL,
    n_saldo_anterior DECIMAL(18,2) NOT NULL,
    n_saldo_insoluto DECIMAL(18,2) NOT NULL,
    n_parcialidad INT NULL,
    s_estatus VARCHAR(60) NOT NULL,
    d_fecha_aplicacion DATETIME NOT NULL,
    n_id_usuario BIGINT NOT NULL,
    n_orden_aplicacion INT NOT NULL DEFAULT 1,
    s_origen_registro VARCHAR(60) NOT NULL,
    n_estatus INT NOT NULL DEFAULT 1,
    PRIMARY KEY (n_id),
    KEY idx_tw_pago_aplicacion_pago (n_id_pago_cliente),
    KEY idx_tw_pago_aplicacion_cliente (n_id_cliente),
    KEY idx_tw_pago_aplicacion_venta (n_id_venta),
    KEY idx_tw_pago_aplicacion_facturacion (n_id_facturacion),
    KEY idx_tw_pago_aplicacion_estatus (s_estatus)
);

-- -------------------------------------------------
-- E) FKs condicionales para tw_pago_cliente / tw_pago_aplicacion
-- -------------------------------------------------
SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_id_cliente')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_clientes' AND COLUMN_NAME = 'n_id')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_id_cliente') =
            (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_clientes' AND COLUMN_NAME = 'n_id')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND CONSTRAINT_NAME = 'fk_tw_pago_cliente_cliente'),
        'ALTER TABLE tw_pago_cliente ADD CONSTRAINT fk_tw_pago_cliente_cliente FOREIGN KEY (n_id_cliente) REFERENCES tc_clientes (n_id)',
        'SELECT ''skip fk_tw_pago_cliente_cliente'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_id_dato_factura')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura' AND COLUMN_NAME = 'n_id')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_id_dato_factura') =
            (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura' AND COLUMN_NAME = 'n_id')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND CONSTRAINT_NAME = 'fk_tw_pago_cliente_dato_factura'),
        'ALTER TABLE tw_pago_cliente ADD CONSTRAINT fk_tw_pago_cliente_dato_factura FOREIGN KEY (n_id_dato_factura) REFERENCES tc_datos_factura (n_id)',
        'SELECT ''skip fk_tw_pago_cliente_dato_factura'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_id_forma_pago')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_formapago' AND COLUMN_NAME = 'n_id')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_id_forma_pago') =
            (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_formapago' AND COLUMN_NAME = 'n_id')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND CONSTRAINT_NAME = 'fk_tw_pago_cliente_forma_pago'),
        'ALTER TABLE tw_pago_cliente ADD CONSTRAINT fk_tw_pago_cliente_forma_pago FOREIGN KEY (n_id_forma_pago) REFERENCES tc_formapago (n_id)',
        'SELECT ''skip fk_tw_pago_cliente_forma_pago'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_id_usuario_registro')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_usuarios' AND COLUMN_NAME = 'n_id')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_id_usuario_registro') =
            (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_usuarios' AND COLUMN_NAME = 'n_id')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND CONSTRAINT_NAME = 'fk_tw_pago_cliente_usuario'),
        'ALTER TABLE tw_pago_cliente ADD CONSTRAINT fk_tw_pago_cliente_usuario FOREIGN KEY (n_id_usuario_registro) REFERENCES tc_usuarios (n_id)',
        'SELECT ''skip fk_tw_pago_cliente_usuario'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_id_caja')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_caja' AND COLUMN_NAME = 'n_id')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_id_caja') =
            (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_caja' AND COLUMN_NAME = 'n_id')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND CONSTRAINT_NAME = 'fk_tw_pago_cliente_caja'),
        'ALTER TABLE tw_pago_cliente ADD CONSTRAINT fk_tw_pago_cliente_caja FOREIGN KEY (n_id_caja) REFERENCES tw_caja (n_id)',
        'SELECT ''skip fk_tw_pago_cliente_caja'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND COLUMN_NAME = 'n_id_pago_cliente')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_id')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND COLUMN_NAME = 'n_id_pago_cliente') =
            (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_id')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND CONSTRAINT_NAME = 'fk_tw_pago_aplicacion_pago'),
        'ALTER TABLE tw_pago_aplicacion ADD CONSTRAINT fk_tw_pago_aplicacion_pago FOREIGN KEY (n_id_pago_cliente) REFERENCES tw_pago_cliente (n_id)',
        'SELECT ''skip fk_tw_pago_aplicacion_pago'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND COLUMN_NAME = 'n_id_cliente')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_clientes' AND COLUMN_NAME = 'n_id')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND COLUMN_NAME = 'n_id_cliente') =
            (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_clientes' AND COLUMN_NAME = 'n_id')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND CONSTRAINT_NAME = 'fk_tw_pago_aplicacion_cliente'),
        'ALTER TABLE tw_pago_aplicacion ADD CONSTRAINT fk_tw_pago_aplicacion_cliente FOREIGN KEY (n_id_cliente) REFERENCES tc_clientes (n_id)',
        'SELECT ''skip fk_tw_pago_aplicacion_cliente'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND COLUMN_NAME = 'n_id_venta')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_ventas' AND COLUMN_NAME = 'n_id')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND COLUMN_NAME = 'n_id_venta') =
            (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_ventas' AND COLUMN_NAME = 'n_id')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND CONSTRAINT_NAME = 'fk_tw_pago_aplicacion_venta'),
        'ALTER TABLE tw_pago_aplicacion ADD CONSTRAINT fk_tw_pago_aplicacion_venta FOREIGN KEY (n_id_venta) REFERENCES tw_ventas (n_id)',
        'SELECT ''skip fk_tw_pago_aplicacion_venta'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND COLUMN_NAME = 'n_id_facturacion')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion' AND COLUMN_NAME = 'n_id')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND COLUMN_NAME = 'n_id_facturacion') =
            (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion' AND COLUMN_NAME = 'n_id')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND CONSTRAINT_NAME = 'fk_tw_pago_aplicacion_facturacion'),
        'ALTER TABLE tw_pago_aplicacion ADD CONSTRAINT fk_tw_pago_aplicacion_facturacion FOREIGN KEY (n_id_facturacion) REFERENCES tw_facturacion (n_id)',
        'SELECT ''skip fk_tw_pago_aplicacion_facturacion'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND COLUMN_NAME = 'n_id_dato_factura')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura' AND COLUMN_NAME = 'n_id')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND COLUMN_NAME = 'n_id_dato_factura') =
            (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura' AND COLUMN_NAME = 'n_id')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND CONSTRAINT_NAME = 'fk_tw_pago_aplicacion_dato_factura'),
        'ALTER TABLE tw_pago_aplicacion ADD CONSTRAINT fk_tw_pago_aplicacion_dato_factura FOREIGN KEY (n_id_dato_factura) REFERENCES tc_datos_factura (n_id)',
        'SELECT ''skip fk_tw_pago_aplicacion_dato_factura'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND COLUMN_NAME = 'n_id_usuario')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_usuarios' AND COLUMN_NAME = 'n_id')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND COLUMN_NAME = 'n_id_usuario') =
            (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_usuarios' AND COLUMN_NAME = 'n_id')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND CONSTRAINT_NAME = 'fk_tw_pago_aplicacion_usuario'),
        'ALTER TABLE tw_pago_aplicacion ADD CONSTRAINT fk_tw_pago_aplicacion_usuario FOREIGN KEY (n_id_usuario) REFERENCES tc_usuarios (n_id)',
        'SELECT ''skip fk_tw_pago_aplicacion_usuario'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- -------------------------------------------------
-- F) tw_abonos: liga a pago canonico
-- -------------------------------------------------
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
        'SELECT ''skip tw_abonos.n_id_pago_cliente_canonico'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_abonos' AND COLUMN_NAME = 'n_id_pago_cliente_canonico')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_abonos' AND INDEX_NAME = 'idx_tw_abonos_pago_cliente_canonico'),
        'ALTER TABLE tw_abonos ADD INDEX idx_tw_abonos_pago_cliente_canonico (n_id_pago_cliente_canonico)',
        'SELECT ''skip idx_tw_abonos_pago_cliente_canonico'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

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
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- -------------------------------------------------
-- G) tw_facturacion_pac_audit (tabla maestra de auditoria PAC)
-- -------------------------------------------------
CREATE TABLE IF NOT EXISTS tw_facturacion_pac_audit (
    n_id BIGINT NOT NULL AUTO_INCREMENT,
    d_fecha_registro DATETIME NULL,
    s_fecha_operacion VARCHAR(100) NULL,
    s_operacion VARCHAR(100) NULL,
    s_proveedor VARCHAR(60) NULL,
    s_endpoint VARCHAR(255) NULL,
    s_metodo_http VARCHAR(10) NULL,
    n_http_status INT NULL,
    b_success TINYINT(1) NULL,
    s_error_code VARCHAR(120) NULL,
    s_error_message VARCHAR(1000) NULL,
    s_correlation_id VARCHAR(100) NULL,
    s_uuid_relacionado VARCHAR(80) NULL,
    n_razon_social_id BIGINT NULL,
    n_id_venta BIGINT NULL,
    s_rfc_emisor VARCHAR(20) NULL,
    s_usuario VARCHAR(120) NULL,
    PRIMARY KEY (n_id)
);

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion_pac_audit' AND COLUMN_NAME = 'n_id_venta')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion_pac_audit' AND COLUMN_NAME = 's_operacion')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion_pac_audit' AND COLUMN_NAME = 'b_success')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion_pac_audit' AND INDEX_NAME = 'idx_tw_fact_pac_audit_venta_operacion_success'),
        'ALTER TABLE tw_facturacion_pac_audit ADD INDEX idx_tw_fact_pac_audit_venta_operacion_success (n_id_venta, s_operacion, b_success)',
        'SELECT ''skip idx_tw_fact_pac_audit_venta_operacion_success'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion_pac_audit' AND COLUMN_NAME = 's_correlation_id')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion_pac_audit' AND INDEX_NAME = 'idx_tw_fact_pac_audit_correlation_id'),
        'ALTER TABLE tw_facturacion_pac_audit ADD INDEX idx_tw_fact_pac_audit_correlation_id (s_correlation_id)',
        'SELECT ''skip idx_tw_fact_pac_audit_correlation_id'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion_pac_audit' AND COLUMN_NAME = 'd_fecha_registro')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion_pac_audit' AND INDEX_NAME = 'idx_tw_fact_pac_audit_fecha'),
        'ALTER TABLE tw_facturacion_pac_audit ADD INDEX idx_tw_fact_pac_audit_fecha (d_fecha_registro)',
        'SELECT ''skip idx_tw_fact_pac_audit_fecha'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- -------------------------------------------------
-- H) tw_facturacion_pac_audit_detalle
-- -------------------------------------------------
CREATE TABLE IF NOT EXISTS tw_facturacion_pac_audit_detalle (
    n_id BIGINT NOT NULL AUTO_INCREMENT,
    n_id_auditoria BIGINT NOT NULL,
    s_bloque VARCHAR(20) NOT NULL,
    s_clave VARCHAR(255) NOT NULL,
    s_valor LONGTEXT DEFAULT NULL,
    PRIMARY KEY (n_id)
);

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion_pac_audit_detalle' AND COLUMN_NAME = 'n_id_auditoria')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion_pac_audit_detalle' AND INDEX_NAME = 'idx_tw_fact_pac_audit_detalle_auditoria'),
        'ALTER TABLE tw_facturacion_pac_audit_detalle ADD INDEX idx_tw_fact_pac_audit_detalle_auditoria (n_id_auditoria)',
        'SELECT ''skip idx_tw_fact_pac_audit_detalle_auditoria'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion_pac_audit_detalle' AND COLUMN_NAME = 'n_id_auditoria')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion_pac_audit_detalle' AND COLUMN_NAME = 's_bloque')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion_pac_audit_detalle' AND COLUMN_NAME = 's_clave')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion_pac_audit_detalle' AND INDEX_NAME = 'idx_tw_fact_pac_audit_detalle_lookup'),
        'ALTER TABLE tw_facturacion_pac_audit_detalle ADD INDEX idx_tw_fact_pac_audit_detalle_lookup (n_id_auditoria, s_bloque, s_clave)',
        'SELECT ''skip idx_tw_fact_pac_audit_detalle_lookup'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion_pac_audit_detalle' AND COLUMN_NAME = 'n_id_auditoria')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion_pac_audit' AND COLUMN_NAME = 'n_id')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion_pac_audit_detalle' AND COLUMN_NAME = 'n_id_auditoria') =
            (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_facturacion_pac_audit' AND COLUMN_NAME = 'n_id')
        AND NOT EXISTS(
            SELECT 1
            FROM tw_facturacion_pac_audit_detalle d
            LEFT JOIN tw_facturacion_pac_audit a ON a.n_id = d.n_id_auditoria
            WHERE d.n_id_auditoria IS NOT NULL
              AND a.n_id IS NULL
        )
        AND NOT EXISTS(
            SELECT 1 FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
            WHERE TABLE_SCHEMA = DATABASE()
              AND TABLE_NAME = 'tw_facturacion_pac_audit_detalle'
              AND CONSTRAINT_NAME = 'fk_tw_fact_pac_audit_detalle_auditoria'
        ),
        'ALTER TABLE tw_facturacion_pac_audit_detalle ADD CONSTRAINT fk_tw_fact_pac_audit_detalle_auditoria FOREIGN KEY (n_id_auditoria) REFERENCES tw_facturacion_pac_audit (n_id) ON DELETE CASCADE ON UPDATE CASCADE',
        'SELECT ''skip fk_tw_fact_pac_audit_detalle_auditoria'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- -------------------------------------------------
-- I) Ajuste precision decimal pagos (DECIMAL 10,2)
-- -------------------------------------------------
SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_importe_total')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_importe_total') <> 'decimal(10,2)',
        'ALTER TABLE tw_pago_cliente MODIFY COLUMN n_importe_total DECIMAL(10,2)',
        'SELECT ''skip tw_pago_cliente.n_importe_total'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_importe_aplicado')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_importe_aplicado') <> 'decimal(10,2)',
        'ALTER TABLE tw_pago_cliente MODIFY COLUMN n_importe_aplicado DECIMAL(10,2)',
        'SELECT ''skip tw_pago_cliente.n_importe_aplicado'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_importe_disponible')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_importe_disponible') <> 'decimal(10,2)',
        'ALTER TABLE tw_pago_cliente MODIFY COLUMN n_importe_disponible DECIMAL(10,2)',
        'SELECT ''skip tw_pago_cliente.n_importe_disponible'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND COLUMN_NAME = 'n_monto_aplicado')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND COLUMN_NAME = 'n_monto_aplicado') <> 'decimal(10,2)',
        'ALTER TABLE tw_pago_aplicacion MODIFY COLUMN n_monto_aplicado DECIMAL(10,2)',
        'SELECT ''skip tw_pago_aplicacion.n_monto_aplicado'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND COLUMN_NAME = 'n_saldo_anterior')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND COLUMN_NAME = 'n_saldo_anterior') <> 'decimal(10,2)',
        'ALTER TABLE tw_pago_aplicacion MODIFY COLUMN n_saldo_anterior DECIMAL(10,2)',
        'SELECT ''skip tw_pago_aplicacion.n_saldo_anterior'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND COLUMN_NAME = 'n_saldo_insoluto')
        AND (SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_aplicacion' AND COLUMN_NAME = 'n_saldo_insoluto') <> 'decimal(10,2)',
        'ALTER TABLE tw_pago_aplicacion MODIFY COLUMN n_saldo_insoluto DECIMAL(10,2)',
        'SELECT ''skip tw_pago_aplicacion.n_saldo_insoluto'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- -------------------------------------------------
-- J) tw_pago_cliente.n_facturar_rep
-- -------------------------------------------------
SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tw_pago_cliente' AND COLUMN_NAME = 'n_facturar_rep'),
        'ALTER TABLE tw_pago_cliente ADD COLUMN n_facturar_rep TINYINT(1) NOT NULL DEFAULT 1 AFTER n_conciliado',
        'SELECT ''skip tw_pago_cliente.n_facturar_rep'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

UPDATE tw_pago_cliente
SET n_facturar_rep = 1
WHERE n_facturar_rep IS NULL;

-- -------------------------------------------------
-- K) tr_venta_cobro: datos bancarios minimos
-- -------------------------------------------------
SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro' AND COLUMN_NAME = 's_referencia'),
        'ALTER TABLE tr_venta_cobro ADD COLUMN s_referencia VARCHAR(255) NULL AFTER n_id_forma_pago',
        'SELECT ''skip tr_venta_cobro.s_referencia'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro' AND COLUMN_NAME = 's_numero_autorizacion'),
        'ALTER TABLE tr_venta_cobro ADD COLUMN s_numero_autorizacion VARCHAR(255) NULL AFTER s_referencia',
        'SELECT ''skip tr_venta_cobro.s_numero_autorizacion'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro' AND COLUMN_NAME = 's_ultimos4_tarjeta'),
        'ALTER TABLE tr_venta_cobro ADD COLUMN s_ultimos4_tarjeta VARCHAR(10) NULL AFTER s_numero_autorizacion',
        'SELECT ''skip tr_venta_cobro.s_ultimos4_tarjeta'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

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
        'SELECT ''skip tr_venta_cobro.n_id_cuenta_bancaria'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro' AND COLUMN_NAME = 'n_id_cuenta_bancaria')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tr_venta_cobro' AND INDEX_NAME = 'idx_tr_venta_cobro_cuenta_bancaria'),
        'ALTER TABLE tr_venta_cobro ADD INDEX idx_tr_venta_cobro_cuenta_bancaria (n_id_cuenta_bancaria)',
        'SELECT ''skip idx_tr_venta_cobro_cuenta_bancaria'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

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
        'SELECT ''skip fk_tr_venta_cobro_cuenta_bancaria'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- -------------------------------------------------
-- L) tc_datos_factura: columnas requeridas por la version actual
-- -------------------------------------------------
SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura' AND COLUMN_NAME = 's_token_api'),
        'ALTER TABLE tc_datos_factura ADD COLUMN s_token_api TEXT NULL AFTER s_password_key',
        'SELECT ''skip tc_datos_factura.s_token_api'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura' AND COLUMN_NAME = 'n_predeterminado'),
        'ALTER TABLE tc_datos_factura ADD COLUMN n_predeterminado TINYINT(1) NULL DEFAULT 0 AFTER s_token_api',
        'SELECT ''skip tc_datos_factura.n_predeterminado'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura' AND COLUMN_NAME = 's_ruta_xml'),
        'ALTER TABLE tc_datos_factura ADD COLUMN s_ruta_xml VARCHAR(500) NULL AFTER s_password_key',
        'SELECT ''skip tc_datos_factura.s_ruta_xml'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura' AND COLUMN_NAME = 's_ruta_pdf'),
        'ALTER TABLE tc_datos_factura ADD COLUMN s_ruta_pdf VARCHAR(500) NULL AFTER s_ruta_xml',
        'SELECT ''skip tc_datos_factura.s_ruta_pdf'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura' AND COLUMN_NAME = 's_ruta_raiz'),
        'ALTER TABLE tc_datos_factura ADD COLUMN s_ruta_raiz VARCHAR(500) NULL AFTER s_ruta_pdf',
        'SELECT ''skip tc_datos_factura.s_ruta_raiz'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura' AND COLUMN_NAME = 'n_predeterminado')
        AND NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura' AND INDEX_NAME = 'idx_tc_datos_factura_predeterminado'),
        'ALTER TABLE tc_datos_factura ADD INDEX idx_tc_datos_factura_predeterminado (n_predeterminado)',
        'SELECT ''skip idx_tc_datos_factura_predeterminado'''
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

UPDATE tc_datos_factura
SET n_predeterminado = 0
WHERE n_predeterminado IS NULL;

SET @id_predeterminado := (
    SELECT CASE
        WHEN EXISTS(SELECT 1 FROM tc_datos_factura WHERE n_predeterminado = 1) THEN NULL
        ELSE (SELECT MIN(n_id) FROM tc_datos_factura)
    END
);

SET @ddl := IF(
    @id_predeterminado IS NULL,
    'SELECT ''skip set predeterminado''',
    CONCAT('UPDATE tc_datos_factura SET n_predeterminado = IF(n_id = ', @id_predeterminado, ', 1, 0)')
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @ddl := (
    SELECT IF(
        EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura' AND COLUMN_NAME = 's_ruta_raiz')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura' AND COLUMN_NAME = 's_ruta_pdf')
        AND EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tc_datos_factura' AND COLUMN_NAME = 's_ruta_xml'),
        "UPDATE tc_datos_factura SET s_ruta_raiz = '/opt/webserver/backEnd/refacFabela', s_ruta_pdf = '/opt/webserver/backEnd/refacFabela/pdf', s_ruta_xml = '/opt/webserver/backEnd/refacFabela/xml' WHERE n_id IS NOT NULL",
        "SELECT 'skip tc_datos_factura.rutas_unificadas'"
    )
);
PREPARE stmt FROM @ddl; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET SQL_SAFE_UPDATES = @OLD_SQL_SAFE_UPDATES;

-- FIN SCRIPT RELEASE PRODUCCION
ALTER TABLE tc_datos_factura
    DROP COLUMN s_usuario_folios,
    DROP COLUMN s_password_folios,
    DROP COLUMN s_email_usuario,
    DROP COLUMN s_password_email,
    DROP COLUMN s_ruta_cer,
    DROP COLUMN s_ruta_cadena_original,
    DROP COLUMN s_no_certificado;