package com.refacFabela.service;

import java.util.List;

import com.refacFabela.dto.DireccionEnvioDto;

public interface ClienteDireccionService {
	List<DireccionEnvioDto> listar(Long clienteId);

	DireccionEnvioDto listarById(Long id);

	DireccionEnvioDto crear(DireccionEnvioDto dto);

	DireccionEnvioDto actualizar(DireccionEnvioDto dto);

	void eliminar(Long dirId);

	void marcarPredeterminada(Long clienteId, Long dirId);
}
