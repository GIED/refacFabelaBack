package com.refacFabela.facturacionv2.dto.internal;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class CfdiTimbradoRequest {

	private String serie;
	private String folio;
	private LocalDateTime fecha;
	private String formaPago;
	private String condicionesDePago;
	private BigDecimal subtotal;
	private BigDecimal descuento;
	private String moneda;
	private BigDecimal tipoCambio;
	private BigDecimal total;
	private String tipoDeComprobante;
	private String exportacion;
	private String metodoPago;
	private String lugarExpedicion;
	private String confirmacion;
	private Long razonSocialId;
	private EmisorDto emisor;
	private ReceptorDto receptor;
	private List<CfdiRelacionadoDto> cfdiRelacionados;
	private List<ConceptoDto> conceptos;
	private ImpuestosDto impuestos;
	private InformacionGlobalDto informacionGlobal;
	private DatosCorreoDto correo;
	private Map<String, Object> addenda;
	private Map<String, Object> metadata;

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

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getCondicionesDePago() {
		return condicionesDePago;
	}

	public void setCondicionesDePago(String condicionesDePago) {
		this.condicionesDePago = condicionesDePago;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
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

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getTipoDeComprobante() {
		return tipoDeComprobante;
	}

	public void setTipoDeComprobante(String tipoDeComprobante) {
		this.tipoDeComprobante = tipoDeComprobante;
	}

	public String getExportacion() {
		return exportacion;
	}

	public void setExportacion(String exportacion) {
		this.exportacion = exportacion;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public String getLugarExpedicion() {
		return lugarExpedicion;
	}

	public void setLugarExpedicion(String lugarExpedicion) {
		this.lugarExpedicion = lugarExpedicion;
	}

	public String getConfirmacion() {
		return confirmacion;
	}

	public void setConfirmacion(String confirmacion) {
		this.confirmacion = confirmacion;
	}

	public Long getRazonSocialId() {
		return razonSocialId;
	}

	public void setRazonSocialId(Long razonSocialId) {
		this.razonSocialId = razonSocialId;
	}

	public EmisorDto getEmisor() {
		return emisor;
	}

	public void setEmisor(EmisorDto emisor) {
		this.emisor = emisor;
	}

	public ReceptorDto getReceptor() {
		return receptor;
	}

	public void setReceptor(ReceptorDto receptor) {
		this.receptor = receptor;
	}

	public List<CfdiRelacionadoDto> getCfdiRelacionados() {
		return cfdiRelacionados;
	}

	public void setCfdiRelacionados(List<CfdiRelacionadoDto> cfdiRelacionados) {
		this.cfdiRelacionados = cfdiRelacionados;
	}

	public List<ConceptoDto> getConceptos() {
		return conceptos;
	}

	public void setConceptos(List<ConceptoDto> conceptos) {
		this.conceptos = conceptos;
	}

	public ImpuestosDto getImpuestos() {
		return impuestos;
	}

	public void setImpuestos(ImpuestosDto impuestos) {
		this.impuestos = impuestos;
	}

	public InformacionGlobalDto getInformacionGlobal() {
		return informacionGlobal;
	}

	public void setInformacionGlobal(InformacionGlobalDto informacionGlobal) {
		this.informacionGlobal = informacionGlobal;
	}

	public DatosCorreoDto getCorreo() {
		return correo;
	}

	public void setCorreo(DatosCorreoDto correo) {
		this.correo = correo;
	}

	public Map<String, Object> getAddenda() {
		return addenda;
	}

	public void setAddenda(Map<String, Object> addenda) {
		this.addenda = addenda;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}

	public static class EmisorDto {

		private String rfc;
		private String nombre;
		private String regimenFiscal;
		private String codigoPostalExpedicion;

		public String getRfc() {
			return rfc;
		}

		public void setRfc(String rfc) {
			this.rfc = rfc;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getRegimenFiscal() {
			return regimenFiscal;
		}

		public void setRegimenFiscal(String regimenFiscal) {
			this.regimenFiscal = regimenFiscal;
		}

		public String getCodigoPostalExpedicion() {
			return codigoPostalExpedicion;
		}

		public void setCodigoPostalExpedicion(String codigoPostalExpedicion) {
			this.codigoPostalExpedicion = codigoPostalExpedicion;
		}
	}

	public static class ReceptorDto {

		private String rfc;
		private String nombre;
		private String domicilioFiscalReceptor;
		private String residenciaFiscal;
		private String numRegIdTrib;
		private String regimenFiscalReceptor;
		private String usoCfdi;
		private String email;

		public String getRfc() {
			return rfc;
		}

		public void setRfc(String rfc) {
			this.rfc = rfc;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getDomicilioFiscalReceptor() {
			return domicilioFiscalReceptor;
		}

		public void setDomicilioFiscalReceptor(String domicilioFiscalReceptor) {
			this.domicilioFiscalReceptor = domicilioFiscalReceptor;
		}

		public String getResidenciaFiscal() {
			return residenciaFiscal;
		}

		public void setResidenciaFiscal(String residenciaFiscal) {
			this.residenciaFiscal = residenciaFiscal;
		}

		public String getNumRegIdTrib() {
			return numRegIdTrib;
		}

		public void setNumRegIdTrib(String numRegIdTrib) {
			this.numRegIdTrib = numRegIdTrib;
		}

		public String getRegimenFiscalReceptor() {
			return regimenFiscalReceptor;
		}

		public void setRegimenFiscalReceptor(String regimenFiscalReceptor) {
			this.regimenFiscalReceptor = regimenFiscalReceptor;
		}

		public String getUsoCfdi() {
			return usoCfdi;
		}

		public void setUsoCfdi(String usoCfdi) {
			this.usoCfdi = usoCfdi;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
	}

	public static class CfdiRelacionadoDto {

		private String tipoRelacion;
		private List<String> uuids;

		public String getTipoRelacion() {
			return tipoRelacion;
		}

		public void setTipoRelacion(String tipoRelacion) {
			this.tipoRelacion = tipoRelacion;
		}

		public List<String> getUuids() {
			return uuids;
		}

		public void setUuids(List<String> uuids) {
			this.uuids = uuids;
		}
	}

	public static class ConceptoDto {

		private String claveProdServ;
		private String noIdentificacion;
		private BigDecimal cantidad;
		private String claveUnidad;
		private String unidad;
		private String descripcion;
		private BigDecimal valorUnitario;
		private BigDecimal importe;
		private BigDecimal descuento;
		private String objetoImp;
		private List<ImpuestoTrasladoDto> traslados;
		private List<ImpuestoRetencionDto> retenciones;
		private CuentaPredialDto cuentaPredial;
		private List<ParteDto> partes;
		private Map<String, Object> complementoConcepto;

		public String getClaveProdServ() {
			return claveProdServ;
		}

		public void setClaveProdServ(String claveProdServ) {
			this.claveProdServ = claveProdServ;
		}

		public String getNoIdentificacion() {
			return noIdentificacion;
		}

		public void setNoIdentificacion(String noIdentificacion) {
			this.noIdentificacion = noIdentificacion;
		}

		public BigDecimal getCantidad() {
			return cantidad;
		}

		public void setCantidad(BigDecimal cantidad) {
			this.cantidad = cantidad;
		}

		public String getClaveUnidad() {
			return claveUnidad;
		}

		public void setClaveUnidad(String claveUnidad) {
			this.claveUnidad = claveUnidad;
		}

		public String getUnidad() {
			return unidad;
		}

		public void setUnidad(String unidad) {
			this.unidad = unidad;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public BigDecimal getValorUnitario() {
			return valorUnitario;
		}

		public void setValorUnitario(BigDecimal valorUnitario) {
			this.valorUnitario = valorUnitario;
		}

		public BigDecimal getImporte() {
			return importe;
		}

		public void setImporte(BigDecimal importe) {
			this.importe = importe;
		}

		public BigDecimal getDescuento() {
			return descuento;
		}

		public void setDescuento(BigDecimal descuento) {
			this.descuento = descuento;
		}

		public String getObjetoImp() {
			return objetoImp;
		}

		public void setObjetoImp(String objetoImp) {
			this.objetoImp = objetoImp;
		}

		public List<ImpuestoTrasladoDto> getTraslados() {
			return traslados;
		}

		public void setTraslados(List<ImpuestoTrasladoDto> traslados) {
			this.traslados = traslados;
		}

		public List<ImpuestoRetencionDto> getRetenciones() {
			return retenciones;
		}

		public void setRetenciones(List<ImpuestoRetencionDto> retenciones) {
			this.retenciones = retenciones;
		}

		public CuentaPredialDto getCuentaPredial() {
			return cuentaPredial;
		}

		public void setCuentaPredial(CuentaPredialDto cuentaPredial) {
			this.cuentaPredial = cuentaPredial;
		}

		public List<ParteDto> getPartes() {
			return partes;
		}

		public void setPartes(List<ParteDto> partes) {
			this.partes = partes;
		}

		public Map<String, Object> getComplementoConcepto() {
			return complementoConcepto;
		}

		public void setComplementoConcepto(Map<String, Object> complementoConcepto) {
			this.complementoConcepto = complementoConcepto;
		}
	}

	public static class ImpuestoTrasladoDto {

		private String base;
		private String impuesto;
		private String tipoFactor;
		private BigDecimal tasaOCuota;
		private BigDecimal importe;

		public String getBase() {
			return base;
		}

		public void setBase(String base) {
			this.base = base;
		}

		public String getImpuesto() {
			return impuesto;
		}

		public void setImpuesto(String impuesto) {
			this.impuesto = impuesto;
		}

		public String getTipoFactor() {
			return tipoFactor;
		}

		public void setTipoFactor(String tipoFactor) {
			this.tipoFactor = tipoFactor;
		}

		public BigDecimal getTasaOCuota() {
			return tasaOCuota;
		}

		public void setTasaOCuota(BigDecimal tasaOCuota) {
			this.tasaOCuota = tasaOCuota;
		}

		public BigDecimal getImporte() {
			return importe;
		}

		public void setImporte(BigDecimal importe) {
			this.importe = importe;
		}
	}

	public static class ImpuestoRetencionDto {

		private String base;
		private String impuesto;
		private String tipoFactor;
		private BigDecimal tasaOCuota;
		private BigDecimal importe;

		public String getBase() {
			return base;
		}

		public void setBase(String base) {
			this.base = base;
		}

		public String getImpuesto() {
			return impuesto;
		}

		public void setImpuesto(String impuesto) {
			this.impuesto = impuesto;
		}

		public String getTipoFactor() {
			return tipoFactor;
		}

		public void setTipoFactor(String tipoFactor) {
			this.tipoFactor = tipoFactor;
		}

		public BigDecimal getTasaOCuota() {
			return tasaOCuota;
		}

		public void setTasaOCuota(BigDecimal tasaOCuota) {
			this.tasaOCuota = tasaOCuota;
		}

		public BigDecimal getImporte() {
			return importe;
		}

		public void setImporte(BigDecimal importe) {
			this.importe = importe;
		}
	}

	public static class CuentaPredialDto {

		private String numero;

		public String getNumero() {
			return numero;
		}

		public void setNumero(String numero) {
			this.numero = numero;
		}
	}

	public static class ParteDto {

		private String claveProdServ;
		private String noIdentificacion;
		private BigDecimal cantidad;
		private String descripcion;
		private BigDecimal valorUnitario;
		private BigDecimal importe;

		public String getClaveProdServ() {
			return claveProdServ;
		}

		public void setClaveProdServ(String claveProdServ) {
			this.claveProdServ = claveProdServ;
		}

		public String getNoIdentificacion() {
			return noIdentificacion;
		}

		public void setNoIdentificacion(String noIdentificacion) {
			this.noIdentificacion = noIdentificacion;
		}

		public BigDecimal getCantidad() {
			return cantidad;
		}

		public void setCantidad(BigDecimal cantidad) {
			this.cantidad = cantidad;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public BigDecimal getValorUnitario() {
			return valorUnitario;
		}

		public void setValorUnitario(BigDecimal valorUnitario) {
			this.valorUnitario = valorUnitario;
		}

		public BigDecimal getImporte() {
			return importe;
		}

		public void setImporte(BigDecimal importe) {
			this.importe = importe;
		}
	}

	public static class ImpuestosDto {

		private BigDecimal totalImpuestosRetenidos;
		private BigDecimal totalImpuestosTrasladados;
		private List<ImpuestoTrasladoDto> traslados;
		private List<ImpuestoRetencionDto> retenciones;

		public BigDecimal getTotalImpuestosRetenidos() {
			return totalImpuestosRetenidos;
		}

		public void setTotalImpuestosRetenidos(BigDecimal totalImpuestosRetenidos) {
			this.totalImpuestosRetenidos = totalImpuestosRetenidos;
		}

		public BigDecimal getTotalImpuestosTrasladados() {
			return totalImpuestosTrasladados;
		}

		public void setTotalImpuestosTrasladados(BigDecimal totalImpuestosTrasladados) {
			this.totalImpuestosTrasladados = totalImpuestosTrasladados;
		}

		public List<ImpuestoTrasladoDto> getTraslados() {
			return traslados;
		}

		public void setTraslados(List<ImpuestoTrasladoDto> traslados) {
			this.traslados = traslados;
		}

		public List<ImpuestoRetencionDto> getRetenciones() {
			return retenciones;
		}

		public void setRetenciones(List<ImpuestoRetencionDto> retenciones) {
			this.retenciones = retenciones;
		}
	}

	public static class InformacionGlobalDto {

		private String periodicidad;
		private String meses;
		private String anio;

		public String getPeriodicidad() {
			return periodicidad;
		}

		public void setPeriodicidad(String periodicidad) {
			this.periodicidad = periodicidad;
		}

		public String getMeses() {
			return meses;
		}

		public void setMeses(String meses) {
			this.meses = meses;
		}

		public String getAnio() {
			return anio;
		}

		public void setAnio(String anio) {
			this.anio = anio;
		}
	}

	public static class DatosCorreoDto {

		private String para;
		private String cc;
		private String bcc;
		private String asunto;
		private String mensaje;

		public String getPara() {
			return para;
		}

		public void setPara(String para) {
			this.para = para;
		}

		public String getCc() {
			return cc;
		}

		public void setCc(String cc) {
			this.cc = cc;
		}

		public String getBcc() {
			return bcc;
		}

		public void setBcc(String bcc) {
			this.bcc = bcc;
		}

		public String getAsunto() {
			return asunto;
		}

		public void setAsunto(String asunto) {
			this.asunto = asunto;
		}

		public String getMensaje() {
			return mensaje;
		}

		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}
	}
}