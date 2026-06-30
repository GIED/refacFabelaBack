package com.refacFabela.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tc_datos_factura")
@NamedQuery(name = "TcDatosFactura.findAll", query = "SELECT t FROM TcDatosFactura t")
public class TcDatosFactura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "s_nombre_emisor")
	private String sNombreEmisor;

	@Column(name = "s_rfc_emisor")
	private String sRfcEmisor;

	@Column(name = "s_ruta_key")
	private String sRutaKey;

	@Column(name = "s_logo")
	private String sLogo;

	@Column(name = "s_regimen_fiscal")
	private String sRegimenFiscal;

	@Column(name = "s_codigo_postal")
	private String sCodigoPostal;

	@Column(name = "s_serie")
	private String sSerie;

	@Column(name = "s_version")
	private String sVersion;

	@Column(name = "s_certificado")
	private String sCertificado;

	@Column(name = "s_password_key")
	private String sPasswordKey;

	@Column(name = "s_token_api")
	private String sTokenApi;

	@Column(name = "n_predeterminado")
	private Integer nPredeterminado;

	@Column(name = "n_asignable")
	private Integer nAsignable;

	@Column(name = "n_aplica_retencion_isr")
	private Integer nAplicaRetencionIsr;

	@Column(name = "n_tasa_retencion_isr")
	private BigDecimal nTasaRetencionIsr;

	@Column(name = "n_aplica_retencion_iva")
	private Integer nAplicaRetencionIva;

	@Column(name = "n_tasa_retencion_iva")
	private BigDecimal nTasaRetencionIva;

	@Transient
	private String sUsuarioFolios;

	@Transient
	private String sPasswordFolios;

	@Transient
	private String sEmailUsuario;

	@Transient
	private String sPasswordEmail;

	@Column(name = "s_ruta_xml")
	private String sRutaXml;

	@Column(name = "s_ruta_pdf")
	private String sRutaPdf;

	@Column(name = "s_ruta_raiz")
	private String sRutaRaiz;

	@Transient
	private String sRutaCer;

	@Transient
	private String sRutaCadenaOriginal;

	@Transient
	private String sNoCertificado;

	public TcDatosFactura() {
	}

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public String getsNombreEmisor() {
		return sNombreEmisor;
	}

	public void setsNombreEmisor(String sNombreEmisor) {
		this.sNombreEmisor = sNombreEmisor;
	}

	public String getsRfcEmisor() {
		return sRfcEmisor;
	}

	public void setsRfcEmisor(String sRfcEmisor) {
		this.sRfcEmisor = sRfcEmisor;
	}

	public String getsRutaKey() {
		return sRutaKey;
	}

	public void setsRutaKey(String sRutaKey) {
		this.sRutaKey = sRutaKey;
	}

	public String getsLogo() {
		return sLogo;
	}

	public void setsLogo(String sLogo) {
		this.sLogo = sLogo;
	}

	public String getsRegimenFiscal() {
		return sRegimenFiscal;
	}

	public void setsRegimenFiscal(String sRegimenFiscal) {
		this.sRegimenFiscal = sRegimenFiscal;
	}

	public String getsCodigoPostal() {
		return sCodigoPostal;
	}

	public void setsCodigoPostal(String sCodigoPostal) {
		this.sCodigoPostal = sCodigoPostal;
	}

	public String getsSerie() {
		return sSerie;
	}

	public void setsSerie(String sSerie) {
		this.sSerie = sSerie;
	}

	public String getsVersion() {
		return sVersion;
	}

	public void setsVersion(String sVersion) {
		this.sVersion = sVersion;
	}

	public String getsCertificado() {
		return sCertificado;
	}

	public void setsCertificado(String sCertificado) {
		this.sCertificado = sCertificado;
	}

	public String getsPasswordKey() {
		return sPasswordKey;
	}

	public void setsPasswordKey(String sPasswordKey) {
		this.sPasswordKey = sPasswordKey;
	}

	public String getsTokenApi() {
		return sTokenApi;
	}

	public void setsTokenApi(String sTokenApi) {
		this.sTokenApi = sTokenApi;
	}

	public String getToken() {
		return sTokenApi;
	}

	public void setToken(String token) {
		this.sTokenApi = token;
	}

	public Integer getnPredeterminado() {
		return nPredeterminado;
	}

	public void setnPredeterminado(Integer nPredeterminado) {
		this.nPredeterminado = nPredeterminado;
	}

	public Integer getnAsignable() {
		return nAsignable;
	}

	public void setnAsignable(Integer nAsignable) {
		this.nAsignable = nAsignable;
	}

	public boolean isAsignable() {
		return nAsignable == null || nAsignable.intValue() != 0;
	}

	public Integer getnAplicaRetencionIsr() {
		return nAplicaRetencionIsr;
	}

	public void setnAplicaRetencionIsr(Integer nAplicaRetencionIsr) {
		this.nAplicaRetencionIsr = nAplicaRetencionIsr;
	}

	public BigDecimal getnTasaRetencionIsr() {
		return nTasaRetencionIsr;
	}

	public void setnTasaRetencionIsr(BigDecimal nTasaRetencionIsr) {
		this.nTasaRetencionIsr = nTasaRetencionIsr;
	}

	public Integer getnAplicaRetencionIva() {
		return nAplicaRetencionIva;
	}

	public void setnAplicaRetencionIva(Integer nAplicaRetencionIva) {
		this.nAplicaRetencionIva = nAplicaRetencionIva;
	}

	public BigDecimal getnTasaRetencionIva() {
		return nTasaRetencionIva;
	}

	public void setnTasaRetencionIva(BigDecimal nTasaRetencionIva) {
		this.nTasaRetencionIva = nTasaRetencionIva;
	}

	public String getsUsuarioFolios() {
		return sUsuarioFolios;
	}

	public void setsUsuarioFolios(String sUsuarioFolios) {
		this.sUsuarioFolios = sUsuarioFolios;
	}

	public String getsPasswordFolios() {
		return sPasswordFolios;
	}

	public void setsPasswordFolios(String sPasswordFolios) {
		this.sPasswordFolios = sPasswordFolios;
	}

	public String getsEmailUsuario() {
		return sEmailUsuario;
	}

	public void setsEmailUsuario(String sEmailUsuario) {
		this.sEmailUsuario = sEmailUsuario;
	}

	public String getsPasswordEmail() {
		return sPasswordEmail;
	}

	public void setsPasswordEmail(String sPasswordEmail) {
		this.sPasswordEmail = sPasswordEmail;
	}

	public String getsRutaXml() {
		return sRutaXml;
	}

	public void setsRutaXml(String sRutaXml) {
		this.sRutaXml = sRutaXml;
	}

	public String getsRutaPdf() {
		return sRutaPdf;
	}

	public void setsRutaPdf(String sRutaPdf) {
		this.sRutaPdf = sRutaPdf;
	}

	public String getsRutaRaiz() {
		return sRutaRaiz;
	}

	public void setsRutaRaiz(String sRutaRaiz) {
		this.sRutaRaiz = sRutaRaiz;
	}

	public String getsRutaCer() {
		return sRutaCer;
	}

	public void setsRutaCer(String sRutaCer) {
		this.sRutaCer = sRutaCer;
	}

	public String getsRutaCadenaOriginal() {
		return sRutaCadenaOriginal;
	}

	public void setsRutaCadenaOriginal(String sRutaCadenaOriginal) {
		this.sRutaCadenaOriginal = sRutaCadenaOriginal;
	}

	public String getsNoCertificado() {
		return sNoCertificado;
	}

	public void setsNoCertificado(String sNoCertificado) {
		this.sNoCertificado = sNoCertificado;
	}

	@Override
	public String toString() {
		return "TcDatosFactura [nId=" + nId + ", sNombreEmisor=" + sNombreEmisor + ", sRfcEmisor=" + sRfcEmisor
				+ ", sRegimenFiscal=" + sRegimenFiscal + ", sCodigoPostal=" + sCodigoPostal + ", sSerie=" + sSerie
				+ ", sVersion=" + sVersion + ", nPredeterminado=" + nPredeterminado + ", nAsignable=" + nAsignable
				+ ", nAplicaRetencionIsr=" + nAplicaRetencionIsr + ", nTasaRetencionIsr=" + nTasaRetencionIsr
				+ ", nAplicaRetencionIva=" + nAplicaRetencionIva + ", nTasaRetencionIva=" + nTasaRetencionIva
				+ ", tieneTokenApi=" + (sTokenApi != null && !sTokenApi.trim().isEmpty()) + "]";
	}
}
