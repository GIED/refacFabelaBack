package com.refacFabela.dto;

import java.io.Serializable;

/**
 * DTO para movimiento externo de mercancía entre bodegas.
 * El frontend envía solamente los IDs y la cantidad a mover;
 * la aritmética de inventario se realiza exclusivamente en el backend.
 */
public class TraspasoExternoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID del producto a traspasar */
    private Long nIdProducto;

    /** ID de la bodega origen (de donde se resta) */
    private Long nIdBodegaOrigen;

    /** ID de la bodega destino (a donde se suma) */
    private Long nIdBodegaDestino;

    /** Cantidad de piezas a mover */
    private Integer nCantidad;

    public TraspasoExternoDTO() {
    }

    public Long getnIdProducto() {
        return nIdProducto;
    }

    public void setnIdProducto(Long nIdProducto) {
        this.nIdProducto = nIdProducto;
    }

    public Long getnIdBodegaOrigen() {
        return nIdBodegaOrigen;
    }

    public void setnIdBodegaOrigen(Long nIdBodegaOrigen) {
        this.nIdBodegaOrigen = nIdBodegaOrigen;
    }

    public Long getnIdBodegaDestino() {
        return nIdBodegaDestino;
    }

    public void setnIdBodegaDestino(Long nIdBodegaDestino) {
        this.nIdBodegaDestino = nIdBodegaDestino;
    }

    public Integer getnCantidad() {
        return nCantidad;
    }

    public void setnCantidad(Integer nCantidad) {
        this.nCantidad = nCantidad;
    }

    @Override
    public String toString() {
        return "TraspasoExternoDTO [nIdProducto=" + nIdProducto + ", nIdBodegaOrigen=" + nIdBodegaOrigen
                + ", nIdBodegaDestino=" + nIdBodegaDestino + ", nCantidad=" + nCantidad + "]";
    }
}
