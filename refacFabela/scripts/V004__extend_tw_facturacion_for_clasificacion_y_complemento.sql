ALTER TABLE tw_facturacion
    ADD COLUMN s_clasificacion_fiscal TEXT NULL AFTER s_uuid,
    ADD COLUMN s_metodo_pago_fiscal TEXT NULL AFTER s_clasificacion_fiscal,
    ADD COLUMN s_forma_pago_fiscal TEXT NULL AFTER s_metodo_pago_fiscal,
    ADD COLUMN s_uuid_complemento_pago TEXT NULL AFTER s_forma_pago_fiscal,
    ADD COLUMN s_estado_complemento TEXT NULL AFTER s_uuid_complemento_pago,
    ADD COLUMN s_error_complemento TEXT NULL AFTER s_estado_complemento;