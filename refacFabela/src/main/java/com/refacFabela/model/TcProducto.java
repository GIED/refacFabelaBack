package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the tc_productos database table.
 * 
 */
@Entity
@Table(name="tc_productos")
@NamedQuery(name="TcProducto.findAll", query="SELECT t FROM TcProducto t")
public class TcProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Column(name="d_fecha")
	private Timestamp dFecha;

	@Column(name="n_estatus")
	private int nEstatus;

	private int n_idCategoriaGeneral;

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

	//bi-directional many-to-one association to TcCategoria
	@ManyToOne
	@JoinColumn(name="n_idcategoria")
	private TcCategoria tcCategoria;

	//bi-directional many-to-one association to TcClavesat
	@ManyToOne
	@JoinColumn(name="n_idclavesat")
	private TcClavesat tcClavesat;

	//bi-directional many-to-one association to TcGanancia
	@ManyToOne
	@JoinColumn(name="n_IdGanancia")
	private TcGanancia tcGanancia;

	//bi-directional many-to-one association to TcUsuario
	@ManyToOne
	@JoinColumn(name="n_idusuario")
	private TcUsuario tcUsuario;

	//bi-directional many-to-one association to TwCotizacionesProducto
	@OneToMany(mappedBy="tcProducto")
	private List<TwCotizacionesProducto> twCotizacionesProductos;

	//bi-directional many-to-one association to TwPedido
	@OneToMany(mappedBy="tcProducto")
	private List<TwPedido> twPedidos;

	//bi-directional many-to-one association to TwProductobodega
	@OneToMany(mappedBy="tcProducto")
	private List<TwProductobodega> twProductobodegas;

	//bi-directional many-to-one association to TwProductosAlternativo
	@OneToMany(mappedBy="tcProducto")
	private List<TwProductosAlternativo> twProductosAlternativos;

	//bi-directional many-to-one association to TwVentasProducto
	@OneToMany(mappedBy="tcProducto")
	private List<TwVentasProducto> twVentasProductos;

	public TcProducto() {
	}

	public Long getNId() {
		return this.nId;
	}

	public void setNId(Long nId) {
		this.nId = nId;
	}

	public Timestamp getDFecha() {
		return this.dFecha;
	}

	public void setDFecha(Timestamp dFecha) {
		this.dFecha = dFecha;
	}

	public int getNEstatus() {
		return this.nEstatus;
	}

	public void setNEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}

	public int getN_idCategoriaGeneral() {
		return this.n_idCategoriaGeneral;
	}

	public void setN_idCategoriaGeneral(int n_idCategoriaGeneral) {
		this.n_idCategoriaGeneral = n_idCategoriaGeneral;
	}

	public double getNPrecio() {
		return this.nPrecio;
	}

	public void setNPrecio(double nPrecio) {
		this.nPrecio = nPrecio;
	}

	public String getSDescripcion() {
		return this.sDescripcion;
	}

	public void setSDescripcion(String sDescripcion) {
		this.sDescripcion = sDescripcion;
	}

	public String getSMarca() {
		return this.sMarca;
	}

	public void setSMarca(String sMarca) {
		this.sMarca = sMarca;
	}

	public String getSMoneda() {
		return this.sMoneda;
	}

	public void setSMoneda(String sMoneda) {
		this.sMoneda = sMoneda;
	}

	public String getSNoParte() {
		return this.sNoParte;
	}

	public void setSNoParte(String sNoParte) {
		this.sNoParte = sNoParte;
	}

	public String getSProducto() {
		return this.sProducto;
	}

	public void setSProducto(String sProducto) {
		this.sProducto = sProducto;
	}

	public TcCategoria getTcCategoria() {
		return this.tcCategoria;
	}

	public void setTcCategoria(TcCategoria tcCategoria) {
		this.tcCategoria = tcCategoria;
	}

	public TcClavesat getTcClavesat() {
		return this.tcClavesat;
	}

	public void setTcClavesat(TcClavesat tcClavesat) {
		this.tcClavesat = tcClavesat;
	}

	public TcGanancia getTcGanancia() {
		return this.tcGanancia;
	}

	public void setTcGanancia(TcGanancia tcGanancia) {
		this.tcGanancia = tcGanancia;
	}

	public TcUsuario getTcUsuario() {
		return this.tcUsuario;
	}

	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}

	public List<TwCotizacionesProducto> getTwCotizacionesProductos() {
		return this.twCotizacionesProductos;
	}

	public void setTwCotizacionesProductos(List<TwCotizacionesProducto> twCotizacionesProductos) {
		this.twCotizacionesProductos = twCotizacionesProductos;
	}

	public TwCotizacionesProducto addTwCotizacionesProducto(TwCotizacionesProducto twCotizacionesProducto) {
		getTwCotizacionesProductos().add(twCotizacionesProducto);
		twCotizacionesProducto.setTcProducto(this);

		return twCotizacionesProducto;
	}

	public TwCotizacionesProducto removeTwCotizacionesProducto(TwCotizacionesProducto twCotizacionesProducto) {
		getTwCotizacionesProductos().remove(twCotizacionesProducto);
		twCotizacionesProducto.setTcProducto(null);

		return twCotizacionesProducto;
	}

	public List<TwPedido> getTwPedidos() {
		return this.twPedidos;
	}

	public void setTwPedidos(List<TwPedido> twPedidos) {
		this.twPedidos = twPedidos;
	}

	public TwPedido addTwPedido(TwPedido twPedido) {
		getTwPedidos().add(twPedido);
		twPedido.setTcProducto(this);

		return twPedido;
	}

	public TwPedido removeTwPedido(TwPedido twPedido) {
		getTwPedidos().remove(twPedido);
		twPedido.setTcProducto(null);

		return twPedido;
	}

	public List<TwProductobodega> getTwProductobodegas() {
		return this.twProductobodegas;
	}

	public void setTwProductobodegas(List<TwProductobodega> twProductobodegas) {
		this.twProductobodegas = twProductobodegas;
	}

	public TwProductobodega addTwProductobodega(TwProductobodega twProductobodega) {
		getTwProductobodegas().add(twProductobodega);
		twProductobodega.setTcProducto(this);

		return twProductobodega;
	}

	public TwProductobodega removeTwProductobodega(TwProductobodega twProductobodega) {
		getTwProductobodegas().remove(twProductobodega);
		twProductobodega.setTcProducto(null);

		return twProductobodega;
	}

	public List<TwProductosAlternativo> getTwProductosAlternativos() {
		return this.twProductosAlternativos;
	}

	public void setTwProductosAlternativos(List<TwProductosAlternativo> twProductosAlternativos) {
		this.twProductosAlternativos = twProductosAlternativos;
	}

	public TwProductosAlternativo addTwProductosAlternativo(TwProductosAlternativo twProductosAlternativo) {
		getTwProductosAlternativos().add(twProductosAlternativo);
		twProductosAlternativo.setTcProducto(this);

		return twProductosAlternativo;
	}

	public TwProductosAlternativo removeTwProductosAlternativo(TwProductosAlternativo twProductosAlternativo) {
		getTwProductosAlternativos().remove(twProductosAlternativo);
		twProductosAlternativo.setTcProducto(null);

		return twProductosAlternativo;
	}

	public List<TwVentasProducto> getTwVentasProductos() {
		return this.twVentasProductos;
	}

	public void setTwVentasProductos(List<TwVentasProducto> twVentasProductos) {
		this.twVentasProductos = twVentasProductos;
	}

	public TwVentasProducto addTwVentasProducto(TwVentasProducto twVentasProducto) {
		getTwVentasProductos().add(twVentasProducto);
		twVentasProducto.setTcProducto(this);

		return twVentasProducto;
	}

	public TwVentasProducto removeTwVentasProducto(TwVentasProducto twVentasProducto) {
		getTwVentasProductos().remove(twVentasProducto);
		twVentasProducto.setTcProducto(null);

		return twVentasProducto;
	}

}