ALTER TABLE tw_pago_cliente MODIFY COLUMN n_importe_total DECIMAL(10,2);
ALTER TABLE tw_pago_cliente MODIFY COLUMN n_importe_aplicado DECIMAL(10,2);
ALTER TABLE tw_pago_cliente MODIFY COLUMN n_importe_disponible DECIMAL(10,2);
ALTER TABLE tw_pago_aplicacion MODIFY COLUMN n_monto_aplicado DECIMAL(10,2);
ALTER TABLE tw_pago_aplicacion MODIFY COLUMN n_saldo_anterior DECIMAL(10,2);
ALTER TABLE tw_pago_aplicacion MODIFY COLUMN n_saldo_insoluto DECIMAL(10,2);
