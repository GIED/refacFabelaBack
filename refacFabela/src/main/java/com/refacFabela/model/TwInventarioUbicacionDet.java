package com.refacFabela.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

/**
 * Entidad que representa el detalle de un inventario por ubicación.
 * Incluye fotos iniciales (ini), fotos de referencia (ref) actualizables solo para pendientes,
 * y el conteo físico (contada).
 */
@Entity
@Table(name = "tw_inventario_ubicacion_det")
@NamedQuery(name = "TwInventarioUbicacionDet.findAll", query = "SELECT t FROM TwInventarioUbicacionDet t")
public class TwInventarioUbicacionDet implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long nId;

    @Column(name = "n_idinventario", nullable = false)
    private Long nIdInventario;

    @Column(name = "n_idproducto", nullable = false)
    private Long nIdProducto;

    @Column(name = "n_cantidad_teorica_ini", nullable = false)
    private Integer nCantidadTeoricaIni; // Foto inicial del stock, nunca se modifica

    @Column(name = "n_cantidad_teorica_ref", nullable = false)
    private Integer nCantidadTeoricaRef; // Foto de referencia, actualizable solo para pendientes

    @Column(name = "d_ref_actualizada")
    private LocalDateTime dRefActualizada;

    @Column(name = "n_cantidad_contada")
    private Integer nCantidadContada; // NULL = pendiente

    @Column(name = "n_estatus_linea", nullable = false)
    private Integer nEstatusLinea; // 1=PENDIENTE, 2=CONTADO, 3=RECONTAR

    @Column(name = "d_captura")
    private LocalDateTime dCaptura;

    @Column(name = "n_idusuario_captura")
    private Long nIdUsuarioCaptura;

    @Column(name = "s_observacion", length = 300)
    private String sObservacion;

    // Campos de ajuste individual (agregados para flujo de autorización)
    @Column(name = "b_ajustado")
    private Boolean bAjustado;

    @Column(name = "s_motivo_ajuste", length = 500)
    private String sMotivoAjuste;

    @Column(name = "d_fecha_ajuste")
    private LocalDateTime dFechaAjuste;

    @Column(name = "n_id_usuario_ajuste")
    private Integer nIdUsuarioAjuste;

    // Relaciones ManyToOne
    @ManyToOne
    @JoinColumn(name = "n_idinventario", insertable = false, updatable = false)
    private TwInventarioUbicacion twInventarioUbicacion;

    @ManyToOne
    @JoinColumn(name = "n_idproducto", insertable = false, updatable = false)
    private TcProducto tcProducto;

    @ManyToOne
    @JoinColumn(name = "n_idusuario_captura", insertable = false, updatable = false)
    private TcUsuario tcUsuarioCaptura;

    @ManyToOne
    @JoinColumn(name = "n_id_usuario_ajuste", insertable = false, updatable = false)
    private TcUsuario tcUsuarioAjuste;

    public TwInventarioUbicacionDet() {
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

    public String getsObservacion() {
        return sObservacion;
    }

    public void setsObservacion(String sObservacion) {
        this.sObservacion = sObservacion;
    }

    public TwInventarioUbicacion getTwInventarioUbicacion() {
        return twInventarioUbicacion;
    }

    public void setTwInventarioUbicacion(TwInventarioUbicacion twInventarioUbicacion) {
        this.twInventarioUbicacion = twInventarioUbicacion;
    }

    public TcProducto getTcProducto() {
        return tcProducto;
    }

    public void setTcProducto(TcProducto tcProducto) {
        this.tcProducto = tcProducto;
    }

    public TcUsuario getTcUsuarioCaptura() {
        return tcUsuarioCaptura;
    }

    public void setTcUsuarioCaptura(TcUsuario tcUsuarioCaptura) {
        this.tcUsuarioCaptura = tcUsuarioCaptura;
    }

    public Boolean getbAjustado() {
        return bAjustado;
    }

    public void setbAjustado(Boolean bAjustado) {
        this.bAjustado = bAjustado;
    }

    public String getsMotivoAjuste() {
        return sMotivoAjuste;
    }

    public void setsMotivoAjuste(String sMotivoAjuste) {
        this.sMotivoAjuste = sMotivoAjuste;
    }

    public LocalDateTime getdFechaAjuste() {
        return dFechaAjuste;
    }

    public void setdFechaAjuste(LocalDateTime dFechaAjuste) {
        this.dFechaAjuste = dFechaAjuste;
    }

    public Integer getnIdUsuarioAjuste() {
        return nIdUsuarioAjuste;
    }

    public void setnIdUsuarioAjuste(Integer nIdUsuarioAjuste) {
        this.nIdUsuarioAjuste = nIdUsuarioAjuste;
    }

    public TcUsuario getTcUsuarioAjuste() {
        return tcUsuarioAjuste;
    }

    public void setTcUsuarioAjuste(TcUsuario tcUsuarioAjuste) {
        this.tcUsuarioAjuste = tcUsuarioAjuste;
    }
}
