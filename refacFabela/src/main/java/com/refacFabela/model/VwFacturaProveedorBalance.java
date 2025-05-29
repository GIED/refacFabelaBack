package com.refacFabela.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.refacFabela.dto.ProveedorMonedaId;

@Entity
@Table(name = "vw_factura_proveedor_balance")
@NamedQuery(name = "VwFacturaProveedorBalance.findAll", query = "SELECT t FROM VwFacturaProveedorBalance t")
public class VwFacturaProveedorBalance implements Serializable {
	private static final long serialVersionUID = 1L; 
	
	@EmbeddedId
    private ProveedorMonedaId id;

    @Column(name = "s_moneda")
    private String sMoneda;

    @Column(name = "total_facturas")
    private BigDecimal totalFacturas;

    @Column(name = "total_abonos")
    private BigDecimal totalAbonos;

    @Column(name = "saldo_pendiente_pago")
    private BigDecimal saldoPendientePago;

    @Column(name = "total_por_pagar")
    private BigDecimal totalPorPagar;

    @Column(name = "total_vencidas")
    private BigDecimal totalVencidas;
    
    
    @ManyToOne
	@JoinColumn(name = "n_id_proveedor", insertable=false, updatable=false)
	private TcProveedore tcProveedore;

    // Getters y setters
    
    

    public ProveedorMonedaId getId() {
        return id;
    }

    public TcProveedore getTcProveedore() {
		return tcProveedore;
	}

	public void setTcProveedore(TcProveedore tcProveedore) {
		this.tcProveedore = tcProveedore;
	}

	public void setId(ProveedorMonedaId id) {
        this.id = id;
    }

    public String getsMoneda() {
        return sMoneda;
    }

    public void setsMoneda(String sMoneda) {
        this.sMoneda = sMoneda;
    }

	public BigDecimal getTotalFacturas() {
		return totalFacturas;
	}

	public void setTotalFacturas(BigDecimal totalFacturas) {
		this.totalFacturas = totalFacturas;
	}

	public BigDecimal getTotalAbonos() {
		return totalAbonos;
	}

	public void setTotalAbonos(BigDecimal totalAbonos) {
		this.totalAbonos = totalAbonos;
	}

	public BigDecimal getSaldoPendientePago() {
		return saldoPendientePago;
	}

	public void setSaldoPendientePago(BigDecimal saldoPendientePago) {
		this.saldoPendientePago = saldoPendientePago;
	}

	public BigDecimal getTotalPorPagar() {
		return totalPorPagar;
	}

	public void setTotalPorPagar(BigDecimal totalPorPagar) {
		this.totalPorPagar = totalPorPagar;
	}

	public BigDecimal getTotalVencidas() {
		return totalVencidas;
	}

	public void setTotalVencidas(BigDecimal totalVencidas) {
		this.totalVencidas = totalVencidas;
	}

   
	
	

}
