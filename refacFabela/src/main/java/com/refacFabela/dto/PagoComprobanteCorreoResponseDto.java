package com.refacFabela.dto;

public class PagoComprobanteCorreoResponseDto {

	private Long nIdPagoCliente;
	private String correoDestino;
	private Boolean enviado;
	private Boolean bloqueado;
	private String detalle;

	public Long getnIdPagoCliente() {
		return nIdPagoCliente;
	}

	public void setnIdPagoCliente(Long nIdPagoCliente) {
		this.nIdPagoCliente = nIdPagoCliente;
	}

	public String getCorreoDestino() {
		return correoDestino;
	}

	public void setCorreoDestino(String correoDestino) {
		this.correoDestino = correoDestino;
	}

	public Boolean getEnviado() {
		return enviado;
	}

	public void setEnviado(Boolean enviado) {
		this.enviado = enviado;
	}

	public Boolean getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
}