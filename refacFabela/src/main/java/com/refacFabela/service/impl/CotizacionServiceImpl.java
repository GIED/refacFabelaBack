package com.refacFabela.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.dto.CotizacionDto;
import com.refacFabela.model.TwCotizacione;
import com.refacFabela.model.TwCotizacionesProducto;
import com.refacFabela.repository.CotizacionProductoRepository;
import com.refacFabela.repository.CotizacionRepository;
import com.refacFabela.service.CotizacionService;
import com.refacFabela.utils.utils;
@Service
public class CotizacionServiceImpl implements CotizacionService {
	
	@Autowired
	private CotizacionRepository cotizacionRepository;
	
	@Autowired
	private CotizacionProductoRepository cotizacionProductoRepository;
	

	
	@Override
	public void guardaCorizacion(List<CotizacionDto> listaCotizacion) {
		
		//System.out.println(listaCotizacion);
		TwCotizacione twCotizacion = new TwCotizacione();
		
		twCotizacion.setnIdCliente(listaCotizacion.get(0).getnIdCliente());
		twCotizacion.setnIdUsuario(1L);
		twCotizacion.setsFolioCotizacion(listaCotizacion.get(0).getsFolio());
		twCotizacion.setdFecha(utils.fechaSistema);
		twCotizacion.setnEstatus(1);
		
		TwCotizacione cotizacionRegistrada = cotizacionRepository.save(twCotizacion);
		
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
			
			
		

	}

}
