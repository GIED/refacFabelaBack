package com.refacFabela.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.dto.PedidoDto;
import com.refacFabela.model.TvPedidoDetalle;
import com.refacFabela.model.TwHistoriaIngresoProducto;
import com.refacFabela.model.TwPedido;
import com.refacFabela.model.TwPedidoProducto;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.model.TwVenta;
import com.refacFabela.repository.PedidosProductoRepository;
import com.refacFabela.repository.ProductoBodegaRepository;
import com.refacFabela.repository.TvPedidoDetalleRepository;
import com.refacFabela.repository.TwHistoriaIngresoProductoRepository;
import com.refacFabela.repository.TwPedidoRepository;
import com.refacFabela.repository.TwProductosVentaRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.service.PedidosService;
import com.refacFabela.utils.envioMail;
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
	private TwPedidoRepository twPedidoRepository;
	@Autowired
	private ProductoBodegaRepository productoBodegaRepository;
	@Autowired
	private VentasRepository ventasRepository;
	@Autowired
	private TwHistoriaIngresoProductoRepository twHistoriaIngresoProductoRepository;
  
	
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

		TwPedido twPedido = new TwPedido();
		TwPedidoProducto twPedidoProducto = new TwPedidoProducto();

		TwPedido res = null;
		utils util = new utils();

		twPedido.setdFechaPedido(util.fechaSistema);
		twPedido.setdFechaPedidoCierre(null);
		twPedido.setnIdUsuario(pedidoDto.getnIdUsuario());
		twPedido.setnEstatus(pedidoDto.getnEstatus());
		twPedido.setsCvePedido(pedidoDto.getsCvePedido());
		twPedido.setsObservaciones(pedidoDto.getsObservaciones());

		res = twPedidoRepository.save(twPedido);

		for (int i = 0; i < pedidoDto.getTwPedidoProducto().size(); i++) {

			twPedidoProducto = null;

			twPedidoProducto = pedidoDto.getTwPedidoProducto().get(i);

			twPedidoProducto.setdFechaPedido(util.fechaSistema);
			twPedidoProducto.setnIdPedido(res.getnId());
			twPedidoProducto.setsClavePedido(pedidoDto.getsCvePedido());

			pedidosProductoRepository.save(twPedidoProducto);

		}

		pedidoDto.setnId(res.getnId());

		System.err.println(res);

		return pedidoDto;
	}

	@Override
	public TwPedidoProducto ingresoProducto(TwPedidoProducto twPedidoProducto) {
		// DECRARACIÓN DE VARIABLES
		utils util = new utils();
		TwPedidoProducto pedidioProducto = new TwPedidoProducto();
		TwPedidoProducto pedidioProductoIngreso = null;
		TwPedido twPedido = new TwPedido();
		TwVenta twVenta = new TwVenta();
		int totalProductosIngreso = 0;
		List<TwPedidoProducto> listaTwPedidoProducto = new ArrayList<TwPedidoProducto>();
		TwProductobodega productoBodega = new TwProductobodega();
	    pedidioProductoIngreso=pedidosProductoRepository.getById(twPedidoProducto.getnId());
		// GUARDA EL ESTATUS DEL ASPIRANTE
	
		
	    if (twPedidoProducto.getTwPedido().getTwVenta() == null) {
			
			productoBodega = productoBodegaRepository.obtenerProductoBodega(twPedidoProducto.getnIdProducto(), "LOCAL");
			System.err.println(pedidioProductoIngreso.getnCantidaRecibida());
			System.err.println(twPedidoProducto.getnCantidaRecibida());
			
		
			
			if(twPedidoProducto.getnCantidaRecibida()==0) {
				System.err.println("entre");
	
			productoBodega.setnCantidad(productoBodega.getnCantidad() + twPedidoProducto.getnCantidaRecibida());
			}
			else {
				
				System.err.println(pedidioProductoIngreso.getnCantidaRecibida());
				System.err.println(twPedidoProducto.getnCantidaRecibida());
				System.err.println(productoBodega.getnCantidad() + pedidioProductoIngreso.getnCantidaRecibida() - twPedidoProducto.getnCantidaRecibida());
				productoBodega.setnCantidad(productoBodega.getnCantidad() +  twPedidoProducto.getnCantidaRecibida() - pedidioProductoIngreso.getnCantidaRecibida() );
				System.err.println(productoBodega);
							
			}
			productoBodegaRepository.save(productoBodega);
		}
	    

		twPedidoProducto.setdFechaRecibida(util.fechaSistema);
		pedidioProducto = pedidosProductoRepository.save(twPedidoProducto);

		
		// CONSULTA EL NUMERO DE PARTIDAS ENTREGADAS
		listaTwPedidoProducto = pedidosProductoRepository.obtenerPedidosRegistrados(twPedidoProducto.getnIdPedido());

		for (int i = 0; i < listaTwPedidoProducto.size(); i++) {

			if (listaTwPedidoProducto.get(i).getnEstatus() == true) {
				totalProductosIngreso = totalProductosIngreso + 1;
			}

		}
        // CONSULTAR PEDIDO
		twPedido = twPedidoRepository.getById(twPedidoProducto.getnIdPedido());

		if (twPedido.getnIdVenta() == null) {
			
			// GUADAR HISTORA DE INGRESO DE PRODUCTOS
			
			TwHistoriaIngresoProducto twHistoriaIngresoProducto = new TwHistoriaIngresoProducto();
			twHistoriaIngresoProducto.setnCantidad(twPedidoProducto.getnCantidaRecibida());
			twHistoriaIngresoProducto.setnIdBodega(1L);
			twHistoriaIngresoProducto.setnIdPedido(twPedidoProducto.getnIdPedido());
			twHistoriaIngresoProducto.setnIdProducto(twPedidoProducto.getnIdProducto());
			twHistoriaIngresoProducto.setnIdUsuario(twPedidoProducto.getnIdUsuario());
			twHistoriaIngresoProducto.setdFechaingreso(util.fechaSistema);
			twHistoriaIngresoProductoRepository.save(twHistoriaIngresoProducto);

		}

		// CAMBIA EL ESTATUS DEL PEDIDO GENERAL SI YA SE RECIBIERON TODOS LOS PRODUCTOS

		if (listaTwPedidoProducto.size() == totalProductosIngreso) {

			twPedido = twPedidoRepository.getById(twPedidoProducto.getnIdPedido());
			twPedido.setnEstatus(1L);
			twPedido.setdFechaPedidoCierre(util.fechaSistema);
			System.err.println("esta es la venta a consultar" + twPedido.getnIdVenta());

			// SI EL PEDIDO ES GENERADO A PARTIR DE UNA VENTA POR PEDIDO SE ENVIA UN CORREO,
			if (twPedido.getnIdVenta() != null) {
				envioMail mail = new envioMail();
				System.err.println("Entre a enviar el correo");
				twVenta = ventasRepository.findBynId(twPedido.getnIdVenta());

				mail.enviarCorreo(twVenta.getTcCliente().getsCorreo(),
						"Pedido número " + twPedidoProducto.getnIdPedido() + " listo para su entrega",
						"Estimado cliente, le informamos que su pedido ya se encuentra disponible para su entrega, favor de acudir a la sucursal para hacerle entrega.",
						"", "", 2);
			}

			twPedidoRepository.save(twPedido);

		}

		return pedidioProducto;
	}

	@Override
	public TwPedidoProducto borrarPedidoProducto(TwPedidoProducto twPedidoProducto) {
		
		pedidosProductoRepository.delete(twPedidoProducto);
		
		return null;
	}

}
