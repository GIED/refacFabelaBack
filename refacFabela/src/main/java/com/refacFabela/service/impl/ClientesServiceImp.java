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
		TcCliente clienteParaGuardar = construirClienteParaGuardar(clienteActual, tcCliente);
		normalizarCorreo(clienteParaGuardar);
		preservarEstadoCorreo(clienteActual, clienteParaGuardar);

		if (clienteParaGuardar.getN_limiteCredito() != null
				&& clienteParaGuardar.getN_limiteCredito().compareTo(BigDecimal.ZERO) > 0
				&& clienteParaGuardar.getD_fechaCredito() == null) {
			clienteParaGuardar.setD_fechaCredito(DateTimeUtil.obtenerHoraExactaDeMexico());
		}

		return clientesRepository.save(clienteParaGuardar);
	}

	private TcCliente construirClienteParaGuardar(TcCliente clienteActual, TcCliente tcCliente) {
		if (clienteActual == null) {
			return tcCliente;
		}

		if (tcCliente.getsCorreo() != null) {
			clienteActual.setsCorreo(tcCliente.getsCorreo());
		}

		if (tcCliente.getsDireccion() != null) {
			clienteActual.setsDireccion(tcCliente.getsDireccion());
		}

		if (tcCliente.getsRazonSocial() != null) {
			clienteActual.setsRazonSocial(tcCliente.getsRazonSocial());
		}

		if (tcCliente.getsRfc() != null) {
			clienteActual.setsRfc(tcCliente.getsRfc());
		}

		if (tcCliente.getsTelefono() != null) {
			clienteActual.setsTelefono(tcCliente.getsTelefono());
		}

		if (tcCliente.getsClave() != null) {
			clienteActual.setsClave(tcCliente.getsClave());
		}

		if (tcCliente.getN_idUsuarioCredito() == null) {
			tcCliente.setN_idUsuarioCredito(clienteActual.getN_idUsuarioCredito());
		}
		clienteActual.setN_idUsuarioCredito(tcCliente.getN_idUsuarioCredito());

		if (tcCliente.getN_limiteCredito() == null) {
			tcCliente.setN_limiteCredito(clienteActual.getN_limiteCredito());
		}
		clienteActual.setN_limiteCredito(tcCliente.getN_limiteCredito());

		if (tcCliente.getD_fechaCredito() == null) {
			tcCliente.setD_fechaCredito(clienteActual.getD_fechaCredito());
		}
		clienteActual.setD_fechaCredito(tcCliente.getD_fechaCredito());

		if (tcCliente.getnIdUsuario() == null) {
			tcCliente.setnIdUsuario(clienteActual.getnIdUsuario());
		}
		clienteActual.setnIdUsuario(tcCliente.getnIdUsuario());

		if (tcCliente.getnCp() == null) {
			tcCliente.setnCp(clienteActual.getnCp());
		}
		clienteActual.setnCp(tcCliente.getnCp());

		if (tcCliente.getnIdRegimenFiscal() == null) {
			tcCliente.setnIdRegimenFiscal(clienteActual.getnIdRegimenFiscal());
		}
		clienteActual.setnIdRegimenFiscal(tcCliente.getnIdRegimenFiscal());

		if (tcCliente.getnDescuento() == null) {
			tcCliente.setnDescuento(clienteActual.getnDescuento());
		}
		clienteActual.setnDescuento(tcCliente.getnDescuento());

		if (tcCliente.getnDatosValidados() == null) {
			tcCliente.setnDatosValidados(clienteActual.getnDatosValidados());
		}
		clienteActual.setnDatosValidados(tcCliente.getnDatosValidados());

		if (tcCliente.getnIdDatoFactura() == null) {
			tcCliente.setnIdDatoFactura(clienteActual.getnIdDatoFactura());
		}
		clienteActual.setnIdDatoFactura(tcCliente.getnIdDatoFactura());

		clienteActual.setnEstatus(tcCliente.getnEstatus());

		return clienteActual;
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