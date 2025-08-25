package com.refacFabela.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tw_carrito_compra_pedido")
public class TwCarritoCompraPedido {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long nId;

    @Column(name = "n_id_usuario")
    private Long nIdUsuario;
    
    
    @Column(name = "n_id_producto")
    private Long nIdProducto;
    
    @Column(name = "n_id_proveedor")
    private Long nIdProveedor;

    @Column(name = "n_cantidad")
    private Integer nCantidad;
    
    
    @Column(name = "d_fecha_registro")
    private LocalDateTime dFechaRegistro;    
  

    @Column(name = "n_estatus")
    private Integer nEstatus;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "n_id_usuario", referencedColumnName = "n_id", insertable = false, updatable = false)
    private TcUsuario twUsuario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "n_id_producto", referencedColumnName = "n_id", insertable = false, updatable = false)
    private TcProducto tcProducto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "n_id_proveedor", referencedColumnName = "n_id", insertable = false, updatable = false)
    private TcProveedore TcProveedor;
    
    public TwCarritoCompraPedido() {
    	
    }

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Long getnIdUsuario() {
		return nIdUsuario;
	}

	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}

	

	public Long getnIdProducto() {
		return nIdProducto;
	}

	public void setnIdProducto(Long nIdProducto) {
		this.nIdProducto = nIdProducto;
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

	

	public Integer getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(Integer nEstatus) {
		this.nEstatus = nEstatus;
	}

	public TcUsuario getTwUsuario() {
		return twUsuario;
	}

	public void setTwUsuario(TcUsuario twUsuario) {
		this.twUsuario = twUsuario;
	}

	public TcProducto getTcProducto() {
		return tcProducto;
	}

	public void setTcProducto(TcProducto tcProducto) {
		this.tcProducto = tcProducto;
	}

	

	public Long getnIdProveedor() {
		return nIdProveedor;
	}

	public void setnIdProveedor(Long nIdProveedor) {
		this.nIdProveedor = nIdProveedor;
	}

	public TcProveedore getTcProveedor() {
		return TcProveedor;
	}

	public void setTcProveedor(TcProveedore tcProveedor) {
		TcProveedor = tcProveedor;
	}

	@Override
	public String toString() {
		return "TwCarritoCompraPedido [nId=" + nId + ", nIdUsuario=" + nIdUsuario + ", nIdProducto=" + nIdProducto
				+ ", nIdProveedor=" + nIdProveedor + ", nCantidad=" + nCantidad + ", dFechaRegistro=" + dFechaRegistro
				+ ", nEstatus=" + nEstatus + ", twUsuario=" + twUsuario + ", tcProducto=" + tcProducto
				+ ", TcProveedor=" + TcProveedor + "]";
	}


    
    

}
