package com.refacFabela.service;

import java.util.List;

import com.refacFabela.dto.FacturaCreditoPendienteDto;
import com.refacFabela.dto.PagoAplicacionLineaDto;
import com.refacFabela.dto.PagoAplicacionAutomaticaRequestDto;
import com.refacFabela.dto.PagoAplicacionManualRequestDto;
import com.refacFabela.dto.PagoAplicacionResultadoDto;
import com.refacFabela.dto.PagoClienteDetalleDto;
import com.refacFabela.dto.PagoClienteRegistroDto;

public interface PagoClienteService {

	PagoClienteDetalleDto registrarPago(PagoClienteRegistroDto registroDto);

	PagoClienteDetalleDto consultarPago(Long nIdPagoCliente);

	List<PagoAplicacionLineaDto> consultarAplicacionesVenta(Long nIdVenta);

	List<PagoClienteDetalleDto> consultarPagosCliente(Long nIdCliente);

	List<FacturaCreditoPendienteDto> consultarFacturasPendientesCliente(Long nIdCliente, Long nIdDatoFactura);

	PagoAplicacionResultadoDto aplicarPagoAutomatico(Long nIdPagoCliente, PagoAplicacionAutomaticaRequestDto requestDto);

	PagoAplicacionResultadoDto aplicarPagoManual(Long nIdPagoCliente, PagoAplicacionManualRequestDto requestDto);
}