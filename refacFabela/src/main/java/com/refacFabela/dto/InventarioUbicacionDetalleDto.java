package com.refacFabela.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO para el detalle de productos en un inventario por ubicación.
 */
public class InventarioUbicacionDetalleDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long nId;
    private Long nIdInventario;
    private Long nIdProducto;
    private String sNoParte;
    private String sNombreProducto;
    private String sMarca;
    private Integer nCantidadTeoricaIni;
    private Integer nCantidadTeoricaRef;
    private LocalDateTime dRefActualizada;
    private Integer nCantidadContada;
    private Integer nEstatusLinea;
    private String sDescripcionEstatusLinea;
    private LocalDateTime dCaptura;
    private Long nIdUsuarioCaptura;
    private String sNombreUsuarioCaptura;
    private String sObservacion;
    
    // Campos calculados
    private Integer nDiferencia; // contada - ref

    public InventarioUbicacionDetalleDto() {
    }

    // Getters y Setters
    public Long getnId() {
        return nId;
    }

    public void setnId(Long nId) {
        this.nId = nId;
    }

    public Long getnIdInventario() {
        return nIdInventario;
    }

    public void setnIdInventario(Long nIdInventario) {
        this.nIdInventario = nIdInventario;
    }

    public Long getnIdProducto() {
        return nIdProducto;
    }

    public void setnIdProducto(Long nIdProducto) {
        this.nIdProducto = nIdProducto;
    }

    public String getsNoParte() {
        return sNoParte;
    }

    public void setsNoParte(String sNoParte) {
        this.sNoParte = sNoParte;
    }

    public String getsNombreProducto() {
        return sNombreProducto;
    }

    public void setsNombreProducto(String sNombreProducto) {
        this.sNombreProducto = sNombreProducto;
    }

    public String getsMarca() {
        return sMarca;
    }

    public void setsMarca(String sMarca) {
        this.sMarca = sMarca;
    }

    public Integer getnCantidadTeoricaIni() {
        return nCantidadTeoricaIni;
    }

    public void setnCantidadTeoricaIni(Integer nCantidadTeoricaIni) {
        this.nCantidadTeoricaIni = nCantidadTeoricaIni;
    }

    public Integer getnCantidadTeoricaRef() {
        return nCantidadTeoricaRef;
    }

    public void setnCantidadTeoricaRef(Integer nCantidadTeoricaRef) {
        this.nCantidadTeoricaRef = nCantidadTeoricaRef;
    }

    public LocalDateTime getdRefActualizada() {
        return dRefActualizada;
    }

    public void setdRefActualizada(LocalDateTime dRefActualizada) {
        this.dRefActualizada = dRefActualizada;
    }

    public Integer getnCantidadContada() {
        return nCantidadContada;
    }

    public void setnCantidadContada(Integer nCantidadContada) {
        this.nCantidadContada = nCantidadContada;
    }

    public Integer getnEstatusLinea() {
        return nEstatusLinea;
    }

    public void setnEstatusLinea(Integer nEstatusLinea) {
        this.nEstatusLinea = nEstatusLinea;
    }

    public String getsDescripcionEstatusLinea() {
        return sDescripcionEstatusLinea;
    }

    public void setsDescripcionEstatusLinea(String sDescripcionEstatusLinea) {
        this.sDescripcionEstatusLinea = sDescripcionEstatusLinea;
    }

    public LocalDateTime getdCaptura() {
        return dCaptura;
    }

    public void setdCaptura(LocalDateTime dCaptura) {
        this.dCaptura = dCaptura;
    }

    public Long getnIdUsuarioCaptura() {
        return nIdUsuarioCaptura;
    }

    public void setnIdUsuarioCaptura(Long nIdUsuarioCaptura) {
        this.nIdUsuarioCaptura = nIdUsuarioCaptura;
    }

    public String getsNombreUsuarioCaptura() {
        return sNombreUsuarioCaptura;
    }

    public void setsNombreUsuarioCaptura(String sNombreUsuarioCaptura) {
        this.sNombreUsuarioCaptura = sNombreUsuarioCaptura;
    }

    public String getsObservacion() {
        return sObservacion;
    }

    public void setsObservacion(String sObservacion) {
        this.sObservacion = sObservacion;
    }

    public Integer getnDiferencia() {
        return nDiferencia;
    }

    public void setnDiferencia(Integer nDiferencia) {
        this.nDiferencia = nDiferencia;
    }
}
