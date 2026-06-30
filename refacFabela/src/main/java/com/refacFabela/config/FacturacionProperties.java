package com.refacFabela.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "facturacion")
public class FacturacionProperties {

	private String proveedorActivo;

	public String getProveedorActivo() {
		return proveedorActivo;
	}

	public void setProveedorActivo(String proveedorActivo) {
		this.proveedorActivo = proveedorActivo;
	}
}
