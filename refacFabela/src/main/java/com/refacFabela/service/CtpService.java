package com.refacFabela.service;

import java.util.List;

import com.refacFabela.model.Part;

public interface CtpService {
	 Part consultarParte(String numeroParte, String cantidad);
	 
	 List<Part> consultarCostexFaltantes(int limit);
}
