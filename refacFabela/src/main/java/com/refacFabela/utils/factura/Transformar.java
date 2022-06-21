package com.refacFabela.utils.factura;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.refacFabela.model.TwVenta;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.model.factura.CabeceraXml;
import com.refacFabela.model.factura.ConceptoXml;
import com.refacFabela.model.factura.Impuesto;
import com.refacFabela.utils.utils;
@Component
public class Transformar {
	
	   public CabeceraXml objCabecera(List<TwVentasProducto> productosVendidos, TwVenta twVenta, String cveCfdi) {

	      

	        CabeceraXml cabeceraXmlBean = new CabeceraXml();
	        
                utils util =new utils();
	        
	            cabeceraXmlBean.setVersion(ConstantesFactura.version);
	            cabeceraXmlBean.setSerie(ConstantesFactura.serie);
	            cabeceraXmlBean.setFolio(twVenta.getsFolioVenta());
	            cabeceraXmlBean.setFormaPago(twVenta.getTcFormapago().getsClave());
	            cabeceraXmlBean.setCondicionesPago(ConstantesFactura.condicionesPago);
	            cabeceraXmlBean.setSubTotal(String.valueOf(util.truncarDecimales(calculaSubTotal(productosVendidos))));
	            cabeceraXmlBean.setMoneda(ConstantesFactura.moneda);
	            cabeceraXmlBean.setTotal(String.valueOf(util.truncarDecimales(calcularTotal(productosVendidos))));
	            cabeceraXmlBean.setTipoComprobante(ConstantesFactura.tipoComprobante);
	            cabeceraXmlBean.setMetodoPago(ConstantesFactura.MetodoPago);
	            cabeceraXmlBean.setLugarExpedicion(ConstantesFactura.lugarExpedicion);
	            cabeceraXmlBean.setNoCertificado(ConstantesFactura.noCertificado);
	            cabeceraXmlBean.setCertificado(ConstantesFactura.certificado);
	            cabeceraXmlBean.setSello("");
	            cabeceraXmlBean.setNombreEmisor(ConstantesFactura.nombreEmisor);
	            cabeceraXmlBean.setRfcEmisor(ConstantesFactura.rfcEmisor);
	            cabeceraXmlBean.setRegimenFiscal(ConstantesFactura.regimenFiscal);
	            cabeceraXmlBean.setUsoCFDI(cveCfdi);//valor que recibe del front
	            cabeceraXmlBean.setNombreReceptor(twVenta.getTcCliente().getsRazonSocial());
	            cabeceraXmlBean.setRfcReceptor(twVenta.getTcCliente().getsRfc());// cambiar por rfc de camp 
	            cabeceraXmlBean.setEmailReceptor(twVenta.getTcCliente().getsCorreo());// cambiar por email camp
	        

	        return cabeceraXmlBean;

	    }

	   public List<ConceptoXml> listaConceptos(List<TwVentasProducto> lista) {

	        System.out.println("listaConceptos: " + lista);
	        List<ConceptoXml> listaConcepto = new ArrayList<ConceptoXml>();

	        for (int i = 0; i < lista.size(); i++) {
	            ConceptoXml conceptoXmlBean = new ConceptoXml();
	            utils util=new utils();
	            conceptoXmlBean.setClaveUnidad(ConstantesFactura.claveUnidad);
	            conceptoXmlBean.setClaveProdServ(lista.get(i).getTcProducto().getTcClavesat().getsClavesat());
	            conceptoXmlBean.setNoIdentificacion(String.valueOf(noIdentificador()));
	            conceptoXmlBean.setCantidad(String.valueOf(lista.get(i).getnCantidad()));
	            conceptoXmlBean.setUnidad("PZA");//checar no se tiene
	            conceptoXmlBean.setDescripcion(lista.get(i).getTcProducto().getsDescripcion());
	            conceptoXmlBean.setValorUnitario(String.valueOf(util.truncarDecimales(lista.get(i).getnPrecioUnitario())));
	            conceptoXmlBean.setImporte(String.valueOf(util.truncarDecimales(lista.get(i).getnPrecioPartida())));
	            conceptoXmlBean.setBase(String.valueOf(util.truncarDecimales(lista.get(i).getnPrecioPartida())));
	            conceptoXmlBean.setImpuesto(ConstantesFactura.impuesto);
	            conceptoXmlBean.setTipoFactor(ConstantesFactura.TipoFactor);
	            conceptoXmlBean.setTasaOCuota(ConstantesFactura.TasaOCuota);
	            conceptoXmlBean.setImporteImpuesto(String.valueOf(util.truncarDecimales(lista.get(i).getnIvaPartida())));
	            listaConcepto.add(conceptoXmlBean);
	        }

	        return listaConcepto;

	    }
	    
	    public int noIdentificador(){
	         Random r = new Random();
	        int valorDado = r.nextInt(10000000)+1;  // Entre 0 y 5, más 1.
	       
	        return valorDado;
	    }

	    public Impuesto obtenerImpuestoTotal(List<TwVentasProducto> lista) {
	        Impuesto impuesto = new Impuesto();
	        utils util =new utils();
	        impuesto.setTotalImpuestosTraslados(String.valueOf(util.truncarDecimales(calculaIvaTotal(lista))));
	        impuesto.setImpuesto(ConstantesFactura.impuesto);
	        impuesto.setTipoFactor(ConstantesFactura.TipoFactor);
	        impuesto.setTasaOCuota(ConstantesFactura.TasaOCuota);
	        impuesto.setImporte(String.valueOf(util.truncarDecimales(calculaIvaTotal(lista))));

	        return impuesto;
	    }

	    private static Double calculaSubTotal(List<TwVentasProducto> lista) {
	        Double subTotal = 0.0;
	        for (int i = 0; i < lista.size(); i++) {
	            subTotal = subTotal + lista.get(i).getnPrecioPartida();
	        }
	        return subTotal;
	    }

	    private static Double calculaIvaTotal(List<TwVentasProducto> lista) {
	        Double iva = 0.0;
	        for (int i = 0; i < lista.size(); i++) {
	            iva = iva + lista.get(i).getnIvaPartida();
	        }

	        return iva;
	    }
	    
	    private static Double calcularTotal(List<TwVentasProducto> lista){     
	      Double  total=0.0;
	      for (int i = 0; i < lista.size(); i++) {
	    	  total = total + lista.get(i).getnTotalPartida();
	        }
	        return total;
	    }

}
