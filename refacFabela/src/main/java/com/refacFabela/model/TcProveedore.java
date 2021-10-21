package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tc_proveedores database table.
 * 
 */
@Entity
@Table(name="tc_proveedores")
@NamedQuery(name="TcProveedore.findAll", query="SELECT t FROM TcProveedore t")
public class TcProveedore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Column(name="n_estatus")
	private int nEstatus;

	@Column(name="n_idusuario")
	private int nIdusuario;

	@Column(name="s_direccion")
	private String sDireccion;

	@Column(name="s_razon_social")
	private String sRazonSocial;

	@Column(name="s_rfc")
	private String sRfc;

	@Column(name="s_telefono")
	private String sTelefono;

	//bi-directional many-to-one association to TwPedido
	@OneToMany(mappedBy="tcProveedore")
	private List<TwPedido> twPedidos;

	public TcProveedore() {
	}

	public Long getNId() {
		return this.nId;
	}

	public void setNId(Long nId) {
		this.nId = nId;
	}

	public int getNEstatus() {
		return this.nEstatus;
	}

	public void setNEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}

	public int getNIdusuario() {
		return this.nIdusuario;
	}

	public void setNIdusuario(int nIdusuario) {
		this.nIdusuario = nIdusuario;
	}

	public String getSDireccion() {
		return this.sDireccion;
	}

	public void setSDireccion(String sDireccion) {
		this.sDireccion = sDireccion;
	}

	public String getSRazonSocial() {
		return this.sRazonSocial;
	}

	public void setSRazonSocial(String sRazonSocial) {
		this.sRazonSocial = sRazonSocial;
	}

	public String getSRfc() {
		return this.sRfc;
	}

	public void setSRfc(String sRfc) {
		this.sRfc = sRfc;
	}

	public String getSTelefono() {
		return this.sTelefono;
	}

	public void setSTelefono(String sTelefono) {
		this.sTelefono = sTelefono;
	}

	public List<TwPedido> getTwPedidos() {
		return this.twPedidos;
	}

	public void setTwPedidos(List<TwPedido> twPedidos) {
		this.twPedidos = twPedidos;
	}

	public TwPedido addTwPedido(TwPedido twPedido) {
		getTwPedidos().add(twPedido);
		twPedido.setTcProveedore(this);

		return twPedido;
	}

	public TwPedido removeTwPedido(TwPedido twPedido) {
		getTwPedidos().remove(twPedido);
		twPedido.setTcProveedore(null);

		return twPedido;
	}

}