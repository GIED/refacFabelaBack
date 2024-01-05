package com.refacFabela.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tw_maquina_cliente")
@NamedQuery(name = "TwMaquinaCliente.findAll", query = "SELECT t FROM TwMaquinaCliente t")
public class TwMaquinaCliente implements Serializable {
	private static final long serialVersionUID = 1L; 
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "n_id")
		private Long nId;
		@Column(name = "n_id_cliente")
		private Long nIdCliente;
		@Column(name = "s_serie")
		private String sSerie;
		@Column(name = "s_marca")
		private String sMarca;
		@Column(name = "s_observaciones")
		private String sObservaciones;
		@Column(name = "s_modelo")
		private String sModelo;
		
		
		public TwMaquinaCliente() {
			
		}


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


		public String getsSerie() {
			return sSerie;
		}


		public void setsSerie(String sSerie) {
			this.sSerie = sSerie;
		}


		public String getsMarca() {
			return sMarca;
		}


		public void setsMarca(String sMarca) {
			this.sMarca = sMarca;
		}


		public String getsModelo() {
			return sModelo;
		}


		public void setsModelo(String sModelo) {
			this.sModelo = sModelo;
		}


		public String getsObservaciones() {
			return sObservaciones;
		}


		public void setsObservaciones(String sObservaciones) {
			this.sObservaciones = sObservaciones;
		}
			
	

		
	

}
