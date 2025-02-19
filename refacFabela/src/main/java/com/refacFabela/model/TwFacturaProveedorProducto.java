package com.refacFabela.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tw_factura_proveedor_producto")
public class TwFacturaProveedorProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long nId;

    @Column(name = "n_id_factura_proveedor")
    private Long nIdFacturaProveedor;

    @Column(name = "n_id_producto")
    private Long nIdProducto;

    @Column(name = "n_id_usuario")
    private Long nIdUsuario;

    @Column(name = "n_cantidad")
    private Integer nCantidad;

    @Column(name = "d_fecha_registro")
    private LocalDateTime dFechaRegistro;

    @Column(name = "d_fecha_cierre_ingreso")
    private LocalDateTime dFechaCierreIngreso;  

    @Column(name = "n_id_marca")
    private Long nIdMarca;

    @Column(name = "n_precio_unitario")
    private BigDecimal nPrecioUnitario;

    @Column(name = "n_estatus")
    private Long nEstatus;

    @Column(name = "s_motivo_cierre", length = 500)
    private String sMotivoCierre;    
    
    @ManyToOne
	@JoinColumn(name = "n_id_producto", insertable=false, updatable=false)
	private TcProducto tcProducto;
    
    @ManyToOne
   	@JoinColumn(name = "n_id_usuario", insertable=false, updatable=false)
   	private TcUsuario tcUsuario;
    
    @ManyToOne
   	@JoinColumn(name = "n_id_marca", insertable=false, updatable=false)
   	private TcMarca tcMarca;

    
    /*get and set*/
    
	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Long getnIdFacturaProveedor() {
		return nIdFacturaProveedor;
	}

	public void setnIdFacturaProveedor(Long nIdFacturaProveedor) {
		this.nIdFacturaProveedor = nIdFacturaProveedor;
	}

	public Long getnIdProducto() {
		return nIdProducto;
	}

	public void setnIdProducto(Long nIdProducto) {
		this.nIdProducto = nIdProducto;
	}

	public Long getnIdUsuario() {
		return nIdUsuario;
	}

	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}

	public Integer getnCantidad() {
		return nCantidad;
	}

	public void setnCantidad(Integer nCantidad) {
		this.nCantidad = nCantidad;
	}

	public LocalDateTime getdFechaRegistro() {
		return dFechaRegistro;
	}

	public void setdFechaRegistro(LocalDateTime dFechaRegistro) {
		this.dFechaRegistro = dFechaRegistro;
	}

	public LocalDateTime getdFechaCierreIngreso() {
		return dFechaCierreIngreso;
	}

	public void setdFechaCierreIngreso(LocalDateTime dFechaCierreIngreso) {
		this.dFechaCierreIngreso = dFechaCierreIngreso;
	}

	public Long getnIdMarca() {
		return nIdMarca;
	}

	public void setnIdMarca(Long nIdMarca) {
		this.nIdMarca = nIdMarca;
	}

	public BigDecimal getnPrecioUnitario() {
		return nPrecioUnitario;
	}

	public void setnPrecioUnitario(BigDecimal nPrecioUnitario) {
		this.nPrecioUnitario = nPrecioUnitario;
	}

	public Long getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(Long nEstatus) {
		this.nEstatus = nEstatus;
	}

	public String getsMotivoCierre() {
		return sMotivoCierre;
	}

	public void setsMotivoCierre(String sMotivoCierre) {
		this.sMotivoCierre = sMotivoCierre;
	}

	public TcProducto getTcProducto() {
		return tcProducto;
	}

	public void setTcProducto(TcProducto tcProducto) {
		this.tcProducto = tcProducto;
	}

	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}

	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}

	public TcMarca getTcMarca() {
		return tcMarca;
	}

	public void setTcMarca(TcMarca tcMarca) {
		this.tcMarca = tcMarca;
	}

	@Override
	public String toString() {
		return "TwFacturaProveedorProducto [nId=" + nId + ", nIdFacturaProveedor=" + nIdFacturaProveedor
				+ ", nIdProducto=" + nIdProducto + ", nIdUsuario=" + nIdUsuario + ", nCantidad=" + nCantidad
				+ ", dFechaRegistro=" + dFechaRegistro + ", dFechaCierreIngreso=" + dFechaCierreIngreso + ", nIdMarca="
				+ nIdMarca + ", nPrecioUnitario=" + nPrecioUnitario + ", nEstatus=" + nEstatus + ", sMotivoCierre="
				+ sMotivoCierre + ", tcProducto=" + tcProducto + ", tcUsuario=" + tcUsuario + ", tcMarca=" + tcMarca
				+ "]";
	}
    
    
    
    
    
}
