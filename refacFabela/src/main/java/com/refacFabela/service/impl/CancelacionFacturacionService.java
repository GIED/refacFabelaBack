package com.refacFabela.service.impl;

import java.math.BigDecimal;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.refacFabela.dto.AuditoriaPacDto;
import com.refacFabela.dto.CancelacionRequest;
import com.refacFabela.dto.CancelacionResponse;
import com.refacFabela.exception.FacturacionException;
import com.refacFabela.service.PacFacturacionClient;
import com.refacFabela.service.PacFacturacionMapper;
import com.refacFabela.model.TcDatosFactura;
import com.refacFabela.model.TwFacturacion;
import com.refacFabela.model.TwVenta;
import com.refacFabela.repository.FacturaRepository;
import com.refacFabela.repository.TcDatosFacturaRepository;
import com.refacFabela.repository.VentasProductoRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.utils.DateTimeUtil;

@Service
public class CancelacionFacturacionService {

	private static final Logger logger = LogManager.getLogger(CancelacionFacturacionService.class);

	private static final Pattern UUID_FISCAL_PATTERN = Pattern.compile(
			"(?i)^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");

	private final VentasRepository ventasRepository;
	private final FacturaRepository facturaRepository;
	private final TcDatosFacturaRepository tcDatosFacturaRepository;
	private final VentasProductoRepository ventasProductoRepository;
	private final PacFacturacionClient pacFacturacionClient;
	private final PacFacturacionMapper pacFacturacionMapper;
	private final AuditoriaPacService auditoriaPacService;
	private final FacturacionMontoHelper facturacionMontoHelper;
	private final DatosFacturaStorageResolver datosFacturaStorageResolver;

	public CancelacionFacturacionService(VentasRepository ventasRepository,
			FacturaRepository facturaRepository,
			TcDatosFacturaRepository tcDatosFacturaRepository,
			VentasProductoRepository ventasProductoRepository,
			PacFacturacionClient pacFacturacionClient,
			PacFacturacionMapper pacFacturacionMapper,
			AuditoriaPacService auditoriaPacService,
			FacturacionMontoHelper facturacionMontoHelper,
			DatosFacturaStorageResolver datosFacturaStorageResolver) {
		this.ventasRepository = ventasRepository;
		this.facturaRepository = facturaRepository;
		this.tcDatosFacturaRepository = tcDatosFacturaRepository;
		this.ventasProductoRepository = ventasProductoRepository;
		this.pacFacturacionClient = pacFacturacionClient;
		this.pacFacturacionMapper = pacFacturacionMapper;
		this.auditoriaPacService = auditoriaPacService;
		this.facturacionMontoHelper = facturacionMontoHelper;
		this.datosFacturaStorageResolver = datosFacturaStorageResolver;
	}

	public CancelacionResponse cancelarVenta(Long idVenta, String motivo, String folioFiscalSustitucion) {
		TwVenta venta = ventasRepository.findBynId(idVenta);
		if (venta == null) {
			throw new FacturacionException("La venta no existe.");
		}
		if (venta.getnIdFacturacion() == null || venta.getnIdFacturacion().longValue() <= 0L) {
			throw new FacturacionException("La venta no cuenta con una factura timbrada para cancelar.");
		}

		TwFacturacion facturacion = facturaRepository.findById(venta.getnIdFacturacion()).orElse(null);
		if (facturacion == null || facturacion.getsUuid() == null || facturacion.getsUuid().trim().isEmpty()) {
			throw new FacturacionException("No existe el UUID de la factura a cancelar.");
		}

		String motivoNormalizado = motivo != null ? motivo.trim() : null;
		String uuidSustitucion = normalizeUuidFiscal(folioFiscalSustitucion);
		validateMotivo(motivoNormalizado, uuidSustitucion, facturacion.getsUuid());
		TcDatosFactura datosFactura = tcDatosFacturaRepository.obtenerDatos(facturacion.getnIdDatoFactura());
		if (datosFactura == null) {
			throw new FacturacionException("No existe configuración fiscal de la factura a cancelar.");
		}
		validateCsdConfiguration(datosFactura);

		CancelacionRequest request = buildRequest(venta, facturacion, datosFactura, motivoNormalizado, uuidSustitucion);
		String correlationId = UUID.randomUUID().toString();
		CancelacionResponse response = null;
		String errorCode = null;
		String errorMessage = null;
		try {
			response = pacFacturacionClient.cancelarCfdi(request);
			if (!Boolean.TRUE.equals(response.getSuccess())) {
				throw new FacturacionException(buildCancelacionFailureMessage(response));
			}
			facturacion.setsEstado("CANCELADA");
			facturaRepository.save(facturacion);
			guardarAcuseCancelacion(datosFactura, venta.getnId(), response.getAcuseBase64());
			registrarAuditoria("cancelacion", correlationId, request, response, null, null);
			return response;
		} catch (Exception e) {
			errorCode = response != null ? response.getCodigoError() : "CANCELACION_ERROR";
			errorMessage = e.getMessage();
			registrarAuditoria("cancelacion", correlationId, request, response, errorCode, errorMessage);
			throw e;
		}
	}

	private CancelacionRequest buildRequest(TwVenta venta, TwFacturacion facturacion, TcDatosFactura datosFactura,
			String motivo, String folioFiscalSustitucion) {
		CancelacionRequest request = new CancelacionRequest();
		request.setRazonSocialId(datosFactura.getnId());
		request.setRfcEmisor(datosFactura.getsRfcEmisor());
		request.setRfcReceptor(venta.getTcCliente().getsRfc());
		request.setUuid(facturacion.getsUuid());
		request.setMotivo(motivo);
		request.setFolioFiscalSustitucion("01".equals(motivo) ? folioFiscalSustitucion : null);
		request.setSello(resolveSello(facturacion));

		Map<String, Object> metadata = new HashMap<String, Object>();
		metadata.put("certificado", datosFactura.getsCertificado());
		metadata.put("llavePrivada", datosFactura.getsRutaKey());
		metadata.put("password", datosFactura.getsPasswordKey());
		metadata.put("passwordKey", datosFactura.getsPasswordKey());
		request.setMetadata(metadata);
		request.setTotal(facturacionMontoHelper.calcularTotal(ventasProductoRepository.findBynIdVenta(venta.getnId())));
		return request;
	}

	private void validateMotivo(String motivo, String folioFiscalSustitucion, String uuidFacturaOriginal) {
		if (motivo == null || motivo.trim().isEmpty()) {
			throw new FacturacionException("El motivo de cancelación SAT es obligatorio.");
		}
		if (!("01".equals(motivo) || "02".equals(motivo) || "03".equals(motivo) || "04".equals(motivo))) {
			throw new FacturacionException("Motivo de cancelación SAT inválido.");
		}
		if ("01".equals(motivo)) {
			if (folioFiscalSustitucion == null || folioFiscalSustitucion.trim().isEmpty()) {
				throw new FacturacionException("El UUID del CFDI sustituto es obligatorio para el motivo 01.");
			}
			if (!UUID_FISCAL_PATTERN.matcher(folioFiscalSustitucion).matches()) {
				throw new FacturacionException("El UUID del CFDI sustituto no tiene un formato fiscal válido.");
			}
			if (uuidFacturaOriginal != null && uuidFacturaOriginal.trim().equalsIgnoreCase(folioFiscalSustitucion)) {
				throw new FacturacionException("El UUID sustituto debe ser diferente del UUID de la factura que se cancela.");
			}
		}
	}

	private String normalizeUuidFiscal(String uuid) {
		if (uuid == null || uuid.trim().isEmpty()) {
			return null;
		}
		return uuid.trim().toUpperCase(Locale.ROOT);
	}

	private void validateCsdConfiguration(TcDatosFactura datosFactura) {
		if (isBlank(datosFactura.getsCertificado()) || isBlank(datosFactura.getsRutaKey())
				|| isBlank(datosFactura.getsPasswordKey())) {
			throw new FacturacionException("No se puede cancelar porque el CSD de la razón social no está configurado completo.");
		}
	}

	private boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}

	private void guardarAcuseCancelacion(TcDatosFactura datosFactura, Long idVenta, String acuseBase64) {
		byte[] acuse = decodeBase64OrPlain(acuseBase64);
		String rutaRaiz = datosFacturaStorageResolver.resolveRutaRaiz(datosFactura);
		if (acuse == null || acuse.length == 0 || idVenta == null || isBlank(rutaRaiz)) {
			return;
		}

		Path rutaAcuse = Paths.get(rutaRaiz, "cancelaciones", "xml", idVenta + ".xml");
		try {
			if (rutaAcuse.getParent() != null) {
				Files.createDirectories(rutaAcuse.getParent());
			}
			Files.write(rutaAcuse, acuse, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			logger.warn("No fue posible guardar el acuse de cancelación de la venta {} en {}", idVenta, rutaAcuse,
					e);
		}
	}

	private byte[] decodeBase64OrPlain(String value) {
		if (isBlank(value)) {
			return null;
		}
		String trimmed = value.trim();
		if (trimmed.startsWith("<")) {
			return trimmed.getBytes(StandardCharsets.UTF_8);
		}
		try {
			return Base64.getDecoder().decode(trimmed);
		} catch (IllegalArgumentException e) {
			return trimmed.getBytes(StandardCharsets.UTF_8);
		}
	}

	private String buildCancelacionFailureMessage(CancelacionResponse response) {
		String providerMessage = response != null ? response.getMensajeError() : null;
		String code = response != null ? response.getCodigoError() : null;
		if (providerMessage == null || providerMessage.trim().isEmpty()) {
			providerMessage = "FacturoPorTi rechazó la cancelación.";
		}
		if ("002".equals(code) || providerMessage.toUpperCase(Locale.ROOT).contains("CSD")) {
			return providerMessage
					+ " Verifica que el certificado, llave privada y contraseña CSD vigentes correspondan al RFC emisor.";
		}
		return providerMessage;
	}

	private void registrarAuditoria(String operacion, String correlationId, CancelacionRequest request,
			CancelacionResponse response, String errorCode, String errorMessage) {
		AuditoriaPacDto auditoria = new AuditoriaPacDto();
		auditoria.setOperacion(operacion);
		auditoria.setProveedor("facturoporti");
		auditoria.setMetodoHttp("POST");
		auditoria.setEndpoint("/servicios/cancelar/csd");
		auditoria.setRequest(pacFacturacionMapper.toSanitizedFacturoPorTiCancelacionPayload(request));
		auditoria.setResponse(response);
		auditoria.setSuccess(response != null ? response.getSuccess() : Boolean.FALSE);
		auditoria.setErrorCode(errorCode != null ? errorCode : (response != null ? response.getCodigoError() : null));
		auditoria.setErrorMessage(errorMessage != null ? errorMessage : (response != null ? response.getMensajeError() : null));
		auditoria.setCorrelationId(correlationId);
		auditoria.setUuidRelacionado(request != null ? request.getUuid() : null);
		auditoria.setRazonSocialId(request != null ? request.getRazonSocialId() : null);
		auditoria.setVentaId(resolveVentaId(request));
		auditoria.setRfcEmisor(request != null ? request.getRfcEmisor() : null);
		auditoria.setFecha(DateTimeUtil.obtenerHoraExactaDeMexico().toString());
		auditoria.setUsuario(auditoriaPacService.resolveUsuarioActual());
		auditoriaPacService.registrar(auditoria);
	}

	private String resolveSello(TwFacturacion facturacion) {
		if (facturacion == null || facturacion.getS_selloCfd() == null) {
			return null;
		}
		String sello = facturacion.getS_selloCfd().trim();
		return sello.length() > 8 ? sello.substring(sello.length() - 8) : sello;
	}

	private Long resolveVentaId(CancelacionRequest request) {
		if (request == null || request.getUuid() == null) {
			return null;
		}
		TwFacturacion facturacion = facturaRepository.findAll().stream()
				.filter(item -> request.getUuid().equals(item.getsUuid()))
				.findFirst()
				.orElse(null);
		return facturacion != null ? facturacion.getN_idVenta() : null;
	}
}
