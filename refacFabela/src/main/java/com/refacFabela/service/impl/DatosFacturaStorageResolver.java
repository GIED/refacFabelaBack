package com.refacFabela.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TcDatosFactura;

@Service
public class DatosFacturaStorageResolver {

	private static final String SUBDIRECTORIO_PDF = "pdf";
	private static final String SUBDIRECTORIO_XML = "xml";

	private final String rutaComprobantesDefault;

	public DatosFacturaStorageResolver(
			@Value("${ventas.internet.ruta-comprobantes:${VENTAS_INTERNET_RUTA_COMPROBANTES:comprobantesInternet}}") String rutaComprobantesDefault) {
		this.rutaComprobantesDefault = rutaComprobantesDefault;
	}

	public String resolveRutaPdf(TcDatosFactura datosFactura) {
		String rutaPdf = datosFactura != null ? datosFactura.getsRutaPdf() : null;
		if (hasText(rutaPdf)) {
			return ensureTrailingSeparator(rutaPdf);
		}
		return appendSubdirectory(resolveRutaRaiz(datosFactura), SUBDIRECTORIO_PDF);
	}

	public String resolveRutaXml(TcDatosFactura datosFactura) {
		String rutaXml = datosFactura != null ? datosFactura.getsRutaXml() : null;
		if (hasText(rutaXml)) {
			return ensureTrailingSeparator(rutaXml);
		}
		return appendSubdirectory(resolveRutaRaiz(datosFactura), SUBDIRECTORIO_XML);
	}

	public String resolveRutaRaiz(TcDatosFactura datosFactura) {
		String rutaRaiz = datosFactura != null ? datosFactura.getsRutaRaiz() : null;
		if (hasText(rutaRaiz)) {
			return ensureTrailingSeparator(rutaRaiz);
		}

		String rutaDerivada = deriveRootFromSubdirectory(datosFactura != null ? datosFactura.getsRutaPdf() : null,
				SUBDIRECTORIO_PDF);
		if (!hasText(rutaDerivada)) {
			rutaDerivada = deriveRootFromSubdirectory(datosFactura != null ? datosFactura.getsRutaXml() : null,
					SUBDIRECTORIO_XML);
		}

		if (hasText(rutaDerivada)) {
			return ensureTrailingSeparator(rutaDerivada);
		}

		return ensureTrailingSeparator(rutaComprobantesDefault);
	}

	private String deriveRootFromSubdirectory(String ruta, String subdirectory) {
		if (!hasText(ruta) || !hasText(subdirectory)) {
			return null;
		}

		String trimmed = trimTrailingSeparator(ruta.trim());
		String suffixForward = "/" + subdirectory;
		String suffixBackward = "\\" + subdirectory;

		if (trimmed.endsWith(suffixForward)) {
			return trimmed.substring(0, trimmed.length() - suffixForward.length());
		}

		if (trimmed.endsWith(suffixBackward)) {
			return trimmed.substring(0, trimmed.length() - suffixBackward.length());
		}

		return null;
	}

	private String appendSubdirectory(String rutaBase, String subdirectory) {
		if (!hasText(rutaBase) || !hasText(subdirectory)) {
			return null;
		}
		String normalizedBase = ensureTrailingSeparator(rutaBase);
		String separator = detectSeparator(normalizedBase);
		return normalizedBase + subdirectory + separator;
	}

	private String ensureTrailingSeparator(String ruta) {
		if (!hasText(ruta)) {
			return null;
		}
		String trimmed = ruta.trim();
		if (trimmed.endsWith("/") || trimmed.endsWith("\\")) {
			return trimmed;
		}
		return trimmed + detectSeparator(trimmed);
	}

	private String trimTrailingSeparator(String ruta) {
		if (!hasText(ruta)) {
			return null;
		}
		String trimmed = ruta;
		while (trimmed.endsWith("/") || trimmed.endsWith("\\")) {
			trimmed = trimmed.substring(0, trimmed.length() - 1);
		}
		return trimmed;
	}

	private String detectSeparator(String ruta) {
		return ruta != null && ruta.contains("\\") ? "\\" : "/";
	}

	private boolean hasText(String value) {
		return value != null && !value.trim().isEmpty();
	}
}