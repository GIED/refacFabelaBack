package com.refacFabela.model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tw_saldo_utilizado")
@NamedQuery(name = "TwSaldoUtilizado.findAll", query = "SELECT t FROM TwSaldoUtilizado t")
public class TwSaldoUtilizado implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name = "n_id_venta")
	private Long nIdVenta;

	@Column(name = "n_saldo_total")
	private BigDecimal nSaldoTotal;

	@Column(name = "n_saldo_utilizado")
	private BigDecimal nSaldoUtilizado;
	
	@Column(name = "n_id_usuario")
	private Long nIdUsuario;
	
	@Column(name = "n_estatus")
	private Boolean nEstatus;
	
	@Column(name = "d_fecha")
	private Date dFecha;
	
	@Column(name = "n_id_caja")
	private Long nIdCaja;
	
	@Column(name = "n_id_venta_utilizado")
	private Long nIdVentaUtilizado;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "n_id_venta", updatable = false, insertable = false, nullable = false)
	private TwVenta twVenta;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "n_id_caja", updatable = false, insertable = false, nullable = false)
	private TwCaja twCaja;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "n_id_venta_utilizado", updatable = false, insertable = false, nullable = false)
	private TwVenta twVentaUtilizado;
	

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Long getnIdVenta() {
		return nIdVenta;
	}

	public void setnIdVenta(Long nIdVenta) {
		this.nIdVenta = nIdVenta;
	}


	public BigDecimal getnSaldoTotal() {
		return nSaldoTotal;
	}

	public void setnSaldoTotal(BigDecimal nSaldoTotal) {
		this.nSaldoTotal = nSaldoTotal;
	}

	public BigDecimal getnSaldoUtilizado() {
		return nSaldoUtilizado;
	}

	public void setnSaldoUtilizado(BigDecimal nSaldoUtilizado) {
		this.nSaldoUtilizado = nSaldoUtilizado;
	}

	public Long getnIdUsuario() {
		return nIdUsuario;
	}

	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}

	public Boolean getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(Boolean nEstatus) {
		this.nEstatus = nEstatus;
	}

	public Date getdFecha() {
		return dFecha;
	}

	public void setdFecha(Date dFecha) {
		this.dFecha = dFecha;
	}

	public Long getnIdCaja() {
		return nIdCaja;
	}

	public void setnIdCaja(Long nIdCaja) {
		this.nIdCaja = nIdCaja;
	}

	public TwVenta getTwVenta() {
		return twVenta;
	}

	public void setTwVenta(TwVenta twVenta) {
		this.twVenta = twVenta;
	}

	public TwCaja getTwCaja() {
		return twCaja;
	}

	public void setTwCaja(TwCaja twCaja) {
		this.twCaja = twCaja;
	}
		
	

	public Long getnIdVentaUtilizado() {
		return nIdVentaUtilizado;
	}

	public void setnIdVentaUtilizado(Long nIdVentaUtilizado) {
		this.nIdVentaUtilizado = nIdVentaUtilizado;
	}

	public TwVenta getTwVentaUtilizado() {
		return twVentaUtilizado;
	}

	public void setTwVentaUtilizado(TwVenta twVentaUtilizado) {
		this.twVentaUtilizado = twVentaUtilizado;
	}

	@Override
	public String toString() {
		return "TwSaldosFavor [nId=" + nId + ", nIdVenta=" + nIdVenta + ", nSaldoTotal=" + nSaldoTotal
				+ ", nSaldoUtilizado=" + nSaldoUtilizado + ", nIdUsuario=" + nIdUsuario + ", nEstatus=" + nEstatus
				+ ", dFecha=" + dFecha + ", nIdCaja=" + nIdCaja + ", twVenta=" + twVenta + ", twCaja=" + twCaja + "]";
	}	
	
	
	

}
