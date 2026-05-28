SET @schema_name = DATABASE();

SET @sql = (
	SELECT IF(
		COUNT(*) = 0,
		'ALTER TABLE tc_clientes ADD COLUMN n_correo_bloqueado TINYINT(1) NOT NULL DEFAULT 0 AFTER n_datos_validados',
		'SELECT ''Column n_correo_bloqueado already exists'''
	)
	FROM information_schema.COLUMNS
	WHERE TABLE_SCHEMA = @schema_name
		AND TABLE_NAME = 'tc_clientes'
		AND COLUMN_NAME = 'n_correo_bloqueado'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = (
	SELECT IF(
		COUNT(*) = 0,
		'ALTER TABLE tc_clientes ADD COLUMN s_motivo_bloqueo_correo VARCHAR(255) NULL AFTER n_correo_bloqueado',
		'SELECT ''Column s_motivo_bloqueo_correo already exists'''
	)
	FROM information_schema.COLUMNS
	WHERE TABLE_SCHEMA = @schema_name
		AND TABLE_NAME = 'tc_clientes'
		AND COLUMN_NAME = 's_motivo_bloqueo_correo'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = (
	SELECT IF(
		COUNT(*) = 0,
		'ALTER TABLE tc_clientes ADD COLUMN d_fecha_bloqueo_correo DATETIME NULL AFTER s_motivo_bloqueo_correo',
		'SELECT ''Column d_fecha_bloqueo_correo already exists'''
	)
	FROM information_schema.COLUMNS
	WHERE TABLE_SCHEMA = @schema_name
		AND TABLE_NAME = 'tc_clientes'
		AND COLUMN_NAME = 'd_fecha_bloqueo_correo'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

UPDATE tc_clientes
SET n_correo_bloqueado = 0
WHERE n_correo_bloqueado IS NULL;