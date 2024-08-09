package com.refacFabela.model;

import java.io.Serializable;

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
    private Double totalFacturas;

    @Column(name = "total_abonos")
    private Double totalAbonos;

    @Column(name = "saldo_pendiente_pago")
    private Double saldoPendientePago;

    @Column(name = "total_por_pagar")
    private Double totalPorPagar;

    @Column(name = "total_vencidas")
    private Double totalVencidas;
    
    
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

    public Double getTotalFacturas() {
        return totalFacturas;
    }

    public void setTotalFacturas(Double totalFacturas) {
        this.totalFacturas = totalFacturas;
    }

    public Double getTotalAbonos() {
        return totalAbonos;
    }

    public void setTotalAbonos(Double totalAbonos) {
        this.totalAbonos = totalAbonos;
    }

    public Double getSaldoPendientePago() {
        return saldoPendientePago;
    }

    public void setSaldoPendientePago(Double saldoPendientePago) {
        this.saldoPendientePago = saldoPendientePago;
    }

    public Double getTotalPorPagar() {
        return totalPorPagar;
    }

    public void setTotalPorPagar(Double totalPorPagar) {
        this.totalPorPagar = totalPorPagar;
    }

    public Double getTotalVencidas() {
        return totalVencidas;
    }

    public void setTotalVencidas(Double totalVencidas) {
        this.totalVencidas = totalVencidas;
    }
	
	

}
