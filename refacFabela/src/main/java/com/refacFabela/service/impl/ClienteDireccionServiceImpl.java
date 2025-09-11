package com.refacFabela.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.refacFabela.dto.DireccionEnvioDto;
import com.refacFabela.model.TwClienteDireccion;
import com.refacFabela.repository.TwClienteDireccionRepository;
import com.refacFabela.service.ClienteDireccionService;

@Service
public class ClienteDireccionServiceImpl implements ClienteDireccionService {
	
	@Autowired
	private TwClienteDireccionRepository clienteDireccionRepository;
	
	private static final Sort DEFAULT_SORT =
	        Sort.by(Sort.Order.desc("bPredeterminada"), Sort.Order.desc("nId"));
	
	@Override
	public List<DireccionEnvioDto> listar(Long clienteId) {
        return clienteDireccionRepository.findBynIdCliente(clienteId, DEFAULT_SORT)
                   .stream()
                   .map(e -> toDto(e)) // evita ambigüedad de método de referencia
                   .collect(Collectors.toList());
    }
	@Override
	public DireccionEnvioDto listarById(Long id) {
		return toDto(clienteDireccionRepository.findBynId(id));
				
	}

	@Override
    @Transactional
    public DireccionEnvioDto crear(DireccionEnvioDto dto) {
        TwClienteDireccion e = toEntity(dto);
        

        // Si es la primera dirección del cliente → predeterminada
        if (clienteDireccionRepository.countBynIdCliente(dto.nIdCliente) == 0) {
            e.setbPredeterminada(Boolean.TRUE);
        }

        // Si viene marcada como predeterminada, limpia previas
        if (Boolean.TRUE.equals(e.getbPredeterminada())) {
        	clienteDireccionRepository.clearPredeterminada(dto.nIdCliente);
        }

        return toDto(clienteDireccionRepository.save(e));
    }

    @Override
    @Transactional
    public DireccionEnvioDto actualizar(DireccionEnvioDto dto) {
        TwClienteDireccion e = clienteDireccionRepository.findOneById(dto.nId)
            .orElseThrow(() -> new IllegalArgumentException("Dirección no encontrada"));

        e.setsReceptor(dto.sReceptor);
        e.setsTel(dto.sTel);
        e.setsCorreo(dto.sCorreo);
        e.setsCalle(dto.sCalle);
        e.setsNumExt(dto.sNumExt);
        e.setsNumInt(dto.sNumInt);
        e.setsColonia(dto.sColonia);
        e.setnCp(dto.nCp);
        e.setsMunicipio(dto.sMunicipio);
        e.setsEstado(dto.sEstado);
        e.setsReferencias(dto.sReferencias);
        if (dto.nEstatus != null) e.setnEstatus(dto.nEstatus);

        // Si solicitan marcarla predeterminada en la edición
        if (Boolean.TRUE.equals(dto.bPredeterminada)) {
        	clienteDireccionRepository.clearPredeterminada(dto.nIdCliente);
            e.setbPredeterminada(Boolean.TRUE);
        } else if (dto.bPredeterminada != null && !dto.bPredeterminada) {
            // si explícitamente mandan false, la desmarcamos
            e.setbPredeterminada(Boolean.FALSE);
        }

        return toDto(clienteDireccionRepository.save(e));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        TwClienteDireccion e = clienteDireccionRepository.findOneById(id)
            .orElseThrow(() -> new IllegalArgumentException("Dirección no encontrada"));
        clienteDireccionRepository.delete(e);
    }

    @Override
    @Transactional
    public void marcarPredeterminada(Long clienteId, Long id) {
        // valida que exista
    	clienteDireccionRepository.findOneById(id)
            .orElseThrow(() -> new IllegalArgumentException("Dirección no encontrada"));
        // limpia y marca
    	clienteDireccionRepository.clearPredeterminada(clienteId);
    	clienteDireccionRepository.setPredeterminada(id);
    }

    /* ----- mapeo simple ----- */
    private DireccionEnvioDto toDto(TwClienteDireccion e) {
        DireccionEnvioDto d = new DireccionEnvioDto();
        d.nId = e.getnId();
        d.nIdCliente = e.getnIdCliente();
        d.sReceptor = e.getsReceptor();
        d.sTel = e.getsTel();
        d.sCorreo = e.getsCorreo();
        d.sCalle = e.getsCalle();
        d.sNumExt = e.getsNumExt();
        d.sNumInt = e.getsNumInt();
        d.sColonia = e.getsColonia();
        d.nCp = e.getnCp();
        d.sMunicipio = e.getsMunicipio();
        d.sEstado = e.getsEstado();
        d.sReferencias = e.getsReferencias();
        d.bPredeterminada = e.getbPredeterminada();
        d.nEstatus = e.getnEstatus();
        return d;
    }

    private TwClienteDireccion toEntity(DireccionEnvioDto d) {
        TwClienteDireccion e = new TwClienteDireccion();
        e.setnIdCliente(d.nIdCliente);
        e.setsReceptor(d.sReceptor);
        e.setsTel(d.sTel);
        e.setsCorreo(d.sCorreo);
        e.setsCalle(d.sCalle);
        e.setsNumExt(d.sNumExt);
        e.setsNumInt(d.sNumInt);
        e.setsColonia(d.sColonia);
        e.setnCp(d.nCp);
        e.setsMunicipio(d.sMunicipio);
        e.setsEstado(d.sEstado);
        e.setsReferencias(d.sReferencias);
        e.setbPredeterminada(Boolean.TRUE.equals(d.bPredeterminada));
        e.setnEstatus(d.nEstatus != null ? d.nEstatus : 1);
        return e;
    }

}
