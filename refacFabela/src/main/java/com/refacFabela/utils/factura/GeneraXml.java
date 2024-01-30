package com.refacFabela.utils.factura;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.refacFabela.model.TcDatosFactura;
import com.refacFabela.model.factura.CabeceraPagosXml;
import com.refacFabela.model.factura.CabeceraXml;
import com.refacFabela.model.factura.ConceptoXml;
import com.refacFabela.model.factura.Impuesto;


import mx.grupocorasa.sat.cfd._40.Comprobante;
import mx.grupocorasa.sat.cfd._40.Comprobante.Complemento;
import mx.grupocorasa.sat.cfd._40.Comprobante.Conceptos;
import mx.grupocorasa.sat.cfd._40.Comprobante.Conceptos.Concepto;
import mx.grupocorasa.sat.cfd._40.Comprobante.Emisor;
import mx.grupocorasa.sat.cfd._40.Comprobante.Impuestos;
import mx.grupocorasa.sat.cfd._40.Comprobante.Receptor;
import mx.grupocorasa.sat.cfd._40.ObjectFactory;

import mx.grupocorasa.sat.common.catalogos.CClaveUnidad;
import mx.grupocorasa.sat.common.catalogos.CExportacion;
import mx.grupocorasa.sat.common.catalogos.CFormaPago;
import mx.grupocorasa.sat.common.catalogos.CImpuesto;
import mx.grupocorasa.sat.common.catalogos.CMetodoPago;
import mx.grupocorasa.sat.common.catalogos.CMoneda;
import mx.grupocorasa.sat.common.catalogos.CObjetoImp;
import mx.grupocorasa.sat.common.catalogos.CRegimenFiscal;
import mx.grupocorasa.sat.common.catalogos.CTipoDeComprobante;
import mx.grupocorasa.sat.common.catalogos.CTipoFactor;
import mx.grupocorasa.sat.common.catalogos.CUsoCFDI;


@Component
public class GeneraXml {
	
	   public  Comprobante createComprobante(CabeceraXml cabeceraXml, List<ConceptoXml> listaConceptos, Impuesto impuesto, TcDatosFactura tcDatosFactura) throws Exception {
	        
	        System.out.println("cabeceraXml: "+cabeceraXml.toString());
	        System.out.println("listaConceptos: "+listaConceptos.toString());
	        System.out.println("impuestoXml: "+impuesto.toString());
	        
	            
	        ObjectFactory of = new ObjectFactory();
	        Comprobante xml = of.createComprobante();
	        
	        LocalDateTime now = LocalDateTime.now();    
	        LocalDateTime oneHourAgo = now.minusHours(1);

	        xml.setVersion(cabeceraXml.getVersion());
	        xml.setSerie(cabeceraXml.getSerie());
	        xml.setFolio(cabeceraXml.getFolio());
	        System.err.println(LocalDateTime.now());
	        xml.setFecha(oneHourAgo);
	        xml.setFormaPago(CFormaPago.fromValue(cabeceraXml.getFormaPago()));
	       
	        System.err.println( xml.getCondicionesDePago());
	        xml.setSubTotal(new BigDecimal(cabeceraXml.getSubTotal()));
	        //xml.setDescuento(new BigDecimal("100.00"));
	        xml.setMoneda(CMoneda.MXN);
	        //xml.setTipoCambio(new BigDecimal("1"));
	        xml.setTotal(new BigDecimal(cabeceraXml.getTotal()));
	        xml.setTipoDeComprobante(CTipoDeComprobante.I);
	        
	        // Se cambia el metodo de pago si la forma de pago es por definir a PPD
	        if(cabeceraXml.getFormaPago().equals("99")) {
	        	 xml.setMetodoPago(CMetodoPago.fromValue("PPD"));
	        	 xml.setCondicionesDePago("Pago en parcialidades o diferido");
	        }
	        else {
	        	 xml.setMetodoPago(CMetodoPago.fromValue(ConstantesFactura.MetodoPago));
	        	 xml.setCondicionesDePago(cabeceraXml.getCondicionesPago());
	        }  
	              
	        xml.setLugarExpedicion(cabeceraXml.getLugarExpedicion());// codigo postal 
	        xml.setExportacion(CExportacion.fromValue(cabeceraXml.getExportacion()));
	       

	        //EMISOR
	        xml.setEmisor(createEmisor(of, cabeceraXml));

	        //RECEPTOR
	        xml.setReceptor(createReceptor(of, cabeceraXml));
	        

	        //CONCEPTOS
	        xml.setConceptos(createConceptos(of, listaConceptos));

	        //ImpuestosTotales
	        xml.setImpuestos(createImpuestos(of, impuesto, cabeceraXml));

	        

	        

	        xml.setCertificado(cabeceraXml.getCertificado());
	        xml.setNoCertificado(cabeceraXml.getNoCertificado());

	        return xml;
	    }
	   
	   public  Comprobante createComprobantePagos(CabeceraPagosXml cabeceraXml, List<ConceptoXml> listaConceptos, Impuesto impuesto) throws Exception {
	        
	        System.out.println("cabeceraXml: "+cabeceraXml.toString());
	        System.out.println("listaConceptos: "+listaConceptos.toString());
	        System.out.println("impuestoXml: "+impuesto.toString());
	        
	            
	        ObjectFactory of = new ObjectFactory();
	        Comprobante xml = of.createComprobante();
	        
	        LocalDateTime now = LocalDateTime.now();    
	        LocalDateTime oneHourAgo = now.minusHours(1);

	        xml.setVersion(cabeceraXml.getVersion());
	        xml.setSerie(cabeceraXml.getSerie());
	        xml.setFolio(cabeceraXml.getFolio());	       
	        xml.setFecha(oneHourAgo);	       
	        xml.setSubTotal(new BigDecimal(cabeceraXml.getSubTotal()));	    
	        xml.setMoneda(CMoneda.MXN);	   
	        xml.setTotal(new BigDecimal(cabeceraXml.getTotal()));
	        xml.setTipoDeComprobante(CTipoDeComprobante.P);	   
	        xml.setLugarExpedicion(cabeceraXml.getLugarExpedicion());// codigo postal 
	        xml.setExportacion(CExportacion.fromValue(cabeceraXml.getExportacion()));
	        xml.setCertificado(cabeceraXml.getCertificado());
	        xml.setNoCertificado(cabeceraXml.getNoCertificado());

	        //EMISOR
	        xml.setEmisor(createEmisorPagos(of, cabeceraXml));

	        //RECEPTOR
	        xml.setReceptor(createReceptorPagos(of, cabeceraXml)); 	        

	        //CONCEPTOS
	        xml.setConceptos(createConceptosPagos(of, listaConceptos));
	        
	        xml.setComplemento(createComplemento(of, listaConceptos));

	        //ImpuestosTotales
	     //  xml.setImpuestos(createImpuestos(of, impuesto, cabeceraXml));

	        

	        

	      

	        return xml;
	    }

	    
	    private static Emisor createEmisor(ObjectFactory of, CabeceraXml cabeceraXml) {
	        Emisor emisor = of.createComprobanteEmisor();
	        emisor.setNombre(cabeceraXml.getNombreEmisor());
	        emisor.setRfc(cabeceraXml.getRfcEmisor());
	        emisor.setRegimenFiscal(CRegimenFiscal.fromValue(cabeceraXml.getRegimenFiscal()));
	        return emisor;
	    }
	    private static Emisor createEmisorPagos(ObjectFactory of, CabeceraPagosXml cabeceraXml) {
	        Emisor emisor = of.createComprobanteEmisor();
	        emisor.setNombre(cabeceraXml.getNombreEmisor());
	        emisor.setRfc(cabeceraXml.getRfcEmisor());
	        emisor.setRegimenFiscal(CRegimenFiscal.fromValue(cabeceraXml.getRegimenFiscal()));
	        return emisor;
	    }

	    private static Receptor createReceptor(ObjectFactory of, CabeceraXml cabeceraXml) {
	        Receptor receptor = of.createComprobanteReceptor();
	        receptor.setRfc(cabeceraXml.getRfcReceptor());
	        receptor.setNombre(cabeceraXml.getNombreReceptor());
	        //receptor.setResidenciaFiscal(CPais.MEX);
	        //receptor.setNumRegIdTrib("ResidenteExtranjero1");
	        receptor.setUsoCFDI(CUsoCFDI.fromValue(cabeceraXml.getUsoCFDI()));
	        receptor.setDomicilioFiscalReceptor(cabeceraXml.getDomicilioFiscalReceptor());
	        receptor.setRegimenFiscalReceptor(CRegimenFiscal.fromValue(cabeceraXml.getRegimenFiscalReceptor()));
	       
	        return receptor;
	    }
	    private static Receptor createReceptorPagos(ObjectFactory of, CabeceraPagosXml cabeceraXml) {
	        Receptor receptor = of.createComprobanteReceptor();
	        receptor.setRfc(cabeceraXml.getRfcReceptor());
	        receptor.setNombre(cabeceraXml.getNombreReceptor());
	        //receptor.setResidenciaFiscal(CPais.MEX);
	        //receptor.setNumRegIdTrib("ResidenteExtranjero1");
	        receptor.setUsoCFDI(CUsoCFDI.fromValue(cabeceraXml.getUsoCFDI()));
	        receptor.setDomicilioFiscalReceptor(cabeceraXml.getDomicilioFiscalReceptor());
	        receptor.setRegimenFiscalReceptor(CRegimenFiscal.fromValue(cabeceraXml.getRegimenFiscalReceptor()));
	       
	        return receptor;
	    }

	    private static Conceptos createConceptos(ObjectFactory of, List<ConceptoXml> listaConceptos) {
	        Conceptos cps = of.createComprobanteConceptos();
	        List<Concepto> list = cps.getConcepto();
	        for (ConceptoXml concepto : listaConceptos) {
	        Concepto c1 = of.createComprobanteConceptosConcepto();
	        c1.setClaveProdServ(concepto.getClaveProdServ());
	        c1.setNoIdentificacion(concepto.getNoIdentificacion());
	        c1.setCantidad(new BigDecimal(concepto.getCantidad()));
	        c1.setClaveUnidad(CClaveUnidad.fromValue(concepto.getClaveUnidad()));
	        c1.setUnidad(concepto.getUnidad());
	        c1.setDescripcion(concepto.getDescripcion());
	        c1.setValorUnitario(new BigDecimal(concepto.getValorUnitario()));
	        c1.setImporte(new BigDecimal(concepto.getImporte()));
	        c1.setObjetoImp(CObjetoImp.fromValue(concepto.getObjetoImp()));
	        //c1.setDescuento(new BigDecimal("100.00"));//
	        c1.setImpuestos(createImpuestosConceptos(of, concepto.getImpuesto(), concepto.getTasaOCuota(), concepto.getImporteImpuesto(), concepto.getBase()));
	        list.add(c1);
	            
	        }  
	        return cps;
	    }
	    
	    private static Conceptos createConceptosPagos(ObjectFactory of, List<ConceptoXml> listaConceptos) {
	        Conceptos cps = of.createComprobanteConceptos();
	        List<Concepto> list = cps.getConcepto();
	       
	        Concepto c1 = of.createComprobanteConceptosConcepto();
	        
	        c1.setClaveUnidad(CClaveUnidad.fromValue("ACT"));
	        c1.setClaveProdServ("84111506");
	       // c1.setNoIdentificacion(concepto.getNoIdentificacion());
	        c1.setCantidad(new BigDecimal(1));
	        c1.setDescripcion("Pago");
	        c1.setUnidad("PZA");	        
	        c1.setValorUnitario(new BigDecimal(0));
	        c1.setImporte(new BigDecimal(0));
	        c1.setObjetoImp(CObjetoImp.fromValue("02"));
	        //c1.setDescuento(new BigDecimal("100.00"));//
	       // c1.setImpuestos(createImpuestosConceptos(of, concepto.getImpuesto(), concepto.getTasaOCuota(), concepto.getImporteImpuesto(), concepto.getBase()));
	        list.add(c1);
	            
	        
	        return cps;
	    }
	    
	    
	    
	    
	    private static Complemento createComplemento(ObjectFactory of, List<ConceptoXml> listaConceptos) {
	    	Complemento cps = of.createComprobanteComplemento();
	                           
	    	
	   
	    	
	    	
	    	
	    	
	       
	   
	        
	        return cps;
	    }

	    private static Concepto.Impuestos createImpuestosConceptos(ObjectFactory of, String impuesto, String tasa, String importe, String base) {
	        Concepto.Impuestos imp = of.createComprobanteConceptosConceptoImpuestos();
	        Concepto.Impuestos.Traslados trs = of.createComprobanteConceptosConceptoImpuestosTraslados();
	        Concepto.Impuestos.Traslados.Traslado tr = of.createComprobanteConceptosConceptoImpuestosTrasladosTraslado();
	        
	        tr.setImpuesto(CImpuesto.fromValue(impuesto));
	        tr.setTipoFactor(CTipoFactor.TASA);
	        tr.setTasaOCuota(new BigDecimal(tasa));
	        tr.setImporte(new BigDecimal(importe));
	        tr.setBase(new BigDecimal(base));
	        trs.getTraslado().add(tr);
	        imp.setTraslados(trs);
	        return imp;
	    }

//	    private static InformacionAduanera createInformacionAduanera(ObjectFactory of) {
//	        InformacionAduanera ia = of.createComprobanteConceptosConceptoInformacionAduanera();
//	        ia.setNumeroPedimento("67  52  3924  8060097");
//	        return ia;
//	    }
	//
//	    private static CuentaPredial createCuentaPredial(ObjectFactory of) {
//	        CuentaPredial cup = of.createComprobanteConceptosConceptoCuentaPredial();
//	        cup.setNumero("123456");
//	        return cup;
//	    }
	    private static Impuestos createImpuestos(ObjectFactory of, Impuesto impuesto, CabeceraXml cabeceraXml) {
	        Impuestos imps = of.createComprobanteImpuestos();
	        imps.setTotalImpuestosTrasladados(new BigDecimal(impuesto.getTotalImpuestosTraslados()));
	        imps.setTraslados(createTraslados(of,impuesto, cabeceraXml));
	        return imps;
	    }

	    private static Impuestos.Traslados createTraslados(ObjectFactory of, Impuesto impuesto, CabeceraXml cabeceraXml) {
	        Impuestos.Traslados its = of.createComprobanteImpuestosTraslados();
	        Impuestos.Traslados.Traslado it = of.createComprobanteImpuestosTrasladosTraslado();
	        it.setImpuesto(CImpuesto.fromValue(impuesto.getImpuesto()));
	        it.setTipoFactor(CTipoFactor.TASA);
	        it.setTasaOCuota(new BigDecimal(impuesto.getTasaOCuota()));
	        it.setImporte(new BigDecimal(impuesto.getImporte()));
	        it.setBase(BigDecimal.valueOf(Double.valueOf(cabeceraXml.getSubTotal())));
	        its.getTraslado().add(it);
	        return its;
	    }

	   

	    


}
