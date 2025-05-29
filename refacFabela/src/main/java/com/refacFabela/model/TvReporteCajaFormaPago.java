package com.refacFabela.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tv_reporte_caja_forma_pago")
@NamedQuery(name = "TvReporteCajaFormaPago.findAll", query = "SELECT t FROM TvReporteCajaFormaPago t")
public class TvReporteCajaFormaPago implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	 
	@Column(name = "s_forma_pago")
	private String sFormaPago;
	@Column(name = "n_id_caja") 
	private Long nIdCaja;	
	@Column(name = "n_total_forma_pago") 
	private BigDecimal nTotalFormaPago;
    
	public TvReporteCajaFormaPago() {
		
	}

	public String getsFormaPago() {
		return sFormaPago;
	}

	public void setsFormaPago(String sFormaPago) {
		this.sFormaPago = sFormaPago;
	}

	public Long getnIdCaja() {
		return nIdCaja;
	}

	public void setnIdCaja(Long nIdCaja) {
		this.nIdCaja = nIdCaja;
	}

	

	public BigDecimal getnTotalFormaPago() {
		return nTotalFormaPago;
	}

	public void setnTotalFormaPago(BigDecimal nTotalFormaPago) {
		this.nTotalFormaPago = nTotalFormaPago;
	}

	@Override
	public String toString() {
		return "TvReporteCajaFormaPago [sFormaPago=" + sFormaPago + ", nIdCaja=" + nIdCaja + ", nTotalFormaPago="
				+ nTotalFormaPago + "]";
	}

	
	
	
	
	

}
