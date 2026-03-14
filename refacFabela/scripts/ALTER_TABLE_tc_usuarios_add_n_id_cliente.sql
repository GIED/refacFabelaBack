-- =====================================================================
-- Script: Agregar campo n_id_cliente a tc_usuarios y rol ROLE_REVENDEDOR
-- Fecha: 2026-03-12
-- Descripción: Permite vincular un usuario con un cliente (revendedor)
--              directamente en tc_usuarios para inyectarlo al JWT.
-- =====================================================================

-- 1. Agregar columna n_id_cliente (nullable) a tc_usuarios
ALTER TABLE tc_usuarios
  ADD COLUMN n_id_cliente BIGINT NULL DEFAULT NULL AFTER n_estatus;

-- 2. Agregar FK hacia tc_clientes
ALTER TABLE tc_usuarios
  ADD CONSTRAINT fk_usuario_cliente
  FOREIGN KEY (n_id_cliente) REFERENCES tc_clientes(n_id_cliente)
  ON DELETE SET NULL;

-- 3. Insertar nuevo rol ROLE_REVENDEDOR en tc_rol
--    (Ajustar el ID según el siguiente disponible en tu tabla)
INSERT INTO tc_rol (s_rol) VALUES ('ROLE_REVENDEDOR');

-- 4. (Opcional) Migrar relación existente: poblar n_id_cliente
--    para usuarios que ya tengan un cliente vinculado vía tc_clientes.n_idUsuario
UPDATE tc_usuarios u
  INNER JOIN tc_clientes c ON c.n_idUsuario = u.n_id
SET u.n_id_cliente = c.n_id_cliente
WHERE c.n_idUsuario IS NOT NULL;
