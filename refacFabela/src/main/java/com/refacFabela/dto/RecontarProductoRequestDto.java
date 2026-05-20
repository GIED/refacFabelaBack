package com.refacFabela.dto;

/**
 * DTO para solicitud de re-conteo de producto por stock obsoleto.
 * Cuando se detecta que el stock cambió desde el levantamiento inicial,
 * se requiere hacer un nuevo conteo actualizado.
 */
public class RecontarProductoRequestDto {

    /**
     * Nueva cantidad contada durante el re-conteo.
     */
    private Integer nCantidadContada;

    /**
     * Motivo/observación del re-conteo.
     */
    private String sMotivo;

    // ===== Constructores =====
    
    public RecontarProductoRequestDto() {
    }

    public RecontarProductoRequestDto(Integer nCantidadContada, String sMotivo) {
        this.nCantidadContada = nCantidadContada;
        this.sMotivo = sMotivo;
    }

    // ===== Getters y Setters =====
    
    public Integer getnCantidadContada() {
        return nCantidadContada;
    }

    public void setnCantidadContada(Integer nCantidadContada) {
        this.nCantidadContada = nCantidadContada;
    }

    public String getsMotivo() {
        return sMotivo;
    }

    public void setsMotivo(String sMotivo) {
        this.sMotivo = sMotivo;
    }
}
