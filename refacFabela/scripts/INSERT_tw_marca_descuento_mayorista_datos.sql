-- INSERT_tw_marca_descuento_mayorista_datos.sql
-- Archivo de inserción de datos de ejemplo para tw_marca_descuento_mayorista
-- Ejecutar DESPUÉS de V003__create_tw_marca_descuento_mayorista.sql
--
-- INSTRUCCIONES:
-- 1. Primero, obtén los n_id reales de las marcas que quieres incluir:
--    SELECT n_id, s_marca FROM tc_marca WHERE n_estatus = 1 ORDER BY s_marca;
--
-- 2. Reemplaza los valores de n_id_marca y n_ganancia en los INSERT de abajo
--    con los valores reales según tu política comercial
--
-- 3. Ejecuta este script en tu BD

-- Ejemplo de inserción con datos ficticios:
-- Reemplaza estos valores con tus datos reales

INSERT INTO tw_marca_descuento_mayorista (n_id_marca, n_ganancia, n_estatus) VALUES
-- (1, 0.08, 1),      -- Ejemplo: Marca Honda con ganancia 8%
-- (2, 0.10, 1),      -- Ejemplo: Marca Bosch con ganancia 10%
-- (3, 0.07, 1);      -- Ejemplo: Marca Continental con ganancia 7%

-- PLANTILLA PARA AGREGAR TUS MARCAS:
-- Descomenta y modifica las líneas anteriores, o agrega nuevas líneas con el formato:
-- (n_id_marca_aqui, 0.XX, 1),  -- Descripción de marca
;

-- Para verificar que se insertaron correctamente:
-- SELECT m.n_id_marca, marca.s_marca, m.n_ganancia, m.n_estatus 
-- FROM tw_marca_descuento_mayorista m
-- LEFT JOIN tc_marca marca ON m.n_id_marca = marca.n_id
-- ORDER BY marca.s_marca;
