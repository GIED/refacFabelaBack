package com.refacFabela.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TcCliente;
import com.refacFabela.repository.ClientesRepository;
import com.refacFabela.service.ClienteService;

@Service
public class ClientesServiceImp implements ClienteService {
	
	@Autowired
	private ClientesRepository clientesRepository;
	
	@Override
	public List<TcCliente> obtenerCliente() {
		
		return clientesRepository.findBynEstatus(1);
	}

	@Override
	public TcCliente guardarCliente(TcCliente tcCliente) {
		// TODO Auto-generated method stub
		return clientesRepository.save(tcCliente);
	}

	@Override
	public TcCliente consultaClienteId(Long id) {
		// TODO Auto-generated method stub
		return clientesRepository.findById(id).get();
	}

	@Override
	public String eliminarClienteId(Long id) {
		// TODO Auto-generated method stub
		clientesRepository.deleteById(id);
		return "Cliente Eliminado";
	}

}
