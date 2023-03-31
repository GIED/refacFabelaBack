package com.refacFabela.utils.factura;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.ssl.PKCS8Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.refacFabela.model.TwFacturacion;
import com.refacFabela.model.TwVenta;
import com.refacFabela.model.factura.CabeceraXml;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.service.FacturacionService;
import com.refacFabela.service.VentasService;
import com.refacFabela.utils.envioMail;
import com.refacFabela.utils.utils;
import com.refacFabela.ws.org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaCreditos;
import com.refacFabela.ws.org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaTFD33;
import com.refacFabela.ws.org.tempuri.IWSCFDI33;
import com.refacFabela.ws.org.tempuri.WSCFDI33;

import mx.grupocorasa.sat.cfd._40.Comprobante;


@Component
public class TimbrarXml {
	
	@Autowired 
	private FacturacionService facturacionService;
	
	@Autowired 
	private VentasService ventasService;
	
	@Autowired
	private VentasRepository ventasRepository;

	public String timbrarXml(Comprobante xml, Long idVenta, CabeceraXml cabecera)
			throws GeneralSecurityException, IOException, ParserConfigurationException, SAXException, Exception {
		String xmlString = "";
		String cadenaOriginal = "";
		PrivateKey llavePrivada = null;
		String selloDigital = "";

		File key = new File(ConstantesFactura.rutaKey);

		try {

			xmlString = ConstantesFactura.xmltoString(xml);

			System.out.println("xml: " + xmlString);

			cadenaOriginal = generarCadenaOriginal(xmlString);
			// obtener llave privada
			llavePrivada = getPrivateKey(key, ConstantesFactura.passwordKey);

			// obtener sello digital
			selloDigital = generarSelloDigital(llavePrivada, cadenaOriginal);
			System.out.println("sali a geerar sello digital");
			String SelloModificado = quitarSaltos(selloDigital);
			System.out.println("sali a  sello digital modificado");

			// Agregamos sello al xml
			xml.setSello(SelloModificado);
			System.out.println("sali de agregar sello xml");

			String consello = ConstantesFactura.xmltoString(xml);
			System.out.println("sali de transformar xml a string");

			// mandamos xml a timbrar al webservice
			if (consultaFolio() > 0) {
				System.out.println("ENTRE A TIMBRAR");
				procesarXml(consello, idVenta, cabecera, cadenaOriginal);
			} else {
				System.err.println("creditos insuficientes");
			}

		} catch (TransformerException e) {
			Logger.getLogger(GeneraXml.class.getName()).log(Level.SEVERE, null, e);
		}

		return "Timbrado Correcto";

	}

	private static String generarCadenaOriginal(final String xml) throws TransformerException {

		StreamSource streamSource = new StreamSource(ConstantesFactura.cadenaOriginalXslt);
		TransformerFactory trasnformerFactory = TransformerFactory.newInstance();
		Transformer xlsTranformer = trasnformerFactory.newTransformer(streamSource);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		xlsTranformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(output));
		String resultado = "";
		try {
			resultado = output.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			Logger.getLogger(GeneraXml.class.getName()).log(Level.SEVERE, null, e);
		}

		return resultado;

	}

	private static PrivateKey getPrivateKey(final File keyFile, final String password)
			throws GeneralSecurityException, IOException {
		FileInputStream in = new FileInputStream(keyFile);
		PKCS8Key pkcs8 = new PKCS8Key(in, password.toCharArray());
		byte[] decrypted = pkcs8.getDecryptedBytes();
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decrypted);

		System.out.println("llegue a obtener ");

		PrivateKey pk = null;

		if (pkcs8.isDSA()) {
			pk = KeyFactory.getInstance("DSA").generatePrivate(spec);
		} else if (pkcs8.isRSA()) {
			pk = KeyFactory.getInstance("RSA").generatePrivate(spec);
		}
// For lazier types (like me):

		System.out.println("private key: " + pkcs8.getPrivateKey());
		pk = pkcs8.getPrivateKey();

		return pk;

	}

	private static String generarSelloDigital(final PrivateKey key, final String cadenaOriginal)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException,
			UnsupportedEncodingException {
		System.out.println("entre a generar sello digital");
		Signature sign = Signature.getInstance("SHA256withRSA");
		sign.initSign(key, new SecureRandom());
		sign.update(cadenaOriginal.getBytes());
		byte[] signature = sign.sign();
		return new String(StringUtils.newStringUtf8(Base64.encodeBase64(signature)));
	}
	
	
	private  void procesarXml(final String xml, Long idVenta, CabeceraXml cabecera, String cadenaOriginal) throws ParserConfigurationException, SAXException, IOException, TransformerException, Exception {
        RespuestaTFD33 Respuesta;
        
        utils util=new utils();

        Respuesta = timbrarCFDI(ConstantesFactura.usuarioFolios, ConstantesFactura.passwordFolios, xml, "TIMBRADO33");
       
       
        TwFacturacion twFacturacion = new TwFacturacion();

        if (Respuesta.isOperacionExitosa()) {

            stringToDom(Respuesta.getXMLResultado().getValue(), idVenta);

            System.out.println("Operación exitosa");
            System.out.println(Respuesta.getXMLResultado().getValue());
            System.out.println("PDF: " + Respuesta.getPDFResultado().getValue());
            System.out.println(Respuesta.getTimbre().getValue().getEstado().getValue());
            System.out.println(Respuesta.getTimbre().getValue().getNumeroCertificadoSAT().getValue());
            System.out.println(Respuesta.getTimbre().getValue().getSelloCFD().getValue());
            System.out.println(Respuesta.getTimbre().getValue().getSelloSAT().getValue());
            System.out.println(Respuesta.getTimbre().getValue().getUUID().getValue());
          
            twFacturacion.setN_idVenta(idVenta);
            twFacturacion.setsUuid(Respuesta.getTimbre().getValue().getUUID().getValue());
            twFacturacion.setsEstado(Respuesta.getTimbre().getValue().getEstado().getValue());
            twFacturacion.setS_noCertificadoSat(Respuesta.getTimbre().getValue().getNumeroCertificadoSAT().getValue());
            twFacturacion.setS_selloCfd(Respuesta.getTimbre().getValue().getSelloCFD().getValue());
            twFacturacion.setS_selloSat(Respuesta.getTimbre().getValue().getSelloSAT().getValue()); 
            twFacturacion.setS_cadenaOriginal(cadenaOriginal);
            twFacturacion.setnEstatus(1);
            
            twFacturacion = facturacionService.guardar(twFacturacion);
            
            TwVenta twVenta = this.ventasService.consltaVentasId(idVenta);
            
            twVenta.setnIdFacturacion(twFacturacion.getnId());
            
            this.ventasService.updateStatusVenta(twVenta);
            
            generarPdf(Respuesta.getTimbre().getValue().getUUID().getValue(), idVenta);
            
            TwVenta venta=new TwVenta();
            
            
            
            /*Consulta para conocer el correo del cliente*/
            
            String ruta="";
    		String rutaRaiz="";
            venta=ventasRepository.findBynId(idVenta);
           
            ruta=com.refacFabela.enums.TipoDoc.PDF_FACTURA.getPath();
			rutaRaiz=ConstantesFactura.rutaRaiz;
            String nombreArchivo=venta.getnId().toString();  
            
            /*Envió de correo*/
                      
              
                        
            envioMail enviar=new envioMail();
       				enviar.enviarCorreo(venta.getTcCliente().getsCorreo(), 
       						"Factura_"+venta.getnId(),
       						"<p>Adjunto al presente factura No. "+venta.getnId()+"</p><p> Sin m&aacute;s por el momento envi&oacute; un cordial saludo.</p>",
       						rutaRaiz,
       						nombreArchivo,
       						2
       						);
            
            
            
  

        } else {
            System.out.println("Hubo un error en la operación");
            System.out.println(Respuesta.getCodigoRespuesta().getValue());
            System.out.println(Respuesta.getMensajeErrorDetallado().getValue());
        }

        if (Respuesta.getCodigoConfirmacion().getValue() != null) {
            System.out.println("Codigo de Confirmacion: " + Respuesta.getCodigoConfirmacion().getValue());
        }    
 
       
    
        
      
    }
	

	public static String quitarSaltos(String cadena) {
		return cadena.replaceAll("\n", "");
	}
	
	private static void generarPdf (String UUID, Long idventa){
        RespuestaTFD33 Respuesta;
        
        //Se declara una variable de tipo String para enviar el logo en Base 64
        String logo64 =ConstantesFactura.logo64;
        String nombreArchivo= idventa+".pdf";
        // "Usuario" , "Contraseña", "UUID", "LogoBase64"
        Respuesta = obtenerPDF(ConstantesFactura.usuarioFolios, ConstantesFactura.passwordFolios, UUID, logo64);
        
        //Se verifica el resultado
        if(Respuesta.isOperacionExitosa())
        {
            System.out.println("Resultado exitoso");
            String pdf;
            pdf = Respuesta.getPDFResultado().getValue();
            ConstantesFactura.obtenerArchivoPdf(pdf, nombreArchivo);
            System.out.println(pdf);
        }
        else
        {
            System.out.println("Obtención incorrecta");
            System.out.println(Respuesta.getMensajeErrorDetallado().getValue());
            System.out.println(Respuesta.getCodigoRespuesta().getValue());
        }
    }


	public static int consultaFolio() {

		int restantes = 0;

		RespuestaCreditos Respuesta;

		// Se invoca al método del WS
		Respuesta = consultarCreditos(ConstantesFactura.usuarioFolios, ConstantesFactura.passwordFolios);

		// Se comprueba le operación
		if (Respuesta.isOperacionExitosa()) {
			for (int i = 0; i < Respuesta.getPaquetes().getValue().getDetallesPaqueteCreditos().size(); i++) {
				  System.out.println("En Uso: " + Respuesta.getPaquetes().getValue().getDetallesPaqueteCreditos().get(i).isEnUso().booleanValue());
	                 System.out.println(Respuesta.getPaquetes().getValue().getDetallesPaqueteCreditos().get(i).getFechaActivacion().toString());
	                 System.out.println(Respuesta.getPaquetes().getValue().getDetallesPaqueteCreditos().get(i).getFechaVencimiento().toString());
	                 System.out.println(Respuesta.getPaquetes().getValue().getDetallesPaqueteCreditos().get(i).getPaquete().getValue());
	                 System.out.println(Respuesta.getPaquetes().getValue().getDetallesPaqueteCreditos().get(i).getTimbres().intValue());
	                 System.out.println(Respuesta.getPaquetes().getValue().getDetallesPaqueteCreditos().get(i).getTimbresRestantes().intValue());
	                 System.out.println(Respuesta.getPaquetes().getValue().getDetallesPaqueteCreditos().get(i).getTimbresUsados().intValue());
	                 System.out.println(Respuesta.getPaquetes().getValue().getDetallesPaqueteCreditos().get(i).isVigente());
	                  restantes=Respuesta.getPaquetes().getValue().getDetallesPaqueteCreditos().get(i).getTimbresRestantes().intValue();
	                         System.out.println("RESTANTES :"+ restantes);
			}

		} else {
			System.out.println("Hubo un error al realizar la consulta");
			System.out.println(Respuesta.getMensajeError().getValue());
		}

		return restantes;
	}
	
	private static void stringToDom(String xmlSource, Long idVenta)
            throws SAXException, ParserConfigurationException, IOException, TransformerConfigurationException, TransformerException {
        // Parse the given input
        String nombreArchivo= idVenta+".xml";
        String rutaComprobante = ConstantesFactura.rutaxmlTimbrado+nombreArchivo;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlSource)));

        // Write the parsed document to an xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        StreamResult result = new StreamResult(new File(rutaComprobante));
        transformer.transform(source, result);
    }
	
	private static RespuestaTFD33 obtenerPDF(java.lang.String usuario, java.lang.String password, java.lang.String uUID, java.lang.String logoBase64) {
        WSCFDI33 service = new WSCFDI33();
        IWSCFDI33 port = service.getSoapHttpEndpoint();
        return port.obtenerPDF(usuario, password, uUID, logoBase64);
    }
	
	private static RespuestaCreditos consultarCreditos(java.lang.String usuario, java.lang.String password) {
		System.out.println("llege a consultar creditos");
		WSCFDI33 service = new WSCFDI33();
		IWSCFDI33 port = service.getSoapHttpEndpoint();
		return port.consultarCreditos(usuario, password);
	}
	
	private static RespuestaTFD33 timbrarCFDI(java.lang.String usuario, java.lang.String password, java.lang.String cadenaXML, java.lang.String referencia) {
        WSCFDI33 service = new WSCFDI33();
        IWSCFDI33 port = service.getSoapHttpEndpoint();
        return port.timbrarCFDI(usuario, password, cadenaXML, referencia);
    }

}
