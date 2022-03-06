package com.refacFabela.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.dto.PedidoDto;
import com.refacFabela.model.TvPedidoDetalle;
import com.refacFabela.model.TwPedido;
import com.refacFabela.model.TwPedidoProducto;
import com.refacFabela.repository.PedidosProductoRepository;
import com.refacFabela.repository.TvPedidoDetalleRepository;
import com.refacFabela.repository.TwPedidoRepository;
import com.refacFabela.repository.TwProductosVentaRepository;
import com.refacFabela.service.PedidosService;
import com.refacFabela.utils.utils;

import antlr.Utils;
import ch.qos.logback.classic.pattern.Util;

@Service
public class PedidosServiceImpl implements PedidosService {

	@Autowired
	private PedidosProductoRepository pedidosProductoRepository;
	@Autowired
	private TvPedidoDetalleRepository tvPedidoDetalleRepository;
	@Autowired
	private TwPedidoRepository  twPedidoRepository;
	
	@Override
	public List<TwPedidoProducto> obtenerPedidosRegistrados(Long nIdPedido) {
		
		return pedidosProductoRepository.obtenerPedidosRegistrados(nIdPedido);
	}

	@Override
	public List<TvPedidoDetalle> obtenerPedidosEstatus(Long nEstatus) {
		
		return tvPedidoDetalleRepository.obtenerPedidosDetalleEstatus(nEstatus);
	}

	@Override
	public PedidoDto guaradarPedido(PedidoDto pedidoDto) {
		
		TwPedido twPedido=new TwPedido();
		TwPedidoProducto twPedidoProducto=new TwPedidoProducto();
		
		TwPedido res=null;
		utils util= new utils();
		
		twPedido.setdFechaPedido(util.fechaSistema);
		twPedido.setdFechaPedidoCierre(null);
		twPedido.setnIdUsuario(pedidoDto.getnIdUsuario());
		twPedido.setnEstatus(pedidoDto.getnEstatus());
		twPedido.setsCvePedido(pedidoDto.getsCvePedido());
		twPedido.setsObservaciones(pedidoDto.getsObservaciones());		
		
		res=twPedidoRepository.save(twPedido);
		
		for (int i = 0; i < pedidoDto.getTwPedidoProducto().size(); i++) {
			
			twPedidoProducto=null;
			
			twPedidoProducto=pedidoDto.getTwPedidoProducto().get(i);
			
			twPedidoProducto.setdFechaPedido(util.fechaSistema);
			twPedidoProducto.setnIdPedido(res.getnId());
			twPedidoProducto.setsClavePedido(pedidoDto.getsCvePedido());
			
			pedidosProductoRepository.save(twPedidoProducto);
			
			
		}
		
		pedidoDto.setnId(res.getnId());
		
		
	
		
		
		
		System.err.println(res);
		
		
		
		
		return pedidoDto;
	}

}
