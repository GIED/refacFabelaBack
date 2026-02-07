package com.refacFabela.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

/**
 * Entidad que representa el historial de cambios de conteo en el inventario por ubicación.
 * Auditoría completa de modificaciones (anterior vs nuevo, usuario, fecha, motivo).
 */
@Entity
@Table(name = "tw_inventario_ubicacion_det_hist")
@NamedQuery(name = "TwInventarioUbicacionDetHist.findAll", query = "SELECT t FROM TwInventarioUbicacionDetHist t")
public class TwInventarioUbicacionDetHist implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long nId;

    @Column(name = "n_idinventario", nullable = false)
    private Long nIdInventario;

    @Column(name = "n_idproducto", nullable = false)
    private Long nIdProducto;

    @Column(name = "d_evento", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dEvento;

    @Column(name = "n_idusuario", nullable = false)
    private Long nIdUsuario;

    @Column(name = "n_contada_anterior")
    private Integer nContadaAnterior;

    @Column(name = "n_contada_nueva")
    private Integer nContadaNueva;

    @Column(name = "s_motivo", length = 200)
    private String sMotivo;

    // Relaciones ManyToOne
    @ManyToOne
    @JoinColumn(name = "n_idusuario", insertable = false, updatable = false)
    private TcUsuario tcUsuario;

    public TwInventarioUbicacionDetHist() {
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

    public LocalDateTime getdEvento() {
        return dEvento;
    }

    public void setdEvento(LocalDateTime dEvento) {
        this.dEvento = dEvento;
    }

    public Long getnIdUsuario() {
        return nIdUsuario;
    }

    public void setnIdUsuario(Long nIdUsuario) {
        this.nIdUsuario = nIdUsuario;
    }

    public Integer getnContadaAnterior() {
        return nContadaAnterior;
    }

    public void setnContadaAnterior(Integer nContadaAnterior) {
        this.nContadaAnterior = nContadaAnterior;
    }

    public Integer getnContadaNueva() {
        return nContadaNueva;
    }

    public void setnContadaNueva(Integer nContadaNueva) {
        this.nContadaNueva = nContadaNueva;
    }

    public String getsMotivo() {
        return sMotivo;
    }

    public void setsMotivo(String sMotivo) {
        this.sMotivo = sMotivo;
    }

    public TcUsuario getTcUsuario() {
        return tcUsuario;
    }

    public void setTcUsuario(TcUsuario tcUsuario) {
        this.tcUsuario = tcUsuario;
    }
}
