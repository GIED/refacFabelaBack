package com.refacFabela.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.refacFabela.utils.DateTimeUtil;

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

	@Value("${ventas.internet.ruta-comprobantes:${VENTAS_INTERNET_RUTA_COMPROBANTES:comprobantesInternet}}")
	private String rutaComprobantesInternet;

	private Path obtenerDirectorioComprobantes() throws IOException {
		Path directorio = Paths.get(rutaComprobantesInternet).toAbsolutePath().normalize();
		Files.createDirectories(directorio);
		return directorio;
	}

	private Path resolverRutaComprobante(String nombreArchivo) throws IOException {
		Path directorio = obtenerDirectorioComprobantes();
		Path rutaArchivo = directorio.resolve(nombreArchivo).normalize();
		if (!rutaArchivo.startsWith(directorio)) {
			throw new IOException("Ruta de comprobante invalida");
		}
		return rutaArchivo;
	}

	private String generarNombreComprobante(MultipartFile archivo) {
		String nombreOriginal = archivo.getOriginalFilename();
		String nombreBase = nombreOriginal == null ? "comprobante" : nombreOriginal.replace("\\", "/");
		int ultimaDiagonal = nombreBase.lastIndexOf('/');
		if (ultimaDiagonal >= 0) {
			nombreBase = nombreBase.substring(ultimaDiagonal + 1);
		}
		if (nombreBase.trim().isEmpty()) {
			nombreBase = "comprobante";
		}
		return UUID.randomUUID().toString() + "_" + nombreBase;
	}

	private void eliminarComprobanteSilencioso(String nombreArchivo) {
		if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
			return;
		}
		try {
			Files.deleteIfExists(resolverRutaComprobante(nombreArchivo));
		} catch (IOException e) {
			logger.warn("No fue posible eliminar el comprobante previo {}", nombreArchivo, e);
		}
	}
	
	
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
			String nombreComprobanteAnterior = comprobantePago.getsComprobante();
			nombreArchivo = generarNombreComprobante(archivo);
			try {
				Path rutaArchivo = resolverRutaComprobante(nombreArchivo);
				Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);

				comprobantePago.setsComprobante(nombreArchivo);
				comprobantePago.setnStatus(1);
				comprobantePago.setdFechaCarga(DateTimeUtil.obtenerHoraExactaDeMexico());

				response.put("twPagoComprobanteInternet", this.ventaInternetService.guardarComprobante(comprobantePago));
				response.put("mensaje", "comprobante subido correctamente: "+nombreArchivo);
				if (nombreComprobanteAnterior != null && !nombreComprobanteAnterior.trim().isEmpty()
						&& !nombreComprobanteAnterior.equals(nombreArchivo)) {
					eliminarComprobanteSilencioso(nombreComprobanteAnterior);
				}
			} catch (IOException e) {
				logger.error("Error al subir comprobante {}", nombreArchivo, e);
				response.put("mensaje", "Error al subir el comprobante:  "+nombreArchivo);
				return new ResponseEntity<Map<String , Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
				
		}
		
		return new ResponseEntity<Map<String , Object>>(response, HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("verComprobante/{nombreComprobante:.+}")
	public ResponseEntity<Resource> mostrarComprobante(@PathVariable String nombreComprobante){
		Resource recurso= null;
		try {
			Path rutaArchivo = resolverRutaComprobante(nombreComprobante);
			recurso= new UrlResource(rutaArchivo.toUri());
		} catch (IOException e) {
			throw new RuntimeException("Error no se pudo cargar el comprobante: "+nombreComprobante, e);
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
			twPagoComprobanteInternet.setdFechaValidacion(DateTimeUtil.obtenerHoraExactaDeMexico());
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
