package com.refacFabela.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.refacFabela.model.TwCotizacionesDetalle;
import com.refacFabela.model.TwPagoComprobanteInternet;
import com.refacFabela.service.CotizacionService;
import com.refacFabela.service.VentaInternetService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class VentasInternetController {
	
	private static final Logger logger = LogManager.getLogger("errorLogger");
	
	@Autowired
	private VentaInternetService ventaInternetService;
	
	@Autowired
	private CotizacionService cotizacionService;
	
	
	
	@PostMapping("/guardaComprobante")
	public ResponseEntity<?> guardaComprobante(@RequestParam MultipartFile archivo, @RequestParam Long id){
		
		Map<String, Object>  response = new HashMap<>();
		
		TwCotizacionesDetalle cotizacion = cotizacionService.consultaCotizacionById(id);
		
		TwPagoComprobanteInternet comprobantePago = this.ventaInternetService.consultarSiComprobanteExiste(cotizacion.getnId(), cotizacion.getnIdCliente());
		
		
		
		if (!archivo.isEmpty()) {
			String nombreArchivo= UUID.randomUUID().toString() +"_"+ archivo.getOriginalFilename();
			Path rutaArchivo = Paths.get("/opt/webserver/backEnd/refacFabela/comprobantesInternet").resolve(nombreArchivo).toAbsolutePath();
			
			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir el comprobante:  "+nombreArchivo);
				return new ResponseEntity<Map<String , Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if (comprobantePago != null) {
				
				String nombreComprobanteAnterior = comprobantePago.getsComprobante();
				
				Path rutaAnterior = Paths.get("/opt/webserver/backEnd/refacFabela/comprobantesInternet").resolve(nombreComprobanteAnterior).toAbsolutePath();
				File comprobanteAnterior = rutaAnterior.toFile();
				
				if (comprobanteAnterior.exists() && comprobanteAnterior.canRead()) {
					comprobanteAnterior.delete();		
				}
				
				comprobantePago.setsComprobante(nombreArchivo);
				
				
				response.put("TwPagoComprobanteInternet", this.ventaInternetService.guardarComprobante(comprobantePago));
				response.put("mensaje", "comprobante subido correctamente: "+nombreArchivo);
				
			}else {
				TwPagoComprobanteInternet comprobantePagoNew= new TwPagoComprobanteInternet();
				comprobantePagoNew.setnIdCliente(cotizacion.getnIdCliente());
				comprobantePagoNew.setnIdCotizacion(cotizacion.getnId());
				comprobantePagoNew.setsComprobante(nombreArchivo);
				comprobantePagoNew.setnStatus(1);
				
				response.put("TwPagoComprobanteInternet", this.ventaInternetService.guardarComprobante(comprobantePagoNew));
				response.put("mensaje", "comprobante subido correctamente: "+nombreArchivo);
			}
			
				
		}
		
		return new ResponseEntity<Map<String , Object>>(response, HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("verComprobante")
	public ResponseEntity<Resource> mostrarComprobante(@RequestParam String nombreComprobante){
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
	
	

	
	
	

}
