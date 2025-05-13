package com.refacFabela.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.controller.BalanceAbonoProveedor;
import com.refacFabela.model.TwAbonoFacturaProveedor;
import com.refacFabela.model.TwFacturaProveedorProducto;
import com.refacFabela.model.TwFacturaProveedorProductoIngreso;
import com.refacFabela.model.TwFacturasProveedor;
import com.refacFabela.model.TwPedido;
import com.refacFabela.model.TwPedidoProducto;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.model.VwFacturaProductoBalance;
import com.refacFabela.model.VwFacturaProveedorBalance;
import com.refacFabela.repository.BalanceFacturaProveedorRepository;
import com.refacFabela.repository.PedidosProductoRepository;
import com.refacFabela.repository.TwAbonoFacturaProveedorRepository;
import com.refacFabela.repository.TwFacturaProveedorProductoIngresoRepository;
import com.refacFabela.repository.TwFacturaProveedorProductoRepository;
import com.refacFabela.repository.TwFacturasProveedorRepository;
import com.refacFabela.repository.TwPedidoProductoRepository;
import com.refacFabela.repository.TwPedidoRepository;
import com.refacFabela.repository.TwProductoBodegaRepository;
import com.refacFabela.repository.VwFacturaProductoBalanceRepository;
import com.refacFabela.repository.VwFacturaProveedorBalanceRepository;
import com.refacFabela.service.FacturasProveedorService;
import com.refacFabela.tipoCambio.DataSerie;
import com.refacFabela.tipoCambio.Series;

@Service
public class TwFacturasProveedorServiceImpl implements FacturasProveedorService {

	@Autowired
	private TwFacturasProveedorRepository twFacturasProveedorRepository;
	
	@Autowired
	private VwFacturaProveedorBalanceRepository vwFacturaProveedorBalanceRepository;
	
	@Autowired
	private BalanceFacturaProveedorRepository balanceFacturaProveedorRepository;
	@Autowired
	private TwAbonoFacturaProveedorRepository twAbonoFacturaProveedorRepository;
	@Autowired
	private VwFacturaProductoBalanceRepository vwFacturaProductoBalanceRepository;
	@Autowired
	private TwFacturaProveedorProductoRepository twFacturaProveedorProductoRepository;
	@Autowired
	private TwFacturaProveedorProductoIngresoRepository twFacturaProveedorProductoIngresoRepository;
	@Autowired
	private TwProductoBodegaRepository twProductoBodegaRepository;
	@Autowired
	private PedidosProductoRepository pedidosProductoRepository;
	@Autowired
	private TwPedidoRepository twPedidoRepository;
	
	@Override
	public List<TwFacturasProveedor> obtenetTodas() {
		// TODO Auto-generated method stub
		return twFacturasProveedorRepository.findBynEstatusFacturaProveedor(1L);
	}

	public List<TwFacturasProveedor> obtenetFacturasProveedor(Long nIdProveedor, Long nIdMoneda) {
		// TODO Auto-generated method stub
		return twFacturasProveedorRepository.findBynIdProveedor(nIdProveedor, nIdMoneda);
	}
	@Override
	public List<BalanceAbonoProveedor> obtenetFacturasProveedorBalance(Long nIdProveedor, Long nIdMoneda) {
		// TODO Auto-generated method stub
		return balanceFacturaProveedorRepository.findByBalance(nIdProveedor, nIdMoneda);
	}
	@Override
	public List<BalanceAbonoProveedor> obtenetFacturasProveedorBalanceHistoria(Long nIdProveedor, Long nIdMoneda) {
		// TODO Auto-generated method stub
		return balanceFacturaProveedorRepository.findByBalanceHistoria(nIdProveedor, nIdMoneda);
	}

	@Override
	public List<VwFacturaProveedorBalance> obtenetBalanceProveedores() {		
	  		
		return vwFacturaProveedorBalanceRepository.findAll();
	}

	@Override
	public DataSerie obtenetTipoCambioBM() {
		Series ser=new Series();
		DataSerie exchangeRate;
		try {
			exchangeRate = ser.getFirstDataSerie();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			exchangeRate=null;
		}
		
		return exchangeRate;
	}

	@Override
	public TwFacturasProveedor guardarFacturaProveedor(TwFacturasProveedor factura) {
		// TODO Auto-generated method stub
		return twFacturasProveedorRepository.save(factura);
	}

	@Override
	public TwAbonoFacturaProveedor guardarAbonoFacturaProveedor(TwAbonoFacturaProveedor abono) {
		return twAbonoFacturaProveedorRepository.save(abono);
	}

	@Override
	public List<TwAbonoFacturaProveedor> obtenetAbonosFactura(Long nIdFactura) {
		// TODO Auto-generated method stub
		return twAbonoFacturaProveedorRepository.buscarAbono(nIdFactura);
	}

	@Override
	public BalanceAbonoProveedor obtenerBalanceFactura(Long nIdFactura) {
	
		return balanceFacturaProveedorRepository.findByBalanceFactura(nIdFactura);
	}

	@Override
	public TwFacturasProveedor obtenerFactura(Long nIdFactura) {
		// TODO Auto-generated method stub
		return twFacturasProveedorRepository.findBynId(nIdFactura);
	}

	@Override
	public List<BalanceAbonoProveedor> obtenerFacturasSinPagar() {
		// TODO Auto-generated method stub
		return balanceFacturaProveedorRepository.findByFactutasSinPagar() ;
	}

	@Override
	public List<TwFacturasProveedor> obtenetPendienteIngreso() {
		// TODO Auto-generated method stub
		return twFacturasProveedorRepository.findBynEstatusFacturaIngreso();
	}

	@Override
	public List<VwFacturaProductoBalance> obtenerVwFacturaProductoBalanceEstatus(Integer nEstatusAlmacen) {
		// TODO Auto-generated method stub
		return vwFacturaProductoBalanceRepository.obtenerFacturasEstatusAlmacen(nEstatusAlmacen);
	}

	@Override
	public List<TwFacturaProveedorProducto> getTwFacturaProveedorProductoId(Long nIdFactura) {
		return twFacturaProveedorProductoRepository.getProductosFactura(nIdFactura);
	}

	@Override
	public TwFacturaProveedorProducto saveTwFacturaProveedorProductoId(TwFacturaProveedorProducto twFacturaProveedorProducto) {
		// TODO Auto-generated method stub
		return twFacturaProveedorProductoRepository.save(twFacturaProveedorProducto);
	}

	@Override
	public void borrarProductoFactura(Long nId) {

		twFacturaProveedorProductoRepository.deleteById(nId);
		
	}

	@Override
	public List<TwFacturaProveedorProductoIngreso> getTwFacturaProveedorProductoIngresoId(Long nId) {
		return twFacturaProveedorProductoIngresoRepository.obtenerFacturaProductoIngreso(nId);
	}

	@Override
	public TwFacturaProveedorProductoIngreso saveTwFacturaProveedorProductoIngreso(TwFacturaProveedorProductoIngreso twFacturaProveedorProductoIngreso) {
		return twFacturaProveedorProductoIngresoRepository.save(twFacturaProveedorProductoIngreso);
	}

	@Override
	public List<TwProductobodega> descontarVentasPedido(Long nIdProducto) {
		
		//Declaracion de varibles
		List<TwProductobodega> listaProductoBodega= new ArrayList<TwProductobodega>();
		List<TwPedidoProducto> listaPedidoProducto= new ArrayList<TwPedidoProducto>();		
		Integer totalbodegas=0;
		Integer pendienteVentaPedido=0;
		
		//consulta de pedidos e inventario
		listaProductoBodega=twProductoBodegaRepository.productoBogas(nIdProducto);
		listaPedidoProducto=pedidosProductoRepository.obtenerProductosIdPedido(nIdProducto);
		
		//sumatoria pedidos producto e inventario
		totalbodegas = listaProductoBodega.stream().mapToInt(producto -> producto.getnCantidad()).sum();		
		pendienteVentaPedido = listaPedidoProducto.stream().mapToInt(pedido -> pedido.getnCantidadPedida() - pedido.getnCantidaRecibida()).sum();

		
		// se hacen los descuentos de las bodegas
		surtirPedidos(listaProductoBodega,listaPedidoProducto);
		
		
		
		
		
		
		System.err.println(listaProductoBodega);
		System.err.println(listaPedidoProducto);
		
		System.err.println(totalbodegas);
		System.err.println(pendienteVentaPedido);
		
		
		
		return listaProductoBodega=twProductoBodegaRepository.productoBogas(nIdProducto);
	}
	
	
	
	public void surtirPedidos(List<TwProductobodega> bodegas, List<TwPedidoProducto> pedidos) {
		
		
		
		System.err.println("EL TOTAL DE PEDIDOS A RECORRER ES DE:" + pedidos.size());

		for (TwPedidoProducto pedido : pedidos) {
			
			TwPedido twPedido=new TwPedido();
			Integer pedidosPendientes=0;
			
			System.err.println("ENTRE AL RECORRER EL PEDIDO:"+ pedido.getnId());

			int cuotaRestante = pedido.getnCantidadPedida() - pedido.getnCantidaRecibida();
			System.err.println("ESTE ES LA CUOTA RESTANTE:"+cuotaRestante);

			for (TwProductobodega bodega : bodegas) {
				System.err.println("ENTRE AL RECORRER LA BODEGA:"+ bodega.getTcBodega().getsBodega());
				if (cuotaRestante <= 0) {
					break;
				}

				int inventarioBodega = bodega.getnCantidad();

				if (inventarioBodega >= cuotaRestante) {
					bodega.setnCantidad(inventarioBodega - cuotaRestante);
					pedido.setnCantidaRecibida(pedido.getnCantidadPedida());
					pedido.setnEstatus(1);
					
					pedidosProductoRepository.save(pedido);
					twProductoBodegaRepository.save(bodega);

					System.err.println("esto queda en la bodega:" + bodega.getnCantidad());
					System.err.println("asi queda el pedido:" + pedido);

					cuotaRestante = 0;
				} else {
					bodega.setnCantidad(0);
					pedido.setnCantidaRecibida(pedido.getnCantidaRecibida() + inventarioBodega);
					System.err.println("esto queda en la bodega:" + bodega);
					System.err.println("asi queda el pedido:" + bodega);
					pedidosProductoRepository.save(pedido);
					twProductoBodegaRepository.save(bodega);

					cuotaRestante -= inventarioBodega;
					
				}
				
			}
			
			if (cuotaRestante > 0) {
				System.out.println("No hay suficiente inventario para surtir el pedido: " + pedido.getnId());
			} else {
				System.out.println("Pedido surtido: " + pedido.getnId());
			}
			
			pedidosPendientes=pedidosProductoRepository.obtenerTotalProductosPendinetes(pedido.getnIdPedido());
			
			if(pedidosPendientes==0) {
				twPedido=twPedidoRepository.pedidoId(pedido.getnIdPedido());
				twPedido.setnEstatus(0L);
				 twPedido.setdFechaPedidoCierre(new Date());				 
				 twPedidoRepository.save(twPedido);
				
			}
			

		}

	}
	
	
	
	
	
	

}
