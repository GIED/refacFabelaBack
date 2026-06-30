package com.refacFabela.exception;

public class PacFacturacionClientException extends FacturacionException {

	private static final long serialVersionUID = 1L;

	public PacFacturacionClientException(String message) {
		super(message);
	}

	public PacFacturacionClientException(String message, Throwable cause) {
		super(message, cause);
	}
}
