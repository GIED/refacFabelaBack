package com.refacFabela.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TcProveedore;
import com.refacFabela.model.TwFacturasProveedor;
import com.refacFabela.repository.ProveedoresRepository;
import com.refacFabela.service.ProveedoresService;

@Service
public class ProveedoresServiceImp implements ProveedoresService {

	@Autowired
	private ProveedoresRepository proveedoresRepository;


	@Override
	public List<TcProveedore> obtenerProveedores() {

		return proveedoresRepository.findBynEstatus(1);
	}

	@Override
	public TcProveedore guardaProveedor(TcProveedore tcProveedores) {

		return proveedoresRepository.save(tcProveedores);
	}

	@Override
	public TcProveedore consultaProveedorId(Long id) {

		return proveedoresRepository.findById(id).get();
	}

	@Override
	public String eliminaProveedor(Long id) {
		proveedoresRepository.deleteById(id);
		return "El producto fue eliminado";
	}



}
