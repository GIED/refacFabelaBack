package com.refacFabela.dto;

import java.io.Serializable;

public class VentaProductoCancelaDto implements  Serializable {

	private static final long serialVersionUID = 1L;
	
	public VentaProductoDto VentaProductoDto;
	public Integer nCancela;	

	



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
		return "VentaProductoCancelaDto [ventaProductoDto=" + VentaProductoDto + ", nCancela=" + nCancela + "]";
	}



	
	

}
