package com.refacFabela.facturacionv2.exception;

public class FacturacionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FacturacionException(String message) {
		super(message);
	}

	public FacturacionException(String message, Throwable cause) {
		super(message, cause);
	}
}