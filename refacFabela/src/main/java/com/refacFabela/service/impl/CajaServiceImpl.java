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
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwCaja;
import com.refacFabela.model.TwVenta;
import com.refacFabela.repository.AbonoVentaIdRepository;
import com.refacFabela.repository.CajaRepository;
import com.refacFabela.repository.TrVentaCobroRepository;
import com.refacFabela.repository.TvReporteDetalleVentaRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.service.CajaService;
import com.refacFabela.utils.utils;

import ch.qos.logback.classic.pattern.Util;
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
	@Autowired 
	public AbonoVentaIdRepository abonoVentaIdRepository;
	

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


	@Override
	public List<TwCaja> obteneroCajas() {
		
		return cajaRepository.obtenerCajas();
	}


	@Override
	public TwCaja nuevaCaja(Double saldoInicial, Long nIdUsuario) {
		
		TwCaja twCaja =new TwCaja();
		TwCaja twCajaActiva =new TwCaja();
		utils util = new utils();
		
		
		/*OBTETER LA CAJA VIGENTE */		
		twCajaActiva=cajaRepository.obtenerCajaVigente();
		
		/* OBTEBER VALORES DE CIERRE DE CAJA */
		
		twCajaActiva.setdFechaCierre(util.fechaSistema);	
		twCajaActiva.setnPagoEfectivo(trVentaCobroRepository.TotalPagoEfectivoVenta(twCajaActiva.getnId()) + abonoVentaIdRepository.TotalAbonoEfectivo(twCajaActiva.getnId()));		
		twCajaActiva.setnPagoElectronico(trVentaCobroRepository.TotalPagoElectronico(twCajaActiva.getnId()) + abonoVentaIdRepository.TotalAbonoElectronico(twCajaActiva.getnId()));
		twCajaActiva.setnSaldoCierre(twCajaActiva.getnPagoEfectivo() + twCajaActiva.getnPagoElectronico());
		twCajaActiva.setnSaldoFinal(twCajaActiva.getnSaldoCierre() + twCajaActiva.getnSaldoInicial() );		
		twCajaActiva.setnEstatus(0);
		
		
		/*Guarda y cierra la caja Activa*/
		cajaRepository.save(twCajaActiva);
				
		
		
		/* PERTURA DE LA NUEVA CAJA */
		
		twCaja.setdFechaApertura(util.fechaSistema);
		twCaja.setnSaldoInicial(0);
		twCaja.setnSaldoCierre(0);
		twCaja.setnSaldoFinal(0);
		twCaja.setnPagoEfectivo(0);
		twCaja.setnPagoElectronico(0);
		twCaja.setnEstatus(1);
		twCaja.setnIdUsuario(nIdUsuario);
		twCaja.setnSaldoInicial(saldoInicial);
		
		/*GUARDA Y APERTURA LA  NUEVA CAJA*/
		cajaRepository.save(twCaja);
		
		
		return twCaja;
	}

	

	

}
