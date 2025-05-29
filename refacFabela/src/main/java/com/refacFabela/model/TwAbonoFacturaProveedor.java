package com.refacFabela.model;

import java.io.Serializable;
import java.math.BigDecimal;
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

@Entity
@Table(name="tw_abono_factura_proveedor")
@NamedQuery(name = "TwAbonoFacturaProveedor.findAll", query = "SELECT t FROM TwAbonoFacturaProveedor t")
public class TwAbonoFacturaProveedor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "n_id", nullable = false)
	    private Integer nId;  // Este campo será la clave primaria autogenerada

	    @Column(name = "n_id_factura_proveedor")
	    private Long nIdFacturaProveedor;  // Factura Proveedor
	   
	    @JsonSerialize(using = ToStringSerializer.class)
	    @Column(name = "n_monto_abono", precision = 10, scale = 2)
	    private BigDecimal nMontoAbono;  // Monto del abono

	    @Column(name = "d_fecha_abono")	  
	    private BigDecimal dFechaAbono;  // Fecha del abono

	    @Column(name = "n_estatus_abono")
	    private Integer nEstatusAbono;  // Estatus del abono

	    @Column(name = "n_id_usuario")
	    private Integer nIdUsuario;  // ID del usuario que realizó el abono

	    @Column(name = "s_nota", length = 500)
	    private String sNota;  // Nota sobre el abono

	    @Column(name = "n_id_forma_pago")
	    private Integer nIdFormaPago;  // ID de la forma de pago
	    
	    @Column(name = "n_id_cuenta_bancaria")
	    private Long nIdCuentaBancaria;  // ID de la forma de pago
	    
	    
	    @ManyToOne()
		@JoinColumn(name = "n_id_factura_proveedor", referencedColumnName = "n_id", updatable = false, insertable = false)
		private TwFacturasProveedor twFacturasProveedor;
	    
	    @ManyToOne()
	 		@JoinColumn(name = "n_id_cuenta_bancaria", referencedColumnName = "n_id", updatable = false, insertable = false)
	 		private TcCuentaBancaria TcCuentaBancaria;
	    
	    
	    

	
		public Long getnIdCuentaBancaria() {
			return nIdCuentaBancaria;
		}

		public void setnIdCuentaBancaria(Long nIdCuentaBancaria) {
			this.nIdCuentaBancaria = nIdCuentaBancaria;
		}

		public TcCuentaBancaria getTcCuentaBancaria() {
			return TcCuentaBancaria;
		}

		public void setTcCuentaBancaria(TcCuentaBancaria tcCuentaBancaria) {
			TcCuentaBancaria = tcCuentaBancaria;
		}

		public TwFacturasProveedor getTwFacturasProveedor() {
			return twFacturasProveedor;
		}

		public void setTwFacturasProveedor(TwFacturasProveedor twFacturasProveedor) {
			this.twFacturasProveedor = twFacturasProveedor;
		}

		// Getters y Setters
	    public Integer getnId() {
	        return nId;
	    }

	    public void setnId(Integer nId) {
	        this.nId = nId;
	    }

	    

	    public Long getnIdFacturaProveedor() {
			return nIdFacturaProveedor;
		}

		public void setnIdFacturaProveedor(Long nIdFacturaProveedor) {
			this.nIdFacturaProveedor = nIdFacturaProveedor;
		}

		public BigDecimal getnMontoAbono() {
	        return nMontoAbono;
	    }

	    public void setnMontoAbono(BigDecimal nMontoAbono) {
	        this.nMontoAbono = nMontoAbono;
	    }

	  

	    public BigDecimal getdFechaAbono() {
			return dFechaAbono;
		}

		public void setdFechaAbono(BigDecimal dFechaAbono) {
			this.dFechaAbono = dFechaAbono;
		}

		public Integer getnEstatusAbono() {
	        return nEstatusAbono;
	    }

	    public void setnEstatusAbono(Integer nEstatusAbono) {
	        this.nEstatusAbono = nEstatusAbono;
	    }

	    public Integer getnIdUsuario() {
	        return nIdUsuario;
	    }

	    public void setnIdUsuario(Integer nIdUsuario) {
	        this.nIdUsuario = nIdUsuario;
	    }

	    public String getsNota() {
	        return sNota;
	    }

	    public void setsNota(String sNota) {
	        this.sNota = sNota;
	    }

	    public Integer getnIdFormaPago() {
	        return nIdFormaPago;
	    }

	    public void setnIdFormaPago(Integer nIdFormaPago) {
	        this.nIdFormaPago = nIdFormaPago;
	    }
	    
	   private  TwAbonoFacturaProveedor() {
		   
	   }

	@Override
	public String toString() {
		return "TwAbonoFacturaProveedor [nId=" + nId + ", nIdFacturaProveedor=" + nIdFacturaProveedor + ", nMontoAbono="
				+ nMontoAbono + ", dFechaAbono=" + dFechaAbono + ", nEstatusAbono=" + nEstatusAbono + ", nIdUsuario="
				+ nIdUsuario + ", sNota=" + sNota + ", nIdFormaPago=" + nIdFormaPago + "]";
	}
	   
	   

}
