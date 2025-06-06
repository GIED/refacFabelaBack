package com.refacFabela.service;

import java.util.List;

import com.refacFabela.controller.BalanceAbonoProveedor;
import com.refacFabela.model.TwAbonoFacturaProveedor;
import com.refacFabela.model.TwFacturaProveedorProducto;
import com.refacFabela.model.TwFacturaProveedorProductoIngreso;
import com.refacFabela.model.TwFacturasProveedor;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.model.VwFacturaProductoBalance;
import com.refacFabela.model.VwFacturaProveedorBalance;
import com.refacFabela.tipoCambio.DataSerie;

public interface FacturasProveedorService {
	
	public List<TwFacturasProveedor> obtenetTodas();
	public List<TwFacturasProveedor> obtenetPendienteIngreso();
	public List<TwFacturasProveedor> obtenetFacturasProveedor(Long nIdProveedor, Long nIdMoneda);
	public List<BalanceAbonoProveedor> obtenetFacturasProveedorBalance(Long nIdProveedor, Long nIdMoneda);
	public List<BalanceAbonoProveedor> obtenetFacturasProveedorBalanceHistoria(Long nIdProveedor, Long nIdMoneda);
	public List<TwAbonoFacturaProveedor> obtenetAbonosFactura(Long nIdFactura);
	public List<VwFacturaProveedorBalance> obtenetBalanceProveedores();
	public BalanceAbonoProveedor obtenerBalanceFactura(Long nIdFactura);
	public TwFacturasProveedor obtenerFactura(Long nIdFactura);
	public List<BalanceAbonoProveedor> obtenerFacturasSinPagar();
	public List<VwFacturaProductoBalance> obtenerVwFacturaProductoBalanceEstatus(Integer nEstatusAlmacen);
	public List<TwFacturaProveedorProducto> getTwFacturaProveedorProductoId(Long nIdFactura);
	public List<TwProductobodega> descontarVentasPedido(Long nIdProducto);

	public List<TwFacturaProveedorProductoIngreso> getTwFacturaProveedorProductoIngresoId(Long nId);
	public TwFacturaProveedorProductoIngreso saveTwFacturaProveedorProductoIngreso(TwFacturaProveedorProductoIngreso twFacturaProveedorProductoIngreso);
	public TwFacturaProveedorProducto saveTwFacturaProveedorProductoId(TwFacturaProveedorProducto twFacturaProveedorProducto);
	public DataSerie obtenetTipoCambioBM();
	public TwFacturasProveedor guardarFacturaProveedor(TwFacturasProveedor factura);
	public TwAbonoFacturaProveedor guardarAbonoFacturaProveedor(TwAbonoFacturaProveedor abono);
	 void borrarProductoFactura(Long nId);
	

}
