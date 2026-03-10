package com.refacFabela.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.OptimisticLockException;

import org.springframework.orm.ObjectOptimisticLockingFailureException;

import com.refacFabela.dto.TraspasoExternoDTO;
import com.refacFabela.model.TwAjustesInventario;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.service.TraspasoService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class TraspasosController {
	
	private static final Logger logger = LogManager.getLogger("errorLogger");
	
	@Autowired
	private TraspasoService traspasoService;
	
	
	@PostMapping("/movimientoInterno")
	public ResponseEntity<?> guardar(@RequestBody() TwProductobodega twProductobodega){
		
		Map<String, Object>  response = new HashMap<>();
		
		try {
			response.put("twProductobodega", this.traspasoService.guardar(twProductobodega));
			return new ResponseEntity<Map<String , Object>>(response, HttpStatus.ACCEPTED);
		} catch (OptimisticLockException | ObjectOptimisticLockingFailureException e) {
			logger.warn("Conflicto de concurrencia en movimiento interno: " + e.getMessage());
			response.put("error", "No se pudo realizar el movimiento porque otro usuario modificó el registro en este momento. Por favor intente de nuevo.");
			return new ResponseEntity<Map<String , Object>>(response, HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error en movimiento interno: " + e.getMessage(), e);
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String , Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/movimientoInterno2")
	public ResponseEntity<?> guardar2(@RequestBody() TwProductobodega twProductobodega){
		
		Map<String, Object>  response = new HashMap<>();
		
		try {
			response.put("twProductobodega", this.traspasoService.guardar2(twProductobodega));
			return new ResponseEntity<Map<String , Object>>(response, HttpStatus.ACCEPTED);
		} catch (OptimisticLockException | ObjectOptimisticLockingFailureException e) {
			logger.warn("Conflicto de concurrencia en movimiento interno2: " + e.getMessage());
			response.put("error", "No se pudo realizar el movimiento porque otro usuario modificó el registro en este momento. Por favor intente de nuevo.");
			return new ResponseEntity<Map<String , Object>>(response, HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error en movimiento interno2: " + e.getMessage(), e);
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String , Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/movimientoExterno")
	public ResponseEntity<?> guardarExterno(@RequestBody() TraspasoExternoDTO traspasoDTO){
		
		Map<String, Object>  response = new HashMap<>();
		
		try {
			List<TwProductobodega> resultado = this.traspasoService.guardarExterno(traspasoDTO);
			response.put("listaProductoBodega", resultado);
			return new ResponseEntity<Map<String , Object>>(response, HttpStatus.ACCEPTED);
		} catch (OptimisticLockException | ObjectOptimisticLockingFailureException e) {
			logger.warn("Conflicto de concurrencia en movimiento externo: " + e.getMessage());
			response.put("error", "No se pudo realizar el traspaso porque otro usuario modificó el inventario en este momento. Por favor recargue el producto e intente de nuevo.");
			return new ResponseEntity<Map<String , Object>>(response, HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error en movimiento externo: " + e.getMessage(), e);
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String , Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("guardarAjusteInventario")
	public TwAjustesInventario guardarCliente(@RequestBody TwAjustesInventario twAjustesInventario) {

		try {
			return  this.traspasoService.guardarAjusteInventario(twAjustesInventario);

		} catch (Exception e) {
			logger.error("Error al guardar el ajuste del inventario" + e);
		}

		return null;
	}
	

	
	
	

}
