package com.refacFabela.dto;

import java.io.Serializable;

/**
 * DTO para actualizar el conteo de un producto específico en el inventario.
 */
public class ActualizarConteoRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer nCantidadContada;
    private String sObservacion;
    private String sMotivo; // Para auditoría

    public ActualizarConteoRequestDto() {
    }

    // Getters y Setters
    public Integer getnCantidadContada() {
        return nCantidadContada;
    }

    public void setnCantidadContada(Integer nCantidadContada) {
        this.nCantidadContada = nCantidadContada;
    }

    public String getsObservacion() {
        return sObservacion;
    }

    public void setsObservacion(String sObservacion) {
        this.sObservacion = sObservacion;
    }

    public String getsMotivo() {
        return sMotivo;
    }

    public void setsMotivo(String sMotivo) {
        this.sMotivo = sMotivo;
    }
}
