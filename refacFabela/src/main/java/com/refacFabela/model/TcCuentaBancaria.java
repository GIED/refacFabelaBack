package com.refacFabela.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tc_cuentas_bancarias")
public class TcCuentaBancaria {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "n_id")
	    private Long nId;

	    @Column(name = "s_banco", nullable = false)
	    private String sBanco;

	    @Column(name = "s_terminacion", nullable = false)
	    private String sTerminacion;

	    @Column(name = "n_estatus", nullable = false)
	    private Integer nEstatus;

	    @Column(name = "n_id_razon_social", nullable = false)
	    private Long nIdRazonSocial;

		public Long getnId() {
			return nId;
		}

		public void setnId(Long nId) {
			this.nId = nId;
		}

		public String getsBanco() {
			return sBanco;
		}

		public void setsBanco(String sBanco) {
			this.sBanco = sBanco;
		}

		public String getsTerminacion() {
			return sTerminacion;
		}

		public void setsTerminacion(String sTerminacion) {
			this.sTerminacion = sTerminacion;
		}

		public Integer getnEstatus() {
			return nEstatus;
		}

		public void setnEstatus(Integer nEstatus) {
			this.nEstatus = nEstatus;
		}

		public Long getnIdRazonSocial() {
			return nIdRazonSocial;
		}

		public void setnIdRazonSocial(Long nIdRazonSocial) {
			this.nIdRazonSocial = nIdRazonSocial;
		}

		@Override
		public String toString() {
			return "TcCuentaBancaria [nId=" + nId + ", sBanco=" + sBanco + ", sTerminacion=" + sTerminacion
					+ ", nEstatus=" + nEstatus + ", nIdRazonSocial=" + nIdRazonSocial + "]";
		}
	    
	    
	    public TcCuentaBancaria() {
	    	
	    }

}


