package com.refacFabela.dto;

import java.util.ArrayList;
import java.util.List;

public class PagoAplicacionManualRequestDto {

	private Long nIdUsuario;
	private String origenRegistro;
	private List<PagoAplicacionManualLineaDto> lineas = new ArrayList<PagoAplicacionManualLineaDto>();

	public Long getnIdUsuario() {
		return nIdUsuario;
	}

	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}

	public String getOrigenRegistro() {
		return origenRegistro;
	}

	public void setOrigenRegistro(String origenRegistro) {
		this.origenRegistro = origenRegistro;
	}

	public List<PagoAplicacionManualLineaDto> getLineas() {
		return lineas;
	}

	public void setLineas(List<PagoAplicacionManualLineaDto> lineas) {
		this.lineas = lineas;
	}
}