package com.refacFabela.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "vw_factura_producto_balance")
public class VwFacturaProductoBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long nId;

    @Column(name = "s_folio_factura", length = 200)
    private String sFolioFactura;

    @Column(name = "n_id_proveedor")
    private Long nIdProveedor;

    @Column(name = "n_id_usuario")
    private Long nIdUsuario;

    @Column(name = "n_id_moneda")
    private Long nIdMoneda;

    @Column(name = "n_id_razon_social")
    private Long nIdRazonSocial;

    @Column(name = "d_fecha_inicio_factura")
    @Temporal(TemporalType.DATE)
    private Date dFechaInicioFactura;

    @Column(name = "n_estatus_factura_proveedor")
    private Integer nEstatusFacturaProveedor;

    @Column(name = "n_estatus_ingreso_almacen")
    private Integer nEstatusIngresoAlmacen;

    @Column(name = "n_total_productos")
    private Integer nTotalProductos;

    @Column(name = "n_total_no_parte")
    private Integer nTotalNoParte;
    
    
	@ManyToOne
	@JoinColumn(name = "n_id_proveedor", insertable = false, updatable = false)
	private TcProveedore tcProveedor;

	
	@ManyToOne
	@JoinColumn(name = "n_id_usuario", insertable = false, updatable = false)
	private TcUsuario tcUsuario;
	
	@ManyToOne
	@JoinColumn(name = "n_id_moneda", insertable = false, updatable = false)
	private TcMoneda tcMoneda;
	
	

	

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public String getsFolioFactura() {
		return sFolioFactura;
	}

	public void setsFolioFactura(String sFolioFactura) {
		this.sFolioFactura = sFolioFactura;
	}

	public Long getnIdProveedor() {
		return nIdProveedor;
	}

	public void setnIdProveedor(Long nIdProveedor) {
		this.nIdProveedor = nIdProveedor;
	}

	public Long getnIdUsuario() {
		return nIdUsuario;
	}

	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}


	public Long getnIdMoneda() {
		return nIdMoneda;
	}

	public void setnIdMoneda(Long nIdMoneda) {
		this.nIdMoneda = nIdMoneda;
	}

	public Long getnIdRazonSocial() {
		return nIdRazonSocial;
	}

	public void setnIdRazonSocial(Long nIdRazonSocial) {
		this.nIdRazonSocial = nIdRazonSocial;
	}

	public Date getdFechaInicioFactura() {
		return dFechaInicioFactura;
	}

	public void setdFechaInicioFactura(Date dFechaInicioFactura) {
		this.dFechaInicioFactura = dFechaInicioFactura;
	}

	public TcProveedore getTcProveedor() {
		return tcProveedor;
	}

	public void setTcProveedor(TcProveedore tcProveedor) {
		this.tcProveedor = tcProveedor;
	}

	public Integer getnEstatusFacturaProveedor() {
		return nEstatusFacturaProveedor;
	}

	public void setnEstatusFacturaProveedor(Integer nEstatusFacturaProveedor) {
		this.nEstatusFacturaProveedor = nEstatusFacturaProveedor;
	}

	public Integer getnEstatusIngresoAlmacen() {
		return nEstatusIngresoAlmacen;
	}

	public void setnEstatusIngresoAlmacen(Integer nEstatusIngresoAlmacen) {
		this.nEstatusIngresoAlmacen = nEstatusIngresoAlmacen;
	}

	public Integer getnTotalProductos() {
		return nTotalProductos;
	}

	public void setnTotalProductos(Integer nTotalProductos) {
		this.nTotalProductos = nTotalProductos;
	}

	public Integer getnTotalNoParte() {
		return nTotalNoParte;
	}

	public void setnTotalNoParte(Integer nTotalNoParte) {
		this.nTotalNoParte = nTotalNoParte;
	}


	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}

	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}
	

	public TcMoneda getTcMoneda() {
		return tcMoneda;
	}

	public void setTcMoneda(TcMoneda tcMoneda) {
		this.tcMoneda = tcMoneda;
	}

	@Override
	public String toString() {
		return "VwFacturaProductoBalance [nId=" + nId + ", sFolioFactura=" + sFolioFactura + ", nIdProveedor="
				+ nIdProveedor + ", nIdUsuario=" + nIdUsuario + ", nIdMoneda=" + nIdMoneda + ", nIdRazonSocial="
				+ nIdRazonSocial + ", dFechaInicioFactura=" + dFechaInicioFactura + ", nEstatusFacturaProveedor="
				+ nEstatusFacturaProveedor + ", nEstatusIngresoAlmacen=" + nEstatusIngresoAlmacen + ", nTotalProductos="
				+ nTotalProductos + ", nTotalNoParte=" + nTotalNoParte + ", tcProveedor=" + tcProveedor + ", tcUsuario="
				+ tcUsuario + ", tcMoneda=" + tcMoneda + "]";
	}





   

  
}
