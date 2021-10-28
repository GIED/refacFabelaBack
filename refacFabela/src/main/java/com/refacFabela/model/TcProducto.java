package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;




@Entity
@Table(name="tc_productos")
public class TcProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Column(name="d_fecha")
	private Date dFecha= new Date();

	@Column(name="n_estatus")
	private int nEstatus;

	@Column(name="n_idCategoriaGeneral")
	private int n_idCategoriaGeneral;
	
	@Column(name="n_idcategoria")
	private int n_idCategoria;

	@Column(name="n_precio")
	private double nPrecio;

	@Column(name="s_descripcion")
	private String sDescripcion;

	@Column(name="s_marca")
	private String sMarca;

	@Column(name="s_moneda")
	private String sMoneda;

	@Column(name="s_no_parte")
	private String sNoParte;

	@Column(name="s_producto")
	private String sProducto;
	
	@Column(name="n_idusuario")
	private Long nIdusuario;
	
	@Column(name="n_IdGanancia")
	private Long nIdGanancia;
	
	@Column(name="n_idclavesat")
	private Long nIdclavesat;


	//bi-directional many-to-one association to TcCategoria
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="n_idcategoria", referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcCategoria tcCategoria;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="n_idCategoriaGeneral", referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcCategoriaGeneral tcCategoriaGeneral;

	//bi-directional many-to-one association to TcClavesat
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="n_idclavesat" , referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcClavesat tcClavesat;

	//bi-directional many-to-one association to TcGanancia
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="n_IdGanancia" , referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcGanancia tcGanancia;

	//bi-directional many-to-one association to TcUsuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="n_idusuario" , referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcUsuario tcUsuario;

	//bi-directional many-to-one association to TwCotizacionesProducto
//	@OneToMany(mappedBy="tcProducto")
//	private List<TwCotizacionesProducto> twCotizacionesProductos;
//
//	//bi-directional many-to-one association to TwPedido
//	@OneToMany(mappedBy="tcProducto")
//	private List<TwPedido> twPedidos;
//
//	//bi-directional many-to-one association to TwProductobodega
//	@OneToMany(mappedBy="tcProducto")
//	private List<TwProductobodega> twProductobodegas;
//
//	//bi-directional many-to-one association to TwProductosAlternativo
//	@OneToMany(mappedBy="tcProducto")
//	private List<TwProductosAlternativo> twProductosAlternativos;
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

	public int getN_idCategoriaGeneral() {
		return n_idCategoriaGeneral;
	}

	public void setN_idCategoriaGeneral(int n_idCategoriaGeneral) {
		this.n_idCategoriaGeneral = n_idCategoriaGeneral;
	}

	public int getN_idCategoria() {
		return n_idCategoria;
	}

	public void setN_idCategoria(int n_idCategoria) {
		this.n_idCategoria = n_idCategoria;
	}

	public double getnPrecio() {
		return nPrecio;
	}

	public void setnPrecio(double nPrecio) {
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

	

	

}