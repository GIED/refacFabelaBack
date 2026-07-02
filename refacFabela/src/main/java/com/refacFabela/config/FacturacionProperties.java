package com.refacFabela.config;

import java.math.BigDecimal;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.refacFabela.enums.ModoComplementoPagoMixto;

@Component
@ConfigurationProperties(prefix = "facturacion")
public class FacturacionProperties {

	private String proveedorActivo;
	private PagosMixtos pagosMixtos = new PagosMixtos();
	private Credito credito = new Credito();
	private Redondeo redondeo = new Redondeo();

	public String getProveedorActivo() {
		return proveedorActivo;
	}

	public void setProveedorActivo(String proveedorActivo) {
		this.proveedorActivo = proveedorActivo;
	}

	public PagosMixtos getPagosMixtos() {
		return pagosMixtos;
	}

	public void setPagosMixtos(PagosMixtos pagosMixtos) {
		this.pagosMixtos = pagosMixtos;
	}

	public Credito getCredito() {
		return credito;
	}

	public void setCredito(Credito credito) {
		this.credito = credito;
	}

	public Redondeo getRedondeo() {
		return redondeo;
	}

	public void setRedondeo(Redondeo redondeo) {
		this.redondeo = redondeo;
	}

	public static class PagosMixtos {

		private boolean usarPpd99 = true;
		private boolean generarComplementoInmediato = true;
		private ModoComplementoPagoMixto modoComplemento = ModoComplementoPagoMixto.UN_COMPLEMENTO_VARIOS_PAGOS;

		public boolean isUsarPpd99() {
			return usarPpd99;
		}

		public void setUsarPpd99(boolean usarPpd99) {
			this.usarPpd99 = usarPpd99;
		}

		public boolean isGenerarComplementoInmediato() {
			return generarComplementoInmediato;
		}

		public void setGenerarComplementoInmediato(boolean generarComplementoInmediato) {
			this.generarComplementoInmediato = generarComplementoInmediato;
		}

		public ModoComplementoPagoMixto getModoComplemento() {
			return modoComplemento;
		}

		public void setModoComplemento(ModoComplementoPagoMixto modoComplemento) {
			this.modoComplemento = modoComplemento;
		}
	}

	public static class Credito {

		private boolean generarComplementoInmediato = false;

		public boolean isGenerarComplementoInmediato() {
			return generarComplementoInmediato;
		}

		public void setGenerarComplementoInmediato(boolean generarComplementoInmediato) {
			this.generarComplementoInmediato = generarComplementoInmediato;
		}
	}

	public static class Redondeo {

		private BigDecimal tolerancia = new BigDecimal("0.01");

		public BigDecimal getTolerancia() {
			return tolerancia;
		}

		public void setTolerancia(BigDecimal tolerancia) {
			this.tolerancia = tolerancia;
		}
	}
}
