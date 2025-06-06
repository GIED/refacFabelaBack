package com.refacFabela.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class VentaProductoCancelaDto implements  Serializable {

	private static final long serialVersionUID = 1L;
	
	public VentaProductoDto VentaProductoDto;
	public Integer nCancela;
	public Boolean penaliza;
	public BigDecimal totalPenaliza;
	public String sMotivo;

	

    	




	public String getsMotivo() {
		return sMotivo;
	}



	public void setsMotivo(String sMotivo) {
		this.sMotivo = sMotivo;
	}



	public Boolean getPenaliza() {
		return penaliza;
	}



	public void setPenaliza(Boolean penaliza) {
		this.penaliza = penaliza;
	}



	



	public BigDecimal getTotalPenaliza() {
		return totalPenaliza;
	}



	public void setTotalPenaliza(BigDecimal totalPenaliza) {
		this.totalPenaliza = totalPenaliza;
	}



	public VentaProductoDto getVentaProductoDto() {
		return VentaProductoDto;
	}



	public void setVentaProductoDto(VentaProductoDto ventaProductoDto) {
		VentaProductoDto = ventaProductoDto;
	}



	public Integer getnCancela() {
		return nCancela;
	}



	public void setnCancela(Integer nCancela) {
		this.nCancela = nCancela;
	}



	@Override
	public String toString() {
		return "VentaProductoCancelaDto [VentaProductoDto=" + VentaProductoDto + ", nCancela=" + nCancela
				+ ", penaliza=" + penaliza + ", totalPenaliza=" + totalPenaliza + ", sMotivo=" + sMotivo + "]";
	}



	


	
	

}
