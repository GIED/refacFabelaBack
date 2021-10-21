package com.refacFabela.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tc_tipo_venta database table.
 * 
 */
@Entity
@Table(name="tc_tipo_venta")
@NamedQuery(name="TcTipoVenta.findAll", query="SELECT t FROM TcTipoVenta t")
public class TcTipoVenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;

	@Column(name="s_clave")
	private String sClave;

	@Column(name="s_descripcion")
	private String sDescripcion;

	//bi-directional many-to-one association to TwVenta
	@OneToMany(mappedBy="tcTipoVenta")
	private List<TwVenta> twVentas;

	public TcTipoVenta() {
	}

	public Long getNId() {
		return this.nId;
	}

	public void setNId(Long nId) {
		this.nId = nId;
	}

	public String getSClave() {
		return this.sClave;
	}

	public void setSClave(String sClave) {
		this.sClave = sClave;
	}

	public String getSDescripcion() {
		return this.sDescripcion;
	}

	public void setSDescripcion(String sDescripcion) {
		this.sDescripcion = sDescripcion;
	}

	public List<TwVenta> getTwVentas() {
		return this.twVentas;
	}

	public void setTwVentas(List<TwVenta> twVentas) {
		this.twVentas = twVentas;
	}

	public TwVenta addTwVenta(TwVenta twVenta) {
		getTwVentas().add(twVenta);
		twVenta.setTcTipoVenta(this);

		return twVenta;
	}

	public TwVenta removeTwVenta(TwVenta twVenta) {
		getTwVentas().remove(twVenta);
		twVenta.setTcTipoVenta(null);

		return twVenta;
	}

}