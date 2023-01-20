package com.refacFabela.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tc_productos")
public class TcProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;

	@Column(name = "d_fecha")
	private Date dFecha = new Date();

	@Column(name = "n_estatus")
	private int nEstatus;

	@Column(name = "n_idCategoriaGeneral")
	private Long nIdCategoriaGeneral;

	@Column(name = "n_idcategoria")
	private Long nIdCategoria;

	@Column(name = "n_precio")
	private Double nPrecio;

	@Column(name = "s_descripcion")
	private String sDescripcion;

	@Column(name = "s_marca")
	private String sMarca;

	@Column(name = "s_moneda")
	private String sMoneda;

	@Column(name = "s_no_parte")
	private String sNoParte;

	@Column(name = "s_producto")
	private String sProducto;

	@Column(name = "n_idusuario")
	private Long nIdusuario;

	@Column(name = "n_IdGanancia")
	private Long nIdGanancia;

	@Column(name = "n_idclavesat")
	private Long nIdclavesat;

	@Column(name = "n_precio_peso")
	private Double nPrecioPeso;

	@Column(name = "n_precio_sin_iva")
	private Double nPrecioSinIva;

	@Column(name = "n_precio_con_iva")
	private Double nPrecioConIva;
	
	@Column(name = "s_id_bar")
	private String sIdBar;
	
	@Column(name = "n_precio_iva")
	private Double nPrecioIva;

	// bi-directional many-to-one association to TcCategoria
	@ManyToOne()
	@JoinColumn(name = "n_idcategoria", referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcCategoria tcCategoria;

	@ManyToOne()
	@JoinColumn(name = "n_idCategoriaGeneral", referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcCategoriaGeneral tcCategoriaGeneral;

	// bi-directional many-to-one association to TcClavesat
	@ManyToOne()
	@JoinColumn(name = "n_idclavesat", referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcClavesat tcClavesat;

	// bi-directional many-to-one association to TcGanancia
	@ManyToOne()
	@JoinColumn(name = "n_IdGanancia", referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcGanancia tcGanancia;

	// bi-directional many-to-one association to TcUsuario
	@ManyToOne()
	@JoinColumn(name = "n_idusuario", referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcUsuario tcUsuario;
	
	//bi-directional many-to-one association to TwProductosAlternativo
	//@OneToMany(mappedBy="tcProducto")
	//private List<TwProductosAlternativo> twProductosAlternativos;



	// bi-directional many-to-one association to TwCotizacionesProducto
//	@OneToMany(mappedBy="tcProducto")
//	private List<TwCotizacionesProducto> twCotizacionesProductos;
//
//	//bi-directional many-to-one association to TwPedido
//	@OneToMany(mappedBy="tcProducto")
//	private List<TwPedido> twPedidos;
//
	//bi-directional many-to-one association to TwProductobodega
//	@OneToMany(mappedBy="tcProducto")
//	private List<TwProductobodega> twProductobodegas;
//
//	
//
//	//bi-directional many-to-one association to TwVentasProducto
//	@OneToMany(mappedBy="tcProducto")
//	private List<TwVentasProducto> twVentasProductos;

	public TcProducto() {
	}


	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Double getnPrecioPeso() {
		return nPrecioPeso;
	}

	public void setnPrecioPeso(Double nPrecioPeso) {
		this.nPrecioPeso = nPrecioPeso;
	}

	public Double getnPrecioSinIva() {
		return nPrecioSinIva;
	}

	public void setnPrecioSinIva(Double nPrecioSinIva) {
		this.nPrecioSinIva = nPrecioSinIva;
	}

	public Double getnPrecioConIva() {
		return nPrecioConIva;
	}

	public void setnPrecioConIva(Double nPrecioConIva) {
		this.nPrecioConIva = nPrecioConIva;
	}

	public Date getdFecha() {
		return dFecha;
	}

	public void setdFecha(Date dFecha) {
		this.dFecha = dFecha;
	}

	public int getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}

	public Long getnIdCategoriaGeneral() {
		return nIdCategoriaGeneral;
	}

	public void setnIdCategoriaGeneral(Long nIdCategoriaGeneral) {
		this.nIdCategoriaGeneral = nIdCategoriaGeneral;
	}

	public Long getnIdCategoria() {
		return nIdCategoria;
	}

	public void setnIdCategoria(Long nIdCategoria) {
		this.nIdCategoria = nIdCategoria;
	}

	public Double getnPrecio() {
		return nPrecio;
	}

	public void setnPrecio(Double nPrecio) {
		this.nPrecio = nPrecio;
	}

	public String getsDescripcion() {
		return sDescripcion;
	}

	public void setsDescripcion(String sDescripcion) {
		this.sDescripcion = sDescripcion;
	}

	public String getsMarca() {
		return sMarca;
	}

	public void setsMarca(String sMarca) {
		this.sMarca = sMarca;
	}

	public String getsMoneda() {
		return sMoneda;
	}

	public void setsMoneda(String sMoneda) {
		this.sMoneda = sMoneda;
	}

	public String getsNoParte() {
		return sNoParte;
	}

	public void setsNoParte(String sNoParte) {
		this.sNoParte = sNoParte;
	}

	public String getsProducto() {
		return sProducto;
	}

	public void setsProducto(String sProducto) {
		this.sProducto = sProducto;
	}

	public TcCategoria getTcCategoria() {
		return tcCategoria;
	}

	public void setTcCategoria(TcCategoria tcCategoria) {
		this.tcCategoria = tcCategoria;
	}

	public TcCategoriaGeneral getTcCategoriaGeneral() {
		return tcCategoriaGeneral;
	}

	public void setTcCategoriaGeneral(TcCategoriaGeneral tcCategoriaGeneral) {
		this.tcCategoriaGeneral = tcCategoriaGeneral;
	}

	public TcClavesat getTcClavesat() {
		return tcClavesat;
	}

	public void setTcClavesat(TcClavesat tcClavesat) {
		this.tcClavesat = tcClavesat;
	}

	public TcGanancia getTcGanancia() {
		return tcGanancia;
	}

	public void setTcGanancia(TcGanancia tcGanancia) {
		this.tcGanancia = tcGanancia;
	}

	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}

	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}

	public Long getnIdusuario() {
		return nIdusuario;
	}

	public void setnIdusuario(Long nIdusuario) {
		this.nIdusuario = nIdusuario;
	}

	public Long getnIdGanancia() {
		return nIdGanancia;
	}

	public void setnIdGanancia(Long nIdGanancia) {
		this.nIdGanancia = nIdGanancia;
	}

	public Long getnIdclavesat() {
		return nIdclavesat;
	}

	public void setnIdclavesat(Long nIdclavesat) {
		this.nIdclavesat = nIdclavesat;
	}
	

	public String getsIdBar() {
		return sIdBar;
	}


	public void setsIdBar(String sIdBar) {
		this.sIdBar = sIdBar;
	}
	
	
	


	public Double getnPrecioIva() {
		return nPrecioIva;
	}


	public void setnPrecioIva(Double nPrecioIva) {
		this.nPrecioIva = nPrecioIva;
	}


	@Override
	public String toString() {
		return "TcProducto [nId=" + nId + ", dFecha=" + dFecha + ", nEstatus=" + nEstatus + ", nIdCategoriaGeneral="
				+ nIdCategoriaGeneral + ", nIdCategoria=" + nIdCategoria + ", nPrecio=" + nPrecio + ", sDescripcion="
				+ sDescripcion + ", sMarca=" + sMarca + ", sMoneda=" + sMoneda + ", sNoParte=" + sNoParte
				+ ", sProducto=" + sProducto + ", nIdusuario=" + nIdusuario + ", nIdGanancia=" + nIdGanancia
				+ ", nIdclavesat=" + nIdclavesat + ", nPrecioPeso=" + nPrecioPeso + ", nPrecioSinIva=" + nPrecioSinIva
				+ ", nPrecioConIva=" + nPrecioConIva + ", sIdBar=" + sIdBar + ", nPrecioIva=" + nPrecioIva
				+ ", tcCategoria=" + tcCategoria + ", tcCategoriaGeneral=" + tcCategoriaGeneral + ", tcClavesat="
				+ tcClavesat + ", tcGanancia=" + tcGanancia + ", tcUsuario=" + tcUsuario + "]";
	}


	

}