package com.refacFabela.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ComplementoPagoRequest {

	private Long razonSocialId;
	private String uuidFactura;
	private String rfcEmisor;
	private String rfcReceptor;
	private String moneda;
	private LocalDateTime fechaPago;
	private List<PagoDto> pagos;
	private CfdiTimbradoRequest.DatosCorreoDto correo;
	private Map<String, Object> metadata;

	public Long getRazonSocialId() {
		return razonSocialId;
	}

	public void setRazonSocialId(Long razonSocialId) {
		this.razonSocialId = razonSocialId;
	}

	public String getUuidFactura() {
		return uuidFactura;
	}

	public void setUuidFactura(String uuidFactura) {
		this.uuidFactura = uuidFactura;
	}

	public String getRfcEmisor() {
		return rfcEmisor;
	}

	public void setRfcEmisor(String rfcEmisor) {
		this.rfcEmisor = rfcEmisor;
	}

	public String getRfcReceptor() {
		return rfcReceptor;
	}

	public void setRfcReceptor(String rfcReceptor) {
		this.rfcReceptor = rfcReceptor;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public LocalDateTime getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDateTime fechaPago) {
		this.fechaPago = fechaPago;
	}

	public List<PagoDto> getPagos() {
		return pagos;
	}

	public void setPagos(List<PagoDto> pagos) {
		this.pagos = pagos;
	}

	public CfdiTimbradoRequest.DatosCorreoDto getCorreo() {
		return correo;
	}

	public void setCorreo(CfdiTimbradoRequest.DatosCorreoDto correo) {
		this.correo = correo;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}

	public static class PagoDto {

		private BigDecimal monto;
		private String formaPago;
		private String moneda;
		private BigDecimal tipoCambio;
		private String numeroOperacion;
		private List<DocumentoRelacionadoPagoDto> documentosRelacionados;

		public BigDecimal getMonto() {
			return monto;
		}

		public void setMonto(BigDecimal monto) {
			this.monto = monto;
		}

		public String getFormaPago() {
			return formaPago;
		}

		public void setFormaPago(String formaPago) {
			this.formaPago = formaPago;
		}

		public String getMoneda() {
			return moneda;
		}

		public void setMoneda(String moneda) {
			this.moneda = moneda;
		}

		public BigDecimal getTipoCambio() {
			return tipoCambio;
		}

		public void setTipoCambio(BigDecimal tipoCambio) {
			this.tipoCambio = tipoCambio;
		}

		public String getNumeroOperacion() {
			return numeroOperacion;
		}

		public void setNumeroOperacion(String numeroOperacion) {
			this.numeroOperacion = numeroOperacion;
		}

		public List<DocumentoRelacionadoPagoDto> getDocumentosRelacionados() {
			return documentosRelacionados;
		}

		public void setDocumentosRelacionados(List<DocumentoRelacionadoPagoDto> documentosRelacionados) {
			this.documentosRelacionados = documentosRelacionados;
		}
	}

	public static class DocumentoRelacionadoPagoDto {

		private String idDocumento;
		private String serie;
		private String folio;
		private String monedaDr;
		private BigDecimal equivalenciaDr;
		private Integer numParcialidad;
		private BigDecimal impSaldoAnt;
		private BigDecimal impPagado;
		private BigDecimal impSaldoInsoluto;

		public String getIdDocumento() {
			return idDocumento;
		}

		public void setIdDocumento(String idDocumento) {
			this.idDocumento = idDocumento;
		}

		public String getSerie() {
			return serie;
		}

		public void setSerie(String serie) {
			this.serie = serie;
		}

		public String getFolio() {
			return folio;
		}

		public void setFolio(String folio) {
			this.folio = folio;
		}

		public String getMonedaDr() {
			return monedaDr;
		}

		public void setMonedaDr(String monedaDr) {
			this.monedaDr = monedaDr;
		}

		public BigDecimal getEquivalenciaDr() {
			return equivalenciaDr;
		}

		public void setEquivalenciaDr(BigDecimal equivalenciaDr) {
			this.equivalenciaDr = equivalenciaDr;
		}

		public Integer getNumParcialidad() {
			return numParcialidad;
		}

		public void setNumParcialidad(Integer numParcialidad) {
			this.numParcialidad = numParcialidad;
		}

		public BigDecimal getImpSaldoAnt() {
			return impSaldoAnt;
		}

		public void setImpSaldoAnt(BigDecimal impSaldoAnt) {
			this.impSaldoAnt = impSaldoAnt;
		}

		public BigDecimal getImpPagado() {
			return impPagado;
		}

		public void setImpPagado(BigDecimal impPagado) {
			this.impPagado = impPagado;
		}

		public BigDecimal getImpSaldoInsoluto() {
			return impSaldoInsoluto;
		}

		public void setImpSaldoInsoluto(BigDecimal impSaldoInsoluto) {
			this.impSaldoInsoluto = impSaldoInsoluto;
		}
	}
}
