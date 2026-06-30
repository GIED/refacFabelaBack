package com.refacFabela.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.text.Normalizer;

import org.springframework.stereotype.Component;

import com.refacFabela.dto.provider.facturoporti.FacturoPorTiCancelacionRequest;
import com.refacFabela.dto.provider.facturoporti.FacturoPorTiCancelacionResponse;
import com.refacFabela.dto.provider.facturoporti.FacturoPorTiTimbradoRequest;
import com.refacFabela.dto.provider.facturoporti.FacturoPorTiTimbradoResponse;
import com.refacFabela.dto.CancelacionRequest;
import com.refacFabela.dto.CancelacionResponse;
import com.refacFabela.dto.CfdiTimbradoRequest;
import com.refacFabela.dto.ComplementoPagoRequest;
import com.refacFabela.dto.TimbradoResponse;

@Component
public class PacFacturacionMapper {

	private static final DateTimeFormatter CFDI_FECHA_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	private static final String CAPITAL_REGIME_SUFFIX_PATTERN = "(?i),?\\s*(?:S\\.?\\s*A\\.?(?:\\s*P\\.?\\s*I\\.?|\\s*B\\.?|\\s*S\\.?)?\\s*DE\\s*C\\.?\\s*V\\.?|SOCIEDAD\\s+ANONIMA(?:\\s+PROMOTORA\\s+DE\\s+INVERSION|\\s+BURSATIL|\\s+SIMPLIFICADA)?\\s+DE\\s+CAPITAL\\s+VARIABLE|S\\.?\\s*DE\\s*R\\.?\\s*L\\.?(?:\\s*DE\\s*C\\.?\\s*V\\.?)?|S\\.?\\s*P\\.?\\s*R\\.?\\s*DE\\s*R\\.?\\s*L\\.?(?:\\s*DE\\s*C\\.?\\s*V\\.?)?)(\\s*)$";

	public FacturoPorTiTimbradoRequest toFacturoPorTiTimbradoRequest(CfdiTimbradoRequest request) {
		FacturoPorTiTimbradoRequest providerRequest = new FacturoPorTiTimbradoRequest();
		Map<String, Object> payload = new LinkedHashMap<String, Object>();
		Map<String, Object> datosGenerales = new LinkedHashMap<String, Object>();
		Map<String, Object> encabezado = new LinkedHashMap<String, Object>();
		Map<String, Object> emisor = new LinkedHashMap<String, Object>();
		Map<String, Object> receptor = new LinkedHashMap<String, Object>();
		List<Map<String, Object>> conceptos = new ArrayList<Map<String, Object>>();

		// TODO_VALIDAR_CON_FACTUROPORTI: align payload shape with official examples zip when available.
		datosGenerales.put("Version", "4.0");
		datosGenerales.put("CSD", normalizeBase64Value(request.getMetadata() != null ? request.getMetadata().get("certificado") : null));
		datosGenerales.put("LlavePrivada", normalizeBase64Value(request.getMetadata() != null ? request.getMetadata().get("llavePrivada") : null));
		datosGenerales.put("CSDPassword", request.getMetadata() != null ? request.getMetadata().get("passwordKey") : null);
		datosGenerales.put("GeneraPDF", Boolean.TRUE);
		datosGenerales.put("CFDI", "Factura");
		datosGenerales.put("TipoCFDI", "Ingreso");
		datosGenerales.put("OpcionDecimales", Integer.valueOf(2));
		datosGenerales.put("NumeroDecimales", Integer.valueOf(2));
		datosGenerales.put("EnviaEmail", Boolean.FALSE);
		datosGenerales.put("Logotipo", normalizeBase64Value(request.getMetadata() != null ? request.getMetadata().get("logo") : null));

		encabezado.put("Fecha", formatCfdiFecha(request.getFecha()));
		encabezado.put("Serie", request.getSerie());
		encabezado.put("Folio", request.getFolio());
		encabezado.put("MetodoPago", request.getMetodoPago());
		encabezado.put("FormaPago", request.getFormaPago());
		encabezado.put("Moneda", request.getMoneda());
		encabezado.put("LugarExpedicion", request.getLugarExpedicion());
		encabezado.put("SubTotal", request.getSubtotal());
		encabezado.put("Total", request.getTotal());

		if (request.getEmisor() != null) {
			emisor.put("RFC", request.getEmisor().getRfc());
			emisor.put("NombreRazonSocial", request.getEmisor().getNombre());
			emisor.put("RegimenFiscal", request.getEmisor().getRegimenFiscal());
		}

		if (request.getReceptor() != null) {
			receptor.put("RFC", request.getReceptor().getRfc());
			receptor.put("NombreRazonSocial", normalizeLegalName(request.getReceptor().getNombre()));
			receptor.put("UsoCFDI", request.getReceptor().getUsoCfdi());
			receptor.put("RegimenFiscal", request.getReceptor().getRegimenFiscalReceptor());
			Map<String, Object> direccion = new LinkedHashMap<String, Object>();
			direccion.put("CodigoPostal", request.getReceptor().getDomicilioFiscalReceptor());
			receptor.put("Direccion", direccion);
		}

		if (request.getConceptos() != null) {
			for (CfdiTimbradoRequest.ConceptoDto conceptoDto : request.getConceptos()) {
				Map<String, Object> concepto = new LinkedHashMap<String, Object>();
				concepto.put("Cantidad", conceptoDto.getCantidad());
				concepto.put("CodigoUnidad", conceptoDto.getClaveUnidad());
				concepto.put("Unidad", conceptoDto.getUnidad());
				concepto.put("CodigoProducto", conceptoDto.getClaveProdServ());
				concepto.put("Producto", conceptoDto.getDescripcion());
				concepto.put("PrecioUnitario", conceptoDto.getValorUnitario());
				concepto.put("Importe", conceptoDto.getImporte());
				concepto.put("ObjetoDeImpuesto", conceptoDto.getObjetoImp());
				List<Map<String, Object>> impuestos = new ArrayList<Map<String, Object>>();
				if (conceptoDto.getTraslados() != null) {
					for (CfdiTimbradoRequest.ImpuestoTrasladoDto trasladoDto : conceptoDto.getTraslados()) {
						Map<String, Object> impuesto = new LinkedHashMap<String, Object>();
						impuesto.put("TipoImpuesto", Integer.valueOf(1));
						impuesto.put("Impuesto", Integer.valueOf(2));
						impuesto.put("Factor", Integer.valueOf(1));
						impuesto.put("Base", trasladoDto.getBase());
						impuesto.put("Tasa", trasladoDto.getTasaOCuota());
						impuesto.put("ImpuestoImporte", trasladoDto.getImporte());
						impuestos.add(impuesto);
					}
				}
				concepto.put("Impuestos", impuestos);
				conceptos.add(concepto);
			}
		}

		encabezado.put("Emisor", emisor);
		encabezado.put("Receptor", receptor);
		payload.put("DatosGenerales", datosGenerales);
		payload.put("Encabezado", encabezado);
		payload.put("Conceptos", conceptos);
		if (request.getAddenda() != null) {
			payload.put("Adenda", request.getAddenda());
		}
		if (request.getMetadata() != null) {
			payload.put("AdicionalData", request.getMetadata());
		}
		providerRequest.setPayload(payload);
		return providerRequest;
	}

	public Map<String, Object> toSanitizedFacturoPorTiTimbradoPayload(CfdiTimbradoRequest request) {
		FacturoPorTiTimbradoRequest providerRequest = toFacturoPorTiTimbradoRequest(request);
		return sanitizePayload(providerRequest != null ? providerRequest.getPayload() : null);
	}

	public FacturoPorTiTimbradoRequest toFacturoPorTiComplementoPagoRequest(ComplementoPagoRequest request) {
		FacturoPorTiTimbradoRequest providerRequest = new FacturoPorTiTimbradoRequest();
		Map<String, Object> payload = new LinkedHashMap<String, Object>();
		Map<String, Object> datosGenerales = new LinkedHashMap<String, Object>();
		Map<String, Object> encabezado = new LinkedHashMap<String, Object>();
		Map<String, Object> emisor = new LinkedHashMap<String, Object>();
		Map<String, Object> receptor = new LinkedHashMap<String, Object>();
		Map<String, Object> complemento = new LinkedHashMap<String, Object>();
		Map<String, Object> pagosV20 = new LinkedHashMap<String, Object>();
		List<Map<String, Object>> pagos = new ArrayList<Map<String, Object>>();

		datosGenerales.put("Version", "4.0");
		datosGenerales.put("CSD", normalizeBase64Value(request.getMetadata() != null ? request.getMetadata().get("certificado") : null));
		datosGenerales.put("LlavePrivada", normalizeBase64Value(request.getMetadata() != null ? request.getMetadata().get("llavePrivada") : null));
		datosGenerales.put("CSDPassword", request.getMetadata() != null ? request.getMetadata().get("passwordKey") : null);
		datosGenerales.put("GeneraPDF", Boolean.TRUE);
		datosGenerales.put("CFDI", "Pago");
		datosGenerales.put("TipoCFDI", "Pago");
		datosGenerales.put("OpcionDecimales", Integer.valueOf(2));
		datosGenerales.put("NumeroDecimales", Integer.valueOf(2));
		datosGenerales.put("EnviaEmail", request.getCorreo() != null && request.getCorreo().getPara() != null);
		datosGenerales.put("ReceptorEmail", request.getCorreo() != null ? request.getCorreo().getPara() : null);
		datosGenerales.put("ReceptorEmailCC", request.getCorreo() != null ? request.getCorreo().getCc() : null);
		datosGenerales.put("ReceptorEmailCCO", request.getCorreo() != null ? request.getCorreo().getBcc() : null);
		datosGenerales.put("EmailMensaje", request.getCorreo() != null ? request.getCorreo().getMensaje() : null);
		datosGenerales.put("Logotipo", normalizeBase64Value(request.getMetadata() != null ? request.getMetadata().get("logo") : null));

		emisor.put("RFC", request.getRfcEmisor());
		emisor.put("NombreRazonSocial", request.getMetadata() != null ? request.getMetadata().get("nombreEmisor") : null);
		emisor.put("RegimenFiscal", request.getMetadata() != null ? request.getMetadata().get("regimenFiscal") : null);
		if (request.getMetadata() != null && request.getMetadata().get("codigoPostalEmisor") != null) {
			Map<String, Object> direccionEmisor = new LinkedHashMap<String, Object>();
			direccionEmisor.put("CodigoPostal", request.getMetadata().get("codigoPostalEmisor"));
			emisor.put("Direccion", direccionEmisor);
		}

		receptor.put("RFC", request.getRfcReceptor());
		receptor.put("NombreRazonSocial", normalizeLegalName(request.getMetadata() != null ? request.getMetadata().get("nombreReceptor") : null));
		receptor.put("UsoCFDI", request.getMetadata() != null ? request.getMetadata().get("usoCfdi") : null);
		receptor.put("RegimenFiscal", request.getMetadata() != null ? request.getMetadata().get("regimenFiscalReceptor") : null);
		Map<String, Object> direccionReceptor = new LinkedHashMap<String, Object>();
		direccionReceptor.put("CodigoPostal", request.getMetadata() != null ? request.getMetadata().get("codigoPostalReceptor") : null);
		receptor.put("Direccion", direccionReceptor);

		encabezado.put("Emisor", emisor);
		encabezado.put("Receptor", receptor);
		encabezado.put("Fecha", formatCfdiFecha(request.getFechaPago()));
		encabezado.put("Serie", request.getMetadata() != null ? request.getMetadata().get("serie") : null);
		encabezado.put("Folio", request.getMetadata() != null ? request.getMetadata().get("folio") : null);
		encabezado.put("MetodoPago", null);
		encabezado.put("FormaPago", null);
		encabezado.put("Moneda", null);
		encabezado.put("LugarExpedicion", request.getMetadata() != null ? request.getMetadata().get("codigoPostalEmisor") : null);
		encabezado.put("SubTotal", BigDecimal.ZERO);
		encabezado.put("Total", BigDecimal.ZERO);

		BigDecimal totalTrasladosBaseIva16 = BigDecimal.ZERO;
		BigDecimal totalTrasladosImpuestoIva16 = BigDecimal.ZERO;
		BigDecimal montoTotalPagos = BigDecimal.ZERO;

		if (request.getPagos() != null) {
			for (ComplementoPagoRequest.PagoDto pagoDto : request.getPagos()) {
				Map<String, Object> pago = new LinkedHashMap<String, Object>();
				pago.put("FechaPago", request.getFechaPago() != null ? request.getFechaPago().toString() : null);
				pago.put("FormaPago", pagoDto.getFormaPago());
				pago.put("Moneda", pagoDto.getMoneda());
				pago.put("TipoCambio", pagoDto.getTipoCambio());

				List<Map<String, Object>> documentosRelacionados = new ArrayList<Map<String, Object>>();
				if (pagoDto.getDocumentosRelacionados() != null) {
					for (ComplementoPagoRequest.DocumentoRelacionadoPagoDto documentoDto : pagoDto.getDocumentosRelacionados()) {
						Map<String, Object> documento = new LinkedHashMap<String, Object>();
						documento.put("IdDocumento", documentoDto.getIdDocumento());
						documento.put("Serie", documentoDto.getSerie());
						documento.put("Folio", documentoDto.getFolio());
						documento.put("Moneda", documentoDto.getMonedaDr());
						documento.put("Equivalencia", documentoDto.getEquivalenciaDr());
						documento.put("NumeroParcialidad", documentoDto.getNumParcialidad());
						documento.put("ImporteSaldoAnterior", documentoDto.getImpSaldoAnt());
						documento.put("ImportePagado", documentoDto.getImpPagado());
						documento.put("ImporteSaldoInsoluto", documentoDto.getImpSaldoInsoluto());
						documento.put("ObjetoDeImpuesto", "02");

						Map<String, Object> impuestosDocumento = new LinkedHashMap<String, Object>();
						List<Map<String, Object>> trasladosDocumento = new ArrayList<Map<String, Object>>();
						Map<String, Object> trasladoDocumento = new LinkedHashMap<String, Object>();
						BigDecimal base = calculateBase(documentoDto.getImpPagado());
						BigDecimal iva = documentoDto.getImpPagado() != null ? documentoDto.getImpPagado().subtract(base) : BigDecimal.ZERO;
						trasladoDocumento.put("Impuesto", Integer.valueOf(2));
						trasladoDocumento.put("Factor", Integer.valueOf(1));
						trasladoDocumento.put("Base", base);
						trasladoDocumento.put("Tasa", new BigDecimal("0.160000"));
						trasladoDocumento.put("Importe", iva);
						trasladosDocumento.add(trasladoDocumento);
						impuestosDocumento.put("Trasladados", trasladosDocumento);
						documento.put("Impuestos", impuestosDocumento);
						documentosRelacionados.add(documento);
						totalTrasladosBaseIva16 = totalTrasladosBaseIva16.add(base);
						totalTrasladosImpuestoIva16 = totalTrasladosImpuestoIva16.add(iva);
					}
				}
				pago.put("DocumentosRelacionados", documentosRelacionados);

				Map<String, Object> impuestosPago = new LinkedHashMap<String, Object>();
				List<Map<String, Object>> trasladosPago = new ArrayList<Map<String, Object>>();
				Map<String, Object> trasladoPago = new LinkedHashMap<String, Object>();
				BigDecimal basePago = calculateBase(pagoDto.getMonto());
				BigDecimal ivaPago = pagoDto.getMonto() != null ? pagoDto.getMonto().subtract(basePago) : BigDecimal.ZERO;
				trasladoPago.put("Impuesto", Integer.valueOf(2));
				trasladoPago.put("Factor", Integer.valueOf(1));
				trasladoPago.put("Base", basePago);
				trasladoPago.put("Tasa", new BigDecimal("0.160000"));
				trasladoPago.put("Importe", ivaPago);
				trasladosPago.add(trasladoPago);
				impuestosPago.put("Trasladados", trasladosPago);
				pago.put("Impuestos", impuestosPago);

				pagos.add(pago);
				montoTotalPagos = montoTotalPagos.add(pagoDto.getMonto() != null ? pagoDto.getMonto() : BigDecimal.ZERO);
			}
		}

		Map<String, Object> totales = new LinkedHashMap<String, Object>();
		totales.put("TotalTrasladosBaseIVA16", totalTrasladosBaseIva16);
		totales.put("TotalTrasladosImpuestoIVA16", totalTrasladosImpuestoIva16);
		totales.put("MontoTotalPagos", montoTotalPagos);

		pagosV20.put("Pagos", pagos);
		pagosV20.put("Totales", totales);
		complemento.put("TipoComplemento", Integer.valueOf(28));
		complemento.put("PagosV20", pagosV20);

		payload.put("DatosGenerales", datosGenerales);
		payload.put("Encabezado", encabezado);
		payload.put("Conceptos", new ArrayList<Object>());
		payload.put("Complemento", complemento);
		if (request.getMetadata() != null) {
			payload.put("AdicionalData", request.getMetadata());
		}
		providerRequest.setPayload(payload);
		return providerRequest;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> sanitizePayload(Map<String, Object> payload) {
		if (payload == null) {
			return null;
		}
		return (Map<String, Object>) sanitizeValue(payload, null);
	}

	private String formatCfdiFecha(LocalDateTime fecha) {
		if (fecha == null) {
			return null;
		}
		return fecha.withNano(0).format(CFDI_FECHA_FORMATTER);
	}

	public String normalizeLegalName(Object rawValue) {
		if (rawValue == null) {
			return null;
		}

		String value = rawValue.toString().trim();
		if (value.isEmpty()) {
			return null;
		}

		String normalized = value.replaceAll(CAPITAL_REGIME_SUFFIX_PATTERN, "");
		normalized = normalized.replaceAll("\\s{2,}", " ").trim();
		return normalized.isEmpty() ? value : normalized;
	}

	public List<String> buildLegalNameCandidates(Object rawValue) {
		LinkedHashSet<String> candidates = new LinkedHashSet<String>();
		String raw = extractNameValue(rawValue);
		if (raw == null) {
			return new ArrayList<String>();
		}
		addCandidate(candidates, normalizeLegalName(raw));
		addCandidate(candidates, normalizeLegalNameStrict(raw, false));
		addCandidate(candidates, normalizeLegalNameStrict(raw, true));
		return new ArrayList<String>(candidates);
	}

	private String normalizeLegalNameStrict(String rawValue, boolean upperCase) {
		String normalized = normalizeLegalName(rawValue);
		if (normalized == null) {
			return null;
		}
		normalized = Normalizer.normalize(normalized, Normalizer.Form.NFD).replaceAll("\\p{M}+", "");
		normalized = normalized.replaceAll("[\"'`´.,;:()]+", " ");
		normalized = normalized.replaceAll(CAPITAL_REGIME_SUFFIX_PATTERN, "");
		normalized = normalized.replaceAll("\\s{2,}", " ").trim();
		if (upperCase) {
			normalized = normalized.toUpperCase(Locale.ROOT);
		}
		return normalized;
	}

	private String extractNameValue(Object rawValue) {
		if (rawValue == null) {
			return null;
		}
		String value = rawValue.toString().trim();
		return value.isEmpty() ? null : value;
	}

	private void addCandidate(LinkedHashSet<String> candidates, String value) {
		if (value == null) {
			return;
		}
		String trimmed = value.trim();
		if (!trimmed.isEmpty()) {
			candidates.add(trimmed);
		}
	}

	@SuppressWarnings("unchecked")
	private Object sanitizeValue(Object value, String fieldName) {
		if (value == null) {
			return null;
		}

		if (fieldName != null && isSensitiveField(fieldName)) {
			return "[REDACTED]";
		}

		if (fieldName != null && isLogoField(fieldName) && value instanceof String) {
			String logoValue = ((String) value).trim();
			if (logoValue.isEmpty()) {
				return "[LOGO_EMPTY]";
			}
			return "[LOGO length=" + logoValue.length() + "]";
		}

		if (value instanceof Map) {
			Map<String, Object> sanitized = new LinkedHashMap<String, Object>();
			for (Map.Entry<?, ?> entry : ((Map<?, ?>) value).entrySet()) {
				String key = entry.getKey() != null ? entry.getKey().toString() : null;
				sanitized.put(key, sanitizeValue(entry.getValue(), key));
			}
			return sanitized;
		}

		if (value instanceof List) {
			List<Object> sanitized = new ArrayList<Object>();
			for (Object item : (List<Object>) value) {
				sanitized.add(sanitizeValue(item, fieldName));
			}
			return sanitized;
		}

		return value;
	}

	private boolean isSensitiveField(String fieldName) {
		return "CSD".equalsIgnoreCase(fieldName)
				|| "LlavePrivada".equalsIgnoreCase(fieldName)
				|| "CSDPassword".equalsIgnoreCase(fieldName)
				|| "certificado".equalsIgnoreCase(fieldName)
				|| "llavePrivada".equalsIgnoreCase(fieldName)
				|| "passwordKey".equalsIgnoreCase(fieldName)
				|| "password".equalsIgnoreCase(fieldName)
				|| "token".equalsIgnoreCase(fieldName)
				|| "sTokenApi".equalsIgnoreCase(fieldName);
	}

	private boolean isLogoField(String fieldName) {
		return "Logotipo".equalsIgnoreCase(fieldName) || "logo".equalsIgnoreCase(fieldName);
	}

	private String normalizeBase64Value(Object rawValue) {
		if (rawValue == null) {
			return null;
		}

		String value = rawValue.toString().trim();
		if (value.isEmpty()) {
			return null;
		}

		try {
			Path filePath = Paths.get(value);
			if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
				byte[] fileBytes = Files.readAllBytes(filePath);
				return Base64.getEncoder().encodeToString(fileBytes);
			}
		} catch (Exception ignored) {
			// If it is not a valid path, treat it as inline content.
		}

		String cleaned = value;
		if (cleaned.regionMatches(true, 0, "data:", 0, 5)) {
			int base64MarkerIndex = cleaned.toLowerCase(Locale.ROOT).indexOf("base64,");
			if (base64MarkerIndex >= 0) {
				cleaned = cleaned.substring(base64MarkerIndex + "base64,".length());
			}
		}

		cleaned = cleaned
				.replace("-----BEGIN CERTIFICATE-----", "")
				.replace("-----END CERTIFICATE-----", "")
				.replace("-----BEGIN PRIVATE KEY-----", "")
				.replace("-----END PRIVATE KEY-----", "")
				.replace("-----BEGIN RSA PRIVATE KEY-----", "")
				.replace("-----END RSA PRIVATE KEY-----", "")
				.replaceAll("\\s+", "");

		try {
			byte[] decoded = Base64.getDecoder().decode(cleaned);
			return Base64.getEncoder().encodeToString(decoded);
		} catch (IllegalArgumentException e) {
			return cleaned;
		}
	}

	private BigDecimal calculateBase(BigDecimal total) {
		if (total == null) {
			return BigDecimal.ZERO;
		}
		return total.divide(new BigDecimal("1.16"), 2, BigDecimal.ROUND_HALF_UP);
	}

	public TimbradoResponse toTimbradoResponse(FacturoPorTiTimbradoResponse response, CfdiTimbradoRequest request) {
		TimbradoResponse internal = new TimbradoResponse();
		internal.setSuccess(response != null ? response.getSuccess() : Boolean.FALSE);
		internal.setUuid(response != null ? response.getUuid() : null);
		internal.setSerie(response != null && response.getSerie() != null ? response.getSerie() : request.getSerie());
		internal.setFolio(response != null && response.getFolio() != null ? response.getFolio() : request.getFolio());
		internal.setRazonSocialId(request.getRazonSocialId());
		internal.setRfcEmisor(request.getEmisor() != null ? request.getEmisor().getRfc() : null);
		internal.setRfcReceptor(request.getReceptor() != null ? request.getReceptor().getRfc() : null);
		internal.setTotal(request.getTotal());
		internal.setMoneda(request.getMoneda());
		internal.setTipoComprobante(request.getTipoDeComprobante());
		internal.setFechaTimbrado(response != null ? response.getFechaTimbrado() : null);
		internal.setSelloCfd(response != null ? response.getSelloCfd() : null);
		internal.setSelloSat(response != null ? response.getSelloSat() : null);
		internal.setNoCertificadoSat(response != null ? response.getNoCertificadoSat() : null);
		internal.setNoCertificadoEmisor(response != null ? response.getNoCertificadoEmisor() : null);
		internal.setCadenaOriginalComplementoSat(response != null ? response.getCadenaOriginal() : null);
		internal.setXmlBase64(response != null ? response.getXmlBase64() : null);
		internal.setPdfBase64(response != null ? response.getPdfBase64() : null);
		internal.setQrBase64(response != null ? response.getQrBase64() : null);
		internal.setCodigoError(response != null ? response.getCodigoError() : null);
		internal.setMensajeError(response != null ? response.getMensajeError() : null);
		internal.setRawResponse(response != null ? response.getRaw() : null);
		internal.setEstatus(response != null && Boolean.TRUE.equals(response.getSuccess()) ? "TIMBRADO" : "ERROR");
		System.err.println(internal);
		return internal;
	}

	public FacturoPorTiCancelacionRequest toFacturoPorTiCancelacionRequest(CancelacionRequest request) {
		FacturoPorTiCancelacionRequest providerRequest = new FacturoPorTiCancelacionRequest();
		providerRequest.setRfcEmisor(request.getRfcEmisor());
		providerRequest.setRfcReceptor(request.getRfcReceptor());
		providerRequest.setUuid(request.getUuid());
		providerRequest.setTotal(request.getTotal());
		providerRequest.setMotivo(request.getMotivo());
		providerRequest.setFolioFiscalSustitucion(request.getFolioFiscalSustitucion());
		providerRequest.setSello(request.getSello());
		if (request.getMetadata() != null) {
			providerRequest.setCertificado(stringValue(request.getMetadata().get("certificado")));
			providerRequest.setLlavePrivada(stringValue(request.getMetadata().get("llavePrivada")));
			String password = stringValue(request.getMetadata().get("password"));
			if (password == null) {
				password = stringValue(request.getMetadata().get("passwordKey"));
			}
			providerRequest.setPassword(password);
		}
		providerRequest.setAdicionalData(request.getMetadata());
		return providerRequest;
	}

	public CancelacionResponse toCancelacionResponse(FacturoPorTiCancelacionResponse response) {
		CancelacionResponse internal = new CancelacionResponse();
		internal.setSuccess(response != null ? response.getSuccess() : Boolean.FALSE);
		internal.setUuid(response != null ? response.getUuid() : null);
		internal.setEstatus(response != null ? response.getEstatus() : null);
		internal.setAcuseBase64(response != null ? response.getAcuseBase64() : null);
		internal.setCodigoError(response != null ? response.getCodigoError() : null);
		internal.setMensajeError(response != null ? response.getMensajeError() : null);
		internal.setRawResponse(response != null ? response.getRaw() : null);
		return internal;
	}

	public Map<String, Object> toMap(Object rawResponse) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("raw", rawResponse);
		return map;
	}

	private String stringValue(Object value) {
		return value != null ? String.valueOf(value) : null;
	}
}
