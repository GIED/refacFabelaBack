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
	

	public TcTipoVenta() {
	}

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public String getsClave() {
		return sClave;
	}

	public void setsClave(String sClave) {
		this.sClave = sClave;
	}

	public String getsDescripcion() {
		return sDescripcion;
	}

	public void setsDescripcion(String sDescripcion) {
		this.sDescripcion = sDescripcion;
	}

	

}