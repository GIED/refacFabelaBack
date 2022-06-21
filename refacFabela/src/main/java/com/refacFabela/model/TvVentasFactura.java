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

@Entity
@Table(name = "tv_ventasFactura")
public class TvVentasFactura implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id	
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name = "n_idCliente")
	private long nIdCliente;
	
	@Column(name = "n_idUsuario")
	private long nIdUsuario;
	
	@Column(name = "s_folioVenta")
	private String sFolioVenta;
	
	@Column(name = "d_fechaVenta")
	private Date dFechaVenta;	
	
	@Column(name = "n_tipoPago")
	private long nTipoPago;		

	@Column(name = "n_estatusVenta")
	private Long nEstatus;
	
	@Column(name = "n_idTipoVenta")
	private Long nIdTipoVenta;
	
	@Column(name = "n_idFormaPago")
	private Long formaPago;
	
	@Column(name = "n_idCaja")
	private Long idCaja;
	
	@Column(name = "n_idFactura")
	private Long idFactura;
	
	@Column(name = "n_totalVenta")
	private Double nTotalVenta;
	
	
	
	
	
	
	@ManyToOne
	@JoinColumn(name = "n_idCliente", updatable = false, insertable = false )
	private TcCliente tcCliente;

	
	@ManyToOne
	@JoinColumn(name = "n_idUsuario", updatable = false, insertable = false)
	private TcUsuario tcUsuario;
	
	@ManyToOne
	@JoinColumn(name = "n_estatusVenta", updatable = false, insertable = false)
	private TcEstatusVenta tcEstatusVenta;
	
	@ManyToOne
	@JoinColumn(name = "n_idFormaPago", updatable = false, insertable = false )
	private TcFormapago tcFormapago ;
	
	@ManyToOne
	@JoinColumn(name = "n_idCaja", updatable = false, insertable = false )
	private TwCaja twCaja ;
	
	@ManyToOne
	@JoinColumn(name = "n_idTipoVenta", updatable = false, insertable = false )
	private TcTipoVenta tcTipoVenta ;
	
	
	
	
	
	

	public TvVentasFactura() {
		
	}





	public Long getnId() {
		return nId;
	}





	public void setnId(Long nId) {
		this.nId = nId;
	}





	public long getnIdCliente() {
		return nIdCliente;
	}





	public void setnIdCliente(long nIdCliente) {
		this.nIdCliente = nIdCliente;
	}





	public long getnIdUsuario() {
		return nIdUsuario;
	}





	public void setnIdUsuario(long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}





	public String getsFolioVenta() {
		return sFolioVenta;
	}





	public void setsFolioVenta(String sFolioVenta) {
		this.sFolioVenta = sFolioVenta;
	}





	public Date getdFechaVenta() {
		return dFechaVenta;
	}





	public void setdFechaVenta(Date dFechaVenta) {
		this.dFechaVenta = dFechaVenta;
	}





	public long getnTipoPago() {
		return nTipoPago;
	}





	public void setnTipoPago(long nTipoPago) {
		this.nTipoPago = nTipoPago;
	}



	public Long getnEstatus() {
		return nEstatus;
	}





	public void setnEstatus(Long nEstatus) {
		this.nEstatus = nEstatus;
	}





	public Long getnIdTipoVenta() {
		return nIdTipoVenta;
	}





	public void setnIdTipoVenta(Long nIdTipoVenta) {
		this.nIdTipoVenta = nIdTipoVenta;
	}





	public Long getFormaPago() {
		return formaPago;
	}





	public void setFormaPago(Long formaPago) {
		this.formaPago = formaPago;
	}





	public Long getIdCaja() {
		return idCaja;
	}





	public void setIdCaja(Long idCaja) {
		this.idCaja = idCaja;
	}





	public Long getIdFactura() {
		return idFactura;
	}





	public void setIdFactura(Long idFactura) {
		this.idFactura = idFactura;
	}





	public Double getnTotalVenta() {
		return nTotalVenta;
	}





	public void setnTotalVenta(Double nTotalVenta) {
		this.nTotalVenta = nTotalVenta;
	}





	public TcCliente getTcCliente() {
		return tcCliente;
	}





	public void setTcCliente(TcCliente tcCliente) {
		this.tcCliente = tcCliente;
	}





	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}





	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}





	public TcEstatusVenta getTcEstatusVenta() {
		return tcEstatusVenta;
	}





	public void setTcEstatusVenta(TcEstatusVenta tcEstatusVenta) {
		this.tcEstatusVenta = tcEstatusVenta;
	}





	public TcFormapago getTcFormapago() {
		return tcFormapago;
	}





	public void setTcFormapago(TcFormapago tcFormapago) {
		this.tcFormapago = tcFormapago;
	}





	public TwCaja getTwCaja() {
		return twCaja;
	}





	public void setTwCaja(TwCaja twCaja) {
		this.twCaja = twCaja;
	}





	public TcTipoVenta getTcTipoVenta() {
		return tcTipoVenta;
	}





	public void setTcTipoVenta(TcTipoVenta tcTipoVenta) {
		this.tcTipoVenta = tcTipoVenta;
	}







	@Override
	public String toString() {
		return "TvVentasFactura [nId=" + nId + ", nIdCliente=" + nIdCliente + ", nIdUsuario=" + nIdUsuario
				+ ", sFolioVenta=" + sFolioVenta + ", dFechaVenta=" + dFechaVenta + ", nTipoPago=" + nTipoPago
				+ ", nEstatus=" + nEstatus + ", nIdTipoVenta=" + nIdTipoVenta + ", formaPago=" + formaPago + ", idCaja="
				+ idCaja + ", idFactura=" + idFactura + ", nTotalVenta=" + nTotalVenta + ", tcCliente=" + tcCliente
				+ ", tcUsuario=" + tcUsuario + ", tcEstatusVenta=" + tcEstatusVenta + ", tcFormapago=" + tcFormapago
				+ ", twCaja=" + twCaja + ", tcTipoVenta=" + tcTipoVenta + "]";
	}	
	
	
	
	
	
}
