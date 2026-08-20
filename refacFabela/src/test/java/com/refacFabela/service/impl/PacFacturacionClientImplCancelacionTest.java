package com.refacFabela.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.refacFabela.config.FacturacionProperties;
import com.refacFabela.config.FacturoPorTiProperties;
import com.refacFabela.dto.CancelacionRequest;
import com.refacFabela.dto.CancelacionResponse;
import com.refacFabela.service.PacFacturacionMapper;

class PacFacturacionClientImplCancelacionTest {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void marcaCanceladaCuandoElPacAceptaLaSolicitudSinAcuse() {
		ObjectNode response = objectMapper.createObjectNode();
		response.put("codigo", "000");
		response.put("estado", "Cancelada");

		CancelacionResponse parsed = parse(response);

		assertTrue(Boolean.TRUE.equals(parsed.getSuccess()));
		assertEquals("CANCELADA", parsed.getEstatus());
		assertNull(parsed.getCodigoError());
	}

	@Test
	void conservaElErrorCuandoElPacRechazaLaCancelacion() {
		ObjectNode response = objectMapper.createObjectNode();
		response.put("codigo", "002");
		response.put("mensaje", "No se pudo enviar la solicitud de cancelación con CSD.");

		CancelacionResponse parsed = parse(response);

		assertFalse(Boolean.TRUE.equals(parsed.getSuccess()));
		assertEquals("002", parsed.getCodigoError());
	}

	@Test
	void marcaCanceladaCuandoElAcuseSatConfirmaEstatus201() {
		ObjectNode response = objectMapper.createObjectNode();
		response.put("codigo", "000");
		response.put("estado", "No Encontrado");
		response.put("acuse", "<Acuse><Folios><UUID>7C9C5FCF-E438-45B1-A65A-346CCE4758B8</UUID>"
				+ "<EstatusUUID>201</EstatusUUID></Folios></Acuse>");

		CancelacionResponse parsed = parse(response);

		assertTrue(Boolean.TRUE.equals(parsed.getSuccess()));
		assertEquals("CANCELADA", parsed.getEstatus());
		assertNull(parsed.getCodigoError());
	}

	private CancelacionResponse parse(ObjectNode response) {
		PacFacturacionClientImpl client = new PacFacturacionClientImpl(new RestTemplate(), objectMapper,
				new FacturoPorTiProperties(), new FacturacionProperties(), new PacFacturacionMapper(), null);
		return ReflectionTestUtils.invokeMethod(client, "parseCancelacionCfdiResponse", response,
				new CancelacionRequest());
	}
}