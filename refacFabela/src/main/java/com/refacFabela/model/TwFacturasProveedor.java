package com.refacFabela.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.math.BigDecimal;

@Entity
@Table(name = "tw_factura_proveedor")
@NamedQuery(name = "TwFacturasProveedor.findAll", query = "SELECT t FROM TwFacturasProveedor t")
public class TwFacturasProveedor implements Serializable {
	private static final long serialVersionUID = 1L; 
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name = "s_folio_factura")
	private String sFolioFactura;
	
	@Column(name = "n_id_proveedor")
	private Long nIdProveedor;
	
	@Column(name = "d_fecha_inicio_factura")
	private Date dFechaInicioFactura;
	
	@Column(name = "d_fecha_termino_factura")
	private Date dFechaTerminoFactura;
	
	@Column(name = "n_id_usuario")
	private Long nIdUsuario;
	
	@Column(name = "n_estatus_factura_proveedor")
	private Long nEstatusFacturaProveedor;
	
	@Column(name = "d_fecha_pago_factura")
	private Date dFechaPagoFactura;
	
	@JsonSerialize(using = ToStringSerializer.class)
	@Column(name = "n_monto_factura", precision = 20, scale=2)
	private BigDecimal nMontoFactura;
	
	@Column(name = "n_id_moneda")
	private Long nIdMoneda;
	
	@Column(name = "s_nota")
	private String  sNota;
	
	@Column(name = "n_id_razon_social")
	private Long  nIdRazonSocial;
	
	@Column(name = "n_especial")
	private Boolean  nEspecial;
	
	@Column(name = "n_estatus_ingreso_almacen")
	private Integer  nEstatusIngresoAlmacen;
	
	
	@ManyToOne
	@JoinColumn(name = "n_id_proveedor", insertable=false, updatable=false)
	private TcProveedore TcProveedore;
	
	
	@ManyToOne
	@JoinColumn(name = "n_id_usuario", insertable=false, updatable=false)
	private TcUsuario TcUsuario;
	
	@ManyToOne
	@JoinColumn(name = "n_id_moneda", insertable=false, updatable=false)
	private TcMoneda TcMoneda;
	
	@ManyToOne
	@JoinColumn(name = "n_estatus_factura_proveedor", insertable=false, updatable=false)
	private TcEstatusFacturaProveedor TcEstatusFacturaProveedor;
	
		
	
	

	public Integer getnEstatusIngresoAlmacen() {
		return nEstatusIngresoAlmacen;
	}

	public void setnEstatusIngresoAlmacen(Integer nEstatusIngresoAlmacen) {
		this.nEstatusIngresoAlmacen = nEstatusIngresoAlmacen;
	}

	public Boolean getnEspecial() {
		return nEspecial;
	}

	public void setnEspecial(Boolean nEspecial) {
		this.nEspecial = nEspecial;
	}

	public Long getnIdRazonSocial() {
		return nIdRazonSocial;
	}

	public void setnIdRazonSocial(Long nIdRazonSocial) {
		this.nIdRazonSocial = nIdRazonSocial;
	}

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public String getsFolioFactura() {
		return sFolioFactura;
	}

	public void setsFolioFactura(String sFolioFactura) {
		this.sFolioFactura = sFolioFactura;
	}

	public Long getnIdProveedor() {
		return nIdProveedor;
	}

	public void setnIdProveedor(Long nIdProveedor) {
		this.nIdProveedor = nIdProveedor;
	}

	public Date getdFechaInicioFactura() {
		return dFechaInicioFactura;
	}

	public void setdFechaInicioFactura(Date dFechaInicioFactura) {
		this.dFechaInicioFactura = dFechaInicioFactura;
	}

	public Date getdFechaTerminoFactura() {
		return dFechaTerminoFactura;
	}

	public void setdFechaTerminoFactura(Date dFechaTerminoFactura) {
		this.dFechaTerminoFactura = dFechaTerminoFactura;
	}

	public Long getnIdUsuario() {
		return nIdUsuario;
	}

	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}

	public Long getnEstatusFacturaProveedor() {
		return nEstatusFacturaProveedor;
	}

	public void setnEstatusFacturaProveedor(Long nEstatusFacturaProveedor) {
		this.nEstatusFacturaProveedor = nEstatusFacturaProveedor;
	}

	public Date getdFechaPagoFactura() {
		return dFechaPagoFactura;
	}

	public void setdFechaPagoFactura(Date dFechaPagoFactura) {
		this.dFechaPagoFactura = dFechaPagoFactura;
	}

	public BigDecimal getnMontoFactura() {
		return nMontoFactura;
	}

	public void setnMontoFactura(BigDecimal nMontoFactura) {
		this.nMontoFactura = nMontoFactura;
	}

	public Long getnIdMoneda() {
		return nIdMoneda;
	}

	public void setnIdMoneda(Long nIdMoneda) {
		this.nIdMoneda = nIdMoneda;
	}

	public String getsNota() {
		return sNota;
	}

	public void setsNota(String sNota) {
		this.sNota = sNota;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TcProveedore getTcProveedore() {
		return TcProveedore;
	}

	public void setTcProveedore(TcProveedore tcProveedore) {
		TcProveedore = tcProveedore;
	}

	public TcUsuario getTcUsuario() {
		return TcUsuario;
	}

	public void setTcUsuario(TcUsuario tcUsuario) {
		TcUsuario = tcUsuario;
	}

	public TcMoneda getTcMoneda() {
		return TcMoneda;
	}

	public void setTcMoneda(TcMoneda tcMoneda) {
		TcMoneda = tcMoneda;
	}
	
	

	public TcEstatusFacturaProveedor getTcEstatusFacturaProveedor() {
		return TcEstatusFacturaProveedor;
	}

	public void setTcEstatusFacturaProveedor(TcEstatusFacturaProveedor tcEstatusFacturaProveedor) {
		TcEstatusFacturaProveedor = tcEstatusFacturaProveedor;
	}

	@Override
	public String toString() {
		return "TwFacturasProveedor [nId=" + nId + ", sFolioFactura=" + sFolioFactura + ", nIdProveedor=" + nIdProveedor
				+ ", dFechaInicioFactura=" + dFechaInicioFactura + ", dFechaTerminoFactura=" + dFechaTerminoFactura
				+ ", nIdUsuario=" + nIdUsuario + ", nEstatusFacturaProveedor=" + nEstatusFacturaProveedor
				+ ", dFechaPagoFactura=" + dFechaPagoFactura + ", nMontoFactura=" + nMontoFactura + ", nIdMoneda="
				+ nIdMoneda + ", sNota=" + sNota + ", nIdRazonSocial=" + nIdRazonSocial + ", nEspecial=" + nEspecial
				+ ", nEstatusIngresoAlmacen=" + nEstatusIngresoAlmacen + ", TcProveedore=" + TcProveedore
				+ ", TcUsuario=" + TcUsuario + ", TcMoneda=" + TcMoneda + ", TcEstatusFacturaProveedor="
				+ TcEstatusFacturaProveedor + "]";
	}

	

	
	
	
	
	


}
