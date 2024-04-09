package com.refacFabela.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.refacFabela.dto.SubirFacturaDto;
import com.refacFabela.enums.TipoDoc;
import com.refacFabela.model.TcDatosFactura;
import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TwFacturacion;
import com.refacFabela.model.TwVenta;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.model.factura.CabeceraPagosXml;
import com.refacFabela.model.factura.CabeceraXml;
import com.refacFabela.model.factura.ConceptoXml;
import com.refacFabela.model.factura.Impuesto;
import com.refacFabela.repository.FacturaRepository;
import com.refacFabela.repository.TcDatosFacturaRepository;
import com.refacFabela.repository.TrVentaCobroRepository;
import com.refacFabela.repository.VentasProductoRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.service.FacturacionService;
import com.refacFabela.utils.subirArchivo;
import com.refacFabela.utils.utils;
import com.refacFabela.utils.factura.GeneraXml;
import com.refacFabela.utils.factura.TimbrarXml;
import com.refacFabela.utils.factura.Transformar;

import mx.grupocorasa.sat.cfd._40.Comprobante;


@Service
public class FacturacionServiceImpl implements FacturacionService {
	
	@Autowired
	private VentasRepository ventaRepository;
	
	@Autowired
	private VentasProductoRepository ventasProductoRepository;
	
	@Autowired
	private Transformar transformar;
	
	@Autowired
	private GeneraXml generarXml;
	
	@Autowired TimbrarXml timbrarXml;
	
	@Autowired
	private FacturaRepository FacturaRepository;
	
	@Autowired
	private TcDatosFacturaRepository tcDatosFacturaRepository;
	
	@Autowired
	private TrVentaCobroRepository trVentaCobroRepository;
	

	@Override
	public String venta(Long idVenta, String cveCfdi) throws Exception {
		
		try {
			
			//System.out.println("llego");
			
			TwVenta twVenta = this.ventaRepository.findBynId(idVenta);		
			TcDatosFactura tcDatosFactura = tcDatosFacturaRepository.obtenerDatos(twVenta.getTcCliente().getnIdDatoFactura());
			List<TrVentaCobro> listaVentaCobro=trVentaCobroRepository.findBynIdVenta(idVenta);
			//System.err.println(tcDatosFactura);
			
			if(twVenta.getnIdFacturacion()==0L) {
			List<TwVentasProducto> productosVendidos = this.ventasProductoRepository.findBynIdVenta(idVenta);	
			
			CabeceraXml cabeceraXml = transformar.objCabecera(productosVendidos, twVenta, cveCfdi, tcDatosFactura, listaVentaCobro);
			List<ConceptoXml> listaConceptos = transformar.listaConceptos(productosVendidos);
			Impuesto impuesto = transformar.obtenerImpuestoTotal(productosVendidos);
			
			Comprobante xml = generarXml.createComprobante(cabeceraXml, listaConceptos, impuesto,tcDatosFactura);
			//System.err.println("Sali de aquí");
			
			//timbramos xml
	        timbrarXml.timbrarXml(xml, idVenta, cabeceraXml, tcDatosFactura);
	        
	        return "ok";
	        }
			else {
				return "Ya se facturó";
			}
			
		}catch (Exception e) {
			return "Error al facturar";
		}
		
	}
	
public String complemento(Long idVenta, String cveCfdi) throws Exception {
		
		try {
			
			//System.out.println("llego");
			
			TwVenta twVenta = this.ventaRepository.findBynId(idVenta);			
			
			if(twVenta.getnIdFacturacion()==0L) {
			List<TwVentasProducto> productosVendidos = this.ventasProductoRepository.findBynIdVenta(idVenta);
			
			
			CabeceraPagosXml cabeceraXml = transformar.objCabeceraPagos(productosVendidos, twVenta, cveCfdi);
			List<ConceptoXml> listaConceptos = transformar.listaConceptos(productosVendidos);
			Impuesto impuesto = transformar.obtenerImpuestoTotal(productosVendidos);
			
			Comprobante xml = generarXml.createComprobantePagos(cabeceraXml, listaConceptos, impuesto);
			
			//timbramos xml
	        timbrarXml.timbrarPagoXml(xml, idVenta, cabeceraXml);
	        
	        return "ok";
	        }
			else {
				return "Ya se facturó";
			}
			
		}catch (Exception e) {
			return "Error al facturar";
		}
		
	}
	
	@Override
	public TwFacturacion guardar(TwFacturacion twFacturacion) {
		return this.FacturaRepository.save(twFacturacion);
	}

	@Override
	public int consultaCreditos(Long nDatoFactura) {
		
		TcDatosFactura tcDatosFactura=tcDatosFacturaRepository.obtenerDatos(nDatoFactura);
		
		return TimbrarXml.consultaFolio(tcDatosFactura);
	}

	@Override
	public SubirFacturaDto subirArchivo(MultipartFile file, MultipartFile fileXml, String venta, String uuid) throws Exception {
		
		
		try {
			
			subirArchivo subir =new subirArchivo();
			SubirFacturaDto subirFacturaDto=new SubirFacturaDto();
			
		System.err.println(TipoDoc.PDF_FACTURA.getPath());
			
			if(subir.subirArchivoFactura(file, Integer.valueOf(venta), TipoDoc.PDF_FACTURA.getPath()) && subir.subirArchivoFactura(fileXml, Integer.valueOf(venta), TipoDoc.XML_FACTURA.getPath())) {
				subirFacturaDto.setMensaje("Se guardaron los documentos");
				
				TwVenta twVentas= new TwVenta();
				TwFacturacion twFacturacion= new TwFacturacion();
				
				twVentas=ventaRepository.findBynId( Long.parseLong(venta));
				
				twFacturacion.setsUuid(uuid);
				twFacturacion.setnEstatus(1);
				twFacturacion.setnIdDatoFactura(1L);
				twFacturacion.setN_idVenta( Long.parseLong(venta));
				
				twFacturacion=FacturaRepository.save(twFacturacion);
				
				twVentas.setnIdFacturacion(twFacturacion.getnId());
				
				ventaRepository.save(twVentas);
				
				
				
				
				
				
				return subirFacturaDto;
				
				
			}
			else {
				subirFacturaDto.setMensaje("No se guardaron los documentos");
				return subirFacturaDto;
			}
			
			
			
			
			
			
		
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		
		
	}
	
	

}
