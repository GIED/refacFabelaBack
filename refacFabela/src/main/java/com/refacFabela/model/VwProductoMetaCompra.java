package com.refacFabela.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vw_producto_meta_compra")
public class VwProductoMetaCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long nId;

    @Column(name = "s_no_parte")
    private String sNoParte;

    @Column(name = "s_producto")
    private String sProducto;

    @Column(name = "s_descripcion")
    private String sDescripcion;

    @Column(name = "s_marca")
    private String sMarca;

    @Column(name = "s_categoria")
    private String sCategoria;

    @Column(name = "s_categoria_general")
    private String sCategoriaGeneral;

    @Column(name = "s_clavesat")
    private String sClavesat;

    @Column(name = "s_id_bar")
    private String sIdBar;

    @Column(name = "n_id_descuento")
    private Long nIdDescuento;

    @Column(name = "n_precio")
    private BigDecimal nPrecio;

    @Column(name = "s_moneda")
    private String sMoneda;

    @Column(name = "n_ganancia")
    private BigDecimal nGanancia;

    @Column(name = "n_precio_unitario_calculado")
    private BigDecimal nPrecioUnitarioCalculado;

    @Column(name = "n_iva_unitario_calculado")
    private BigDecimal nIvaUnitarioCalculado;

    @Column(name = "n_total_unitario_calculado")
    private BigDecimal nTotalUnitarioCalculado;

    @Column(name = "n_cantidad")
    private Integer nCantidad;

    @Column(name = "n_stock_minimo_requerido")
    private Integer nStockMinimoRequerido;

    @Column(name = "n_stock_maximo_requerido")
    private Integer nStockMaximoRequerido;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "d_ultima_fecha_compra")
    private LocalDateTime dUltimaFechaCompra;

    @Column(name = "n_stock_sugerido")
    private Integer nStockSugerido;

    @Column(name = "n_sugerencia_compra")
    private Integer nSugerenciaCompra;
    
    @OneToMany
    @JoinColumn(name = "n_idproducto", referencedColumnName = "n_id") // Define la FK sin modificar ProductoBodega
    private List<TwProductobodega> twProductoBodega;


    // Constructor sin argumentos
    public VwProductoMetaCompra() {
    }

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
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

	public String getsCategoria() {
		return sCategoria;
	}

	public void setsCategoria(String sCategoria) {
		this.sCategoria = sCategoria;
	}

	public String getsCategoriaGeneral() {
		return sCategoriaGeneral;
	}

	public void setsCategoriaGeneral(String sCategoriaGeneral) {
		this.sCategoriaGeneral = sCategoriaGeneral;
	}

	public String getsClavesat() {
		return sClavesat;
	}

	public void setsClavesat(String sClavesat) {
		this.sClavesat = sClavesat;
	}

	public String getsIdBar() {
		return sIdBar;
	}

	public void setsIdBar(String sIdBar) {
		this.sIdBar = sIdBar;
	}

	public Long getnIdDescuento() {
		return nIdDescuento;
	}

	public void setnIdDescuento(Long nIdDescuento) {
		this.nIdDescuento = nIdDescuento;
	}

	public BigDecimal getnPrecio() {
		return nPrecio;
	}

	public void setnPrecio(BigDecimal nPrecio) {
		this.nPrecio = nPrecio;
	}

	public String getsMoneda() {
		return sMoneda;
	}

	public void setsMoneda(String sMoneda) {
		this.sMoneda = sMoneda;
	}

	public BigDecimal getnGanancia() {
		return nGanancia;
	}

	public void setnGanancia(BigDecimal nGanancia) {
		this.nGanancia = nGanancia;
	}

	public BigDecimal getnPrecioUnitarioCalculado() {
		return nPrecioUnitarioCalculado;
	}

	public void setnPrecioUnitarioCalculado(BigDecimal nPrecioUnitarioCalculado) {
		this.nPrecioUnitarioCalculado = nPrecioUnitarioCalculado;
	}

	public BigDecimal getnIvaUnitarioCalculado() {
		return nIvaUnitarioCalculado;
	}

	public void setnIvaUnitarioCalculado(BigDecimal nIvaUnitarioCalculado) {
		this.nIvaUnitarioCalculado = nIvaUnitarioCalculado;
	}

	public BigDecimal getnTotalUnitarioCalculado() {
		return nTotalUnitarioCalculado;
	}

	public void setnTotalUnitarioCalculado(BigDecimal nTotalUnitarioCalculado) {
		this.nTotalUnitarioCalculado = nTotalUnitarioCalculado;
	}

	public Integer getnCantidad() {
		return nCantidad;
	}

	public void setnCantidad(Integer nCantidad) {
		this.nCantidad = nCantidad;
	}

	public Integer getnStockMinimoRequerido() {
		return nStockMinimoRequerido;
	}

	public void setnStockMinimoRequerido(Integer nStockMinimoRequerido) {
		this.nStockMinimoRequerido = nStockMinimoRequerido;
	}

	public Integer getnStockMaximoRequerido() {
		return nStockMaximoRequerido;
	}

	public void setnStockMaximoRequerido(Integer nStockMaximoRequerido) {
		this.nStockMaximoRequerido = nStockMaximoRequerido;
	}


	public LocalDateTime getdUltimaFechaCompra() {
		return dUltimaFechaCompra;
	}

	public void setdUltimaFechaCompra(LocalDateTime dUltimaFechaCompra) {
		this.dUltimaFechaCompra = dUltimaFechaCompra;
	}

	public Integer getnStockSugerido() {
		return nStockSugerido;
	}

	public void setnStockSugerido(Integer nStockSugerido) {
		this.nStockSugerido = nStockSugerido;
	}

	public Integer getnSugerenciaCompra() {
		return nSugerenciaCompra;
	}

	public void setnSugerenciaCompra(Integer nSugerenciaCompra) {
		this.nSugerenciaCompra = nSugerenciaCompra;
	}

	public List<TwProductobodega> getTwProductoBodega() {
		return twProductoBodega;
	}

	public void setTwProductoBodega(List<TwProductobodega> twProductoBodega) {
		this.twProductoBodega = twProductoBodega;
	}

	

    
    
    
    
}