package com.refacFabela.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.refacFabela.dto.TvStockProductoDto;
import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TwCotizacionesDetalle;
import com.refacFabela.model.TwPagoComprobanteInternet;
import com.refacFabela.service.ClienteService;
import com.refacFabela.service.CotizacionService;
import com.refacFabela.service.VentaInternetService;
import com.refacFabela.utils.utils;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class VentasInternetController {
	
	private static final Logger logger = LogManager.getLogger("errorLogger");
	
	@Autowired
	private VentaInternetService ventaInternetService;
	
	@Autowired
	private CotizacionService cotizacionService;
	
	@Autowired
	private ClienteService clienteService;
	
	
	@PostMapping("/guardaRegistroCotizacionInternet")
	public ResponseEntity<?> guardar(@RequestBody() TwPagoComprobanteInternet twPagoComprobanteInternet){
		
		Map<String, Object>  response = new HashMap<>();
		System.out.println(twPagoComprobanteInternet);
		response.put("twPagoComprobanteInternet", this.ventaInternetService.guardarComprobante(twPagoComprobanteInternet));
				
		return new ResponseEntity<Map<String , Object>>(response, HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/consultaCotizacionDistribuidor")
	public List<TwPagoComprobanteInternet> consultaCotizacionDistribuidor(@RequestParam Long idUsuario) {

		try {
			TcCliente tcCliente = this.clienteService.consultaClienteIdUsuario(idUsuario);
			return ventaInternetService.consultaCotizacionDistribuidor(tcCliente.getnId());
		} catch (Exception e) {

			logger.error("Error al guardar la cotización" + e);
		}
		return null;
	}
	
	@PostMapping("/guardaComprobante")
	public ResponseEntity<?> guardaComprobante(@RequestParam MultipartFile archivo, @RequestParam Long id){
		
		Map<String, Object>  response = new HashMap<>();
		String nombreArchivo="";
		
		System.err.println(archivo);
		System.err.println(id);
		
		TwCotizacionesDetalle cotizacion = cotizacionService.consultaCotizacionById(id);	
		TwPagoComprobanteInternet comprobantePago = this.ventaInternetService.consultarSiComprobanteExiste(cotizacion.getnId(), cotizacion.getnIdCliente());
		
		
		
		if (!archivo.isEmpty()) {
			
			if (comprobantePago.getsComprobante() == null) {
				
				nombreArchivo= UUID.randomUUID().toString() +"_"+ archivo.getOriginalFilename();
				Path rutaArchivo = Paths.get("/opt/webserver/backEnd/refacFabela/comprobantesInternet").resolve(nombreArchivo).toAbsolutePath();	
				try {
					
					Files.copy(archivo.getInputStream(), rutaArchivo);
					
					
					
					comprobantePago.setsComprobante(nombreArchivo);
					comprobantePago.setnStatus(1);
					comprobantePago.setdFechaCarga(new Date());
					
					response.put("twPagoComprobanteInternet", this.ventaInternetService.guardarComprobante(comprobantePago));
					response.put("mensaje", "comprobante subido correctamente: "+nombreArchivo);
					
					
				} catch (IOException e) {
					response.put("mensaje", "Error al subir el comprobante:  "+nombreArchivo);
					return new ResponseEntity<Map<String , Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				
			}else  {
				
				String nombreComprobanteAnterior = comprobantePago.getsComprobante();
				
				Path rutaAnterior = Paths.get("/opt/webserver/backEnd/refacFabela/comprobantesInternet").resolve(nombreComprobanteAnterior).toAbsolutePath();
				File comprobanteAnterior = rutaAnterior.toFile();
				
				if (comprobanteAnterior.exists() && comprobanteAnterior.canRead()) {
					comprobanteAnterior.delete();		
				}
				
				comprobantePago.setsComprobante(nombreArchivo);
				
				
				response.put("twPagoComprobanteInternet", this.ventaInternetService.guardarComprobante(comprobantePago));
				response.put("mensaje", "comprobante subido correctamente: "+nombreArchivo);
				
			}
			
				
		}
		
		return new ResponseEntity<Map<String , Object>>(response, HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("verComprobante/{nombreComprobante:.+}")
	public ResponseEntity<Resource> mostrarComprobante(@PathVariable String nombreComprobante){
		Path rutaArchivo = Paths.get("/opt/webserver/backEnd/refacFabela/comprobantesInternet").resolve(nombreComprobante).toAbsolutePath();
		Resource recurso= null;
		try {
			recurso= new UrlResource(rutaArchivo.toUri());
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		
		if (!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("Error no se pudo cargar la imagen: "+nombreComprobante);
		}
		HttpHeaders cabecera= new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+recurso.getFilename()+"\"");
		
		return new ResponseEntity<Resource>(recurso,cabecera, HttpStatus.OK);
	}
	
	@GetMapping("consultaPagoComprobante")
	public List<TwPagoComprobanteInternet> consultarPagoComprobanteInternet(@RequestParam() Integer status){
		return this.ventaInternetService.consultaRegistros(status);
	}
	
	
	@PostMapping("actualizarEstatusComprobante")
	public ResponseEntity<?> ActualizaEstatusComprobante(@RequestBody() TwPagoComprobanteInternet twPagoComprobanteInternet){
		
		Map<String, Object>  response = new HashMap<>();
		TwPagoComprobanteInternet pagoActualizado= new TwPagoComprobanteInternet();
		List<TvStockProductoDto> Listacotizacion = null;
		
			//Pago Aceptado
		if (twPagoComprobanteInternet.getnStatus() == 2) {
			twPagoComprobanteInternet.setdFechaValidacion(new Date());
			pagoActualizado = this.ventaInternetService.guardarComprobante(twPagoComprobanteInternet);
			Listacotizacion=cotizacionService.consultaCotizacionId(pagoActualizado.getnIdCotizacion());
			
		}else {
			pagoActualizado = this.ventaInternetService.guardarComprobante(twPagoComprobanteInternet);
		}
		
		
		
		response.put("twPagoComprobanteInternet", pagoActualizado);
		response.put("listacotizacion", Listacotizacion);
		response.put("mensaje", "Estatus del comprobante, Actualizado Correctamente");
		return new ResponseEntity<Map<String , Object>>(response, HttpStatus.ACCEPTED);
	}
	
	

	
	
	

}
