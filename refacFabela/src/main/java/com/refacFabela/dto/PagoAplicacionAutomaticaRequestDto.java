package com.refacFabela.dto;

public class PagoAplicacionAutomaticaRequestDto {

	private Long nIdUsuario;
	private String origenRegistro;

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
}