
package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "tc_proveedores")
@NamedQuery(name = "TcProveedore.findAll", query = "SELECT t FROM TcProveedore t")
public class TcProveedore implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long nId;

    @Column(name = "n_estatus")
    private int nEstatus;

    @Column(name = "n_idusuario")
    private int nIdusuario;

    @Column(name = "s_direccion")
    private String sDireccion;

    @Column(name = "s_razon_social")
    private String sRazonSocial;

    @Column(name = "s_rfc")
    private String sRfc;

    @Column(name = "s_telefono")
    private String sTelefono;
    
    @Column(name = "n_id_tipo_proveedor")
    private Long nIdTipoProveedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "n_id_tipo_proveedor", referencedColumnName = "n_id", updatable = false, insertable = false)
    private TcTipoProveedor tcTipoProveedor;

    // Getters and Setters
    public Long getnId() {
        return nId;
    }

    public void setnId(Long nId) {
        this.nId = nId;
    }

    public int getnEstatus() {
        return nEstatus;
    }

    public void setnEstatus(int nEstatus) {
        this.nEstatus = nEstatus;
    }

    public int getnIdusuario() {
        return nIdusuario;
    }

    public void setnIdusuario(int nIdusuario) {
        this.nIdusuario = nIdusuario;
    }

    public String getsDireccion() {
        return sDireccion;
    }

    public void setsDireccion(String sDireccion) {
        this.sDireccion = sDireccion;
    }

    public String getsRazonSocial() {
        return sRazonSocial;
    }

    public void setsRazonSocial(String sRazonSocial) {
        this.sRazonSocial = sRazonSocial;
    }

    public String getsRfc() {
        return sRfc;
    }

    public void setsRfc(String sRfc) {
        this.sRfc = sRfc;
    }

    public String getsTelefono() {
        return sTelefono;
    }

    public void setsTelefono(String sTelefono) {
        this.sTelefono = sTelefono;
    }

    public Long getnIdTipoProveedor() {
        return nIdTipoProveedor;
    }

    public void setnIdTipoProveedor(Long nIdTipoProveedor) {
        this.nIdTipoProveedor = nIdTipoProveedor;
    }

    public TcTipoProveedor getTcTipoProveedor() {
        return tcTipoProveedor;
    }

    public void setTcTipoProveedor(TcTipoProveedor tcTipoProveedor) {
        this.tcTipoProveedor = tcTipoProveedor;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "TcProveedore [nId=" + nId + ", nEstatus=" + nEstatus + ", nIdusuario=" + nIdusuario + ", sDireccion="
                + sDireccion + ", sRazonSocial=" + sRazonSocial + ", sRfc=" + sRfc + ", sTelefono=" + sTelefono
                + ", nIdTipoProveedor=" + nIdTipoProveedor + ", tcTipoProveedor=" + tcTipoProveedor + "]";
    }
}
