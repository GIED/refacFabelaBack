package com.refacFabela.service;

import java.util.List;



import com.refacFabela.model.TcProveedore;
import com.refacFabela.model.TwFacturasProveedor;

public interface ProveedoresService {
	
	public List<TcProveedore> obtenerProveedores();
	public TcProveedore guardaProveedor(TcProveedore tcProveedores);
	public TcProveedore consultaProveedorId(Long id);
	public String eliminaProveedor(Long id);

}
