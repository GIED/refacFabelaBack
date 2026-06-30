package com.refacFabela.facturacionv2.exception;

public class FacturoPorTiClientException extends FacturacionException {

	private static final long serialVersionUID = 1L;

	public FacturoPorTiClientException(String message) {
		super(message);
	}

	public FacturoPorTiClientException(String message, Throwable cause) {
		super(message, cause);
	}
}