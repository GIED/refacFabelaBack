package com.refacFabela.service;

import java.util.Optional;

import com.refacFabela.enums.RolNombre;
import com.refacFabela.model.TcRol;

public interface RolService {
	
	public Optional<TcRol> getByRolNombre(RolNombre sRol);

}
