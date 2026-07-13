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
@Table(name = "tw_pago_aplicacion")
public class TwPagoAplicacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "n_id_pago_cliente")
	private Long nIdPagoCliente;

	@Column(name = "n_id_cliente")
	private Long nIdCliente;

	@Column(name = "n_id_venta")
	private Long nIdVenta;

	@Column(name = "n_id_facturacion")
	private Long nIdFacturacion;

	@Column(name = "n_id_dato_factura")
	private Long nIdDatoFactura;

	@Column(name = "n_monto_aplicado")
	private BigDecimal nMontoAplicado;

	@Column(name = "n_saldo_anterior")
	private BigDecimal nSaldoAnterior;

	@Column(name = "n_saldo_insoluto")
	private BigDecimal nSaldoInsoluto;

	@Column(name = "n_parcialidad")
	private Integer nParcialidad;

	@Column(name = "s_estatus")
	private String sEstatus;

	@Column(name = "d_fecha_aplicacion")
	private LocalDateTime dFechaAplicacion;

	@Column(name = "n_id_usuario")
	private Long nIdUsuario;

	@Column(name = "n_orden_aplicacion")
	private Integer nOrdenAplicacion;

	@Column(name = "s_origen_registro")
	private String sOrigenRegistro;

	@Column(name = "n_estatus")
	private Integer nEstatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "n_id_pago_cliente", insertable = false, updatable = false)
	private TwPagoCliente twPagoCliente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "n_id_cliente", insertable = false, updatable = false)
	private TcCliente tcCliente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "n_id_venta", insertable = false, updatable = false)
	private TwVenta twVenta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "n_id_facturacion", insertable = false, updatable = false)
	private TwFacturacion twFacturacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "n_id_dato_factura", insertable = false, updatable = false)
	private TcDatosFactura tcDatosFactura;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "n_id_usuario", insertable = false, updatable = false)
	private TcUsuario tcUsuario;

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Long getnIdPagoCliente() {
		return nIdPagoCliente;
	}

	public void setnIdPagoCliente(Long nIdPagoCliente) {
		this.nIdPagoCliente = nIdPagoCliente;
	}

	public Long getnIdCliente() {
		return nIdCliente;
	}

	public void setnIdCliente(Long nIdCliente) {
		this.nIdCliente = nIdCliente;
	}

	public Long getnIdVenta() {
		return nIdVenta;
	}

	public void setnIdVenta(Long nIdVenta) {
		this.nIdVenta = nIdVenta;
	}

	public Long getnIdFacturacion() {
		return nIdFacturacion;
	}

	public void setnIdFacturacion(Long nIdFacturacion) {
		this.nIdFacturacion = nIdFacturacion;
	}

	public Long getnIdDatoFactura() {
		return nIdDatoFactura;
	}

	public void setnIdDatoFactura(Long nIdDatoFactura) {
		this.nIdDatoFactura = nIdDatoFactura;
	}

	public BigDecimal getnMontoAplicado() {
		return nMontoAplicado;
	}

	public void setnMontoAplicado(BigDecimal nMontoAplicado) {
		this.nMontoAplicado = nMontoAplicado;
	}

	public BigDecimal getnSaldoAnterior() {
		return nSaldoAnterior;
	}

	public void setnSaldoAnterior(BigDecimal nSaldoAnterior) {
		this.nSaldoAnterior = nSaldoAnterior;
	}

	public BigDecimal getnSaldoInsoluto() {
		return nSaldoInsoluto;
	}

	public void setnSaldoInsoluto(BigDecimal nSaldoInsoluto) {
		this.nSaldoInsoluto = nSaldoInsoluto;
	}

	public Integer getnParcialidad() {
		return nParcialidad;
	}

	public void setnParcialidad(Integer nParcialidad) {
		this.nParcialidad = nParcialidad;
	}

	public String getsEstatus() {
		return sEstatus;
	}

	public void setsEstatus(String sEstatus) {
		this.sEstatus = sEstatus;
	}

	public LocalDateTime getdFechaAplicacion() {
		return dFechaAplicacion;
	}

	public void setdFechaAplicacion(LocalDateTime dFechaAplicacion) {
		this.dFechaAplicacion = dFechaAplicacion;
	}

	public Long getnIdUsuario() {
		return nIdUsuario;
	}

	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}

	public Integer getnOrdenAplicacion() {
		return nOrdenAplicacion;
	}

	public void setnOrdenAplicacion(Integer nOrdenAplicacion) {
		this.nOrdenAplicacion = nOrdenAplicacion;
	}

	public String getsOrigenRegistro() {
		return sOrigenRegistro;
	}

	public void setsOrigenRegistro(String sOrigenRegistro) {
		this.sOrigenRegistro = sOrigenRegistro;
	}

	public Integer getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(Integer nEstatus) {
		this.nEstatus = nEstatus;
	}

	public TwPagoCliente getTwPagoCliente() {
		return twPagoCliente;
	}

	public TcCliente getTcCliente() {
		return tcCliente;
	}

	public TwVenta getTwVenta() {
		return twVenta;
	}

	public TwFacturacion getTwFacturacion() {
		return twFacturacion;
	}

	public TcDatosFactura getTcDatosFactura() {
		return tcDatosFactura;
	}

	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}
}