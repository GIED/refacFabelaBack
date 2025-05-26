package com.refacFabela.utils.factura;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.util.PropertySource.Util;
import org.springframework.stereotype.Component;

import com.refacFabela.model.TcDatosFactura;
import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TwVenta;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.model.factura.CabeceraPagosXml;
import com.refacFabela.model.factura.CabeceraXml;
import com.refacFabela.model.factura.ConceptoXml;
import com.refacFabela.model.factura.Impuesto;
import com.refacFabela.utils.DateTimeUtil;
import com.refacFabela.utils.utils;
@Component
public class Transformar {
	
	   public CabeceraXml objCabecera(List<TwVentasProducto> productosVendidos, TwVenta twVenta, String cveCfdi, TcDatosFactura tcDatosFactura, List<TrVentaCobro> listaVentaCobro) {

	      

	        CabeceraXml cabeceraXmlBean = new CabeceraXml();
	        
                utils util =new utils();
                
                /*Se verifica que no tenga más de un metodo de pago y que el pago en efectivo no sea mayor a 2000, de lo contrario sale por definir*/
                if(listaVentaCobro.size()>1 || (calcularTotal(productosVendidos).compareTo(BigDecimal.valueOf(2000)) >= 0 && (twVenta.getTcFormapago().getnId()==1L) )) {
                	cabeceraXmlBean.setFormaPago("99");
                	cabeceraXmlBean.setMetodoPago("Pago en parcialidades o diferido");
                }
                else {
                	cabeceraXmlBean.setFormaPago(twVenta.getTcFormapago().getsClave());
                	cabeceraXmlBean.setMetodoPago(ConstantesFactura.MetodoPago);
                	
                }
                
                
	        
	            cabeceraXmlBean.setVersion(tcDatosFactura.getsVersion());
	            cabeceraXmlBean.setSerie(tcDatosFactura.getsSerie());
	            cabeceraXmlBean.setFolio(twVenta.getnId().toString());	            
	            cabeceraXmlBean.setCondicionesPago(ConstantesFactura.condicionesPago);
	            cabeceraXmlBean.setSubTotal(String.valueOf(calculaSubTotal(productosVendidos)));
	            cabeceraXmlBean.setMoneda(ConstantesFactura.moneda);
	            cabeceraXmlBean.setTotal(String.valueOf(calcularTotal(productosVendidos)));
	            cabeceraXmlBean.setTipoComprobante(ConstantesFactura.tipoComprobante);
	            
	            cabeceraXmlBean.setLugarExpedicion(tcDatosFactura.getsCodigoPostal());
	            cabeceraXmlBean.setNoCertificado(tcDatosFactura.getsNoCertificado());
	            cabeceraXmlBean.setCertificado(tcDatosFactura.getsCertificado());
	            cabeceraXmlBean.setSello("");
	            cabeceraXmlBean.setNombreEmisor(tcDatosFactura.getsNombreEmisor());
	            cabeceraXmlBean.setRfcEmisor(tcDatosFactura.getsRfcEmisor());
	            cabeceraXmlBean.setRegimenFiscal(tcDatosFactura.getsRegimenFiscal());
	            cabeceraXmlBean.setUsoCFDI(cveCfdi);//valor que recibe del front
	            cabeceraXmlBean.setNombreReceptor(twVenta.getTcCliente().getsRazonSocial());
	            cabeceraXmlBean.setRfcReceptor(twVenta.getTcCliente().getsRfc());// cambiar por rfc de camp 
	            cabeceraXmlBean.setEmailReceptor(twVenta.getTcCliente().getsCorreo());// cambiar por email camp
	            cabeceraXmlBean.setRegimenFiscalReceptor(twVenta.getTcCliente().getTcRegimenFiscal().getsCveRegimen());
	            cabeceraXmlBean.setDomicilioFiscalReceptor(twVenta.getTcCliente().getTcCp().getsCp());
	            cabeceraXmlBean.setExportacion(ConstantesFactura.exportacion);
	        

	        return cabeceraXmlBean;

	    }
	   
	   public CabeceraPagosXml objCabeceraPagos(List<TwVentasProducto> productosVendidos, TwVenta twVenta, String cveCfdi) {

		      

	        CabeceraPagosXml cabeceraXmlBean = new CabeceraPagosXml();
	        
               utils util =new utils();
	        
	            cabeceraXmlBean.setVersion(ConstantesFactura.version);
	            cabeceraXmlBean.setSerie(ConstantesFactura.serie);
	            cabeceraXmlBean.setFolio(twVenta.getnId().toString()+"_1");	           
	            cabeceraXmlBean.setSubTotal(String.valueOf(0));
	            cabeceraXmlBean.setMoneda(ConstantesFactura.moneda);
	            cabeceraXmlBean.setTotal(String.valueOf(0));
	            cabeceraXmlBean.setTipoComprobante("P");	           
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
	            cabeceraXmlBean.setRegimenFiscalReceptor(twVenta.getTcCliente().getTcRegimenFiscal().getsCveRegimen());
	            cabeceraXmlBean.setDomicilioFiscalReceptor(twVenta.getTcCliente().getTcCp().getsCp());
	            cabeceraXmlBean.setExportacion(ConstantesFactura.exportacion);
	        

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
	            conceptoXmlBean.setValorUnitario(String.valueOf(lista.get(i).getnPrecioUnitario()));
	            conceptoXmlBean.setImporte(String.valueOf(lista.get(i).getnPrecioPartida()));
	            conceptoXmlBean.setBase(String.valueOf(lista.get(i).getnPrecioPartida()));
	            conceptoXmlBean.setImpuesto(ConstantesFactura.impuesto);
	            conceptoXmlBean.setTipoFactor(ConstantesFactura.TipoFactor);
	            conceptoXmlBean.setTasaOCuota(ConstantesFactura.TasaOCuota);
	            conceptoXmlBean.setImporteImpuesto(String.valueOf(lista.get(i).getnIvaPartida()));
	            conceptoXmlBean.setObjetoImp(ConstantesFactura.objetoImp);
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
	        impuesto.setTotalImpuestosTraslados(String.valueOf(calculaIvaTotal(lista)));
	        impuesto.setImpuesto(ConstantesFactura.impuesto);
	        impuesto.setTipoFactor(ConstantesFactura.TipoFactor);
	        impuesto.setTasaOCuota(ConstantesFactura.TasaOCuota);
	        impuesto.setImporte(String.valueOf(calculaIvaTotal(lista)));

	        return impuesto;
	    }

	    private static BigDecimal calculaSubTotal(List<TwVentasProducto> lista) {
	        BigDecimal subTotal = BigDecimal.ZERO;
	        utils util =new utils();
	        for (int i = 0; i < lista.size(); i++) {
	            subTotal = subTotal.add( lista.get(i).getnPrecioPartida());
	        }
	        return DateTimeUtil.truncarDosDecimales(subTotal);
	    }

	    private static BigDecimal calculaIvaTotal(List<TwVentasProducto> lista) {
	    	BigDecimal iva = BigDecimal.ZERO;
	        utils util =new utils();
	        for (int i = 0; i < lista.size(); i++) {
	            iva = iva.add( lista.get(i).getnIvaPartida());
	        }

	        return DateTimeUtil.truncarDosDecimales(iva);
	    }
	    
	    private static BigDecimal calcularTotal(List<TwVentasProducto> lista){     
	    	BigDecimal  total=BigDecimal.ZERO;      
	      utils util =new utils();
	        total= calculaSubTotal(lista).add(calculaIvaTotal(lista)) ; 
	        return  DateTimeUtil.truncarDosDecimales(total);
	    }

}
