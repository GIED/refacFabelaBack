package com.refacFabela.utils.factura;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Base64;
import java.util.Calendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.refacFabela.model.TcDatosFactura;

import mx.grupocorasa.sat.cfd._40.Comprobante;



public class ConstantesFactura {
	
	 public static final String version = "4.0";
	    public static final String serie = "B";
	    public static final String formaPago = "03"; // 01 efectivo, 02- cheque nominativo, 03 tranferencia, 99 por definir catalogo selecciona caja *****
	    public static final String condicionesPago = "Pago en una sola exhibición"; //constante 
	    public static final String moneda = "MXN"; // constante
	    public static final String tipoComprobante = "I"; // constante 
	    public static final String MetodoPago = "PUE";
	    public static final String lugarExpedicion = "50020"; //Codigo postal
	    public static final String regimenFiscal = "612"; //pendiente
	    public static final String usoCFDI = "I04"; // catalogo selecciona caja *****
	    public static final String claveUnidad = "H87"; //  
	    public static final String impuesto = "002"; // constante 
	    public static final String TipoFactor = "Tasa"; // constante
	    public static final String TasaOCuota = "0.160000";
	    public static final String exportacion = "01";
	    public static final String objetoImp = "02";
	    
	    
	   
	    public static final String logo64 = "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";
	    public static final String emailprueba = "jesus_fabela5@hotmail.com ";
	    //public static final String cadenaOriginalXslt = "/opt/webserver/backEnd/refacFabela/factura/archivos/cadenaoriginal_3_3.xslt";   
	    public static final String cadenaOriginalXslt = "/opt/webserver/backEnd/refacFabela/factura/archivos/cadenaoriginal_4_0.xslt";   

	    //pruebas
	    
//	    public static final String noCertificado = "20001000000300022823";
//	    public static final String certificado = "MIIDhDCCAmygAwIBAgIUMjAwMDEwMDAwMDAzMDAwMjI4MjMwDQYJKoZIhvcNAQEFBQAwSzEVMBMGA1UEAwwMQS5DLiBQcnVlYmFzMRAwDgYDVQQKDAdQcnVlYmFzMQswCQYDVQQGEwJNWDETMBEGA1UEBwwKQ3VhdWh0ZW1vYzAeFw0xNzA0MjgxODMwMzZaFw0yMjEwMTkxODMwMzZaMIGQMRswGQYDVQQDDBJQYWJsbyBOZXJ1ZGEgUGVyZXoxGzAZBgNVBCkMElBhYmxvIE5lcnVkYSBQZXJlejEbMBkGA1UECgwSUGFibG8gTmVydWRhIFBlcmV6MSUwIwYDVQQtDBxURVNUMDEwMjAzMDAxIC8gVEVTVDEwMzE3QTQ2MRAwDgYDVQQLDAdDRU5UUkFMMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4S8y29PfV6zBib8HEx/SK2XaUBeAb5YZbL+MHjX4K710kFdskgYhX35N0StfN5wbHLIsnj0eLtIk7gjXmaAjF6PkM9jtRtUrWgS22lcah0K7ws+nqfhNFuPX9rfm8SXzkBFDjUiBmW0U0Lz3cT/fEoJiqMxTwgJyhpuQ3vIcrv85cUaNpnf85eSCWVXGFmXpZD7EgXKLQulD1OOQbqcBPF6sK0wlz27HeQsM0X+2rO+RJvWAsHqIT4z0Sct4FFzj3XjGiF+DK8KxbQOmWpTnOSw0k9oKHmrFG3c1GxxyhvHoz+RurZFWPwuCWAzaUDhwq3uB6dtP7foeEjUUFlRD/wIDAQABoxowGDAJBgNVHRMEAjAAMAsGA1UdDwQEAwIGwDANBgkqhkiG9w0BAQUFAAOCAQEAl/4v3mCKfiwvPqmtFOnQ5HomJQq5W351gC5vKwP4vDKSdFDsvKtU5UMET6AnN6zHdI13AIZiOQQ8t1yv8RByMQ8dSHaoWaBkR0thzbK+Uol91Rp/TffNfneESAqvfBWKwXBTxGIxTNkJl5XXG5SyChRIan2sXvrSEGmfqxzzf3X5QFeQstRw80RbUWS21PGSgC9LMGkQVd76wqujP6P/QPjbYes5PD3xV0+6vbm3Q2NGj24s42I4Op2UEX+QjkTMd2o6FY2ek1zzcVjCyN2eNRgb/OKDjfiA/tyJ0HV1JxW3nc7BgIpRuHRI63NLWzIxe8233LgIf+y1oypT2W/o9Q==";
//	    public static final String nombreEmisor = "EMISOR";
//	    public static final String rfcEmisor = "TEST010203001";
//	    public static final String rfcReceptor = "TES030201001";   
//	    public static final String passwordKey = "12345678a";
//	    public static final String usuarioFolios = "FAMJ810312D33";  // producción FAMJ810312FY6
//	    public static final String passwordFolios = "contRa$3na";  // producción 1Nfzoaj@
//	    public static final String emailusuario = "ventas@refaccionesfabela.com";
//	    public static final String emailpass = "fabela20";     
//	    public static final String rutaxmlTimbrado = "/apache-tomcat-8.5.61/webapps/xml/";
//	    public static final String rutaPdf = "/apache-tomcat-8.5.61/webapps/pdf/";
//	     public static final String rutaCer = "/opt/facturas/archivos/CertificadoFirmadoPF.cer";
//	    public static final String rutaKey = "/opt/facturas/archivos/LlavePkcs8PF.key";
//	     
	     
	    //producción
	    
	    public static final String noCertificado = "00001000000506837151";
	    public static final String certificado = "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";
	    public static final String nombreEmisor = "JESUS ALEJANDRO FABELA MARTINEZ";
	    public static final String rfcEmisor = "FAMJ810312FY6";
	    public static final String rfcReceptor = "TES030201001";   
	    public static final String passwordKey = "fabela1203";
	    public static final String usuarioFolios = "CJE210818NY8"; 
	    public static final String passwordFolios = "gypF1JW$";  
	    public static final String emailusuario = "ventas@refaccionesfabela.com";
	    public static final String emailpass = "fabela20";     
	    public static final String rutaxmlTimbrado = "/opt/webserver/backEnd/refacFabela/xml/";
	    public static final String rutaPdf = "/opt/webserver/backEnd/refacFabela/pdf/";
	    public static final String rutaPdfProveedor = "/opt/webserver/backEnd/refacFabela/pdfproveedor/";
	    public static final String rutaRaiz = "/opt/webserver/backEnd/refacFabela/";
	    public static final String rutaCer = "/opt/webserver/backEnd/refacFabela/factura/archivos/00001000000506837151.cer";
	    public static final String rutaKey = "/opt/webserver/backEnd/refacFabela/factura/archivos/CSD_JESUS_ALEJANDRO_FABELA_MARTINEZ_FAMJ810312FY6_20210321_221418.key";
	    
	    
	    public static Double truncaValor(Double numero) {

	        BigDecimal bd = new BigDecimal(numero);
	        bd = bd.setScale(2, RoundingMode.HALF_UP);
	        System.out.println(bd.doubleValue());

	        return bd.doubleValue();
	    }
	    
	    public static XMLGregorianCalendar getXMLCalendar() throws Exception {
	        Calendar sDate = Calendar.getInstance();
	        DatatypeFactory dtf = DatatypeFactory.newInstance();
	        XMLGregorianCalendar calendar;

	        calendar = dtf.newXMLGregorianCalendar();
	        calendar.setYear(sDate.get(Calendar.YEAR));
	        calendar.setDay(sDate.get(Calendar.DAY_OF_MONTH));
	        calendar.setMonth(sDate.get(Calendar.MONTH) + 1);
	        calendar.setHour(sDate.get(Calendar.HOUR));
	        calendar.setMinute(sDate.get(Calendar.MINUTE));
	        calendar.setSecond(sDate.get(Calendar.SECOND));

	        System.out.println("Calendar: " + calendar);

	        return calendar;

	    }
	    
	    public static String xmltoString(Comprobante xml) {

	        String xmlString = "";
	        try {

	            JAXBContext context = JAXBContext.newInstance(Comprobante.class);
	            Marshaller m = context.createMarshaller();

	            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	            m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	            m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new DefaultNamespacePrefixMapper());
	            m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd");
	            m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
	          

	            // String rutaComprobante = "C:/Users/gioca/Downloads/facturacion/prueba/cfdi.xml";        
	            // m.marshal(xml, new File(rutaComprobante));
	            StringWriter sw = new StringWriter();
	            m.marshal(xml, sw);

	            xmlString = sw.toString();

	            //System.out.println("xml en string: " + xmlString);
	        } catch (JAXBException e) {
	            e.printStackTrace();
	        }
	        return xmlString;
	    }
	    
	    public static String xmltoStringPagos(Comprobante xml) {

	        String xmlString = "";
	        try {

	            JAXBContext context = JAXBContext.newInstance(Comprobante.class);
	            Marshaller m = context.createMarshaller();
	            

	            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	            m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	            m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new DefaultNamespacePrefixMapper());
	            m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd http://www.sat.gob.mx/Pagos20 http://www.sat.gob.mx/sitio_internet/cfd/Pagos/Pagos20.xsd");
	            m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
	           

	      
	          

	            // String rutaComprobante = "C:/Users/gioca/Downloads/facturacion/prueba/cfdi.xml";        
	            // m.marshal(xml, new File(rutaComprobante));
	            StringWriter sw = new StringWriter();
	            m.marshal(xml, sw);

	            xmlString = sw.toString();

	            //System.out.println("xml en string: " + xmlString);
	        } catch (JAXBException e) {
	            e.printStackTrace();
	        }
	        return xmlString;
	    }
	    
	    public static void obtenerArchivoPdf(String cadena, String nombreArchivo, TcDatosFactura tcDatosFactura) {
	        File file = new File(tcDatosFactura.getsRutaPdf() + nombreArchivo);

	        try (FileOutputStream fos = new FileOutputStream(file);) {
	            // To be short I use a corrupted PDF string, so make sure to use a valid one if you want to preview the PDF file
	            String b64 = cadena;
	            byte[] decoder = Base64.getDecoder().decode(b64);

	            fos.write(decoder);
	            System.out.println("PDF File Saved");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    }

}
