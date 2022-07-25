package com.refacFabela.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.refacFabela.dto.BalanceCajaDto;
import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TvReporteDetalleVenta;
import com.refacFabela.model.TwCaja;
import com.refacFabela.model.TwVenta;
import com.refacFabela.repository.CajaRepository;
import com.refacFabela.repository.TrVentaCobroRepository;
import com.refacFabela.repository.TvReporteDetalleVentaRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.service.CajaService;
@Service
public class CajaServiceImpl implements CajaService {
	
	@Autowired
	public CajaRepository cajaRepository;
	@Autowired
	public VentasRepository ventasRepository;
	@Autowired
	public TrVentaCobroRepository trVentaCobroRepository;
	@Autowired 
	public TvReporteDetalleVentaRepository tvReporteDetalleVentaRepository;
	

	@Override
	public TwCaja obtenerCajaActiva() {
		// TODO Auto-generated method stub
		return cajaRepository.obtenerCajaVigente();
	}


	@Override
	public BalanceCajaDto obtenerBalanceCaja(Long nIdCaja) {	
		
		List<TrVentaCobro> trVentasCobro=trVentaCobroRepository.obtenerPagosCaja(nIdCaja);
		
		List<TvReporteDetalleVenta> trReporteDetalleVentas=tvReporteDetalleVentaRepository.obtenerVentasCajaReporte(nIdCaja);
		
		System.err.println(trReporteDetalleVentas);
		
		
		
		return null;
	}

	

	

}
