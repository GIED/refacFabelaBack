package com.refacFabela.service;

import java.util.List;

import com.refacFabela.dto.DireccionEnvioDto;

public interface ClienteDireccionService {
	List<DireccionEnvioDto> listar(Long clienteId);
    DireccionEnvioDto crear(Long clienteId, DireccionEnvioDto dto);
    DireccionEnvioDto actualizar(Long clienteId, Long dirId, DireccionEnvioDto dto);
    void eliminar(Long clienteId, Long dirId);
    void marcarPredeterminada(Long clienteId, Long dirId);
}
