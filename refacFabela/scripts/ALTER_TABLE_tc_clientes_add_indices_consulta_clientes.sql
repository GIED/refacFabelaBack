SET @schema_name = DATABASE();

SET @sql = (
    SELECT IF(
        COUNT(*) = 0,
        'ALTER TABLE tc_clientes ADD INDEX idx_tc_clientes_estatus (n_estatus)',
        'SELECT ''Index idx_tc_clientes_estatus already exists'''
    )
    FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = @schema_name
      AND TABLE_NAME = 'tc_clientes'
      AND INDEX_NAME = 'idx_tc_clientes_estatus'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = (
    SELECT IF(
        COUNT(*) = 0,
        'ALTER TABLE tc_clientes ADD INDEX idx_tc_clientes_estatus_razon (n_estatus, s_razon_social(191))',
        'SELECT ''Index idx_tc_clientes_estatus_razon already exists'''
    )
    FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = @schema_name
      AND TABLE_NAME = 'tc_clientes'
      AND INDEX_NAME = 'idx_tc_clientes_estatus_razon'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
