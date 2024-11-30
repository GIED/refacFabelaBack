package com.refacFabela.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.dto.VentaCotizacionProductoAnoDto;
import com.refacFabela.model.VwProductoMetaCompra;
import com.refacFabela.repository.VentasCotizacionesProductoAnoRepository;
import com.refacFabela.repository.VwProductoMetaCompraRepository;
import com.refacFabela.service.ComprasService;

@Service
public class CompraServiceImpl implements ComprasService {
	
	@Autowired
	private VwProductoMetaCompraRepository vwProductoMetaCompraRepository;
	
	@Autowired
	private VentasCotizacionesProductoAnoRepository ventasCotizacionesProductoAnoRepository;
	

	public List<VwProductoMetaCompra> obtenerProductosVendidosFechaCompra(String FechaIncio, String FechaTermino) {	 
		
		return vwProductoMetaCompraRepository.ultimafechaCompra(FechaIncio, FechaTermino);
	}


	@Override
	public List<VentaCotizacionProductoAnoDto> obtenerVentaCotizacionProductoAnoDto(Integer idProducto) {
	
		return ventasCotizacionesProductoAnoRepository.getSalesAndQuotations(idProducto);
	}

}
