package com.refacFabela.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "tw_facturacion_pac_audit_detalle")
public class TwFacturacionPacAuditDetalle implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "n_id_auditoria")
	private Long nIdAuditoria;

	@Column(name = "s_bloque")
	private String sBloque;

	@Column(name = "s_clave")
	private String sClave;

	@Lob
	@Column(name = "s_valor", columnDefinition = "LONGTEXT")
	private String sValor;

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Long getnIdAuditoria() {
		return nIdAuditoria;
	}

	public void setnIdAuditoria(Long nIdAuditoria) {
		this.nIdAuditoria = nIdAuditoria;
	}

	public String getsBloque() {
		return sBloque;
	}

	public void setsBloque(String sBloque) {
		this.sBloque = sBloque;
	}

	public String getsClave() {
		return sClave;
	}

	public void setsClave(String sClave) {
		this.sClave = sClave;
	}

	public String getsValor() {
		return sValor;
	}

	public void setsValor(String sValor) {
		this.sValor = sValor;
	}
}