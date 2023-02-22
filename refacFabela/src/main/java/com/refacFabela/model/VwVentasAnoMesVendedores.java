package com.refacFabela.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "vw_ventas_ano_mes_vendedor")
@NamedQuery(name = "VwVentasAnoMesVendedores.findAll", query = "SELECT t FROM VwVentasAnoMesVendedores t")
public class VwVentasAnoMesVendedores implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "n_id_mes")
	private Long nId;

	@Column(name = "ano")
	private String sAno;
	
	@Column(name = "mes")
	private String sMes;

	@Column(name = "total_ventas")
	private Integer nTotalVentas;
	
	@Column(name = "s_nombreUsuario")
	private String sNombreUsuario;	
	
	@Column(name = "n_id_usuario")
	private Long nIdUsuario;	
	
	public VwVentasAnoMesVendedores() {
		
	}

	public Long getnId() {
		return nId;
	}

	public void setnId(Long nId) {
		this.nId = nId;
	}

	public String getsAno() {
		return sAno;
	}

	public void setsAno(String sAno) {
		this.sAno = sAno;
	}

	public String getsMes() {
		return sMes;
	}

	public void setsMes(String sMes) {
		this.sMes = sMes;
	}

	public Integer getnTotalVentas() {
		return nTotalVentas;
	}

	public void setnTotalVentas(Integer nTotalVentas) {
		this.nTotalVentas = nTotalVentas;
	}

	public String getsNombreUsuario() {
		return sNombreUsuario;
	}

	public void setsNombreUsuario(String sNombreUsuario) {
		this.sNombreUsuario = sNombreUsuario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getnIdUsuario() {
		return nIdUsuario;
	}

	public void setnIdUsuario(Long nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}

	
	
	
	

}
