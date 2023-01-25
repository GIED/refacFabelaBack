package com.refacFabela.service;

import java.util.List;

import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TvSaldoGeneralCliente;
import com.refacFabela.model.TwProductobodega;

public interface ClienteService {
	
	public List<TcCliente> obtenerCliente();
	public TcCliente guardarCliente(TcCliente tcCliente);
	public TcCliente consultaClienteId(Long id);
	public TcCliente consultaClienteIdUsuario(Long id);
	public TcCliente consultaClienteRfc(String rfc);
	public String eliminarClienteId(Long id);
	public List<TcCliente> consultaClienteLike(String clienteBuscar);	
	public TvSaldoGeneralCliente consultaClienteIdSaldo(Long id);
	public List<TvSaldoGeneralCliente> consultaClienteSaldo();
	

}
