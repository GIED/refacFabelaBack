package com.refacFabela.service;

import java.util.List;

import com.refacFabela.model.TwFacturasProveedor;
import com.refacFabela.model.VwFacturaProveedorBalance;
import com.refacFabela.tipoCambio.DataSerie;

public interface FacturasProveedorService {
	
	public List<TwFacturasProveedor> obtenetTodas();
	public List<TwFacturasProveedor> obtenetFacturasProveedor(Long id);
	public List<VwFacturaProveedorBalance> obtenetBalanceProveedores();
	public DataSerie obtenetTipoCambioBM();
	public TwFacturasProveedor guardarFacturaProveedor(TwFacturasProveedor factura);




}
