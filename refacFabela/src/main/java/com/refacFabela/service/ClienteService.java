package com.refacFabela.service;

import java.util.List;

import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TwProductobodega;

public interface ClienteService {
	
	public List<TcCliente> obtenerCliente();
	public TcCliente guardarCliente(TcCliente tcCliente);
	public TcCliente consultaClienteId(Long id);
	public String eliminarClienteId(Long id);
	

}
