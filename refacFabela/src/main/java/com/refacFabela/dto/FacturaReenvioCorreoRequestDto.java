package com.refacFabela.dto;

public class FacturaReenvioCorreoRequestDto {

	private Boolean usarCorreoRegistrado;
	private String correoDestino;

	public Boolean getUsarCorreoRegistrado() {
		return usarCorreoRegistrado;
	}

	public void setUsarCorreoRegistrado(Boolean usarCorreoRegistrado) {
		this.usarCorreoRegistrado = usarCorreoRegistrado;
	}

	public String getCorreoDestino() {
		return correoDestino;
	}

	public void setCorreoDestino(String correoDestino) {
		this.correoDestino = correoDestino;
	}
}
