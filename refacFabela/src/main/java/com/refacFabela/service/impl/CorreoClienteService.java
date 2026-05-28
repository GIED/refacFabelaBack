package com.refacFabela.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TcCliente;
import com.refacFabela.repository.ClientesRepository;
import com.refacFabela.utils.DateTimeUtil;
import com.refacFabela.utils.envioMail;

@Service
public class CorreoClienteService {

	private static final Logger logger = LogManager.getLogger("errorLogger");

	private final ClientesRepository clientesRepository;
	private final envioMail mailSender;

	@Value("${mail.customer-email-block-enabled:true}")
	private boolean bloqueoCorreoClienteHabilitado;

	@Autowired
	public CorreoClienteService(ClientesRepository clientesRepository, envioMail envioMail) {
		this.clientesRepository = clientesRepository;
		this.mailSender = envioMail;
	}

	public envioMail.ResultadoEnvioCorreo enviarCorreoCliente(Long clienteId, String asunto, String mensaje, String ruta,
			String nombreArchivo, int tipo) {
		TcCliente cliente = obtenerCliente(clienteId);
		if (cliente == null) {
			logger.warn("[CorreoClienteService] No se encontro el cliente {} para enviar correo con asunto {}.", clienteId,
					asunto);
			return envioMail.ResultadoEnvioCorreo.omitido("Cliente no encontrado.");
		}

		if (!bloqueoCorreoClienteHabilitado) {
			return enviarSinBloqueo(cliente, asunto, mensaje, ruta, nombreArchivo, tipo);
		}

		if (Boolean.TRUE.equals(cliente.getnCorreoBloqueado())) {
			logger.info("[CorreoClienteService] Envio omitido para cliente {} porque su correo esta bloqueado.",
					cliente.getnId());
			return envioMail.ResultadoEnvioCorreo.omitido("Correo bloqueado para el cliente.");
		}

		envioMail.ResultadoEnvioCorreo resultado = mailSender.enviarCorreoDetallado(cliente.getsCorreo(), asunto,
				mensaje, ruta, nombreArchivo, tipo);

		if (resultado.debeBloquearCorreoCliente()) {
			bloquearCorreoCliente(cliente, resultado.getMotivoBloqueo());
		}

		return resultado;
	}

	private TcCliente obtenerCliente(Long clienteId) {
		if (clienteId == null) {
			return null;
		}

		return clientesRepository.findById(clienteId).orElse(null);
	}

	private envioMail.ResultadoEnvioCorreo enviarSinBloqueo(TcCliente cliente, String asunto, String mensaje,
			String ruta, String nombreArchivo, int tipo) {
		String respuesta = mailSender.enviarCorreo(cliente.getsCorreo(), asunto, mensaje, ruta, nombreArchivo, tipo);
		return "SI".equals(respuesta) ? envioMail.ResultadoEnvioCorreo.enviado("Correo enviado correctamente.")
				: envioMail.ResultadoEnvioCorreo.error("Correo no enviado.");
	}

	private void bloquearCorreoCliente(TcCliente cliente, String motivoBloqueo) {
		cliente.setnCorreoBloqueado(true);
		cliente.setsMotivoBloqueoCorreo(motivoBloqueo);
		cliente.setdFechaBloqueoCorreo(DateTimeUtil.obtenerHoraExactaDeMexico());
		clientesRepository.save(cliente);
		logger.warn("[CorreoClienteService] Se bloqueo el correo del cliente {} por motivo: {}", cliente.getnId(),
				motivoBloqueo);
	}
}