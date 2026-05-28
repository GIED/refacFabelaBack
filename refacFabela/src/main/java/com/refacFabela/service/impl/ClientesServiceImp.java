package com.refacFabela.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TcCliente;
import com.refacFabela.model.TvSaldoGeneralCliente;
import com.refacFabela.repository.ClienteSaldoRepository;
import com.refacFabela.repository.ClientesRepository;
import com.refacFabela.service.ClienteService;
import com.refacFabela.utils.DateTimeUtil;

@Service
public class ClientesServiceImp implements ClienteService {

	@Autowired
	private ClientesRepository clientesRepository;

	@Autowired
	private ClienteSaldoRepository clienteSaldoRepository;

	@Override
	public List<TcCliente> obtenerCliente() {
		return clientesRepository.findBynEstatus(1);
	}

	@Override
	public TcCliente guardarCliente(TcCliente tcCliente) {
		System.err.println("esto es lo que voy a guardar" + tcCliente);

		TcCliente clienteActual = obtenerClienteActual(tcCliente.getnId());
		normalizarCorreo(tcCliente);
		preservarEstadoCorreo(clienteActual, tcCliente);

		if (tcCliente.getN_limiteCredito() != null && tcCliente.getN_limiteCredito().compareTo(BigDecimal.ZERO) > 0
				&& tcCliente.getD_fechaCredito() == null) {
			tcCliente.setD_fechaCredito(DateTimeUtil.obtenerHoraExactaDeMexico());
		}

		return clientesRepository.save(tcCliente);
	}

	private TcCliente obtenerClienteActual(Long clienteId) {
		if (clienteId == null) {
			return null;
		}

		return clientesRepository.findById(clienteId).orElse(null);
	}

	private void preservarEstadoCorreo(TcCliente clienteActual, TcCliente tcCliente) {
		if (clienteActual == null) {
			inicializarEstadoCorreo(tcCliente);
			return;
		}

		String correoActual = normalizarTexto(clienteActual.getsCorreo());
		String correoNuevo = normalizarTexto(tcCliente.getsCorreo());

		if (!Objects.equals(correoActual, correoNuevo)) {
			limpiarEstadoCorreo(tcCliente);
			return;
		}

		if (tcCliente.getnCorreoBloqueado() == null) {
			tcCliente.setnCorreoBloqueado(Boolean.TRUE.equals(clienteActual.getnCorreoBloqueado()));
		}

		if (Boolean.TRUE.equals(tcCliente.getnCorreoBloqueado())) {
			if (tcCliente.getsMotivoBloqueoCorreo() == null) {
				tcCliente.setsMotivoBloqueoCorreo(clienteActual.getsMotivoBloqueoCorreo());
			}
			if (tcCliente.getdFechaBloqueoCorreo() == null) {
				tcCliente.setdFechaBloqueoCorreo(clienteActual.getdFechaBloqueoCorreo());
			}
			return;
		}

		limpiarEstadoCorreo(tcCliente);
	}

	private void inicializarEstadoCorreo(TcCliente tcCliente) {
		if (tcCliente.getnCorreoBloqueado() == null) {
			tcCliente.setnCorreoBloqueado(false);
		}

		if (!Boolean.TRUE.equals(tcCliente.getnCorreoBloqueado())) {
			limpiarEstadoCorreo(tcCliente);
		}
	}

	private void normalizarCorreo(TcCliente tcCliente) {
		tcCliente.setsCorreo(normalizarTexto(tcCliente.getsCorreo()));
	}

	private void limpiarEstadoCorreo(TcCliente tcCliente) {
		tcCliente.setnCorreoBloqueado(false);
		tcCliente.setsMotivoBloqueoCorreo(null);
		tcCliente.setdFechaBloqueoCorreo(null);
	}

	private String normalizarTexto(String valor) {
		if (valor == null) {
			return null;
		}

		String valorNormalizado = valor.trim();
		return valorNormalizado.isEmpty() ? null : valorNormalizado;
	}

	@Override
	public TcCliente consultaClienteId(Long id) {
		return clientesRepository.findById(id).get();
	}

	@Override
	public TcCliente consultaClienteIdUsuario(Long id) {
		return clientesRepository.findBynIdUsuario(id);
	}

	@Override
	public TcCliente consultaClienteRfc(String rfc) {
		return clientesRepository.findBysRfc(rfc);
	}

	@Override
	public String eliminarClienteId(Long id) {
		TcCliente cliente = clientesRepository.findBynIdUsuario(id);
		cliente.setnEstatus(0);

		clientesRepository.save(cliente);
		return "Cliente Eliminado";
	}

	@Override
	public List<TcCliente> consultaClienteLike(String clienteBuscar) {
		return clientesRepository.buscarClineteLike(clienteBuscar);
	}

	@Override
	public TvSaldoGeneralCliente consultaClienteIdSaldo(Long id) {
		return clienteSaldoRepository.findBynIdCliente(id);
	}

	@Override
	public List<TvSaldoGeneralCliente> consultaClienteSaldo() {
		return clienteSaldoRepository.obtenerClienteSaldo();
	}
}