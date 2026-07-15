package com.refacFabela.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.refacFabela.dto.FacturaCreditoPendienteDto;
import com.refacFabela.dto.PagoAplicacionAutomaticaRequestDto;
import com.refacFabela.dto.PagoAplicacionManualRequestDto;
import com.refacFabela.dto.PagoAplicacionResultadoDto;
import com.refacFabela.dto.PagoComprobanteCorreoResponseDto;
import com.refacFabela.dto.PagoClienteDetalleDto;
import com.refacFabela.dto.PagoClienteRegistroDto;
import com.refacFabela.service.PagoClienteService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/creditos/pagos")
public class PagoClienteController {

	private static final Logger logger = LogManager.getLogger("errorLogger");

	@Autowired
	private PagoClienteService pagoClienteService;

	@PostMapping("/registrar")
	public ResponseEntity<?> registrarPago(@RequestBody PagoClienteRegistroDto registroDto) {
		try {
			PagoClienteDetalleDto pago = pagoClienteService.registrarPago(registroDto);
			return new ResponseEntity<PagoClienteDetalleDto>(pago, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			logger.warn("Validación al registrar pago cliente: {}", e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error al registrar pago cliente", e);
			return new ResponseEntity<String>("No fue posible registrar el pago del cliente.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{nIdPagoCliente}")
	public ResponseEntity<?> consultarPago(@PathVariable Long nIdPagoCliente) {
		try {
			return ResponseEntity.ok(pagoClienteService.consultarPago(nIdPagoCliente));
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error("Error al consultar pago cliente", e);
			return new ResponseEntity<String>("No fue posible consultar el pago del cliente.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<?> consultarPagosCliente(@RequestParam Long nIdCliente) {
		try {
			List<PagoClienteDetalleDto> pagos = pagoClienteService.consultarPagosCliente(nIdCliente);
			return ResponseEntity.ok(pagos);
		} catch (Exception e) {
			logger.error("Error al consultar pagos del cliente", e);
			return new ResponseEntity<String>("No fue posible consultar los pagos del cliente.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/aplicaciones-venta")
	public ResponseEntity<?> consultarAplicacionesVenta(@RequestParam Long nIdVenta) {
		try {
			return ResponseEntity.ok(pagoClienteService.consultarAplicacionesVenta(nIdVenta));
		} catch (Exception e) {
			logger.error("Error al consultar aplicaciones canónicas de la venta", e);
			return new ResponseEntity<String>("No fue posible consultar las aplicaciones del pago global para la venta.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/facturas-pendientes")
	public ResponseEntity<?> consultarFacturasPendientes(@RequestParam Long nIdCliente, @RequestParam Long nIdDatoFactura) {
		try {
			List<FacturaCreditoPendienteDto> facturas = pagoClienteService.consultarFacturasPendientesCliente(nIdCliente, nIdDatoFactura);
			return ResponseEntity.ok(facturas);
		} catch (Exception e) {
			logger.error("Error al consultar facturas pendientes del cliente", e);
			return new ResponseEntity<String>("No fue posible consultar las facturas pendientes del cliente.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/{nIdPagoCliente}/aplicar/automatico")
	public ResponseEntity<?> aplicarAutomatico(@PathVariable Long nIdPagoCliente,
			@RequestBody(required = false) PagoAplicacionAutomaticaRequestDto requestDto) {
		try {
			PagoAplicacionResultadoDto resultado = pagoClienteService.aplicarPagoAutomatico(nIdPagoCliente, requestDto);
			return ResponseEntity.ok(resultado);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error al aplicar pago automáticamente", e);
			return new ResponseEntity<String>("No fue posible aplicar el pago automáticamente.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/{nIdPagoCliente}/aplicar/manual")
	public ResponseEntity<?> aplicarManual(@PathVariable Long nIdPagoCliente,
			@RequestBody PagoAplicacionManualRequestDto requestDto) {
		try {
			PagoAplicacionResultadoDto resultado = pagoClienteService.aplicarPagoManual(nIdPagoCliente, requestDto);
			return ResponseEntity.ok(resultado);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error al aplicar pago manualmente", e);
			return new ResponseEntity<String>("No fue posible aplicar el pago manualmente.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{nIdPagoCliente}/comprobante/paquete", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<?> descargarPaqueteComprobante(@PathVariable Long nIdPagoCliente) {
		try {
			byte[] contenido = pagoClienteService.descargarPaqueteComprobante(nIdPagoCliente);
			if (contenido == null || contenido.length == 0) {
				return new ResponseEntity<String>("No fue posible generar el paquete del comprobante de pago.", HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(contenido);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error al descargar paquete del comprobante del pago global", e);
			return new ResponseEntity<String>("No fue posible generar el paquete del comprobante del pago global.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/{nIdPagoCliente}/comprobante/correo")
	public ResponseEntity<?> enviarComprobanteCorreo(@PathVariable Long nIdPagoCliente) {
		try {
			PagoComprobanteCorreoResponseDto resultado = pagoClienteService.enviarComprobanteCorreo(nIdPagoCliente);
			return ResponseEntity.ok(resultado);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error al enviar por correo el comprobante del pago global", e);
			return new ResponseEntity<String>("No fue posible enviar el comprobante del pago global al correo del cliente.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}