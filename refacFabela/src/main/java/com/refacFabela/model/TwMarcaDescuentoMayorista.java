package com.refacFabela.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entity: tw_marca_descuento_mayorista
 * Tabla que almacena el % de ganancia específico por marca para clientes mayoristas.
 * 
 * Cuando un mayorista compra un producto de una marca registrada en esta tabla,
 * se utiliza la ganancia de esta tabla en lugar de la ganancia del producto (tc_productos.n_IdGanancia).
 * 
 * @author Sistema Fabela
 * @version 1.0
 * @date 2026-04-25
 */
@Entity
@Table(name = "tw_marca_descuento_mayorista")
@NamedQuery(name = "TwMarcaDescuentoMayorista.findAll", 
            query = "SELECT t FROM TwMarcaDescuentoMayorista t")
public class TwMarcaDescuentoMayorista implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Identificador de la marca (PK, FK a tc_marca.n_id)
	 */
	@Id
	@Column(name = "n_id_marca")
	private Long nIdMarca;

	/**
	 * Porcentaje de ganancia para mayoristas (ej: 0.08 = 8%)
	 * DECIMAL(10,4): permite valores hasta 9999.9999
	 */
	@Column(name = "n_ganancia")
	private java.math.BigDecimal nGanancia;

	/**
	 * Relación @ManyToOne con TcMarca
	 * Permite acceder a datos de la marca (s_marca, etc.)
	 */
	@ManyToOne
	@JoinColumn(name = "n_id_marca", insertable = false, updatable = false)
	private TcMarca tcMarca;

	// ============ CONSTRUCTORES ============

	/**
	 * Constructor vacío (requerido por JPA)
	 */
	public TwMarcaDescuentoMayorista() {
	}

	/**
	 * Constructor completo
	 */
	public TwMarcaDescuentoMayorista(Long nIdMarca, java.math.BigDecimal nGanancia) {
		this.nIdMarca = nIdMarca;
		this.nGanancia = nGanancia;
	}

	// ============ GETTERS Y SETTERS ============

	public Long getnIdMarca() {
		return nIdMarca;
	}

	public void setnIdMarca(Long nIdMarca) {
		this.nIdMarca = nIdMarca;
	}

	public java.math.BigDecimal getnGanancia() {
		return nGanancia;
	}

	public void setnGanancia(java.math.BigDecimal nGanancia) {
		this.nGanancia = nGanancia;
	}

	public TcMarca getTcMarca() {
		return tcMarca;
	}

	public void setTcMarca(TcMarca tcMarca) {
		this.tcMarca = tcMarca;
	}

	// ============ MÉTODOS DE UTILIDAD ============

	@Override
	public String toString() {
		return "TwMarcaDescuentoMayorista{" +
				"nIdMarca=" + nIdMarca +
				", nGanancia=" + nGanancia +
				'}';
	}
}
