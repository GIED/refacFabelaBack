package com.refacFabela.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tc_categoria_general")
@NamedQuery(name="TcCategoriaGeneral.findAll", query="SELECT t FROM TcCategoriaGeneral t")
public class TcCategoriaGeneral implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="n_id")
	private Long nId;
	

	@Column(name="s_categoriaGeneral")
	private String sCategoriaGeneral;

	@Column(name="n_estatus")
	private int nEstatus;
	


	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public String getsCategoriaGeneral() {
		return sCategoriaGeneral;
	}

	public void setsCategoriaGeneral(String sCategoriaGeneral) {
		this.sCategoriaGeneral = sCategoriaGeneral;
	}

	public int getnEstatus() {
		return nEstatus;
	}

	public void setnEstatus(int nEstatus) {
		this.nEstatus = nEstatus;
	}



	
	
	

}
