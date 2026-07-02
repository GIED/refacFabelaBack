package com.refacFabela.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.refacFabela.config.FacturacionProperties;
import com.refacFabela.dto.ClasificacionFacturacionVenta;
import com.refacFabela.enums.ClasificacionFacturacionPago;
import com.refacFabela.model.TcFormapago;
import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TwVenta;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.repository.CatalagoFormaPagoRepository;

@Service
public class ClasificacionFacturacionService {

	private static final String FORMA_PAGO_POR_DEFINIR = "99";
	private static final String METODO_PAGO_PUE = "PUE";
	private static final String METODO_PAGO_PPD = "PPD";

	private final FacturacionMontoHelper facturacionMontoHelper;
	private final CatalagoFormaPagoRepository catalagoFormaPagoRepository;
	private final FacturacionProperties facturacionProperties;

	public ClasificacionFacturacionService(FacturacionMontoHelper facturacionMontoHelper,
			CatalagoFormaPagoRepository catalagoFormaPagoRepository,
			FacturacionProperties facturacionProperties) {
		this.facturacionMontoHelper = facturacionMontoHelper;
		this.catalagoFormaPagoRepository = catalagoFormaPagoRepository;
		this.facturacionProperties = facturacionProperties;
	}

	public ClasificacionFacturacionVenta clasificarVenta(TwVenta venta, List<TwVentasProducto> productos,
			List<TrVentaCobro> cobros) {
		ClasificacionFacturacionVenta clasificacion = new ClasificacionFacturacionVenta();
		clasificacion.setIdVenta(venta != null ? venta.getnId() : null);

		BigDecimal totalVenta = facturacionMontoHelper.calcularTotal(productos);
		BigDecimal totalPagado = calcularTotalPagado(cobros);
		BigDecimal saldoPendiente = totalVenta.subtract(totalPagado);
		if (saldoPendiente.compareTo(BigDecimal.ZERO) < 0
				&& saldoPendiente.abs().compareTo(resolveTolerancia()) <= 0) {
			saldoPendiente = BigDecimal.ZERO;
		}

		Map<String, BigDecimal> montosPorClaveSat = agruparMontosPorClaveSat(cobros);
		String formaFiscal = resolveFormaPagoFiscal(venta, cobros, montosPorClaveSat);
		String formaDescripcionFiscal = resolveDescripcionFormaPago(formaFiscal);

		boolean esCredito = venta != null && Long.valueOf(1L).equals(venta.getnTipoPago());
		boolean totalmenteLiquidada = saldoPendiente.compareTo(BigDecimal.ZERO) <= 0;
		int numeroFormasPago = montosPorClaveSat.size();

		ClasificacionFacturacionPago tipoClasificacion;
		String metodoPagoFiscal;
		String formaPagoFiscal;

		if (!esCredito && totalmenteLiquidada && numeroFormasPago <= 1) {
			tipoClasificacion = ClasificacionFacturacionPago.PUE_UNA_FORMA;
			metodoPagoFiscal = METODO_PAGO_PUE;
			formaPagoFiscal = formaFiscal;
		} else if (!esCredito && totalmenteLiquidada && numeroFormasPago > 1) {
			tipoClasificacion = ClasificacionFacturacionPago.PPD_PAGO_MIXTO_COMPLEMENTO_INMEDIATO;
			metodoPagoFiscal = METODO_PAGO_PPD;
			formaPagoFiscal = shouldUsePpd99() ? FORMA_PAGO_POR_DEFINIR : formaFiscal;
		} else {
			tipoClasificacion = ClasificacionFacturacionPago.PPD_CREDITO_SIN_COMPLEMENTO;
			metodoPagoFiscal = METODO_PAGO_PPD;
			formaPagoFiscal = FORMA_PAGO_POR_DEFINIR;
		}

		clasificacion.setTotalVenta(totalVenta);
		clasificacion.setTotalPagado(totalPagado);
		clasificacion.setSaldoPendiente(saldoPendiente);
		clasificacion.setNumeroPagosRegistrados(cobros != null ? cobros.size() : 0);
		clasificacion.setNumeroFormasPagoSatDistintas(numeroFormasPago);
		clasificacion.setCredito(Boolean.valueOf(esCredito));
		clasificacion.setTotalmenteLiquidada(Boolean.valueOf(totalmenteLiquidada));
		clasificacion.setClasificacion(tipoClasificacion);
		clasificacion.setMetodoPagoFiscal(metodoPagoFiscal);
		clasificacion.setFormaPagoFiscal(formaPagoFiscal);
		clasificacion.setFormaPagoDescripcionFiscal(formaDescripcionFiscal);
		clasificacion.setClavesFormaPagoSat(new ArrayList<String>(montosPorClaveSat.keySet()));
		clasificacion.setMontosPorClaveSat(montosPorClaveSat);
		return clasificacion;
	}

	private BigDecimal calcularTotalPagado(List<TrVentaCobro> cobros) {
		BigDecimal totalPagado = BigDecimal.ZERO;
		if (cobros == null) {
			return totalPagado;
		}
		for (TrVentaCobro cobro : cobros) {
			if (cobro == null || cobro.getnMonto() == null) {
				continue;
			}
			totalPagado = totalPagado.add(cobro.getnMonto());
		}
		return totalPagado;
	}

	private Map<String, BigDecimal> agruparMontosPorClaveSat(List<TrVentaCobro> cobros) {
		Map<String, BigDecimal> montosPorClaveSat = new LinkedHashMap<String, BigDecimal>();
		if (cobros == null) {
			return montosPorClaveSat;
		}
		for (TrVentaCobro cobro : cobros) {
			String clave = resolveClaveSat(cobro);
			if (clave == null || clave.trim().isEmpty()) {
				continue;
			}
			BigDecimal monto = cobro.getnMonto() != null ? cobro.getnMonto() : BigDecimal.ZERO;
			BigDecimal acumulado = montosPorClaveSat.get(clave);
			montosPorClaveSat.put(clave, acumulado != null ? acumulado.add(monto) : monto);
		}
		return montosPorClaveSat;
	}

	private String resolveFormaPagoFiscal(TwVenta venta, List<TrVentaCobro> cobros, Map<String, BigDecimal> montosPorClaveSat) {
		String formaPorMonto = resolveClaveSatMayorMonto(montosPorClaveSat);
		if (formaPorMonto != null) {
			return formaPorMonto;
		}
		if (venta != null && venta.getTcFormapago() != null && venta.getTcFormapago().getsClave() != null
				&& !venta.getTcFormapago().getsClave().trim().isEmpty()) {
			return venta.getTcFormapago().getsClave().trim();
		}
		if (cobros != null) {
			for (TrVentaCobro cobro : cobros) {
				String clave = resolveClaveSat(cobro);
				if (clave != null && !clave.trim().isEmpty()) {
					return clave;
				}
			}
		}
		return null;
	}

	private String resolveClaveSatMayorMonto(Map<String, BigDecimal> montosPorClaveSat) {
		String claveMayor = null;
		BigDecimal montoMayor = null;
		for (Map.Entry<String, BigDecimal> entry : montosPorClaveSat.entrySet()) {
			if (montoMayor == null || entry.getValue().compareTo(montoMayor) > 0) {
				montoMayor = entry.getValue();
				claveMayor = entry.getKey();
			}
		}
		return claveMayor;
	}

	private String resolveClaveSat(TrVentaCobro cobro) {
		if (cobro == null) {
			return null;
		}
		if (cobro.getTcFormapago() != null && cobro.getTcFormapago().getsClave() != null
				&& !cobro.getTcFormapago().getsClave().trim().isEmpty()) {
			return cobro.getTcFormapago().getsClave().trim();
		}
		if (cobro.getnIdFormaPago() == null) {
			return null;
		}
		TcFormapago formaPago = catalagoFormaPagoRepository.findById(cobro.getnIdFormaPago()).orElse(null);
		if (formaPago == null || formaPago.getsClave() == null || formaPago.getsClave().trim().isEmpty()) {
			return null;
		}
		return formaPago.getsClave().trim();
	}

	private String resolveDescripcionFormaPago(String claveSat) {
		if (claveSat == null || claveSat.trim().isEmpty() || FORMA_PAGO_POR_DEFINIR.equals(claveSat)) {
			return "Por definir";
		}
		List<TcFormapago> formasPago = catalagoFormaPagoRepository.findBynEstatus(1);
		for (TcFormapago formaPago : formasPago) {
			if (formaPago != null && claveSat.equals(formaPago.getsClave())) {
				return formaPago.getsDescripcion();
			}
		}
		return claveSat;
	}

	private boolean shouldUsePpd99() {
		return facturacionProperties != null && facturacionProperties.getPagosMixtos() != null
				? facturacionProperties.getPagosMixtos().isUsarPpd99()
				: true;
	}

	private BigDecimal resolveTolerancia() {
		if (facturacionProperties == null || facturacionProperties.getRedondeo() == null
				|| facturacionProperties.getRedondeo().getTolerancia() == null) {
			return new BigDecimal("0.01");
		}
		return facturacionProperties.getRedondeo().getTolerancia();
	}
}