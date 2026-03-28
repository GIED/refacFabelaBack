package com.refacFabela.utils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.refacFabela.config.MailConfigProperties;

@Component
public class envioMail {

	private static final String EMPRESA_NOMBRE = "Refacciones Fabela";
	private static final String MENSAJE_AUTOMATICO = "Mensaje automatico";

	private final MailConfigProperties mailConfig;

	@Autowired
	public envioMail(MailConfigProperties mailConfig) {
		this.mailConfig = mailConfig;
	}

	public String enviarCorreo(String correo, String paramasunto, String mensaje, String ruta, String nombreArchivo,
			int tipo) {
		if (!tieneTexto(correo)) {
			System.err.println("[envioMail] Correo no enviado: destinatario vacio.");
			return "NO";
		}

		String asunto = tieneTexto(paramasunto) ? paramasunto : "Notificacion " + EMPRESA_NOMBRE;
		boolean incluirAdjuntos = tieneTexto(ruta) && tieneTexto(nombreArchivo);

		try {
			Session mailSession = crearSesion();
			MimeMessage mensajeCorreo = crearMensaje(mailSession, correo, asunto, true);
			mensajeCorreo.setContent(construirContenidoCorreo(asunto, mensaje, incluirAdjuntos, ruta, nombreArchivo, tipo));
			Transport.send(mensajeCorreo, mensajeCorreo.getAllRecipients());
			System.out.println("[envioMail] Correo enviado a " + correo + " con asunto: " + asunto);
			return "SI";
		} catch (Exception e) {
			System.err.println("[envioMail] Error al enviar correo a " + correo + " con asunto: " + asunto);
			e.printStackTrace();
			return "NO";
		}

	}
	
	public void enviarCorreoEstandar(String destinatario, String asunto, String cuerpo) {
		if (!tieneTexto(destinatario)) {
			System.err.println("[envioMail] Correo estandar no enviado: destinatario vacio.");
			return;
		}

		String asuntoFinal = tieneTexto(asunto) ? asunto : "Notificacion " + EMPRESA_NOMBRE;

		try {
			Session session = crearSesion();
			MimeMessage message = crearMensaje(session, destinatario, asuntoFinal, false);
			message.setContent(construirContenidoCorreo(asuntoFinal, cuerpo, false, null, null, 0));
			Transport.send(message);
			System.out.println("[envioMail] Correo enviado a " + destinatario + " con asunto: " + asuntoFinal);
		} catch (Exception e) {
			System.err.println("[envioMail] Error al enviar correo estandar a " + destinatario + " con asunto: " + asuntoFinal);
			e.printStackTrace();
		}
	}

	public void enviarCorreoEstandarConCopia(String destinatario, String copia, String asunto, String cuerpo) {
		if (!tieneTexto(destinatario)) {
			System.err.println("[envioMail] Correo estandar con copia no enviado: destinatario vacio.");
			return;
		}

		String asuntoFinal = tieneTexto(asunto) ? asunto : "Notificacion " + EMPRESA_NOMBRE;

		try {
			Session session = crearSesion();
			MimeMessage message = crearMensaje(session, destinatario, asuntoFinal, false);
			agregarSiTieneTexto(message, Message.RecipientType.CC, copia);
			message.setContent(construirContenidoCorreo(asuntoFinal, cuerpo, false, null, null, 0));
			Transport.send(message, message.getAllRecipients());
			System.out.println("[envioMail] Correo enviado a " + destinatario + " con copia a " + copia + " y asunto: " + asuntoFinal);
		} catch (Exception e) {
			System.err.println("[envioMail] Error al enviar correo estandar con copia a " + destinatario + " con asunto: " + asuntoFinal);
			e.printStackTrace();
		}
	}

	public String obtenerDestinatarioNotificacionInterna() {
		if (tieneTexto(mailConfig.getNotificationTo())) {
			return mailConfig.getNotificationTo().trim();
		}
		if (tieneTexto(mailConfig.getBccPrimary())) {
			return mailConfig.getBccPrimary().trim();
		}
		return tieneTexto(mailConfig.getAccount()) ? mailConfig.getAccount().trim() : null;
	}

	public String obtenerDestinatarioAjusteInventario() {
		if (tieneTexto(mailConfig.getInventoryAdjustmentTo())) {
			return mailConfig.getInventoryAdjustmentTo().trim();
		}
		return obtenerDestinatarioNotificacionInterna();
	}

	public String obtenerCopiaAjusteInventario() {
		return tieneTexto(mailConfig.getInventoryAdjustmentCc()) ? mailConfig.getInventoryAdjustmentCc().trim() : null;
	}

	private Session crearSesion() {
		validarConfiguracion();
		Properties properties = new Properties();
		properties.put("mail.smtp.host", mailConfig.getHost());
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth", String.valueOf(mailConfig.isAuth()));
		properties.put("mail.smtp.user", mailConfig.getAccount());
		properties.put("mail.smtp.starttls.enable", String.valueOf(mailConfig.isStarttlsEnable()));
		properties.put("mail.smtp.port", String.valueOf(mailConfig.getPort()));
		if (mailConfig.getConnectionTimeout() != null) {
			properties.put("mail.smtp.connectiontimeout", String.valueOf(mailConfig.getConnectionTimeout()));
		}
		if (mailConfig.getTimeout() != null) {
			properties.put("mail.smtp.timeout", String.valueOf(mailConfig.getTimeout()));
			properties.put("mail.smtp.writetimeout", String.valueOf(mailConfig.getTimeout()));
		}

		return Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailConfig.getAccount(), mailConfig.getPassword());
			}
		});
	}

	private MimeMessage crearMensaje(Session session, String destinatario, String asunto, boolean incluirCopiaOculta)
			throws Exception {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(mailConfig.getAccount(), EMPRESA_NOMBRE, StandardCharsets.UTF_8.name()));
		if (tieneTexto(mailConfig.getReplyTo())) {
			message.setReplyTo(new InternetAddress[] {
					new InternetAddress(mailConfig.getReplyTo(), EMPRESA_NOMBRE, StandardCharsets.UTF_8.name()) });
		}
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
		if (incluirCopiaOculta) {
			agregarSiTieneTexto(message, Message.RecipientType.BCC, mailConfig.getBccPrimary());
			agregarSiTieneTexto(message, Message.RecipientType.BCC, mailConfig.getBccSecondary());
		}
		message.setSubject(asunto, StandardCharsets.UTF_8.name());
		message.setSentDate(new Date());
		return message;
	}

	private Multipart construirContenidoCorreo(String asunto, String cuerpo, boolean incluirAdjuntos, String ruta,
			String nombreArchivo, int tipo) throws Exception {
		MimeMultipart multipart = new MimeMultipart("mixed");

		MimeBodyPart contenidoPrincipal = new MimeBodyPart();
		MimeMultipart contenidoAlternativo = new MimeMultipart("alternative");

		MimeBodyPart textoPlano = new MimeBodyPart();
		textoPlano.setText(construirTextoPlano(asunto, cuerpo, incluirAdjuntos), StandardCharsets.UTF_8.name());
		contenidoAlternativo.addBodyPart(textoPlano);

		MimeBodyPart contenidoHtml = new MimeBodyPart();
		contenidoHtml.setContent(construirPlantillaHtml(asunto, cuerpo, incluirAdjuntos), "text/html; charset=UTF-8");
		contenidoAlternativo.addBodyPart(contenidoHtml);

		contenidoPrincipal.setContent(contenidoAlternativo);
		multipart.addBodyPart(contenidoPrincipal);

		if (incluirAdjuntos) {
			agregarAdjuntos(multipart, ruta, nombreArchivo, tipo);
		}

		return multipart;
	}

	private void agregarAdjuntos(Multipart multipart, String ruta, String nombreArchivo, int tipo)
			throws MessagingException {
		if (tipo == 2) {
			agregarAdjunto(multipart, new File(ruta, "pdf" + File.separator + nombreArchivo + ".pdf"),
					nombreArchivo + ".pdf");
			agregarAdjunto(multipart, new File(ruta, "xml" + File.separator + nombreArchivo + ".xml"),
					nombreArchivo + ".xml");
			return;
		}

		agregarAdjunto(multipart, new File(ruta, nombreArchivo + ".pdf"), nombreArchivo + ".pdf");
	}

	private void agregarAdjunto(Multipart multipart, File archivo, String nombreVisible) throws MessagingException {
		if (!archivo.exists()) {
			throw new MessagingException("No se encontro el archivo adjunto requerido: " + archivo.getAbsolutePath());
		}

		MimeBodyPart adjunto = new MimeBodyPart();
		adjunto.setDataHandler(new DataHandler(new FileDataSource(archivo)));
		adjunto.setFileName(nombreVisible);
		multipart.addBodyPart(adjunto);
	}

	private String construirPlantillaHtml(String asunto, String cuerpo, boolean incluirAdjuntos) {
		String contenido = construirContenidoHtml(cuerpo);
		String notaAdjuntos = incluirAdjuntos
				? "<p style='margin:0 0 16px 0;color:#475467;font-size:14px;line-height:21px;'>Este mensaje incluye los archivos relacionados en formato adjunto.</p>"
				: "";

		return "<!DOCTYPE html>"
				+ "<html><body style='margin:0;padding:0;background-color:#eef2f7;font-family:Segoe UI,Arial,sans-serif;color:#1f2937;'>"
				+ "<table role='presentation' width='100%' cellspacing='0' cellpadding='0' style='background-color:#eef2f7;padding:24px 12px;'>"
				+ "<tr><td align='center'>"
				+ "<table role='presentation' width='100%' cellspacing='0' cellpadding='0' style='max-width:680px;background-color:#ffffff;border-radius:20px;overflow:hidden;border:1px solid #d0d9e5;'>"
				+ "<tr><td style='padding:0;background:linear-gradient(135deg,#0f4c81 0%,#1a6aa6 55%,#2ea3b0 100%);'>"
				+ "<table role='presentation' width='100%' cellspacing='0' cellpadding='0'><tr>"
				+ "<td style='padding:28px 32px;'>"
				+ "<div style='display:inline-block;padding:6px 12px;border-radius:999px;background-color:rgba(255,255,255,0.18);color:#ffffff;font-size:12px;letter-spacing:0.08em;text-transform:uppercase;'>"
				+ MENSAJE_AUTOMATICO + "</div>"
				+ "<h1 style='margin:16px 0 6px 0;font-size:28px;line-height:34px;color:#ffffff;'>" + escaparHtml(asunto)
				+ "</h1>"
				+ "<p style='margin:0;color:#dbeafe;font-size:14px;line-height:21px;'>" + EMPRESA_NOMBRE
				+ "</p></td></tr></table></td></tr>"
				+ "<tr><td style='padding:32px;'>"
				+ "<p style='margin:0 0 16px 0;color:#344054;font-size:15px;line-height:24px;'>Estimado cliente:</p>"
				+ notaAdjuntos
				+ "<div style='padding:24px;border-radius:16px;background-color:#f8fafc;border:1px solid #e2e8f0;'>"
				+ contenido + "</div>"
				+ "<div style='padding-top:24px;margin-top:24px;border-top:1px solid #e5e7eb;'>"
				+ "<p style='margin:0 0 10px 0;color:#344054;font-size:14px;line-height:22px;'>Atentamente,<br/><strong>" + EMPRESA_NOMBRE + "</strong></p>"
				+ "<p style='margin:0;color:#667085;font-size:12px;line-height:20px;'>Este es un correo generado automaticamente por nuestro sistema. Puede responder directamente a este mensaje si requiere seguimiento comercial o aclaraciones.</p>"
				+ "</div></td></tr>"
				+ "<tr><td style='padding:0 32px 28px 32px;'>"
				+ "<p style='margin:0;color:#98a2b3;font-size:11px;line-height:18px;'>El contenido de este correo es confidencial y esta dirigido exclusivamente a su destinatario.</p>"
				+ "</td></tr></table></td></tr></table></body></html>";
	}

	private String construirContenidoHtml(String cuerpo) {
		if (!tieneTexto(cuerpo)) {
			return "<p style='margin:0;color:#344054;font-size:15px;line-height:24px;'>Le compartimos informacion relevante de su operacion.</p>";
		}

		if (pareceHtml(cuerpo)) {
			return "<div style='color:#344054;font-size:15px;line-height:24px;'>" + cuerpo + "</div>";
		}

		String textoEscapado = escaparHtml(cuerpo.trim());
		if (requiereFormatoPre(cuerpo)) {
			return "<pre style='margin:0;font-family:Consolas,Monaco,monospace;font-size:13px;line-height:20px;color:#344054;white-space:pre-wrap;'>"
					+ textoEscapado + "</pre>";
		}

		String[] parrafos = cuerpo.trim().split("(\\r?\\n){2,}");
		StringBuilder contenido = new StringBuilder();
		for (String parrafo : parrafos) {
			String linea = escaparHtml(parrafo.trim()).replace("\r\n", "\n").replace("\n", "<br/>");
			if (!linea.isEmpty()) {
				contenido.append("<p style='margin:0 0 14px 0;color:#344054;font-size:15px;line-height:24px;'>")
						.append(linea).append("</p>");
			}
		}

		return contenido.length() > 0 ? contenido.toString()
				: "<p style='margin:0;color:#344054;font-size:15px;line-height:24px;'>" + textoEscapado + "</p>";
	}

	private String construirTextoPlano(String asunto, String cuerpo, boolean incluirAdjuntos) {
		StringBuilder texto = new StringBuilder();
		texto.append(EMPRESA_NOMBRE).append("\n");
		texto.append(asunto).append("\n\n");
		texto.append("Estimado cliente:\n\n");
		if (incluirAdjuntos) {
			texto.append("Este correo incluye archivos adjuntos relacionados con su operacion.\n\n");
		}
		texto.append(convertirHtmlATexto(cuerpo)).append("\n\n");
		texto.append("Atentamente,\n").append(EMPRESA_NOMBRE).append("\n\n");
		texto.append("Este correo fue generado automaticamente por nuestro sistema. Puede responder directamente a este mensaje si requiere seguimiento.");
		return texto.toString();
	}

	private String convertirHtmlATexto(String cuerpo) {
		if (!tieneTexto(cuerpo)) {
			return "Le compartimos informacion relevante de su operacion.";
		}

		String texto = cuerpo.replaceAll("(?i)<br\\s*/?>", "\n");
		texto = texto.replaceAll("(?i)</p>", "\n\n");
		texto = texto.replaceAll("(?i)<[^>]+>", "");
		texto = texto.replace("&nbsp;", " ").replace("&oacute;", "o").replace("&aacute;", "a")
				.replace("&eacute;", "e").replace("&iacute;", "i").replace("&uacute;", "u")
				.replace("&ntilde;", "n").replace("&amp;", "&");
		return texto.trim();
	}

	private boolean requiereFormatoPre(String cuerpo) {
		return cuerpo.contains("------------------------------------------------------------------------------------------------------")
				|| cuerpo.matches("(?s).*\\S\\s{2,}\\S.*");
	}

	private boolean pareceHtml(String texto) {
		return texto != null && texto.matches("(?s).*</?[a-zA-Z][^>]*>.*");
	}

	private boolean tieneTexto(String texto) {
		return texto != null && !texto.trim().isEmpty();
	}

	private String escaparHtml(String texto) {
		if (texto == null) {
			return "";
		}

		return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
				.replace("\"", "&quot;").replace("'", "&#39;");
	}

	private void agregarSiTieneTexto(MimeMessage message, Message.RecipientType tipo, String direccion)
			throws MessagingException {
		if (tieneTexto(direccion)) {
			message.addRecipients(tipo, InternetAddress.parse(direccion));
		}
	}

	private void validarConfiguracion() {
		if (!tieneTexto(mailConfig.getHost()) || !tieneTexto(mailConfig.getAccount())
				|| !tieneTexto(mailConfig.getPassword()) || mailConfig.getPort() == null) {
			throw new IllegalStateException(
					"Configuracion SMTP incompleta. Revise mail.smtp.host, mail.smtp.port, mail.smtp.account y mail.smtp.password.");
		}
	}

}
