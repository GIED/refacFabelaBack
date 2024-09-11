package com.refacFabela.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwAbonoFacturaProveedor;
import com.refacFabela.model.TwFacturasProveedor;
import com.refacFabela.model.VwFacturaProveedorBalance;
import com.refacFabela.service.FacturasProveedorService;
import com.refacFabela.tipoCambio.DataSerie;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/facturacionProveedor/")
public class FacturasProveedorController {
	private static final Logger logger = LogManager.getLogger("errorLogger");
	
	
	@Autowired
	private FacturasProveedorService facturasProveedorService;
	
	@GetMapping("/obtenerTodas")
	public List<TwFacturasProveedor> obtenerTodas() {

		try {
			
			return facturasProveedorService.obtenetTodas();

		} catch (Exception e) {
			logger.error("Error al recuperar todas las facturtas" + e);
		}

		return null;
	}
	
	
	@GetMapping("/obtenerFacturasProveedor")
	public List<TwFacturasProveedor> obtenerFacturasProveedor(HttpServletResponse response, @RequestParam() Long nIdProveedor, @RequestParam() Long nIdMoneda) {

		try {
			
			return facturasProveedorService.obtenetFacturasProveedor(nIdProveedor, nIdMoneda);

		} catch (Exception e) {
			logger.error("Error al recuperar las facturas del proveedor" + e);
		}

		return null;
	}
	
	@GetMapping("/obtenerBalacesProveedores")
	public List<VwFacturaProveedorBalance> obtenerBalacesProveedores() {

		try {
			
			return facturasProveedorService.obtenetBalanceProveedores();

		} catch (Exception e) {
			logger.error("Error al recuperar los balances de los clientes" + e);
		}

		return null;
	}
	@GetMapping("/tipoCambioBM")
	public DataSerie tipoCambioBM() {

		try {
			
			return facturasProveedorService.obtenetTipoCambioBM();

		} catch (Exception e) {
			logger.error("Error al recuperar el tipo de cambio BM" + e);
		}

		return null;
	}
	
	@PostMapping("/guardarFacturaProveedor")
	public TwFacturasProveedor guardarfacturaPro(@RequestBody TwFacturasProveedor twFacturasProveedor) {

		try {

			return facturasProveedorService.guardarFacturaProveedor(twFacturasProveedor);
		} catch (Exception e) {

			logger.error("Error al guargar la factura del proveedor" + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerFacturasProveedorBalance")
	public List<BalanceAbonoProveedor> obtenerFacturasProveedorBalance(HttpServletResponse response, @RequestParam() Long nIdProveedor, @RequestParam() Long nIdMoneda) {

		try {
			
			return facturasProveedorService.obtenetFacturasProveedorBalance(nIdProveedor, nIdMoneda);

		} catch (Exception e) {
			logger.error("Error al recuperar las facturas del proveedor balance" + e);
		}

		return null;
	}
	
	@PostMapping("/guardarAbonoFactura")
	public TwAbonoFacturaProveedor guardarAbono(@RequestBody TwAbonoFacturaProveedor twFacturasProveedor) {

		try {

			return facturasProveedorService.guardarAbonoFacturaProveedor(twFacturasProveedor);
		} catch (Exception e) {

			logger.error("Error al guargar la factura del proveedor" + e);
		}
		return null;
	}
	
	@GetMapping("/obtenerAbonosFactura")
	public List<TwAbonoFacturaProveedor> obtenerAbonos(HttpServletResponse response, @RequestParam() Long nIdFactura) {

		try {
			
			return facturasProveedorService.obtenetAbonosFactura(nIdFactura);

		} catch (Exception e) {
			logger.error("Error al recuperar los abonos de la factura del proveedor" + e);
		}

		return null;
	}
	
	@GetMapping("/obtenerBalanceFactura")
	public BalanceAbonoProveedor obtenerBalanceFactura(HttpServletResponse response, @RequestParam() Long nIdFactura) {

		try {
			
			return facturasProveedorService.obtenerBalanceFactura(nIdFactura);

		} catch (Exception e) {
			logger.error("Error al recuperar los abonos de la factura del proveedor" + e);
		}

		return null;
	}
	
	@GetMapping("/obtenerFacturaProveedor")
	public TwFacturasProveedor obtenerFacturaProveedor(HttpServletResponse response, @RequestParam() Long nIdFactura) {

		try {
			
			return facturasProveedorService.obtenerFactura(nIdFactura);

		} catch (Exception e) {
			logger.error("Error al recuperar los abonos de la factura del proveedor" + e);
		}

		return null;
	}
	
	@GetMapping("/obtenerFacturaProveedorBalanceActivas")
	public List<BalanceAbonoProveedor> obtenerFacturaProveedorBalanceActivas() {

		try {
			
			return facturasProveedorService.obtenerFacturasSinPagar();

		} catch (Exception e) {
			logger.error("Error al recuperar los abonos de la factura del proveedor" + e);
		}

		return null;
	}
	
	

}
