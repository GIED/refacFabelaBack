package com.refacFabela.dto;

import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TcProducto;

public class ProductoDescuentoDto {
	
	
	private TcProducto tcProducto;
	private TcCliente tcCliente;
	
	
	public TcProducto getTcProducto() {
		return tcProducto;
	}
	public void setTcProducto(TcProducto tcProducto) {
		this.tcProducto = tcProducto;
	}
	public TcCliente getTcCliente() {
		return tcCliente;
	}
	public void setTcCliente(TcCliente tcCliente) {
		this.tcCliente = tcCliente;
	}
	@Override
	public String toString() {
		return "ProductoDescuentoDto [tcProducto=" + tcProducto + ", tcCliente=" + tcCliente + "]";
	}
	
	
	
	

}
