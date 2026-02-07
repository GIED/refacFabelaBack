package com.refacFabela.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO completo del inventario por ubicación con su detalle.
 */
public class InventarioUbicacionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long nId;
    private Long nIdBodega;
    private String sBodega;
    private Long nIdAnaquel;
    private String sAnaquel;
    private Long nIdNivel;
    private String sNivel;
    private Integer nEstatus;
    private String sDescripcionEstatus;
    private LocalDateTime dInicio;
    private LocalDateTime dUltimaActividad;
    private LocalDateTime dCierre;
    private Long nIdUsuarioResponsable;
    private String sNombreUsuarioResponsable;
    private Long nIdUsuarioCierra;
    private String sNombreUsuarioCierra;
    private Long nIdUsuarioAutoriza;
    private String sNombreUsuarioAutoriza;
    private LocalDateTime dAutorizacion;
    private String sMotivoAutorizacion;
    private String sObservaciones;
    
    // Estadísticas calculadas
    private Integer totalLineas;
    private Integer lineasPendientes;
    private Integer lineasContadas;
    private Integer lineasRecontar;

    // Detalle de productos
    private List<InventarioUbicacionDetalleDto> detalle;

    public InventarioUbicacionDto() {
    }

    // Getters y Setters
    public Long getnId() {
        return nId;
    }

    public void setnId(Long nId) {
        this.nId = nId;
    }

    public Long getnIdBodega() {
        return nIdBodega;
    }

    public void setnIdBodega(Long nIdBodega) {
        this.nIdBodega = nIdBodega;
    }

    public String getsBodega() {
        return sBodega;
    }

    public void setsBodega(String sBodega) {
        this.sBodega = sBodega;
    }

    public Long getnIdAnaquel() {
        return nIdAnaquel;
    }

    public void setnIdAnaquel(Long nIdAnaquel) {
        this.nIdAnaquel = nIdAnaquel;
    }

    public String getsAnaquel() {
        return sAnaquel;
    }

    public void setsAnaquel(String sAnaquel) {
        this.sAnaquel = sAnaquel;
    }

    public Long getnIdNivel() {
        return nIdNivel;
    }

    public void setnIdNivel(Long nIdNivel) {
        this.nIdNivel = nIdNivel;
    }

    public String getsNivel() {
        return sNivel;
    }

    public void setsNivel(String sNivel) {
        this.sNivel = sNivel;
    }

    public Integer getnEstatus() {
        return nEstatus;
    }

    public void setnEstatus(Integer nEstatus) {
        this.nEstatus = nEstatus;
    }

    public String getsDescripcionEstatus() {
        return sDescripcionEstatus;
    }

    public void setsDescripcionEstatus(String sDescripcionEstatus) {
        this.sDescripcionEstatus = sDescripcionEstatus;
    }

    public LocalDateTime getdInicio() {
        return dInicio;
    }

    public void setdInicio(LocalDateTime dInicio) {
        this.dInicio = dInicio;
    }

    public LocalDateTime getdUltimaActividad() {
        return dUltimaActividad;
    }

    public void setdUltimaActividad(LocalDateTime dUltimaActividad) {
        this.dUltimaActividad = dUltimaActividad;
    }

    public LocalDateTime getdCierre() {
        return dCierre;
    }

    public void setdCierre(LocalDateTime dCierre) {
        this.dCierre = dCierre;
    }

    public Long getnIdUsuarioResponsable() {
        return nIdUsuarioResponsable;
    }

    public void setnIdUsuarioResponsable(Long nIdUsuarioResponsable) {
        this.nIdUsuarioResponsable = nIdUsuarioResponsable;
    }

    public String getsNombreUsuarioResponsable() {
        return sNombreUsuarioResponsable;
    }

    public void setsNombreUsuarioResponsable(String sNombreUsuarioResponsable) {
        this.sNombreUsuarioResponsable = sNombreUsuarioResponsable;
    }

    public Long getnIdUsuarioCierra() {
        return nIdUsuarioCierra;
    }

    public void setnIdUsuarioCierra(Long nIdUsuarioCierra) {
        this.nIdUsuarioCierra = nIdUsuarioCierra;
    }

    public String getsNombreUsuarioCierra() {
        return sNombreUsuarioCierra;
    }

    public void setsNombreUsuarioCierra(String sNombreUsuarioCierra) {
        this.sNombreUsuarioCierra = sNombreUsuarioCierra;
    }

    public Long getnIdUsuarioAutoriza() {
        return nIdUsuarioAutoriza;
    }

    public void setnIdUsuarioAutoriza(Long nIdUsuarioAutoriza) {
        this.nIdUsuarioAutoriza = nIdUsuarioAutoriza;
    }

    public String getsNombreUsuarioAutoriza() {
        return sNombreUsuarioAutoriza;
    }

    public void setsNombreUsuarioAutoriza(String sNombreUsuarioAutoriza) {
        this.sNombreUsuarioAutoriza = sNombreUsuarioAutoriza;
    }

    public LocalDateTime getdAutorizacion() {
        return dAutorizacion;
    }

    public void setdAutorizacion(LocalDateTime dAutorizacion) {
        this.dAutorizacion = dAutorizacion;
    }

    public String getsMotivoAutorizacion() {
        return sMotivoAutorizacion;
    }

    public void setsMotivoAutorizacion(String sMotivoAutorizacion) {
        this.sMotivoAutorizacion = sMotivoAutorizacion;
    }

    public String getsObservaciones() {
        return sObservaciones;
    }

    public void setsObservaciones(String sObservaciones) {
        this.sObservaciones = sObservaciones;
    }

    public Integer getTotalLineas() {
        return totalLineas;
    }

    public void setTotalLineas(Integer totalLineas) {
        this.totalLineas = totalLineas;
    }

    public Integer getLineasPendientes() {
        return lineasPendientes;
    }

    public void setLineasPendientes(Integer lineasPendientes) {
        this.lineasPendientes = lineasPendientes;
    }

    public Integer getLineasContadas() {
        return lineasContadas;
    }

    public void setLineasContadas(Integer lineasContadas) {
        this.lineasContadas = lineasContadas;
    }

    public Integer getLineasRecontar() {
        return lineasRecontar;
    }

    public void setLineasRecontar(Integer lineasRecontar) {
        this.lineasRecontar = lineasRecontar;
    }

    public List<InventarioUbicacionDetalleDto> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<InventarioUbicacionDetalleDto> detalle) {
        this.detalle = detalle;
    }
}
