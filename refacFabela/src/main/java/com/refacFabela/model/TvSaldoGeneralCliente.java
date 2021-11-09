package com.refacFabela.model;

import java.io.Serializable;
import java.util.Date;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
	@Table(name = "tv_saldogeneralclie")
	@NamedQuery(name = "TvSaldoGeneralCliente.findAll", query = "SELECT t FROM TvSaldoGeneralCliente t")
public class TvSaldoGeneralCliente implements Serializable {
		private static final long serialVersionUID = 1L;
	
		@Id	
		@Column(name = "n_idCliente")
		private Long nIdCliente;
	
		@Column(name = "n_limiteCredito")
		private double nLimiteCredito;

		@Column(name = "n_saldo_total")
		private double nSaldoTotal;

		@Column(name = "n_credito_disponible")
		private double nCreditoDisponible;
		
		
		@ManyToOne
		@JoinColumn(name = "n_idCliente", referencedColumnName = "n_id", updatable = false, insertable = false)
		private TcCliente tcCliente;
		
		
		

		public  TvSaldoGeneralCliente() {
		}

		


		public TcCliente getTcCliente() {
			return tcCliente;
		}




		public void setTcCliente(TcCliente tcCliente) {
			this.tcCliente = tcCliente;
		}




		public Long getnIdCliente() {
			return nIdCliente;
		}




		public void setnIdCliente(Long nIdCliente) {
			this.nIdCliente = nIdCliente;
		}




		public double getnLimiteCredito() {
			return nLimiteCredito;
		}




		public void setnLimiteCredito(double nLimiteCredito) {
			this.nLimiteCredito = nLimiteCredito;
		}




		public double getnSaldoTotal() {
			return nSaldoTotal;
		}




		public void setnSaldoTotal(double nSaldoTotal) {
			this.nSaldoTotal = nSaldoTotal;
		}




		public double getnCreditoDisponible() {
			return nCreditoDisponible;
		}




		public void setnCreditoDisponible(double nCreditoDisponible) {
			this.nCreditoDisponible = nCreditoDisponible;
		}


		
		
		
	}


