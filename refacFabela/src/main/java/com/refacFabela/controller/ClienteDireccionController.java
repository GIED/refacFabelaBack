package com.refacFabela.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.refacFabela.dto.DireccionEnvioDto;
import com.refacFabela.service.ClienteDireccionService;

@RestController
@RequestMapping("/clientes/direcciones")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class ClienteDireccionController {
	@Autowired
	private ClienteDireccionService service;

	/**
	 * Lista todas las direcciones del cliente (predeterminada primero, luego por
	 * nId desc).
	 */
	@GetMapping
	public ResponseEntity<List<DireccionEnvioDto>> listar(@RequestParam Long clienteId) {
		return ResponseEntity.ok(service.listar(clienteId));
	}

	/** Obtiene una dirección específica del cliente. */
	@GetMapping("/obtener")
	public ResponseEntity<DireccionEnvioDto> obtener(@RequestParam Long id) {
		// Puedes tener un service.obtener(clienteId, dirId) si prefieres;
		// aquí reusamos el listado para mantener el ejemplo simple.
		return ResponseEntity.ok(service.listarById(id));
	}

	// ====== POST ======

	/** Crea una nueva dirección para el cliente. */
	@PostMapping("/crear")
	public ResponseEntity<DireccionEnvioDto> crear(@RequestBody DireccionEnvioDto dto) {
		return ResponseEntity.ok(service.crear(dto));
	}

	/** Actualiza una dirección existente (POST en lugar de PUT). */
	@PostMapping("/editar")
	public ResponseEntity<DireccionEnvioDto> editar(@RequestBody DireccionEnvioDto dto) {
		return ResponseEntity.ok(service.actualizar(dto));
	}

	/** Elimina una dirección (POST en lugar de DELETE). */
	@PostMapping("/eliminar")
	public ResponseEntity<Void> eliminar(@RequestParam Long dirId) {
		service.eliminar(dirId);
		return ResponseEntity.noContent().build();
	}

	/** Marca una dirección como predeterminada (POST en lugar de PUT). */
	@PostMapping("/predeterminada")
	public ResponseEntity<Void> marcarPredeterminada(@RequestParam Long clienteId, @RequestParam Long dirId) {
		service.marcarPredeterminada(clienteId, dirId);
		return ResponseEntity.noContent().build();
	}

}
