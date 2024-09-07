package com.refacFabela.controller;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import com.refacFabela.model.TwFacturasProveedor;

@Entity
@Table(name = "vw_balance_facturas_proveedor")
@NamedQuery(name = "BalanceAbonoProveedor.findAll", query = "SELECT t FROM BalanceAbonoProveedor t")
public class BalanceAbonoProveedor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name = "estatus_factura")
	private String estatusFactura;

	@Column(name = "total_abonos")
	private BigDecimal totalAbonos;
	
	@ManyToOne()
	@JoinColumn(name = "n_id", referencedColumnName = "n_id", updatable = false, insertable = false)
	private TwFacturasProveedor twFacturasProveedor;

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public String getEstatusFactura() {
		return estatusFactura;
	}

	public void setEstatusFactura(String estatusFactura) {
		this.estatusFactura = estatusFactura;
	}

	public BigDecimal getTotalAbonos() {
		return totalAbonos;
	}

	public void setTotalAbonos(BigDecimal totalAbonos) {
		this.totalAbonos = totalAbonos;
	}

	public TwFacturasProveedor getTwFacturasProveedor() {
		return twFacturasProveedor;
	}

	public void setTwFacturasProveedor(TwFacturasProveedor twFacturasProveedor) {
		this.twFacturasProveedor = twFacturasProveedor;
	}

	@Override
	public String toString() {
		return "BalanceAbonoProveedor [nId=" + nId + ", estatusFactura=" + estatusFactura + ", totalAbonos="
				+ totalAbonos + ", twFacturasProveedor=" + twFacturasProveedor + "]";
	}
	
	public BalanceAbonoProveedor() {
		
	}

	
	
	

}
