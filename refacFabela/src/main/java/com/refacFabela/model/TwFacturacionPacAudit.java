package com.refacFabela.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "tw_facturacion_pac_audit")
public class TwFacturacionPacAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "d_fecha_registro")
	private LocalDateTime dFechaRegistro;

	@Column(name = "s_fecha_operacion")
	private String sFechaOperacion;

	@Column(name = "s_operacion")
	private String sOperacion;

	@Column(name = "s_proveedor")
	private String sProveedor;

	@Column(name = "s_endpoint")
	private String sEndpoint;

	@Column(name = "s_metodo_http")
	private String sMetodoHttp;

	@Lob
	@Column(name = "s_request_json", columnDefinition = "LONGTEXT")
	private String sRequestJson;

	@Lob
	@Column(name = "s_response_json", columnDefinition = "LONGTEXT")
	private String sResponseJson;

	@Column(name = "n_http_status")
	private Integer nHttpStatus;

	@Column(name = "b_success")
	private Boolean bSuccess;

	@Column(name = "s_error_code")
	private String sErrorCode;

	@Column(name = "s_error_message")
	private String sErrorMessage;

	@Column(name = "s_correlation_id")
	private String sCorrelationId;

	@Column(name = "s_uuid_relacionado")
	private String sUuidRelacionado;

	@Column(name = "n_razon_social_id")
	private Long nRazonSocialId;

	@Column(name = "n_id_venta")
	private Long nIdVenta;

	@Column(name = "s_rfc_emisor")
	private String sRfcEmisor;

	@Column(name = "s_usuario")
	private String sUsuario;

	@Lob
	@Column(name = "s_metadata_json", columnDefinition = "LONGTEXT")
	private String sMetadataJson;

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public LocalDateTime getdFechaRegistro() {
		return dFechaRegistro;
	}

	public void setdFechaRegistro(LocalDateTime dFechaRegistro) {
		this.dFechaRegistro = dFechaRegistro;
	}

	public String getsFechaOperacion() {
		return sFechaOperacion;
	}

	public void setsFechaOperacion(String sFechaOperacion) {
		this.sFechaOperacion = sFechaOperacion;
	}

	public String getsOperacion() {
		return sOperacion;
	}

	public void setsOperacion(String sOperacion) {
		this.sOperacion = sOperacion;
	}

	public String getsProveedor() {
		return sProveedor;
	}

	public void setsProveedor(String sProveedor) {
		this.sProveedor = sProveedor;
	}

	public String getsEndpoint() {
		return sEndpoint;
	}

	public void setsEndpoint(String sEndpoint) {
		this.sEndpoint = sEndpoint;
	}

	public String getsMetodoHttp() {
		return sMetodoHttp;
	}

	public void setsMetodoHttp(String sMetodoHttp) {
		this.sMetodoHttp = sMetodoHttp;
	}

	public String getsRequestJson() {
		return sRequestJson;
	}

	public void setsRequestJson(String sRequestJson) {
		this.sRequestJson = sRequestJson;
	}

	public String getsResponseJson() {
		return sResponseJson;
	}

	public void setsResponseJson(String sResponseJson) {
		this.sResponseJson = sResponseJson;
	}

	public Integer getnHttpStatus() {
		return nHttpStatus;
	}

	public void setnHttpStatus(Integer nHttpStatus) {
		this.nHttpStatus = nHttpStatus;
	}

	public Boolean getbSuccess() {
		return bSuccess;
	}

	public void setbSuccess(Boolean bSuccess) {
		this.bSuccess = bSuccess;
	}

	public String getsErrorCode() {
		return sErrorCode;
	}

	public void setsErrorCode(String sErrorCode) {
		this.sErrorCode = sErrorCode;
	}

	public String getsErrorMessage() {
		return sErrorMessage;
	}

	public void setsErrorMessage(String sErrorMessage) {
		this.sErrorMessage = sErrorMessage;
	}

	public String getsCorrelationId() {
		return sCorrelationId;
	}

	public void setsCorrelationId(String sCorrelationId) {
		this.sCorrelationId = sCorrelationId;
	}

	public String getsUuidRelacionado() {
		return sUuidRelacionado;
	}

	public void setsUuidRelacionado(String sUuidRelacionado) {
		this.sUuidRelacionado = sUuidRelacionado;
	}

	public Long getnRazonSocialId() {
		return nRazonSocialId;
	}

	public void setnRazonSocialId(Long nRazonSocialId) {
		this.nRazonSocialId = nRazonSocialId;
	}

	public Long getnIdVenta() {
		return nIdVenta;
	}

	public void setnIdVenta(Long nIdVenta) {
		this.nIdVenta = nIdVenta;
	}

	public String getsRfcEmisor() {
		return sRfcEmisor;
	}

	public void setsRfcEmisor(String sRfcEmisor) {
		this.sRfcEmisor = sRfcEmisor;
	}

	public String getsUsuario() {
		return sUsuario;
	}

	public void setsUsuario(String sUsuario) {
		this.sUsuario = sUsuario;
	}

	public String getsMetadataJson() {
		return sMetadataJson;
	}

	public void setsMetadataJson(String sMetadataJson) {
		this.sMetadataJson = sMetadataJson;
	}
}