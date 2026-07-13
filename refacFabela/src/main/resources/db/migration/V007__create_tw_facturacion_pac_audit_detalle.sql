CREATE TABLE IF NOT EXISTS tw_facturacion_pac_audit_detalle (
    n_id BIGINT NOT NULL AUTO_INCREMENT,
    n_id_auditoria BIGINT NOT NULL,
    s_bloque VARCHAR(20) NOT NULL,
    s_clave VARCHAR(255) NOT NULL,
    s_valor LONGTEXT DEFAULT NULL,
    PRIMARY KEY (n_id),
    KEY idx_tw_fact_pac_audit_detalle_auditoria (n_id_auditoria),
    KEY idx_tw_fact_pac_audit_detalle_lookup (n_id_auditoria, s_bloque, s_clave),
    CONSTRAINT fk_tw_fact_pac_audit_detalle_auditoria
        FOREIGN KEY (n_id_auditoria) REFERENCES tw_facturacion_pac_audit (n_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);