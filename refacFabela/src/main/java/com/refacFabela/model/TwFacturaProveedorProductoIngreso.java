package com.refacFabela.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "tw_factura_proveedor_producto_ingreso")
public class TwFacturaProveedorProductoIngreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long nId;

    @Column(name = "n_id_factura_proveedor_producto")
    private Long nIdFacturaProveedorProducto;

    @Column(name = "n_cantidad")
    private Integer nCantidad;

    @Column(name = "n_id_usuario")
    private Long nIdUsuario;

    @Column(name = "d_fecha_ingreso",  columnDefinition = "TIMESTAMP(0)")
    private Instant dFechaIngreso;

    @Column(name = "n_estatus")
    private Integer nEstatus;

    @Column(name = "n_id_bodega")
    private Long nIdBodega;

    @Column(name = "n_id_anaquel")
    private Long nIdAnaquel;

    @Column(name = "n_id_nivel")
    private Long nIdNivel;
    
    
    @ManyToOne
   	@JoinColumn(name = "n_id_factura_proveedor_producto",  insertable=false, updatable=false)
   	private TwFacturaProveedorProducto twFacturaProveedorProducto;
    
    @ManyToOne
   	@JoinColumn(name = "n_id_bodega",  insertable=false, updatable=false)
   	private TcBodega tcBodega;
    
    @ManyToOne
   	@JoinColumn(name = "n_id_anaquel",  insertable=false, updatable=false)
   	private TcAnaquel tcAnaquel;
    
    @ManyToOne
   	@JoinColumn(name = "n_id_nivel",  insertable=false, updatable=false)
   	private TcNivel tcNivel;
    
    @ManyToOne
   	@JoinColumn(name = "n_id_usuario",  insertable=false, updatable=false)
   	private TcUsuario tcUsuario;
       
    
    
    
    

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Long getnIdFacturaProveedorProducto() {
		return nIdFacturaProveedorProducto;
	}

	public void setnIdFacturaProveedorProducto(Long nIdFacturaProveedorProducto) {
		this.nIdFacturaProveedorProducto = nIdFacturaProveedorProducto;
	}

	public Integer getnCantidad() {
		return nCantidad;
	}

	public void setnCantidad(Integer nCantidad) {
		this.nCantidad = nCantidad;
	}

	public Long getnIdUsuario() {
		return nIdUsuario;
	}

	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}

	public Instant getdFechaIngreso() {
		return dFechaIngreso;
	}

	public void setdFechaIngreso(Instant dFechaIngreso) {
		this.dFechaIngreso = dFechaIngreso;
	}

	public Integer getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(Integer nEstatus) {
		this.nEstatus = nEstatus;
	}

	public Long getnIdBodega() {
		return nIdBodega;
	}

	public void setnIdBodega(Long nIdBodega) {
		this.nIdBodega = nIdBodega;
	}

	public Long getnIdAnaquel() {
		return nIdAnaquel;
	}

	public void setnIdAnaquel(Long nIdAnaquel) {
		this.nIdAnaquel = nIdAnaquel;
	}

	public Long getnIdNivel() {
		return nIdNivel;
	}

	public void setnIdNivel(Long nIdNivel) {
		this.nIdNivel = nIdNivel;
	}

	public TwFacturaProveedorProducto getTwFacturaProveedorProducto() {
		return twFacturaProveedorProducto;
	}

	public void setTwFacturaProveedorProducto(TwFacturaProveedorProducto twFacturaProveedorProducto) {
		this.twFacturaProveedorProducto = twFacturaProveedorProducto;
	}

	public TcBodega getTcBodega() {
		return tcBodega;
	}

	public void setTcBodega(TcBodega tcBodega) {
		this.tcBodega = tcBodega;
	}

	public TcAnaquel getTcAnaquel() {
		return tcAnaquel;
	}

	public void setTcAnaquel(TcAnaquel tcAnaquel) {
		this.tcAnaquel = tcAnaquel;
	}

	public TcNivel getTcNivel() {
		return tcNivel;
	}

	public void setTcNivel(TcNivel tcNivel) {
		this.tcNivel = tcNivel;
	}

	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}

	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}

	@Override
	public String toString() {
		return "TwFacturaProveedorProductoIngreso [nId=" + nId + ", nIdFacturaProveedorProducto="
				+ nIdFacturaProveedorProducto + ", nCantidad=" + nCantidad + ", nIdUsuario=" + nIdUsuario
				+ ", dFechaIngreso=" + dFechaIngreso + ", nEstatus=" + nEstatus + ", nIdBodega=" + nIdBodega
				+ ", nIdAnaquel=" + nIdAnaquel + ", nIdNivel=" + nIdNivel + ", twFacturaProveedorProducto="
				+ twFacturaProveedorProducto + ", tcBodega=" + tcBodega + ", tcAnaquel=" + tcAnaquel + ", tcNivel="
				+ tcNivel + ", tcUsuario=" + tcUsuario + "]";
	}
	
	
	

   
    
    

    
}