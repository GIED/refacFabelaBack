package com.refacFabela.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tv_reporte_detalle_venta")
@NamedQuery(name = "TvReporteDetalleVenta.findAll", query = "SELECT t FROM TvReporteDetalleVenta t")
public class TvReporteDetalleVenta implements Serializable {
	private static final long serialVersionUID = 1L;
	    
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "n_id_venta") 
		private Long nIdVenta;
	    @Column(name = "n_id_caja")
		private Long nIdCaja;
		@Column(name = "s_rfc") 
		private String sRfc;
		@Column(name = "s_razon_social")
		private String sRazonSocial;
		@Column(name = "n_estatus_venta") 
		private Long nEstatusVenta;
		@Column(name = "d_fecha_venta") 
		private Date dFechaVenta;
		@Column(name = "s_tipo_venta")
		private String sTipoVenta;
		@Column(name = "s_tipo_pago") 
		private String sTipoPago;
		
		@Column(name = "d_inicio_credito")
		private Date dInicioCredito;
		@Column(name = "d_termino_credito") 
		private Date dTerminoCredito;
		@Column(name = "s_nombre_usuario" )
		private String sNombreUsuario;
		@Column(name = "n_anticipo") 
		private Double nAnticipo;
		@Column(name = "n_id_forma_pago" )
		private Long nIdFormaPago;
		@Column(name = "s_forma_pago" )
		private String sFormaPago;
		@Column(name = "n_descuento" )
		private Double nDescuento;
		@Column(name = "n_total_venta")
		private Double nTotalVenta;
		@Column(name = "n_total_pago_caja_nota")
		private Double nTotalPagoCajaNota;
		@Column(name = "n_saldo_final_venta" )
		private Double nSaldoFinalVenta;
		@Column(name = "s_estatus_entrega")		
		private String sEstatusEntrega;
		
		public TvReporteDetalleVenta() {
			
		}

		public Long getnIdCaja() {
			return nIdCaja;
		}

		public void setnIdCaja(Long nIdCaja) {
			this.nIdCaja = nIdCaja;
		}

		public String getsRfc() {
			return sRfc;
		}

		public void setsRfc(String sRfc) {
			this.sRfc = sRfc;
		}

		public String getsRazonSocial() {
			return sRazonSocial;
		}

		public void setsRazonSocial(String sRazonSocial) {
			this.sRazonSocial = sRazonSocial;
		}

		public Long getnEstatusVenta() {
			return nEstatusVenta;
		}

		public void setnEstatusVenta(Long nEstatusVenta) {
			this.nEstatusVenta = nEstatusVenta;
		}

		public Date getdFechaVenta() {
			return dFechaVenta;
		}

		public void setdFechaVenta(Date dFechaVenta) {
			this.dFechaVenta = dFechaVenta;
		}

		public String getsTipoVenta() {
			return sTipoVenta;
		}

		public void setsTipoVenta(String sTipoVenta) {
			this.sTipoVenta = sTipoVenta;
		}

	

		public String getsTipoPago() {
			return sTipoPago;
		}

		public void setsTipoPago(String sTipoPago) {
			this.sTipoPago = sTipoPago;
		}

		public Long getnIdVenta() {
			return nIdVenta;
		}

		public void setnIdVenta(Long nIdVenta) {
			this.nIdVenta = nIdVenta;
		}

		public Date getdInicioCredito() {
			return dInicioCredito;
		}

		public void setdInicioCredito(Date dInicioCredito) {
			this.dInicioCredito = dInicioCredito;
		}

		public Date getdTerminoCredito() {
			return dTerminoCredito;
		}

		public void setdTerminoCredito(Date dTerminoCredito) {
			this.dTerminoCredito = dTerminoCredito;
		}

		public String getsNombreUsuario() {
			return sNombreUsuario;
		}

		public void setsNombreUsuario(String sNombreUsuario) {
			this.sNombreUsuario = sNombreUsuario;
		}

		public Double getnAnticipo() {
			return nAnticipo;
		}

		public void setnAnticipo(Double nAnticipo) {
			this.nAnticipo = nAnticipo;
		}

		public Long getnIdFormaPago() {
			return nIdFormaPago;
		}

		public void setnIdFormaPago(Long nIdFormaPago) {
			this.nIdFormaPago = nIdFormaPago;
		}

		public String getsFormaPago() {
			return sFormaPago;
		}

		public void setsFormaPago(String sFormaPago) {
			this.sFormaPago = sFormaPago;
		}

		public Double getnDescuento() {
			return nDescuento;
		}

		public void setnDescuento(Double nDescuento) {
			this.nDescuento = nDescuento;
		}

		public Double getnTotalVenta() {
			return nTotalVenta;
		}

		public void setnTotalVenta(Double nTotalVenta) {
			this.nTotalVenta = nTotalVenta;
		}

		public Double getnTotalPagoCajaNota() {
			return nTotalPagoCajaNota;
		}

		public void setnTotalPagoCajaNota(Double nTotalPagoCajaNota) {
			this.nTotalPagoCajaNota = nTotalPagoCajaNota;
		}

		public Double getnSaldoFinalVenta() {
			return nSaldoFinalVenta;
		}

		public void setnSaldoFinalVenta(Double nSaldoFinalVenta) {
			this.nSaldoFinalVenta = nSaldoFinalVenta;
		}

		public String getsEstatusEntrega() {
			return sEstatusEntrega;
		}

		public void setsEstatusEntrega(String sEstatusEntrega) {
			this.sEstatusEntrega = sEstatusEntrega;
		}

		@Override
		public String toString() {
			return "TvReporteDetalleVenta [nIdVenta=" + nIdVenta + ", nIdCaja=" + nIdCaja + ", sRfc=" + sRfc
					+ ", sRazonSocial=" + sRazonSocial + ", nEstatusVenta=" + nEstatusVenta + ", dFechaVenta="
					+ dFechaVenta + ", sTipoVenta=" + sTipoVenta + ", sTipoPago=" + sTipoPago + ", dInicioCredito="
					+ dInicioCredito + ", dTerminoCredito=" + dTerminoCredito + ", sNombreUsuario=" + sNombreUsuario
					+ ", nAnticipo=" + nAnticipo + ", nIdFormaPago=" + nIdFormaPago + ", sFormaPago=" + sFormaPago
					+ ", nDescuento=" + nDescuento + ", nTotalVenta=" + nTotalVenta + ", nTotalPagoCajaNota="
					+ nTotalPagoCajaNota + ", nSaldoFinalVenta=" + nSaldoFinalVenta + ", sEstatusEntrega="
					+ sEstatusEntrega + "]\n";
		}

	
		


}
