package com.refacFabela.dto;

import java.io.Serializable;

/**
 * DTO para la petición de ajuste individual de producto en inventario (rol ADMIN).
 */
public class AjustarProductoRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sMotivoAjuste;
    private Integer nCantidadCorregida;

    public AjustarProductoRequestDto() {
    }

    // Getters y Setters
    public String getsMotivoAjuste() {
        return sMotivoAjuste;
    }

    public void setsMotivoAjuste(String sMotivoAjuste) {
        this.sMotivoAjuste = sMotivoAjuste;
    }

    public Integer getnCantidadCorregida() {
        return nCantidadCorregida;
    }

    public void setnCantidadCorregida(Integer nCantidadCorregida) {
        this.nCantidadCorregida = nCantidadCorregida;
    }
}
