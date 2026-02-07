package com.refacFabela.dto;

import java.io.Serializable;

/**
 * DTO para la petición de autorización de inventario (rol ADMIN).
 */
public class AutorizarInventarioRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sMotivoAutorizacion;

    public AutorizarInventarioRequestDto() {
    }

    // Getters y Setters
    public String getsMotivoAutorizacion() {
        return sMotivoAutorizacion;
    }

    public void setsMotivoAutorizacion(String sMotivoAutorizacion) {
        this.sMotivoAutorizacion = sMotivoAutorizacion;
    }
}
