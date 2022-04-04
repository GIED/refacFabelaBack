package com.refacFabela.utils.factura;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.refacFabela.model.factura.CabeceraXml;
import com.refacFabela.model.factura.ConceptoXml;
import com.refacFabela.model.factura.Impuesto;

import mx.bigdata.sat.cfdi.v33.schema.CMetodoPago;
import mx.bigdata.sat.cfdi.v33.schema.CMoneda;
import mx.bigdata.sat.cfdi.v33.schema.CTipoDeComprobante;
import mx.bigdata.sat.cfdi.v33.schema.CTipoFactor;
import mx.bigdata.sat.cfdi.v33.schema.CUsoCFDI;
import mx.bigdata.sat.cfdi.v33.schema.Comprobante;
import mx.bigdata.sat.cfdi.v33.schema.Comprobante.Conceptos;
import mx.bigdata.sat.cfdi.v33.schema.Comprobante.Conceptos.Concepto;
import mx.bigdata.sat.cfdi.v33.schema.Comprobante.Emisor;
import mx.bigdata.sat.cfdi.v33.schema.Comprobante.Impuestos;
import mx.bigdata.sat.cfdi.v33.schema.Comprobante.Receptor;
import mx.bigdata.sat.cfdi.v33.schema.ObjectFactory;
@Component
public class GeneraXml {
	
	   public  Comprobante createComprobante(CabeceraXml cabeceraXml, List<ConceptoXml> listaConceptos, Impuesto impuesto) throws Exception {
	        
	        System.out.println("cabeceraXml: "+cabeceraXml.toString());
	        System.out.println("listaConceptos: "+listaConceptos.toString());
	        System.out.println("impuestoXml: "+impuesto.toString());
	        
	            
	        ObjectFactory of = new ObjectFactory();
	        Comprobante xml = of.createComprobante();

	        xml.setVersion(cabeceraXml.getVersion());
	        xml.setSerie(cabeceraXml.getSerie());
	        xml.setFolio(cabeceraXml.getFolio());
	        xml.setFecha(ConstantesFactura.getXMLCalendar());
	        xml.setFormaPago(cabeceraXml.getFormaPago());
	        xml.setCondicionesDePago(cabeceraXml.getCondicionesPago());
	        xml.setSubTotal(new BigDecimal(cabeceraXml.getSubTotal()));
	        //xml.setDescuento(new BigDecimal("100.00"));
	        xml.setMoneda(CMoneda.MXN);
	        //xml.setTipoCambio(new BigDecimal("1"));
	        xml.setTotal(new BigDecimal(cabeceraXml.getTotal()));
	        xml.setTipoDeComprobante(CTipoDeComprobante.I);
	        xml.setMetodoPago(CMetodoPago.fromValue(ConstantesFactura.MetodoPago));
	        xml.setLugarExpedicion(cabeceraXml.getLugarExpedicion());// codigo postal 

	        //EMISOR
	        xml.setEmisor(createEmisor(of, cabeceraXml));

	        //RECEPTOR
	        xml.setReceptor(createReceptor(of, cabeceraXml));

	        //CONCEPTOS
	        xml.setConceptos(createConceptos(of, listaConceptos));

	        //ImpuestosTotales
	        xml.setImpuestos(createImpuestos(of, impuesto));

	        

	        

	        xml.setCertificado(cabeceraXml.getCertificado());
	        xml.setNoCertificado(cabeceraXml.getNoCertificado());

	        return xml;
	    }

	    
	    private static Emisor createEmisor(ObjectFactory of, CabeceraXml cabeceraXml) {
	        Emisor emisor = of.createComprobanteEmisor();
	        emisor.setNombre(cabeceraXml.getNombreEmisor());
	        emisor.setRfc(cabeceraXml.getRfcEmisor());
	        emisor.setRegimenFiscal(cabeceraXml.getRegimenFiscal());
	        return emisor;
	    }

	    private static Receptor createReceptor(ObjectFactory of, CabeceraXml cabeceraXml) {
	        Receptor receptor = of.createComprobanteReceptor();
	        receptor.setRfc(cabeceraXml.getRfcReceptor());
	        receptor.setNombre(cabeceraXml.getNombreReceptor());
	        //receptor.setResidenciaFiscal(CPais.MEX);
	        //receptor.setNumRegIdTrib("ResidenteExtranjero1");
	        receptor.setUsoCFDI(CUsoCFDI.fromValue(cabeceraXml.getUsoCFDI()));
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
	        c1.setClaveUnidad(concepto.getClaveUnidad());
	        c1.setUnidad(concepto.getUnidad());
	        c1.setDescripcion(concepto.getDescripcion());
	        c1.setValorUnitario(new BigDecimal(concepto.getValorUnitario()));
	        c1.setImporte(new BigDecimal(concepto.getImporte()));
	        //c1.setDescuento(new BigDecimal("100.00"));
	        c1.setImpuestos(createImpuestosConceptos(of, concepto.getImpuesto(), concepto.getTasaOCuota(), concepto.getImporteImpuesto(), concepto.getBase()));
	        list.add(c1);
	            
	        }  
	        return cps;
	    }

	    private static Concepto.Impuestos createImpuestosConceptos(ObjectFactory of, String impuesto, String tasa, String importe, String base) {
	        Concepto.Impuestos imp = of.createComprobanteConceptosConceptoImpuestos();
	        Concepto.Impuestos.Traslados trs = of.createComprobanteConceptosConceptoImpuestosTraslados();
	        Concepto.Impuestos.Traslados.Traslado tr = of.createComprobanteConceptosConceptoImpuestosTrasladosTraslado();
	        tr.setImpuesto(impuesto);
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
	    private static Impuestos createImpuestos(ObjectFactory of, Impuesto impuesto) {
	        Impuestos imps = of.createComprobanteImpuestos();
	        imps.setTotalImpuestosTrasladados(new BigDecimal(impuesto.getTotalImpuestosTraslados()));
	        imps.setTraslados(createTraslados(of,impuesto));
	        return imps;
	    }

	    private static Impuestos.Traslados createTraslados(ObjectFactory of, Impuesto impuesto) {
	        Impuestos.Traslados its = of.createComprobanteImpuestosTraslados();
	        Impuestos.Traslados.Traslado it = of.createComprobanteImpuestosTrasladosTraslado();
	        it.setImpuesto(impuesto.getImpuesto());
	        it.setTipoFactor(CTipoFactor.TASA);
	        it.setTasaOCuota(new BigDecimal(impuesto.getTasaOCuota()));
	        it.setImporte(new BigDecimal(impuesto.getImporte()));
	        its.getTraslado().add(it);
	        return its;
	    }

	   

	    


}
