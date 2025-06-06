package com.refacFabela.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tc_clientes")
@NamedQuery(name = "TcCliente.findAll", query = "SELECT t FROM TcCliente t")
public class TcCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "d_fechaCredito")
	private LocalDateTime d_fechaCredito;

	@Column(name = "n_estatus")
	private int nEstatus;

	private Long n_idUsuarioCredito;

	private BigDecimal n_limiteCredito;

	@Column(name = "s_correo")
	private String sCorreo;

	@Column(name = "s_direccion")
	private String sDireccion;

	@Column(name = "s_razon_social")
	private String sRazonSocial;

	@Column(name = "s_rfc")
	private String sRfc;

	@Column(name = "s_telefono")
	private String sTelefono;

	@Column(name = "s_clave")
	private String sClave;
	
	@Column(name="n_idUsuario")
	private Long nIdUsuario;
	
	@Column(name="n_cp")
	private Long nCp;
	
	@Column(name="n_idRegimenFiscal")
	private Integer nIdRegimenFiscal;
	
	@Column(name="n_descuento")
	private Boolean nDescuento;
	
	@Column(name="n_datos_validados")
	private Boolean nDatosValidados;
	
	@Column(name="n_id_dato_factura")
	private Long nIdDatoFactura;

	// bi-directional many-to-one association to TcUsuario
	@ManyToOne
	@JoinColumn(name = "n_idUsuario", insertable=false, updatable=false)
	private TcUsuario tcUsuario;
	@ManyToOne
	@JoinColumn(name = "n_idRegimenFiscal", insertable=false, updatable=false)
	private TcRegimenFiscal tcRegimenFiscal;
	
	@ManyToOne
	@JoinColumn(name = "n_cp", insertable=false, updatable=false)
	private TcCp tcCp;
	
	public TcCliente() {
	}
	
	public Long getnIdDatoFactura() {
		return nIdDatoFactura;
	}
	public void setnIdDatoFactura(Long nIdDatoFactura) {
		this.nIdDatoFactura = nIdDatoFactura;
	}

	public Integer getnIdRegimenFiscal() {
		return nIdRegimenFiscal;
	}

	public void setnIdRegimenFiscal(Integer nIdRegimenFiscal) {
		this.nIdRegimenFiscal = nIdRegimenFiscal;
	}


	public TcRegimenFiscal getTcRegimenFiscal() {
		return tcRegimenFiscal;
	}


	public void setTcRegimenFiscal(TcRegimenFiscal tcRegimenFiscal) {
		this.tcRegimenFiscal = tcRegimenFiscal;
	}


	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	

	public LocalDateTime getD_fechaCredito() {
		return d_fechaCredito;
	}

	public void setD_fechaCredito(LocalDateTime d_fechaCredito) {
		this.d_fechaCredito = d_fechaCredito;
	}

	public void setN_limiteCredito(BigDecimal n_limiteCredito) {
		this.n_limiteCredito = n_limiteCredito;
	}

	public int getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}

	public Long getN_idUsuarioCredito() {
		return n_idUsuarioCredito;
	}

	public void setN_idUsuarioCredito(Long n_idUsuarioCredito) {
		this.n_idUsuarioCredito = n_idUsuarioCredito;
	}

	

	public BigDecimal getN_limiteCredito() {
		return n_limiteCredito;
	}

	public String getsCorreo() {
		return sCorreo;
	}

	public void setsCorreo(String sCorreo) {
		this.sCorreo = sCorreo;
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

	public String getsClave() {
		return sClave;
	}

	public void setsClave(String sClave) {
		this.sClave = sClave;
	}

	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}

	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}


	public Long getnIdUsuario() {
		return nIdUsuario;
	}


	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}



	public Long getnCp() {
		return nCp;
	}


	public void setnCp(Long nCp) {
		this.nCp = nCp;
	}


	public Boolean getnDescuento() {
		return nDescuento;
	}


	public void setnDescuento(Boolean nDescuento) {
		this.nDescuento = nDescuento;
	}



	public TcCp getTcCp() {
		return tcCp;
	}




	public void setTcCp(TcCp tcCp) {
		this.tcCp = tcCp;
	}

	public Boolean getnDatosValidados() {
		return nDatosValidados;
	}

	public void setnDatosValidados(Boolean nDatosValidados) {
		this.nDatosValidados = nDatosValidados;
	}


	

}