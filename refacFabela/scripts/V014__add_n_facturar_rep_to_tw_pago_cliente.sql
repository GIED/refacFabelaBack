ALTER TABLE tw_pago_cliente
ADD COLUMN n_facturar_rep TINYINT(1) NOT NULL DEFAULT 1 AFTER n_conciliado;

UPDATE tw_pago_cliente
SET n_facturar_rep = 1
WHERE n_facturar_rep IS NULL;
