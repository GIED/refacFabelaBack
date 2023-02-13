package com.refacFabela.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.refacFabela.enums.RolNombre;
import com.sun.istack.NotNull;

@Entity
@Table(name = "tc_rol")
public class TcRol {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Column(name="n_id")
	private Long nId;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="s_rol")
	private RolNombre sRol;
	
	

	public TcRol() {
		
	}

	public TcRol(@NotNull RolNombre sRol) {
		this.sRol = sRol;
	}

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public RolNombre getsRol() {
		return sRol;
	}

	public void setsRol(RolNombre sRol) {
		this.sRol = sRol;
	}

	@Override
	public String toString() {
		return "TcRol [nId=" + nId + ", sRol=" + sRol + "]";
	}
	
	
	
	
	

}
