-- V003: Crear tabla tw_marca_descuento_mayorista para descuentos específicos de marca a mayoristas
-- Fecha: 2026-04-25
-- Descripción: Tabla que almacena % de ganancia específica por marca para clientes mayoristas
-- 
-- IMPORTANTE: 
-- 1. La migración crea la tabla vacía
-- 2. Después de ejecutar esta migración, ejecuta: INSERT_tw_marca_descuento_mayorista_datos.sql
--    para insertar los datos según tu política comercial

CREATE TABLE tw_marca_descuento_mayorista (
    n_id_marca BIGINT NOT NULL PRIMARY KEY,
    n_ganancia DECIMAL(10, 4) NOT NULL COMMENT 'Porcentaje de ganancia (ej: 0.08 = 8%)',
    n_estatus TINYINT DEFAULT 1 COMMENT '1=Activo, 0=Inactivo',
    d_fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_tw_marca_descuento_mayorista_tc_marca 
        FOREIGN KEY (n_id_marca) REFERENCES tc_marca(n_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    
    INDEX idx_tw_marca_descuento_mayorista_estatus (n_estatus)
) ENGINE=InnoDB 
  DEFAULT CHARSET=utf8 
  COLLATE=utf8_general_ci
  COMMENT='Descuentos/ganancias especiales por marca para mayoristas';
