package com.refacFabela.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.utils.DateTimeUtil;

@Component
public class FacturacionMontoHelper {

	public BigDecimal calcularSubTotal(List<TwVentasProducto> productos) {
		BigDecimal subtotal = BigDecimal.ZERO;
		if (productos == null) {
			return subtotal;
		}
		for (TwVentasProducto producto : productos) {
			subtotal = subtotal.add(producto.getnPrecioPartida() != null ? producto.getnPrecioPartida() : BigDecimal.ZERO);
		}
		return DateTimeUtil.truncarDosDecimales(subtotal);
	}

	public BigDecimal calcularIvaTotal(List<TwVentasProducto> productos) {
		BigDecimal iva = BigDecimal.ZERO;
		if (productos == null) {
			return iva;
		}
		for (TwVentasProducto producto : productos) {
			iva = iva.add(producto.getnIvaPartida() != null ? producto.getnIvaPartida() : BigDecimal.ZERO);
		}
		return DateTimeUtil.truncarDosDecimales(iva);
	}

	public BigDecimal calcularTotal(List<TwVentasProducto> productos) {
		return DateTimeUtil.truncarDosDecimales(calcularSubTotal(productos).add(calcularIvaTotal(productos)));
	}
}
