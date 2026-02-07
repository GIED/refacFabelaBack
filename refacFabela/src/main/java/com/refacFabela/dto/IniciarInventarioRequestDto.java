package com.refacFabela.dto;

import java.io.Serializable;

/**
 * DTO para la petición de inicio de inventario por ubicación.
 */
public class IniciarInventarioRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long nIdBodega;
    private Long nIdAnaquel;
    private Long nIdNivel;
    private String sObservaciones;

    public IniciarInventarioRequestDto() {
    }

    // Getters y Setters
    public Long getnIdBodega() {
        return nIdBodega;
    }

    public void setnIdBodega(Long nIdBodega) {
        this.nIdBodega = nIdBodega;
    }

    public Long getnIdAnaquel() {
        return nIdAnaquel;
    }

    public void setnIdAnaquel(Long nIdAnaquel) {
        this.nIdAnaquel = nIdAnaquel;
    }

    public Long getnIdNivel() {
        return nIdNivel;
    }

    public void setnIdNivel(Long nIdNivel) {
        this.nIdNivel = nIdNivel;
    }

    public String getsObservaciones() {
        return sObservaciones;
    }

    public void setsObservaciones(String sObservaciones) {
        this.sObservaciones = sObservaciones;
    }
}
