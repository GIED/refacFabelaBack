package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tw_ventas database table.
 * 
 */
@Entity
@Table(name="tw_ventas")
@NamedQuery(name="TwVenta.findAll", query="SELECT t FROM TwVenta t")
public class TwVenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Temporal(TemporalType.DATE)
	private Date d_fechaVenta;

	private int n_estatusVenta;

	private int n_tipoPago;

	private String s_folioVenta;

	

	//bi-directional many-to-one association to TcCliente
	@ManyToOne
	@JoinColumn(name="n_idCliente")
	private TcCliente tcCliente;

	//bi-directional many-to-one association to TcTipoVenta
	@ManyToOne
	@JoinColumn(name="n_idTipoVenta")
	private TcTipoVenta tcTipoVenta;

	//bi-directional many-to-one association to TcUsuario
	@ManyToOne
	@JoinColumn(name="n_idUsuario")
	private TcUsuario tcUsuario;

	//bi-directional many-to-one association to TwCaja
	@ManyToOne
	@JoinColumn(name="n_idCaja")
	private TwCaja twCaja;

	//bi-directional many-to-one association to TwFacturacion
	@ManyToOne
	@JoinColumn(name="n_idFacturacion")
	private TwFacturacion twFacturacion;

	
	public TwVenta() {
	}


	public Long getnId() {
		return nId;
	}


	public void setnId(Long nId) {
		this.nId = nId;
	}


	public Date getD_fechaVenta() {
		return d_fechaVenta;
	}


	public void setD_fechaVenta(Date d_fechaVenta) {
		this.d_fechaVenta = d_fechaVenta;
	}


	public int getN_estatusVenta() {
		return n_estatusVenta;
	}


	public void setN_estatusVenta(int n_estatusVenta) {
		this.n_estatusVenta = n_estatusVenta;
	}


	public int getN_tipoPago() {
		return n_tipoPago;
	}


	public void setN_tipoPago(int n_tipoPago) {
		this.n_tipoPago = n_tipoPago;
	}


	public String getS_folioVenta() {
		return s_folioVenta;
	}


	public void setS_folioVenta(String s_folioVenta) {
		this.s_folioVenta = s_folioVenta;
	}


	public TcCliente getTcCliente() {
		return tcCliente;
	}


	public void setTcCliente(TcCliente tcCliente) {
		this.tcCliente = tcCliente;
	}


	public TcTipoVenta getTcTipoVenta() {
		return tcTipoVenta;
	}


	public void setTcTipoVenta(TcTipoVenta tcTipoVenta) {
		this.tcTipoVenta = tcTipoVenta;
	}


	public TcUsuario getTcUsuario() {
		return tcUsuario;
	}


	public void setTcUsuario(TcUsuario tcUsuario) {
		this.tcUsuario = tcUsuario;
	}


	public TwCaja getTwCaja() {
		return twCaja;
	}


	public void setTwCaja(TwCaja twCaja) {
		this.twCaja = twCaja;
	}


	public TwFacturacion getTwFacturacion() {
		return twFacturacion;
	}


	public void setTwFacturacion(TwFacturacion twFacturacion) {
		this.twFacturacion = twFacturacion;
	}

	

}