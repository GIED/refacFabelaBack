package com.refacFabela.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "tw_cliente_direccion_envio")
@NamedQuery(name = "TwClienteDireccion.findAll", query = "SELECT d FROM TwClienteDireccion d")
public class TwClienteDireccion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long nId;

    @Column(name = "n_idCliente")
    private Long nIdCliente;

    @Column(name = "s_receptor")
    private String sReceptor;

    @Column(name = "s_tel")
    private String sTel;

    @Column(name = "s_correo")
    private String sCorreo;

    @Column(name = "s_calle")
    private String sCalle;

    @Column(name = "s_num_ext")
    private String sNumExt;

    @Column(name = "s_num_int")
    private String sNumInt;

    @Column(name = "s_colonia")
    private String sColonia;

    @Column(name = "n_cp")
    private Long nCp;

    @Column(name = "s_municipio")
    private String sMunicipio;

    @Column(name = "s_estado")
    private String sEstado;

    @Column(name = "s_referencias")
    private String sReferencias;

    @Column(name = "b_predeterminada")
    private Boolean bPredeterminada = Boolean.FALSE;

    @Column(name = "n_estatus")
    private Integer nEstatus = 1;

    @Column(name = "d_created_at")
    private LocalDateTime dCreatedAt;

    @Column(name = "d_updated_at")
    private LocalDateTime dUpdatedAt;

    /* Relaciones de solo lectura */
    @ManyToOne
    @JoinColumn(name = "n_idCliente", insertable = false, updatable = false)
    private TcCliente tcCliente;

    @ManyToOne
    @JoinColumn(name = "n_cp", insertable = false, updatable = false)
    private TcCp tcCp;

    @PrePersist
    public void prePersist() {
        dCreatedAt = LocalDateTime.now();
        dUpdatedAt = dCreatedAt;
    }

    @PreUpdate
    public void preUpdate() {
        dUpdatedAt = LocalDateTime.now();
    }
    
    /* Getters & Setters */

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Long getnIdCliente() {
		return nIdCliente;
	}

	public void setnIdCliente(Long nIdCliente) {
		this.nIdCliente = nIdCliente;
	}

	public String getsReceptor() {
		return sReceptor;
	}

	public void setsReceptor(String sReceptor) {
		this.sReceptor = sReceptor;
	}

	public String getsTel() {
		return sTel;
	}

	public void setsTel(String sTel) {
		this.sTel = sTel;
	}

	public String getsCorreo() {
		return sCorreo;
	}

	public void setsCorreo(String sCorreo) {
		this.sCorreo = sCorreo;
	}

	public String getsCalle() {
		return sCalle;
	}

	public void setsCalle(String sCalle) {
		this.sCalle = sCalle;
	}

	public String getsNumExt() {
		return sNumExt;
	}

	public void setsNumExt(String sNumExt) {
		this.sNumExt = sNumExt;
	}

	public String getsNumInt() {
		return sNumInt;
	}

	public void setsNumInt(String sNumInt) {
		this.sNumInt = sNumInt;
	}

	public String getsColonia() {
		return sColonia;
	}

	public void setsColonia(String sColonia) {
		this.sColonia = sColonia;
	}

	public Long getnCp() {
		return nCp;
	}

	public void setnCp(Long nCp) {
		this.nCp = nCp;
	}

	public String getsMunicipio() {
		return sMunicipio;
	}

	public void setsMunicipio(String sMunicipio) {
		this.sMunicipio = sMunicipio;
	}

	public String getsEstado() {
		return sEstado;
	}

	public void setsEstado(String sEstado) {
		this.sEstado = sEstado;
	}

	public String getsReferencias() {
		return sReferencias;
	}

	public void setsReferencias(String sReferencias) {
		this.sReferencias = sReferencias;
	}

	public Boolean getbPredeterminada() {
		return bPredeterminada;
	}

	public void setbPredeterminada(Boolean bPredeterminada) {
		this.bPredeterminada = bPredeterminada;
	}

	public Integer getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(Integer nEstatus) {
		this.nEstatus = nEstatus;
	}

	public LocalDateTime getdCreatedAt() {
		return dCreatedAt;
	}

	public void setdCreatedAt(LocalDateTime dCreatedAt) {
		this.dCreatedAt = dCreatedAt;
	}

	public LocalDateTime getdUpdatedAt() {
		return dUpdatedAt;
	}

	public void setdUpdatedAt(LocalDateTime dUpdatedAt) {
		this.dUpdatedAt = dUpdatedAt;
	}

	public TcCliente getTcCliente() {
		return tcCliente;
	}

	public void setTcCliente(TcCliente tcCliente) {
		this.tcCliente = tcCliente;
	}

	public TcCp getTcCp() {
		return tcCp;
	}

	public void setTcCp(TcCp tcCp) {
		this.tcCp = tcCp;
	}

   
    
    
}
