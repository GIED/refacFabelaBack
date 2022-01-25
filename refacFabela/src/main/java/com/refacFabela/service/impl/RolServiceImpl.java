package com.refacFabela.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.enums.RolNombre;
import com.refacFabela.model.TcRol;
import com.refacFabela.repository.RolRepository;
import com.refacFabela.service.RolService;

@Service
@Transactional
public class RolServiceImpl implements RolService {
	
	@Autowired
	private RolRepository rolRepository;

	@Override
	public Optional<TcRol> getByRolNombre(RolNombre sRol) {
		return rolRepository.findBysRol(sRol);
	}

}
