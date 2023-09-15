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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tw_ajustes_inventario")
@NamedQuery(name = "TwAjustesInventario.findAll", query = "SELECT t FROM TwAjustesInventario t")
public class TwAjustesInventario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id")
	private Long nId;
	
	@Column(name = "n_id_producto")
	private Long nIdProducto;
	
	@Column(name = "n_id_bodega")
	private Long nIdBodega;
	
	@Column(name = "n_id_nivel")
	private Long nIdNivel;
	
	@Column(name = "n_id_anaquel")
	private Long nIdAnaquel;
	
	@Column(name = "n_cantidad_anterior")
	private Integer nCantidadAnterior;
	
	@Column(name = "n_cantidad_actual")
	private Integer nCantidadActual;
	
	@Column(name = "n_total_ajustado")
	private Integer nTotalAjustado;
	
	@Column(name = "n_id_usuario")
	private Long nIdUsuario;
	
	@Column(name = "s_fecha")
	private Date sFecha;
	
	
	@ManyToOne()
	@JoinColumn(name = "n_id_usuario", referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcUsuario tcUsuario;
	
	@ManyToOne()
	@JoinColumn(name = "n_id_producto", referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcProducto tcProducto;
	
	@ManyToOne()
	@JoinColumn(name = "n_id_anaquel", referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcAnaquel tcAnaquel;
	
	@ManyToOne()
	@JoinColumn(name = "n_id_nivel", referencedColumnName = "n_id", updatable = false, insertable = false)
	private TcNivel tcNivel;

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public Long getnIdProducto() {
		return nIdProducto;
	}

	public void setnIdProducto(Long nIdProducto) {
		this.nIdProducto = nIdProducto;
	}

	public Long getnIdBodega() {
		return nIdBodega;
	}

	public void setnIdBodega(Long nIdBodega) {
		this.nIdBodega = nIdBodega;
	}

	public Long getnIdNivel() {
		return nIdNivel;
	}

	public void setnIdNivel(Long nIdNivel) {
		this.nIdNivel = nIdNivel;
	}

	public Integer getnCantidadAnterior() {
		return nCantidadAnterior;
	}

	public void setnCantidadAnterior(Integer nCantidadAnterior) {
		this.nCantidadAnterior = nCantidadAnterior;
	}

	public Integer getnCantidadActual() {
		return nCantidadActual;
	}

	public void setnCantidadActual(Integer nCantidadActual) {
		this.nCantidadActual = nCantidadActual;
	}

	public Integer getnTotalAjustado() {
		return nTotalAjustado;
	}

	public void setnTotalAjustado(Integer nTotalAjustado) {
		this.nTotalAjustado = nTotalAjustado;
	}

	public Long getnIdUsuario() {
		return nIdUsuario;
	}

	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}

	public Date getsFecha() {
		return sFecha;
	}

	public void setsFecha(Date sFecha) {
		this.sFecha = sFecha;
	}

	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}

	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}

	public TcProducto getTcProducto() {
		return tcProducto;
	}

	public void setTcProducto(TcProducto tcProducto) {
		this.tcProducto = tcProducto;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	public Long getnIdAnaquel() {
		return nIdAnaquel;
	}

	public void setnIdAnaquel(Long nIdAnaquel) {
		this.nIdAnaquel = nIdAnaquel;
	}

	@Override
	public String toString() {
		return "TwAjustesInventario [nId=" + nId + ", nIdProducto=" + nIdProducto + ", nIdBodega=" + nIdBodega
				+ ", nIdNivel=" + nIdNivel + ", nCantidadAnterior=" + nCantidadAnterior + ", nCantidadActual="
				+ nCantidadActual + ", nTotalAjustado=" + nTotalAjustado + ", nIdUsuario=" + nIdUsuario + ", sFecha="
				+ sFecha + ", tcUsuario=" + tcUsuario + ", tcProducto=" + tcProducto + ", tcAnaquel=" + tcAnaquel
				+ ", tcNivel=" + tcNivel + "]";
	}
	
	
	

	
	

}
