package com.refacFabela.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.dto.CotizacionDto;
import com.refacFabela.dto.TvStockProductoDto;
import com.refacFabela.model.TwCotizaciones;
import com.refacFabela.model.TwCotizacionesDetalle;
import com.refacFabela.model.TwCotizacionesProducto;
import com.refacFabela.repository.CotizacionProductoRepository;
import com.refacFabela.repository.CotizacionRepository;
import com.refacFabela.repository.TwCotizacionesRepository;
import com.refacFabela.service.CotizacionService;
import com.refacFabela.utils.utils;
@Service
public class CotizacionServiceImpl implements CotizacionService {
	
	@Autowired
	private CotizacionRepository cotizacionRepository;
	
	@Autowired
	private CotizacionProductoRepository cotizacionProductoRepository;
	
	@Autowired
	private TwCotizacionesRepository twCotizacionesRepository;
	

	
	@Override
	public TwCotizaciones guardaCorizacion(List<CotizacionDto> listaCotizacion) {
		
		//System.out.println(listaCotizacion);
		TwCotizaciones twCotizacion = new TwCotizaciones();
		
		twCotizacion.setnIdCliente(listaCotizacion.get(0).getnIdCliente());
		twCotizacion.setnIdUsuario(listaCotizacion.get(0).getnIdUsuario());
		twCotizacion.setsFolioCotizacion(listaCotizacion.get(0).getsFolio());
		twCotizacion.setdFecha(utils.fechaSistema);
		twCotizacion.setnEstatus(1);
		
		TwCotizaciones cotizacionRegistrada = new TwCotizaciones();
		cotizacionRegistrada = cotizacionRepository.save(twCotizacion);
		
		List<TwCotizacionesProducto> listaCotizacionRegistro = new ArrayList<TwCotizacionesProducto>();
		
		for (CotizacionDto cotizacionDto : listaCotizacion) {
			
			TwCotizacionesProducto twCotizacionProducto = new TwCotizacionesProducto();
			
			twCotizacionProducto.setnIdCotizacion(cotizacionRegistrada.getnId());
			twCotizacionProducto.setnIdProducto(cotizacionDto.getnIdProducto());
			twCotizacionProducto.setnCantidad(cotizacionDto.getnCantidad());
			twCotizacionProducto.setnPrecioUnitario(cotizacionDto.getnPrecioUnitario());
			twCotizacionProducto.setnIvaUnitario(cotizacionDto.getnIvaUnitario());
			twCotizacionProducto.setnTotalUnitario(cotizacionDto.getnTotalUnitario());
			twCotizacionProducto.setnPrecioPartida(cotizacionDto.getnCantidad()* cotizacionDto.getnPrecioUnitario());
			twCotizacionProducto.setnIvaPartida(twCotizacionProducto.getnPrecioPartida() * .16);
			twCotizacionProducto.setnTotalPartida(twCotizacionProducto.getnPrecioPartida() + twCotizacionProducto.getnIvaPartida());
			
			listaCotizacionRegistro.add(twCotizacionProducto);
			
		}
		
		cotizacionProductoRepository.saveAll(listaCotizacionRegistro);
		
		return cotizacionRegistrada;
			
			
		

	}



	@Override
	public List<TwCotizacionesDetalle> consultaCotizaciones() {
		

		return twCotizacionesRepository.findAll();
		
	}
	
	@Override
	public List<TwCotizacionesDetalle> consultaCotizacionDistribuidor(Long idUsuario) {
		
		return twCotizacionesRepository.findBynIdUsuario(idUsuario);
	}
	
	@Override
	public TwCotizacionesDetalle consultaCotizacionById(Long idCotizacion) {
		
		return this.twCotizacionesRepository.findById(idCotizacion).get();
	}



	@Override
	public List<TvStockProductoDto> consultaCotizacionId(Long id) {
		
		List<TwCotizacionesProducto> listaProductoCotizados = cotizacionProductoRepository.findBynIdCotizacion(id);
		
		List<TvStockProductoDto> listaProductos = new ArrayList<TvStockProductoDto>();
		
		for (TwCotizacionesProducto twCotizacionesProducto : listaProductoCotizados) {
			
			TvStockProductoDto tvStockProducto = new TvStockProductoDto();
			
			tvStockProducto.setnIdProducto(twCotizacionesProducto.getnIdProducto());
			tvStockProducto.setnCantidadTotal(0);
			tvStockProducto.setnCantidad(twCotizacionesProducto.getnCantidad());
			tvStockProducto.setTcProducto(twCotizacionesProducto.getTcProducto());
			tvStockProducto.setnStatus(1);
			
			listaProductos.add(tvStockProducto);
			
			
		}
		
		
		return listaProductos;
	}



	@Override
	public List<TwCotizacionesProducto> consultaCotizacionIdCotizacion(Long id) {
		// TODO Auto-generated method stub
		return cotizacionProductoRepository.findBynIdCotizacion(id);
	}



	



	



	

}
