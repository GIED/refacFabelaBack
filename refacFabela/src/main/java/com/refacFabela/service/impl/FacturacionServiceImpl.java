package com.refacFabela.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TwFacturacion;
import com.refacFabela.model.TwVenta;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.model.factura.CabeceraXml;
import com.refacFabela.model.factura.ConceptoXml;
import com.refacFabela.model.factura.Impuesto;
import com.refacFabela.repository.FacturaRepository;
import com.refacFabela.repository.VentasProductoRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.service.FacturacionService;
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
	

	@Override
	public String venta(Long idVenta, String cveCfdi) throws Exception {
		
		try {
			
			//System.out.println("llego");
			
			TwVenta twVenta = this.ventaRepository.findBynId(idVenta);
			List<TwVentasProducto> productosVendidos = this.ventasProductoRepository.findBynIdVenta(idVenta);
			
			
			CabeceraXml cabeceraXml = transformar.objCabecera(productosVendidos, twVenta, cveCfdi);
			List<ConceptoXml> listaConceptos = transformar.listaConceptos(productosVendidos);
			Impuesto impuesto = transformar.obtenerImpuestoTotal(productosVendidos);
			
			Comprobante xml = generarXml.createComprobante(cabeceraXml, listaConceptos, impuesto);
			
			//timbramos xml
	        timbrarXml.timbrarXml(xml, idVenta, cabeceraXml);
	        
	        return "ok";
			
		}catch (Exception e) {
			return null;
		}
		
	}
	
	@Override
	public TwFacturacion guardar(TwFacturacion twFacturacion) {
		return this.FacturaRepository.save(twFacturacion);
	}

	@Override
	public int consultaCreditos() {
		
		return TimbrarXml.consultaFolio();
	}
	
	

}
