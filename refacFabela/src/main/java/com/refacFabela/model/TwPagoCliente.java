package com.refacFabela.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tw_pago_cliente")
public class TwPagoCliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "n_id_cliente")
	private Long nIdCliente;

	@Column(name = "n_id_dato_factura")
	private Long nIdDatoFactura;

	@Column(name = "d_fecha_registro")
	private LocalDateTime dFechaRegistro;

	@Column(name = "d_fecha_pago")
	private LocalDateTime dFechaPago;

	@Column(name = "n_importe_total")
	private BigDecimal nImporteTotal;

	@Column(name = "n_importe_aplicado")
	private BigDecimal nImporteAplicado;

	@Column(name = "n_importe_disponible")
	private BigDecimal nImporteDisponible;

	@Column(name = "s_moneda")
	private String sMoneda;

	@Column(name = "n_id_forma_pago")
	private Long nIdFormaPago;

	@Column(name = "s_forma_pago_sat")
	private String sFormaPagoSat;

	@Column(name = "s_descripcion_forma_pago")
	private String sDescripcionFormaPago;

	@Column(name = "s_referencia")
	private String sReferencia;

	@Column(name = "s_numero_autorizacion")
	private String sNumeroAutorizacion;

	@Column(name = "s_folio_operacion")
	private String sFolioOperacion;

	@Column(name = "s_clave_rastreo")
	private String sClaveRastreo;

	@Column(name = "s_banco_origen")
	private String sBancoOrigen;

	@Column(name = "s_cuenta_origen")
	private String sCuentaOrigen;

	@Column(name = "s_ultimos4_cuenta_origen")
	private String sUltimos4CuentaOrigen;

	@Column(name = "s_banco_destino")
	private String sBancoDestino;

	@Column(name = "s_cuenta_destino")
	private String sCuentaDestino;

	@Column(name = "s_ultimos4_cuenta_destino")
	private String sUltimos4CuentaDestino;

	@Column(name = "s_titular_cuenta")
	private String sTitularCuenta;

	@Column(name = "s_terminal")
	private String sTerminal;

	@Column(name = "s_numero_voucher")
	private String sNumeroVoucher;

	@Column(name = "s_ultimos4_tarjeta")
	private String sUltimos4Tarjeta;

	@Column(name = "s_tipo_tarjeta")
	private String sTipoTarjeta;

	@Column(name = "s_red_tarjeta")
	private String sRedTarjeta;

	@Column(name = "s_comprobante_url")
	private String sComprobanteUrl;

	@Column(name = "s_observaciones", columnDefinition = "TEXT")
	private String sObservaciones;

	@Column(name = "n_id_usuario_registro")
	private Long nIdUsuarioRegistro;

	@Column(name = "n_id_caja")
	private Long nIdCaja;

	@Column(name = "n_id_corte_caja")
	private Long nIdCorteCaja;

	@Column(name = "s_estatus")
	private String sEstatus;

	@Column(name = "d_fecha_conciliacion")
	private LocalDateTime dFechaConciliacion;

	@Column(name = "n_conciliado")
	private Boolean nConciliado;

	@Column(name = "n_estatus")
	private Integer nEstatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "n_id_cliente", insertable = false, updatable = false)
	private TcCliente tcCliente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "n_id_dato_factura", insertable = false, updatable = false)
	private TcDatosFactura tcDatosFactura;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "n_id_forma_pago", insertable = false, updatable = false)
	private TcFormapago tcFormapago;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "n_id_usuario_registro", insertable = false, updatable = false)
	private TcUsuario tcUsuarioRegistro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "n_id_caja", insertable = false, updatable = false)
	private TwCaja twCaja;

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Long getnIdCliente() {
		return nIdCliente;
	}

	public void setnIdCliente(Long nIdCliente) {
		this.nIdCliente = nIdCliente;
	}

	public Long getnIdDatoFactura() {
		return nIdDatoFactura;
	}

	public void setnIdDatoFactura(Long nIdDatoFactura) {
		this.nIdDatoFactura = nIdDatoFactura;
	}

	public LocalDateTime getdFechaRegistro() {
		return dFechaRegistro;
	}

	public void setdFechaRegistro(LocalDateTime dFechaRegistro) {
		this.dFechaRegistro = dFechaRegistro;
	}

	public LocalDateTime getdFechaPago() {
		return dFechaPago;
	}

	public void setdFechaPago(LocalDateTime dFechaPago) {
		this.dFechaPago = dFechaPago;
	}

	public BigDecimal getnImporteTotal() {
		return nImporteTotal;
	}

	public void setnImporteTotal(BigDecimal nImporteTotal) {
		this.nImporteTotal = nImporteTotal;
	}

	public BigDecimal getnImporteAplicado() {
		return nImporteAplicado;
	}

	public void setnImporteAplicado(BigDecimal nImporteAplicado) {
		this.nImporteAplicado = nImporteAplicado;
	}

	public BigDecimal getnImporteDisponible() {
		return nImporteDisponible;
	}

	public void setnImporteDisponible(BigDecimal nImporteDisponible) {
		this.nImporteDisponible = nImporteDisponible;
	}

	public String getsMoneda() {
		return sMoneda;
	}

	public void setsMoneda(String sMoneda) {
		this.sMoneda = sMoneda;
	}

	public Long getnIdFormaPago() {
		return nIdFormaPago;
	}

	public void setnIdFormaPago(Long nIdFormaPago) {
		this.nIdFormaPago = nIdFormaPago;
	}

	public String getsFormaPagoSat() {
		return sFormaPagoSat;
	}

	public void setsFormaPagoSat(String sFormaPagoSat) {
		this.sFormaPagoSat = sFormaPagoSat;
	}

	public String getsDescripcionFormaPago() {
		return sDescripcionFormaPago;
	}

	public void setsDescripcionFormaPago(String sDescripcionFormaPago) {
		this.sDescripcionFormaPago = sDescripcionFormaPago;
	}

	public String getsReferencia() {
		return sReferencia;
	}

	public void setsReferencia(String sReferencia) {
		this.sReferencia = sReferencia;
	}

	public String getsNumeroAutorizacion() {
		return sNumeroAutorizacion;
	}

	public void setsNumeroAutorizacion(String sNumeroAutorizacion) {
		this.sNumeroAutorizacion = sNumeroAutorizacion;
	}

	public String getsFolioOperacion() {
		return sFolioOperacion;
	}

	public void setsFolioOperacion(String sFolioOperacion) {
		this.sFolioOperacion = sFolioOperacion;
	}

	public String getsClaveRastreo() {
		return sClaveRastreo;
	}

	public void setsClaveRastreo(String sClaveRastreo) {
		this.sClaveRastreo = sClaveRastreo;
	}

	public String getsBancoOrigen() {
		return sBancoOrigen;
	}

	public void setsBancoOrigen(String sBancoOrigen) {
		this.sBancoOrigen = sBancoOrigen;
	}

	public String getsCuentaOrigen() {
		return sCuentaOrigen;
	}

	public void setsCuentaOrigen(String sCuentaOrigen) {
		this.sCuentaOrigen = sCuentaOrigen;
	}

	public String getsUltimos4CuentaOrigen() {
		return sUltimos4CuentaOrigen;
	}

	public void setsUltimos4CuentaOrigen(String sUltimos4CuentaOrigen) {
		this.sUltimos4CuentaOrigen = sUltimos4CuentaOrigen;
	}

	public String getsBancoDestino() {
		return sBancoDestino;
	}

	public void setsBancoDestino(String sBancoDestino) {
		this.sBancoDestino = sBancoDestino;
	}

	public String getsCuentaDestino() {
		return sCuentaDestino;
	}

	public void setsCuentaDestino(String sCuentaDestino) {
		this.sCuentaDestino = sCuentaDestino;
	}

	public String getsUltimos4CuentaDestino() {
		return sUltimos4CuentaDestino;
	}

	public void setsUltimos4CuentaDestino(String sUltimos4CuentaDestino) {
		this.sUltimos4CuentaDestino = sUltimos4CuentaDestino;
	}

	public String getsTitularCuenta() {
		return sTitularCuenta;
	}

	public void setsTitularCuenta(String sTitularCuenta) {
		this.sTitularCuenta = sTitularCuenta;
	}

	public String getsTerminal() {
		return sTerminal;
	}

	public void setsTerminal(String sTerminal) {
		this.sTerminal = sTerminal;
	}

	public String getsNumeroVoucher() {
		return sNumeroVoucher;
	}

	public void setsNumeroVoucher(String sNumeroVoucher) {
		this.sNumeroVoucher = sNumeroVoucher;
	}

	public String getsUltimos4Tarjeta() {
		return sUltimos4Tarjeta;
	}

	public void setsUltimos4Tarjeta(String sUltimos4Tarjeta) {
		this.sUltimos4Tarjeta = sUltimos4Tarjeta;
	}

	public String getsTipoTarjeta() {
		return sTipoTarjeta;
	}

	public void setsTipoTarjeta(String sTipoTarjeta) {
		this.sTipoTarjeta = sTipoTarjeta;
	}

	public String getsRedTarjeta() {
		return sRedTarjeta;
	}

	public void setsRedTarjeta(String sRedTarjeta) {
		this.sRedTarjeta = sRedTarjeta;
	}

	public String getsComprobanteUrl() {
		return sComprobanteUrl;
	}

	public void setsComprobanteUrl(String sComprobanteUrl) {
		this.sComprobanteUrl = sComprobanteUrl;
	}

	public String getsObservaciones() {
		return sObservaciones;
	}

	public void setsObservaciones(String sObservaciones) {
		this.sObservaciones = sObservaciones;
	}

	public Long getnIdUsuarioRegistro() {
		return nIdUsuarioRegistro;
	}

	public void setnIdUsuarioRegistro(Long nIdUsuarioRegistro) {
		this.nIdUsuarioRegistro = nIdUsuarioRegistro;
	}

	public Long getnIdCaja() {
		return nIdCaja;
	}

	public void setnIdCaja(Long nIdCaja) {
		this.nIdCaja = nIdCaja;
	}

	public Long getnIdCorteCaja() {
		return nIdCorteCaja;
	}

	public void setnIdCorteCaja(Long nIdCorteCaja) {
		this.nIdCorteCaja = nIdCorteCaja;
	}

	public String getsEstatus() {
		return sEstatus;
	}

	public void setsEstatus(String sEstatus) {
		this.sEstatus = sEstatus;
	}

	public LocalDateTime getdFechaConciliacion() {
		return dFechaConciliacion;
	}

	public void setdFechaConciliacion(LocalDateTime dFechaConciliacion) {
		this.dFechaConciliacion = dFechaConciliacion;
	}

	public Boolean getnConciliado() {
		return nConciliado;
	}

	public void setnConciliado(Boolean nConciliado) {
		this.nConciliado = nConciliado;
	}

	public Integer getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(Integer nEstatus) {
		this.nEstatus = nEstatus;
	}

	public TcCliente getTcCliente() {
		return tcCliente;
	}

	public TcDatosFactura getTcDatosFactura() {
		return tcDatosFactura;
	}

	public TcFormapago getTcFormapago() {
		return tcFormapago;
	}

	public TcUsuario getTcUsuarioRegistro() {
		return tcUsuarioRegistro;
	}

	public TwCaja getTwCaja() {
		return twCaja;
	}
}