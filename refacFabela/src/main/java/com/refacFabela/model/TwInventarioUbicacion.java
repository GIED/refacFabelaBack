package com.refacFabela.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

/**
 * Entidad que representa la cabecera de un inventario por ubicación (bodega+anaquel+nivel).
 * Controla el flujo de trabajo desde apertura (ALMACÉN) hasta autorización y aplicación (ADMIN).
 */
@Entity
@Table(name = "tw_inventario_ubicacion")
@NamedQuery(name = "TwInventarioUbicacion.findAll", query = "SELECT t FROM TwInventarioUbicacion t")
public class TwInventarioUbicacion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long nId;

    @Column(name = "n_idbodega", nullable = false)
    private Long nIdBodega;

    @Column(name = "n_idanaquel", nullable = false)
    private Long nIdAnaquel;

    @Column(name = "n_idnivel", nullable = false)
    private Long nIdNivel;

    @Column(name = "n_estatus", nullable = false)
    private Integer nEstatus; // 1=ABIERTO, 2=PAUSADO, 3=EN_REVISION, 4=AUTORIZADO, 5=APLICADO, 6=CANCELADO

    @Column(name = "d_inicio", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dInicio;

    @Column(name = "d_ultima_actividad", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dUltimaActividad;

    @Column(name = "d_cierre")
    private LocalDateTime dCierre;

    @Column(name = "n_idusuario_responsable", nullable = false)
    private Long nIdUsuarioResponsable;

    @Column(name = "n_idusuario_cierra")
    private Long nIdUsuarioCierra;

    @Column(name = "n_idusuario_autoriza")
    private Long nIdUsuarioAutoriza;

    @Column(name = "d_autorizacion")
    private LocalDateTime dAutorizacion;

    @Column(name = "s_motivo_autorizacion", length = 300)
    private String sMotivoAutorizacion;

    @Column(name = "s_observaciones", length = 500)
    private String sObservaciones;

    // Relaciones ManyToOne
    @ManyToOne
    @JoinColumn(name = "n_idbodega", insertable = false, updatable = false)
    private TcBodega tcBodega;

    @ManyToOne
    @JoinColumn(name = "n_idanaquel", insertable = false, updatable = false)
    private TcAnaquel tcAnaquel;

    @ManyToOne
    @JoinColumn(name = "n_idnivel", insertable = false, updatable = false)
    private TcNivel tcNivel;

    @ManyToOne
    @JoinColumn(name = "n_idusuario_responsable", insertable = false, updatable = false)
    private TcUsuario tcUsuarioResponsable;

    @ManyToOne
    @JoinColumn(name = "n_idusuario_cierra", insertable = false, updatable = false)
    private TcUsuario tcUsuarioCierra;

    @ManyToOne
    @JoinColumn(name = "n_idusuario_autoriza", insertable = false, updatable = false)
    private TcUsuario tcUsuarioAutoriza;

    public TwInventarioUbicacion() {
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

    public Integer getnEstatus() {
        return nEstatus;
    }

    public void setnEstatus(Integer nEstatus) {
        this.nEstatus = nEstatus;
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

    public Long getnIdUsuarioCierra() {
        return nIdUsuarioCierra;
    }

    public void setnIdUsuarioCierra(Long nIdUsuarioCierra) {
        this.nIdUsuarioCierra = nIdUsuarioCierra;
    }

    public Long getnIdUsuarioAutoriza() {
        return nIdUsuarioAutoriza;
    }

    public void setnIdUsuarioAutoriza(Long nIdUsuarioAutoriza) {
        this.nIdUsuarioAutoriza = nIdUsuarioAutoriza;
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

    public TcBodega getTcBodega() {
        return tcBodega;
    }

    public void setTcBodega(TcBodega tcBodega) {
        this.tcBodega = tcBodega;
    }

    public TcAnaquel getTcAnaquel() {
        return tcAnaquel;
    }

    public void setTcAnaquel(TcAnaquel tcAnaquel) {
        this.tcAnaquel = tcAnaquel;
    }

    public TcNivel getTcNivel() {
        return tcNivel;
    }

    public void setTcNivel(TcNivel tcNivel) {
        this.tcNivel = tcNivel;
    }

    public TcUsuario getTcUsuarioResponsable() {
        return tcUsuarioResponsable;
    }

    public void setTcUsuarioResponsable(TcUsuario tcUsuarioResponsable) {
        this.tcUsuarioResponsable = tcUsuarioResponsable;
    }

    public TcUsuario getTcUsuarioCierra() {
        return tcUsuarioCierra;
    }

    public void setTcUsuarioCierra(TcUsuario tcUsuarioCierra) {
        this.tcUsuarioCierra = tcUsuarioCierra;
    }

    public TcUsuario getTcUsuarioAutoriza() {
        return tcUsuarioAutoriza;
    }

    public void setTcUsuarioAutoriza(TcUsuario tcUsuarioAutoriza) {
        this.tcUsuarioAutoriza = tcUsuarioAutoriza;
    }
}
