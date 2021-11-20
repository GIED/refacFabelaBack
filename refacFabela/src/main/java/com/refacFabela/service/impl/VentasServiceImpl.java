package com.refacFabela.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TvVentaDetalle;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwVenta;
import com.refacFabela.repository.AbonoVentaIdRepository;
import com.refacFabela.repository.TvVentaDetalleRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.service.VentasService;

@Service
public class VentasServiceImpl implements VentasService {
	@Autowired
	VentasRepository ventasRepository;
	@Autowired
	TvVentaDetalleRepository tvVentaDetalleRepository;
	@Autowired
	AbonoVentaIdRepository abonoVentaIdRepository;
 
	@Override
	public List<TwVenta> consltaVentas() {
		
		return ventasRepository.findAll();
	}

	@Override
	public List<TvVentaDetalle> consultaVentaDetalle(Long nIdCliente, Long nTipoPago){
		
		return tvVentaDetalleRepository.consultaVentaDetalle(nIdCliente, nTipoPago);
	}

	@Override
	public List<TwAbono> consultaAbonoVentaId(Long nId) {
		//
		return abonoVentaIdRepository.findBynIdVenta(nId);
	}

}
