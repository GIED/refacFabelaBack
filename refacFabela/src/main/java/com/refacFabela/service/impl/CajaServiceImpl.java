package com.refacFabela.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.refacFabela.dto.BalanceCajaDto;
import com.refacFabela.model.TrVentaCobro;
import com.refacFabela.model.TvReporteDetalleVenta;
import com.refacFabela.model.TwCaja;
import com.refacFabela.model.TwGasto;
import com.refacFabela.repository.AbonoVentaIdRepository;
import com.refacFabela.repository.CajaRepository;
import com.refacFabela.repository.TrVentaCobroRepository;
import com.refacFabela.repository.TvReporteDetalleVentaRepository;
import com.refacFabela.repository.TwGastoRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.service.CajaService;
import com.refacFabela.utils.DateTimeUtil;
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
	@Autowired 
	public TwGastoRepository twGastoRepository;
	
	

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
	public TwCaja nuevaCaja(BigDecimal saldoInicial, Long nIdUsuario) {
		
		TwCaja twCaja =new TwCaja();
		TwCaja twCajaActiva =new TwCaja();
		
		
		
		/*OBTETER LA CAJA VIGENTE */		
		twCajaActiva=cajaRepository.obtenerCajaVigente();
		
		/* OBTEBER VALORES DE CIERRE DE CAJA */
		
		
		twCajaActiva.setdFechaCierre(DateTimeUtil.obtenerHoraExactaDeMexico());	
		twCajaActiva.setnPagoEfectivo( trVentaCobroRepository.TotalPagoEfectivoVenta(twCajaActiva.getnId()).add(abonoVentaIdRepository.TotalAbonoEfectivo(twCajaActiva.getnId())));

			twCajaActiva.setnPagoElectronico( trVentaCobroRepository.TotalPagoElectronico(twCajaActiva.getnId()) .add(abonoVentaIdRepository.TotalAbonoElectronico(twCajaActiva.getnId()))
			);

			twCajaActiva.setnSaldoCierre(
			    twCajaActiva.getnPagoEfectivo()
			        .add(twCajaActiva.getnPagoElectronico())
			);

			twCajaActiva.setnSaldoFinal(
			    twCajaActiva.getnSaldoCierre()
			        .add(twCajaActiva.getnSaldoInicial())
			);		
		twCajaActiva.setnEstatus(0);
		
		
		/*Guarda y cierra la caja Activa*/
		cajaRepository.save(twCajaActiva);
				
		
		
		/* PERTURA DE LA NUEVA CAJA */

		
		twCaja.setdFechaApertura(DateTimeUtil.obtenerHoraExactaDeMexico());
		twCaja.setnSaldoInicial( BigDecimal.ZERO);
		twCaja.setnSaldoCierre( BigDecimal.ZERO);
		twCaja.setnSaldoFinal( BigDecimal.ZERO);
		twCaja.setnPagoEfectivo( BigDecimal.ZERO);
		twCaja.setnPagoElectronico( BigDecimal.ZERO);
		twCaja.setnEstatus(1);
		twCaja.setnIdUsuario(nIdUsuario);
		twCaja.setnSaldoInicial(saldoInicial);
		
		/*GUARDA Y APERTURA LA  NUEVA CAJA*/
		cajaRepository.save(twCaja);
		
		
		return twCaja;
	}


	@Override
	public List<TwGasto> obteberGastosCaja(Long nIdCaja) {		
		return twGastoRepository.obtenerGastosCaja(nIdCaja);
	}


	@Override
	public TwGasto guardarGasto(TwGasto twGasto) {
		return twGastoRepository.save(twGasto);
	}
	
	@Override
	public TwGasto borrarGasto(TwGasto twGasto) {
		twGastoRepository.delete(twGasto);
		return null;
	}


	@Override
	public TwCaja consultaCaja(Long nidCaja) {
		
		TwCaja twCaja=new TwCaja();
		twCaja=cajaRepository.getById(nidCaja);
		
		return twCaja   ;
	}

	

	

}
