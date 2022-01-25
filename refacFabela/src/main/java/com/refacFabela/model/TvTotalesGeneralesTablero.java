package com.refacFabela.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tv_totales_generales_tablero")
@NamedQuery(name = "TvTotalesGeneralesTablero.findAll", query = "SELECT t FROM TvTotalesGeneralesTablero t")
public class TvTotalesGeneralesTablero implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "nId")
	private Long nId;
	
	@Column(name = "nTotalClientes")
	private int nTotalClientes;
	
	@Column(name = "nTotalProductos")
	private int nTotalProductos;
	
	@Column(name = "nTotalProveedores")
	private int nTotalProveedores;
	
	@Column(name = "nTotalUsuarios")
	private int nTotalUsuarios;
	
	public TvTotalesGeneralesTablero() {
		
	}

	

	public Long getnId() {
		return nId;
	}


	public void setnId(Long nId) {
		this.nId = nId;
	}



	public int getnTotalClientes() {
		return nTotalClientes;
	}

	public void setnTotalClientes(int nTotalClientes) {
		this.nTotalClientes = nTotalClientes;
	}

	public int getnTotalProductos() {
		return nTotalProductos;
	}

	public void setnTotalProductos(int nTotalProductos) {
		this.nTotalProductos = nTotalProductos;
	}

	public int getnTotalProveedores() {
		return nTotalProveedores;
	}

	public void setnTotalProveedores(int nTotalProveedores) {
		this.nTotalProveedores = nTotalProveedores;
	}

	public int getnTotalUsuarios() {
		return nTotalUsuarios;
	}

	public void setnTotalUsuarios(int nTotalUsuarios) {
		this.nTotalUsuarios = nTotalUsuarios;
	}
	
	

}
