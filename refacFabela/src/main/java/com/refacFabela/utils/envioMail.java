package com.refacFabela.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import com.refacFabela.utils.*;

@Component

public class envioMail {

	private static String host = "mail.refaccionesfabela.com";
	private static String user = "ventas@refaccionesfabela.com";
	private static String password = "K!Peak,HrPs!";
	private static String port = "587";
	private static String hostbcc = "facturas@refaccionesfabela.com";
	private static String hostbcc2 = "jesus_fabela5@hotmail.com";



	public String enviarCorreo(String correo, String paramasunto, String mensaje, String ruta, String nombreArchivo,
			int tipo) {

		System.err.println(correo);
		System.err.println(paramasunto);
		System.err.println(mensaje);
		System.err.println(ruta);
		System.err.println(nombreArchivo);
		System.err.println(tipo);

		correo="fabelapedro@gmail.com";
		String error = "";

		Properties properties = new Properties();
		String siEnvio = "NO";

		try {
			properties.put("mail.smtp.host", host);
			properties.put("mail.transport.protocol", "smtp");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.user", user);
			properties.setProperty("mail.password", password);
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.port", port); // 587
			// sesion
			Session mailSession = Session.getInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			});

			// construyendo el mensaje
			Message msg = new MimeMessage(mailSession);
			msg.setSubject("Refaciones Fabela ");
			try {

				msg.setFrom(new InternetAddress(user, paramasunto));
			} catch (Exception e) {
				error = "error en conexion  " + e;

			}

			msg.addRecipients(Message.RecipientType.TO, new InternetAddress[] { new InternetAddress(correo) });
			msg.addRecipients(Message.RecipientType.BCC, new InternetAddress[] { new InternetAddress(hostbcc) });
			msg.addRecipients(Message.RecipientType.BCC, new InternetAddress[] { new InternetAddress(hostbcc2) });

			Multipart multipart = new MimeMultipart("related");
			BodyPart htmlPart = new MimeBodyPart();

			htmlPart.setContent("<html><center>" + "<img src='http://refaccionesfabela.com/LOGO.jpg'>" + "<br/>"
					+ "<h3><span style='color:blue;'>Refacciones Fabela</span></h3>" + "<p>" + mensaje + "<br/>"
					+ "NOTA: El contenido de este correo electr&oacute;nico es confidencial y exclusivo para el  destinatario, <br/>favor de no responder a esta "
					+ "direcci&oacute;n de correo, ya que no se encuentra habilitada para recibir mensajes."
					+ "<br/><br/><br/><br/>" + "</p></center></html>", "text/html");

			System.err.println(ruta + nombreArchivo);

			if (tipo == 2) {

				BodyPart adjuntoPDF = new MimeBodyPart();
				adjuntoPDF.setDataHandler(new DataHandler(new FileDataSource(ruta + "pdf/" + nombreArchivo + ".pdf")));
				adjuntoPDF.setFileName(nombreArchivo + ".pdf");

				BodyPart adjuntoXML = new MimeBodyPart();
				adjuntoXML.setDataHandler(new DataHandler(new FileDataSource(ruta + "xml/" + nombreArchivo + ".xml")));
				adjuntoXML.setFileName(nombreArchivo + ".xml");

				multipart.addBodyPart(adjuntoPDF);
				multipart.addBodyPart(adjuntoXML);

			}else {

				BodyPart adjuntoPDF = new MimeBodyPart();
				adjuntoPDF.setDataHandler(new DataHandler(new FileDataSource(ruta + nombreArchivo + ".pdf")));
				adjuntoPDF.setFileName(nombreArchivo + ".pdf");
				multipart.addBodyPart(adjuntoPDF);

			}
			multipart.addBodyPart(htmlPart);
			msg.setContent(multipart);

			javax.mail.Transport.send(msg, msg.getAllRecipients());
			siEnvio = "SI";
		} catch (Exception e) {

		}

		if (siEnvio == "SI") {
			System.err.println("TE HEMOS ENVIADO UN CORREO, FAVOR DE VERIFICAR TU BANDEJA DE ENTRADA");
		} else {
			error = "Tramite EMITIDO. Solicitante sin direccion de correo electronico. Correo NO enviado....";
		}
		return siEnvio;

	}

}
