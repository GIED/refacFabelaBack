/*
  AMBIENTE DE PRUEBAS
  Depuración de columnas JSON del modelo anterior.
  Se asume que ya se creó tw_facturacion_pac_audit_detalle y que el histórico legacy NO se conserva.
*/

/* Evita error 1175 en clientes con safe updates activo (ej. Workbench) */
SET @OLD_SQL_SAFE_UPDATES := @@SQL_SAFE_UPDATES;
SET SQL_SAFE_UPDATES = 0;

/*
  Eliminar triggers legacy sobre tw_facturacion_pac_audit que todavía
  referencien columnas JSON depuradas.
*/
DROP PROCEDURE IF EXISTS sp_drop_legacy_pac_audit_triggers;
DELIMITER $$
CREATE PROCEDURE sp_drop_legacy_pac_audit_triggers()
BEGIN
    DECLARE v_done INT DEFAULT 0;
    DECLARE v_trigger_name VARCHAR(128);

    DECLARE cur CURSOR FOR
        SELECT TRIGGER_NAME
        FROM INFORMATION_SCHEMA.TRIGGERS
        WHERE TRIGGER_SCHEMA = DATABASE()
          AND EVENT_OBJECT_TABLE = 'tw_facturacion_pac_audit'
          AND (
              ACTION_STATEMENT LIKE '%s_request_json%'
           OR ACTION_STATEMENT LIKE '%s_response_json%'
           OR ACTION_STATEMENT LIKE '%s_metadata_json%'
          );

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_done = 1;

    OPEN cur;

    drop_loop: LOOP
        FETCH cur INTO v_trigger_name;
        IF v_done = 1 THEN
            LEAVE drop_loop;
        END IF;

        SET @drop_trigger_sql := CONCAT(
            'DROP TRIGGER IF EXISTS `',
            REPLACE(DATABASE(), '`', '``'),
            '`.`',
            REPLACE(v_trigger_name, '`', '``'),
            '`'
        );

        PREPARE stmt FROM @drop_trigger_sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;

    CLOSE cur;
END$$
DELIMITER ;

CALL sp_drop_legacy_pac_audit_triggers();
DROP PROCEDURE IF EXISTS sp_drop_legacy_pac_audit_triggers;

/* Eliminar s_request_json si existe */
SET @sql := (
    SELECT IF(
        EXISTS(
            SELECT 1
            FROM INFORMATION_SCHEMA.COLUMNS
            WHERE TABLE_SCHEMA = DATABASE()
              AND TABLE_NAME = 'tw_facturacion_pac_audit'
              AND COLUMN_NAME = 's_request_json'
        ),
        'ALTER TABLE tw_facturacion_pac_audit DROP COLUMN s_request_json',
        'SELECT ''skip drop s_request_json: not exists'''
    )
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

/* Eliminar s_response_json si existe */
SET @sql := (
    SELECT IF(
        EXISTS(
            SELECT 1
            FROM INFORMATION_SCHEMA.COLUMNS
            WHERE TABLE_SCHEMA = DATABASE()
              AND TABLE_NAME = 'tw_facturacion_pac_audit'
              AND COLUMN_NAME = 's_response_json'
        ),
        'ALTER TABLE tw_facturacion_pac_audit DROP COLUMN s_response_json',
        'SELECT ''skip drop s_response_json: not exists'''
    )
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

/* Eliminar s_metadata_json si existe */
SET @sql := (
    SELECT IF(
        EXISTS(
            SELECT 1
            FROM INFORMATION_SCHEMA.COLUMNS
            WHERE TABLE_SCHEMA = DATABASE()
              AND TABLE_NAME = 'tw_facturacion_pac_audit'
              AND COLUMN_NAME = 's_metadata_json'
        ),
        'ALTER TABLE tw_facturacion_pac_audit DROP COLUMN s_metadata_json',
        'SELECT ''skip drop s_metadata_json: not exists'''
    )
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

/* Eliminar tabla legacy si quedó de pruebas anteriores */
DROP TABLE IF EXISTS tw_facturacion_pac_audit_legacy;

/* Verificación: este SELECT debe regresar 0 filas */
SELECT
        TRIGGER_NAME,
        EVENT_MANIPULATION,
        EVENT_OBJECT_TABLE
FROM INFORMATION_SCHEMA.TRIGGERS
WHERE TRIGGER_SCHEMA = DATABASE()
    AND EVENT_OBJECT_TABLE = 'tw_facturacion_pac_audit'
    AND (
            ACTION_STATEMENT LIKE '%s_request_json%'
     OR ACTION_STATEMENT LIKE '%s_response_json%'
     OR ACTION_STATEMENT LIKE '%s_metadata_json%'
    );

SET SQL_SAFE_UPDATES = @OLD_SQL_SAFE_UPDATES;
