package com.refacFabela.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TvSaldoGeneralCliente;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.repository.ClienteSaldoRepository;
import com.refacFabela.repository.ClientesRepository;
import com.refacFabela.service.ClienteService;

@Service
public class ClientesServiceImp implements ClienteService {

	@Autowired
	private ClientesRepository clientesRepository;
	@Autowired
	private ClienteSaldoRepository clienteSaldoRepository;

	@Override
	public List<TcCliente> obtenerCliente() {

		return clientesRepository.findBynEstatus(1);
	}

	@Override
	public TcCliente guardarCliente(TcCliente tcCliente) {
		
		return clientesRepository.save(tcCliente);
	}

	@Override
	public TcCliente consultaClienteId(Long id) {
		
		return clientesRepository.findById(id).get();
	}
	
	@Override
	public TcCliente consultaClienteIdUsuario(Long id) {
		
		 return clientesRepository.findBynIdUsuario(id);
	}
	
	@Override
	public TcCliente consultaClienteRfc(String rfc) {
		
		return clientesRepository.findBysRfc(rfc);
	}

	@Override
	public String eliminarClienteId(Long id) {
	
		clientesRepository.deleteById(id);
		return "Cliente Eliminado";
	}

	@Override
	public List<TcCliente> consultaClienteLike(String clienteBuscar) {
		
		return clientesRepository.buscarClineteLike(clienteBuscar);
	}

	@Override
	public TvSaldoGeneralCliente consultaClienteIdSaldo(Long id) {
	
		return clienteSaldoRepository.findBynIdCliente(id);
	}

	@Override
	public List<TvSaldoGeneralCliente> consultaClienteSaldo() {
		
		return clienteSaldoRepository.obtenerClienteSaldo();
	}

	

	

}
