-- =============================================================
-- MIGRACIÓN: Optimistic Locking para tw_productobodega
-- =============================================================
-- EJECUTAR EN MYSQL ANTES DE DESPLEGAR LA NUEVA VERSIÓN DEL BACKEND.
-- Agrega columna n_version para @Version de JPA (bloqueo optimista).
-- Complementa el bloqueo pesimista (SELECT FOR UPDATE) ya implementado.
-- =============================================================

ALTER TABLE tw_productobodega 
  ADD COLUMN n_version INT NOT NULL DEFAULT 0;

-- =============================================================
-- OPCIONAL: Actualizar el trigger para ignorar UPDATEs que no 
-- cambian la cantidad (evita "movimientos fantasma" en el log).
-- 
-- IMPORTANTE: Primero revisa tu trigger actual con:
--   SHOW TRIGGERS WHERE `Table` = 'tw_productobodega';
-- 
-- Luego adapta el cuerpo del trigger para agregar esta condición:
--   IF OLD.n_cantidad != NEW.n_cantidad THEN
--     -- ... tu lógica de log aquí ...
--   END IF;
-- =============================================================
