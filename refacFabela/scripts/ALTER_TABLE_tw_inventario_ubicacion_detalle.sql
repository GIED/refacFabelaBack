-- Script SQL para agregar campos de ajuste individual a tw_inventario_ubicacion_det
-- Fecha: 07/02/2026
-- Propósito: Permitir el registro de ajustes individuales por producto antes de la autorización final del inventario

-- Agregar campos de ajuste individual
ALTER TABLE tw_inventario_ubicacion_det
ADD COLUMN b_ajustado BOOLEAN DEFAULT FALSE COMMENT 'Indica si el producto ya fue ajustado individualmente',
ADD COLUMN s_motivo_ajuste VARCHAR(500) COMMENT 'Motivo del ajuste individual del producto',
ADD COLUMN d_fecha_ajuste DATETIME COMMENT 'Fecha y hora en que se realizó el ajuste',
ADD COLUMN n_id_usuario_ajuste INT COMMENT 'ID del usuario (admin) que realizó el ajuste',
ADD CONSTRAINT fk_inventario_detalle_usuario_ajuste 
    FOREIGN KEY (n_id_usuario_ajuste) REFERENCES tc_usuarios(n_id);

-- Crear índice para búsquedas por estado de ajuste
CREATE INDEX idx_inventario_detalle_ajustado ON tw_inventario_ubicacion_det(b_ajustado);

-- Comentarios de la tabla
ALTER TABLE tw_inventario_ubicacion_det COMMENT = 'Detalle de productos en inventario por ubicación con control de ajustes individuales';

-- NOTA IMPORTANTE: 
-- Este script agrega la funcionalidad de ajuste individual de productos.
-- Flujo de ajuste:
-- 1. El usuario de almacén cierra el inventario (pasa a EN_REVISION)
-- 2. El administrador revisa los productos con diferencias
-- 3. Para cada producto con diferencia, el admin hace clic en "Ajustar"
-- 4. El sistema actualiza tw_productobodega con la cantidad contada
-- 5. Se marca b_ajustado = TRUE y se registra el motivo
-- 6. Solo cuando TODOS los productos con diferencias estén ajustados, 
--    el botón "Autorizar" se habilitará
